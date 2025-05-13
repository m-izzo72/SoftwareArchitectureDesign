package org.softwarearchitecturedesigngroup10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("src/main/java/org/softwarearchitecturedesigngroup10/view/view.fxml").toUri().toURL());
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Software Architecture Design ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}