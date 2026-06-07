package logicasinapsis.algoritmos;

import logicasinapsis.estructuras.Grafo;
import logicasinapsis.estructuras.HashTable;
import logicasinapsis.modelo.Neurotransmisor;

/**
 * Clase independiente encargada de ejecutar el Algoritmo de Dijkstra.
 * Devuelve reportes en texto para ser inyectados en la interfaz Swing.
 */
public class RutaDijkstra {

    private static final double INFINITO = Double.MAX_VALUE;

    /**
     * Ejecuta Dijkstra para encontrar las rutas más rápidas de transmisión.
     * @param red           Grafo dirigido que representa la red sináptica.
     * @param diccionario   Tabla Hash que contiene los neurotransmisores y sus velocidades.
     * @param idInicio      Id de la neurona origen del estímulo.
     * @return String con el diagnóstico y desglose de rutas.
     */
    public String analizarRed(Grafo red, HashTable diccionario, String idInicio) {
        int totalNodos = red.getCantidadNodos();
        if (totalNodos == 0) {
            return "El grafo está vacío. Cargue los datos primero.";
        }

        int nodoInicio = encontrarIndicePorId(red, idInicio);
        if (nodoInicio == -1) {
            return "La neurona de inicio '" + idInicio + "' no existe en la red.";
        }

        double[] distancias = new double[totalNodos];
        boolean[] visitados = new boolean[totalNodos];
        int[] predecesores = new int[totalNodos];

        // Inicialización de estructuras
        for (int i = 0; i < totalNodos; i++) {
            distancias[i] = INFINITO;
            visitados[i] = false;
            predecesores[i] = -1;
        }
        
        distancias[nodoInicio] = 0;

        // Algoritmo de Dijkstra principal
        for (int contador = 0; contador < totalNodos - 1; contador++) {
            int u = encontrarMinimaDistancia(distancias, visitados, totalNodos);
            if (u == -1 || distancias[u] == INFINITO) {
                break;
            }

            visitados[u] = true;

            Grafo.ListaSinapsis.NodoSinapsis actual = red.getPrimerNodoSinapsis(u);
            while (actual != null) {
                String idDestino = actual.sinapsis.getDestino().getId();
                int vIndice = encontrarIndicePorId(red, idDestino);

                if (vIndice != -1 && !visitados[vIndice]) {
                    String ntId = actual.sinapsis.getIdNeurotransmisor();
                    Neurotransmisor nt = diccionario.buscar(ntId);
                    double velocidad = (nt != null) ? nt.getVelocidad() : 1.0;

                    double pesoArista = actual.sinapsis.calcularPeso(velocidad);
                    double distanciaPotencial = distancias[u] + pesoArista;

                    if (distanciaPotencial < distancias[vIndice]) {
                        distancias[vIndice] = distanciaPotencial;
                        predecesores[vIndice] = u;
                    }
                }
                actual = actual.siguiente;
            }
        }
        
        // Construimos el nuevo reporte con el resumen estructurado
        return construirReporteTexto(red, distancias, predecesores, idInicio);
    }

