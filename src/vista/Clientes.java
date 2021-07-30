/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.DatosCliente;
import modelo.Validadores;

/**
 *
 * @author Alejandro
 */
public class Clientes extends javax.swing.JInternalFrame {

    Cliente cli = new Cliente();
    DatosCliente cliente = new DatosCliente();
    DefaultTableModel modelo = new DefaultTableModel();
    Validadores val = new Validadores();

    public Clientes() {
        initComponents();
        bloquearBotones();
        bloquearCampos();
        listarClientes();
    }

    public void insertarCliente() {
        if (!"".equals(jTxtIdentificacionCliente.getText()) && !"".equals(jTxtNombreCliente.getText()) && !"".equals(jTxtApellidoCliente.getText()) && !"".equals(jTxtCorreoCliente.getText())) {
            cli.setTipoId((String) jCbxTipoIdentificacion.getSelectedItem());
            cli.setId(jTxtIdentificacionCliente.getText());
            cli.setNombre(jTxtNombreCliente.getText());
            cli.setApellido(jTxtApellidoCliente.getText());
            cli.setCorreo(jTxtCorreoCliente.getText());
            cli.setEstado("ACTIVO");

            if ("".equals(jTxtDireccionCliente.getText())) {
                cli.setDireccion("SIN DIRECCIÓN");
            } else {
                cli.setDireccion(jTxtDireccionCliente.getText());
            }
            if ("".equals(jTxtTelefonoCliente.getText())) {
                cli.setTelefono("0000000000");
            } else {
                cli.setTelefono(jTxtTelefonoCliente.getText());
            }
            if (cliente.comprobar(jTxtIdentificacionCliente.getText()) == false) {

                if (jCbxTipoIdentificacion.getSelectedItem().equals("RUC")) {
                    if (jTxtIdentificacionCliente.getText().length() == 13) {
                        cliente.insertar(cli);

                        limpiarCampos();
                        bloquearBotones();
                        bloquearCampos();
                        limpiarTabla();
                        listarClientes();
                    }else{
                        JOptionPane.showMessageDialog(null, "El RUC debe contener 13 digitos");
                    }
                }else{
                    if (jTxtIdentificacionCliente.getText().length() == 10) {
                        cliente.insertar(cli);

                        limpiarCampos();
                        bloquearBotones();
                        bloquearCampos();
                        limpiarTabla();
                        listarClientes();
                    }else{
                        JOptionPane.showMessageDialog(null, "La Cédula debe contener 10 digitos");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un cliente registrado con esa identificación");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }
    }

    public void actualizarCliente() {

        if (!"".equals(jTxtNombreCliente.getText()) && !"".equals(jTxtApellidoCliente.getText()) && !"".equals(jTxtCorreoCliente.getText())) {
            cli.setNombre(jTxtNombreCliente.getText());
            cli.setApellido(jTxtApellidoCliente.getText());
            cli.setCorreo(jTxtCorreoCliente.getText());
            cli.setId(jTxtIdentificacionCliente.getText());
            if ("".equals(jTxtDireccionCliente.getText())) {
                cli.setDireccion("SIN DIRECCIÓN");
            } else {
                cli.setDireccion(jTxtDireccionCliente.getText());
            }
            if ("".equals(jTxtTelefonoCliente.getText())) {
                cli.setTelefono("0000000000");
            } else {
                cli.setTelefono(jTxtTelefonoCliente.getText());
            }

            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea actualizar el cliente?", "Confirmar actualización", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opc == 0) {
                cliente.actualizar(cli);

                limpiarCampos();
                limpiarTabla();
                listarClientes();
                bloquearBotones();
                bloquearCampos();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }

    }

    public void eliminarCliente() {

        int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opc == 0) {

            cli.setId(jTxtIdentificacionCliente.getText());
            cli.setEstado("INACTIVO");
            cliente.eliminar(cli);

            limpiarCampos();
            limpiarTabla();
            listarClientes();
            bloquearBotones();
            bloquearCampos();

        }
    }

    public void buscar() {
        limpiarTabla();
        List<Cliente> ListarCli = cliente.buscar(Integer.valueOf(jTxtBuscarCliente.getText()));
        modelo = (DefaultTableModel) jTblCliente.getModel();
        Object[] ob = new Object[8];

        for (int i = 0; i < ListarCli.size(); i++) {
            ob[0] = ListarCli.get(i).getId();
            ob[1] = ListarCli.get(i).getTipoId();
            ob[2] = ListarCli.get(i).getNombre();
            ob[3] = ListarCli.get(i).getApellido();
            ob[4] = ListarCli.get(i).getDireccion();
            ob[5] = ListarCli.get(i).getTelefono();
            ob[6] = ListarCli.get(i).getCorreo();
            ob[7] = ListarCli.get(i).getEstado();
            modelo.addRow(ob);
        }
        jTblCliente.setModel(modelo);
        limpiarBusqueda();

    }

    public void listarClientes() {
        List<Cliente> ListarCli = cliente.listar(String.valueOf(jCbxEstadoCliente.getSelectedItem()));
        modelo = (DefaultTableModel) jTblCliente.getModel();
        Object[] ob = new Object[8];

        for (int i = 0; i < ListarCli.size(); i++) {
            ob[0] = ListarCli.get(i).getId();
            ob[1] = ListarCli.get(i).getTipoId();
            ob[2] = ListarCli.get(i).getNombre();
            ob[3] = ListarCli.get(i).getApellido();
            ob[4] = ListarCli.get(i).getDireccion();
            ob[5] = ListarCli.get(i).getTelefono();
            ob[6] = ListarCli.get(i).getCorreo();
            ob[7] = ListarCli.get(i).getEstado();
            modelo.addRow(ob);
        }
        jTblCliente.setModel(modelo);
    }

    public void activarCliente() {
        String cedula = jTxtIdentificacionCliente.getText();
        String estado = cliente.estadoCliente(cedula);

        if ("INACTIVO".equals(estado)) {
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea activar el cliente?", "Confirmar activación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opc == 0) {
                cliente.activarCliente(cedula);
                bloquearBotones();
                bloquearCampos();
                limpiarCampos();
                limpiarTabla();
                listarClientes();
            }
        }
    }

    private void limpiarBusqueda() {
        jTxtBuscarCliente.setText("");
    }

    public void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void bloquearBotones() {
        jBtnNuevoCliente.setEnabled(true);
        jBtnGuardarCliente.setEnabled(false);
        jBtnActualizarCliente.setEnabled(false);
        jBtnEliminarCliente.setEnabled(false);
        jBtnCancelarCliente.setEnabled(false);
        jBtnActivarCliente.setEnabled(false);
    }

    public void bloquearBotonesNuevo() {
        jBtnNuevoCliente.setEnabled(false);
        jBtnGuardarCliente.setEnabled(true);
        jBtnActualizarCliente.setEnabled(false);
        jBtnEliminarCliente.setEnabled(false);
        jBtnCancelarCliente.setEnabled(true);
    }

    public void bloquearBotonesTabla() {
        jBtnNuevoCliente.setEnabled(false);
        jBtnGuardarCliente.setEnabled(false);
        jBtnActualizarCliente.setEnabled(true);
        jBtnEliminarCliente.setEnabled(true);
        jBtnCancelarCliente.setEnabled(true);
    }

    public void bloquearBotonesInactivo() {
        jBtnNuevoCliente.setEnabled(false);
        jBtnGuardarCliente.setEnabled(false);
        jBtnActualizarCliente.setEnabled(false);
        jBtnEliminarCliente.setEnabled(false);
        jBtnCancelarCliente.setEnabled(true);
        jBtnActivarCliente.setEnabled(true);
    }

    public void bloquearCampos() {
        jTxtIdentificacionCliente.setEnabled(false);
        jCbxTipoIdentificacion.setEnabled(false);
        jTxtNombreCliente.setEnabled(false);
        jTxtApellidoCliente.setEnabled(false);
        jTxtDireccionCliente.setEnabled(false);
        jTxtTelefonoCliente.setEnabled(false);
        jTxtCorreoCliente.setEnabled(false);
    }

    public void bloquearCamposActualizar() {
        jTxtIdentificacionCliente.setEnabled(false);
        jCbxTipoIdentificacion.setEnabled(false);
        jTxtNombreCliente.setEnabled(true);
        jTxtApellidoCliente.setEnabled(true);
        jTxtDireccionCliente.setEnabled(true);
        jTxtTelefonoCliente.setEnabled(true);
        jTxtCorreoCliente.setEnabled(true);
    }

    public void desbloquearCampos() {
        jTxtIdentificacionCliente.setEnabled(true);
        jCbxTipoIdentificacion.setEnabled(true);
        jTxtNombreCliente.setEnabled(true);
        jTxtApellidoCliente.setEnabled(true);
        jTxtDireccionCliente.setEnabled(true);
        jTxtTelefonoCliente.setEnabled(true);
        jTxtCorreoCliente.setEnabled(true);
    }

    public void limpiarCampos() {
        jTxtIdentificacionCliente.setText("");
        jCbxTipoIdentificacion.setSelectedIndex(0);
        jTxtNombreCliente.setText("");
        jTxtApellidoCliente.setText("");
        jTxtDireccionCliente.setText("");
        jTxtTelefonoCliente.setText("");
        jTxtCorreoCliente.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnNuevoCliente = new javax.swing.JButton();
        jBtnGuardarCliente = new javax.swing.JButton();
        jBtnActualizarCliente = new javax.swing.JButton();
        jBtnEliminarCliente = new javax.swing.JButton();
        jBtnCancelarCliente = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtIdentificacionCliente = new javax.swing.JTextField();
        jCbxTipoIdentificacion = new javax.swing.JComboBox<>();
        jTxtNombreCliente = new javax.swing.JTextField();
        jTxtApellidoCliente = new javax.swing.JTextField();
        jTxtDireccionCliente = new javax.swing.JTextField();
        jTxtTelefonoCliente = new javax.swing.JTextField();
        jTxtCorreoCliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscarCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblCliente = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jCbxEstadoCliente = new javax.swing.JComboBox<>();
        jBtnActivarCliente = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CLIENTES");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO DE DATOS"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("NOMBRE:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("IDENTIFICACIÓN:");

        jBtnNuevoCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jBtnNuevoCliente.setText("NUEVO");
        jBtnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoClienteActionPerformed(evt);
            }
        });

        jBtnGuardarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jBtnGuardarCliente.setText("GUARDAR");
        jBtnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarClienteActionPerformed(evt);
            }
        });

        jBtnActualizarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jBtnActualizarCliente.setText("ACTUALIZAR");
        jBtnActualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarClienteActionPerformed(evt);
            }
        });

        jBtnEliminarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        jBtnEliminarCliente.setText("ELIMINAR");
        jBtnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarClienteActionPerformed(evt);
            }
        });

        jBtnCancelarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnCancelarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jBtnCancelarCliente.setText("CANCELAR");
        jBtnCancelarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarClienteActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("TIPO IDENTIFICACIÓN:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("APELLIDO:");

        jTxtIdentificacionCliente.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtIdentificacionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtIdentificacionClienteKeyTyped(evt);
            }
        });

        jCbxTipoIdentificacion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jCbxTipoIdentificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RUC", "CÉDULA" }));
        jCbxTipoIdentificacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCbxTipoIdentificacionItemStateChanged(evt);
            }
        });

        jTxtNombreCliente.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtNombreClienteKeyTyped(evt);
            }
        });

        jTxtApellidoCliente.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtApellidoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtApellidoClienteKeyTyped(evt);
            }
        });

        jTxtDireccionCliente.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtDireccionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtDireccionClienteKeyTyped(evt);
            }
        });

        jTxtTelefonoCliente.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtTelefonoClienteKeyTyped(evt);
            }
        });

        jTxtCorreoCliente.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtCorreoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCorreoClienteKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("DIRECCIÓN:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("CORREO:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("TELÉFONO:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCbxTipoIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtCorreoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtIdentificacionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jBtnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnActualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnCancelarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCbxTipoIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtIdentificacionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtCorreoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnActualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DETALLES"));

        jTxtBuscarCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("BUSCAR:");

        jTblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TIPO", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "CORREO", "ESTADO"
            }
        ));
        jTblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTblCliente);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCbxEstadoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        jCbxEstadoCliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCbxEstadoClienteItemStateChanged(evt);
            }
        });

        jBtnActivarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActivarCliente.setText("ACTIVAR");
        jBtnActivarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActivarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCbxEstadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnActivarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTxtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCbxEstadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jBtnActivarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnActualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarClienteActionPerformed
        actualizarCliente();
    }//GEN-LAST:event_jBtnActualizarClienteActionPerformed

    private void jBtnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoClienteActionPerformed
        bloquearBotonesNuevo();
        desbloquearCampos();
    }//GEN-LAST:event_jBtnNuevoClienteActionPerformed

    private void jBtnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarClienteActionPerformed
        insertarCliente();
    }//GEN-LAST:event_jBtnGuardarClienteActionPerformed

    private void jBtnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarClienteActionPerformed
        eliminarCliente();
    }//GEN-LAST:event_jBtnEliminarClienteActionPerformed

    private void jBtnCancelarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarClienteActionPerformed
        bloquearBotones();
        limpiarCampos();
        bloquearCampos();
        limpiarTabla();
        listarClientes();
    }//GEN-LAST:event_jBtnCancelarClienteActionPerformed

    private void jTblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblClienteMouseClicked
        int fila = jTblCliente.rowAtPoint(evt.getPoint());
        jTxtIdentificacionCliente.setText(jTblCliente.getValueAt(fila, 0).toString());
        jCbxTipoIdentificacion.setSelectedItem((jTblCliente.getValueAt(fila, 1).toString()));
        jTxtNombreCliente.setText(jTblCliente.getValueAt(fila, 2).toString());
        jTxtApellidoCliente.setText(jTblCliente.getValueAt(fila, 3).toString());
        jTxtDireccionCliente.setText(jTblCliente.getValueAt(fila, 4).toString());
        jTxtTelefonoCliente.setText(jTblCliente.getValueAt(fila, 5).toString());
        jTxtCorreoCliente.setText(jTblCliente.getValueAt(fila, 6).toString());
        String estado = jTblCliente.getValueAt(fila, 7).toString();

        if (estado.equals("INACTIVO")) {
            bloquearBotonesInactivo();
            bloquearCampos();
        } else {
            bloquearBotonesTabla();
            desbloquearCampos();
            bloquearCamposActualizar();
        }
    }//GEN-LAST:event_jTblClienteMouseClicked

    private void jTxtIdentificacionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtIdentificacionClienteKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (jCbxTipoIdentificacion.getSelectedItem().equals("RUC")) {
            int contador = 0;
            contador = jTxtIdentificacionCliente.getText().length();

            if (contador >= 13) {
                evt.consume();
            }
        } else {
            int contador = 0;
            contador = jTxtIdentificacionCliente.getText().length();

            if (contador >= 10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTxtIdentificacionClienteKeyTyped

    private void jTxtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTelefonoClienteKeyTyped
        int contador = 0;
        contador = jTxtTelefonoCliente.getText().length();

        if (contador >= 10) {
            evt.consume();
        }
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtTelefonoClienteKeyTyped

    private void jTxtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombreClienteKeyTyped
        int contador = 0;
        contador = jTxtNombreCliente.getText().length();

        if (contador >= 45) {
            evt.consume();
        }
        if (Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtNombreClienteKeyTyped

    private void jTxtApellidoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtApellidoClienteKeyTyped
        int contador = 0;
        contador = jTxtApellidoCliente.getText().length();

        if (contador >= 45) {
            evt.consume();
        }
        if (Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtApellidoClienteKeyTyped

    private void jTxtDireccionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDireccionClienteKeyTyped
        int contador = 0;
        contador = jTxtDireccionCliente.getText().length();

        if (contador >= 300) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtDireccionClienteKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        buscar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCbxEstadoClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCbxEstadoClienteItemStateChanged
        limpiarTabla();
        listarClientes();
    }//GEN-LAST:event_jCbxEstadoClienteItemStateChanged

    private void jBtnActivarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActivarClienteActionPerformed
        activarCliente();
    }//GEN-LAST:event_jBtnActivarClienteActionPerformed

    private void jCbxTipoIdentificacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCbxTipoIdentificacionItemStateChanged

    }//GEN-LAST:event_jCbxTipoIdentificacionItemStateChanged

    private void jTxtCorreoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCorreoClienteKeyTyped
        int contador = 0;
        contador = jTxtCorreoCliente.getText().length();

        if (contador >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtCorreoClienteKeyTyped

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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActivarCliente;
    private javax.swing.JButton jBtnActualizarCliente;
    private javax.swing.JButton jBtnCancelarCliente;
    private javax.swing.JButton jBtnEliminarCliente;
    private javax.swing.JButton jBtnGuardarCliente;
    private javax.swing.JButton jBtnNuevoCliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jCbxEstadoCliente;
    private javax.swing.JComboBox<String> jCbxTipoIdentificacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTblCliente;
    private javax.swing.JTextField jTxtApellidoCliente;
    private javax.swing.JTextField jTxtBuscarCliente;
    private javax.swing.JTextField jTxtCorreoCliente;
    private javax.swing.JTextField jTxtDireccionCliente;
    private javax.swing.JTextField jTxtIdentificacionCliente;
    private javax.swing.JTextField jTxtNombreCliente;
    private javax.swing.JTextField jTxtTelefonoCliente;
    // End of variables declaration//GEN-END:variables
}
