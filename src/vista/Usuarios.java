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
import modelo.Usuario;
import modelo.DatosUsuario;
import modelo.Validadores;

/**
 *
 * @author Alejandro
 */
public class Usuarios extends javax.swing.JInternalFrame {

    Usuario usu = new Usuario();
    DatosUsuario usuario = new DatosUsuario();
    DefaultTableModel modelo = new DefaultTableModel();
    Validadores val = new Validadores();

    public Usuarios() {
        initComponents();
        usuario.consultarRol(jCbxRol);
        bloquearBotones();
        bloquearCampos();
        listarUsuarios();
    }

    public void insertarUsuario() {
        String rol = (String) jCbxRol.getSelectedItem();

        if (!"".equals(jTxtIdentificacion.getText()) && !"".equals(jTxtNombre.getText()) && !"".equals(jTxtApellido.getText()) && !"".equals(jTxtCorreo.getText()) && !"".equals(jTxtContrasena.getText())) {
            if (val.validadorDeCedula(jTxtIdentificacion.getText()) != false) {
                usu.setId(jTxtIdentificacion.getText());
                usu.setNombre(jTxtNombre.getText());
                usu.setApellido(jTxtApellido.getText());
                if ("".equals(jTxtDireccion.getText())) {
                    usu.setDireccion("SIN DIRECCIÓN");
                } else {
                    usu.setDireccion(jTxtDireccion.getText());
                }
                if ("".equals(jTxtTelefono.getText())) {
                    usu.setTelefono("0000000000");
                } else {
                    usu.setTelefono(jTxtTelefono.getText());
                }
                usu.setCorreo(jTxtCorreo.getText());
                usu.setContrasena(jTxtContrasena.getText());
                usu.setEstado("ACTIVO");
                if (rol.charAt(1) == '.') {
                    usu.setRol(Character.getNumericValue(rol.charAt(0)));
                } else {
                    usu.setRol(Character.getNumericValue((rol.charAt(0)) + (rol.charAt(1))));
                }
                if (usuario.comprobar(jTxtIdentificacion.getText()) == false) {
                    usuario.insertar(usu);

                    limpiarCampos();
                    bloquearBotones();
                    bloquearCampos();
                    limpiarTabla();
                    listarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario registrado con esa identificación");
                }
            }else{
                JOptionPane.showMessageDialog(null, "La cédula es incorrecta", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }
    }

    public void actualizarUsuario() {
        String rol = (String) jCbxRol.getSelectedItem();

        if (!"".equals(jTxtNombre.getText()) && !"".equals(jTxtApellido.getText()) && !"".equals(jTxtCorreo.getText()) && !"".equals(jTxtContrasena.getText())) {
            usu.setNombre(jTxtNombre.getText());
            usu.setApellido(jTxtApellido.getText());
            usu.setCorreo(jTxtCorreo.getText());
            usu.setId(jTxtIdentificacion.getText());
            usu.setContrasena(jTxtContrasena.getText());
            if (rol.charAt(1) == '.') {
                usu.setRol(Character.getNumericValue(rol.charAt(0)));
            } else {
                usu.setRol(Character.getNumericValue((rol.charAt(0)) + (rol.charAt(1))));
            }
            if ("".equals(jTxtDireccion.getText())) {
                usu.setDireccion("SIN DIRECCIÓN");
            } else {
                usu.setDireccion(jTxtDireccion.getText());
            }
            if ("".equals(jTxtTelefono.getText())) {
                usu.setTelefono("0000000000");
            } else {
                usu.setTelefono(jTxtTelefono.getText());
            }

            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea actualizar el cliente?", "Confirmar actualización", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opc == 0) {
                usuario.actualizar(usu);

                limpiarCampos();
                limpiarTabla();
                listarUsuarios();
                bloquearBotones();
                bloquearCampos();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }
    }

    public void eliminarUsuario() {
        int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opc == 0) {

            usu.setId(jTxtIdentificacion.getText());
            usu.setEstado("INACTIVO");
            usuario.eliminar(usu);

            limpiarCampos();
            limpiarTabla();
            listarUsuarios();
            bloquearBotones();
            bloquearCampos();

        }
    }

    public void listarUsuarios() {
        List<Usuario> ListarUsu = usuario.listar(String.valueOf(jCbxEstadoCliente.getSelectedItem()));
        modelo = (DefaultTableModel) jTblCliente.getModel();
        Object[] ob = new Object[9];

        for (int i = 0; i < ListarUsu.size(); i++) {
            ob[0] = ListarUsu.get(i).getId();
            ob[1] = ListarUsu.get(i).getNombre();
            ob[2] = ListarUsu.get(i).getApellido();
            ob[3] = ListarUsu.get(i).getDireccion();
            ob[4] = ListarUsu.get(i).getTelefono();
            ob[5] = ListarUsu.get(i).getCorreo();
            ob[6] = ListarUsu.get(i).getEstado();
            int idRol = ListarUsu.get(i).getRol();
            ob[7] = idRol + ".- " + usuario.consultarNombreRol(idRol);
            modelo.addRow(ob);
        }
        jTblCliente.setModel(modelo);
    }

    public void activarUsuario() {
        String cedula = jTxtIdentificacion.getText();
        String estado = usuario.estadoUsuario(cedula);

        if ("INACTIVO".equals(estado)) {
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea activar el cliente?", "Confirmar activación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opc == 0) {
                usuario.activarUsuario(cedula);
                bloquearBotones();
                bloquearCampos();
                limpiarCampos();
                limpiarTabla();
                listarUsuarios();
            }
        }
    }

