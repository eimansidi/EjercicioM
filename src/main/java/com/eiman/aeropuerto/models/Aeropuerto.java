package com.eiman.aeropuerto.models;

import java.sql.Blob;
import java.util.Objects;

/**
 * Clase Aeropuerto
 */
public class Aeropuerto {
    private int id;
    private String nombre;
    private int anio_inauguracion;
    private int capacidad;
    private Direccion direccion;
    private Blob imagen;

    /**
     * Constructor con parámetros aeropuerto
     *
     * @param id del aeropuerto
     * @param nombre del aeropuerto
     * @param anio_inauguracion del aeropuerto
     * @param capacidad del aeropuerto
     * @param direccion del aeropuerto
     * @param imagen del aeropuerto
     */
    public Aeropuerto(int id, String nombre, int anio_inauguracion, int capacidad, Direccion direccion, Blob imagen) {
        this.id = id;
        this.nombre = nombre;
        this.anio_inauguracion = anio_inauguracion;
        this.capacidad = capacidad;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    /**
     * Constructor vacío de aeropuerto
     */
    public Aeropuerto() {}

    /**
     * ToString de aeropuerto
     *
     * @return nombre del aeropuerto
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Getter para el id del aeropuerto
     *
     * @return id del aeropuerto
     */
    public int getId() {
        return id;
    }

    /**
     * Setter para el id del aeropuerto
     *
     * @param id nuevo id del aeropuerto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter para el id del aeropuerto
     *
     * @return id del aeropuerto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter para el nombre del aeropuerto
     *
     * @param nombre nuevo nombre del aeropuerto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter para el año de inauguración del aeropuerto
     *
     * @return año de inauguración del aeropuerto
     */
    public int getAnio_inauguracion() {
        return anio_inauguracion;
    }

    /**
     * Setter para el año de inauguración del aeropuerto
     *
     * @param anio_inauguracion nuevo año de inauguración del aeropuerto
     */
    public void setAnio_inauguracion(int anio_inauguracion) {
        this.anio_inauguracion = anio_inauguracion;
    }

    /**
     * Getter para la capacidad del aeropuerto
     *
     * @return capacidad del aeropuerto
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Setter para la capacidad del aeropuerto
     *
     * @param capacidad nueva capacidad del aeropuerto
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Getter para la dirección del aeropuerto
     *
     * @return dirección del aeropuerto
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Setter para la dirección del aeropuerto
     *
     * @param direccion nueva dirección del aeropuerto
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Getter para la imagen del aeropuerto
     *
     * @return imagen del aeropuerto
     */
    public Blob getImagen() {
        return imagen;
    }

    /**
     * Setter para la imagen del aeropuerto
     *
     * @param imagen nueva imagen del aeropuerto
     */
    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aeropuerto that = (Aeropuerto) o;
        return anio_inauguracion == that.anio_inauguracion && capacidad == that.capacidad && Objects.equals(nombre, that.nombre) && Objects.equals(direccion, that.direccion) && Objects.equals(imagen, that.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, anio_inauguracion, capacidad, direccion, imagen);
    }
}
