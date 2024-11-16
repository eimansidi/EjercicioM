package com.eiman.aeropuerto.controllers;

import com.eiman.aeropuerto.dao.AeropuertoDAO;
import com.eiman.aeropuerto.dao.AvionDAO;
import com.eiman.aeropuerto.models.Aeropuerto;
import com.eiman.aeropuerto.models.Avion;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la ventana de borrar aviones
 */
public class BorrarAvionController implements Initializable {
    /**
     * ComboBox de aeropuertos
     */
    @FXML
    private ComboBox<Aeropuerto> cbAeropuerto;

    /**
     * ComboBox de aviones
     */
    @FXML
    private ComboBox<Avion> cbAvion;

    /**
     * Se ejecuta cuando se carga la ventana
     *
     * @param url la url
     * @param resourceBundle los recursos
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Aeropuerto> aeropuertos = AeropuertoDAO.cargarListado();
        cbAeropuerto.setItems(aeropuertos);
        cbAeropuerto.getSelectionModel().select(0);
        cbAeropuerto.valueProperty().addListener((_, _, newValue) -> cambioAeropuerto(newValue));
        cambioAeropuerto(cbAeropuerto.getSelectionModel().getSelectedItem());
    }

    /**
     * Carga los aviones de un aeropuerto cuando este se cambia
     *
     * @param aeropuerto nuevo aeropuerto seleccionado
     */
    public void cambioAeropuerto(Aeropuerto aeropuerto) {
        if (aeropuerto != null) {
            ObservableList<Avion> aviones = AvionDAO.cargarListado(aeropuerto);
            cbAvion.setItems(aviones);
            cbAvion.getSelectionModel().select(0);
        }
    }

    /**
     * Cierra la ventana
     */
    @FXML
    void cancelar() {
        Stage stage = (Stage)cbAeropuerto.getScene().getWindow();
        stage.close();
    }

    /**
     * Elimina el avión
     */
    @FXML
    void guardar() {
        Avion avion = cbAvion.getSelectionModel().getSelectedItem();
        boolean resultado = AvionDAO.eliminarAvion(avion);
        if (resultado) {
            mensajeConfirmacion("Avión eliminado correctamente");
            Stage stage = (Stage)cbAeropuerto.getScene().getWindow();
            stage.close();
        } else {
            mensajeAlerta("Ha habido un error eliminado el avión. Por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que muestra un mensaje de mensajeAlerta al usuario
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
     * Función que muestra un mensaje de confirmación al usuario
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
