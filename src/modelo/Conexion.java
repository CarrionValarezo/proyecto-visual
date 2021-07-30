/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 */
public class Conexion {
    
    Connection connect;
    
    public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/sistema_facturacion", "root", "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse con el servidor, por favor verifique su conexión a internet o contactese con el administrador", "Error", JOptionPane.WARNING_MESSAGE);
        }
        return connect;
    }

}
