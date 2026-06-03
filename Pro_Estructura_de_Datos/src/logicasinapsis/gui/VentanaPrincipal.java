/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logicasinapsis.gui;

import javax.swing.*;
import java.awt.*;
import com.logicasinapsis.estructuras.Grafo;
import com.logicasinapsis.estructuras.HashTable;

/**
 * Interfaz gráfica de usuario principal.
 */
public class VentanaPrincipal extends JFrame {
    // Componentes de lógica
    private Grafo redNeuronal;
    private HashTable diccionario;
    
    // Componentes visuales
    private JPanel panelGrafo; // Donde se dibujará la red (Requerimiento G)
    private JTextArea areaConsola; // Para mostrar rutas y zonas aisladas
    private JButton btnCargar, btnDijkstra, btnZonasAisladas, btnFatiga;

    public VentanaPrincipal() {
        super("SynapseLogic - Analizador de Redes Neuronales");
        this.redNeuronal = new Grafo(100);
        this.diccionario = new HashTable(50);
        
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Layout y creación de botones/paneles
        this.setLayout(new BorderLayout());
        
        btnCargar = new JButton("Cargar Datos CSV");
        btnDijkstra = new JButton("Calcular Ruta Más Rápida");
        btnZonasAisladas = new JButton("Detectar Zonas Aisladas");
        btnFatiga = new JButton("Simular Fatiga");

        // Agregar listeners para conectar con las clases de 'com.logicasinapsis.algoritmos'
    }

    private void configurarVentana() {
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}