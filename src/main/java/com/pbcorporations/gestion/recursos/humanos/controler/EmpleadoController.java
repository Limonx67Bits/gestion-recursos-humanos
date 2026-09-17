package main.java.com.pbcorporations.gestion.recursos.humanos.controler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.EmpleadoService;

public class EmpleadoController implements Initializable {

    @FXML private TableView<EmpleadoModel> tablaEmpleados;
    @FXML private TableColumn<EmpleadoModel, String> colId; 
    @FXML private TableColumn<EmpleadoModel, String> colNombre;
    @FXML private TableColumn<EmpleadoModel, String> colDpi;
    @FXML private TableColumn<EmpleadoModel, String> colTelefono;
    @FXML private TableColumn<EmpleadoModel, String> colPuesto;
    @FXML private TableColumn<EmpleadoModel, String> colDepartamento;

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDpi;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtPuesto;
    @FXML private TextField txtDepartamento;

    private final EmpleadoService service = new EmpleadoService();
    private final ObservableList<EmpleadoModel> listaEmpleados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDpi.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));

        tablaEmpleados.setItems(listaEmpleados);
        
        tablaEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getIdEmpleado());
                txtNombre.setText(newSelection.getNombre());
                txtDpi.setText(newSelection.getDpi());
                txtTelefono.setText(newSelection.getTelefono());
                txtPuesto.setText(newSelection.getPuesto());
                txtDepartamento.setText(newSelection.getDepartamento());
            }
        });

        cargarDatos();
    }

    private void cargarDatos() {
        try {
            listaEmpleados.clear();
            listaEmpleados.addAll(service.obtenerEmpleados());
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar datos", e.getMessage());
        }
    }

    @FXML
    private void handleGuardar() {
        try {
            EmpleadoModel emp = new EmpleadoModel(
                null,
                txtNombre.getText(),
                txtDpi.getText(),
                txtTelefono.getText(),
                txtPuesto.getText(),
                txtDepartamento.getText()
            );
            if (service.guardarEmpleado(emp)) {
                cargarDatos();
                limpiarCampos();
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar", e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        String id = txtId.getText();
        if (id == null || id.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un empleado de la tabla.");
            return;
        }
        try {
            if (service.eliminarEmpleado(id)) {
                cargarDatos();
                limpiarCampos();
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", e.getMessage());
        }
    }

    @FXML
    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtDpi.clear();
        txtTelefono.clear();
        txtPuesto.clear();
        txtDepartamento.clear();
        tablaEmpleados.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}