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
        private class NodoSinapsis {
            Sinapsis sinapsis;
            NodoSinapsis siguiente;

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

        /**
         * Remueve las conexiones dirigidas hacia una neurona destino específica.
         */
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

    /**
     * Busca una neurona por su identificador único dentro del arreglo interno.
     */
    public Neurona buscarNeurona(String id) {
        if (id == null) return null;
        for (int i = 0; i < cantidadNodos; i++) {
            if (nodos[i] != null && nodos[i].neurona.getId().equals(id)) {
                return nodos[i].neurona;
            }
        }
        return null;
    }

    /**
     * Agrega una neurona al grafo (Requerimiento G).
     * Si el arreglo se llena, se duplica su tamaño manualmente.
     */
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

    /**
     * Crea una conexión dirigida entre dos neuronas (Arista).
     */
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

    /**
     * Elimina una neurona y todas sus conexiones (Requerimiento G y Sub-criterio 3.1).
     * Compacta el arreglo interno para evitar dejar espacios nulos intermedios.
     */
    public void eliminarNeurona(String id) {
        if (id == null) return;
        int indiceEncontrado = -1;

        // 1. Localizar la posición de la neurona a eliminar
        for (int i = 0; i < cantidadNodos; i++) {
            if (nodos[i].neurona.getId().equals(id)) {
                indiceEncontrado = i;
                break;
            }
        }

        // Si la neurona no existe en el grafo, terminamos
        if (indiceEncontrado == -1) return;

        // 2. Limpar todas las sinapsis ENTRANTES desde los demás nodos
        for (int i = 0; i < cantidadNodos; i++) {
            if (i != indiceEncontrado) {
                nodos[i].adyacentes.removerConexionesA(id);
            }
        }

        // 3. Desplazar los elementos del arreglo para mantener la contigüidad física
        for (int i = indiceEncontrado; i < cantidadNodos - 1; i++) {
            nodos[i] = nodos[i + 1];
        }

        // Liberar la última posición duplicada y decrementar el contador
        nodos[cantidadNodos - 1] = null;
        cantidadNodos--;
    }

    public int getCantidadNodos() {
        return cantidadNodos;
    }
}