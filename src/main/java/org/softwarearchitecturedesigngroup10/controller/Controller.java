package org.softwarearchitecturedesigngroup10.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Controller {

    private Stage stage;
    @FXML
    private Button minimizeButton;
    @FXML
    private HBox titleBar;
    @FXML
    private Button closeButton;
    @FXML
    private Button maximizeButton;

    @FXML
    protected void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        // Configura il trascinamento della finestra
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onMaximizeButtonClick(ActionEvent actionEvent) {
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    public void onCloseButtonClick(ActionEvent actionEvent) {
        stage.close();
    }


}