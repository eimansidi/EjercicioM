package com.eiman.aeropuerto.controllers;

import com.eiman.aeropuerto.dao.AeropuertoDAO;
import com.eiman.aeropuerto.dao.AvionDAO;
import com.eiman.aeropuerto.models.Aeropuerto;
import com.eiman.aeropuerto.models.Avion;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la ventana de añadir aviones
 */
public class AniadirAvionController implements Initializable {
    /**
     * ComboBox del aeropuerto
     */
    @FXML
    private ComboBox<Aeropuerto> cbAeropuerto;

    /**
     * RadioButton activado
     */
    @FXML
    private RadioButton rbActivado;

    /**
     * Campo de texto para añadir los asientos
     */
    @FXML
    private TextField txtAsientos;

    /**
     * Campo de texto modelos
     */
    @FXML
    private TextField txtModelos;

    /**
     * Campo de texto velocidad maxima
     */
    @FXML
    private TextField txtVelMax;

    /**
     * Cuando se carga la ventana
     *
     * @param url la url
     * @param resourceBundle los recursos
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Aeropuerto> aeropuertos = AeropuertoDAO.cargarListado();
        cbAeropuerto.setItems(aeropuertos);
        cbAeropuerto.getSelectionModel().select(0);
    }

    /**
     * Cierra la ventana
     */
    @FXML
    void cancelar() {
        Stage stage = (Stage)txtAsientos.getScene().getWindow();
        stage.close();
    }

    /**
     * Se ejecuta cuando se pulsa el botón "Guardar". Valida que los campos de texto tengan un valor no vacío.
     * Si los campos son válidos, llama a {@link #procesarAvion()}
     */
    @FXML
    void guardar() {
        String error = validarCampos();
        if (!error.isEmpty()) {
            mensajeAlerta(error);
        } else {
            procesarAvion();
        }
    }

    /**
     * Valida que los campos de texto tengan un valor no vacío
     * @return Cadena con los errores de validación o cadena vacía si los campos son válidos
     */
    private String validarCampos() {
        StringBuilder error = new StringBuilder();

        if (txtModelos.getText().isEmpty()) {
            error.append("El modelo del avión no puede estar vacío");
        }

        String numAsientosError = validarNumeroAsientos();
        if (!numAsientosError.isEmpty()) {
            if (!error.isEmpty()) error.append("\n");
            error.append(numAsientosError);
        }

        String velMaxError = validarVelocidadMaxima();
        if (!velMaxError.isEmpty()) {
            if (!error.isEmpty()) error.append("\n");
            error.append(velMaxError);
        }

        return error.toString();
    }

    /**
     * Valida que el campo de texto para el número de asientos del avión no esté vacío
     * y que el valor ingresado sea un número entero.
     *
     * @return Cadena con el error si el campo está vacío o no es un número entero,
     *         o cadena vacía si el campo es válido
     */
    private String validarNumeroAsientos() {
        if (txtAsientos.getText().isEmpty()) {
            return "El número de asientos del avión no puede estar vacío";
        }
        try {
            Integer.parseInt(txtAsientos.getText());
        } catch (NumberFormatException e) {
            return "El número de asientos del avión tiene que ser un número entero";
        }
        return "";
    }

    /**
     * Valida la velocidad máxima del avión. Comprueba que esté relleno y que sea un número entero.
     *
     * @return Cadena con el error o cadena vacía si el campo es válido
     */
    private String validarVelocidadMaxima() {
        if (txtVelMax.getText().isEmpty()) {
            return "La velocidad máxima del avión no puede estar vacío";
        }
        try {
            Integer.parseInt(txtVelMax.getText());
        } catch (NumberFormatException e) {
            return "La velocidad máxima del avión tiene que ser un número entero";
        }
        return "";
    }

    /**
     * Procesa el avión que se ha creado en el formulario de
     * "Añadir Avión". Primero crea el avión y luego, si no
     * existe ya ese avión en el aeropuerto, lo guarda en la
     * base de datos.
     * Si el avión ya existe, se muestra una mensajeAlerta para
     * que el usuario elija otro modelo u otro aeropuerto.
     */
    private void procesarAvion() {
        Avion avion = crearAvion();
        if (esAvionDuplicado(avion)) {
            mensajeAlerta("Este modelo ya existe en el aeropuerto. Elige otro modelo u otro aeropuerto");
        } else {
            guardarAvion(avion);
        }
    }

    /**
     * Crea un objeto Avion con los valores ingresados en los campos de texto y seleccionados en el
     * radio buttons y el ComboBox.
     *
     * @return Avion con el modelo, número de asientos, velocidad máxima, estado de activación,
     *         y aeropuerto seleccionado.
     */
    private Avion crearAvion() {
        Avion avion = new Avion();
        avion.setModelo(txtModelos.getText());
        avion.setNumero_asientos(Integer.parseInt(txtAsientos.getText()));
        avion.setVelocidad_maxima(Integer.parseInt(txtVelMax.getText()));
        avion.setActivado(rbActivado.isSelected());
        avion.setAeropuerto(cbAeropuerto.getSelectionModel().getSelectedItem());
        return avion;
    }

    /**
     * Verifica si el avión especificado ya existe en la lista de aviones.
     *
     * @param avion Avión a comprobar si es duplicado
     * @return true si el avión ya existe en la lista, false de lo contrario
     */
    private boolean esAvionDuplicado(Avion avion) {
        ObservableList<Avion> aviones = AvionDAO.cargarListado();
        return aviones.contains(avion);
    }

    /**
     * Guarda el avión en la BD y muestra un mensaje de confirmación si está bien
     * o un mensaje de error si ha habido un problema
     *
     * @param avion Avión a insertarDireccion
     */
    private void guardarAvion(Avion avion) {
        int resultado = AvionDAO.insertarAvion(avion);
        if (resultado == -1) {
            mensajeAlerta("Ha habido un error insertando el avión. Por favor inténtelo de nuevo");
        } else {
            mensajeConfirmacion("Avión insertado correctamente!");
            cancelar();
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

    /**
     * Muestra un mensaje de confirmación al usuario
     *
     * @param texto contenido del mensaje
     */
    public void mensajeConfirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

}
