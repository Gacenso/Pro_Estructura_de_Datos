/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicasinapsis.lector;

import logicasinapsis.estructuras.Grafo;
import logicasinapsis.estructuras.HashTable;
import logicasinapsis.modelo.Neurona;
import logicasinapsis.modelo.Neurotransmisor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase encargada de la carga de datos desde archivos CSV.
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
            boolean esCabecera = true;
            
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                // Evitar procesar cabeceras de columnas
                if (esCabecera) {
                    esCabecera = false;
                    if (linea.toLowerCase().contains("id") || linea.toLowerCase().contains("nombre")) {
                        continue;
                    }
                }
                
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    String id = datos[0].trim();
                    String nombre = datos[1].trim();
                    String efecto = datos[2].trim();
                    double velocidad = Double.parseDouble(datos[3].trim());
                    String descripcion = datos[4].trim();
                    
                    Neurotransmisor nt = new Neurotransmisor(id, nombre, efecto, velocidad, descripcion);
                    tabla.insertar(id, nt);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar diccionario: " + e.getMessage());
        }
    }

    /**
     * Carga las neuronas y sinapsis en el grafo de manera coordinada.
     * @param rutaArchivo Ruta del CSV de la red.
     * @param red El grafo donde se cargarán los datos.
     */
    public void cargarRed(String rutaArchivo, Grafo red) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esCabecera = true;
            
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                if (esCabecera) {
                    esCabecera = false;
                    if (linea.toLowerCase().contains("origen") || linea.toLowerCase().contains("destino")) {
                        continue;
                    }
                }
                
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    String idOrigen = datos[0].trim();
                    String idDestino = datos[1].trim();
                    double distancia = Double.parseDouble(datos[2].trim());
                    String idNeurotransmisor = datos[3].trim();
                    double k = Double.parseDouble(datos[4].trim());
                    
                    // Asegurar la existencia de las neuronas en el arreglo interno antes de conectarlas
                    if (red.buscarNeurona(idOrigen) == null) {
                        red.agregarNeurona(new Neurona(idOrigen));
                    }
                    if (red.buscarNeurona(idDestino) == null) {
                        red.agregarNeurona(new Neurona(idDestino));
                    }
                    
                    // Añadir la sinapsis dirigida al grafo
                    red.conectar(idOrigen, idDestino, distancia, idNeurotransmisor, k);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar la red sináptica: " + e.getMessage());
        }
    }
}