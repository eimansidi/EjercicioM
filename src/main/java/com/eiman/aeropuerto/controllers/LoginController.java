package com.eiman.aeropuerto.controllers;

import com.eiman.aeropuerto.dao.UsuarioDAO;
import com.eiman.aeropuerto.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de la ventana login
 */
public class LoginController {
    /**
     * Campo de texto para la contraseña
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Campo de texto para el usuario
     */
    @FXML
    private TextField txtUsuario;

    /**
     * Controlador para el botón de login. Valida que los campos de usuario
     * y contraseña tengan un valor no vacío. Si los campos son válidos,
     * intenta obtener el usuario de la base de datos y llama a
     * {@link #validarLogin(Usuario)} si existe o a
     * {@link #invalidUser()} si no existe.
     */
    @FXML
    void login() {
        String error = validarCampos();
        if (!error.isBlank()) {
            mensajeAlerta(error);
        } else {
            Usuario user = obtenerUsuario();
            if (user == null) {
                invalidUser();
            } else {
                validarLogin(user);
            }
        }
    }

    /**
     * Valida que los campos de usuario y password tengan un valor no vacío
     *
     * @return Cadena con los errores de validación o cadena vacía si los campos son válidos
     */
    private String validarCampos() {
        String error = "";
        String usuario = txtUsuario.getText();
        String password = passwordField.getText();

        if (usuario.isBlank()) {
            error = "El campo usuario no puede estar vacío";
        }
        if (password.isEmpty()) {
            if (!error.isBlank()) {
                error += "\n";
            }
            error += "El campo password no puede estar vacío";
        }
        return error;
    }

    /**
     * Obtiene el usuario del campo de texto y lo devuelve como objeto Usuario si existe en la base de datos
     *
     * @return Usuario o null si el usuario no existe en la base de datos
     */
    private Usuario obtenerUsuario() {
        String usuario = txtUsuario.getText();
        return UsuarioDAO.getUsuario(usuario);
    }

    /**
     * Muestra un mensaje de error al usuario y limpia los campos de texto cuando
     * el usuario o la contraseña no son válidos.
     */
    private void invalidUser() {
        mensajeAlerta("Usuario no valido");
        txtUsuario.setText("");
        passwordField.setText("");
    }

    /**
     * Comprueba si el password del usuario coincide con el password de la base de datos.
     * Si coincide, llama a {@link #loginValidado()} para mostrar la ventana principal.
     * Si no coincide, muestra un mensaje de error al usuario y borra el campo de texto
     * de la contraseña.
     *
     * @param user objeto Usuario que contiene el password del usuario de la base de datos
     */
    private void validarLogin(Usuario user) {
        String password = passwordField.getText();
        if (password.equals(user.getPassword())) {
            loginValidado();
        } else {
            mensajeAlerta("Contraseña incorrecta");
            passwordField.setText("");
        }
    }

    /**
     * Abre la ventana principal de la aplicación y cierra la ventana de login
     * si el usuario ha introducido correctamente su usuario y contraseña.
     */
    private void loginValidado() {
        System.out.println("Login correcto");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxml/Aeropuerto.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("AEROPUERTOS");
            stage.show();
            Stage actual = (Stage) txtUsuario.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            mensajeAlerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Muestra un mensaje de mensajeAlerta al usuario
     *
     * @param texto contenido de la mensajeAlerta
     */
    public void mensajeAlerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ERROR");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

}