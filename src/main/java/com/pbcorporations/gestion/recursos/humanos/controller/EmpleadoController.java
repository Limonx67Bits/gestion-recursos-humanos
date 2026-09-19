package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.EmpleadoService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class EmpleadoController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtDpi;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtPuesto;
    @FXML private TextField txtDepartamento;
    @FXML private TextField txtTipoVialidad;
    @FXML private TextField txtNumeroVialidad;
    @FXML private TextField txtNumeroCasa;
    @FXML private TextField txtColonia;
    @FXML private TextField txtZona;
    @FXML private TextField txtMunicipio;
    @FXML private TextField txtDepartamentoDir;
    @FXML private TextField txtCodigoPostal;

    @FXML private TableView<EmpleadoModel> tablaEmpleados;
    @FXML private TableColumn<EmpleadoModel, String> colId;
    @FXML private TableColumn<EmpleadoModel, String> colDpi;
    @FXML private TableColumn<EmpleadoModel, String> colTelefono;
    @FXML private TableColumn<EmpleadoModel, String> colCorreo;
    @FXML private TableColumn<EmpleadoModel, String> colPuesto;
    @FXML private TableColumn<EmpleadoModel, String> colDepartamento;
    @FXML private TableColumn<EmpleadoModel, String> colColonia;
    @FXML private TableColumn<EmpleadoModel, Integer> colZona;
    @FXML private TableColumn<EmpleadoModel, String> colMunicipio;
    @FXML private TableColumn<EmpleadoModel, String> colCodigoPostal;

    private final EmpleadoService empleadoService = new EmpleadoService();
    private ObservableList<EmpleadoModel> listaEmpleados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
        cargarEmpleados();

        tablaEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                seleccionarFila(newSelection);
            }
        });
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colDpi.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colColonia.setCellValueFactory(new PropertyValueFactory<>("coloniaBarrio"));
        colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));
        colMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
    }

    private void cargarEmpleados() {
        try {
            listaEmpleados.clear();
            listaEmpleados.addAll(empleadoService.obtenerEmpleados());
            tablaEmpleados.setItems(listaEmpleados);
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los datos: " + e.getMessage());
        }
    }

    @FXML
    public void handleGuardar() {
        try {
            EmpleadoModel emp = crearObjetoDesdeCampos();

            if (txtId.getText() == null || txtId.getText().trim().isEmpty()) {
                empleadoService.guardarEmpleado(emp);
                SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Empleado guardado correctamente.");
            } else {
                empleadoService.actualizarEmpleado(emp);
                SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Empleado actualizado correctamente.");
            }

            limpiarCampos();
            cargarEmpleados();
        } catch (NumberFormatException e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Error", "La Zona debe ser un valor numérico.");
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Error", e.getMessage());
        }
    }

    @FXML
    public void handleActualizar() {
        if (txtId.getText() == null || txtId.getText().trim().isEmpty()) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Advertencia", "Debe seleccionar un empleado de la tabla para actualizar.");
            return;
        }

        try {
            EmpleadoModel emp = crearObjetoDesdeCampos();
            empleadoService.actualizarEmpleado(emp);
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Empleado actualizado correctamente.");

            limpiarCampos();
            cargarEmpleados();
        } catch (NumberFormatException e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Error", "La Zona debe ser un valor numérico.");
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Error", e.getMessage());
        }
    }

    private EmpleadoModel crearObjetoDesdeCampos() {
        EmpleadoModel emp = new EmpleadoModel();
        emp.setIdEmpleado(txtId.getText());
        emp.setDpi(txtDpi.getText());

        if (txtNombres != null && !txtNombres.getText().trim().isEmpty()) {
            String[] partesNombres = txtNombres.getText().trim().split("\\s+", 2);
            emp.setPrimerNombre(partesNombres[0]);
            emp.setSegundoNombre(partesNombres.length > 1 ? partesNombres[1] : "");
        }

        if (txtApellidos != null && !txtApellidos.getText().trim().isEmpty()) {
            String[] partesApellidos = txtApellidos.getText().trim().split("\\s+", 2);
            emp.setPrimerApellido(partesApellidos[0]);
            emp.setSegundoApellido(partesApellidos.length > 1 ? partesApellidos[1] : "");
        }

        emp.setTelefono(txtTelefono.getText());
        emp.setCorreo(txtCorreo.getText());
        
        if (txtTipoVialidad != null) emp.setTipoVialidad(txtTipoVialidad.getText());
        if (txtNumeroVialidad != null) emp.setNumeroVialidad(txtNumeroVialidad.getText());
        if (txtNumeroCasa != null) emp.setNumeroCasaLote(txtNumeroCasa.getText());
        if (txtColonia != null) emp.setColoniaBarrio(txtColonia.getText());
        
        int zonaVal = 0;
        if (txtZona != null && txtZona.getText() != null && !txtZona.getText().trim().isEmpty()) {
            zonaVal = Integer.parseInt(txtZona.getText().trim());
        }
        emp.setZona(zonaVal);
        
        if (txtMunicipio != null) emp.setMunicipio(txtMunicipio.getText());
        if (txtDepartamentoDir != null) emp.setDepartamentoDir(txtDepartamentoDir.getText());
        if (txtCodigoPostal != null) emp.setCodigoPostal(txtCodigoPostal.getText());
        
        emp.setPuesto(txtPuesto.getText());
        emp.setDepartamento(txtDepartamento.getText());

        return emp;
    }

    @FXML
    public void handleEliminar() {
        EmpleadoModel seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            SceneManager.showAlertInfo(Alert.AlertType.WARNING, "Advertencia", "Debe seleccionar un empleado para eliminar.");
            return;
        }

        try {
            empleadoService.eliminarEmpleado(seleccionado.getIdEmpleado());
            SceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Empleado eliminado correctamente.");
            limpiarCampos();
            cargarEmpleados();
        } catch (Exception e) {
            SceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void seleccionarFila(EmpleadoModel emp) {
        txtId.setText(emp.getIdEmpleado());
        txtDpi.setText(emp.getDpi());
        
        if (txtNombres != null) txtNombres.setText(emp.getNombres());
        if (txtApellidos != null) txtApellidos.setText(emp.getApellidos());
        
        txtTelefono.setText(emp.getTelefono());
        txtCorreo.setText(emp.getCorreo());
        
        if (txtTipoVialidad != null) txtTipoVialidad.setText(emp.getTipoVialidad());
        if (txtNumeroVialidad != null) txtNumeroVialidad.setText(emp.getNumeroVialidad());
        if (txtNumeroCasa != null) txtNumeroCasa.setText(emp.getNumeroCasaLote());
        if (txtColonia != null) txtColonia.setText(emp.getColoniaBarrio());
        if (txtZona != null) txtZona.setText(String.valueOf(emp.getZona()));
        if (txtMunicipio != null) txtMunicipio.setText(emp.getMunicipio());
        if (txtDepartamentoDir != null) txtDepartamentoDir.setText(emp.getDepartamentoDir());
        if (txtCodigoPostal != null) txtCodigoPostal.setText(emp.getCodigoPostal());
        
        txtPuesto.setText(emp.getPuesto());
        txtDepartamento.setText(emp.getDepartamento());
    }

    @FXML
    public void limpiarCampos() {
        txtId.clear();
        if (txtNombres != null) txtNombres.clear();
        if (txtApellidos != null) txtApellidos.clear();
        txtDpi.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtPuesto.clear();
        txtDepartamento.clear();
        if (txtTipoVialidad != null) txtTipoVialidad.clear();
        if (txtNumeroVialidad != null) txtNumeroVialidad.clear();
        if (txtNumeroCasa != null) txtNumeroCasa.clear();
        if (txtColonia != null) txtColonia.clear();
        if (txtZona != null) txtZona.clear();
        if (txtMunicipio != null) txtMunicipio.clear();
        if (txtDepartamentoDir != null) txtDepartamentoDir.clear();
        if (txtCodigoPostal != null) txtCodigoPostal.clear();
        tablaEmpleados.getSelectionModel().clearSelection();
    }
}