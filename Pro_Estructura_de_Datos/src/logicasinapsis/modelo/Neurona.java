/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicasinapsis.modelo;

/**
 * Representa una neurona (nodo) en la red sináptica.
 */
public class Neurona {
    private String id; // Puede ser String o Integer según el dataset 

    public Neurona(String id) {
        this.id = id;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Neurona{" + "id='" + id + '\'' + '}';
    }
}
