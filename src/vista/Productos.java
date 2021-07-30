/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jbarcodebean.JBarcodeBean;
import modelo.Producto;
import modelo.DatosProducto;
import net.sourceforge.jbarcodebean.model.Interleaved25;

/**
 *
 * @author Alejandro
 */
public class Productos extends javax.swing.JInternalFrame {

    Producto pro = new Producto();
    DatosProducto producto = new DatosProducto();
    DefaultTableModel modelo = new DefaultTableModel();
    JBarcodeBean barcode = new JBarcodeBean();
    public static BufferedImage imagen = null;

    public Productos() {
        initComponents();
        producto.consultarCategoria(jCbxCategoriaProducto);
        bloquearBotones();
        bloquearCampos();
        listarProductos();
    }

    public void insertarProducto() {
        String categoria = (String) jCbxCategoriaProducto.getSelectedItem();

        if (!"".equals(jTxtNombreProducto.getText()) && !"".equals(jTxtCodigoProducto.getText()) && !"".equals(jTxtPrecioProducto.getText()) && !"".equals(jTxtCantidadProducto.getText())) {
            pro.setCodigo(jTxtCodigoProducto.getText());
            pro.setNombre(jTxtNombreProducto.getText());
            pro.setPrecio(Float.valueOf(jTxtPrecioProducto.getText()));
            pro.setCantidad(Integer.valueOf(jTxtCantidadProducto.getText()));
            if ("".equals(jTxtDescripcionProducto.getText())) {
                pro.setDescripcion("SIN DESCRIPCIÓN");
            } else {
                pro.setDescripcion(jTxtDescripcionProducto.getText());
            }
            pro.setEstado("ACTIVO");
            if (categoria.charAt(1) == '.') {
                pro.setCategoria(Character.getNumericValue(categoria.charAt(0)));
            } else {
                pro.setCategoria(Character.getNumericValue((categoria.charAt(0)) + (categoria.charAt(1))));
            }
            if (producto.comprobar(jTxtCodigoProducto.getText()) == false) {
                producto.insertar(pro);

                bloquearBotones();
                bloquearCampos();
                limpiarTabla();
                listarProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un producto registrado con ese código");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }
    }

    public void actualizarProducto() {
        if (!"".equals(jTxtNombreProducto.getText()) && !"".equals(jTxtCodigoProducto.getText()) && !"".equals(jTxtPrecioProducto.getText()) && !"".equals(jTxtCantidadProducto.getText())) {
            pro.setId(Integer.valueOf(jTxtIdProducto.getText()));
            pro.setNombre(jTxtNombreProducto.getText());
            pro.setPrecio(Float.valueOf(jTxtPrecioProducto.getText()));
            pro.setCantidad(Integer.valueOf(jTxtCantidadProducto.getText()));
            if ("".equals(jTxtDescripcionProducto.getText())) {
                pro.setDescripcion("SIN DESCRIPCIÓN");
            } else {
                pro.setDescripcion(jTxtDescripcionProducto.getText());
            }
            
            pro.setCategoria(jCbxCategoriaProducto.getSelectedIndex());

            producto.actualizar(pro);

            limpiarCampos();
            limpiarTabla();
            listarProductos();
            bloquearBotones();
            bloquearCampos();

        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }
    }

    public void eliminarProducto() {

        int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opc == 0) {
            pro.setId(Integer.valueOf(jTxtIdProducto.getText()));
            pro.setEstado("INACTIVO");
            producto.eliminar(pro);

            limpiarCampos();
            limpiarTabla();
            listarProductos();
            bloquearBotones();
            bloquearCampos();
        }
    }

    public void buscar() {
        limpiarTabla();
        List<Producto> ListarPro = producto.buscar(jTxtBuscarProducto.getText());
        modelo = (DefaultTableModel) jTblProducto.getModel();
        Object[] ob = new Object[8];

        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getNombre();
            ob[2] = ListarPro.get(i).getCodigo();
            ob[3] = ListarPro.get(i).getPrecio();
            ob[4] = ListarPro.get(i).getCantidad();
            ob[5] = ListarPro.get(i).getDescripcion();
            ob[6] = ListarPro.get(i).getEstado();
            ob[7] = ListarPro.get(i).getCategoria();
            modelo.addRow(ob);
        }
        jTblProducto.setModel(modelo);
        limpiarBusqueda();
    }

