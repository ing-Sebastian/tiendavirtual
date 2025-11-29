/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author samuel y maira
 */
public class ListaDeseos {
    private Productos productos;
    private NodoDeseo siguiente;

    public NodoDeseo(Productos productos) {
        this.productos = productos;
        this.siguiente = null;
    }

    public Productos getProductos() {
        return productos;
    }

    public NodoDeseo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDeseo siguiente) {
        this.siguiente = siguiente;
    }    
    
}
