package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.EmpleadoService;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.NominasService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

/**
 *
 * @author el doomslayer
 */
public class SalarioController  implements Initializable{
    private NominasService nominasService;
    private SceneManager sceneManager;
    
    private ObservableList<EmpleadoModel> listaEmpleados = FXCollections.observableArrayList();
    private EmpleadoModel seleccionado;
    
    @FXML private TableView<EmpleadoModel> tablaEmpleados;
    @FXML private TableColumn<EmpleadoModel, String> colId;
    @FXML private TableColumn<EmpleadoModel, String> colCorreo;
    @FXML private TableColumn<EmpleadoModel, String> colPuesto;
    @FXML private TableColumn<EmpleadoModel, String> colSueldo;
    
    @FXML private Button btnPagarNomina;
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtSueldo;
    
    public SalarioController(){
        
    }
    public SalarioController(NominasService nominasService, SceneManager sceneManager){
        this.nominasService = nominasService;
        this.sceneManager = sceneManager;
    }
    
     @Override
    public void initialize(URL location, ResourceBundle resources) {
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        int periodoMes = fechaActual.getMonthValue(); 
        int periodoAnio = fechaActual.getYear(); 
            nominasService.crearNomina(periodoMes, periodoAnio);
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
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
    }
       
    protected void cargarEmpleados() {
        try {
     
            listaEmpleados.clear();
            listaEmpleados.addAll(nominasService.obtenerListaDeNominas());
            tablaEmpleados.setItems(listaEmpleados);
        } catch (Exception e) {
            sceneManager.showAlertInfo(AlertType.ERROR, "Error", "No se pudieron cargar los datos: " + e.getMessage());
        }
    }
    
    private void seleccionarFila(EmpleadoModel empleado) {
        
        if (empleado != null) {
            this.seleccionado = empleado;
            System.out.println("Empleado seleccionado ID: " + empleado.getIdEmpleado());
            System.out.println("Correo: " + empleado.getCorreo());
            System.out.println("Puesto: " + empleado.getPuesto());
            System.out.println("Sueldo: " + empleado.getSalarioBase());

           if (txtSueldo != null) {
            txtSueldo.setText(String.valueOf(empleado.getSalarioBase()));
        }
    }
    }
    
    @FXML
    private void pagar(){
    if (seleccionado == null) {
        sceneManager.showAlertInfo(AlertType.WARNING, "Advertencia", "Por favor, selecciona un empleado de la tabla para realizar el pago.");
        return;
    }
    try {
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        int periodoMes = fechaActual.getMonthValue(); 
        int periodoAnio = fechaActual.getYear(); 

        System.out.println("Procesando pago para el período: " + periodoMes + "/" + periodoAnio);

       nominasService.editarNomina(seleccionado.getIdEmpleado(),periodoMes, periodoAnio, "pagada");

        sceneManager.showAlertInfo(AlertType.INFORMATION, "Éxito", "Nómina procesada correctamente para el período " + periodoMes + "-" + periodoAnio);
        
    } catch (Exception e) {
        sceneManager.showAlertInfo(AlertType.ERROR, "Error", "No se pudo procesar el pago: " + e.getMessage());
    }
}    
    
    @FXML
    public void regresar() throws Exception{
        sceneManager.showDashboardView();
    }
    
    }
    

