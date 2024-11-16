package com.eiman.aeropuerto.models;

import java.util.Objects;

/**
 * Clase Avión
 */
public class Avion {
    private int id;
    private String modelo;
    private int numero_asientos;
    private int velocidad_maxima;
    private boolean activado;
    private Aeropuerto aeropuerto;

    /**
     * Constructor con parámetros del avión
     *
     * @param id del avión
     * @param modelo del avión
     * @param numero_asientos del avión
     * @param velocidad_maxima del avión
     * @param activado del avión
     * @param aeropuerto del avión
     */
    public Avion(int id, String modelo, int numero_asientos, int velocidad_maxima, boolean activado, Aeropuerto aeropuerto) {
        this.id = id;
        this.modelo = modelo;
        this.numero_asientos = numero_asientos;
        this.velocidad_maxima = velocidad_maxima;
        this.activado = activado;
        this.aeropuerto = aeropuerto;
    }

    /**
     * Constructor vacío del avión
     */
    public Avion() {}

    /**
     * ToString de avión
     *
     * @return modelo del avión
     */
    @Override
    public String toString() {
        return modelo;
    }

    /**
     * Getter para el id del avión
     *
     * @return id del avión
     */
    public int getId() {
        return id;
    }

    /**
     * Setter para el id del avión
     *
     * @param id nuevo id del avión
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter para modelo id del avión
     *
     * @return modelo del avión
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Setter para modelo id del avión
     *
     * @param modelo nuevo modelo del avión
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Getter para el número de asientos del avión
     *
     * @return número de asientos del avión
     */
    public int getNumero_asientos() {
        return numero_asientos;
    }

    /**
     * Setter para el número de asientos del avión
     *
     * @param numero_asientos nuevo número de asientos del avión
     */
    public void setNumero_asientos(int numero_asientos) {
        this.numero_asientos = numero_asientos;
    }

    /**
     * Getter para la velocidad máxima del avión
     *
     * @return velocidad máxima del avión
     */
    public int getVelocidad_maxima() {
        return velocidad_maxima;
    }

    /**
     * Setter para la velocidad máxima del avión
     *
     * @param velocidad_maxima nueva velocidad máxima del avión
     */
    public void setVelocidad_maxima(int velocidad_maxima) {
        this.velocidad_maxima = velocidad_maxima;
    }

    /**
     * Getter para si el avión está activado o no
     *
     * @return si el avión está activado o no
     */
    public boolean isActivado() {
        return activado;
    }

    /**
     * Setter para si el avión está activado o no
     *
     * @param activado nuevo valor para si el avión está activado o no
     */
    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    /**
     * Getter para el aeropuerto del avión
     *
     * @return aeropuerto del avión
     */
    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    /**
     * Setter para el aeropuerto del avión
     *
     * @param aeropuerto nuevo aeropuerto del avión
     */
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return Objects.equals(modelo, avion.modelo) && Objects.equals(aeropuerto, avion.aeropuerto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelo, aeropuerto);
    }

}