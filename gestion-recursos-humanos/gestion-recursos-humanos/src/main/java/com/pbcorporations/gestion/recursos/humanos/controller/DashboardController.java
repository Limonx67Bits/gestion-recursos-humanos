package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class DashboardController implements Initializable {

    @FXML private StackPane contentPane;

    @FXML private Button btnDepartamentos;
    @FXML private Button btnAsistencia;
    @FXML private Button btnHorasExtra;

    // NOTA: cuando agreguen sus propias pantallas (Empleados,
    // Nómina, Usuarios, etc.), solo tienen que
    //    Agregar un @FXML Button más aquí y en dashboard.fxml
    //    Agregar un handleXxx() que llame a cargarVista
    // No hay que tocar nada más de esta clase.

    private Button botonActivo;
    private final SceneManager sceneManager = new SceneManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    cargarVista("/main/resources/fxml/departamentos.fxml", btnDepartamentos);
    }

    @FXML
    private void handleDepartamentos() {
    cargarVista("/main/resources/fxml/departamentos.fxml", btnDepartamentos);
    }

    @FXML
    private void handleAsistencia() {
    cargarVista("/main/resources/fxml/asistencia.fxml", btnAsistencia);
    }

    @FXML
    private void handleHorasExtra() {
    cargarVista("/main/resources/fxml/horas_extra.fxml", btnHorasExtra);
    }

    private void cargarVista(String rutaFxml, Button botonPresionado) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(rutaFxml));
            contentPane.getChildren().setAll(vista);
            marcarBotonActivo(botonPresionado);

        } catch (IOException e) {
            sceneManager.showAlertInfo("Error al cargar la vista", "Navegación",
                    "No se pudo cargar " + rutaFxml + ": " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void marcarBotonActivo(Button nuevoActivo) {
        if (botonActivo != null) {
            botonActivo.getStyleClass().remove("menu-button-active");
        }
        nuevoActivo.getStyleClass().add("menu-button-active");
        botonActivo = nuevoActivo;
    }
}
