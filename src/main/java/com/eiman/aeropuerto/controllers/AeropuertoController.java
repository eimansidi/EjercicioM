package com.eiman.aeropuerto.controllers;

import com.eiman.aeropuerto.dao.AeropuertoDAO;
import com.eiman.aeropuerto.dao.AeropuertoPrivadoDAO;
import com.eiman.aeropuerto.dao.AeropuertoPublicoDAO;
import com.eiman.aeropuerto.dao.AvionDAO;
import com.eiman.aeropuerto.models.Aeropuerto;
import com.eiman.aeropuerto.models.AeropuertoPrivado;
import com.eiman.aeropuerto.models.AeropuertoPublico;
import com.eiman.aeropuerto.models.Avion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador de la ventana de aeropuertos
 */
public class AeropuertoController implements Initializable {
    /**
     * Menu de borrar aeropuertos
     */
    @FXML
    private MenuItem menuBorrarAeropuerto;

    /**
     * ToggleGroup para los radio buttons
     */
    @FXML
    private ToggleGroup rbGroup;

    /**
     * RadioButton publico
     */
    @FXML
    private RadioButton rbPublicos;

    /**
     * RadioButton privado
     */
    @FXML
    private RadioButton rbPrivados;

    /**
     * Filtrar por nombre
     */
    @FXML
    private TextField filtroNombre;

    /**
     * Menu de editar aeropuertos
     */
    @FXML
    private MenuItem menuEditarAeropuerto;

    /**
     * Menu de info aeropuertos
     */
    @FXML
    private MenuItem menuInfoAeropuerto;

    /**
     * Tabla
     */
    @FXML
    private TableView tabla;

    private ObservableList masterData = FXCollections.observableArrayList();
    private ObservableList filteredData = FXCollections.observableArrayList();