    /**
     * Construye un reporte en String con los resultados del diagnóstico.
     * @param red           Grafo de la red neuronal.
     * @param distancias    Arreglo con los tiempos mínimos calculados hacia cada nodo.
     * @param predecesores  Arreglo con los índices de los nodos previos en la ruta óptima.
     * @param idInicio      Id de la neurona donde inició el estímulo.
     * @return Reporte estructurado
     */
    private String construirReporteTexto(Grafo red, double[] distancias, int[] predecesores, String idInicio) {
        StringBuilder sb = new StringBuilder();
        int totalNodos = red.getCantidadNodos();
        int contadorAisladas = 0;
        int caminosValidos = 0;
        
        double tiempoMinimo = INFINITO;
        String neuronaMasRapida = "Ninguna";
        
        // Primero calculamos métricas rápidas para el resumen ejecutivo
        for (int i = 0; i < totalNodos; i++) {
            String idDestino = red.getNeuronaEnPosicion(i).getId();
            if (idDestino.equals(idInicio)) continue;
            
            if (distancias[i] < (INFINITO - 100000.0)) {
                caminosValidos++;
                if (distancias[i] < tiempoMinimo) {
                    tiempoMinimo = distancias[i];
                    neuronaMasRapida = idDestino;
                }
            } else {
                contadorAisladas++;
            }
        }

        // =====================================================
        // SECCIÓN 1: RESUMEN DE TRANSMISIÓN CRÍTICA
        // =====================================================
        sb.append("=====================================================\n");
        sb.append("      RESUMEN DE TRANSMISIÓN SINÁPTICA CRÍTICA\n");
        sb.append("=====================================================\n");
        sb.append(String.format("Neurona Origen del Estímulo : %s\n", idInicio));
        sb.append(String.format("Neuronas Alcanzables        : %d\n", caminosValidos));
        sb.append(String.format("Neuronas Aisladas/Muertas   : %d\n", contadorAisladas));
        if (caminosValidos > 0) {
            sb.append(String.format("Vía de Respuesta Más Rápida : Hacia %s (%.4f ms)\n", neuronaMasRapida, tiempoMinimo));
        } else {
            sb.append("Vía de Respuesta Más Rápida : N/A (Red completamente bloqueada)\n");
        }
        sb.append("-----------------------------------------------------\n");
        sb.append(" DIAGNÓSTICO CLÍNICO: ");
        if (contadorAisladas == 0) {
            sb.append("ÓPTIMO. Integridad de red neuronal intacta.\n");
        } else if (caminosValidos > 0) {
            sb.append("ALERTA DE LESIÓN. Presencia de fragmentación sináptica.\n");
        } else {
            sb.append("CRÍTICO. Estímulo completamente encajonado en el origen.\n");
        }
        sb.append("=====================================================\n\n");

        // =====================================================
        // SECCIÓN 2: DESGLOSE DE CAMINOS MÁS CORTOS (RUTAS)
        // =====================================================
        sb.append("=====================================================\n");
        sb.append("       DETALLE DE RUTAS ÓPTIMAS CALCULADAS\n");
        sb.append("=====================================================\n");
        
        for (int i = 0; i < totalNodos; i++) {
            String idDestino = red.getNeuronaEnPosicion(i).getId();
            
            // Omitir la neurona origen consigo misma en el listado
            if (idDestino.equals(idInicio)) continue;

            sb.append("Neurona Objetivo: ").append(idDestino).append("\n");
            if (distancias[i] < (INFINITO - 100000.0)) {
                sb.append(String.format(" -> Tiempo Mínimo de Viaje : %.4f ms\n", distancias[i]));
                // AQUÍ SE MUESTRA EL CAMINO CORTO EXACTO PASO A PASO
                sb.append(" -> Secuencia del Impulso  : ").append(obtenerRutaComoTexto(red, predecesores, i)).append("\n");
            } else {
                sb.append(" -> [ZONA INALCANZABLE / SINAPSIS ININTERRUMPIDA]\n");
            }
            sb.append("-----------------------------------------------------\n");
        }
        
        return sb.toString();
    }

    /**
     * Reconstruye de forma recursiva la secuencia exacta de nodos del camino óptimo.
     * @param red           Grafo de la red neuronal.
     * @param predecesores  Arreglo con la información de los nodos previos.
     * @param indiceNodo    Índice numérico del nodo destino actual.
     * @return String con la ruta formateada.
     */
    private String obtenerRutaComoTexto(Grafo red, int[] predecesores, int indiceNodo) {
        if (predecesores[indiceNodo] == -1) {
            return red.getNeuronaEnPosicion(indiceNodo).getId();
        }
        return obtenerRutaComoTexto(red, predecesores, predecesores[indiceNodo]) + " -> " + red.getNeuronaEnPosicion(indiceNodo).getId();
    }

    /**
     * Busca el vértice no visitado que tiene la distancia mínima acumulada desde el origen.
     * @param distancias    Arreglo con las distancias actuales calculadas.
     * @param visitados     Arreglo booleano que indica cuales nodos fueron procesados.
     * @param totalNodos    Cantidad total de nodos en la red.
     * @return Índice del nodo con la menor distancia, o -1 si no existe nodo alcanzable.
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
     * Localiza el índice numérico de una neurona en el grafo.
     * @param red   Grafo donde se realiza la búsqueda.
     * @param id    Id de la neurona
     * @return Índice en el arreglo del grafo, o -1 si no existe.
     */
    private int encontrarIndicePorId(Grafo red, String id) {
        if (id == null) return -1;
        int totalNodos = red.getCantidadNodos();
        for (int i = 0; i < totalNodos; i++) {
            if (red.getNeuronaEnPosicion(i).getId().equals(id)) return i;
        }
        return -1;
    }
}