    package main.java.com.pbcorporations.gestion.recursos.humanos;

    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.stage.Stage;

    public class MainApp extends Application {

        @Override
        public void start(Stage primaryStage) {
            try {
                // La ruta inicia desde la raíz del classpath (omitir "main/resources")
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/EmpleadoView.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                primaryStage.setTitle("Gestión de Recursos Humanos - Empleados");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false); // Opcional: fija el tamaño de la ventana
                primaryStage.centerOnScreen();
                primaryStage.show();

            } catch (Exception e) {
                System.err.println("Error al cargar la interfaz de usuario: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }