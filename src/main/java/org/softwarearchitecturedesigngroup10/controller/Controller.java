package org.softwarearchitecturedesigngroup10.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import org.softwarearchitecturedesigngroup10.model.command.AddShapeCommand;
import org.softwarearchitecturedesigngroup10.model.command.CommandManager;
import org.softwarearchitecturedesigngroup10.model.factories.LineDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.ShapeDataFactory;
import org.softwarearchitecturedesigngroup10.model.helper.Highlighter;
import org.softwarearchitecturedesigngroup10.model.observer.ModelObserver;
import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.view.CanvasView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Controller implements ModelObserver{

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
    private CanvasView canvasView;
    private CommandManager commandManager;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void update() {
        System.out.println("Controller: Ricevuta notifica di update dal Model.");
        // 1. Ottieni lo stato aggiornato dal Model
        LinkedHashMap<String, ShapeData> modelShapes = canvasModel.getShapes();
        System.out.println("Controller: Numero di ShapeData dal model: " + modelShapes.size() + "\n" + modelShapes.toString());

        // 2. Converti ShapeData in oggetti JavaFX Shape (Logica di Mapping)
        LinkedHashMap<String, Shape> viewShapes = new LinkedHashMap<>();
        for (Map.Entry<String, ShapeData> entry : modelShapes.entrySet()) {
            Shape fxShape = convertShapeDataToFxShape(entry.getValue());
            System.out.println("Controller: FXShape" + fxShape.toString());
            viewShapes.put(entry.getKey(), fxShape);
            //System.out.println(viewShapes.put(entry.getKey(), null));
        }
        System.out.println("Controller: Numero di JavaFX Shapes convertite: " + viewShapes.size());
        System.out.println("Shapes in viewShapes" + viewShapes.toString());

        // 3. Istruisci la View ad aggiornarsi
        canvasView.clear(); // Pulisce il canvas precedente
        canvasView.repaintAll(viewShapes); // Ridisegna tutte le forme
        System.out.println("Controller: CanvasView istruita a ridisegnare.");
    }

    private Shape convertShapeDataToFxShape(ShapeData data) {
        Shape fxShape = null;
        // Nota: i colori devono essere convertiti da String a javafx.scene.paint.Color
        // e le coordinate/dimensioni devono essere usate correttamente.
        try {
            Color fillColor = data.getFillColor() != null ? Color.valueOf(data.getFillColor()) : null;
            Color strokeColor = data.getStrokeColor() != null ? Color.valueOf(data.getStrokeColor()) : Color.BLACK; // Default a nero se null

            if (data instanceof RectangleData rd) {
                Rectangle rect = new Rectangle(rd.getX(), rd.getY(), rd.getWidth(), rd.getHeight());
                rect.setFill(fillColor);
                rect.setStroke(strokeColor);
                rect.setStrokeWidth(rd.getStrokeWidth());
                fxShape = rect;
            } else if (data instanceof EllipseData ed) {
                // Ellipse in JavaFX usa centerX, centerY, radiusX, radiusY
                // Assumendo che x,y in EllipseData siano centerX, centerY
                Ellipse ellipse = new Ellipse(ed.getX(), ed.getY(), ed.getRadiusX(), ed.getRadiusY());
                ellipse.setFill(fillColor);
                ellipse.setStroke(strokeColor);
                ellipse.setStrokeWidth(ed.getStrokeWidth());
                fxShape = ellipse;
            } else if (data instanceof LineData ld) {
                // Line in JavaFX usa startX, startY, endX, endY
                // Assumendo che x,y in LineData siano startX, startY
                Line line = new Line(ld.getX(), ld.getY(), ld.getEndX(), ld.getEndY());
                // Le linee di solito non hanno un fill, ma lo stroke è importante
                line.setStroke(strokeColor);
                line.setStrokeWidth(ld.getStrokeWidth());
                fxShape = line;
            }



        } catch (IllegalArgumentException e) {
            System.err.println("Errore nella conversione del colore per la forma: " + e.getMessage());
            // Gestisci l'errore, magari non aggiungendo la forma o usando colori di default
        }
        return fxShape;
    }


    @FXML
    public void initialize() {
        canvasModel = new CanvasModel();
        canvasView = new CanvasView(canvas);
        commandManager = new CommandManager();

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
//            if(target instanceof Node && canvas.getChildren().contains(target)) {
//                toSelectShape = (Shape) target;
//                if(selectedShapes.contains(toSelectShape)) {
//                    selectedShapes.remove(toSelectShape);
//                    Highlighter.unHighlightShape(toSelectShape);
//                    System.out.println("Unselected shape " + toSelectShape);
//                } else {
//                    Highlighter.highlightShape(toSelectShape);
//                    selectedShapes.add(toSelectShape);
//
//
//                    System.out.println("Selected shapes " + selectedShapes);
//                }
//
//            } else if(target == canvas) {
//                System.out.println("Canvas clicked");
//                Highlighter.unHighlightShapes(selectedShapes);
//                //Highlighter.unHighlightShape(toSelectShape);
//                selectedShapes.clear();
//
//            }
        }

        event.consume();
        System.out.println("Mouse clicked at: " + startX + ", " + startY);
    }

    @FXML
    public void setOnMouseReleased(MouseEvent event) {

        ShapeDataFactory factory;
        if(lineButton.isSelected()) {
            factory = new LineDataFactory();

            AddShapeCommand command = new AddShapeCommand(canvasModel, factory.createShapeData(startX, startY, event.getX(), event.getY(), fillColorPicker.getValue().toString(), strokeColorPicker.getValue().toString(), 3, 0));

            commandManager.executeCommand(command);
        }
//        if (!shapesTab.isSelected()) {
//            return;
//        }
//
//        ShapeFactory factory;
//
//        // Seleziona la factory appropriata
//        if (lineButton.isSelected()) {
//            factory = new LineFactory();
//        } else if (rectangleButton.isSelected()) {
//            factory = new RectangleFactory();
//        } else if (ellipseButton.isSelected()) {
//            factory = new EllipseFactory();
//        } else {
//            return;
//        }
//
//        double thickness = 3;
//        // Crea e configura la forma utilizzando la factory con tutti i parametri necessari
//        Shape shape = factory.createShape(
//                startX, startY, event.getX(), event.getY(),
//                fillColorPicker.getValue(), strokeColorPicker.getValue(), thickness
//        );
//
//        // Aggiungi la forma al modello usando il pattern Command
//        canvas.getChildren().add(shape);
//        canvasModel.paint(shape);
    }


    @FXML
    public void onSelectToolButtonClick(ActionEvent actionEvent) {
        rectangleButton.setSelected(false);
        ellipseButton.setSelected(false);
        lineButton.setSelected(false);
    }

    @FXML
    public void onDeleteShapeButtonClick(ActionEvent actionEvent) {
        //canvasModel.deleteShapes(selectedShapes);
    }
}