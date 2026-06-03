/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package logicasinapsis.main;

import logicasinapsis.gui.VentanaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada principal del simulador SynapseLogic.
 */
public class Main {

    public static void main(String[] args) {
        // 1. Opcional: Configurar el LookAndFeel para que la GUI se vea moderna
        configurarApariencia();

        // 2. Iniciar la interfaz gráfica en el hilo de despacho de eventos (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Instanciamos la ventana principal que ya contiene el Grafo y la HashTable
                VentanaPrincipal app = new VentanaPrincipal();
                app.setVisible(true);
                
                System.out.println("SynapseLogic: Sistema iniciado correctamente.");
            } catch (Exception e) {
                System.err.println("Error crítico al iniciar la aplicación: " + e.getMessage());
            }
        });
    }

    /**
     * Ajusta la apariencia de la interfaz al estilo del sistema operativo.
     */
    private static void configurarApariencia() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, se queda con el estilo Java por defecto
        }
    }
}