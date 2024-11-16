package com.eiman.aeropuerto.models;

import java.util.Objects;

/**
 * Clase Aeropuerto Privado
 */
public class AeropuertoPrivado {
    private Aeropuerto aeropuerto;
    private int numero_socios;

    /**
     * Constructor con parámetros de aeropuerto privado
     *
     * @param aeropuerto
     * @param numero_socios del aeropuerto
     */
    public AeropuertoPrivado(Aeropuerto aeropuerto, int numero_socios) {
        this.aeropuerto = aeropuerto;
        this.numero_socios = numero_socios;
    }

    /**
     * Constructor vacío de aeropuerto privado
     */
    public AeropuertoPrivado() {}

    /**
     * Getter para el aeropuerto
     *
     * @return aeropuerto
     */
    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    /**
     * Setter para el aeropuerto
     *
     * @param aeropuerto objeto aeropuerto
     */
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Getter para el número de socios del aeropuerto
     *
     * @return número de socios del aeropuerto
     */
    public int getNumero_socios() {
        return numero_socios;
    }

    /**
     * Setter para el número de socios del aeropuerto
     *
     * @param numero_socios nuevo número de socios del aeropuerto
     */
    public void setNumero_socios(int numero_socios) {
        this.numero_socios = numero_socios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AeropuertoPrivado that = (AeropuertoPrivado) o;
        return Objects.equals(aeropuerto, that.aeropuerto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(aeropuerto);
    }
}
