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
import modelo.Categoria;
import modelo.DatosCategoria;

/**
 *
 * @author Alejandro
 */
public class Categorias extends javax.swing.JInternalFrame {

    Categoria cat = new Categoria();
    DatosCategoria categoria = new DatosCategoria();
    DefaultTableModel modelo = new DefaultTableModel();

    public Categorias() {
        initComponents();
        listarCategoria();
        bloquearBotones();
        bloquearCampos();
    }

    public void insertarCategoria() {
        if (!"".equals(jTxtNombreCategoria.getText())) {
            cat.setNombre(jTxtNombreCategoria.getText());
            if (jTxtDescripcionCategoria.getText().equals("")) {
                cat.setDescripcion("SIN DESCRIPCIÓN");
            } else {
                cat.setDescripcion(jTxtDescripcionCategoria.getText());
            }
            cat.setEstado("ACTIVO");
            if (categoria.comprobar(jTxtNombreCategoria.getText()) == false) {
                categoria.insertar(cat);
                limpiarTabla();
                listarCategoria();
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe una categoría registrada con ese nombre");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }
    }

    public void actualizarCategoria() {
        String auxNombre = jTxtNombreCategoria.getText();

        if (!"".equals(jTxtNombreCategoria.getText())) {
            cat.setId(Integer.valueOf(jTxtIdCategoria.getText()));
            cat.setNombre(jTxtNombreCategoria.getText());
            cat.setDescripcion(jTxtDescripcionCategoria.getText());
            categoria.actualizar(cat);

            limpiarCampos();
            limpiarTabla();
            listarCategoria();
            bloquearBotones();
            bloquearCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Uno o varios campos están vacios");
        }

    }

    public void eliminarCategoria() {

        int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la categoría", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opc == 0) {

            if (categoria.categoriaVerificador(jTxtIdCategoria.getText()) == 0) {
                cat.setId(Integer.valueOf(jTxtIdCategoria.getText()));
                cat.setEstado("INACTIVO");
                categoria.eliminar(cat);

                limpiarCampos();
                limpiarTabla();
                listarCategoria();
                bloquearBotones();
                bloquearCampos();
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar una categoria con productos activos", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void buscar() {
        limpiarTabla();
        List<Categoria> ListarCat = categoria.buscar(Integer.valueOf(jTxtBuscarCategoria.getText()));
        modelo = (DefaultTableModel) jTblCategoria.getModel();
        Object[] ob = new Object[4];

        for (int i = 0; i < ListarCat.size(); i++) {
            ob[0] = ListarCat.get(i).getId();
            ob[1] = ListarCat.get(i).getNombre();
            ob[2] = ListarCat.get(i).getDescripcion();
            ob[3] = ListarCat.get(i).getEstado();
            modelo.addRow(ob);
        }
        jTblCategoria.setModel(modelo);
        limpiarBusqueda();

    }

    public void listarCategoria() {
        List<Categoria> ListarCat = categoria.listar(String.valueOf(jCbxEstadoCategoria.getSelectedItem()));
        modelo = (DefaultTableModel) jTblCategoria.getModel();
        Object[] ob = new Object[4];

        for (int i = 0; i < ListarCat.size(); i++) {
            ob[0] = ListarCat.get(i).getId();
            ob[1] = ListarCat.get(i).getNombre();
            ob[2] = ListarCat.get(i).getDescripcion();
            ob[3] = ListarCat.get(i).getEstado();
            modelo.addRow(ob);
        }
        jTblCategoria.setModel(modelo);

    }

    public void activarCategoria() {
        String id = jTxtIdCategoria.getText();
        String estado = categoria.estadoCategoria(id);

        if ("INACTIVO".equals(estado)) {
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea activar la categoría");
            if (opc == 0) {
                categoria.activarCategoria(id);
                bloquearBotones();
                bloquearCampos();
                limpiarCampos();
                limpiarTabla();
                listarCategoria();
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
        jTxtIdCategoria.setText("");
        jTxtNombreCategoria.setText("");
        jTxtDescripcionCategoria.setText("");
    }

    private void limpiarBusqueda() {
        jTxtBuscarCategoria.setText("");
    }

    public void bloquearBotones() {
        jBtnNuevoCategoria.setEnabled(true);
        jBtnGuardarCategoria.setEnabled(false);
        jBtnActualizarCategoria.setEnabled(false);
        jBtnEliminarCategoria.setEnabled(false);
        jBtnCancelarCategoria.setEnabled(false);
        jBtnActivarCategoria.setEnabled(false);
    }

    public void bloquearBotonesNuevo() {
        jBtnNuevoCategoria.setEnabled(false);
        jBtnGuardarCategoria.setEnabled(true);
        jBtnActualizarCategoria.setEnabled(false);
        jBtnEliminarCategoria.setEnabled(false);
        jBtnCancelarCategoria.setEnabled(true);
    }

    public void bloquearBotonesTabla() {
        jBtnNuevoCategoria.setEnabled(false);
        jBtnGuardarCategoria.setEnabled(false);
        jBtnActualizarCategoria.setEnabled(true);
        jBtnEliminarCategoria.setEnabled(true);
        jBtnCancelarCategoria.setEnabled(true);
    }

    public void bloquearBotonesInactivo() {
        jBtnNuevoCategoria.setEnabled(false);
        jBtnGuardarCategoria.setEnabled(false);
        jBtnActualizarCategoria.setEnabled(false);
        jBtnEliminarCategoria.setEnabled(false);
        jBtnCancelarCategoria.setEnabled(true);
        jBtnActivarCategoria.setEnabled(true);
    }

    public void bloquearCampos() {
        jTxtNombreCategoria.setEnabled(false);
        jTxtDescripcionCategoria.setEnabled(false);
        jTxtIdCategoria.setEnabled(false);
    }

    public void bloquearCamposActualizar() {
        jTxtNombreCategoria.setEnabled(false);
        jTxtDescripcionCategoria.setEnabled(true);
        jTxtIdCategoria.setEnabled(false);
    }

    public void desbloquearCampos() {
        jTxtNombreCategoria.setEnabled(true);
        jTxtDescripcionCategoria.setEnabled(true);
        jTxtIdCategoria.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtNombreCategoria = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtDescripcionCategoria = new javax.swing.JTextArea();
        jBtnNuevoCategoria = new javax.swing.JButton();
        jBtnGuardarCategoria = new javax.swing.JButton();
        jBtnActualizarCategoria = new javax.swing.JButton();
        jBtnEliminarCategoria = new javax.swing.JButton();
        jBtnCancelarCategoria = new javax.swing.JButton();
        jTxtIdCategoria = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jTxtBuscarCategoria = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblCategoria = new javax.swing.JTable();
        jBtnBuscarCategoria = new javax.swing.JButton();
        jCbxEstadoCategoria = new javax.swing.JComboBox<>();
        jBtnActivarCategoria = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CATEGORIAS");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO DE DATOS"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("DESCRIPCIÓN:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("NOMBRE:");

        jTxtNombreCategoria.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtNombreCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtNombreCategoriaKeyTyped(evt);
            }
        });

        jTxtDescripcionCategoria.setColumns(20);
        jTxtDescripcionCategoria.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTxtDescripcionCategoria.setRows(5);
        jTxtDescripcionCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtDescripcionCategoriaKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTxtDescripcionCategoria);

        jBtnNuevoCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnNuevoCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jBtnNuevoCategoria.setText("NUEVO");
        jBtnNuevoCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoCategoriaActionPerformed(evt);
            }
        });

        jBtnGuardarCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnGuardarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jBtnGuardarCategoria.setText("GUARDAR");
        jBtnGuardarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarCategoriaActionPerformed(evt);
            }
        });

        jBtnActualizarCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActualizarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jBtnActualizarCategoria.setText("ACTUALIZAR");
        jBtnActualizarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarCategoriaActionPerformed(evt);
            }
        });

        jBtnEliminarCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnEliminarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        jBtnEliminarCategoria.setText("ELIMINAR");
        jBtnEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarCategoriaActionPerformed(evt);
            }
        });

        jBtnCancelarCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnCancelarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jBtnCancelarCategoria.setText("CANCELAR");
        jBtnCancelarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jBtnNuevoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnGuardarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnActualizarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnEliminarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnCancelarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtNombreCategoria)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTxtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jTxtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTxtNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGuardarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnActualizarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DETALLES"));

        jTxtBuscarCategoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("BUSCAR:");

        jTblCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "DESCRIPCIÓN", "ESTADO"
            }
        ));
        jTblCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblCategoriaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTblCategoria);

        jBtnBuscarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        jBtnBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarCategoriaActionPerformed(evt);
            }
        });

        jCbxEstadoCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        jCbxEstadoCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCbxEstadoCategoriaItemStateChanged(evt);
            }
        });

        jBtnActivarCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnActivarCategoria.setText("ACTIVAR");
        jBtnActivarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActivarCategoriaActionPerformed(evt);
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
                        .addComponent(jTxtBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCbxEstadoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnActivarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTxtBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jBtnBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCbxEstadoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnActivarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnActualizarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarCategoriaActionPerformed
        actualizarCategoria();
    }//GEN-LAST:event_jBtnActualizarCategoriaActionPerformed

    private void jBtnNuevoCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoCategoriaActionPerformed
        bloquearBotonesNuevo();
        desbloquearCampos();
    }//GEN-LAST:event_jBtnNuevoCategoriaActionPerformed

    private void jBtnGuardarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarCategoriaActionPerformed
        insertarCategoria();
        limpiarCampos();
        bloquearBotones();
        bloquearCampos();
        limpiarTabla();
        listarCategoria();
    }//GEN-LAST:event_jBtnGuardarCategoriaActionPerformed

    private void jBtnEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarCategoriaActionPerformed
        eliminarCategoria();
    }//GEN-LAST:event_jBtnEliminarCategoriaActionPerformed

    private void jBtnCancelarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarCategoriaActionPerformed
        bloquearBotones();
        limpiarCampos();
        bloquearCampos();
        limpiarTabla();
        listarCategoria();
    }//GEN-LAST:event_jBtnCancelarCategoriaActionPerformed

    private void jTblCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblCategoriaMouseClicked
        int fila = jTblCategoria.rowAtPoint(evt.getPoint());
        jTxtIdCategoria.setText(jTblCategoria.getValueAt(fila, 0).toString());
        jTxtNombreCategoria.setText(jTblCategoria.getValueAt(fila, 1).toString());
        jTxtDescripcionCategoria.setText(jTblCategoria.getValueAt(fila, 2).toString());
        String estado = jTblCategoria.getValueAt(fila, 3).toString();

        if (estado.equals("INACTIVO")) {
            bloquearBotonesInactivo();
            bloquearCampos();
        } else {
            bloquearBotonesTabla();
            desbloquearCampos();
            bloquearCamposActualizar();
        }
    }//GEN-LAST:event_jTblCategoriaMouseClicked

    private void jBtnBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarCategoriaActionPerformed
        buscar();
    }//GEN-LAST:event_jBtnBuscarCategoriaActionPerformed

    private void jTxtNombreCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombreCategoriaKeyTyped
        int contador = 0;
        contador = jTxtNombreCategoria.getText().length();

        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
        if (contador >= 50) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtNombreCategoriaKeyTyped

    private void jTxtDescripcionCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDescripcionCategoriaKeyTyped
        int contador = 0;
        contador = jTxtDescripcionCategoria.getText().length();

        if (Character.isLowerCase(evt.getKeyChar())) {
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        }
        if (contador >= 300) {
            evt.consume();
        }
    }//GEN-LAST:event_jTxtDescripcionCategoriaKeyTyped

    private void jCbxEstadoCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCbxEstadoCategoriaItemStateChanged
        limpiarTabla();
        listarCategoria();
    }//GEN-LAST:event_jCbxEstadoCategoriaItemStateChanged

    private void jBtnActivarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActivarCategoriaActionPerformed
        activarCategoria();
    }//GEN-LAST:event_jBtnActivarCategoriaActionPerformed

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
            java.util.logging.Logger.getLogger(Categorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Categorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Categorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Categorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Categorias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActivarCategoria;
    private javax.swing.JButton jBtnActualizarCategoria;
    private javax.swing.JButton jBtnBuscarCategoria;
    private javax.swing.JButton jBtnCancelarCategoria;
    private javax.swing.JButton jBtnEliminarCategoria;
    private javax.swing.JButton jBtnGuardarCategoria;
    private javax.swing.JButton jBtnNuevoCategoria;
    private javax.swing.JComboBox<String> jCbxEstadoCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTblCategoria;
    private javax.swing.JTextField jTxtBuscarCategoria;
    private javax.swing.JTextArea jTxtDescripcionCategoria;
    private javax.swing.JTextField jTxtIdCategoria;
    private javax.swing.JTextField jTxtNombreCategoria;
    // End of variables declaration//GEN-END:variables
}
