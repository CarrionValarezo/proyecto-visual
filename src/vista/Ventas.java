/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.scene.input.DataFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DatosDetalleVenta;
import modelo.DatosLogin;
import modelo.DatosProducto;
import modelo.Venta;
import modelo.DatosVenta;
import modelo.DetalleVenta;
import modelo.Validadores;
import modelo.login;

/**
 *
 */
public class Ventas extends javax.swing.JInternalFrame {

    Venta ven = new Venta();
    DetalleVenta detalle = new DetalleVenta();
    DatosVenta usuario = new DatosVenta();
    Login log = new Login();
    DatosVenta venta = new DatosVenta();
    DatosLogin login = new DatosLogin();
    DatosProducto producto = new DatosProducto();
    DatosDetalleVenta detven = new DatosDetalleVenta();
    Validadores val = new Validadores();
    Date fecha = new Date();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-YYYY");

    public Ventas() {
        initComponents();
        limpiarCampos();
        bloquearBotones();
        bloquearCampos();
        listarVentas();
    }

    public void insertarDetalleVenta() {

        for (int i = 0; i < jTblVenta.getRowCount(); i++) {

            detalle.setIdPertenece(venta.numeroVenta());
            detalle.setIdProducto(detven.consultarIdProducto(String.valueOf(jTblVenta.getValueAt(i, 0).toString())));
            detalle.setCantidad(Integer.valueOf(jTblVenta.getValueAt(i, 3).toString()));

            detven.modificarStock(((venta.buscarStockProducto(String.valueOf(jTblVenta.getValueAt(i, 0).toString()))) - (Integer.valueOf(jTblVenta.getValueAt(i, 3).toString()))), detven.consultarIdProducto(String.valueOf(jTblVenta.getValueAt(i, 0).toString())));
            detven.insertarDetalle(detalle);
        }

        limpiarTabla();
    }

    public void insertarVenta() {
        String metodo = (String) jCbxMetodo.getSelectedItem();

        ven.setCedulaCajero(log.cedula);
        ven.setCedulaCliente(jTxtCedula.getText());
        ven.setMetodo(metodo);
        ven.setTotal(Float.parseFloat(jLblTotal.getText()));
        ven.setEstado("COMPLETADO");

        usuario.insertar(ven);

        limpiarCampos();
        bloquearBotones();
        bloquearCampos();
    }

