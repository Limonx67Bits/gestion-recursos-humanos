package main.java.com.pbcorporations.gestion.recursos.humanos;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.SceneManager;

public class MainApp extends Application {

    private Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        SceneManager manager = new SceneManager(stage);
        manager.showLoginView();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}