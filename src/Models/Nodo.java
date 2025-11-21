/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Usuario
 */
public class Nodo<N> {
    public N dato;
    public Nodo<N> sig, ant;

    public Nodo(N dato) {
        this.dato = dato;
        sig=ant=null;
    }
    
    public Nodo(){
        this.dato = null;
        sig=ant=null;
    }
}
