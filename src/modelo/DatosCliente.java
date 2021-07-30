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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Alejandro
 */
public class DatosCliente {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Icon icono = new ImageIcon(getClass().getResource("/imagenes/correcto.png"));
    
    public boolean insertar(Cliente cliente){
        
        String sql = "INSERT INTO CLIENTES (ID_CLI, TIP_ID_CLI, NOM_CLI, APE_CLI, DIR_CLI, TEL_CLI, COR_ELE_CLI, EST_CLI) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getId());
            ps.setString(2, cliente.getTipoId());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getApellido());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getTelefono());
            ps.setString(7, cliente.getCorreo());
            ps.setString(8, cliente.getEstado());
            boolean ejecucion = ps.execute();
            
            if(ejecucion == false){
                JOptionPane.showMessageDialog(null, "Cliente Agregado", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al agregar el cliente", "Error", JOptionPane.WARNING_MESSAGE);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        }
        return false;
    }
    
    public boolean actualizar(Cliente cliente){
        String sql = "UPDATE CLIENTES SET NOM_CLI=?, APE_CLI=?, DIR_CLI=?, TEL_CLI=?, COR_ELE_CLI=? WHERE ID_CLI=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.setString(6, cliente.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Cliente Actualizado", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public boolean eliminar(Cliente cliente){
        String sql = "UPDATE CLIENTES SET EST_CLI=? WHERE ID_CLI=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getEstado());
            ps.setString(2, cliente.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Cliente Eliminado", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public List buscar(int id){
        
        List <Cliente> ListarCli = new ArrayList();
        String sql = "SELECT * FROM CLIENTES WHERE ID_CLI="+id;
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getString("ID_CLI"));
                cliente.setTipoId(rs.getString("TIP_ID_CLI"));
                cliente.setNombre(rs.getString("NOM_CLI"));
                cliente.setApellido(rs.getString("APE_CLI"));
                cliente.setDireccion(rs.getString("DIR_CLI"));
                cliente.setTelefono(rs.getString("TEL_CLI"));
                cliente.setCorreo(rs.getString("COR_ELE_CLI"));
                cliente.setEstado(rs.getString("EST_CLI"));
                ListarCli.add(cliente);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListarCli;
    }
    
    public List listar(String estado){
       
        List <Cliente> ListarCli = new ArrayList();
        String sql = "SELECT * FROM CLIENTES WHERE EST_CLI = '"+estado+"'";
        
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getString("ID_CLI"));
                cliente.setTipoId(rs.getString("TIP_ID_CLI"));
                cliente.setNombre(rs.getString("NOM_CLI"));
                cliente.setApellido(rs.getString("APE_CLI"));
                cliente.setDireccion(rs.getString("DIR_CLI"));
                cliente.setTelefono(rs.getString("TEL_CLI"));
                cliente.setCorreo(rs.getString("COR_ELE_CLI"));
                cliente.setEstado(rs.getString("EST_CLI"));
                ListarCli.add(cliente);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListarCli;
    }
    
    public void activarCliente(String cedula){
        String sql = "UPDATE CLIENTES SET EST_CLI=? WHERE ID_CLI=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "ACTIVO");
            ps.setString(2, cedula);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Cliente Activo", "Aviso", JOptionPane.PLAIN_MESSAGE, icono);
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
    
    public String estadoCliente(String cedula){
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
    
    public boolean comprobar(String id){
        String sql = "SELECT ID_CLI FROM CLIENTES WHERE ID_CLI=?";
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
