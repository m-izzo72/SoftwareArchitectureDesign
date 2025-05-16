package org.softwarearchitecturedesigngroup10.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
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
    private Button clipboardNavigation;
    @FXML
    private Button shapeNavigation;
    @FXML
    private VBox shapeToolbar;
    @FXML
    private VBox fileToolbar;
    @FXML
    private VBox clipboardToolbar;
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
    private Button deleteButton;
    @FXML
    private Button saveFileButton;
    @FXML
    private Button newCanvasButton;
    @FXML
    private Button pasteButton;
    @FXML
    private Button openFileButton;
    @FXML
    private Button copyButton;
    @FXML
    private ColorPicker borderColorPicker;
    @FXML
    private ColorPicker shapeColorPicker;
    @FXML
    private StackPane canvasListener;

    @FXML
    protected void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void initStyle() {
        ArrayList<SVGPath> iconList = new ArrayList<>();
        ArrayList<SVGPath> otherList = new ArrayList<>();
        ArrayList<SVGPath> titleList = new ArrayList<>();

        for(int i = 0; i < 9; i++) {
            otherList.add(new SVGPath());
        }

        for(int i = 0; i < 3; i++) {
            titleList.add(new SVGPath());
        }

        titleList.get(0).setContent("M260-160v-40h440v40H260Z");
        titleList.get(1).setContent("M160-160v-176.92h40V-200h136.92v40H160Zm463.85 0v-40h136.92v-136.92h40V-160H623.85ZM160-623.08V-800h176.92v40H200v136.92h-40Zm600.77 0V-760H623.85v-40h176.92v176.92h-40Z");
        titleList.get(2).setContent("M256-227.69 227.69-256l224-224-224-224L256-732.31l224 224 224-224L732.31-704l-224 224 224 224L704-227.69l-224-224-224 224Z");

        otherList.get(0).setContent("M200-440v-80h560v80H200Z");
        otherList.get(1).setContent("M200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h560q33 0 56.5 23.5T840-760v560q0 33-23.5 56.5T760-120H200Zm0-80h560v-560H200v560Z");
        otherList.get(2).setContent("M480-80q-83 0-156-31.5T197-197q-54-54-85.5-127T80-480q0-83 31.5-156T197-763q54-54 127-85.5T480-880q83 0 156 31.5T763-763q54 54 85.5 127T880-480q0 83-31.5 156T763-197q-54 54-127 85.5T480-80Zm0-80q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z");

        otherList.get(3).setContent("M450-250h60v-120h120v-60H510v-120h-60v120H330v60h120v120ZM252.31-100Q222-100 201-121q-21-21-21-51.31v-615.38Q180-818 201-839q21-21 51.31-21H570l210 210v477.69Q780-142 759-121q-21 21-51.31 21H252.31ZM540-620v-180H252.31q-4.62 0-8.46 3.85-3.85 3.84-3.85 8.46v615.38q0 4.62 3.85 8.46 3.84 3.85 8.46 3.85h455.38q4.62 0 8.46-3.85 3.85-3.84 3.85-8.46V-620H540ZM240-800v180-180V-160v-640Z");
        otherList.get(4).setContent("M252.31-100Q222-100 201-121q-21-21-21-51.31v-615.38Q180-818 201-839q21-21 51.31-21H570l210 210v260h-60v-230H540v-180H252.31q-4.62 0-8.46 3.85-3.85 3.84-3.85 8.46v615.38q0 4.62 3.85 8.46 3.84 3.85 8.46 3.85H610v60H252.31ZM878-79.23l-128-128v103.61h-60V-310h206.38v60H791.77l128 128L878-79.23ZM240-160v-640 640Z");
        otherList.get(5).setContent("M720-134.23 865.77-280 824-321.77l-74 74V-429h-60v181.23l-74-74L574.23-280 720-134.23ZM570-10v-60h300v60H570ZM242.31-170q-29.54 0-50.92-21.39Q170-212.77 170-242.31v-555.38q0-29.54 21.39-50.92Q212.77-870 242.31-870H520l230 230v123.31h-60V-610H490v-200H242.31q-4.62 0-8.46 3.85-3.85 3.84-3.85 8.46v555.38q0 4.62 3.85 8.46 3.84 3.85 8.46 3.85h240v60h-240ZM230-230v-580 580Z");

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

        for(SVGPath icon : titleList) {
            icon.setScaleX(icon.getScaleX() * 0.025);
            icon.setScaleY(icon.getScaleY() * 0.025);
            icon.setFill(Color.valueOf("#ffffff"));
            icon.setStrokeWidth(0.5);
        }

        fileNavigation.setGraphic(iconList.get(0));
        shapeNavigation.setGraphic(iconList.get(1));
        clipboardNavigation.setGraphic(iconList.get(2));

        lineButton.setGraphic(otherList.get(0));
        rectangleButton.setGraphic(otherList.get(1));
        ellipseButton.setGraphic(otherList.get(2));

        newCanvasButton.setGraphic(otherList.get(3));
        saveFileButton.setGraphic(otherList.get(4));
        openFileButton.setGraphic(otherList.get(5));

        minimizeButton.setGraphic(titleList.get(0));
        maximizeButton.setGraphic(titleList.get(1));
        closeButton.setGraphic(titleList.get(2));

    }

    public void initToolBar() {
        fileNavigation.setStyle("-fx-background-color: #5874e6");
        fileNavigation.getGraphic().setStyle("-fx-fill: #ffffff");

        shapeToolbar.setVisible(false);
        clipboardToolbar.setVisible(false);
    }

    GraphicsContext gc;

    @FXML
    public void initialize() {

        initStyle();
        initToolBar();

        canvas.widthProperty().bind(canvasListener.widthProperty());
        canvas.heightProperty().bind(canvasListener.heightProperty());



        gc = canvas.getGraphicsContext2D();

        HBox.setHgrow(titleBar, Priority.ALWAYS);
        titleBar.setMaxWidth(Double.MAX_VALUE);



        if (rootPane != null && titleBar != null)

        {

            backgroundPane.prefWidthProperty().bind(rootPane.widthProperty());
            backgroundPane.prefHeightProperty().bind(rootPane.heightProperty());

        }


        canvasContainer.prefWidthProperty().bind(operatingArea.widthProperty());
        canvasContainer.prefHeightProperty().bind(rootPane.heightProperty().subtract(60));
        navigationPanel.prefHeightProperty().bind(rootPane.heightProperty().subtract(60));
        toolbar.prefHeightProperty().bind(rootPane.heightProperty().subtract(60));
        canvasListener.prefHeightProperty().bind(rootPane.heightProperty().subtract(60));
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


    @FXML

        public void handleMouseClick(MouseEvent event) {

            if(lineButton.isSelected()) {

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
        }

}