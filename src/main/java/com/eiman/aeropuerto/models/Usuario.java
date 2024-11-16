package com.eiman.aeropuerto.models;

import java.util.Objects;

/**
 * Clase Usuario
 */
public class Usuario {
    private String usuario;
    private String password;

    /**
     * Constructor con parámetros de usuario
     *
     * @param usuario nombre de usuario
     * @param password contraseña del usuario
     */
    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Constructor vacío de usuario
     */
    public Usuario() {}

    /**
     * Getter para el nombre del usuario
     *
     * @return nombre de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Setter para el nombre del usuario
     *
     * @param usuario nuevo nombre de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Getter para la contraseña del usuario
     *
     * @return contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter para la contraseña del usuario
     *
     * @param password nueva contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(usuario, usuario1.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(usuario);
    }

}
