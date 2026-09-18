package main.java.com.pbcorporations.gestion.recursos.humanos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Usuario;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.UserSession;

public class DashboardController implements Initializable {

    private final SceneManager manager;

    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblRol;
    
    @FXML
    private Button btnDepartamentos;
    @FXML
    private Button btnAsistencia;
    @FXML
    private Button btnEmpleados;
    
    @FXML
    private Button btnSalarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserSession();
    }

    public DashboardController(SceneManager manager) {
        this.manager = manager;
    }

    public void setUserSession() {
        UserSession sesion = UserSession.getInstance();
        Usuario usuario = sesion.getUsuarioActual();

        if (usuario != null) {
            lblBienvenida.setText("¡Bienvenido: ");
            lblUsuario.setText(usuario.getUsername() + "!");
            lblRol.setText(sesion.getNombreRol());
            
            boolean esAdmn = sesion.isAdmin();
            
            configVisibility(btnDepartamentos, esAdmn);
            configVisibility(btnEmpleados, esAdmn);
            configVisibility(btnSalarios, esAdmn);
            
            configVisibility(btnAsistencia, true);
        }
    }
    
    private void configVisibility(Button btn, boolean vsbl) {
        if (btn != null) {
            btn.setVisible(vsbl);
            btn.setManaged(vsbl);
        }
    }
    
    @FXML
    public void handleLogout() throws Exception {
        UserSession.getInstance().logout();
        manager.showLoginView();
    }
    
    @FXML
    public void handleShowDepartamentos() {
        // acá ingresen sus vistas de lo que hayan trabajado, siempre creen las vistas con SCENE MANAGER para no meter como 1000 lineas por boton XD
    }
            
    @FXML
    public void handleShowEmpleados() {
        // acá ingresen sus vistas de lo que hayan trabajado, siempre creen las vistas con SCENE MANAGER para no meter como 1000 lineas por boton XD
    }
    
    @FXML
    public void handleShowSalarios() {
        // acá ingresen sus vistas de lo que hayan trabajado, siempre creen las vistas con SCENE MANAGER para no meter como 1000 lineas por boton XD
    }
            
    @FXML
    public void handleShowAsistencia() {
        // acá ingresen sus vistas de lo que hayan trabajado, siempre creen las vistas con SCENE MANAGER para no meter como 1000 lineas por boton XD
    }
}
