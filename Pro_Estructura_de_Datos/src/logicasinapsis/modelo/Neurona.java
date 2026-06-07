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

    /**
     * Crea una nueva instancia de Neurona.
     * @param id Identificador
     */
    public Neurona(String id) {
        this.id = id;
    }

    /**
     * Obtiene el id de la neurona.
     * @return id como String.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el id de la neurona.
     * @param id nuevo id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el String de la neurona.
     * @return String con identificadores
     */
    @Override
    public String toString() {
        return "Neurona{" + "id='" + id + '\'' + '}';
    }
}
