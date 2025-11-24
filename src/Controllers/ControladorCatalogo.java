/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import Models.Nodo;
import Models.Productos;

/**
 *
 * @author sebas, samuel, mayra
 */
public class ControladorCatalogo {
    //lista Productos
    public Nodo<Productos> inicio;
    public Nodo<Productos> fin;
    
    public ControladorCatalogo() {
        inicio = null;
        fin = null;
    }
    
    //revisar si es vacia
    public boolean listaVacia() {
        return inicio == null;
    }

    //buscar por id
    public Productos buscarPorId(int id) {

        if (listaVacia()) {
            return null;
        }

        Nodo<Productos> actual = inicio;

        do {
            if (actual.dato.idProd == id) {
                return actual.dato;
            }
            actual = actual.sig;
        } while (actual != inicio);

        return null;
    }

   
    //metodo registrar producto
    public boolean registrarProducto(JTextField jtfId, JTextField jtfNombre,
            JTextField jtfPrecio, JTextField jtfDescripcion,
            JTextField jtfImg) {

        try {
            //validaciones, trim es para quitar espacios vacios
            if (jtfId.getText().trim().isEmpty()
                    || jtfNombre.getText().trim().isEmpty()
                    || jtfPrecio.getText().trim().isEmpty()
                    || jtfDescripcion.getText().trim().isEmpty()
                    || jtfImg.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios");
                return false;
            }
            //parseamos y tomamos para crear el obj
            int id = Integer.parseInt(jtfId.getText().trim());
            String nombre = jtfNombre.getText().trim();
            double precio = Double.parseDouble(jtfPrecio.getText().trim());
            String descripcion = jtfDescripcion.getText().trim();
            String imagen = jtfImg.getText().trim();//no estoy seguro todavia de como se aplicaria en este caso

            if (buscarPorId(id) != null) {
                JOptionPane.showMessageDialog(null,
                        "Ya existe un producto con ese ID");
                return false;
            }

            //se crea el producto
            Productos nuevo = new Productos(id, nombre, precio, descripcion, imagen);
            Nodo<Productos> nuevoNodo = new Nodo<>(nuevo);

            //lo introducimos en la lista, ojito con eso
            if (listaVacia()) {
                inicio = nuevoNodo;
                fin = nuevoNodo;
                inicio.sig = inicio;
                inicio.ant = inicio;
            } else {
                nuevoNodo.ant = fin;
                nuevoNodo.sig = inicio;
                fin.sig = nuevoNodo;
                inicio.ant = nuevoNodo;
                fin = nuevoNodo;
            }

            JOptionPane.showMessageDialog(null,
                    "Producto registrado exitosamente");

            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage());
            return false;
        }
    }

    //metodo eliminar por el id del producto
    public boolean eliminarPorId(int id) {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null,
                    "No hay productos registrados");
            return false;
        }

        Nodo<Productos> actual = inicio;

        do {

            if (actual.dato.idProd == id) {

                //uno
                if (actual == inicio && actual == fin) {
                    inicio = null;
                    fin = null;
                }
                // bora iinicio
                else if (actual == inicio) {
                    inicio = inicio.sig;
                    inicio.ant = fin;
                    fin.sig = inicio;
                }
                // Borra final
                else if (actual == fin) {
                    fin = fin.ant;
                    fin.sig = inicio;
                    inicio.ant = fin;
                }
                // borrado normal
                else {
                    actual.ant.sig = actual.sig;
                    actual.sig.ant = actual.ant;
                }

                JOptionPane.showMessageDialog(null,
                        "Producto eliminado con exito");

                return true;
            }

            actual = actual.sig;

        } while (actual != inicio);

        JOptionPane.showMessageDialog(null,
                "ID no encontrado");
        return false;
    }

   
    //mostrar producto
    public void mostrarProductos() {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null,
                    "No hay productos registrados");
            return;
        }

        String msj = "CATÁLOGO DE PRODUCTOS:\n\n";
        Nodo<Productos> actual = inicio;

        do {
            msj += "ID: " + actual.dato.idProd + "\n"
                    + "Nombre: " + actual.dato.nombre + "\n"
                    + "Precio: " + actual.dato.precio + "\n"
                    + "Descripción: " + actual.dato.descripcion + "\n"
                    + "Imagen: " + actual.dato.nombreImagen + "\n"//cambiar por como se realize el guardado de la imagen
                    + "------------------------------\n";

            actual = actual.sig;

        } while (actual != inicio);

        JOptionPane.showMessageDialog(null, msj);
    }

}
