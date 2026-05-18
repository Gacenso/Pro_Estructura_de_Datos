/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
     * Inserta un neurotransmisor. Si la llave ya existe, actualiza su valor.
     * Si hay una colisión de índice, la maneja mediante encadenamiento.
     * Complejidad esperada O(1).
     */
    public void insertar(String llave, Neurotransmisor valor) {
        if (llave == null) return;
        
        int indice = hash(llave);
        NodoHash actual = tabla[indice];
        
        // 1. Verificar si la llave ya existe en la lista enlazada para actualizarla
        while (actual != null) {
            if (actual.llave.equals(llave)) {
                actual.valor = valor; // Se sobreescribe con la nueva información
                return;
            }
            actual = actual.siguiente;
        }
        
        // 2. Si la llave no existía, creamos un nuevo nodo
        NodoHash nuevoNodo = new NodoHash(llave, valor);
        
        // Manejo de colisión por encadenamiento: insertamos al inicio de la lista
        nuevoNodo.siguiente = tabla[indice];
        tabla[indice] = nuevoNodo;
        
        tamano++;
    }

    /**
     * Busca un neurotransmisor por su ID recorriendo los nodos encadenados.
     * Complejidad esperada O(1).
     */
    public Neurotransmisor buscar(String llave) {
        if (llave == null) return null;
        
        int indice = hash(llave);
        NodoHash actual = tabla[indice];
        
        // Recorrer la lista enlazada en esa posición del arreglo
        while (actual != null) {
            if (actual.llave.equals(llave)) {
                return actual.valor; // Encontrado
            }
            actual = actual.siguiente;
        }
        
        return null; // No se encuentra en la tabla
    }

    public int getTamano() {
        return tamano;
    }
}