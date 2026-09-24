package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Asistencia;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.AsistenciaService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class AsistenciaController implements Initializable {

    // --- Marcaje de asistencia ---
    @FXML private ComboBox<EmpleadoModel> comboEmpleados;
    @FXML private Label labelFechaHoy;

    @FXML private TableView<Asistencia> tableAsistenciasHoy;
    @FXML private TableColumn<Asistencia, String> columnEmpleadoHoy;
    @FXML private TableColumn<Asistencia, LocalTime> columnEntrada;
    @FXML private TableColumn<Asistencia, LocalTime> columnSalida;
    @FXML private TableColumn<Asistencia, String> columnEstado;

    // --- Registro de horas extra ---
    @FXML private TableView<Asistencia> tableHorasExtra;
    @FXML private TableColumn<Asistencia, String> columnEmpleadoHE;
    @FXML private TableColumn<Asistencia, LocalDate> columnFechaHE;
    @FXML private TableColumn<Asistencia, Double> columnHorasExtra;
    @FXML private TextField fieldHorasExtra;

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final AsistenciaService service;
    private final SceneManager sceneManager;

    private Asistencia asistenciaSeleccionada;

    public AsistenciaController(AsistenciaService service, SceneManager sceneManager) {
        this.service = service;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelFechaHoy.setText("Fecha: " + LocalDate.now());

        configurarComboEmpleados();

        columnEmpleadoHoy.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        columnEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        columnSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        columnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        aplicarFormatoHora(columnEntrada);
        aplicarFormatoHora(columnSalida);

        columnEmpleadoHE.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        columnFechaHE.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnHorasExtra.setCellValueFactory(new PropertyValueFactory<>("horasExtraTrabajadas"));

        cargarAsistenciasHoy();
        cargarAsistenciasParaHorasExtra();

        tableHorasExtra.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            if (seleccionada != null) {
                asistenciaSeleccionada = seleccionada;
                fieldHorasExtra.setText(String.valueOf(seleccionada.getHorasExtraTrabajadas()));
            }
        });
    }

    private void configurarComboEmpleados() {
        try {
            comboEmpleados.setItems(FXCollections.observableArrayList(service.listarEmpleados()));
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los empleados: " + e.getMessage());
        }

        comboEmpleados.setConverter(new StringConverter<>() {
            @Override
            public String toString(EmpleadoModel emp) {
                return emp == null ? "" : emp.getNombreCompleto();
            }

            @Override
            public EmpleadoModel fromString(String string) {
                return null;
            }
        });
    }

    private void aplicarFormatoHora(TableColumn<Asistencia, LocalTime> columna) {
        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalTime hora, boolean vacio) {
                super.updateItem(hora, vacio);
                setText((vacio || hora == null) ? "" : hora.format(FORMATO_HORA));
            }
        });
    }

    private void cargarAsistenciasHoy() {
        try {
            ObservableList<Asistencia> lista = FXCollections.observableArrayList(service.listarAsistenciasDeHoy());
            tableAsistenciasHoy.setItems(lista);
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudieron cargar las asistencias de hoy: " + e.getMessage());
        }
    }

    private void cargarAsistenciasParaHorasExtra() {
        try {
            // Solo asistencias con salida ya marcada: no tiene sentido asignar
            // horas extra a una jornada que todavía no ha cerrado.
            ObservableList<Asistencia> lista = FXCollections.observableArrayList(service.listarAsistenciasParaHorasExtra());
            tableHorasExtra.setItems(lista);
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los registros para horas extra: " + e.getMessage());
        }
    }

    @FXML
    public void handleMarcarEntrada() {
        try {
            EmpleadoModel empleado = comboEmpleados.getValue();
            if (empleado == null) {
                throw new RuntimeException("Selecciona un empleado antes de marcar entrada");
            }
            service.marcarEntrada(empleado.getIdEmpleado());
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Entrada registrada",
                    "Se marcó la entrada de " + empleado.getNombreCompleto() + ".");
            cargarAsistenciasHoy();
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Verificar", e.getMessage());
        }
    }

    @FXML
    public void handleMarcarSalida() {
        try {
            EmpleadoModel empleado = comboEmpleados.getValue();
            if (empleado == null) {
                throw new RuntimeException("Selecciona un empleado antes de marcar salida");
            }
            service.marcarSalida(empleado.getIdEmpleado());
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Salida registrada",
                    "Se marcó la salida de " + empleado.getNombreCompleto() + ".");
            cargarAsistenciasHoy();
            cargarAsistenciasParaHorasExtra();
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Verificar", e.getMessage());
        }
    }

    @FXML
    public void handleGuardarHorasExtra() {
        try {
            if (asistenciaSeleccionada == null) {
                throw new RuntimeException("Selecciona un registro de la tabla de abajo");
            }
            double horas = Double.parseDouble(fieldHorasExtra.getText().trim());
            service.registrarHorasExtra(asistenciaSeleccionada.getIdAsistencia(), horas);
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Horas extra registradas",
                    "Se guardaron " + horas + " horas extra para " + asistenciaSeleccionada.getNombreEmpleado() + ".");
            fieldHorasExtra.clear();
            asistenciaSeleccionada = null;
            tableHorasExtra.getSelectionModel().clearSelection();
            cargarAsistenciasParaHorasExtra();
        } catch (NumberFormatException e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Valor inválido", "Las horas extra deben ser un número (ej. 2 o 2.5).");
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Verificar", e.getMessage());
        }
    }

    @FXML
    public void regresar() {
        try {
            sceneManager.showDashboardView();
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudo regresar al dashboard: " + e.getMessage());
        }
    }
}
