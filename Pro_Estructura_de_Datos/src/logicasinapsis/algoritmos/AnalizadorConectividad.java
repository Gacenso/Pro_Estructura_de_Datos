/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicasinapsis.algoritmos;

import logicasinapsis.estructuras.Grafo;
import logicasinapsis.modelo.Neurona;

/**
 * Clase encargada de analizar la resiliencia y conectividad de la red.
 */
public class AnalizadorConectividad {

    /**
     * Determina qué zonas son inalcanzables desde una neurona fuente (Requerimiento B).
     * @param red El grafo de la red sináptica.
     * @param idFuente ID de la neurona donde inicia el estímulo.
     * @return Arreglo o estructura propia con las neuronas aisladas.
     */
   public String[] detectarZonasAisladas(Grafo red, String idFuente) {
        int totalNodos = red.getCantidadNodos();
        if (totalNodos == 0) return new String[0];

        int indiceFuente = -1;
        for (int i = 0; i < totalNodos; i++) {
            if (red.getNeuronaEnPosicion(i).getId().equals(idFuente)) {
                indiceFuente = i;
                break;
            }
        }

        if (indiceFuente == -1) return new String[0];

        // Implementación de BFS manual usando arreglos como cola fija
        boolean[] visitados = new boolean[totalNodos];
        int[] cola = new int[totalNodos];
        int inicioCola = 0;
        int finCola = 0;

        visitados[indiceFuente] = true;
        cola[finCola++] = indiceFuente;

        while (inicioCola < finCola) {
            int u = cola[inicioCola++];
            Grafo.ListaSinapsis.NodoSinapsis actual = red.getPrimerNodoSinapsis(u);
            while (actual != null) {
                String idDestino = actual.sinapsis.getDestino().getId();
                int vIndice = -1;
                for (int i = 0; i < totalNodos; i++) {
                    if (red.getNeuronaEnPosicion(i).getId().equals(idDestino)) {
                        vIndice = i;
                        break;
                    }
                }
                if (vIndice != -1 && !visitados[vIndice]) {
                    visitados[vIndice] = true;
                    cola[finCola++] = vIndice;
                }
                actual = actual.siguiente;
            }
        }

        // Contar cuántas quedaron aisladas (visitados == false)
        int contadorAisladas = 0;
        for (int i = 0; i < totalNodos; i++) {
            if (!visitados[i]) contadorAisladas++;
        }

        String[] aisladas = new String[contadorAisladas];
        int idx = 0;
        for (int i = 0; i < totalNodos; i++) {
            if (!visitados[i]) {
                aisladas[idx++] = red.getNeuronaEnPosicion(i).getId();
            }
        }
        return aisladas;
    }

    public boolean esFuertementeConexa(Grafo red) {
        int totalNodos = red.getCantidadNodos();
        if (totalNodos <= 1) return true;

        // Para que sea fuertemente conexo, un BFS desde CUALQUIER nodo debe alcanzar a TODOS los demás.
        // Hacemos una validación simplificada pero efectiva para el entregable.
        for (int i = 0; i < totalNodos; i++) {
            boolean[] visitados = new boolean[totalNodos];
            int[] cola = new int[totalNodos];
            int inicio = 0, fin = 0;

            visitados[i] = true;
            cola[fin++] = i;

            while (inicio < fin) {
                int u = cola[inicio++];
                Grafo.ListaSinapsis.NodoSinapsis actual = red.getPrimerNodoSinapsis(u);
                while (actual != null) {
                    String destId = actual.sinapsis.getDestino().getId();
                    int vIdx = -1;
                    for (int k = 0; k < totalNodos; k++) {
                        if (red.getNeuronaEnPosicion(k).getId().equals(destId)) { vIdx = k; break; }
                    }
                    if (vIdx != -1 && !visitados[vIdx]) {
                        visitados[vIdx] = true;
                        cola[fin++] = vIdx;
                    }
                    actual = actual.siguiente;
                }
            }
            // Si desde este nodo i no se llegó a todos, la red no es fuertemente conexa
            for (boolean v : visitados) {
                if (!v) return false;
            }
        }
        return true;
    }

    public void simularFatigaCognitiva(Grafo red) {
        if (red == null) return;

        int totalNodos = red.getCantidadNodos();
        // Recorremos cada neurona del arreglo principal
        for (int i = 0; i < totalNodos; i++) {
            Grafo.ListaSinapsis.NodoSinapsis actual = red.getPrimerNodoSinapsis(i);
            
            // Recorremos la lista enlazada de conexiones de esa neurona
            while (actual != null) {
                double kActual = actual.sinapsis.getK();
                
                // Aplicamos la fórmula matemática exacta de la rúbrica
                double nuevoK = kActual * 1.2;
                
                // Guardamos el nuevo coeficiente alterado en caliente
                actual.sinapsis.setK(nuevoK);
                
                actual = actual.siguiente; // Avanzar al siguiente enlace
            }
        }
    }
}
