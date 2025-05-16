package org.softwarearchitecturedesigngroup10.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.softwarearchitecturedesigngroup10.model.factories.EllipseFactory;
import org.softwarearchitecturedesigngroup10.model.factories.LineFactory;
import org.softwarearchitecturedesigngroup10.model.factories.RectangleFactory;
import org.softwarearchitecturedesigngroup10.model.shapes.Line;
import org.softwarearchitecturedesigngroup10.model.shapes.Shape;

import java.util.ArrayList;

public class Controller {

    private Stage stage;
    @FXML
    private Pane rootPane;
    @FXML
    private ToggleButton ellipseButton;
    @FXML
    private ToggleButton lineButton;
    @FXML
    private ToggleButton rectangleButton;
    @FXML
    private Button saveFileButton;
    @FXML
    private Button newCanvasButton;
    @FXML
    private Button openFileButton;
    @FXML
    private Pane canvas;
    @FXML
    private Pane bottomPane;
    @FXML
    private Pane quickToolbar;
    @FXML
    private HBox mainArea;
    @FXML
    private Tab fileTab;
    @FXML
    private Pane titleBar;
    @FXML
    private TabPane tab;
    @FXML
    private VBox operatingArea;
    @FXML
    private Tab shapesTab;
    @FXML
    private Tab clipboardTab;
    @FXML
    private Button minimizeButton;
    @FXML
    private AnchorPane clipboardTabContainer;
    @FXML
    private AnchorPane fileTabContainer;
    @FXML
    private AnchorPane shapesTabContainer;
    @FXML
    private Button closeButton;

    @FXML
    protected void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {



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

        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rootPane.setStyle("-fx-background-color: #464646;");
                } else { // La finestra HA PERSO il focus
                    rootPane.setStyle("-fx-background-color: #5e5c5c;");
                }
            }
        });
    }

    @Deprecated
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

    }

    @FXML
    public void onOpenFileButtonClick(ActionEvent actionEvent) {
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

    @Deprecated
    public void onOpenFileClickButton(ActionEvent actionEvent) {
    }

    /*
    @Deprecated
    public void handleMouseClick(MouseEvent event) {

        ShapeFactory factory;
            if(lineButton.isSelected()) {
                factory =
                Line line = new LineFactory().createShape(x, y);
                line.setX();
                LineFactory lineFactory = new LineFactory();
                Shape line = lineFactory.createShape();
                line.setShapePosition(event.getX(), event.getY());
                line.draw(gc);

            } else if(ellipseButton.isSelected()) {
                EllipseFactory ellipseFactory = new EllipseFactory();
                Shape ellipse = ellipseFactory.createShape();
                ellipse.setShapePosition(event.getX(), event.getY());
                ellipse.draw(gc);
            } else if(rectangleButton.isSelected()) {
                RectangleFactory rectangleFactory = new RectangleFactory();
                Shape rectangle = rectangleFactory.createShape();
                rectangle.setShapePosition(event.getX(), event.getY());
                rectangle.draw(gc);
            }


        }*/

}