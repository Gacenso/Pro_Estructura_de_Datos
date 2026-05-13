/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logicasinapsis.estructuras;

import com.logicasinapsis.modelo.Neurotransmisor;

/**
 * Implementación propia de una Tabla Hash para el diccionario de neurotransmisores.
 * No utiliza librerías de Java Collections.
 */
public class HashTable {
    private NodoHash[] tabla;
    private int tamano;

    // Clase interna para el manejo de colisiones (Encadenamiento)
    private class NodoHash {
        String llave;
        Neurotransmisor valor;
        NodoHash siguiente;

        NodoHash(String llave, Neurotransmisor valor) {
            this.llave = llave;
            this.valor = valor;
        }
    }

    public HashTable(int capacidad) {
        this.tabla = new NodoHash[capacidad];
        this.tamano = 0;
    }

    /**
     * Función de dispersión para convertir la llave en un índice.
     */
    private int hash(String llave) {
        int hashAcumulado = 0;
        for (int i = 0; i < llave.length(); i++) {
            hashAcumulado = (31 * hashAcumulado + llave.charAt(i));
        }
        return Math.abs(hashAcumulado) % tabla.length;
    }

    /**
     * Inserta un neurotransmisor. Complejidad esperada O(1).
     */
    public void insertar(String llave, Neurotransmisor valor) {
        // Lógica para insertar o actualizar en la posición del hash
    }

    /**
     * Busca un neurotransmisor por su ID. Complejidad esperada O(1).
     */
    public Neurotransmisor buscar(String llave) {
        // Lógica para recorrer el nodo en la posición hash
        return null; 
    }

    public int getTamano() {
        return tamano;
    }
}