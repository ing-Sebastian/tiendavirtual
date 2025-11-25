/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Nodo;
import Models.Usuario;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class ControllerUsuario {
    
    public Nodo<Usuario> inicio;
    public Nodo<Usuario> fin;

    public ControllerUsuario() {
        this.inicio = null;
        this.fin = null;
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

            //se crea el user
            Usuario nuevo = new Usuario(nombre, correo, contraseña);
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

            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage()
                    + "\nVerifique los datos ingresados");
            return false;
        }
    }
    
    /*
    //eliminar por corre
     public boolean eliminarPorCorreo(String correo) {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null,
                    "No hay usuarios registrados");
            return false;
        }

        Nodo<Usuarios> actual = inicio;

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
     */
    
    /*
    //metodo para mostrar usuarios
    public void mostrarUsuarios() {

        if (listaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
            return;
        }

        String mensaje = "LISTA DE USUARIOS:\n\n";
        Nodo<Usuarios> actual = inicio;

        do {
            mensaje += "Nombre: " + actual.dato.nombre + "\n"
                    + "Correo: " + actual.dato.correo + "\n"
                    + "Contraseña: " + actual.dato.contraseña + "\n"
                    + "------------------------------\n";//se puede mejorar pues
            actual = actual.sig;
        } while (actual != inicio);

        JOptionPane.showMessageDialog(null, mensaje);
    }
    */
    
    //creo que metodo final, el que valida el user y contra para dar paso 
    public Usuario login(String correo, String contraseña) {
        if (listaVacia()) 
            return null;
        Nodo<Usuario> actual = inicio;
        do {
            if (actual.dato.correo.equals(correo)
                    && actual.dato.contraseña.equals(contraseña)) {
                return actual.dato;
            }
            actual = actual.sig;
        } while (actual != inicio);

        return null;
    }

}
