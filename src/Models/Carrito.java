/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Samue
 */


public class Carrito {
    private static NodoProducto cabeza = null;

    public static void agregarProducto(Productos producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoProducto actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
    }

    public static void limpiar() {
        cabeza = null;
    }

    public static NodoProducto getPrimero() {
        return cabeza;
    }

    public static int contarProductos() {
        int contador = 0;
        NodoProducto actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    public static void eliminarProducto(Productos producto) {
        if (producto == null || cabeza == null) return;

        if (cabeza.getProducto().equals(producto)) {
            cabeza = cabeza.getSiguiente();
            return;
        }

        NodoProducto actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getProducto().equals(producto)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                return;
            }
            actual = actual.getSiguiente();
        }
    }
}

