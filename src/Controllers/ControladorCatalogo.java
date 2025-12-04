/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Nodo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import Models.Productos;

public class ControladorCatalogo {

    public Nodo<Productos> inicio;
    public Nodo<Productos> fin;
    public String rutaArchivo = "productos.txt";

    public ControladorCatalogo() {
        inicio = null;
        fin = null;
        cargarDesdeTXT();
    }

    public boolean listaVacia() {
        return inicio == null;
    }

    // --------------------- REGISTRAR PRODUCTO ---------------------
    public boolean registrarProducto(JTextField jtfNombre,
            JTextField jtfPrecio, JTextField jtfDescripcion,
            JTextField jtfImg) {

        try {

            if (jtfNombre.getText().trim().isEmpty()
                    || jtfPrecio.getText().trim().isEmpty()
                    || jtfDescripcion.getText().trim().isEmpty()
                    || jtfImg.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios");
                return false;
            }

            int nuevoID;
            if (listaVacia()) {
                nuevoID = 1;
            } else {
                nuevoID = fin.dato.idProd + 1;
            }

            String nombre = jtfNombre.getText().trim();
            double precio = Double.parseDouble(jtfPrecio.getText().trim());
            String descripcion = jtfDescripcion.getText().trim();
            
           //solo nombre de la imagen
            String nombreImagen = jtfImg.getText().trim();
            

            Productos p = new Productos(nuevoID, nombre, precio, descripcion, nombreImagen);
            Nodo<Productos> nuevo = new Nodo<>(p);

            if (listaVacia()) {
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

            JOptionPane.showMessageDialog(null,
                    "Producto registrado correctamente");

            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage());
            return false;
        }
    }

    // --------------------- ELIMINAR PRODUCTO ---------------------
    public boolean eliminarPorId(int id) {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null,
                    "No hay productos registrados");
            return false;
        }

        Nodo<Productos> actual = inicio;

        do {

            if (actual.dato.idProd == id) {

                if (actual == inicio && actual == fin) {
                    inicio = null;
                    fin = null;

                } else if (actual == inicio) {
                    inicio = inicio.sig;
                    inicio.ant = fin;
                    fin.sig = inicio;

                } else if (actual == fin) {
                    fin = fin.ant;
                    fin.sig = inicio;
                    inicio.ant = fin;

                } else {
                    actual.ant.sig = actual.sig;
                    actual.sig.ant = actual.ant;
                }

                guardarEnTXT();

                JOptionPane.showMessageDialog(null,
                        "Producto eliminado exitosamente");

                return true;
            }

            actual = actual.sig;

        } while (actual != inicio);

        JOptionPane.showMessageDialog(null,
                "ID no encontrado");

        return false;
    }

    // --------------------- GUARDAR EN TXT ---------------------
    public void guardarEnTXT() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));

            if (!listaVacia()) {

                Nodo<Productos> actual = inicio;

                do {

                    writer.write("ID: " + actual.dato.idProd);
                    writer.newLine();
                    writer.write("Nombre: " + actual.dato.nombre);
                    writer.newLine();
                    writer.write("Precio: " + actual.dato.precio);
                    writer.newLine();
                    writer.write("Descripción: " + actual.dato.descripcion);
                    writer.newLine();

                   //nombre de la imagen
                    writer.write("Imagen: " + actual.dato.nombreImagen);
                  

                    writer.newLine();
                    writer.write("---------------------------");
                    writer.newLine();

                    actual = actual.sig;

                } while (actual != inicio);
            }

            writer.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar archivo: " + e.getMessage());
        }
    }

    // --------------------- CARGAR DESDE TXT ---------------------
    public void cargarDesdeTXT() {

        try {

            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            String id = "";
            String nombre = "";
            String precio = "";
            String descripcion = "";
            String imagen = "";

            while ((linea = reader.readLine()) != null) {

                if (linea.startsWith("ID: ")) {
                    id = linea.substring(4).trim();
                } else if (linea.startsWith("Nombre: ")) {
                    nombre = linea.substring(8).trim();
                } else if (linea.startsWith("Precio: ")) {
                    precio = linea.substring(8).trim();
                } else if (linea.startsWith("Descripción: ")) {
                    descripcion = linea.substring(13).trim();
                } else if (linea.startsWith("Imagen: ")) {
                    imagen = linea.substring(8).trim();
                }

                if (linea.equals("---------------------------")) {

                    int idNum = Integer.parseInt(id);
                    double precioNum = Double.parseDouble(precio);

                    
                    Productos p = new Productos(idNum, nombre, precioNum, descripcion, imagen);
                    Nodo<Productos> nuevo = new Nodo<>(p);

                    if (listaVacia()) {
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

                    id = nombre = precio = descripcion = imagen = "";
                }
            }

            reader.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar archivo: " + e.getMessage());
        }
    }
}