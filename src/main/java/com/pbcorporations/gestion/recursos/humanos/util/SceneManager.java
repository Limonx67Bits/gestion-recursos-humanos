package main.java.com.pbcorporations.gestion.recursos.humanos.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import main.java.com.pbcorporations.gestion.recursos.humanos.controller.DashboardController;
import main.java.com.pbcorporations.gestion.recursos.humanos.controller.LoginController;
import main.java.com.pbcorporations.gestion.recursos.humanos.controller.RegisterController;
import main.java.com.pbcorporations.gestion.recursos.humanos.controller.SalarioController;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.EmpleadoRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.NominasRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.RolRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.UsuarioRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.AuthService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.pbcorporations.gestion.recursos.humanos.config.Credentials;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.EmpleadoService;
import main.java.com.pbcorporations.gestion.recursos.humanos.service.NominasService;

public class SceneManager {

    private final Stage stage;
    private final String PATH_FXML = "/main/resources/view/";

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH_FXML + "login-view.fxml"));

        loader.setControllerFactory(
                clazz -> {
                    if (clazz == LoginController.class) {
                        UsuarioRepository uRepository = new UsuarioRepository();
                        RolRepository rRepository = new RolRepository();
                        EmpleadoRepository eRepository = new EmpleadoRepository();
                        AuthService service = new AuthService(uRepository, rRepository, eRepository);
                        return new LoginController(service, this);
                    }

                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor... " + e.getMessage());
                    }
                }
        );

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        stage.setMinWidth(550);
        stage.setMinHeight(450);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void showDashboardView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH_FXML + "dashboard-view.fxml"));

        loader.setControllerFactory(
                clazz -> {
                    if (clazz == DashboardController.class) {
                        return new DashboardController(this);
                    }

                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor... " + e.getMessage());
                    }
                }
        );

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        stage.setMinWidth(550);
        stage.setMinHeight(450);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    
    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH_FXML + "register-view.fxml"));
        
        loader.setControllerFactory(
                clazz -> {
                    if (clazz == RegisterController.class) {
                        UsuarioRepository uRepository = new UsuarioRepository();
                        RolRepository rRepository = new RolRepository();
                        EmpleadoRepository eRepository = new EmpleadoRepository();
                        AuthService service = new AuthService(uRepository, rRepository, eRepository);
                        return new RegisterController(service, this);
                    }
                    
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor... " + e.getMessage());
                    }
                }
        );
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        stage.setMinWidth(550);
        stage.setMinHeight(450);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    
     public void showSalarioView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH_FXML + "salario-view.fxml"));

        loader.setControllerFactory(
                clazz -> {
                    if (clazz == SalarioController.class) {
                        Connection connection;
                        try {
                        connection = DriverManager.getConnection(Credentials.URL, Credentials.USER, Credentials.PASSWORD);
                        NominasRepository nominasRepository = new NominasRepository(connection);
                        EmpleadoRepository empleado = new EmpleadoRepository();
                        NominasService nominaService = new NominasService(nominasRepository, empleado);
                        return new SalarioController(nominaService, this );
                        } catch (SQLException ex) {
                            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                    }

                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor... " + e.getMessage());
                    }
                }
        );

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        stage.setMinWidth(550);
        stage.setMinHeight(450);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void showAlertInfo(Alert.AlertType tipo, String title, String content) {
        Alert alert = new Alert(tipo);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        DialogPane dialog = alert.getDialogPane();

        dialog.getStylesheets().add(
                SceneManager.class.getResource("/main/resources/css/alerts.css").toExternalForm()
        );

        alert.showAndWait();

    }
}
