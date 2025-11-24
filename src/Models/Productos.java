/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Sebas, Samuel y Mayra
 */
public class Productos {
    public int idProd;
    public String nombre;
    public double precio;
    public String descripcion;
    public String nombreImagen; //nombre tipo pants.png

    public Productos(int idProd, String nombre, double precio, String descripcion, String nombreImagen) {
        this.idProd = idProd;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.nombreImagen = nombreImagen;
    }
}
