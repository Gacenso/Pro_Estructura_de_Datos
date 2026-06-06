/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicasinapsis.estructuras;

import logicasinapsis.modelo.Neurona;
import logicasinapsis.modelo.Sinapsis;

/**
 * Representa la red neuronal como un grafo dirigido.
 * Implementado mediante listas de adyacencia propias.
 */
public class Grafo {
    private NodoGrafo[] nodos;
    private int cantidadNodos;
    private int capacidad;

    // Clase interna para el nodo principal del arreglo
    private class NodoGrafo {
        Neurona neurona;
        ListaSinapsis adyacentes;

        NodoGrafo(Neurona n) {
            this.neurona = n;
            this.adyacentes = new ListaSinapsis();
        }
    }

    // ¡CAMBIO IMPORTANTE! Ahora es public para que la interfaz pueda iterarla
    public class ListaSinapsis {
        
        // ¡CAMBIO IMPORTANTE! Ahora es public y sus variables también
        public class NodoSinapsis {
            public Sinapsis sinapsis;
            public NodoSinapsis siguiente;

            NodoSinapsis(Sinapsis s) {
                this.sinapsis = s;
                this.siguiente = null;
            }
        }

        private NodoSinapsis cabeza;

        public void agregar(Sinapsis s) {
            NodoSinapsis nuevo = new NodoSinapsis(s);
            if (cabeza == null) {
                cabeza = nuevo;
            } else {
                NodoSinapsis actual = cabeza;
                while (actual.siguiente != null) {
                    actual = actual.siguiente;
                }
                actual.siguiente = nuevo;
            }
        }

        public void removerConexionesA(String idDestino) {
            while (cabeza != null && cabeza.sinapsis.getDestino().getId().equals(idDestino)) {
                cabeza = cabeza.siguiente;
            }
            if (cabeza == null) return;

            NodoSinapsis actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.siguiente.sinapsis.getDestino().getId().equals(idDestino)) {
                    actual.siguiente = actual.siguiente.siguiente;
                } else {
                    actual = actual.siguiente;
                }
            }
        }

        public NodoSinapsis getCabeza() {
            return cabeza;
        }
    }

    public Grafo(int capacidadInicial) {
        this.nodos = new NodoGrafo[capacidadInicial];
        this.cantidadNodos = 0;
        this.capacidad = capacidadInicial;
    }

    public Neurona buscarNeurona(String id) {
        if (id == null) return null;
        for (int i = 0; i < cantidadNodos; i++) {
            if (nodos[i] != null && nodos[i].neurona.getId().equals(id)) {
                return nodos[i].neurona;
            }
        }
        return null;
    }

    public void agregarNeurona(Neurona n) {
        if (n == null || buscarNeurona(n.getId()) != null) return;
        
        if (cantidadNodos >= capacidad) {
            capacidad *= 2;
            NodoGrafo[] nuevoArreglo = new NodoGrafo[capacidad];
            for (int i = 0; i < cantidadNodos; i++) {
                nuevoArreglo[i] = nodos[i];
            }
            nodos = nuevoArreglo;
        }
        
        nodos[cantidadNodos] = new NodoGrafo(n);
        cantidadNodos++;
    }

    public void conectar(String idOrigen, String idDestino, double distancia, String neurotransmisor, double k) {
        NodoGrafo nodoOrigen = null;
        Neurona neuronaDestino = null;

        for (int i = 0; i < cantidadNodos; i++) {
            if (nodos[i].neurona.getId().equals(idOrigen)) {
                nodoOrigen = nodos[i];
            }
            if (nodos[i].neurona.getId().equals(idDestino)) {
                neuronaDestino = nodos[i].neurona;
            }
        }

        if (nodoOrigen != null && neuronaDestino != null) {
            Sinapsis nuevaSinapsis = new Sinapsis(neuronaDestino, distancia, neurotransmisor, k);
            nodoOrigen.adyacentes.agregar(nuevaSinapsis);
        }
    }

    public void eliminarNeurona(String id) {
        if (id == null) return;
        int indiceEncontrado = -1;

        for (int i = 0; i < cantidadNodos; i++) {
            if (nodos[i].neurona.getId().equals(id)) {
                indiceEncontrado = i;
                break;
            }
        }

        if (indiceEncontrado == -1) return;

        for (int i = 0; i < cantidadNodos; i++) {
            if (i != indiceEncontrado) {
                nodos[i].adyacentes.removerConexionesA(id);
            }
        }

        for (int i = indiceEncontrado; i < cantidadNodos - 1; i++) {
            nodos[i] = nodos[i + 1];
        }

        nodos[cantidadNodos - 1] = null;
        cantidadNodos--;
    }

    public int getCantidadNodos() {
        return cantidadNodos;
    }

    // ==========================================================
    // MÉTODOS AÑADIDOS PARA LA INTERFAZ GRÁFICA Y ALGORITMOS
    // ==========================================================

    public Neurona getNeuronaEnPosicion(int indice) {
        if (indice >= 0 && indice < cantidadNodos) {
            return nodos[indice].neurona;
        }
        return null;
    }

    public ListaSinapsis.NodoSinapsis getPrimerNodoSinapsis(int indice) {
        if (indice >= 0 && indice < cantidadNodos) {
            return nodos[indice].adyacentes.getCabeza();
        }
        return null;
    }

    public int getIndicePorId(String id) {
        for (int i = 0; i < cantidadNodos; i++) {
            if (nodos[i].neurona.getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}