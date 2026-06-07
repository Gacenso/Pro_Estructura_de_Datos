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
 * Clase encargada de la carga de datos desde archivos CSV de forma robusta.
 */
public class LectorCSV {

    /**
     * Carga el diccionario de neurotransmisores en la tabla hash.
     * @param rutaArchivo   Ruta del archivo CSV que contiene los neurotransmisores.
     * @param tabla         Instancia del HashTable donde se almacenarán los neurotransmisores.   
     */
    public void cargarDiccionario(String rutaArchivo, HashTable tabla) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esCabecera = true;
            int numeroLinea = 0;
            
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) continue;
                
                // Evitar procesar cabeceras de columnas
                if (esCabecera) {
                    esCabecera = false;
                    if (linea.toLowerCase().contains("id") || linea.toLowerCase().contains("nombre")) {
                        continue;
                    }
                }
                
                // Tolerancia a fallos interna para que no se detenga si hay una fila mala (o archivo equivocado)
                try {
                    String[] datos = linea.split("[,;]"); // Acepta comas o punto y coma de Excel
                    
                    if (datos.length >= 5) {
                        String id = datos[0].trim();
                        String nombre = datos[1].trim();
                        String efecto = datos[2].trim();
                        double velocidad = Double.parseDouble(datos[3].trim()); // Aquí espera el número
                        String descripcion = datos[4].trim();
                        
                        Neurotransmisor nt = new Neurotransmisor(id, nombre, efecto, velocidad, descripcion);
                        tabla.insertar(id, nt);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Aviso: Formato incorrecto en la línea " + numeroLinea + " del diccionario. Fila ignorada.");
                }
            }
            System.out.println("SynapseLogic: Diccionario cargado con éxito.");
            
        } catch (IOException e) {
            System.err.println("Error al cargar diccionario: " + e.getMessage());
        }
    }

    /**
     * Carga las neuronas y sinapsis en el grafo de manera coordinada.
     */
    public void cargarRed(String rutaArchivo, Grafo red) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esCabecera = true;
            int numeroLinea = 0; 
            
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) continue; 
                
                if (esCabecera) {
                    esCabecera = false;
                    String lineaMinuscula = linea.toLowerCase();
                    if (lineaMinuscula.contains("origen") || lineaMinuscula.contains("destino") || lineaMinuscula.contains("id")) {
                        continue; 
                    }
                }
                
                try {
                    String[] datos = linea.split("[,;]");
                    
                    if (datos.length >= 5) {
                        String idOrigen = datos[0].trim();
                        String idDestino = datos[1].trim();
                        
                        double distancia = Double.parseDouble(datos[2].trim());
                        String idNeurotransmisor = datos[3].trim();
                        double k = Double.parseDouble(datos[4].trim());
                        
                        // Asegurar la existencia de las neuronas en el arreglo interno
                        if (red.buscarNeurona(idOrigen) == null) {
                            red.agregarNeurona(new Neurona(idOrigen));
                        }
                        if (red.buscarNeurona(idDestino) == null) {
                            red.agregarNeurona(new Neurona(idDestino));
                        }
                        
                        // Añadir la sinapsis dirigida al grafo
                        red.conectar(idOrigen, idDestino, distancia, idNeurotransmisor, k);
                        
                    } else {
                        System.err.println("Advertencia [Línea " + numeroLinea + "]: Columnas insuficientes. Fila omitida.");
                    }
                    
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en la línea " + numeroLinea + " de la Red. Se omitió esta conexión.");
                }
            }
            
            System.out.println("SynapseLogic: Datos de la red cargados con éxito en memoria.");
            
        } catch (IOException e) {
            System.err.println("Error crítico: No se pudo abrir o leer el archivo CSV: " + e.getMessage());
        }
    }
    public void guardarRedCSV(String rutaArchivo, Grafo red) {
        if (red == null || red.getCantidadNodos() == 0) {
            System.err.println("No hay datos en la red para guardar.");
            return;
        }

        // Usamos BufferedWriter para escribir línea por línea de forma eficiente
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(rutaArchivo))) {
            // 1. Escribir la cabecera exacta del formato reglamentario
            bw.write("Neurona_Origen,Neurona_Destino,Distancia,Neurotransmisor,Eficiencia_k");
            bw.newLine();

            // 2. Recorrer el grafo para extraer todas las sinapsis existentes
            for (int i = 0; i < red.getCantidadNodos(); i++) {
                logicasinapsis.modelo.Neurona origen = red.getNeuronaEnPosicion(i);
                if (origen == null) continue;

                Grafo.ListaSinapsis.NodoSinapsis actual = red.getPrimerNodoSinapsis(i);
                while (actual != null) {
                    String idDestino = actual.sinapsis.getDestino().getId();
                    double distancia = actual.sinapsis.getDistancia();
                    String nt = actual.sinapsis.getIdNeurotransmisor();
                    double k = actual.sinapsis.getK();

                    // Formatear la línea separada por comas
                    String linea = String.format("%s,%s,%.2f,%s,%.2f", 
                            origen.getId(), idDestino, distancia, nt, k);
                    
                    bw.write(linea);
                    bw.newLine();
                    
                    actual = actual.siguiente;
                }
            }
            System.out.println("SynapseLogic: Cambios de la red guardados exitosamente en CSV.");
            
        } catch (java.io.IOException e) {
            System.err.println("Error crítico al intentar guardar el archivo CSV: " + e.getMessage());
        }
    }
}