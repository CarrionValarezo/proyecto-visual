/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.List;
import java.awt.event.KeyEvent;
import modelo.DatosLogin;
import modelo.login;
import javax.swing.JOptionPane;

/**
 *
 */
public class Login extends javax.swing.JFrame {

    login lg = new login();
    DatosLogin login = new DatosLogin();
    public static String cedula;

    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);

    }

    public void acceder() {

        String usuario = JTxtUsuario.getText();
        String contrasena = String.valueOf(JTxtContrasena.getPassword());

        if (!"".equals(JTxtUsuario.getText()) && !"".equals(String.valueOf(JTxtContrasena.getPassword()))) {
            jLblAviso.setText("");
            boolean valor = login.acceder(usuario, contrasena);
            if (valor == true) {
                jLblAviso.setText("");
                cedula = JTxtUsuario.getText();
                Menu menu = new Menu(Integer.valueOf(login.infoUsuario[2]), login.infoUsuario[3]);
                menu.setVisible(true);
                dispose();
            } else {
                jLblAviso.setText("USUARIO O CONTRASEÑA INCORRECTOS");
            }
        } else {
            jLblAviso.setText("UNO O MÁS CAMPOS ESTÁN VACIOS");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JTxtUsuario = new javax.swing.JTextField();
        JTxtContrasena = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        JBtnSalir = new javax.swing.JButton();
        JBtnAcceder = new javax.swing.JButton();
        jLblAviso = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/login.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(222, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(218, 218, 218))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 32, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, -6, 540, 150));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("USUARIO:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("CONTRASEÑA:");

        JTxtUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTxtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTxtUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JTxtUsuarioKeyTyped(evt);
            }
        });

        JTxtContrasena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTxtContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTxtContrasenaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JTxtUsuario)
                    .addComponent(JTxtContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(JTxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(JTxtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 540, 130));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        JBtnSalir.setBackground(new java.awt.Color(0, 80, 137));
        JBtnSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        JBtnSalir.setForeground(new java.awt.Color(255, 255, 255));
        JBtnSalir.setText("SALIR");
        JBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBtnSalirActionPerformed(evt);
            }
        });

        JBtnAcceder.setBackground(new java.awt.Color(0, 80, 137));
        JBtnAcceder.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        JBtnAcceder.setForeground(new java.awt.Color(255, 255, 255));
        JBtnAcceder.setText("ACCEDER");
        JBtnAcceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBtnAccederActionPerformed(evt);
            }
        });
        JBtnAcceder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JBtnAccederKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JBtnAccederKeyTyped(evt);
            }
        });

        jLblAviso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblAviso.setForeground(new java.awt.Color(255, 102, 102));
        jLblAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(JBtnAcceder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(JBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblAviso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLblAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JBtnAcceder, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 540, 160));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBtnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_JBtnSalirActionPerformed

    private void JBtnAccederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBtnAccederActionPerformed
        acceder();
    }//GEN-LAST:event_JBtnAccederActionPerformed

    private void JTxtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTxtUsuarioKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_JTxtUsuarioKeyTyped

    private void JBtnAccederKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JBtnAccederKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_JBtnAccederKeyTyped

    private void JBtnAccederKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JBtnAccederKeyPressed
        
    }//GEN-LAST:event_JBtnAccederKeyPressed

    private void JTxtContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTxtContrasenaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            acceder();
        }
    }//GEN-LAST:event_JTxtContrasenaKeyPressed

    private void JTxtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTxtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            acceder();
        }
    }//GEN-LAST:event_JTxtUsuarioKeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBtnAcceder;
    private javax.swing.JButton JBtnSalir;
    private javax.swing.JPasswordField JTxtContrasena;
    private javax.swing.JTextField JTxtUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLblAviso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
