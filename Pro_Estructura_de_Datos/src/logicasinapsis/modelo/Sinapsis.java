/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logicasinapsis.modelo;

/**
 * Representa la conexión (arista) entre dos neuronas.
 */
public class Sinapsis {
    private Neurona destino;
    private double distancia; // d en la fórmula 
    private String idNeurotransmisor; // Para buscar en la Hash Table 
    private double k; // Factor de eficiencia

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
        // Implementación de la fórmula del requerimiento E 
        return this.distancia / (v * this.k);
    }

    // Getters y Setters
    public Neurona getDestino() { return destino; }
    public void setDestino(Neurona destino) { this.destino = destino; }

    public double getDistancia() { return distancia; }
    public void setDistancia(double distancia) { this.distancia = distancia; }

    public String getIdNeurotransmisor() { return idNeurotransmisor; }
    public void setIdNeurotransmisor(String idNeurotransmisor) { this.idNeurotransmisor = idNeurotransmisor; }

    public double getK() { return k; }
    public void setK(double k) { this.k = k; }
}
