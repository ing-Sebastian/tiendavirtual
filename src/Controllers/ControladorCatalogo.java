/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import Models.Nodo;
import Models.Productos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author sebas, samuel, mayra
 */
public class ControladorCatalogo {
    
    String rutaArchivo = System.getProperty("user.dir") + "/txtFiles/productos.txt";
    
    //lista Productos
    public Nodo<Productos> inicio;
    public Nodo<Productos> fin;
    
    public ControladorCatalogo() {
        inicio = null;
        fin = null;
        cargarDesdeTXT();
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
    
    public int tamaño(){
        int cant = 0;
        if (listaVacia())
            return 0;
        else{
            Nodo<Productos> actual = inicio;
            do {
                cant++;
                actual = actual.sig;
            } while (actual != inicio);
        }
        return cant;
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

    //metodos txt
    //guardar
    public void guardarEnTXT() {
        if (listaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay productos para guardar");
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));

            Nodo<Productos> p = inicio;

            do {
                writer.write("ID: " + p.dato.idProd);
                writer.newLine();
                writer.write("Nombre: " + p.dato.nombre);
                writer.newLine();
                writer.write("Precio: " + p.dato.precio);
                writer.newLine();
                writer.write("Descripción: " + p.dato.descripcion);
                writer.newLine();
                writer.write("Imagen: " + p.dato.nombreImagen);
                writer.newLine();
                writer.write("---------------------------");
                writer.newLine();

                p = p.sig;
            } while (p != inicio);

            writer.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
        }
    }

    //metodo cargar
    public void cargarDesdeTXT() {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            String id = "", nombre = "", precio = "", descripcion = "", imagen = "";

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

                    Productos prod = new Productos(idNum, nombre, precioNum, descripcion, imagen);
                    Nodo<Productos> nodo = new Nodo<>(prod);

                    if (listaVacia()) {
                        inicio = fin = nodo;
                        nodo.sig = nodo.ant = nodo;
                    } else {
                        nodo.ant = fin;
                        nodo.sig = inicio;
                        fin.sig = nodo;
                        inicio.ant = nodo;
                        fin = nodo;
                    }

                    id = nombre = precio = descripcion = imagen = "";
                }
            }

            reader.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar: " + e.getMessage());
        }
    }

    //buscar 
    public void buscarEnTXT(int idBuscar) {

        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(null, "No existe el archivo");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            boolean encontrado = false;
            String datos = "";

            while ((linea = reader.readLine()) != null && !encontrado) {

                if (linea.equals("ID: " + idBuscar)) {
                    encontrado = true;
                    datos += linea + "\n";

                    while ((linea = reader.readLine()) != null
                            && !linea.equals("---------------------------")) {
                        datos += linea + "\n";
                    }
                }
            }

            reader.close();

            if (encontrado) {
                JOptionPane.showMessageDialog(null, datos);
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar: " + e.getMessage());
        }
    }

    //mostrar todos los productos
    public void listarTXT() {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(null, "No existe el archivo");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            String datos = "";

            while ((linea = reader.readLine()) != null) {
                datos += linea + "\n";
            }

            reader.close();
            JOptionPane.showMessageDialog(null, datos);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar: " + e.getMessage());
        }
    }

    //eliiminar
    public void eliminarEnTXT(int idEliminar) {

        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(null, "No existe el archivo");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            StringBuilder nuevoContenido = new StringBuilder();
            boolean encontrado = false;
            boolean saltar = false;

            while ((linea = reader.readLine()) != null) {

                //detecta el producto a borrar
                if (linea.equals("ID: " + idEliminar)) {
                    encontrado = true;
                    saltar = true; //se saltan todas sus líneas
                    continue;
                }

                //fin del bloque del producto
                if (saltar && linea.equals("---------------------------")) {
                    saltar = false;
                    continue;
                }

                // si no se salta, s4e copia
                if (!saltar) {
                    nuevoContenido.append(linea).append("\n");
                }
            }

            reader.close();

            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
                return;
            }

            //reescribir el archivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(nuevoContenido.toString());
            writer.close();

            JOptionPane.showMessageDialog(null, "Producto eliminado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }

    //edtar, ppor sii acaso
    public void editarEnTXT(int idEditar, String nuevoNombre, double nuevoPrecio, String nuevaDesc, String nuevaImagen) {

        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(null, "No existe el archivo");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            StringBuilder nuevoArchivo = new StringBuilder();
            boolean encontrado = false;
            boolean saltar = false;

            while ((linea = reader.readLine()) != null) {

                if (linea.equals("ID: " + idEditar)) {
                    encontrado = true;
                    saltar = true;
                    // agregamos el producto EDITADO directamente
                    nuevoArchivo.append("ID: ").append(idEditar).append("\n");
                    nuevoArchivo.append("Nombre: ").append(nuevoNombre).append("\n");
                    nuevoArchivo.append("Precio: ").append(nuevoPrecio).append("\n");
                    nuevoArchivo.append("Descripción: ").append(nuevaDesc).append("\n");
                    nuevoArchivo.append("Imagen: ").append(nuevaImagen).append("\n");
                    nuevoArchivo.append("---------------------------\n");
                    continue;
                }

                if (saltar && linea.equals("---------------------------")) {
                    saltar = false;
                    continue;
                }

                if (!saltar) {
                    nuevoArchivo.append(linea).append("\n");
                }
            }

            reader.close();

            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
                return;
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(nuevoArchivo.toString());
            writer.close();

            JOptionPane.showMessageDialog(null, "Producto editado correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar: " + e.getMessage());
        }
    }
    
}
