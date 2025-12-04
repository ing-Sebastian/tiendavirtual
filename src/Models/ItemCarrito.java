/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author samue
 */

public class ItemCarrito {

    public int idProd;
    public String nombre;
    public double precioUnitario;
    public int cantidad;
    public double subtotal;
    public String fecha;
    public String nombreImagen;

    public ItemCarrito(int idProd, String nombre, double precioUnitario,
                       int cantidad, String fecha, String nombreImagen) {

        this.idProd = idProd;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = precioUnitario * cantidad;
        this.fecha = fecha;
        this.nombreImagen = nombreImagen;
    }

    public void actualizarCantidad(int nuevaCantidad) {
        this.cantidad = nuevaCantidad;
        this.subtotal = this.precioUnitario * nuevaCantidad;
    }
}