    /**
     * Se ejecuta cuando se carga la ventana
     *
     * @param url la url
     * @param resourceBundle los recursos
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Listener
        tabla.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (_, _, newValue) -> {
            deshabilitarMenus(newValue == null);
        });

        cargarPublicos();

        rbGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> _, Toggle _, Toggle newBtn) -> {
            if (newBtn.equals(rbPublicos)) {
                cargarPublicos();
            } else {
                cargarPrivados();
            }
        });

        filtroNombre.setOnKeyTyped(_ -> filtrar());
    }

    /**
     * Abre un menú para añadir un aeropuerto nuevo
     *
     * @param event el evento
     */
    @FXML
    void aniadirAeropuerto(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FormAeropuerto.fxml"));
            FormAeropuertoController controlador = new FormAeropuertoController();
            fxmlLoader.setController(controlador);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("AVIONES - AÑADIR AEROPUERTO");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (rbPublicos.isSelected()) {
                cargarPublicos();
            } else {
                cargarPrivados();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            mensajeAlerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Abre un menú para editar el aeropuerto seleccionado
     *
     * @param event el evento
     */
    @FXML
    void editarAeropuerto(ActionEvent event) {
        Object aeropuerto = tabla.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            mensajeAlerta("Selecciona un aeropuerto antes de editarlo");
        } else {
            try {
                Window ventana = rbPrivados.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FormAeropuerto.fxml"));
                FormAeropuertoController controlador = new FormAeropuertoController(aeropuerto);
                fxmlLoader.setController(controlador);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("AVIONES - EDITAR AEROPUERTO");
                stage.initOwner(ventana);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (rbPublicos.isSelected()) {
                    cargarPublicos();
                } else {
                    cargarPrivados();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                mensajeAlerta("Error abriendo ventana, por favor inténtelo de nuevo");
            }
        }
    }

    /**
     * Elimina el aeropuerto seleccionado
     *
     * @param event el evento
     */
    @FXML
    void borrarAeropuerto(ActionEvent event) {
        Object aeropuerto = tabla.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            mensajeAlerta("Selecciona un aeropuerto antes de eliminarlo");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(tabla.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estás seguro que quieres eliminarDireccion ese aeropuerto? Esto también eliminara los aviones en este aeropuerto.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if (aeropuerto instanceof AeropuertoPublico aeropuertoPublico) {
                    // Aeropuerto Público
                    ObservableList<Avion> aviones = AvionDAO.cargarListado(aeropuertoPublico.getAeropuerto());
                    for (Avion avion:aviones) {
                        if (!AvionDAO.eliminarAvion(avion)) {
                            mensajeAlerta("No se pudo eliminarDireccion ese aeropuerto. Inténtelo de nuevo");
                            return;
                        }
                    }
                    Aeropuerto airport = aeropuertoPublico.getAeropuerto();
                    if (AeropuertoPublicoDAO.eliminarAeropuertoPublico(aeropuertoPublico)) {
                        if (AeropuertoDAO.eliminarAeropuerto(airport)) {
                            cargarPublicos();
                            mensajeConfirmacion("Aeropuerto eliminado correctamente");
                        } else {
                            mensajeAlerta("No se pudo eliminarDireccion ese aeropuerto. Inténtelo de nuevo");
                        }
                    } else {
                        mensajeAlerta("No se pudo eliminarDireccion ese aeropuerto. Inténtelo de nuevo");
                    }
                } else {
                    // Aeropuerto Privado
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                    ObservableList<Avion> aviones = AvionDAO.cargarListado(aeropuertoPrivado.getAeropuerto());
                    for (Avion avion:aviones) {
                        if (!AvionDAO.eliminarAvion(avion)) {
                            mensajeAlerta("No se pudo eliminarDireccion ese aeropuerto. Inténtelo de nuevo");
                            return;
                        }
                    }
                    Aeropuerto airport = aeropuertoPrivado.getAeropuerto();
                    if (AeropuertoPrivadoDAO.eliminarAeropuertoPrivado(aeropuertoPrivado)) {
                        if (AeropuertoDAO.eliminarAeropuerto(airport)) {
                            cargarPrivados();
                            mensajeConfirmacion("Aeropuerto eliminado correctamente");
                        } else {
                            mensajeAlerta("No se pudo eliminarDireccion ese aeropuerto. Inténtelo de nuevo");
                        }
                    } else {
                        mensajeAlerta("No se pudo eliminarDireccion ese aeropuerto. Inténtelo de nuevo");
                    }
                }
            }
        }
    }

    /**
     * Abre un menú para ver los datos del aeropuerto
     *
     * @param event el evento
     */
    @FXML
    void infoAeropuerto(ActionEvent event) {
        Object aeropuerto = tabla.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            mensajeAlerta("Selecciona un aeropuerto antes de ver su información");
        } else {
            String info = "";
            if (aeropuerto instanceof AeropuertoPublico aeropuertoPublico) {
                // Aeropuerto Público
                Aeropuerto airport = aeropuertoPublico.getAeropuerto();
                info = "Nombre: " + airport.getNombre();
                info += "\nPaís:" + airport.getDireccion().getPais();
                info += "\nDirección: C\\ " + airport.getDireccion().getCalle() + " " + airport.getDireccion().getNumero() + ", " + airport.getDireccion().getCiudad();
                info += "\nAño de inauguración: " + airport.getAnio_inauguracion();
                info += "\nCapacidad: " + airport.getCapacidad();
                info += "\nAviones:";
                ObservableList<Avion> aviones = AvionDAO.cargarListado(airport);
                for (Avion avion:aviones) {
                    info += "\n\tModelo: " + avion.getModelo();
                    info += "\n\tNúmero de asientos: " + avion.getNumero_asientos();
                    info += "\n\tVelocidad máxima: " + avion.getVelocidad_maxima();
                    if (avion.isActivado()) {
                        info += "\n\tActivado";
                    } else {
                        info+="\n\tDesactivado";
                    }
                }
                info += "\nPúblico";
                info += "\nFinanciación: " + aeropuertoPublico.getFinanciacion();
                info += "\nNúmero de trabajadores: " + aeropuertoPublico.getNum_trabajadores();
            } else {
                // Aeropuerto Privado
                AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                Aeropuerto airport = aeropuertoPrivado.getAeropuerto();
                info = "Nombre: " + airport.getNombre();
                info += "\nPaís:" + airport.getDireccion().getPais();
                info += "\nDirección: C\\ " + airport.getDireccion().getCalle() + " " + airport.getDireccion().getNumero() + ", " + airport.getDireccion().getCiudad();
                info += "\nAño de inauguración: " + airport.getAnio_inauguracion();
                info += "\nCapacidad: " + airport.getCapacidad();
                info += "\nAviones:";
                ObservableList<Avion> aviones = AvionDAO.cargarListado(airport);
                for (Avion avion:aviones) {
                    info += "\n\tModelo: " + avion.getModelo();
                    info += "\n\tNúmero de asientos: " + avion.getNumero_asientos();
                    info += "\n\tVelocidad máxima: " + avion.getVelocidad_maxima();
                    if (avion.isActivado()) {
                        info += "\n\tActivado";
                    } else {
                        info += "\n\tDesactivado";
                    }
                }
                info += "\nPrivado";
                info += "\nNúmero de socios: " + aeropuertoPrivado.getNumero_socios();
            }
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setTitle("Información");
            alerta.setContentText(info);
            alerta.showAndWait();
        }
    }

