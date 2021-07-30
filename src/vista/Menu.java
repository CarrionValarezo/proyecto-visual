/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Graphics;
import java.awt.Image;
import static java.lang.System.exit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.DatosLogin;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Alejandro
 */
public class Menu extends javax.swing.JFrame {

    Icon icono = new ImageIcon(getClass().getResource("/imagenes/cajero.png"));
    Icon icono1 = new ImageIcon(getClass().getResource("/imagenes/admin.png"));
    DatosLogin login = new DatosLogin();

    public Menu() {
        initComponents();
        this.setTitle("Sistema de Facturación");
        setExtendedState(MAXIMIZED_BOTH);
    }

    public Menu(int rol, String estado) {
        initComponents();
        this.setTitle("Sistema de Facturación");
        setExtendedState(MAXIMIZED_BOTH);
        if (estado.equals("ACTIVO")) {
            if (rol == 1) {
                jMnuInventario.setEnabled(true);
                jMnuVentas.setEnabled(true);
                jMnuReportes.setEnabled(true);
                jMnuAcceso.setEnabled(true);
                jMnuSalir.setEnabled(true);
                JOptionPane.showMessageDialog(null, "¡BIENVENIDO "+login.infoUsuario[0]+" "+login.infoUsuario[1]+"!", "Mensaje de Bienvenida", JOptionPane.PLAIN_MESSAGE, icono1);
            }
            if (rol == 2) {
                jMnuInventario.setEnabled(false);
                jMnuVentas.setEnabled(true);
                jMnuReportes.setEnabled(false);
                jMnuAcceso.setEnabled(false);
                jMnuSalir.setEnabled(true);
                JOptionPane.showMessageDialog(null, "¡BIENVENIDO "+login.infoUsuario[0]+" "+login.infoUsuario[1]+"!", "Mensaje de Bienvenida", JOptionPane.PLAIN_MESSAGE, icono);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El usuario se encuentra inhabilitado, por favor contactese con el administrador.", "Aviso", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondo3.png"));
        Image image = icon.getImage();
        jDskMenu = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                g.drawImage(image,0,0,getWidth(),getHeight(),this);
            }
        };
        jMenuBar1 = new javax.swing.JMenuBar();
        jMnuInventario = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMnuVentas = new javax.swing.JMenu();
        jMnuClientes = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMnuReportes = new javax.swing.JMenu();
        jMnuReporteStock = new javax.swing.JMenuItem();
        jMnuAcceso = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMnuSalir = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDskMenuLayout = new javax.swing.GroupLayout(jDskMenu);
        jDskMenu.setLayout(jDskMenuLayout);
        jDskMenuLayout.setHorizontalGroup(
            jDskMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        jDskMenuLayout.setVerticalGroup(
            jDskMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 153));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuBar1.setMargin(new java.awt.Insets(10, 0, 10, 0));

        jMnuInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inventario.png"))); // NOI18N
        jMnuInventario.setText(" INVENTARIO     ");
        jMnuInventario.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/categoria.png"))); // NOI18N
        jMenuItem1.setText("CATEGORIAS     ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMnuInventario.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/producto.png"))); // NOI18N
        jMenuItem2.setText("PRODUCTOS");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMnuInventario.add(jMenuItem2);

        jMenuBar1.add(jMnuInventario);

        jMnuVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ventas.png"))); // NOI18N
        jMnuVentas.setText(" VENTAS       ");
        jMnuVentas.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jMnuClientes.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMnuClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clientes.png"))); // NOI18N
        jMnuClientes.setText("CLIENTES");
        jMnuClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMnuClientesActionPerformed(evt);
            }
        });
        jMnuVentas.add(jMnuClientes);

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/venta.png"))); // NOI18N
        jMenuItem5.setText("VENTAS");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMnuVentas.add(jMenuItem5);

        jMenuBar1.add(jMnuVentas);

        jMnuReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reportes.png"))); // NOI18N
        jMnuReportes.setText(" REPORTES       ");
        jMnuReportes.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jMnuReporteStock.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMnuReporteStock.setText("REPORTE STOCK");
        jMnuReporteStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMnuReporteStockActionPerformed(evt);
            }
        });
        jMnuReportes.add(jMnuReporteStock);

        jMenuBar1.add(jMnuReportes);

        jMnuAcceso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/acceso.png"))); // NOI18N
        jMnuAcceso.setText(" ACCESO       ");
        jMnuAcceso.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuarios.png"))); // NOI18N
        jMenuItem8.setText("USUARIOS");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMnuAcceso.add(jMenuItem8);

        jMenuBar1.add(jMnuAcceso);

        jMnuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        jMnuSalir.setText(" SALIR       ");
        jMnuSalir.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jMnuSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMnuSalirMouseClicked(evt);
            }
        });
        jMnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMnuSalirActionPerformed(evt);
            }
        });

        jMenuItem7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logout.png"))); // NOI18N
        jMenuItem7.setText("CERRAR SESIÓN");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMnuSalir.add(jMenuItem7);

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/close.png"))); // NOI18N
        jMenuItem9.setText("SALIR");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMnuSalir.add(jMenuItem9);

        jMenuBar1.add(jMnuSalir);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDskMenu)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDskMenu)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Categorias cat = new Categorias();
        jDskMenu.add(cat);
        cat.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMnuReporteStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMnuReporteStockActionPerformed
        try {
            Conexion cc = new Conexion();
            JasperReport reporte = JasperCompileManager.compileReport("c:/reportes/ReporteStock.jrxml");
            JasperPrint print = JasperFillManager.fillReport(reporte, null, cc.conectar());
            JasperViewer.viewReport(print);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jMnuReporteStockActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Productos pro = new Productos();
        jDskMenu.add(pro);
        pro.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMnuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMnuClientesActionPerformed
        Clientes cli = new Clientes();
        jDskMenu.add(cli);
        cli.setVisible(true);
    }//GEN-LAST:event_jMnuClientesActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        Usuarios usu = new Usuarios();
        jDskMenu.add(usu);
        usu.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMnuSalirActionPerformed

    }//GEN-LAST:event_jMnuSalirActionPerformed

    private void jMnuSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMnuSalirMouseClicked

    }//GEN-LAST:event_jMnuSalirMouseClicked

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        Login log = new Login();
        dispose();
        log.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
		Ventas v = new Ventas(); 
		jDskMenu.add(v); 
		v.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane jDskMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenu jMnuAcceso;
    private javax.swing.JMenuItem jMnuClientes;
    private javax.swing.JMenu jMnuInventario;
    private javax.swing.JMenuItem jMnuReporteStock;
    private javax.swing.JMenu jMnuReportes;
    private javax.swing.JMenu jMnuSalir;
    private javax.swing.JMenu jMnuVentas;
    // End of variables declaration//GEN-END:variables
}