    public void listarProductos() {
        List<Producto> ListarPro = producto.listar(String.valueOf(jCbxEstadoProducto.getSelectedItem()));
        modelo = (DefaultTableModel) jTblProducto.getModel();
        Object[] ob = new Object[8];

        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getNombre();
            ob[2] = ListarPro.get(i).getCodigo();
            ob[3] = ListarPro.get(i).getPrecio();
            ob[4] = ListarPro.get(i).getCantidad();
            ob[5] = ListarPro.get(i).getDescripcion();
            ob[6] = ListarPro.get(i).getEstado();
            int idCat = ListarPro.get(i).getCategoria();
            ob[7] = idCat + ".- " + producto.consultarNombreCategoria(idCat);
            modelo.addRow(ob);
        }
        jTblProducto.setModel(modelo);
    }

    public void activarProducto() {
        int id = Integer.valueOf(jTxtIdProducto.getText());
        String estado = producto.estadoProducto(id);

        if ("INACTIVO".equals(estado)) {
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea activar el producto?");
            if (opc == 0) {
                producto.activarProducto(id);
                bloquearBotones();
                bloquearCampos();
                limpiarCampos();
                limpiarTabla();
                listarProductos();
            }
        }
    }

    public void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void limpiarCampos() {
        jTxtIdProducto.setText("");
        jTxtNombreProducto.setText("");
        jTxtCodigoProducto.setText("");
        jTxtPrecioProducto.setText("");
        jTxtCantidadProducto.setText("");
        jTxtDescripcionProducto.setText("");
        jCbxCategoriaProducto.setSelectedIndex(0);
        jLblCodigo.setIcon(frameIcon);
    }

    private void limpiarBusqueda() {
        jTxtBuscarProducto.setText("");
    }

    public void bloquearBotones() {
        jBtnNuevoProducto.setEnabled(true);
        jBtnGuardarProducto.setEnabled(false);
        jBtnActualizarProducto.setEnabled(false);
        jBtnEliminarProducto.setEnabled(false);
        jBtnCancelarProducto.setEnabled(false);
        jBtnActivarProducto.setEnabled(false);
    }

    public void bloquearBotonesInactivo() {
        jBtnNuevoProducto.setEnabled(false);
        jBtnGuardarProducto.setEnabled(false);
        jBtnActualizarProducto.setEnabled(false);
        jBtnEliminarProducto.setEnabled(false);
        jBtnCancelarProducto.setEnabled(true);
        jBtnActivarProducto.setEnabled(true);
    }

    public void bloquearBotonesNuevo() {
        jBtnNuevoProducto.setEnabled(false);
        jBtnGuardarProducto.setEnabled(true);
        jBtnActualizarProducto.setEnabled(false);
        jBtnEliminarProducto.setEnabled(false);
        jBtnCancelarProducto.setEnabled(true);
        jBtnActivarProducto.setEnabled(false);
    }

    public void bloquearBotonesTabla() {
        jBtnNuevoProducto.setEnabled(false);
        jBtnGuardarProducto.setEnabled(false);
        jBtnActualizarProducto.setEnabled(true);
        jBtnEliminarProducto.setEnabled(true);
        jBtnCancelarProducto.setEnabled(true);
        jBtnActivarProducto.setEnabled(false);
    }

    public void bloquearCampos() {
        jTxtIdProducto.setEnabled(false);
        jTxtNombreProducto.setEnabled(false);
        jTxtCodigoProducto.setEnabled(false);
        jTxtPrecioProducto.setEnabled(false);
        jTxtCantidadProducto.setEnabled(false);
        jTxtDescripcionProducto.setEnabled(false);
        jCbxCategoriaProducto.setEnabled(false);
    }

    public void bloquearCamposActualizar() {
        jTxtIdProducto.setEnabled(false);
        jTxtNombreProducto.setEnabled(true);
        jTxtCodigoProducto.setEnabled(false);
        jTxtPrecioProducto.setEnabled(true);
        jTxtCantidadProducto.setEnabled(true);
        jTxtDescripcionProducto.setEnabled(true);
        jCbxCategoriaProducto.setEnabled(false);
    }

    public void desbloquearCampos() {
        jTxtIdProducto.setEnabled(false);
        jTxtNombreProducto.setEnabled(true);
        jTxtCodigoProducto.setEnabled(true);
        jTxtPrecioProducto.setEnabled(true);
        jTxtCantidadProducto.setEnabled(true);
        jTxtDescripcionProducto.setEnabled(true);
        jCbxCategoriaProducto.setEnabled(true);
    }

    private void generaCodigo(String codigo) {
        // nuestro tipo de codigo de barra
        barcode.setCodeType(new Interleaved25());
        //barcode.setCodeType(new Code39());

        // nuestro valor a codificar y algunas configuraciones mas
        barcode.setCode(codigo);
        barcode.setCheckDigit(true);

        imagen = barcode.draw(new BufferedImage(470, 250, BufferedImage.TYPE_INT_RGB));

        ImageIcon barras = new ImageIcon(imagen);
        this.jLblCodigo.setIcon(barras);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtNombreProducto = new javax.swing.JTextField();
        jBtnNuevoProducto = new javax.swing.JButton();
        jBtnGuardarProducto = new javax.swing.JButton();
        jBtnActualizarProducto = new javax.swing.JButton();
        jBtnEliminarProducto = new javax.swing.JButton();
        jBtnCancelarProducto = new javax.swing.JButton();
        jTxtIdProducto = new javax.swing.JTextField();
        jTxtCodigoProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtPrecioProducto = new javax.swing.JTextField();
        jTxtCantidadProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTxtDescripcionProducto = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jCbxCategoriaProducto = new javax.swing.JComboBox<>();
        jLblCodigo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscarProducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblProducto = new javax.swing.JTable();
        jBtnBuscarCategoria = new javax.swing.JButton();
        jCbxEstadoProducto = new javax.swing.JComboBox<>();
        jBtnActivarProducto = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PRODUCTOS");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO DE DATOS"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("CÓDIGO:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("NOMBRE:");

        jTxtNombreProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtNombreProductoKeyTyped(evt);
            }
        });

        jBtnNuevoProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jBtnNuevoProducto.setText("NUEVO");
        jBtnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoProductoActionPerformed(evt);
            }
        });

        jBtnGuardarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jBtnGuardarProducto.setText("GUARDAR");
        jBtnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarProductoActionPerformed(evt);
            }
        });

        jBtnActualizarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActualizarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jBtnActualizarProducto.setText("ACTUALIZAR");
        jBtnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarProductoActionPerformed(evt);
            }
        });

        jBtnEliminarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        jBtnEliminarProducto.setText("ELIMINAR");
        jBtnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarProductoActionPerformed(evt);
            }
        });

        jBtnCancelarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnCancelarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jBtnCancelarProducto.setText("CANCELAR");
        jBtnCancelarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarProductoActionPerformed(evt);
            }
        });

        jTxtCodigoProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtCodigoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtCodigoProductoActionPerformed(evt);
            }
        });
        jTxtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCodigoProductoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCodigoProductoKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("PRECIO:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("STOCK:");

        jTxtPrecioProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtPrecioProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtPrecioProductoActionPerformed(evt);
            }
        });
        jTxtPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtPrecioProductoKeyTyped(evt);
            }
        });

        jTxtCantidadProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtCantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCantidadProductoKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("DESCRIPCIÓN:");

        jTxtDescripcionProducto.setColumns(20);
        jTxtDescripcionProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtDescripcionProducto.setRows(5);
        jTxtDescripcionProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTxtDescripcionProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtDescripcionProductoKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jTxtDescripcionProducto);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("CATEGORÍA:");

        jCbxCategoriaProducto.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jCbxCategoriaProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jCbxCategoriaProductoKeyTyped(evt);
            }
        });

        jLblCodigo.setBackground(new java.awt.Color(255, 255, 255));
        jLblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblCodigo.setToolTipText("");
        jLblCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jBtnNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(57, 57, 57)
                                .addComponent(jTxtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel7))
                                        .addGap(35, 35, 35))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCbxCategoriaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTxtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jTxtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jTxtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTxtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTxtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCbxCategoriaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DETALLES"));

        jTxtBuscarProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtBuscarProductoKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("BUSCAR:");

        jTblProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "CODIGO", "PRECIO", "STOCK", "DESCRIPCION", "ESTADO", "CATEGORIA"
            }
        ));
        jTblProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblProductoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTblProducto);

        jBtnBuscarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        jBtnBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarCategoriaActionPerformed(evt);
            }
        });

        jCbxEstadoProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        jCbxEstadoProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCbxEstadoProductoItemStateChanged(evt);
            }
        });

        jBtnActivarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActivarProducto.setText("ACTIVAR");
        jBtnActivarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActivarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(270, 270, 270)
                        .addComponent(jCbxEstadoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnActivarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTxtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnActivarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCbxEstadoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarProductoActionPerformed
        actualizarProducto();
    }//GEN-LAST:event_jBtnActualizarProductoActionPerformed

    private void jBtnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoProductoActionPerformed
        bloquearBotonesNuevo();
        desbloquearCampos();
    }//GEN-LAST:event_jBtnNuevoProductoActionPerformed

    private void jBtnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarProductoActionPerformed
        insertarProducto();
    }//GEN-LAST:event_jBtnGuardarProductoActionPerformed

    private void jBtnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarProductoActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_jBtnEliminarProductoActionPerformed

    private void jBtnCancelarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarProductoActionPerformed
        bloquearBotones();
        limpiarCampos();
        bloquearCampos();
        limpiarTabla();
        listarProductos();
    }//GEN-LAST:event_jBtnCancelarProductoActionPerformed

    private void jTblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblProductoMouseClicked
        int fila = jTblProducto.rowAtPoint(evt.getPoint());

        jTxtIdProducto.setText(jTblProducto.getValueAt(fila, 0).toString());
        jTxtNombreProducto.setText(jTblProducto.getValueAt(fila, 1).toString());
        jTxtCodigoProducto.setText(jTblProducto.getValueAt(fila, 2).toString());
        jTxtPrecioProducto.setText(jTblProducto.getValueAt(fila, 3).toString());
        jTxtCantidadProducto.setText(jTblProducto.getValueAt(fila, 4).toString());
        jTxtDescripcionProducto.setText(jTblProducto.getValueAt(fila, 5).toString());
        String estado = jTblProducto.getValueAt(fila, 6).toString();
        jCbxCategoriaProducto.setSelectedItem(jTblProducto.getValueAt(fila, 7).toString());
        jLblCodigo.setIcon(frameIcon);
        generaCodigo(jTxtCodigoProducto.getText());

        if (estado.equals("INACTIVO")) {
            bloquearBotonesInactivo();
            bloquearCampos();
        } else {
            bloquearBotonesTabla();
            desbloquearCampos();
            bloquearCamposActualizar();
        }

    }//GEN-LAST:event_jTblProductoMouseClicked

    private void jBtnBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarCategoriaActionPerformed
        buscar();
    }//GEN-LAST:event_jBtnBuscarCategoriaActionPerformed

    private void jTxtNombreProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombreProductoKeyTyped
        int contador = 0;
        contador = jTxtNombreProducto.getText().length();

        if (contador >= 100) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtNombreProductoKeyTyped

    private void jTxtCodigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCodigoProductoKeyTyped
        int contador = 0;
        contador = jTxtCodigoProducto.getText().length();

        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (contador >= 13) {
            evt.consume();
        }
        generaCodigo(this.jTxtCodigoProducto.getText());
    }//GEN-LAST:event_jTxtCodigoProductoKeyTyped

    private void jTxtPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPrecioProductoKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if (evt.getKeyChar() == '.' && jTxtPrecioProducto.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtPrecioProductoKeyTyped

    private void jTxtCantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCantidadProductoKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtCantidadProductoKeyTyped

    private void jCbxEstadoProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCbxEstadoProductoItemStateChanged
        limpiarTabla();
        listarProductos();
    }//GEN-LAST:event_jCbxEstadoProductoItemStateChanged

    private void jBtnActivarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActivarProductoActionPerformed
        activarProducto();
    }//GEN-LAST:event_jBtnActivarProductoActionPerformed

    private void jTxtCodigoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtCodigoProductoActionPerformed

    }//GEN-LAST:event_jTxtCodigoProductoActionPerformed

    private void jTxtCodigoProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCodigoProductoKeyPressed

    }//GEN-LAST:event_jTxtCodigoProductoKeyPressed

    private void jTxtDescripcionProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDescripcionProductoKeyTyped
        int contador = 0;
        contador = jTxtDescripcionProducto.getText().length();

        if (contador >= 300) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtDescripcionProductoKeyTyped

    private void jCbxCategoriaProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCbxCategoriaProductoKeyTyped

    }//GEN-LAST:event_jCbxCategoriaProductoKeyTyped

    private void jTxtBuscarProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtBuscarProductoKeyTyped

    }//GEN-LAST:event_jTxtBuscarProductoKeyTyped

    private void jTxtPrecioProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtPrecioProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPrecioProductoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Productos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Productos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Productos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Productos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActivarProducto;
    private javax.swing.JButton jBtnActualizarProducto;
    private javax.swing.JButton jBtnBuscarCategoria;
    private javax.swing.JButton jBtnCancelarProducto;
    private javax.swing.JButton jBtnEliminarProducto;
    private javax.swing.JButton jBtnGuardarProducto;
    private javax.swing.JButton jBtnNuevoProducto;
    private javax.swing.JComboBox<String> jCbxCategoriaProducto;
    private javax.swing.JComboBox<String> jCbxEstadoProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLblCodigo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTblProducto;
    private javax.swing.JTextField jTxtBuscarProducto;
    private javax.swing.JTextField jTxtCantidadProducto;
    private javax.swing.JTextField jTxtCodigoProducto;
    private javax.swing.JTextArea jTxtDescripcionProducto;
    private javax.swing.JTextField jTxtIdProducto;
    private javax.swing.JTextField jTxtNombreProducto;
    private javax.swing.JTextField jTxtPrecioProducto;
    // End of variables declaration//GEN-END:variables
}
