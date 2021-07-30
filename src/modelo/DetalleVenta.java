/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 */
public class DetalleVenta {

    private int idPertenece;
    private int idProducto;
    private int cantidad;

    public int getIdPertenece() {
        return idPertenece;
    }

    public void setIdPertenece(int idPertenece) {
        this.idPertenece = idPertenece;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