    public void buscar() {
        limpiarTabla();
        List<Usuario> ListarUsu = usuario.buscar(Integer.valueOf(jTxtBuscarUsuario.getText()));
        modelo = (DefaultTableModel) jTblCliente.getModel();
        Object[] ob = new Object[8];

        for (int i = 0; i < ListarUsu.size(); i++) {
            ob[0] = ListarUsu.get(i).getId();
            ob[1] = ListarUsu.get(i).getNombre();
            ob[2] = ListarUsu.get(i).getApellido();
            ob[3] = ListarUsu.get(i).getDireccion();
            ob[4] = ListarUsu.get(i).getTelefono();
            ob[5] = ListarUsu.get(i).getCorreo();
            ob[6] = ListarUsu.get(i).getEstado();
            ob[7] = ListarUsu.get(i).getRol();
            modelo.addRow(ob);
        }
        jTblCliente.setModel(modelo);
        limpiarBusqueda();

    }

    private void limpiarBusqueda() {
        jTxtBuscarUsuario.setText("");
    }

    public void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void bloquearBotones() {
        jBtnNuevo.setEnabled(true);
        jBtnGuardar.setEnabled(false);
        jBtnActualizar.setEnabled(false);
        jBtnEliminar.setEnabled(false);
        jBtnCancelar.setEnabled(false);
        jBtnActivar.setEnabled(false);
    }

    public void bloquearBotonesNuevo() {
        jBtnNuevo.setEnabled(false);
        jBtnGuardar.setEnabled(true);
        jBtnActualizar.setEnabled(false);
        jBtnEliminar.setEnabled(false);
        jBtnCancelar.setEnabled(true);
    }

    public void bloquearBotonesInactivo() {
        jBtnNuevo.setEnabled(false);
        jBtnGuardar.setEnabled(false);
        jBtnActualizar.setEnabled(false);
        jBtnEliminar.setEnabled(false);
        jBtnCancelar.setEnabled(true);
        jBtnActivar.setEnabled(true);
    }

    public void bloquearBotonesTabla() {
        jBtnNuevo.setEnabled(false);
        jBtnGuardar.setEnabled(false);
        jBtnActualizar.setEnabled(true);
        jBtnEliminar.setEnabled(true);
        jBtnCancelar.setEnabled(true);
    }

    public void bloquearCampos() {
        jTxtIdentificacion.setEnabled(false);
        jCbxRol.setEnabled(false);
        jTxtNombre.setEnabled(false);
        jTxtApellido.setEnabled(false);
        jTxtDireccion.setEnabled(false);
        jTxtTelefono.setEnabled(false);
        jTxtCorreo.setEnabled(false);
        jTxtContrasena.setEnabled(false);
    }

    public void bloquearCamposActualizar() {
        jTxtIdentificacion.setEnabled(false);
        jCbxRol.setEnabled(true);
        jTxtNombre.setEnabled(true);
        jTxtApellido.setEnabled(true);
        jTxtDireccion.setEnabled(true);
        jTxtTelefono.setEnabled(true);
        jTxtCorreo.setEnabled(true);
        jTxtContrasena.setEnabled(true);
    }

    public void desbloquearCampos() {
        jTxtIdentificacion.setEnabled(true);
        jCbxRol.setEnabled(true);
        jTxtNombre.setEnabled(true);
        jTxtApellido.setEnabled(true);
        jTxtDireccion.setEnabled(true);
        jTxtTelefono.setEnabled(true);
        jTxtCorreo.setEnabled(true);
        jTxtContrasena.setEnabled(true);
    }

