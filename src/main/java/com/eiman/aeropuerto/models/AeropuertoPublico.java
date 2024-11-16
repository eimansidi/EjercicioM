package com.eiman.aeropuerto.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Clase Aeropuerto Público
 */
public class AeropuertoPublico {
    private Aeropuerto aeropuerto;
    private BigDecimal financiacion;
    private int num_trabajadores;

    /**
     * Constructor con parámetros de aeropuerto público
     *
     * @param aeropuerto
     * @param financiacion del aeropuerto
     * @param num_trabajadores del aeropuerto
     */
    public AeropuertoPublico(Aeropuerto aeropuerto, BigDecimal financiacion, int num_trabajadores) {
        this.aeropuerto = aeropuerto;
        this.financiacion = financiacion;
        this.num_trabajadores = num_trabajadores;
    }

    /**
     * Constructor vacío de aeropuerto público
     */
    public AeropuertoPublico() {}

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
     * Getter para la financiación del aeropuerto
     *
     * @return financiación del aeropuerto
     */
    public BigDecimal getFinanciacion() {
        return financiacion;
    }

    /**
     * Setter para la financiación del aeropuerto
     *
     * @param financiacion nueva financiación del aeropuerto
     */
    public void setFinanciacion(BigDecimal financiacion) {
        this.financiacion = financiacion;
    }

    /**
     * Getter para el número de trabajadores del aeropuerto
     *
     * @return número de trabajadores del aeropuerto
     */
    public int getNum_trabajadores() {
        return num_trabajadores;
    }

    /**
     * Setter para el número de trabajadores del aeropuerto
     *
     * @param num_trabajadores nuevo número de trabajadores del aeropuerto
     */
    public void setNum_trabajadores(int num_trabajadores) {
        this.num_trabajadores = num_trabajadores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AeropuertoPublico that = (AeropuertoPublico) o;
        return Objects.equals(aeropuerto, that.aeropuerto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(aeropuerto);
    }
}