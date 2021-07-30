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
 * @author Alejandro
 */
public class DatosUsuario {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Icon icono = new ImageIcon(getClass().getResource("/imagenes/correcto.png"));
    
    public boolean insertar(Usuario usuario){ 
        String sql = "INSERT INTO USUARIOS (CED_USU, NOM_USU, APE_USU, DIR_USU, TEL_USU, COR_ELE_USU, CON_USU, EST_USU, ID_ROL_PER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getId());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getCorreo());
            ps.setString(7, usuario.getContrasena());
            ps.setString(8, usuario.getEstado());
            ps.setInt(9, usuario.getRol());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Usuario Agregado Correctamente", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public boolean actualizar(Usuario usuario){
        String sql = "UPDATE USUARIOS SET NOM_USU=?, APE_USU=?, DIR_USU=?, TEL_USU=?, COR_ELE_USU=?, CON_USU=?, ID_ROL_PER=? WHERE CED_USU=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getDireccion());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, usuario.getContrasena());
            ps.setInt(7, usuario.getRol());
            ps.setString(8, usuario.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Usuario Actualizado", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public boolean eliminar(Usuario usuario){
        String sql = "UPDATE USUARIOS SET EST_USU=? WHERE CED_USU=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getEstado());
            ps.setString(2, usuario.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Usuario Eliminado", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public void activarUsuario(String cedula){
        String sql = "UPDATE USUARIOS SET EST_USU=? WHERE CED_USU=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "ACTIVO");
            ps.setString(2, cedula);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Usuario Activo", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public String estadoUsuario(String cedula){
        String estado = "";
        String sql = "SELECT EST_USU FROM USUARIOS WHERE CED_USU=?";
        
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
    
    public List buscar(int id){
        
        List <Usuario> ListarUsu = new ArrayList();
        String sql = "SELECT * FROM USUARIOS WHERE CED_USU="+id;
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getString("CED_USU"));
                usuario.setNombre(rs.getString("NOM_USU"));
                usuario.setApellido(rs.getString("APE_USU"));
                usuario.setDireccion(rs.getString("DIR_USU"));
                usuario.setTelefono(rs.getString("TEL_USU"));
                usuario.setCorreo(rs.getString("COR_ELE_USU"));
                usuario.setEstado(rs.getString("EST_USU"));
                usuario.setRol(rs.getInt("ID_ROL_PER"));
                ListarUsu.add(usuario);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListarUsu;
    }
    
    public List listar(String estado){
       
        List <Usuario> ListarUsu = new ArrayList();
        String sql = "SELECT * FROM USUARIOS WHERE EST_USU = '"+estado+"'";
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getString("CED_USU"));
                usuario.setNombre(rs.getString("NOM_USU"));
                usuario.setApellido(rs.getString("APE_USU"));
                usuario.setDireccion(rs.getString("DIR_USU"));
                usuario.setTelefono(rs.getString("TEL_USU"));
                usuario.setCorreo(rs.getString("COR_ELE_USU"));
                usuario.setContrasena(rs.getString("CON_USU"));
                usuario.setEstado(rs.getString("EST_USU"));
                usuario.setRol(rs.getInt("ID_ROL_PER"));
                ListarUsu.add(usuario);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListarUsu;
    }
    
    public void consultarRol(JComboBox usuario){
        String sql = "SELECT ID_ROL, NOM_ROL FROM ROLES WHERE EST_ROL='ACTIVO'";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                usuario.addItem(rs.getInt("ID_ROL")+".- "+rs.getString("NOM_ROL"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Object consultarNombreRol(int id){
        String sql = "SELECT NOM_ROL FROM ROLES WHERE ID_ROL=?";
        Object nombre = "";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                nombre = rs.getString("NOM_ROL");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return nombre;
    }
    
    public Object consultarContrasena(String id){
        String sql = "SELECT CON_USU FROM USUARIOS WHERE CED_USU=?";
        Object contrasena = "";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                contrasena = rs.getString("CON_USU");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return contrasena;
    }
    
    public boolean comprobar(String id){
        String sql = "SELECT CED_USU FROM USUARIOS WHERE CED_USU=?";
        boolean resultado = false;
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            resultado = rs.next();
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return resultado;
    }
    
}
