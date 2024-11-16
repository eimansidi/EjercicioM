package com.eiman.aeropuerto.models;

import java.util.Objects;

/**
 * Clase Dirección
 */
public class Direccion {
    private int id;
    private String pais;
    private String ciudad;
    private String calle;
    private int numero;

    /**
     * Constructor con parámetros de dirección
     *
     * @param id ID de la dirección
     * @param pais pais de la dirección
     * @param ciudad ciudad de la dirección
     * @param calle calle de la dirección
     * @param numero número de la dirección
     */
    public Direccion(int id, String pais, String ciudad, String calle, int numero) {
        this.id = id;
        this.pais = pais;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    /**
     * Constructor vacío de dirección
     */
    public Direccion() {}

    /**
     * Getter para la id de la dirección
     *
     * @return id ID de la dirección
     */
    public int getId() {
        return id;
    }

    /**
     * Setter para la id de la dirección
     *
     * @param id nueva id de la dirección
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter para el país de la dirección
     *
     * @return país de la dirección
     */
    public String getPais() {
        return pais;
    }

    /**
     * Setter para el país de la dirección
     *
     * @param pais nuevo país de la dirección
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Getter para la ciudad de la dirección
     *
     * @return ciudad de la dirección
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Setter para la ciudad de la dirección
     *
     * @param ciudad nueva ciudad de la dirección
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Getter para la calle de la dirección
     *
     * @return calle de la dirección
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Setter para la calle de la dirección
     *
     * @param calle nueva calle de la dirección
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Getter para el número de la dirección
     *
     * @return número de la dirección
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Setter para el número de la dirección
     *
     * @param numero nuevo número de la dirección
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return numero == direccion.numero && Objects.equals(pais, direccion.pais) && Objects.equals(ciudad, direccion.ciudad) && Objects.equals(calle, direccion.calle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pais, ciudad, calle, numero);
    }
}