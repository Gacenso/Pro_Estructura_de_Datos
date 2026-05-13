/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logicasinapsis.algoritmos;

import com.logicasinapsis.estructuras.Grafo;
import com.logicasinapsis.estructuras.HashTable;
import com.logicasinapsis.modelo.Neurona;

/**
 * Implementación del algoritmo de Dijkstra para flujo de señal eléctrica.
 */
public class RutaDijkstra {

    /**
     * Calcula la ruta más rápida entre dos neuronas considerando v y k (Requerimiento E).
     * @param red El grafo de la red.
     * @param diccionario La Hash Table con los factores de velocidad v.
     * @param inicio ID neurona origen.
     * @param fin ID neurona destino.
     */
    public void calcularRutaMasRapida(Grafo red, HashTable diccionario, String inicio, String fin) {
        // 1. Inicializar distancias (pesos W) en infinito.
        // 2. Para cada arista, recuperar v de la Hash Table.
        // 3. Aplicar fórmula W = d / (v * k) para obtener el peso real.
        // 4. Utilizar una cola de prioridad (manual) para relajar las conexiones.
    }
}