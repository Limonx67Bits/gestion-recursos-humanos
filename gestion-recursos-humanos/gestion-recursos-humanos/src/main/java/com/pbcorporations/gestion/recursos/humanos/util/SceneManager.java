package main.java.com.pbcorporations.gestion.recursos.humanos.util;

import javafx.scene.control.Alert;

public class SceneManager {

    public void showAlertInfo(String titulo, String header, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