    private void insertarDetalle() {

        if (!jTxtCantidad.getText().equals("")) {
            DecimalFormatSymbols separador = new DecimalFormatSymbols();
            DecimalFormat df = new DecimalFormat("0.00");
            separador.setDecimalSeparator('.');
            int stock = venta.buscarStockProducto(jTxtCodigoProducto.getText());
            double precio = Double.parseDouble(jLblPrecioProducto.getText());
            int cantidad = Integer.valueOf(jTxtCantidad.getText());
            DefaultTableModel modelo = (DefaultTableModel) jTblVenta.getModel();

            if (!verificadorExistenciaDetalle().equals(jTxtCodigoProducto.getText())) {
                if (cantidad > stock) {
                    JOptionPane.showMessageDialog(null, "Solo existen " + stock + " " + jLblNombreProducto.getText() + " en stock", "Aviso", JOptionPane.ERROR_MESSAGE);
                } else {
                    Object[] fila = new Object[6];

                    fila[0] = jTxtCodigoProducto.getText();
                    fila[1] = jLblNombreProducto.getText();
                    fila[2] = jLblPrecioProducto.getText();
                    fila[3] = cantidad;
                    fila[4] = val.cambiarComa(String.valueOf(df.format((precio * 0.12) * cantidad)));
                    fila[5] = val.cambiarComa(String.valueOf(df.format((precio * cantidad) + ((precio * 0.12) * cantidad))));

                    modelo.addRow(fila);
                    jTblVenta.setModel(modelo);
                    calcularTotal();
                    limpiarCamposAgregar();
                    bloquearBotonesAgregar();
                    bloquearCamposAgregar();
                    jBtnAgregar.setEnabled(false);
                    if (jTblVenta.getRowCount() == 0) {
                        jBtnGuardar.setEnabled(false);
                    } else {
                        jBtnGuardar.setEnabled(true);
                    }

                }
            } else {
                String cod = "";
                int can = 0;
                int fil = 0;

                for (int i = 0; i < jTblVenta.getRowCount(); i++) {
                    String codigo = String.valueOf(jTblVenta.getValueAt(i, 0).toString());
                    int cant = Integer.valueOf(jTblVenta.getValueAt(i, 3).toString());
                    if (codigo.equals(jTxtCodigoProducto.getText())) {
                        cod = codigo;
                        can = cant;
                        fil = i;
                    }
                }

                if ((cantidad + can) > stock) {
                    JOptionPane.showMessageDialog(null, "Solo existen " + stock + " " + jLblNombreProducto.getText() + " en stock", "Aviso", JOptionPane.ERROR_MESSAGE);
                } else {
                    jTblVenta.setValueAt((cantidad + can), fil, 3);
                    jTblVenta.setValueAt(val.cambiarComa(String.valueOf(df.format(((precio * 0.12) * (cantidad + can))))), fil, 4);
                    jTblVenta.setValueAt(val.cambiarComa(String.valueOf(df.format((precio + (precio * 0.12)) * (cantidad + can)))), fil, 5);

                    calcularTotal();
                    limpiarCamposAgregar();
                    bloquearBotonesAgregar();
                    bloquearCamposAgregar();
                    jBtnAgregar.setEnabled(false);
                    if (jTblVenta.getRowCount() == 0) {
                        jBtnGuardar.setEnabled(false);
                    } else {
                        jBtnGuardar.setEnabled(true);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor coloque la cantidad de productos a agregar", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarDetalle() {
        DefaultTableModel modelo = (DefaultTableModel) jTblVenta.getModel();

        if (Integer.valueOf(jTxtCantidad.getText()) == 0) {
            modelo.removeRow(Integer.valueOf(jTxtCantidad.getText()));

            bloquearBotonesNuevo();
            jBtnAgregar.setEnabled(false);
            jBtnGuardar.setEnabled(false);
            desbloquearCamposPrimerPaso();
            calcularTotal();
            limpiarCamposAgregar();
            jLblTotal.setText("");
        } else {
            if (Integer.valueOf(jTxtCantidad.getText()) > venta.buscarStockProducto(jTxtCodigoProducto.getText())) {
                JOptionPane.showMessageDialog(null, "Solo existen " + venta.buscarStockProducto(jTxtCodigoProducto.getText()) + " " + jLblNombreProducto.getText() + " en stock", "Aviso", JOptionPane.ERROR_MESSAGE);
            } else {
                jTblVenta.setValueAt(Integer.valueOf(jTxtCantidad.getText()), jTblVenta.getSelectedRow(), 3);
                jTblVenta.setValueAt((Integer.valueOf(jTxtCantidad.getText()) * Double.parseDouble(jLblPrecioProducto.getText())) * 0.12, jTblVenta.getSelectedRow(), 4);
                jTblVenta.setValueAt(((Integer.valueOf(jTxtCantidad.getText()) * Double.parseDouble(jLblPrecioProducto.getText())) * 0.12) + (Integer.valueOf(jTxtCantidad.getText()) * Double.parseDouble(jLblPrecioProducto.getText())), jTblVenta.getSelectedRow(), 5);

                bloquearBotonesNuevo();
                jBtnAgregar.setEnabled(false);
                desbloquearCamposPrimerPaso();
                calcularTotal();
                limpiarCamposAgregar();
                jTblVenta.clearSelection();
                if (jTblVenta.getRowCount() == 0) {
                    jBtnGuardar.setEnabled(false);
                } else {
                    jBtnGuardar.setEnabled(true);
                }
            }
        }
    }

    public void eliminarDetalle() {
        DefaultTableModel modelo = (DefaultTableModel) jTblVenta.getModel();

        modelo.removeRow(jTblVenta.getSelectedRow());
        bloquearBotonesNuevo();
        jBtnAgregar.setEnabled(false);
        desbloquearCamposPrimerPaso();

        if (jTblVenta.getRowCount() == 0) {
            jBtnGuardar.setEnabled(false);
        } else {
            jBtnGuardar.setEnabled(true);
        }
        limpiarCamposAgregar();
        if (jTblVenta.getRowCount() == 0) {
            jLblTotal.setText("");
        } else {
            calcularTotal();
        }
    }

    public void anularVenta() {
        Object id = jTblVentasCajero.getValueAt(jTblVentasCajero.getSelectedRow(), 0).toString();
        List<DetalleVenta> ListarDetVen = detven.productosDevolucion((String) id);

        for (int i = 0; i < detven.cantidadProductosVenta((String) id); i++) {
            int idAux = Integer.valueOf(ListarDetVen.get(i).getIdProducto());
            int canAux = Integer.valueOf(ListarDetVen.get(i).getCantidad());
            int stockAct = Integer.valueOf(venta.buscarStockProducto2(idAux));

            detven.modificarStock((stockAct+canAux), (int) idAux);
        }
        ven.setId((String) id);
        venta.anular(ven);
        limpiarTablaVentas();
        listarVentas();
    }

    public void numeroVenta() {
        int numero = venta.numeroVenta();
        int numerof = numero + 1;
        jLblNumeroFactura.setText(String.valueOf(numerof));
    }

    public void buscarCliente() {
        String cedula = jTxtCedula.getText();
        String estado = venta.estadoCliente(cedula);
        String nombre = venta.buscarCliente(cedula);

        if ("INACTIVO".equals(estado)) {
            int opc = JOptionPane.showConfirmDialog(null, "El usuario está inactivo, ¿desea activarlo?", "Confirmar activación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (opc == 0) {
                venta.activarCliente(cedula);
                jLblCliente.setText(nombre);
                desbloquearCamposPrimerPaso();
                jBtnBuscarCedula.setEnabled(false);
            } else {
                jLblCliente.setText("");
            }
        }
        if ("ACTIVO".equals(estado)) {
            jLblCliente.setText(nombre);
            desbloquearCamposPrimerPaso();
            jBtnBuscarCedula.setEnabled(false);

        }
        if ("".equals(estado)) {
            int opc = JOptionPane.showConfirmDialog(null, "El cliente no existe ¿Desea registrar el cliente?", "Confirmar registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opc == 0) {
                Clientes cli = new Clientes();
                Menu.jDskMenu.add(cli);
                cli.toFront();
                cli.setVisible(true);
            }
        }
    }

    public void listarVentas() {
        List<Venta> ListarVen = venta.listar(String.valueOf(jCbxVentas.getSelectedItem()));
        DefaultTableModel modelo = (DefaultTableModel) jTblVentasCajero.getModel();
        Object[] ob = new Object[6];

        for (int i = 0; i < ListarVen.size(); i++) {
            ob[0] = ListarVen.get(i).getId();
            ob[1] = ListarVen.get(i).getCedulaCliente();
            ob[2] = ListarVen.get(i).getFecha();
            ob[3] = ListarVen.get(i).getMetodo();
            ob[4] = ListarVen.get(i).getTotal();
            ob[5] = ListarVen.get(i).getEstado();

            modelo.addRow(ob);
        }
        jTblVentasCajero.setModel(modelo);
    }

    public void buscarProducto() {
        String codigo = jTxtCodigoProducto.getText();
        String nombre = venta.buscarNombreProducto(codigo);
        float precio = venta.buscarPrecioProducto(codigo);

        jTxtCantidad.setText("");
        jLblNombreProducto.setText(nombre);
        jLblPrecioProducto.setText(String.valueOf(precio));

        if (!jLblNombreProducto.getText().equals("")) {
            if (!jLblPrecioProducto.getText().equals("")) {
                jTxtCantidad.setText("1");
                jBtnAgregar.setEnabled(true);
            }
        } else {
            jBtnAgregar.setEnabled(false);
            JOptionPane.showMessageDialog(null, "El producto no existe", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String verificadorExistenciaDetalle() {
        String codigo = "";
        String cod = "";

        for (int i = 0; i < jTblVenta.getRowCount(); i++) {
            codigo = String.valueOf(jTblVenta.getValueAt(i, 0).toString());
            if (codigo.equals(jTxtCodigoProducto.getText())) {
                cod = codigo;
            }
        }
        return cod;
    }

    public void calcularTotal() {

        DecimalFormat df = new DecimalFormat("0.00");
        double total1 = 0;
        double total = 0;

        for (int i = 0; i < jTblVenta.getRowCount(); i++) {
            total = Double.parseDouble(jTblVenta.getValueAt(i, 5).toString());
            total1 = total1 + total;
        }
        jLblTotal.setText(val.cambiarComa(String.valueOf(df.format(total1))));
    }

    public void limpiarCamposAgregar() {
        jTxtCodigoProducto.setText("");
        jTxtCantidad.setText("");
        jLblPrecioProducto.setText("");
        jLblNombreProducto.setText("");
    }

    public void bloquearBotonesAgregar() {
        jBtnNuevo.setEnabled(false);
        jBtnAgregar.setEnabled(true);
        jBtnActualizar.setEnabled(false);
        jBtnEliminar.setEnabled(false);
        jBtnCancelar.setEnabled(true);
        jBtnBuscarCedula.setEnabled(false);
        jBtnBuscarProducto.setEnabled(true);
    }

    public void bloquearCamposAgregar() {
        jTxtCedula.setEnabled(false);
        jTxtCodigoProducto.setEnabled(true);
        jTxtCantidad.setEnabled(true);
        jCbxMetodo.setEnabled(true);
    }

    public void limpiarCampos() {
        jTxtCedula.setText("");
        jTxtCodigoProducto.setText("");
        jTxtCantidad.setText("");
        jLblPrecioProducto.setText("");
        jLblNombreProducto.setText("");
        jLblFecha.setText("");
        jLblCliente.setText("");
        jLblNumeroFactura.setText("");
        jLblTotal.setText("");
        jCbxMetodo.setSelectedIndex(0);
    }

    public void bloquearBotones() {
        jBtnNuevo.setEnabled(true);
        jBtnAgregar.setEnabled(false);
        jBtnActualizar.setEnabled(false);
        jBtnEliminar.setEnabled(false);
        jBtnCancelar.setEnabled(false);
        jBtnBuscarCedula.setEnabled(false);
        jBtnBuscarProducto.setEnabled(false);
        jBtnGuardar.setEnabled(false);
    }

    public void bloquearCampos() {
        jTxtCedula.setEnabled(false);
        jTxtCodigoProducto.setEnabled(false);
        jTxtCantidad.setEnabled(false);
        jCbxMetodo.setEnabled(false);
    }

    public void desbloquearCampos() {
        jTxtCedula.setEnabled(true);
        jTxtCodigoProducto.setEnabled(false);
        jTxtCantidad.setEnabled(false);
        jCbxMetodo.setEnabled(false);
    }

    public void desbloquearCamposPrimerPaso() {
        jTxtCedula.setEnabled(false);
        jTxtCodigoProducto.setEnabled(true);
        jTxtCantidad.setEnabled(true);
        jCbxMetodo.setEnabled(true);
    }

    public void bloquearBotonesNuevo() {
        jBtnNuevo.setEnabled(false);
        jBtnAgregar.setEnabled(false);
        jBtnActualizar.setEnabled(false);
        jBtnEliminar.setEnabled(false);
        jBtnCancelar.setEnabled(true);
        jBtnBuscarCedula.setEnabled(true);
        jBtnBuscarProducto.setEnabled(true);
    }

    public void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTblVenta.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaVentas() {
        DefaultTableModel modelo = (DefaultTableModel) jTblVentasCajero.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        DOCUMENTO = new javax.swing.JLabel();
        jTxtCedula = new javax.swing.JTextField();
        jBtnBuscarCedula = new javax.swing.JButton();
        CLIENTE1 = new javax.swing.JLabel();
        CLIENTE2 = new javax.swing.JLabel();
        jBtnBuscarProducto = new javax.swing.JButton();
        CLIENTE3 = new javax.swing.JLabel();
        CLIENTE4 = new javax.swing.JLabel();
        jTxtCodigoProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblVenta = new javax.swing.JTable();
        CLIENTE5 = new javax.swing.JLabel();
        CLIENTE6 = new javax.swing.JLabel();
        jTxtCantidad = new javax.swing.JTextField();
        jLblNumeroFactura = new javax.swing.JLabel();
        jLblCliente = new javax.swing.JLabel();
        jLblPrecioProducto = new javax.swing.JLabel();
        jLblTotal = new javax.swing.JLabel();
        jLblNombreProducto = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jLblFecha = new javax.swing.JLabel();
        CLIENTE7 = new javax.swing.JLabel();
        jCbxMetodo = new javax.swing.JComboBox<>();
        jBtnNuevo = new javax.swing.JButton();
        jBtnAgregar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        CLIENTE8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblVentasCajero = new javax.swing.JTable();
        jCbxVentas = new javax.swing.JComboBox<>();
        jBtnAnular = new javax.swing.JButton();

        setClosable(true);
        setTitle("VENTAS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("N° VENTA:");

        DOCUMENTO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DOCUMENTO.setText("DOCUMENTO:");

        jTxtCedula.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCedulaKeyTyped(evt);
            }
        });

        jBtnBuscarCedula.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        jBtnBuscarCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarCedulaActionPerformed(evt);
            }
        });

        CLIENTE1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE1.setText("CLIENTE:");

        CLIENTE2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE2.setText("FECHA:");

        jBtnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        jBtnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarProductoActionPerformed(evt);
            }
        });

