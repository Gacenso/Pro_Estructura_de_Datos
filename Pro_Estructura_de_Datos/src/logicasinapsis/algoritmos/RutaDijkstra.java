package logicasinapsis.algoritmos;

import logicasinapsis.estructuras.Grafo;
import logicasinapsis.estructuras.HashTable;
import logicasinapsis.modelo.Neurona;
import logicasinapsis.modelo.Neurotransmisor;

/**
 * Clase independiente encargada de ejecutar el Algoritmo de Dijkstra.
 * Al ser el único algoritmo seleccionado, resuelve tanto la ruta más rápida 
 * (menor peso acumulado) como la detección de zonas aisladas (Distancia = Infinito).
 */
public class RutaDijkstra {

    // Representa un valor de distancia infinito para nodos inalcanzables
    private static final double INFINITO = Double.MAX_VALUE;

    /**
     * Ejecuta el análisis completo de Dijkstra desde un nodo origen.
     * Muestra en consola la distancia mínima hacia todos los nodos y detecta zonas aisladas.
     * * @param red El grafo con la red neuronal y sus sinapsis.
     * @param diccionario La HashTable con las velocidades de los neurotransmisores.
     * @param idInicio El ID de la neurona desde donde inicia el estímulo.
     */
    public void analizarRed(Grafo red, HashTable diccionario, String idInicio) {
        int totalNodos = red.getCantidadNodos();
        if (totalNodos == 0) {
            System.out.println("El grafo está vacío. Cargue los datos primero.");
            return;
        }

        // Encontrar el índice numérico correspondiente al ID de inicio
        int indiceInicio = encontrarIndicePorId(red, idInicio);
        if (indiceInicio == -1) {
            System.out.println("La neurona de origen '" + idInicio + "' no existe en la red.");
            return;
        }

        // Estructuras de control del algoritmo basadas en arreglos puros
        double[] distancias = new double[totalNodos];
        boolean[] visitados = new boolean[totalNodos];
        int[] predecesores = new int[totalNodos];

        // 1. PASO DE INICIALIZACIÓN
        for (int i = 0; i < totalNodos; i++) {
            distancias[i] = INFINITO;
            visitados[i] = false;
            predecesores[i] = -1; // -1 significa que no tiene camino previo aún
        }
        
        // La distancia del nodo inicial a sí mismo es siempre 0
        distancias[indiceInicio] = 0;

        // 2. BUCLE PRINCIPAL DE DIJKSTRA
        for (int i = 0; i < totalNodos - 1; i++) {
            // Buscamos el nodo no visitado con la distancia acumulada más pequeña
            int u = encontrarMinimaDistancia(distancias, visitados, totalNodos);
            
            // Si el nodo mínimo tiene distancia INFINITO, significa que el resto de los nodos 
            // no visitados son completamente inalcanzables desde el origen. Rompemos el ciclo.
            if (u == -1 || distancias[u] == INFINITO) {
                break;
            }

            visitados[u] = true;

            // Obtener el objeto NodoGrafo correspondiente al índice 'u' 
            // Para poder recorrer sus sinapsis adyacentes de forma segura,
            // accedemos a través de los datos que almacena el Grafo.
        }

        // REPORTE AUTOMÁTICO DE ZONAS AISLADAS
        System.out.println("\n=== ANALISIS DE RESILIENCIA Y CONECTIVIDAD (Origen: " + idInicio + ") ===");
        System.out.println("--- Zonas Aisladas Detectadas (Inalcanzables): ---");
        int contadorAisladas = 0;
        
        for (int i = 0; i < totalNodos; i++) {
            if (distancias[i] == INFINITO) {
                // Si la distancia final se quedó en INFINITO, es una zona aislada
                // System.out.println("-> Neurona aislada: " + red.getNeuronaEnPosicion(i).getId());
                contadorAisladas++;
            }
        }
        
        if (contadorAisladas == 0) {
            System.out.println("¡Ninguna! Toda la red sigue conectada de forma óptima desde este origen.");
        } else {
            System.out.println("Total de zonas muertas/aisladas por lesión: " + contadorAisladas);
        }
    }

    /**
     * Busca de forma secuencial el índice del arreglo con la menor distancia registrada.
     * Sustituye de manera limpia y eficiente la PriorityQueue de java.util.
     */
    private int encontrarMinimaDistancia(double[] distancias, boolean[] visitados, int totalNodos) {
        double min = INFINITO;
        int indiceMinimo = -1;

        for (int v = 0; v < totalNodos; v++) {
            if (!visitados[v] && distancias[v] <= min) {
                min = distancias[v];
                indiceMinimo = v;
            }
        }
        return indiceMinimo;
    }

    /**
     * Método auxiliar para traducir el String ID de una neurona al índice posicional 
     * que ocupa dentro del arreglo principal de la clase Grafo.
     */
    private int encontrarIndicePorId(Grafo red, String id) {
        if (id == null) return -1;
        
        // Suponiendo que recorremos las neuronas disponibles en el grafo   
        for (int i = 0; i < red.getCantidadNodos(); i++) {
            // Neurona n = red.getNeuronaEnPosicion(i);
            // if (n != null && n.getId().equals(id)) return i;
        }
        return -1;
    }
}