/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Usuario
 */
public class Usuario {
    public int idUser;
    public String nombre;
    public String correo;
    public String contraseña;

    public Usuario(int idUser, String nombre, String correo, String contraseña) {
        this.idUser=idUser;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
    }
    
    
}