        CLIENTE3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE3.setText("PRODUCTO:");

        CLIENTE4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE4.setText("COD. PROD:");

        jTxtCodigoProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCodigoProductoKeyTyped(evt);
            }
        });

        jTblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "PRODUCTO", "PRECIO UNITARIO", "CANTIDAD", "IVA", "SUBTOTAL"
            }
        ));
        jTblVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblVentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblVenta);

        CLIENTE5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE5.setText("CANTIDAD:");

        CLIENTE6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE6.setText("PRECIO:");

        jTxtCantidad.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCantidadKeyTyped(evt);
            }
        });

        jLblNumeroFactura.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLblNumeroFactura.setForeground(new java.awt.Color(51, 51, 51));

        jLblCliente.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLblPrecioProducto.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblTotal.setForeground(new java.awt.Color(0, 0, 153));

        jLblNombreProducto.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLblNombreProducto.setForeground(new java.awt.Color(51, 51, 51));

        jBtnGuardar.setText("GUARDAR");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jLblFecha.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        CLIENTE7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE7.setText("METODO DE PAGO:");

        jCbxMetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EFECTIVO", "TARJETA DE CREDITO" }));

        jBtnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ventas.png"))); // NOI18N
        jBtnNuevo.setText("NUEVO");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });

        jBtnAgregar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jBtnAgregar.setText("AGREGAR");
        jBtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAgregarActionPerformed(evt);
            }
        });

        jBtnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar2.png"))); // NOI18N
        jBtnActualizar.setText("ACTUALIZAR");
        jBtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarActionPerformed(evt);
            }
        });

        jBtnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        jBtnEliminar.setText("ELIMINAR");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jBtnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jBtnCancelar.setText("CANCELAR");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        CLIENTE8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CLIENTE8.setText("TOTAL A PAGAR:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CLIENTE7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCbxMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126)
                        .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CLIENTE8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DOCUMENTO)
                            .addComponent(CLIENTE4)
                            .addComponent(jLabel2)
                            .addComponent(CLIENTE3))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtCedula)
                                    .addComponent(jTxtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jBtnBuscarCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jBtnBuscarProducto)))
                            .addComponent(jLblNumeroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CLIENTE1)
                            .addComponent(CLIENTE2)
                            .addComponent(CLIENTE5)
                            .addComponent(CLIENTE6))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jBtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLblNumeroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CLIENTE2))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(DOCUMENTO)
                                        .addComponent(jTxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jBtnBuscarCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CLIENTE1)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CLIENTE4)
                                    .addComponent(jTxtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(CLIENTE3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLblNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CLIENTE5)
                                    .addComponent(jTxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(CLIENTE6))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLblPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CLIENTE8)
                        .addComponent(jCbxMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CLIENTE7))
                    .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jTabbedPane3.addTab("INGRESAR VENTA", jPanel1);

        jTblVentasCajero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° VENTA", "CLIENTE", "FECHA", "PAGO", "TOTAL", "ESTADO"
            }
        ));
        jScrollPane2.setViewportView(jTblVentasCajero);

        jCbxVentas.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jCbxVentas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMPLETADO", "ANULADO" }));
        jCbxVentas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCbxVentasItemStateChanged(evt);
            }
        });

        jBtnAnular.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jBtnAnular.setText("ANULAR");
        jBtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jBtnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCbxVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 954, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCbxVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("CONSULTAR VENTAS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar la venta?", "Confirmar cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opc == 0) {
            bloquearBotones();
            limpiarCampos();
            bloquearCampos();
            limpiarTabla();
        }
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        eliminarDetalle();
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        actualizarDetalle();
    }//GEN-LAST:event_jBtnActualizarActionPerformed

    private void jBtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAgregarActionPerformed
        insertarDetalle();
    }//GEN-LAST:event_jBtnAgregarActionPerformed

    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        bloquearBotonesNuevo();
        desbloquearCampos();
        numeroVenta();
        jLblFecha.setText(formatoFecha.format(fecha));
    }//GEN-LAST:event_jBtnNuevoActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        insertarVenta();
        insertarDetalleVenta();
        listarVentas();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTblVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblVentaMouseClicked
        int fila = jTblVenta.rowAtPoint(evt.getPoint());
        jTxtCodigoProducto.setText(jTblVenta.getValueAt(fila, 0).toString());
        jLblNombreProducto.setText(jTblVenta.getValueAt(fila, 1).toString());
        jLblPrecioProducto.setText(jTblVenta.getValueAt(fila, 2).toString());
        jTxtCantidad.setText(jTblVenta.getValueAt(fila, 3).toString());

        jBtnActualizar.setEnabled(true);
        jBtnEliminar.setEnabled(true);
        jTxtCodigoProducto.setEnabled(false);
        jBtnAgregar.setEnabled(false);
        jBtnCancelar.setEnabled(false);
        jBtnBuscarProducto.setEnabled(false);
    }//GEN-LAST:event_jTblVentaMouseClicked

    private void jBtnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarProductoActionPerformed
        buscarProducto();
    }//GEN-LAST:event_jBtnBuscarProductoActionPerformed

    private void jBtnBuscarCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarCedulaActionPerformed
        buscarCliente();
    }//GEN-LAST:event_jBtnBuscarCedulaActionPerformed

    private void jTxtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCedulaKeyTyped
        int contador = 0;
        contador = jTxtCedula.getText().length();

        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (contador >= 13) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtCedulaKeyTyped

    private void jTxtCodigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCodigoProductoKeyTyped
        int contador = 0;
        contador = jTxtCodigoProducto.getText().length();

        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (contador >= 13) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtCodigoProductoKeyTyped

    private void jTxtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCantidadKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtCantidadKeyTyped

    private void jCbxVentasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCbxVentasItemStateChanged
        limpiarTablaVentas();
        listarVentas();
    }//GEN-LAST:event_jCbxVentasItemStateChanged

    private void jBtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnularActionPerformed
        anularVenta();
    }//GEN-LAST:event_jBtnAnularActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CLIENTE1;
    private javax.swing.JLabel CLIENTE2;
    private javax.swing.JLabel CLIENTE3;
    private javax.swing.JLabel CLIENTE4;
    private javax.swing.JLabel CLIENTE5;
    private javax.swing.JLabel CLIENTE6;
    private javax.swing.JLabel CLIENTE7;
    private javax.swing.JLabel CLIENTE8;
    private javax.swing.JLabel DOCUMENTO;
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnAgregar;
    private javax.swing.JButton jBtnAnular;
    private javax.swing.JButton jBtnBuscarCedula;
    private javax.swing.JButton jBtnBuscarProducto;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JComboBox<String> jCbxMetodo;
    private javax.swing.JComboBox<String> jCbxVentas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLblCliente;
    private javax.swing.JLabel jLblFecha;
    private javax.swing.JLabel jLblNombreProducto;
    private javax.swing.JLabel jLblNumeroFactura;
    private javax.swing.JLabel jLblPrecioProducto;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTblVenta;
    private javax.swing.JTable jTblVentasCajero;
    private javax.swing.JTextField jTxtCantidad;
    private javax.swing.JTextField jTxtCedula;
    private javax.swing.JTextField jTxtCodigoProducto;
    // End of variables declaration//GEN-END:variables
}
