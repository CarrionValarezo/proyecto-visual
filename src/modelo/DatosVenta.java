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
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import vista.Login;

/**
 *
 */
public class DatosVenta {

    Icon icono = new ImageIcon(getClass().getResource("/imagenes/correcto.png"));
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public boolean insertar(Venta venta) {
        String sql = "INSERT INTO VENTAS (CED_USU_PER, ID_CLI_PER, MET_PAG_VEN, TOT_VEN, EST_VEN) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, venta.getCedulaCajero());
            ps.setString(2, venta.getCedulaCliente());
            ps.setString(3, venta.getMetodo());
            ps.setFloat(4, venta.getTotal());
            ps.setString(5, venta.getEstado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Venta Realizada Correctamente", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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

    public int numeroVenta() {
        int numero = 0;
        String sql = "SELECT MAX(NUM_FAC) FROM VENTAS";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                numero = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return numero;
    }

    public String buscarCliente(String cedula) {
        String nombre = "";
        String apellido = "";
        String sql = "SELECT NOM_CLI, APE_CLI FROM CLIENTES WHERE ID_CLI=?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString(1);
                apellido = rs.getString(2);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return nombre + " " + apellido;
    }

    public String buscarNombreProducto(String codigo) {
        String nombre = "";
        String sql = "SELECT NOM_PRO FROM PRODUCTOS WHERE COD_BAR_PRO=? AND EST_PRO='ACTIVO'";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return nombre;
    }

    public List listar(String estado) {

        List<Venta> ListarVen = new ArrayList();
        Login login = new Login();
        String sql = "SELECT * FROM VENTAS WHERE EST_VEN=? AND CED_USU_PER=?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setString(2, login.cedula);
            rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getString("NUM_FAC"));
                venta.setCedulaCajero(rs.getString("CED_USU_PER"));
                venta.setCedulaCliente(rs.getString("ID_CLI_PER"));
                venta.setFecha(rs.getString("FEC_VEN"));
                venta.setMetodo(rs.getString("MET_PAG_VEN"));
                venta.setTotal(rs.getFloat("TOT_VEN"));
                venta.setEstado(rs.getString("EST_VEN"));
                ListarVen.add(venta);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListarVen;
    }

    public float buscarPrecioProducto(String codigo) {
        float precio = 0;
        String sql = "SELECT PRE_PRO FROM PRODUCTOS WHERE COD_BAR_PRO=? AND EST_PRO='ACTIVO'";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                precio = rs.getFloat(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return precio;
    }

    public int buscarStockProducto2(int codigo) {
        int stock = 0;
        String sql = "SELECT CAN_PRO FROM PRODUCTOS WHERE ID_PRO=?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                stock = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return stock;
    }
    
    public int buscarStockProducto(String codigo) {
        int stock = 0;
        String sql = "SELECT CAN_PRO FROM PRODUCTOS WHERE COD_BAR_PRO=? AND EST_PRO='ACTIVO'";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                stock = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return stock;
    }

    public int buscarIdProducto(String codigo) {
        int id = 0;
        String sql = "SELECT ID_PRO FROM PRODUCTOS WHERE COD_BAR_PRO=?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }

    public String estadoCliente(String cedula) {
        String estado = "";
        String sql = "SELECT EST_CLI FROM CLIENTES WHERE ID_CLI=?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            rs = ps.executeQuery();
            if (rs.next()) {
                estado = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return estado;
    }

    public void activarCliente(String cedula) {
        String sql = "UPDATE CLIENTES SET EST_CLI=? WHERE ID_CLI=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "ACTIVO");
            ps.setString(2, cedula);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Cliente Activo", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public boolean anular(Venta venta) {
        String sql = "UPDATE VENTAS SET EST_VEN=? WHERE NUM_FAC=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "ANULADO");
            ps.setString(2, venta.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Venta Anulada", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

}
