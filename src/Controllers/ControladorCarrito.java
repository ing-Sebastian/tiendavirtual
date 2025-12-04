/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.ItemCarrito;
import Models.Nodo;
import Models.Productos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import javax.swing.JOptionPane;


/**
 *
 * @author samue
 */

public class ControladorCarrito {

    public Nodo<ItemCarrito> inicio;
    public Nodo<ItemCarrito> fin;
    public String rutaArchivo = "carrito.txt";

    public ControladorCarrito() {
        inicio = null;
        fin = null;
        cargarDesdeTXT();
    }

    public boolean vacio() {
        return inicio == null;
    }

    // ------------------------- AGREGAR PRODUCTO -------------------------
    public void agregarProducto(Productos p) {

        if (p == null) return;

        // si ya está en el carrito, aumentar cantidad en 1
        if (!vacio()) {
            Nodo<ItemCarrito> actual = inicio;
            do {
                if (actual.dato != null && actual.dato.idProd == p.idProd) {
                    actual.dato.actualizarCantidad(actual.dato.cantidad + 1);
                    guardarEnTXT();
                    return;
                }
                actual = actual.sig;
            } while (actual != inicio);
        }

        // crear nuevo item con cantidad 1
        String fecha = LocalDate.now().toString();
        ItemCarrito nuevoItem = new ItemCarrito(p.idProd, p.nombre, p.precio, 1, fecha, p.nombreImagen);
        Nodo<ItemCarrito> nuevo = new Nodo<>(nuevoItem);

        if (vacio()) {
            inicio = nuevo;
            fin = nuevo;
            inicio.sig = inicio;
            inicio.ant = inicio;
        } else {
            nuevo.ant = fin;
            nuevo.sig = inicio;
            fin.sig = nuevo;
            inicio.ant = nuevo;
            fin = nuevo;
        }

        guardarEnTXT();
    }

    // ----------------------- MODIFICAR CANTIDAD -----------------------
    public boolean modificarCantidad(int idProd, int nuevaCant) {

        if (vacio()) return false;
        if (nuevaCant < 1) return false; // no permitimos cero o negativos aquí 

        Nodo<ItemCarrito> actual = inicio;
        do {
            if (actual.dato != null && actual.dato.idProd == idProd) {
                actual.dato.actualizarCantidad(nuevaCant);
                guardarEnTXT();
                return true;
            }
            actual = actual.sig;
        } while (actual != inicio);

        return false;
    }

    // ----------------------- ELIMINAR ITEM -----------------------
    public boolean eliminar(int idProd) {

        if (vacio()) return false;

        Nodo<ItemCarrito> actual = inicio;
        do {
            if (actual.dato != null && actual.dato.idProd == idProd) {

                if (actual == inicio && actual == fin) { // único nodo
                    inicio = null;
                    fin = null;

                } else if (actual == inicio) { // borrar inicio
                    inicio = inicio.sig;
                    inicio.ant = fin;
                    fin.sig = inicio;

                } else if (actual == fin) { // borrar fin
                    fin = fin.ant;
                    fin.sig = inicio;
                    inicio.ant = fin;

                } else { // nodo intermedio
                    actual.ant.sig = actual.sig;
                    actual.sig.ant = actual.ant;
                }

                guardarEnTXT();
                return true;
            }

            actual = actual.sig;
        } while (actual != inicio);

        return false;
    }

    // ----------------------- CALCULAR TOTAL -----------------------
    public double totalCarrito() {
        if (vacio()) return 0;

        double total = 0;
        Nodo<ItemCarrito> actual = inicio;
        do {
            if (actual.dato != null) total += actual.dato.subtotal;
            actual = actual.sig;
        } while (actual != inicio);

        return total;
    }

    // ----------------------- GUARDAR EN TXT -----------------------
    public void guardarEnTXT() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));

            if (!vacio()) {
                Nodo<ItemCarrito> actual = inicio;
                do {
                    if (actual.dato != null) {
                        writer.write("ID: " + actual.dato.idProd); writer.newLine();
                        writer.write("Nombre: " + actual.dato.nombre); writer.newLine();
                        writer.write("Precio: " + actual.dato.precioUnitario); writer.newLine();
                        writer.write("Cantidad: " + actual.dato.cantidad); writer.newLine();
                        writer.write("Subtotal: " + actual.dato.subtotal); writer.newLine();
                        writer.write("Fecha: " + actual.dato.fecha); writer.newLine();
                        writer.write("Imagen: " + actual.dato.nombreImagen); writer.newLine();
                        writer.write("---------------------------"); writer.newLine();
                    }
                    actual = actual.sig;
                } while (actual != inicio);
            }

            writer.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar carrito: " + e.getMessage());
        }
    }

    // ----------------------- CARGAR DESDE TXT -----------------------
    public void cargarDesdeTXT() {

        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) return;

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            String id = "", nombre = "", precio = "", cantidad = "", subtotal = "", fecha = "", imagen = "";

            while ((linea = reader.readLine()) != null) {

                if (linea.startsWith("ID: ")) id = linea.substring(4).trim();
                else if (linea.startsWith("Nombre: ")) nombre = linea.substring(8).trim();
                else if (linea.startsWith("Precio: ")) precio = linea.substring(8).trim();
                else if (linea.startsWith("Cantidad: ")) cantidad = linea.substring(10).trim();
                else if (linea.startsWith("Subtotal: ")) subtotal = linea.substring(10).trim();
                else if (linea.startsWith("Fecha: ")) fecha = linea.substring(7).trim();
                else if (linea.startsWith("Imagen: ")) imagen = linea.substring(8).trim();

                if (linea.equals("---------------------------")) {

                    try {
                        int idN = Integer.parseInt(id);
                        double precioN = Double.parseDouble(precio);
                        int cantN = Integer.parseInt(cantidad);
                        // subtotal y fecha/imagen ya leídos 

                        ItemCarrito it = new ItemCarrito(idN, nombre, precioN, cantN, fecha, imagen);

                        Nodo<ItemCarrito> nuevo = new Nodo<>(it);

                        if (vacio()) {
                            inicio = nuevo;
                            fin = nuevo;
                            inicio.sig = inicio;
                            inicio.ant = inicio;
                        } else {
                            nuevo.ant = fin;
                            nuevo.sig = inicio;
                            fin.sig = nuevo;
                            inicio.ant = nuevo;
                            fin = nuevo;
                        }
                    } catch (NumberFormatException nfe) {
                        // si hay un registro corrupto, simplemente lo saltamos
                        System.err.println("Registro carrito inválido: " + id + " | " + nombre);
                    }

                    id = nombre = precio = cantidad = subtotal = fecha = imagen = "";
                }
            }

            reader.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error cargando carrito: " + e.getMessage());
        }
    }

    // ----------------------- VACIAR CARRITO (USAR AL COMPRAR) -----------------------
    public void vaciar() {
        inicio = null;
        fin = null;
        // borrar archivo físico
        try {
            File f = new File(rutaArchivo);
            if (f.exists()) f.delete();
        } catch (Exception e) { /* ignorar */ }
    }
}