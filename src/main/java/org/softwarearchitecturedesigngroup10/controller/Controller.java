package org.softwarearchitecturedesigngroup10.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.factories.EllipseFactory;
import org.softwarearchitecturedesigngroup10.model.factories.LineFactory;
import org.softwarearchitecturedesigngroup10.model.factories.RectangleFactory;
import org.softwarearchitecturedesigngroup10.model.factories.ShapeFactory;
import org.softwarearchitecturedesigngroup10.model.helper.Highlighter;

import java.io.File;
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
    private Label title;
    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private Label canvasInfoLabel;
    @FXML
    private Button deleteShapeButton;
    @FXML
    private ToggleButton selectToolButton;

    @FXML
    protected void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    private CanvasModel canvasModel;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        canvasModel = new CanvasModel(canvas);

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

    public void addFocusListener() {
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
                } else {
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

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    @FXML
    public void onCloseButtonClick(ActionEvent actionEvent) {
        stage.close();
    }


    @FXML
    public void onSaveFileButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        fileChooser.setInitialFileName("canvas.pr");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("File P.Ritrovato", "*.pr")
        );

        File fileToSave = fileChooser.showSaveDialog(stage);

        canvasModel.save(fileToSave);
    }

    @FXML
    public void onNewCanvasButtonClick(ActionEvent actionEvent) {
        canvas.getChildren().clear();
    }

    @FXML
    public void onOpenFileButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("File P.Ritrovato", "*.pr")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        canvasModel.load(selectedFile);
    }


    @FXML
    public void onEllipseButtonClick(ActionEvent actionEvent) {
        rectangleButton.setSelected(false);
        lineButton.setSelected(false);

        selectToolButton.setSelected(false);
    }

    @FXML
    public void onRectangleButtonClick(ActionEvent actionEvent) {
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);

        selectToolButton.setSelected(false);
    }

    @FXML
    public void onLineButtonClick(ActionEvent actionEvent) {
        rectangleButton.setSelected(false);
        ellipseButton.setSelected(false);

        selectToolButton.setSelected(false);
    }

    @Deprecated
    public void onOpenFileClickButton(ActionEvent actionEvent) {
    }

    double startX, startY, endX, endY;
    ArrayList<Shape> selectedShapes = new ArrayList<>();

    @FXML
    public void setOnMousePressed(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();

        if(selectToolButton.isSelected()) {
            Object target = event.getTarget();
            Shape toSelectShape;

            // Checks if target is a Shape and if it is painted on the canvas
            if(target instanceof Node && canvas.getChildren().contains(target)) {
                toSelectShape = (Shape) target;
                if(selectedShapes.contains(toSelectShape)) {
                    selectedShapes.remove(toSelectShape);
                    Highlighter.unHighlightShape(toSelectShape);
                    System.out.println("Unselected shape " + toSelectShape);
                } else {
                    Highlighter.highlightShape(toSelectShape);
                    selectedShapes.add(toSelectShape);


                    System.out.println("Selected shapes " + selectedShapes);
                }

            } else if(target == canvas) {
                System.out.println("Canvas clicked");
                Highlighter.unHighlightShapes(selectedShapes);
                //Highlighter.unHighlightShape(toSelectShape);
                selectedShapes.clear();

            }
        }

        event.consume();
        System.out.println("Mouse clicked at: " + startX + ", " + startY);
    }

    @FXML
    public void setOnMouseReleased(MouseEvent event) {
        if (!shapesTab.isSelected()) {
            return;
        }

        ShapeFactory factory;

        // Seleziona la factory appropriata
        if (lineButton.isSelected()) {
            factory = new LineFactory();
        } else if (rectangleButton.isSelected()) {
            factory = new RectangleFactory();
        } else if (ellipseButton.isSelected()) {
            factory = new EllipseFactory();
        } else {
            return;
        }

        double thickness = 3;
        // Crea e configura la forma utilizzando la factory con tutti i parametri necessari
        Shape shape = factory.createShape(
                startX, startY, event.getX(), event.getY(),
                fillColorPicker.getValue(), strokeColorPicker.getValue(), thickness
        );

        // Aggiungi la forma al modello usando il pattern Command
        canvas.getChildren().add(shape);
        canvasModel.paint(shape);
    }


    @FXML
    public void onSelectToolButtonClick(ActionEvent actionEvent) {
        rectangleButton.setSelected(false);
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
    }

    @FXML
    public void onDeleteShapeButtonClick(ActionEvent actionEvent) {
        canvasModel.deleteShapes(selectedShapes);
    }
}