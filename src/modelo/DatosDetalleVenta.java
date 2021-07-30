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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Alejandro
 */
public class DatosDetalleVenta {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean insertarDetalle(DetalleVenta dventa) {
        String sql = "INSERT INTO DETALLE_VENTAS (NUM_FAC_PER, ID_PRO_PER, CAN_PRO) VALUES (?, ?, ?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, dventa.getIdPertenece());
            ps.setInt(2, dventa.getIdProducto());
            ps.setInt(3, dventa.getCantidad());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public int consultarIdProducto(String codigo) {
        String sql = "SELECT ID_PRO FROM PRODUCTOS WHERE COD_BAR_PRO=?";
        int id = 0;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getInt("ID_PRO");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }

    public boolean reducirStock(int cantidad, int id) {
        String sql = "UPDATE PRODUCTOS SET CAN_PRO=? WHERE ID_PRO=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, id);
            ps.execute();

            }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.out.println(e.toString());
            }
        }
        return true;
    }

}
