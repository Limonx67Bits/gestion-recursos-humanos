package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Departamento;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.DepartamentoService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class DepartamentoController implements Initializable {

    @FXML private TableView<Departamento> tableDepartamentos;
    @FXML private TableColumn<Departamento, Integer> columnId;
    @FXML private TableColumn<Departamento, String> columnNombre;
    @FXML private TableColumn<Departamento, String> columnDescripcion;

    @FXML private TextField fieldNombre;
    @FXML private TextField fieldDescripcion;

    private final DepartamentoService service;
    private final SceneManager sceneManager;

    private Departamento departamentoSeleccionado;

    public DepartamentoController(DepartamentoService service, SceneManager sceneManager) {
        this.service = service;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idDepartamento"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        cargarTabla();

        tableDepartamentos.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            if (seleccionado != null) {
                departamentoSeleccionado = seleccionado;
                fieldNombre.setText(seleccionado.getNombreDepartamento());
                fieldDescripcion.setText(seleccionado.getDescripcion());
            }
        });
    }

    private void cargarTabla() {
        try {
            ObservableList<Departamento> lista = FXCollections.observableArrayList(service.listarDepartamentos());
            tableDepartamentos.setItems(lista);
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los departamentos: " + e.getMessage());
        }
    }

    @FXML
    public void handleGuardar() {
        try {
            Departamento d = new Departamento(fieldNombre.getText(), fieldDescripcion.getText());
            service.guardarDepartamento(d);
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "El departamento fue guardado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Verificar campos", e.getMessage());
        }
    }

    @FXML
    public void handleActualizar() {
        try {
            if (departamentoSeleccionado == null) {
                throw new RuntimeException("Selecciona un departamento de la tabla antes de actualizar");
            }
            departamentoSeleccionado.setNombreDepartamento(fieldNombre.getText());
            departamentoSeleccionado.setDescripcion(fieldDescripcion.getText());

            service.actualizarDepartamento(departamentoSeleccionado);
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "El departamento fue actualizado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Verificar campos", e.getMessage());
        }
    }

    @FXML
    public void handleEliminar() {
        try {
            if (departamentoSeleccionado == null) {
                throw new RuntimeException("Selecciona un departamento de la tabla antes de eliminar");
            }
            service.eliminarDepartamento(departamentoSeleccionado.getIdDepartamento());
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "El departamento fue eliminado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } catch (Exception e) {
            // Motivo más probable si falla: tiene puestos asociados (FK con ON DELETE RESTRICT).
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "No se pudo eliminar",
                    "El departamento no pudo ser eliminado. Puede que tenga puestos asignados. " + e.getMessage());
        }
    }

    @FXML
    public void handleLimpiar() {
        limpiarFormulario();
    }

    @FXML
    public void regresar() {
        try {
            sceneManager.showDashboardView();
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudo regresar al dashboard: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        fieldNombre.clear();
        fieldDescripcion.clear();
        departamentoSeleccionado = null;
        tableDepartamentos.getSelectionModel().clearSelection();
    }
}