    public void limpiarCampos() {
        jTxtIdentificacion.setText("");
        jCbxRol.setSelectedIndex(0);
        jTxtNombre.setText("");
        jTxtApellido.setText("");
        jTxtDireccion.setText("");
        jTxtTelefono.setText("");
        jTxtCorreo.setText("");
        jTxtContrasena.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBtnNuevo = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtIdentificacion = new javax.swing.JTextField();
        jTxtNombre = new javax.swing.JTextField();
        jTxtApellido = new javax.swing.JTextField();
        jTxtDireccion = new javax.swing.JTextField();
        jTxtTelefono = new javax.swing.JTextField();
        jTxtCorreo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jCbxRol = new javax.swing.JComboBox<>();
        jTxtContrasena = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscarUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblCliente = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jCbxEstadoCliente = new javax.swing.JComboBox<>();
        jBtnActivar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("USUARIOS");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO DE DATOS"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("NOMBRE:");

        jBtnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jBtnNuevo.setText("NUEVO");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });

        jBtnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jBtnGuardar.setText("GUARDAR");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("IDENTIFICACIÓN:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("APELLIDO:");

        jTxtIdentificacion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtIdentificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtIdentificacionKeyTyped(evt);
            }
        });

        jTxtNombre.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtNombreKeyTyped(evt);
            }
        });

        jTxtApellido.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtApellidoKeyTyped(evt);
            }
        });

        jTxtDireccion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtDireccionKeyTyped(evt);
            }
        });

        jTxtTelefono.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtTelefonoKeyTyped(evt);
            }
        });

        jTxtCorreo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCorreoKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("DIRECCIÓN:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("CORREO:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("TELÉFONO:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("CONTRASEÑA:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("ROL:");

        jCbxRol.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jTxtContrasena.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtContrasenaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jBtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtIdentificacion, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(jTxtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(jTxtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(jTxtContrasena))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(jTxtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(jCbxRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(67, 67, 67))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTxtIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jCbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DETALLES"));

        jTxtBuscarUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("BUSCAR:");

        jTblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CEDULA", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "CORREO", "ESTADO", "ROL"
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

        jBtnActivar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActivar.setText("ACTIVAR");
        jBtnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActivarActionPerformed(evt);
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
                        .addComponent(jTxtBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCbxEstadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(jTxtBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCbxEstadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jBtnActivar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        actualizarUsuario();
    }//GEN-LAST:event_jBtnActualizarActionPerformed

    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        bloquearBotonesNuevo();
        desbloquearCampos();
    }//GEN-LAST:event_jBtnNuevoActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        insertarUsuario();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        eliminarUsuario();
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        bloquearBotones();
        limpiarCampos();
        bloquearCampos();
        limpiarTabla();
        listarUsuarios();
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jTblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblClienteMouseClicked
        int fila = jTblCliente.rowAtPoint(evt.getPoint());
        jTxtIdentificacion.setText(jTblCliente.getValueAt(fila, 0).toString());
        jTxtNombre.setText(jTblCliente.getValueAt(fila, 1).toString());
        jTxtApellido.setText(jTblCliente.getValueAt(fila, 2).toString());
        jTxtDireccion.setText(jTblCliente.getValueAt(fila, 3).toString());
        jTxtTelefono.setText(jTblCliente.getValueAt(fila, 4).toString());
        jTxtCorreo.setText(jTblCliente.getValueAt(fila, 5).toString());
        jTxtContrasena.setText((String) usuario.consultarContrasena(jTblCliente.getValueAt(fila, 0).toString()));
        Object estado = jTblCliente.getValueAt(fila, 6).toString();
        jCbxRol.setSelectedItem(jTblCliente.getValueAt(fila, 7).toString());

        if (estado.equals("INACTIVO")) {
            bloquearBotonesInactivo();
            bloquearCampos();
        } else {
            bloquearBotonesTabla();
            desbloquearCampos();
            bloquearCamposActualizar();
        }
    }//GEN-LAST:event_jTblClienteMouseClicked

    private void jTxtIdentificacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtIdentificacionKeyTyped
        int contador = 0;
        contador = jTxtIdentificacion.getText().length();

        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (contador >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtIdentificacionKeyTyped

    private void jTxtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTelefonoKeyTyped
        int contador = 0;
        contador = jTxtTelefono.getText().length();

        if (contador >= 10) {
            evt.consume();
        }
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtTelefonoKeyTyped

    private void jTxtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombreKeyTyped
        int contador = 0;
        contador = jTxtNombre.getText().length();

        if (contador >= 45) {
            evt.consume();
        }
        if (Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtNombreKeyTyped

    private void jTxtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtApellidoKeyTyped
        int contador = 0;
        contador = jTxtApellido.getText().length();

        if (contador >= 45) {
            evt.consume();
        }
        if (Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtApellidoKeyTyped

    private void jTxtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDireccionKeyTyped
        int contador = 0;
        contador = jTxtDireccion.getText().length();

        if (contador >= 300) {
            evt.consume();
        }
        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
    }//GEN-LAST:event_jTxtDireccionKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        buscar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCbxEstadoClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCbxEstadoClienteItemStateChanged
        limpiarTabla();
        listarUsuarios();
    }//GEN-LAST:event_jCbxEstadoClienteItemStateChanged

    private void jBtnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActivarActionPerformed
        activarUsuario();
    }//GEN-LAST:event_jBtnActivarActionPerformed

    private void jTxtCorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCorreoKeyTyped
        int contador = 0;
        contador = jTxtCorreo.getText().length();

        if (contador >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtCorreoKeyTyped

    private void jTxtContrasenaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtContrasenaKeyTyped
        int contador = 0;
        contador = jTxtContrasena.getText().length();

        if (contador >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtContrasenaKeyTyped

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
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActivar;
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jCbxEstadoCliente;
    private javax.swing.JComboBox<String> jCbxRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTblCliente;
    private javax.swing.JTextField jTxtApellido;
    private javax.swing.JTextField jTxtBuscarUsuario;
    private javax.swing.JPasswordField jTxtContrasena;
    private javax.swing.JTextField jTxtCorreo;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtIdentificacion;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtTelefono;
    // End of variables declaration//GEN-END:variables
}
