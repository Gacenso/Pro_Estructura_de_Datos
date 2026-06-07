/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package logicasinapsis.main;

import logicasinapsis.gui.Interfazprincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada principal del simulador SynapseLogic.
 * Clase encargada de inicializar la configuración visual
 * necesitada para Graphstream y la UI.
 */
public class Main {
    
    /**
     * Método principal del programa.
     * Configura el renderizado de GraphStream, estableciendo
     * la ventana principal del programa.
     */
    public static void main(String[] args) {
       // 1. ¡OBLIGATORIO PARA GRAPHSTREAM! Configurar el motor gráfico antes de iniciar la ventana
        System.setProperty("org.graphstream.ui", "swing");

        // 2. Configurar el LookAndFeel para que la interfaz se vea moderna
        configurarApariencia();

        // 3. Iniciar tu interfaz gráfica real en su hilo seguro (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Instanciamos tu JFrame real
                Interfazprincipal ventana = new Interfazprincipal();
                
                // Centrar la ventana automáticamente en el medio de la pantalla
                ventana.setLocationRelativeTo(null);
                
                // Hacer visible la ventana
                ventana.setVisible(true);
                
                System.out.println("SynapseLogic: Interfazprincipal iniciada con éxito.");
            } catch (Exception e) {
                System.err.println("Error crítico al inicializar la interfaz gráfica: " + e.getMessage());
                e.printStackTrace();
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
            // Si falla, se queda con el estilo Java por defecto o Metal
        }
    }
}