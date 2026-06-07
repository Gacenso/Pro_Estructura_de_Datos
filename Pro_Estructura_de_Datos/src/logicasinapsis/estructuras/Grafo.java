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

    /**
     * Clase interna para el nodo principal del arreglo.
     */
    private class NodoGrafo {
        Neurona neurona;
        ListaSinapsis adyacentes;

        NodoGrafo(Neurona n) {
            this.neurona = n;
            this.adyacentes = new ListaSinapsis();
        }
    }

    /**
     * Implementación de una lista enlazada simple para gestionar las adyacencias.
     */
    public class ListaSinapsis {
        
        /**
         * Nodo individual de la lista.
         */
        public class NodoSinapsis {
            public Sinapsis sinapsis;
            public NodoSinapsis siguiente;

            NodoSinapsis(Sinapsis s) {
                this.sinapsis = s;
                this.siguiente = null;
            }
        }

        private NodoSinapsis cabeza;

        /**
         * Agrega una nueva sinapsis al final de la lista de adyacencia.
         * @param s La conexión sináptica a insertar.
         */
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
         * Recorre la lista y elimina cualquier sinapsis que apunte a la
         * neurona destino especificada.
         * @param idDestino id de la neurona que se eliminará.
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

        /**
         * Obtiene el primero nodo de la lista enlazada.
         * @return Nodo inicial de la lista.
         */
        public NodoSinapsis getCabeza() {
            return cabeza;
        }
    }

    /**
     * Crea un nuevo grafo con la capacidad inicial dada.
     * @param capacidadInicial El tamaño inicial del arreglo de vértices.
     */
    public Grafo(int capacidadInicial) {
        this.nodos = new NodoGrafo[capacidadInicial];
        this.cantidadNodos = 0;
        this.capacidad = capacidadInicial;
    }

    /**
     * Busca una neurona dentro del grafo utilizando su id.
     * @param id id de la neurona que se busca.
     * @return Neurona buscada o null si no existe.
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
     * Agrega una nueva neurona al grafo.
     * @param n Neurona a insertart. Se ignorara si es null o ya existente.
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
     * Crea una conexión entre dos neuronas en el grafo.
     * @param idOrigen          id de la neurona origen.
     * @param idDestino         id de la neurona destino.
     * @param distancia         Distancia de la conexión.
     * @param neurotransmisor   Código del neurotransmisor.
     * @param k                 Coeficiente de eificiencia.
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
     * Elimina una neurona del grafo y todas las sinapsis asociadas.
     * @param id id de la neurona a eliminar.
     */
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

    /**
     * Obtiene la cantidad actual de neuronas en el grafo.
     * @return La cantidad de nodos activos.
     */
    public int getCantidadNodos() {
        return cantidadNodos;
    }

    /**
     * Obtiene una neurona basándose en su índice físico dentro 
     * del arreglo interno.
     * @param indice La posición de la neurona en el arreglo.
     * @return La neurona en dicha posición, o null si no se encuentra
     */
    public Neurona getNeuronaEnPosicion(int indice) {
        if (indice >= 0 && indice < cantidadNodos) {
            return nodos[indice].neurona;
        }
        return null;
    }

    /**
     * Obtiene el primer nodo de la lista de adyacencia.
     * @param indice La posición de la neurona origen en el arreglo.
     * @return La cabeza de la lista de conexiones, o null si no se encuentra.
     */
    public ListaSinapsis.NodoSinapsis getPrimerNodoSinapsis(int indice) {
        if (indice >= 0 && indice < cantidadNodos) {
            return nodos[indice].adyacentes.getCabeza();
        }
        return null;
    }

    /**
     * Busca la posición física de una neurona dentro del arreglo utilizando
     * el id.
     * @param id id de la neurona
     * @return Índice en el arreglo, o -1 si no existe.
     */
    public int getIndicePorId(String id) {
        for (int i = 0; i < cantidadNodos; i++) {
            if (nodos[i].neurona.getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}