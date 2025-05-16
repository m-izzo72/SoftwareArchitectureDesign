package org.softwarearchitecturedesigngroup10;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.softwarearchitecturedesigngroup10.controller.Controller;

import java.io.IOException;
import java.nio.file.Paths;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        System.setProperty("prism.lcdtext", "false"); // Fixes font rendering
        FXMLLoader fxmlLoader = new FXMLLoader();//new FXMLLoader(Paths.get("src/main/java/org/softwarearchitecturedesigngroup10/view/view.fxml").toUri().toURL());

        /*Controller controller = new Controller();
        fxmlLoader.setController(controller);*/
        fxmlLoader.setLocation(Paths.get("src/main/java/org/softwarearchitecturedesigngroup10/view/view.fxml").toUri().toURL());
        Parent parent = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Software Architecture Design Group 10");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}