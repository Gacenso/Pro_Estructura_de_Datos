/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logicasinapsis.lector;

import com.logicasinapsis.estructuras.Grafo;
import com.logicasinapsis.estructuras.HashTable;
import com.logicasinapsis.modelo.Neurona;
import com.logicasinapsis.modelo.Neurotransmisor;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Clase encargada de la carga de datos desde archivos CSV (Requerimiento A).
 */
public class LectorCSV {

    /**
     * Carga el diccionario de neurotransmisores en la tabla hash.
     * @param rutaArchivo Ruta del CSV de neurotransmisores.
     * @param tabla La tabla hash donde se guardarán.
     */
    public void cargarDiccionario(String rutaArchivo, HashTable tabla) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            // Omitir cabecera si existe
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Estructura esperada: ID, Nombre, Efecto, Velocidad, Descripción
                Neurotransmisor nt = new Neurotransmisor(
                    datos[0], datos[1], datos[2], 
                    Double.parseDouble(datos[3]), datos[4]
                );
                tabla.insertar(nt.getId(), nt);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar diccionario: " + e.getMessage());
        }
    }

    /**
     * Carga las neuronas y sinapsis en el grafo.
     * @param rutaArchivo Ruta del CSV de la red.
     * @param red El grafo donde se cargarán los datos.
     */
    public void cargarRed(String rutaArchivo, Grafo red) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Lógica: Identificar si es definición de neurona o sinapsis
                // Y llamar a red.agregarNeurona() o red.conectar()
            }
        } catch (Exception e) {
            System.err.println("Error al cargar red: " + e.getMessage());
        }
    }
}