    /**
     * Abre un menú para añadir un avión nuevo
     *
     * @param event el evento
     */
    @FXML
    void aniadirAvion(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AniadirAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("AVIONES - AÑADIR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            mensajeAlerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Abre un menú para activar o desactivar un avión
     *
     * @param event el evento
     */
    @FXML
    void activarDesactivarAvion(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ActivarDesactivarAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("AVIONES - ACTIVAR/DESACTIVAR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            mensajeAlerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Abre un menú para borrar un avión
     *
     * @param event el evento
     */
    @FXML
    void borrarAvion(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BorrarAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("AVIONES - BORRAR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            mensajeAlerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que carga los aeropuertos públicos en la lista
     */
    public void cargarPublicos() {
        tabla.getSelectionModel().clearSelection();
        filtroNombre.setText(null);
        masterData.clear();
        filteredData.clear();
        tabla.getItems().clear();
        tabla.getColumns().clear();

        TableColumn<AeropuertoPublico, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getId()));
        TableColumn<AeropuertoPublico, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getNombre()));
        TableColumn<AeropuertoPublico, String> colPais = new TableColumn<>("País");
        colPais.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getPais()));
        TableColumn<AeropuertoPublico, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCiudad()));
        TableColumn<AeropuertoPublico, String> colCalle = new TableColumn<>("Calle");
        colCalle.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCalle()));
        TableColumn<AeropuertoPublico, Integer> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getNumero()));
        TableColumn<AeropuertoPublico, Integer> colAnio = new TableColumn<>("Año");
        colAnio.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getAnio_inauguracion()));
        TableColumn<AeropuertoPublico, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getCapacidad()));
        TableColumn<AeropuertoPublico, BigDecimal> colFinanciacion = new TableColumn<>("Financiación");
        colFinanciacion.setCellValueFactory(new PropertyValueFactory("financiacion"));
        TableColumn<AeropuertoPublico, Integer> colTrabajadores = new TableColumn<>("Nº Trabajadores");
        colTrabajadores.setCellValueFactory(new PropertyValueFactory("num_trabajadores"));
        tabla.getColumns().addAll(colId,colNombre,colPais,colCiudad,colCalle,colNumero,colAnio,colCapacidad,colFinanciacion,colTrabajadores);

        ObservableList<AeropuertoPublico> aeropuertos = AeropuertoPublicoDAO.cargarListado();
        masterData.setAll(aeropuertos);
        tabla.setItems(aeropuertos);
    }

    /**
     * Función que carga los aeropuertos privados en la lista
     */
    public void cargarPrivados() {
        tabla.getSelectionModel().clearSelection();
        filtroNombre.setText(null);
        masterData.clear();
        filteredData.clear();
        tabla.getItems().clear();
        tabla.getColumns().clear();

        TableColumn<AeropuertoPrivado, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getId()));
        TableColumn<AeropuertoPrivado, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getNombre()));
        TableColumn<AeropuertoPrivado, String> colPais = new TableColumn<>("País");
        colPais.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getPais()));
        TableColumn<AeropuertoPrivado, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCiudad()));
        TableColumn<AeropuertoPrivado, String> colCalle = new TableColumn<>("Calle");
        colCalle.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCalle()));
        TableColumn<AeropuertoPrivado, Integer> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getNumero()));
        TableColumn<AeropuertoPrivado, Integer> colAnio = new TableColumn<>("Año");
        colAnio.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getAnio_inauguracion()));
        TableColumn<AeropuertoPrivado, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getCapacidad()));
        TableColumn<AeropuertoPrivado, Integer> colSocios = new TableColumn<>("Nº Socios");
        colSocios.setCellValueFactory(new PropertyValueFactory("numero_socios"));
        tabla.getColumns().addAll(colId,colNombre,colPais,colCiudad,colCalle,colNumero,colAnio,colCapacidad,colSocios);

        ObservableList<AeropuertoPrivado> aeropuertos = AeropuertoPrivadoDAO.cargarListado();
        masterData.setAll(aeropuertos);
        tabla.setItems(aeropuertos);
    }

    /**
     * Función que habilita o deshabilita los menus de aeropuertos
     *
     * @param deshabilitado true/false
     */
    public void deshabilitarMenus(boolean deshabilitado) {
        menuEditarAeropuerto.setDisable(deshabilitado);
        menuBorrarAeropuerto.setDisable(deshabilitado);
        menuInfoAeropuerto.setDisable(deshabilitado);
    }

    /**
     * Filtra la tabla de aeropuertos por nombre
     */
    public void filtrar() {
        String valor = filtroNombre.getText();
        valor = valor.toLowerCase();
        if (valor.isEmpty()) {
            tabla.setItems(masterData);
        } else {
            filteredData.clear();
            if (masterData.getFirst() instanceof AeropuertoPublico) {
                for (Object aeropuerto : masterData) {
                    AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                    String nombre = aeropuertoPublico.getAeropuerto().getNombre();
                    nombre = nombre.toLowerCase();
                    if (nombre.contains(valor)) {
                        filteredData.add(aeropuertoPublico);
                    }
                }
            } else {
                for (Object aeropuerto : masterData) {
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                    String nombre = aeropuertoPrivado.getAeropuerto().getNombre();
                    nombre = nombre.toLowerCase();
                    if (nombre.startsWith(valor)) {
                        filteredData.add(aeropuertoPrivado);
                    }
                }
            }
            tabla.setItems(filteredData);
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