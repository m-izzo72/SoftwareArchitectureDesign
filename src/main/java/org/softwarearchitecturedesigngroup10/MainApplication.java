package org.softwarearchitecturedesigngroup10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.softwarearchitecturedesigngroup10.controller.Controller;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        System.setProperty("prism.lcdtext", "false"); // Fixes font rendering
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));

        //fxmlLoader.setLocation(Paths.get("src/main/java/org/softwarearchitecturedesigngroup10/view/view.fxml").toUri().toURL());
        Parent parent = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.addFocusListener();
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Software Architecture Design Group 10");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
    }

    public static void main(String[] args) {
        launch();
    }
}