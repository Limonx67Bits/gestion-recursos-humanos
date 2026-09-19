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
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.DepartamentoRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.DepartamentoService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class DepartamentoController implements Initializable {

    @FXML private TableView<Departamento> tableDepartamentos;
    @FXML private TableColumn<Departamento, Integer> columnId;
    @FXML private TableColumn<Departamento, String> columnNombre;
    @FXML private TableColumn<Departamento, String> columnDescripcion;

    @FXML private TextField fieldNombre;
    @FXML private TextField fieldDescripcion;

   
    private final DepartamentoService service = new DepartamentoService(new DepartamentoRepository());
    private final SceneManager sceneManager = new SceneManager();

    private Departamento departamentoSeleccionado;

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
        ObservableList<Departamento> lista = FXCollections.observableArrayList(service.listarDepartamentos());
        tableDepartamentos.setItems(lista);
    }

    @FXML
    private void handleGuardar() {
        try {
            Departamento d = new Departamento(fieldNombre.getText(), fieldDescripcion.getText());
            boolean guardado = service.guardarDepartamento(d);

            if (guardado) {
                sceneManager.showAlertInfo("Departamento guardado", "Registro exitoso",
                        "El departamento fue guardado correctamente.", Alert.AlertType.INFORMATION);
                limpiarFormulario();
                cargarTabla();
            } else {
                sceneManager.showAlertInfo("No se pudo guardar", "Error",
                        "El departamento no pudo ser guardado.", Alert.AlertType.ERROR);
            }
        } catch (RuntimeException e) {
            sceneManager.showAlertInfo("Datos inválidos", "Verificar campos", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleActualizar() {
        try {
            if (departamentoSeleccionado == null) {
                throw new RuntimeException("Selecciona un departamento de la tabla antes de actualizar");
            }
            departamentoSeleccionado.setNombreDepartamento(fieldNombre.getText());
            departamentoSeleccionado.setDescripcion(fieldDescripcion.getText());

            boolean actualizado = service.actualizarDepartamento(departamentoSeleccionado);

            if (actualizado) {
                sceneManager.showAlertInfo("Actualización exitosa", "Actualizando...",
                        "El departamento fue modificado correctamente.", Alert.AlertType.INFORMATION);
                limpiarFormulario();
                cargarTabla();
            } else {
                sceneManager.showAlertInfo("No se pudo actualizar", "Error",
                        "El departamento no pudo ser actualizado.", Alert.AlertType.ERROR);
            }
        } catch (RuntimeException e) {
            sceneManager.showAlertInfo("Datos inválidos", "Verificar campos", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleEliminar() {
        try {
            if (departamentoSeleccionado == null) {
                throw new RuntimeException("Selecciona un departamento de la tabla antes de eliminar");
            }

            boolean eliminado = service.eliminarDepartamento(departamentoSeleccionado.getIdDepartamento());

            if (eliminado) {
                sceneManager.showAlertInfo("Eliminación exitosa", "Eliminando...",
                        "El departamento fue eliminado correctamente.", Alert.AlertType.INFORMATION);
                limpiarFormulario();
                cargarTabla();
            } else {
               
                sceneManager.showAlertInfo("No se pudo eliminar", "Error",
                        "El departamento no pudo ser eliminado. Puede que tenga puestos asignados.",
                        Alert.AlertType.ERROR);
            }
        } catch (RuntimeException e) {
            sceneManager.showAlertInfo("Acción inválida", "Verificar selección", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleLimpiar() {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        fieldNombre.clear();
        fieldDescripcion.clear();
        departamentoSeleccionado = null;
        tableDepartamentos.getSelectionModel().clearSelection();
    }
}
