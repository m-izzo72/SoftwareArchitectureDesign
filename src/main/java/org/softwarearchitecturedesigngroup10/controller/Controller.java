package org.softwarearchitecturedesigngroup10.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.softwarearchitecturedesigngroup10.controller.adapters.ShapeConverter;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.*;
import org.softwarearchitecturedesigngroup10.model.factories.EllipseDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.LineDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.RectangleDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.ShapeDataFactory;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;
import org.softwarearchitecturedesigngroup10.view.CanvasView;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Controller implements ModelObserver {

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
    private Button eraseShapeButton;
    @FXML
    private ToggleButton selectToolButton;
    @FXML
    private Button bringToFrontButton;
    @FXML
    private TextField canvasWidthInput;
    @FXML
    private Button copyShapeButton;
    @FXML
    private Button pasteShapeButton;
    @FXML
    private ColorPicker fillColorToChangePicker;
    @FXML
    private ColorPicker strokeColorToChangePicker;
    @FXML
    private Slider strokeSlider;
    @FXML
    private Button cutShapeButton;
    @FXML
    private Button undoButton;
    @FXML
    private TextField canvasHeightInput;
    @FXML
    private Button sendToBackButton;
    @FXML
    private ScrollPane scrollableCanvasContainer;
    @FXML
    private Pane bottomLeftCorner;


    private CanvasModel canvasModel;

    private CanvasView canvasView;

    private CommandManager commandManager;

    private ShapeConverter shapeConverter;

    private ShapeDataFactory factory;


    private double xOffset = 0, yOffset = 0;
    private double startX, startY;
    @FXML
    private SVGPath fillColorToChangeIcon;
    @FXML
    private SVGPath strokeColorToChangeIcon;

    @FXML
    private void onMinimizeButtonClick() {
        stage.setIconified(true);
    }

    //private Shape previewShape = null;


    @Override
    public void update() {
        LinkedHashMap<String, Shape> viewShapes = new LinkedHashMap<>();
        canvasModel.getShapes()
                .forEach((key, value) -> {
                    viewShapes.put(key, shapeConverter.convert(value));
                    if(value.isSelected()) canvasView.highlight(viewShapes.get(key));
                });

        canvasView.paintAllFromScratch(viewShapes);

//        LinkedHashMap<String, ShapeData> modelShapes = canvasModel.getShapes();
//
//        LinkedHashMap<String, Shape> viewShapes = new LinkedHashMap<>();
//        for (Map.Entry<String, ShapeData> entry : modelShapes.entrySet()) {
//            Shape fxShape = toFXShape(entry.getValue());
//            viewShapes.put(entry.getKey(), fxShape);
//        }
//
//        canvasView.paintAllFromScratch(viewShapes);
    }

    /*private Shape toFXShape(ShapeData data) {
        Shape fxShape = null;
        try {
            Color fillColor = data.getFillColor() != null ? Color.valueOf(data.getFillColor()) : null;
            Color strokeColor = data.getStrokeColor() != null ? Color.valueOf(data.getStrokeColor()) : Color.BLACK;
            if (data instanceof RectangleData rd) {
                Rectangle rect = new Rectangle(rd.getX(), rd.getY(), rd.getWidth(), rd.getHeight());
                rect.setFill(fillColor);
                rect.setStroke(strokeColor);
                rect.setStrokeWidth(rd.getStrokeWidth());
                fxShape = rect;
            } else if (data instanceof EllipseData ed) {
                Ellipse ellipse = new Ellipse(ed.getCenterX(), ed.getCenterY(), ed.getRadiusX(), ed.getRadiusY());
                ellipse.setFill(fillColor);
                ellipse.setStroke(strokeColor);
                ellipse.setStrokeWidth(ed.getStrokeWidth());
                fxShape = ellipse;
            } else if (data instanceof LineData ld) {
                Line line = new Line(ld.getX(), ld.getY(), ld.getEndX(), ld.getEndY());
                line.setStroke(strokeColor);
                line.setStrokeWidth(ld.getStrokeWidth());
                fxShape = line;
            }

            if(data.isSelected()) canvasView.highlight(fxShape);

        } catch (IllegalArgumentException e) {
            System.err.println();
        }

        return fxShape;
    }*/

    @FXML
    public void initialize() {
        canvasModel = new CanvasModel();
        canvasView = new CanvasView(canvas);
        commandManager = new CommandManager();
        shapeConverter = new ShapeConverter();

        this.canvasModel.addObserver(this);

        canvasInfoLabel.textProperty().bind(Bindings.size(canvas.getChildren()).asString().concat(" shapes on the canvas."));

        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });


        StringConverter<Number> doubleConverter = new StringConverter<>() {
            @Override
            public String toString(Number object) {
                return String.format("%.0f", object.doubleValue());
            }

            @Override
            public Number fromString(String string) {
                try {
                    return Double.parseDouble(string);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        };

        //fillColorToChangePicker.disableProperty().bind(Bindings.isEmpty(canvasModel.getSelectedShapes());

        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(canvas.widthProperty());
        clipRect.heightProperty().bind(canvas.heightProperty());
        canvas.setClip(clipRect);

        DoubleProperty customWidthProperty = new SimpleDoubleProperty(canvas.getPrefWidth());
        DoubleProperty customHeightProperty = new SimpleDoubleProperty(canvas.getPrefHeight());
        canvas.prefWidthProperty().bind(customWidthProperty);
        canvas.prefHeightProperty().bind(customHeightProperty);
        Bindings.bindBidirectional(canvasWidthInput.textProperty(), customWidthProperty, doubleConverter);
        Bindings.bindBidirectional(canvasHeightInput.textProperty(), customHeightProperty, doubleConverter);

       // System.out.println(canvas.widthProperty().toString());
    }

    public void addFocusListener() {
        stage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
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
        });

    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    @FXML
    public void onCloseButtonAction(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    public void onSaveFileButtonAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        fileChooser.setInitialFileName("canvas.pr");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("File Canvas", "*.pr")
        );

        File fileToSave = fileChooser.showSaveDialog(stage);

        canvasModel.save(fileToSave);
    }

    @FXML
    public void onNewCanvasButtonAction(ActionEvent actionEvent) {
        canvasModel.clear();
    }

    @FXML
    public void onOpenFileButtonAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("File Canvas", "*.pr")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        canvasModel.load(selectedFile);
        title.setText(selectedFile.getName());
    }

    @FXML
    public void onEllipseButtonSelected(ActionEvent actionEvent) {
        if(ellipseButton.isSelected()) {
            rectangleButton.setSelected(false);
            lineButton.setSelected(false);
            selectToolButton.setSelected(false);
            factory = new EllipseDataFactory();
        } else {
            factory = null;
        }
    }

    @FXML
    public void onRectangleButtonSelected(ActionEvent actionEvent) {
        if (rectangleButton.isSelected()) {
            ellipseButton.setSelected(false);
            lineButton.setSelected(false);
            selectToolButton.setSelected(false);
            factory = new RectangleDataFactory();
        } else {
            factory = null;
        }
    }

    @FXML
    public void onLineButtonSelected(ActionEvent actionEvent) {
        if(lineButton.isSelected()) {
            rectangleButton.setSelected(false);
            ellipseButton.setSelected(false);
            selectToolButton.setSelected(false);
            factory = new LineDataFactory();
        } else {
            factory = null;
        }
    }

    @FXML
    public void onSelectToolButtonSelected(ActionEvent actionEvent) {
        rectangleButton.setSelected(false);
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
        factory = null;
    }

    @FXML
    public void setOnMousePressed(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();

        if (selectToolButton.isSelected()) {
            Object target = event.getTarget();

            if (target instanceof Node && canvas.getChildren().contains(target)) {
                if (target instanceof Shape) {
                    SelectShapeCommand command = new SelectShapeCommand(this.canvasModel, ((Shape) target).getId());
                    command.execute();
                    canvasView.highlight((Shape) target);

                    // Checks if color pickers are disabled and then enables/disables them if a shape is selected
                    fillColorToChangePicker.setDisable(canvasModel.getSelectedShapes().isEmpty());
                    fillColorToChangeIcon.setDisable(canvasModel.getSelectedShapes().isEmpty());
                    strokeColorToChangePicker.setDisable(canvasModel.getSelectedShapes().isEmpty());
                    strokeColorToChangeIcon.setDisable(canvasModel.getSelectedShapes().isEmpty());

                    /*boolean isDisabled = fillColorToChangePicker.isDisabled();
                    fillColorToChangePicker.setDisable(!isDisabled);
                    fillColorToChangeIcon.setDisable(!isDisabled);
                    strokeColorToChangePicker.setDisable(!isDisabled);
                    strokeColorToChangeIcon.setDisable(!isDisabled);*/

                    // Changes color pickers displayed colors
                    fillColorToChangePicker.setValue(Color.valueOf( ((Shape) target).getFill().toString() ));
                    strokeColorToChangePicker.setValue(Color.valueOf(((Shape) target).getStroke().toString()));
                }
            } else if (target == canvas) {
                DeselectAllShapeCommand command = new DeselectAllShapeCommand(this.canvasModel);
                command.execute();
                canvasView.unHighlightAll();

                // Disable shape fill and stroke color pickers
                fillColorToChangePicker.setDisable(true);
                fillColorToChangeIcon.setDisable(true);
                strokeColorToChangePicker.setDisable(true);
                strokeColorToChangeIcon.setDisable(true);
            }
            event.consume();
        }

        /*boolean isDrawingToolActive = (lineButton.isSelected() || ellipseButton.isSelected() || rectangleButton.isSelected()) && shapesTab.isSelected();

        if (isDrawingToolActive) {
            startX = event.getX();
            startY = event.getY();

            if (previewShape != null) {
                canvasView.erase(previewShape);
                previewShape = null;
            }

            if (lineButton.isSelected()) {
                previewShape = new Line(startX, startY, startX, startY);
            } else if (ellipseButton.isSelected()) {
                previewShape = new Ellipse(startX, startY, 0, 0);
            } else if (rectangleButton.isSelected()) {
                previewShape = new Rectangle(startX, startY, 0, 0);
            }

            if (previewShape != null) {
                canvasView.stylePreviewShape(previewShape);
                canvasView.paint(previewShape);
            }
            event.consume();
        }*/

    }

    @FXML
    public void setOnMouseReleased(MouseEvent event) {
        if(factory != null && shapesTab.isSelected()) {
            commandManager.executeCommand(
                    new AddShapeCommand(
                            canvasModel,
                            factory.createShapeData(startX, startY, event.getX(), event.getY(), fillColorPicker.getValue().toString(), strokeColorPicker.getValue().toString(), strokeSlider.getValue(), 0)
                    )
            );
        }

//        ShapeDataFactory factory;
//        if (lineButton.isSelected() && shapesTab.isSelected()) {
//            factory = new LineDataFactory();
//            AddShapeCommand command = new AddShapeCommand(canvasModel, factory.createShapeData(startX, startY, event.getX(), event.getY(), fillColorPicker.getValue().toString(), strokeColorPicker.getValue().toString(), strokeSlider.getValue(), 0));
//            commandManager.executeCommand(command);
//        } else if (ellipseButton.isSelected() && shapesTab.isSelected()) {
//            factory = new EllipseDataFactory();
//            AddShapeCommand command = new AddShapeCommand(canvasModel, factory.createShapeData(startX, startY, event.getX(), event.getY(), fillColorPicker.getValue().toString(), strokeColorPicker.getValue().toString(), strokeSlider.getValue(), 0));
//            commandManager.executeCommand(command);
//        } else if (rectangleButton.isSelected() && shapesTab.isSelected()) {
//            factory = new RectangleDataFactory();
//            AddShapeCommand command = new AddShapeCommand(canvasModel, factory.createShapeData(startX, startY, event.getX(), event.getY(), fillColorPicker.getValue().toString(), strokeColorPicker.getValue().toString(), strokeSlider.getValue(), 0));
//            commandManager.executeCommand(command);
//        }
    }



    @FXML
    public void onEraseShapeButtonAction(ActionEvent actionEvent) {
        DeleteShapeCommand command = new DeleteShapeCommand(canvasModel);
        command.execute();
    }

    @FXML
    public void setOnMouseDragged(MouseEvent event) {
        /*boolean isDrawingToolActive = (lineButton.isSelected() || ellipseButton.isSelected() || rectangleButton.isSelected()) && shapesTab.isSelected();

        if (previewShape != null && isDrawingToolActive) {
            canvasView.updatePreviewShapeGeometry(previewShape, event.getX(), event.getY(), startX, startY);
            event.consume();
        }*/
        // Might be used later for moving a shape
//        else if (selectToolButton.isSelected() && canvas.getChildren().contains(previewShape)) {
//        }
    }

    @FXML
    public void onMinimizeButtonAction(ActionEvent actionEvent) {
        stage.setIconified(true);
    }

    @FXML
    public void onFillColorToChangePickerAction(ActionEvent actionEvent) {
        ChangeShapeFillColorCommand command = new ChangeShapeFillColorCommand(
                canvasModel, fillColorToChangePicker.getValue().toString(), strokeColorToChangePicker.getValue().toString()
        );
        commandManager.executeCommand(command);
    }

    @FXML
    public void onStrokeColorToChangePickerAction(ActionEvent actionEvent) {
        ChangeShapeFillColorCommand command = new ChangeShapeFillColorCommand(
                canvasModel, fillColorToChangePicker.getValue().toString(), strokeColorToChangePicker.getValue().toString()
        );
        commandManager.executeCommand(command);
    }
}