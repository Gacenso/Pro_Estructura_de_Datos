/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicasinapsis.modelo;

/**
 * Representa los neurotransmisores almacenados en el 
 * diccionario (Hash Table).
 */
public class Neurotransmisor {
    private String id;
    private String nombre;
    private String efecto; // Excitatorio, Inhibitorio o Modulador 
    private double velocidad; // Factor v para el cálculo de peso
    private String descripcion;

    /**
     * Construye una nueva instancia de un Neurotransmisor
     * con sus propiedades.
     * @param id Identificador único del neurotransmisor.
     * @param nombre Nombre del neurotransmisor.
     * @param efecto Tipo de efecto que produce.
     * @param velocidad Factor de velocidad "v". 
     * @param descripcion Descripción del rol que toma el neurotransmisor.
     */
    public Neurotransmisor(String id, String nombre, String efecto, double velocidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.efecto = efecto;
        this.velocidad = velocidad;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el id del neurotransmisor.
     * @return id en formato String.
     */ 
    public String getId() { return id; }
    
    /**
     * Crea o actualiza el id del neurotransmisor.
     * @param id el nuevo id.
     */
    public void setId(String id) { this.id = id; }

    /**
     * Obtiene el nombre del neurotransmisor.
     * @return nombre del neurotransmisor.
     */
    public String getNombre() { return nombre; }
    
    /**
     * Crea o actualiza el nombre del neurotransmisor.
     * @param nombre el nuevo nombre.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el efecto del neurotransmisor.
     * @return efecto del neurotransmisor.
     */
    public String getEfecto() { return efecto; }
    
    /**
     * Crea o actualiza el efecto del neurotransmisor.
     * @param efecto el nuevo efecto.
     */
    public void setEfecto(String efecto) { this.efecto = efecto; }

    /**
     * Obtiene la velocidad del neurotransmisor.
     * @return velocidad del neurotransmisor
     */
    public double getVelocidad() { return velocidad; }
    
    /**
     * Crea o actualiza la velocdiad del nerotransmisor.
     * @param velocidad la nueva velocidad.
     */
    public void setVelocidad(double velocidad) { this.velocidad = velocidad; }

    /**
     * Obtiene la descripción del neurotransmisor.
     * @return descripción del neurotransmisor.
     */
    public String getDescripcion() { return descripcion; }
    
    /**
     * Crea o actualiza la descripción del neurotransmisor.
     * @param descripcion nueva descripción 
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Devuelve el String de los atributos del neurotransmisor.
     * @return String con id, nombre, efecto y velocidad.
     */
    @Override
    public String toString() {
        return "Neurotransmisor{" + "id=" + id + ", nombre=" + nombre + ", efecto=" + efecto + ", velocidad=" + velocidad + '}';
    }
}