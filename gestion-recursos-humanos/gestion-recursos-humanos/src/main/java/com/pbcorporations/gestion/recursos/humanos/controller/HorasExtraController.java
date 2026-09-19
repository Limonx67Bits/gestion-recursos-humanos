package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Asistencia;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.AsistenciaRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.AsistenciaService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class HorasExtraController implements Initializable {

    @FXML private TableView<Asistencia> tableAsistencias;
    @FXML private TableColumn<Asistencia, String> columnEmpleado;
    @FXML private TableColumn<Asistencia, java.time.LocalDate> columnFecha;
    @FXML private TableColumn<Asistencia, java.time.LocalTime> columnEntrada;
    @FXML private TableColumn<Asistencia, java.time.LocalTime> columnSalida;
    @FXML private TableColumn<Asistencia, Double> columnHorasExtra;

    @FXML private TextField fieldHorasExtra;

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final AsistenciaService service = new AsistenciaService(new AsistenciaRepository());
    private final SceneManager sceneManager = new SceneManager();

    private Asistencia asistenciaSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        columnSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        columnHorasExtra.setCellValueFactory(new PropertyValueFactory<>("horasExtraTrabajadas"));

        aplicarFormatoHora(columnEntrada);
        aplicarFormatoHora(columnSalida);

        cargarTabla();

        tableAsistencias.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionada) -> {
            if (seleccionada != null) {
                asistenciaSeleccionada = seleccionada;
                fieldHorasExtra.setText(String.valueOf(seleccionada.getHorasExtraTrabajadas()));
            }
        });
    }

    private void aplicarFormatoHora(TableColumn<Asistencia, java.time.LocalTime> columna) {
        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(java.time.LocalTime hora, boolean vacio) {
                super.updateItem(hora, vacio);
                setText((vacio || hora == null) ? "" : hora.format(FORMATO_HORA));
            }
        });
    }

    private void cargarTabla() {
        
        ObservableList<Asistencia> lista = FXCollections.observableArrayList(service.listarAsistenciasParaHorasExtra());
        tableAsistencias.setItems(lista);
    }

    @FXML
    private void handleGuardarHorasExtra() {
        try {
            if (asistenciaSeleccionada == null) {
                throw new RuntimeException("Selecciona un registro de asistencia de la tabla");
            }

            double horas = Double.parseDouble(fieldHorasExtra.getText().trim());
            boolean guardado = service.registrarHorasExtra(asistenciaSeleccionada.getIdAsistencia(), horas);

            if (guardado) {
                sceneManager.showAlertInfo("Horas extra registradas", "Registro exitoso",
                        "Se guardaron " + horas + " horas extra para " + asistenciaSeleccionada.getNombreEmpleado() + ".",
                        Alert.AlertType.INFORMATION);
                limpiarFormulario();
                cargarTabla();
            } else {
                sceneManager.showAlertInfo("No se pudo guardar", "Error",
                        "Las horas extra no pudieron ser registradas.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            sceneManager.showAlertInfo("Valor inválido", "Verificar campo",
                    "Las horas extra deben ser un número (ej. 2 o 2.5).", Alert.AlertType.WARNING);
        } catch (RuntimeException e) {
            sceneManager.showAlertInfo("Acción inválida", "Verificar", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void limpiarFormulario() {
        fieldHorasExtra.clear();
        asistenciaSeleccionada = null;
        tableAsistencias.getSelectionModel().clearSelection();
    }
}
