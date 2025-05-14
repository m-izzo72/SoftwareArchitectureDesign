package org.softwarearchitecturedesigngroup10.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
    private Button newCanvasButton;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox shapeToolbar;
    @FXML
    private VBox fileToolbar;
    @FXML
    private VBox clipboardToolbar;
    @FXML
    private Button pasteButton;
    @FXML
    private Button copyButton;
    @FXML
    private VBox canvasContainer;
    @FXML
    private HBox operatingArea;
    @FXML
    private Button fileNavigation;
    @FXML
    private ToggleButton ellipseButton;
    @FXML
    private ToggleButton lineButton;
    @FXML
    private ToggleButton rectangleButton;

    @FXML
    protected void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void initStyle() {
        ArrayList<SVGPath> iconList = new ArrayList<>();
        ArrayList<SVGPath> otherList = new ArrayList<>();

        otherList.add(new SVGPath());
        otherList.add(new SVGPath());
        otherList.add(new SVGPath());

        otherList.get(0).setContent("M200-440v-80h560v80H200Z");
        otherList.get(1).setContent("M200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h560q33 0 56.5 23.5T840-760v560q0 33-23.5 56.5T760-120H200Zm0-80h560v-560H200v560Z");
        otherList.get(2).setContent("M480-80q-83 0-156-31.5T197-197q-54-54-85.5-127T80-480q0-83 31.5-156T197-763q54-54 127-85.5T480-880q83 0 156 31.5T763-763q54 54 85.5 127T880-480q0 83-31.5 156T763-197q-54 54-127 85.5T480-80Zm0-80q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z");

        iconList.add(new SVGPath());
        iconList.add(new SVGPath());
        iconList.add(new SVGPath());

        iconList.get(0).setContent("M320-240h320v-80H320v80Zm0-160h320v-80H320v80ZM240-80q-33 0-56.5-23.5T160-160v-640q0-33 23.5-56.5T240-880h320l240 240v480q0 33-23.5 56.5T720-80H240Zm280-520v-200H240v640h480v-440H520ZM240-800v200-200 640-640Z");
        iconList.get(1).setContent("m260-520 220-360 220 360H260ZM700-80q-75 0-127.5-52.5T520-260q0-75 52.5-127.5T700-440q75 0 127.5 52.5T880-260q0 75-52.5 127.5T700-80Zm-580-20v-320h320v320H120Zm580-60q42 0 71-29t29-71q0-42-29-71t-71-29q-42 0-71 29t-29 71q0 42 29 71t71 29Zm-500-20h160v-160H200v160Zm202-420h156l-78-126-78 126Zm78 0ZM360-340Zm340 80Z");
        iconList.get(2).setContent("M620-163 450-333l56-56 114 114 226-226 56 56-282 282Zm220-397h-80v-200h-80v120H280v-120h-80v560h240v80H200q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h167q11-35 43-57.5t70-22.5q40 0 71.5 22.5T594-840h166q33 0 56.5 23.5T840-760v200ZM480-760q17 0 28.5-11.5T520-800q0-17-11.5-28.5T480-840q-17 0-28.5 11.5T440-800q0 17 11.5 28.5T480-760Z");

        for(SVGPath icon : iconList) {

            icon.setScaleX(icon.getScaleX() * 0.025);
            icon.setScaleY(icon.getScaleY() * 0.025);
            icon.setFill(Color.valueOf("#797b86"));
            icon.setStrokeWidth(0.5);
        }

        for(SVGPath icon : otherList) {

            icon.setScaleX(icon.getScaleX() * 0.05);
            icon.setScaleY(icon.getScaleY() * 0.05);
            icon.setFill(Color.valueOf("#797b86"));
            icon.setStrokeWidth(0.5);
        }

        fileNavigation.setGraphic(iconList.get(0));
        shapeNavigation.setGraphic(iconList.get(1));
        clipboardNavigation.setGraphic(iconList.get(2));

        lineButton.setGraphic(otherList.get(0));
        rectangleButton.setGraphic(otherList.get(1));
        ellipseButton.setGraphic(otherList.get(2));

    }

    public void initToolBar() {
        fileNavigation.setStyle("-fx-background-color: #5874e6");
        fileNavigation.getGraphic().setStyle("-fx-fill: #ffffff");

        shapeToolbar.setVisible(false);
        clipboardToolbar.setVisible(false);
    }

    @FXML
    public void initialize() {

        initStyle();
        initToolBar();

        HBox.setHgrow(titleBar, Priority.ALWAYS);
        titleBar.setMaxWidth(Double.MAX_VALUE);



        if (rootPane != null && titleBar != null)

        {

            backgroundPane.prefWidthProperty().bind(rootPane.widthProperty());
            backgroundPane.prefHeightProperty().bind(rootPane.heightProperty());

        }


        canvasContainer.prefWidthProperty().bind(operatingArea.widthProperty());
        canvasContainer.prefHeightProperty().bind(rootPane.heightProperty().subtract(55));
        navigationPanel.prefHeightProperty().bind(rootPane.heightProperty().subtract(55));
        toolbar.prefHeightProperty().bind(rootPane.heightProperty().subtract(55));
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

    public void selectButtonStyle(Button button) {
        button.setStyle("-fx-background-color: #5874e6");
        button.getGraphic().setStyle("-fx-fill: #ffffff");
    }

    public void deselectButtonStyle(Button button) {
        button.setStyle("-fx-background-color: transparent");
        button.getGraphic().setStyle("-fx-fill: #797b86");
    }

    @FXML
    public void onShapeNavigationButtonClick(ActionEvent actionEvent) {
        fileToolbar.setVisible(false);
        deselectButtonStyle(fileNavigation);
        clipboardToolbar.setVisible(false);
        deselectButtonStyle(clipboardNavigation);
        shapeToolbar.setVisible(true);
        selectButtonStyle(shapeNavigation);

    }

    @FXML
    public void onFileNavigationButtonClick(ActionEvent actionEvent) {
        fileToolbar.setVisible(true);
        selectButtonStyle(fileNavigation);
        clipboardToolbar.setVisible(false);
        deselectButtonStyle(clipboardNavigation);
        shapeToolbar.setVisible(false);
        deselectButtonStyle(shapeNavigation);

    }

    @FXML
    public void onClipboardNavigationButtonClick(ActionEvent actionEvent) {
        fileToolbar.setVisible(false);
        deselectButtonStyle(fileNavigation);
        clipboardToolbar.setVisible(true);
        selectButtonStyle(clipboardNavigation);
        shapeToolbar.setVisible(false);
        deselectButtonStyle(shapeNavigation);

    }

    @FXML
    public void onEllipseButtonClick(ActionEvent actionEvent) {
        rectangleButton.setSelected(false);
        lineButton.setSelected(false);
    }

    @FXML
    public void onRectangleButtonClick(ActionEvent actionEvent) {
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
    }

    @FXML
    public void onLineButtonClick(ActionEvent actionEvent) {
        rectangleButton.setSelected(false);
        ellipseButton.setSelected(false);
    }
}