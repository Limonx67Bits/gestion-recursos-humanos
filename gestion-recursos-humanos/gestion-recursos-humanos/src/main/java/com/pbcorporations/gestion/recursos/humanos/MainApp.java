package main.java.com.pbcorporations.gestion.recursos.humanos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       
       Parent root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/dashboard.fxml"));
        Scene scene = new Scene(root, 1100, 650);

        stage.setTitle("Gestión de Recursos Humanos - Kinal");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
