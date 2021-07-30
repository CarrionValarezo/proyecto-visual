/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 */
public class DatosLogin {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    public static String infoUsuario[] = new String[4];

    public boolean acceder(String cedula, String contrasena) {
        String sql = "SELECT NOM_USU, APE_USU, ID_ROL_PER, EST_USU FROM usuarios WHERE CED_USU=? AND CON_USU=?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            if (rs.next()) {
                login l = new login();
                l.setNombre(rs.getString("NOM_USU"));
                infoUsuario[0] = l.getNombre();
                l.setApellido(rs.getString("APE_USU"));
                infoUsuario[1] = l.getApellido();
                l.setRol(rs.getInt("ID_ROL_PER"));
                infoUsuario[2] = String.valueOf(l.getRol());
                l.setEstado(rs.getString("EST_USU"));
                infoUsuario[3] = String.valueOf(l.getEstado());

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}
