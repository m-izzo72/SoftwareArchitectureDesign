package org.softwarearchitecturedesigngroup10.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
    private Label title;
    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private Label canvasInfoLabel;

    @FXML
    protected void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {

        canvasInfoLabel.textProperty().bind(Bindings.size(canvas.getChildren()).asString().concat(" shapes on the canvas."));

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
                    rootPane.setStyle("-fx-background-color: #525355;");
                    titleBar.getStyleClass().add("watercolorTitleBar");
                    titleBar.getStyleClass().remove("unfocused");
                    minimizeButton.getGraphic().setStyle("-fx-fill: #fffffe");
                    closeButton.getGraphic().setStyle("-fx-fill: #fffffe");
                    title.setTextFill(Color.valueOf("#fffffe"));
                } else { // La finestra HA PERSO il focus
                    rootPane.setStyle("-fx-background-color: #5a5b5e;");
                    titleBar.getStyleClass().remove("watercolorTitleBar");
                    titleBar.getStyleClass().add("unfocused");
                    minimizeButton.getGraphic().setStyle("-fx-fill: #797979");
                    closeButton.getGraphic().setStyle("-fx-fill: #797979");
                    title.setTextFill(Color.valueOf("#797979"));
                }
            }
        });
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

    @FXML
    public void handleMouseClick(MouseEvent event) {
        if(shapesTab.isSelected() && lineButton.isSelected()){
            Line line = new Line(event.getX(),event.getY(),100,100);
            line.setFill(fillColorPicker.getValue());
            line.setStroke(fillColorPicker.getValue());
            canvas.getChildren().add(line);
        } else if(shapesTab.isSelected() && rectangleButton.isSelected()){
            Rectangle rect = new Rectangle(event.getX(),event.getY(),100,100);
            rect.setFill(fillColorPicker.getValue());
            rect.setStroke(strokeColorPicker.getValue());
            canvas.getChildren().add(rect);
        } else if(shapesTab.isSelected() && ellipseButton.isSelected()){
            Ellipse ellipse = new Ellipse(event.getX() + 50,event.getY() + 50,50,50);
            ellipse.setFill(fillColorPicker.getValue());
            ellipse.setStroke(strokeColorPicker.getValue());
            canvas.getChildren().add(ellipse);
        };
    }

}