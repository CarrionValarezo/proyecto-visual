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

/**
 *
 * @author Alejandro
 */
public class DatosCategoria {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Icon icono = new ImageIcon(getClass().getResource("/imagenes/correcto.png"));
    
    public boolean insertar(Categoria categoria){
        
        String sql = "INSERT INTO CATEGORIAS (NOM_CAT, DES_CAT, EST_CAT) VALUES (?, ?, ?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setString(3, categoria.getEstado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Categoría Agregada Correctamente", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public boolean actualizar(Categoria categoria){
        String sql = "UPDATE CATEGORIAS SET NOM_CAT=?, DES_CAT=? WHERE ID_CAT=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Categoría Actualizada", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public boolean eliminar(Categoria categoria){
        String sql = "UPDATE CATEGORIAS SET EST_CAT=? WHERE ID_CAT=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria.getEstado());
            ps.setInt(2, categoria.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Categoría Eliminada", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public int categoriaVerificador(String id){
        int idPro = 0;
        String sql = "SELECT ID_PRO FROM PRODUCTOS WHERE ID_CAT_PER=? AND EST_PRO='ACTIVO'";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                idPro = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return idPro;
    }
    
    public List buscar(int id){
        
        List <Categoria> ListarCat = new ArrayList();
        String sql = "SELECT * FROM CATEGORIAS WHERE ID_CAT="+id;
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("ID_CAT"));
                categoria.setNombre(rs.getString("NOM_CAT"));
                categoria.setDescripcion(rs.getString("DES_CAT"));
                categoria.setEstado(rs.getString("EST_CAT"));
                ListarCat.add(categoria);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No existen coincidencias");
        }
        return ListarCat;
    }
    
    public List listar(String estado){
       
        List <Categoria> ListarCat = new ArrayList();
        String sql = "SELECT * FROM CATEGORIAS WHERE EST_CAT = '"+estado+"'";
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("ID_CAT"));
                categoria.setNombre(rs.getString("NOM_CAT"));
                categoria.setDescripcion(rs.getString("DES_CAT"));
                categoria.setEstado(rs.getString("EST_CAT"));
                ListarCat.add(categoria);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListarCat;
    }
    
    public void activarCategoria(String id){
        String sql = "UPDATE CATEGORIAS SET EST_CAT=? WHERE ID_CAT=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "ACTIVO");
            ps.setString(2, id);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Categoría Activa", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public boolean comprobar(String nombre){
        String sql = "SELECT NOM_CAT FROM CATEGORIAS WHERE NOM_CAT=?";
        boolean resultado = false;
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            
            resultado = rs.next();
            System.out.println(resultado);
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return resultado;
    }
    
    public String estadoCategoria(String id){
        String estado = "";
        String sql = "SELECT EST_CAT FROM CATEGORIAS WHERE ID_CAT=?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
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
