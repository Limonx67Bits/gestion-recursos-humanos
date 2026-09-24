package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Rol;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.AuthService;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class RegisterController implements Initializable {
    
    private final AuthService service;
    private final SceneManager manager;
    
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ComboBox<Rol> cmbxRoles;
    @FXML
    private ComboBox<EmpleadoModel> cmbxEmpleados;
    
    public void initialize(URL url, ResourceBundle rb) {
        chargeComboBox();
    }
    
    public RegisterController(AuthService service, SceneManager manager) {
        this.service = service;
        this.manager = manager;
    }
    
    public void chargeComboBox() {
        cmbxRoles.setItems(service.getRoles());
        cmbxEmpleados.setItems(service.getEmpleadosSinUsuario());
    }
    
    public void handleRegister(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        Rol rol = cmbxRoles.getValue();
        EmpleadoModel empleado = cmbxEmpleados.getValue();
        
        boolean registrado = service.registerUser(username, password, rol, empleado);
        
        if (registrado) {
            manager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "El usuario se ha registrado con éxito");
            cleanForm();
            chargeComboBox();
        } else {
            manager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudo registrar su usuario. Revise los campos.");
        }
    }
    
    @FXML
    public void handleReturn() throws Exception {
        manager.showLoginView();
    }
    
    public void cleanForm() {
        txtUsername.clear();
        txtPassword.clear();
        cmbxRoles.getSelectionModel().clearSelection();
        cmbxEmpleados.getSelectionModel().clearSelection();
    }
}
