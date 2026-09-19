package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Asistencia;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoResumen;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.AsistenciaRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.EmpleadoRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.AsistenciaService;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.EmpleadoService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class AsistenciaController implements Initializable {

    @FXML private ComboBox<EmpleadoResumen> comboEmpleados;
    @FXML private Label labelFechaHoy;

    @FXML private TableView<Asistencia> tableAsistencias;
    @FXML private TableColumn<Asistencia, String> columnEmpleado;
    @FXML private TableColumn<Asistencia, java.time.LocalTime> columnEntrada;
    @FXML private TableColumn<Asistencia, java.time.LocalTime> columnSalida;
    @FXML private TableColumn<Asistencia, String> columnEstado;

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final AsistenciaService service = new AsistenciaService(new AsistenciaRepository());
    private final EmpleadoService empleadoService = new EmpleadoService(new EmpleadoRepository());
    private final SceneManager sceneManager = new SceneManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelFechaHoy.setText("Fecha: " + LocalDate.now());

        comboEmpleados.setItems(FXCollections.observableArrayList(empleadoService.listarEmpleadosActivos()));

        columnEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        columnEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        columnSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        columnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        aplicarFormatoHora(columnEntrada);
        aplicarFormatoHora(columnSalida);

        cargarTabla();
    }

    private void aplicarFormatoHora(TableColumn<Asistencia, java.time.LocalTime> columna) {
        columna.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(java.time.LocalTime hora, boolean vacio) {
                super.updateItem(hora, vacio);
                setText((vacio || hora == null) ? "" : hora.format(FORMATO_HORA));
            }
        });
    }

    private void cargarTabla() {
        ObservableList<Asistencia> lista = FXCollections.observableArrayList(service.listarAsistenciasDeHoy());
        tableAsistencias.setItems(lista);
    }

    @FXML
    private void handleMarcarEntrada() {
        try {
            EmpleadoResumen empleado = comboEmpleados.getValue();
            if (empleado == null) {
                throw new RuntimeException("Selecciona un empleado antes de marcar entrada");
            }

            boolean marcado = service.marcarEntrada(empleado.getIdEmpleado());

            if (marcado) {
                sceneManager.showAlertInfo("Entrada registrada", "Marcaje de asistencia",
                        "Se marcó la entrada de " + empleado.getNombreCompleto() + " correctamente.",
                        Alert.AlertType.INFORMATION);
                cargarTabla();
            } else {
                sceneManager.showAlertInfo("No se pudo marcar", "Error",
                        "La entrada no pudo ser registrada.", Alert.AlertType.ERROR);
            }
        } catch (RuntimeException e) {
            sceneManager.showAlertInfo("Acción inválida", "Verificar", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleMarcarSalida() {
        try {
            EmpleadoResumen empleado = comboEmpleados.getValue();
            if (empleado == null) {
                throw new RuntimeException("Selecciona un empleado antes de marcar salida");
            }

            boolean marcado = service.marcarSalida(empleado.getIdEmpleado());

            if (marcado) {
                sceneManager.showAlertInfo("Salida registrada", "Marcaje de asistencia",
                        "Se marcó la salida de " + empleado.getNombreCompleto() + " correctamente.",
                        Alert.AlertType.INFORMATION);
                cargarTabla();
            } else {
                sceneManager.showAlertInfo("No se pudo marcar", "Error",
                        "La salida no pudo ser registrada.", Alert.AlertType.ERROR);
            }
        } catch (RuntimeException e) {
            sceneManager.showAlertInfo("Acción inválida", "Verificar", e.getMessage(), Alert.AlertType.WARNING);
        }
    }
}
