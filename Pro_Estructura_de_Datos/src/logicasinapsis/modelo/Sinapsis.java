/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicasinapsis.modelo;

/**
 * Representa la conexión (arista) entre dos neuronas.
 */
public class Sinapsis {
    private Neurona destino; // nodo destino
    private double distancia; // d en la fórmula 
    private String idNeurotransmisor; // Para buscar en la Hash Table \r
    private double k; // Factor de eficiencia

    /**
     * Crea una nueva conexión con sus parámetros inciales.
     * @param destino           Neurona a la que llega la conexión.
     * @param distancia         Longitud de la conexión.
     * @param idNeurotransmisor Código del neurotransmisor involucrado.
     * @param k                 Coeficiente de eficiencia.
     */
    public Sinapsis(Neurona destino, double distancia, String idNeurotransmisor, double k) {
        this.destino = destino;
        this.distancia = distancia;
        this.idNeurotransmisor = idNeurotransmisor;
        this.k = k;
    }

    /**
     * Calcula el peso W de la arista según la fórmula: W = d / (v * k) 
     * @param v Factor de velocidad obtenido de la Hash Table.
     * @return El peso calculado para el algoritmo de Dijkstra.
     */
    public double calcularPeso(double v) {
        return this.distancia / (v * this.k);
    }

    /**
     * Obtiene el destino del neurona.
     * @return destino del neurona.
     */
    public Neurona getDestino() { return destino; }

    /**
     * Crea o actualiza el destino del neurona.
     * @param destino nuevo destino.
     */
    public void setDestino(Neurona destino) { this.destino = destino; }

    /**
     * Obtiene la distancia de la neurona.
     * @return distancia de la neurona.
     */
    public double getDistancia() { return distancia; }
    
    /**
     * Crea o actualiza la distancia de la neurona.
     * @param distancia nueva distancia.
     */
    public void setDistancia(double distancia) { this.distancia = distancia; }

    /**
     * Consigue el id del neurotransmisor.
     * @return id del neurotransmisor.
     */
    public String getIdNeurotransmisor() { return idNeurotransmisor; }
    
    /**
     * Crea o actualiza el id del neurotransmisor.
     * @param idNeurotransmisor nuevo id.
     */
    public void setIdNeurotransmisor(String idNeurotransmisor) { this.idNeurotransmisor = idNeurotransmisor; }

    /**
     * Obtiene el coeficiente de eficiencia ("k").
     * @return coeficiente de eficiencia ("k").
     */
    public double getK() { return k; }
    
    /**
     * Crea o actualiza el coeficiente de eficiencia ("k").
     * @param k nuevo coeficiente de eficiencia ("k").
     */
    public void setK(double k) { this.k = k; }

    /**
     * Devuelve un String de la sinapsis.
     * @return String que contiene el destino, distancia, id del neurotransmisor y eficiencia.
     */
    @Override
    public String toString() {
        return " -> " + destino.getId() + " (d=" + distancia + ", NT=" + idNeurotransmisor + ", k=" + k + ")";
    }
}