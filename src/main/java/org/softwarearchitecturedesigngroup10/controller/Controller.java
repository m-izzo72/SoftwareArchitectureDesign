package org.softwarearchitecturedesigngroup10.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
    private Label title;
    @FXML
    private Pane rootPane;
    @FXML
    private VBox toolbar;
    @FXML
    private HBox canvasToolbar;
    @FXML
    private Canvas canvas;
    @FXML
    private VBox backgroundPane;
    @FXML
    private VBox navigationPanel;
    @FXML
    private HBox bottomPane;
    @FXML
    private Button openFileButton;
    @FXML
    private Button clipboardNavigation;
    @FXML
    private Button saveFileButton;
    @FXML
    private Button shapeNavigation;
    @FXML
    private Button fileNavigation;
    @FXML
    private Button newCanvasButton;
    @FXML
    private Button ellipseButton;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox shapeToolbar;
    @FXML
    private VBox fileToolbar;
    @FXML
    private Button lineButton;
    @FXML
    private VBox clipboardToolbar;
    @FXML
    private Button rectangleButton;

    @FXML
    protected void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    public void initialize() {
        HBox.setHgrow(titleBar, Priority.ALWAYS);
        titleBar.setMaxWidth(Double.MAX_VALUE);

        shapeToolbar.setVisible(false);
        clipboardToolbar.setVisible(false);

        if (rootPane != null && titleBar != null)

        {

            backgroundPane.prefWidthProperty().bind(rootPane.widthProperty());
            backgroundPane.prefHeightProperty().bind(rootPane.heightProperty());
        }
        title.setMaxWidth(Double.MAX_VALUE);
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


    @FXML
    public void onSaveFileButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onNewCanvasButtonClick(ActionEvent actionEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    public void onOpenFileButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onShapeNavigationButtonClick(ActionEvent actionEvent) {
        fileToolbar.setVisible(false);

        clipboardToolbar.setVisible(false);

        shapeToolbar.setVisible(true);
    }

    @FXML
    public void onFileNavigationButtonClick(ActionEvent actionEvent) {
        fileToolbar.setVisible(true);
        clipboardToolbar.setVisible(false);

        shapeToolbar.setVisible(false);

    }

    @FXML
    public void onClipboardNavigationButtonClick(ActionEvent actionEvent) {
        fileToolbar.setVisible(false);

        clipboardToolbar.setVisible(true);
        shapeToolbar.setVisible(false);

    }
}