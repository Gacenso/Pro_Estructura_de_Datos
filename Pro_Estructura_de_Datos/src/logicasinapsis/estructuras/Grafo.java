/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logicasinapsis.estructuras;

import com.logicasinapsis.modelo.Neurona;
import com.logicasinapsis.modelo.Sinapsis;

/**
 * Representa la red neuronal como un grafo dirigido.
 * Implementado mediante listas de adyacencia propias.
 */
public class Grafo {
    private NodoGrafo[] nodos;
    private int cantidadNodos;
    private int capacidad;

    // Clase interna para representar un nodo y su lista de sinapsis (aristas)
    private class NodoGrafo {
        Neurona neurona;
        ListaSinapsis adyacentes; // Lista enlazada de objetos Sinapsis

        NodoGrafo(Neurona n) {
            this.neurona = n;
            this.adyacentes = new ListaSinapsis();
        }
    }

    // Clase interna para manejar la lista de aristas sin usar java.util.ArrayList
    private class ListaSinapsis {
        // Implementación de lista enlazada simple para guardar las Sinapsis
    }

    public Grafo(int capacidadInicial) {
        this.nodos = new NodoGrafo[capacidadInicial];
        this.cantidadNodos = 0;
        this.capacidad = capacidadInicial;
    }

    /**
     * Agrega una neurona al grafo (Requerimiento G).
     */
    public void agregarNeurona(Neurona n) {
        // Lógica para añadir al arreglo 'nodos'
    }

    /**
     * Crea una conexión dirigida entre dos neuronas (Arista).
     */
    public void conectar(String idOrigen, String idDestino, double distancia, String neurotransmisor, double k) {
        // Lógica para buscar el nodo origen y añadir una Sinapsis a su lista
    }

    /**
     * Elimina una neurona y todas sus conexiones (Requerimiento G).
     */
    public void eliminarNeurona(String id) {
        // Lógica de eliminación consistente (Requerimiento C)
    }

    public NodoGrafo[] getNodos() {
        return nodos;
    }
}