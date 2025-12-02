/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Nodo;
import Models.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class ControllerUsuario {
    
    //txt ruta general
    String rutaArchivo = System.getProperty("user.dir") + "/txtFiles/usuarios.txt";
    
    public Nodo<Usuario> inicio;
    public Nodo<Usuario> fin;

    public ControllerUsuario() {
        this.inicio = null;
        this.fin = null;
        //cargar txt al iniciar
        cargarDesdeTXT();
        
    }

    public boolean listaVacia() {
        return inicio == null;
    }
    
    public Usuario buscarPorCorreo(String correo) {
        if (listaVacia()) 
            return null;
        Nodo<Usuario> actual = inicio;
        do{
            if (actual.dato.correo.equals(correo)) 
                return actual.dato;
            actual = actual.sig;
        } while (actual != inicio);
        return null;
    }
    
    public Usuario buscarPorId(int idUser) {

        if (listaVacia()) {
            return null;
        }

        Nodo<Usuario> actual = inicio;

        do {
            if (actual.dato.idUser == idUser) {
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
            Nodo<Usuario> actual = inicio;
            do {
                cant++;
                actual = actual.sig;
            } while (actual != inicio);
        }
        return cant;
    }
    
    //metodo registrar usuario
    public boolean registrarUsuario(TextField jtfNombre, TextField jtfCorreo, TextField jtfContraseña) {

        try {
            //validaciones, trim es para quitar espacios vacios
            if (jtfNombre.getText().trim().isEmpty()
                    || jtfCorreo.getText().trim().isEmpty()
                    || jtfContraseña.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios");
                return false;
            }

            String nombre = jtfNombre.getText().trim();
            String correo = jtfCorreo.getText().trim();
            String contraseña = jtfContraseña.getText().trim();

            if (buscarPorCorreo(correo) != null) {
                JOptionPane.showMessageDialog(null,
                        "Este correo ya está registrado");
                jtfCorreo.setText("");
                jtfCorreo.requestFocus();
                return false;
            }

            //id autoincrementado corregido
            int nuevoID;
            if (listaVacia()) {
                nuevoID = 1;
            } else {
                nuevoID = fin.dato.idUser + 1;
            }
            
            //se crea el user
            Usuario nuevo = new Usuario(nuevoID, nombre, correo, contraseña);
            Nodo<Usuario> nuevoNodo = new Nodo<>(nuevo);

            //se mete en la lista, ojito
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
                    "Usuario registrado exitosamente");
            guardarEnTXT();

            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage()
                    + "\nVerifique los datos ingresados");
            return false;
        }
    }
    
    //eliminar por id
    public boolean eliminarPorId(int idUser) {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null,
                    "No hay usuarios registrados");
            return false;
        }

        Nodo<Usuario> actual = inicio;

        do {
            if (actual.dato.idUser == idUser) {

                //uno
                if (actual == inicio && actual == fin) {
                    inicio = null;
                    fin = null;
                } // bora iinicio
                else if (actual == inicio) {
                    inicio = inicio.sig;
                    inicio.ant = fin;
                    fin.sig = inicio;
                } // Borra final
                else if (actual == fin) {
                    fin = fin.ant;
                    fin.sig = inicio;
                    inicio.ant = fin;
                } // borrado normal
                else {
                    actual.ant.sig = actual.sig;
                    actual.sig.ant = actual.ant;
                }

                JOptionPane.showMessageDialog(null,
                        "Usuario eliminado");
                return true;
            }

            actual = actual.sig;

        } while (actual != inicio);

        JOptionPane.showMessageDialog(null,
                "ID no encontrado");
        return false;
    }
    
    
    //eliminar por corre
     public boolean eliminarPorCorreo(String correo) {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null,
                    "No hay usuarios registrados");
            return false;
        }

        Nodo<Usuario> actual = inicio;

        do {
            if (actual.dato.correo.equals(correo)) {

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
                        "Usuario eliminado");
                return true;
            }

            actual = actual.sig;

        } while (actual != inicio);

        JOptionPane.showMessageDialog(null,
                "Correo no encontrado");
        return false;
    }
    
    //metodo para mostrar usuarios
    public void mostrarUsuarios() {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
            return;
        }

        String mensaje = "LISTA DE USUARIOS:\n\n";
        Nodo<Usuario> actual = inicio;

        do {
            mensaje += "ID: " + actual.dato.idUser + "\n"
                    + "Nombre: " + actual.dato.nombre + "\n"
                    + "Correo: " + actual.dato.correo + "\n"
                    + "Contraseña: " + actual.dato.contraseña + "\n"
                    + "------------------------------\n";//se puede mejorar pues
            actual = actual.sig;
        } while (actual != inicio);

        JOptionPane.showMessageDialog(null, mensaje);
    }
    
    //el que valida el user y contra para dar paso 
    public Usuario login(String correo, String contraseña) {
        if (listaVacia()) {
            return null;
        }

        //parsea lo ingresado
        correo = correo.trim().toLowerCase();
        contraseña = contraseña.trim();

        Nodo<Usuario> actual = inicio;

        do {
            //parsea de la lista
            String correoUser = actual.dato.correo.trim().toLowerCase();
            String contraUser = actual.dato.contraseña.trim();

            if (correoUser.equals(correo) && contraUser.equals(contraseña)) {
                return actual.dato; //lo encontro
            }

            actual = actual.sig;
        } while (actual != inicio);

        return null; //no encontrado
    }
    
    //metodos txt
    //metodo guardar en txt
    public void guardarEnTXT() {
        if (listaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios para guardar");
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));

            writer.write("USUARIOS REGISTRADOS");
            writer.newLine();
            writer.write("===================");
            writer.newLine();
            writer.newLine();

            Nodo<Usuario> p = inicio;

            do {
                writer.write("ID: " + p.dato.idUser);
                writer.newLine();
                writer.write("Nombre: " + p.dato.nombre);
                writer.newLine();
                writer.write("Correo: " + p.dato.correo);
                writer.newLine();
                writer.write("Contraseña: " + p.dato.contraseña);
                writer.newLine();
                writer.write("---------------------------");
                writer.newLine();
                writer.newLine();

                p = p.sig;
            } while (p != inicio);

            writer.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
        }
    }

    //cargar txt
    public void cargarDesdeTXT() {
        try {
            /*
            if (!rutaArchivo.exists()) {
                return; // Sale del método inmediatamente
            }*/

            BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
            String linea;

            String id = "", nombre = "", correo = "", contra = "";

            while ((linea = reader.readLine()) != null) {

                linea = linea.trim(); // limpiar espacios y saltos de línea

                if (linea.startsWith("ID: ")) {
                    id = linea.substring(4).trim();
                } else if (linea.startsWith("Nombre: ")) {
                    nombre = linea.substring(8).trim();
                } else if (linea.startsWith("Correo: ")) {
                    correo = linea.substring(8).trim();
                } else if (linea.startsWith("Contraseña: ")) {
                    contra = linea.substring(12).trim();
                }

                if (linea.equals("---------------------------")) {

                    if (!id.isEmpty() && !nombre.isEmpty() && !correo.isEmpty() && !contra.isEmpty()) {
                        int idNum = Integer.parseInt(id.trim());
                        Usuario user = new Usuario(idNum, nombre.trim(), correo.trim(), contra.trim());
                        Nodo<Usuario> nodo = new Nodo<>(user);

                        if (listaVacia()) {
                            inicio = nodo;
                            fin = nodo;
                            nodo.sig = nodo;
                            nodo.ant = nodo;
                        } else {
                            nodo.ant = fin;
                            nodo.sig = inicio;
                            fin.sig = nodo;
                            inicio.ant = nodo;
                            fin = nodo;
                        }
                    }

                    id = nombre = correo = contra = "";//reset para el next
                }
            }

            reader.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar archivo: " + e.getMessage());
        }
    }

    //eliminar un regis por correo
    public void setEliminarDelArchivo(String correoEliminar) {
        try {
            java.io.File archivoOriginal = new java.io.File("txtFiles/usuarios.txt");
            java.io.File archivoTemp = new java.io.File("txtFiles/temp_usuarios.txt");

            if (!archivoOriginal.exists()) {
                JOptionPane.showMessageDialog(null, "El archivo no existe.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivoOriginal));
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemp));

            String linea;
            boolean encontrado = false;
            boolean saltarRegistro = false;

            while ((linea = reader.readLine()) != null) {

                // encontramos el correo que queremos eliminar
                if (linea.equals("Correo: " + correoEliminar)) {
                    encontrado = true;
                    saltarRegistro = true;
                }

                // mientras no este saltando el registro, se escribe el archivo temporal
                if (!saltarRegistro) {
                    writer.write(linea);
                    writer.newLine();
                }

                if (saltarRegistro && linea.equals("---------------------------")) {
                    saltarRegistro = false;
                }
            }

            reader.close();
            writer.close();

            // aca reemplaza el archivo original con el nuevo temp
            if (archivoOriginal.delete()) {
                archivoTemp.renameTo(archivoOriginal);

                if (encontrado) {
                    JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el usuario en el archivo.");
                    archivoTemp.delete(); // borra ekl archivo temporal si no se uso
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
        }
    }

    //buscar txt
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
                JOptionPane.showMessageDialog(null, "No encontrado en archivo");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al buscar en archivo: " + e.getMessage());
        }
    }

}
