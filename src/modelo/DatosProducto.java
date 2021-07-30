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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 */
public class DatosProducto {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Icon icono = new ImageIcon(getClass().getResource("/imagenes/correcto.png"));
    Icon icono1 = new ImageIcon(getClass().getResource("/imagenes/cancelar.png"));
    
    public boolean insertar(Producto producto){
        
        String sql = "INSERT INTO PRODUCTOS (COD_BAR_PRO, NOM_PRO, PRE_PRO, CAN_PRO, DES_PRO, EST_PRO, ID_CAT_PER) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getPrecio());
            ps.setInt(4, producto.getCantidad());
            ps.setString(5, producto.getDescripcion());
            ps.setString(6, producto.getEstado());
            ps.setInt(7, producto.getCategoria());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Producto Agregado Correctamente", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public boolean actualizar(Producto producto){
        String sql = "UPDATE PRODUCTOS SET NOM_PRO=?, PRE_PRO=?, CAN_PRO=?, DES_PRO=? WHERE ID_PRO=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setFloat(2, producto.getPrecio());
            ps.setInt(3, producto.getCantidad());
            ps.setString(4, producto.getDescripcion());
            ps.setInt(5, producto.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Producto Actualizado", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.out.println(e.toString());
            }
        }
    }
    
    public boolean eliminar(Producto producto){
        String sql = "UPDATE PRODUCTOS SET EST_PRO=? WHERE ID_PRO=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getEstado());
            ps.setInt(2, producto.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Producto Eliminado", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.out.println(e.toString());
            }
        }
    }
    
    public List buscar(String codigo){
        
        List <Producto> ListarPro = new ArrayList();
        String sql = "SELECT * FROM PRODUCTOS WHERE COD_BAR_PRO=?";
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt("ID_PRO"));
                producto.setCodigo(rs.getString("COD_BAR_PRO"));
                producto.setNombre(rs.getString("NOM_PRO"));
                producto.setPrecio(rs.getFloat("PRE_PRO"));
                producto.setCantidad(rs.getInt("CAN_PRO"));
                producto.setDescripcion(rs.getString("DES_PRO"));
                producto.setEstado(rs.getString("EST_PRO"));
                producto.setCategoria(rs.getInt("ID_CAT_PER"));
                ListarPro.add(producto);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No existen coincidencias");
        }
        return ListarPro;
    }
    
    public boolean comprobar(String codigo){
        String sql = "SELECT COD_BAR_PRO FROM PRODUCTOS WHERE COD_BAR_PRO=?";
        boolean resultado = false;
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            
            resultado = rs.next();
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return resultado;
    }
    
    public void consultarCategoria(JComboBox categoria){
        String sql = "SELECT ID_CAT, NOM_CAT FROM CATEGORIAS WHERE EST_CAT='ACTIVO'";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                categoria.addItem(rs.getString("ID_CAT")+".- "+rs.getString("NOM_CAT"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Object consultarNombreCategoria(int id){
        String sql = "SELECT NOM_CAT FROM CATEGORIAS WHERE ID_CAT=?";
        Object nombre = "";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                nombre = rs.getString("NOM_CAT");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return nombre;
    }
    
    public List listar(String estado){
       
        List <Producto> ListarPro = new ArrayList();
        String sql = "SELECT * FROM PRODUCTOS WHERE EST_PRO=?";
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt("ID_PRO"));
                producto.setNombre(rs.getString("NOM_PRO"));
                producto.setCodigo(rs.getString("COD_BAR_PRO"));
                producto.setPrecio(rs.getFloat("PRE_PRO"));
                producto.setCantidad(rs.getInt("CAN_PRO"));
                producto.setDescripcion(rs.getString("DES_PRO"));
                producto.setEstado(rs.getString("EST_PRO"));
                producto.setCategoria(rs.getInt("ID_CAT_PER"));
                ListarPro.add(producto);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListarPro;
    }
    
    public void activarProducto(int id){
        String sql = "UPDATE PRODUCTOS SET EST_PRO=? WHERE ID_PRO=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "ACTIVO");
            ps.setInt(2, id);
            boolean ejecucion = ps.execute();
            if (ejecucion != true) {
                JOptionPane.showMessageDialog(null, "Producto Activo", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
            }else{
                JOptionPane.showMessageDialog(null, "Error al activar el producto", "Aviso", JOptionPane.PLAIN_MESSAGE, icono1);
            }
            }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.out.println(e.toString());
            }
        }
    }
    
    public String estadoProducto(int id){
        String estado = "";
        String sql = "SELECT EST_PRO FROM PRODUCTOS WHERE ID_PRO=?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                estado = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return estado;
    }
}
