package org.softwarearchitecturedesigngroup10.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import org.softwarearchitecturedesigngroup10.controller.states.*;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.*;
import org.softwarearchitecturedesigngroup10.model.commands.clipboard.CopyShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.clipboard.CutShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.clipboard.DeleteShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.clipboard.PasteShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.shapeediting.*;
import org.softwarearchitecturedesigngroup10.model.factories.EllipseDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.LineDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.RectangleDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.ShapeDataFactory;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;
import org.softwarearchitecturedesigngroup10.model.observers.observed.SelectionPropertyObserver;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData; // Assicurati che sia importato
import org.softwarearchitecturedesigngroup10.view.CanvasView;
import org.softwarearchitecturedesigngroup10.view.CircularSlider;
import org.softwarearchitecturedesigngroup10.view.helper.Highlighter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map; // Aggiunto Map
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Double.parseDouble;

public class Controller implements ModelObserver {

    /****************** FXML COMPONENTS ***********/

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


    private CanvasModel canvasModel;

    private CanvasView canvasView;

    private CommandManager commandManager;

    private ShapeConverter shapeConverter;

    private ShapeDataFactory factory;

    private double xOffset = 0, yOffset = 0;
    private double startX, startY;
    private double defaultCanvasWidth, defaultCanvasHeight;

    private SelectionPropertyObserver selectionPropertyObserver;
    @FXML
    private Slider zoomSlider;

    private double zoomFactor;
    @FXML
    private Slider editStrokeWidthSlider;
    @FXML
    private Pane canvasQuickToolbar;
    @FXML
    private Group canvasGroup;
    @FXML
    private ToggleButton textButton;
    @FXML
    private ToggleButton polygonButton;
    @FXML
    private Canvas grid;
    @FXML
    private Button flipXButton;
    @FXML
    private CircularSlider rotationSlider;
    @FXML
    private Button flipYButton;
    @FXML
    private ToggleButton toggleGridButton;
    @FXML
    private Group editStrokeColourIcon;
    @FXML
    private Group editFillColourIcon;
    @FXML
    private Group editStrokeWidthIcon;
    @FXML
    private ColorPicker editStrokeColorPicker;
    @FXML
    private ColorPicker editFillColorPicker;

    /* CLOSE AND MINIMIZE WINDOW */

    @FXML
    public void onMinimizeButtonAction(ActionEvent actionEvent) {
        stage.setIconified(true);
    }

    @FXML
    public void onCloseButtonAction(ActionEvent actionEvent) {
        stage.close();
    }

    /* UPDATE METHOD CALLED BY OBSERVER */

    @Override
    public void update() {
        AtomicReference<String> selectedShapeLabelText = new AtomicReference<>("");
        LinkedHashMap<String, Shape> viewShapes = new LinkedHashMap<>();
        LinkedHashMap<String, ShapeData> modelShapes = canvasModel.getShapes();
        LinkedHashMap<String, ShapeData> selectedModelShapes = canvasModel.getSelectedShapes();

        modelShapes.forEach((key, value) -> {
            Shape fxShape = shapeConverter.convert(value);
            fxShape.setId(key);
            viewShapes.put(key, fxShape);
        });

        canvasView.paintAllFromScratch(viewShapes);

        boolean anySelected = !selectedModelShapes.isEmpty();
        viewShapes.forEach((key, fxShape) -> {
            boolean isSelected = selectedModelShapes.containsKey(key);

            if (anySelected) {
                if (isSelected) {
                    canvasView.setUnselectedState(fxShape);
                    Highlighter.highlightShape(fxShape);
                } else {
                    canvasView.setSelectedEffect(fxShape);
                    Highlighter.unhighlightShape(fxShape);
                }
            } else {
                canvasView.setUnselectedState(fxShape);
                Highlighter.unhighlightShape(fxShape);
            }
        });

        if (selectedModelShapes.size() == 1) {
            Map.Entry<String, ShapeData> entry = selectedModelShapes.entrySet().iterator().next();
            String selectedId = entry.getKey();
            ShapeData selectedData = entry.getValue();

            if (!(selectedData instanceof LineData) && selectedData.getWidth() > 5 && selectedData.getHeight() > 5) {
                canvasView.updateResizeHandle(viewShapes.get(selectedId));
            } else {
                canvasView.updateResizeHandle(null);
            }
        } else {
            canvasView.updateResizeHandle(null);
        }

        if (anySelected) {
            selectedShapeLabelText.set(" > " + selectedModelShapes.size() + " selected shape(s)");
            tab.getSelectionModel().select(2);
        } else {
            selectedShapeLabelText.set("");
        }
        canvasInfoLabel.setText(modelShapes.size() + " shape(s) on the canvas" + selectedShapeLabelText);
        newCanvasButton.setDisable(modelShapes.isEmpty());
        undoButton.setDisable(commandManager.isUndoStackEmpty());
    }


    /* STATES */

    private State currentState;
    private final State idleState = new IdleState();
    private final State selectionState = new SelectionState();
    private final State drawingState = new PaintingState();
    private final State movingState = new MovingState();
    private final State resizingState = new ResizingState();



    @FXML
    public void initialize() {
        canvasModel = new CanvasModel();
        canvasView = new CanvasView(canvas);
        commandManager = new CommandManager();
        shapeConverter = new ShapeConverter();

        defaultCanvasHeight = canvas.getPrefHeight();
        defaultCanvasWidth = canvas.getPrefWidth();
        zoomFactor = 1.0;

        tab.getSelectionModel().select(1);

        // Initial state
        setCurrentState(idleState);

        canvasModel.addObserver(this);
        ArrayList<Node> nodesToBind = new ArrayList<>();
        Collections.addAll(nodesToBind,
                editFillColourIcon,
                editFillColorPicker,
                editStrokeColourIcon,
                editStrokeColorPicker,
                copyShapeButton,
                eraseShapeButton,
                cutShapeButton,
                sendToBackButton,
                bringToFrontButton,
                editStrokeWidthIcon,
                editStrokeWidthSlider,
                flipXButton,
                flipYButton,
                rotationSlider
        );

        selectionPropertyObserver = new SelectionPropertyObserver(canvasModel, nodesToBind);

        editStrokeWidthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            final double roundedValue = Math.round(newValue.doubleValue());
            editStrokeWidthSlider.valueProperty().set(roundedValue);
        });

        editStrokeWidthSlider.setOnMouseReleased((MouseEvent event) -> {
            commandManager.executeCommand(new EditShapeStrokeWidthCommand(canvasModel, editStrokeWidthSlider.getValue()));
        });

        zoomSlider.valueProperty().addListener(this::zoomListener);

        canvasModel.addObserver(selectionPropertyObserver);

        canvasInfoLabel.setText("No shapes on the canvas");

        titleBar.setOnMousePressed(this::setOnTitleBarPressed);
        titleBar.setOnMouseDragged(this::setOnTitleBarDragged);

        setCanvasScrollableAndResizable();

        rotationSlider.setOnThumbMouseReleased(this::setOnRotationSliderMouseReleased);
        rotationSlider.setOnThumbMouseDragged(this::setOnRotationSliderMouseDragged);


    }

    /* ANGLES AND ROTATION */

    public void setOnRotationSliderMouseDragged(MouseEvent event) {
        canvasView.rotatePreview(rotationSlider.getAngle());
    }

    public void setOnRotationSliderMouseReleased(MouseEvent event) {
        commandManager.executeCommand(new RotateShapeCommand(canvasModel, rotationSlider.getAngle()));
    }

    /* UNDO */

    @FXML
    public void onUndoButtonAction(ActionEvent actionEvent) {
        commandManager.undo();
    }

    /* GRID */

    @FXML
    public void onToggleGridButtonAction(ActionEvent actionEvent) {
        if(toggleGridButton.isSelected()) {
            drawGrid(grid.getGraphicsContext2D());
        } else {
            clearGrid(grid.getGraphicsContext2D());
        }
    }

    public void clearGrid(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawGrid(GraphicsContext gc) {
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();
        gc.getCanvas().setWidth(canvas.getWidth());

        // CONTROLLO CRUCIALE:
        if (width <= 0 || height <= 0) {
            System.err.println("Skipping grid draw: Invalid dimensions - W: " + width + ", H: " + height);
            return; // Non fare nulla se le dimensioni non sono valide
        }

        gc.clearRect(0, 0, width, height); // Pulisce il canvas

        gc.setStroke(Color.LIGHTGRAY); // Colore della griglia
        gc.setLineWidth(0.5); // Spessore delle linee

        // Linee verticali
        for (double x = 0; x <= width; x += 20) {
            // Aggiungere 0.5 per linee più nitide quando lo spessore è dispari o per allineamento pixel
            gc.strokeLine(x + 0.5, 0, x + 0.5, height);
        }

        // Linee orizzontali
        for (double y = 0; y <= height; y += 20) {
            gc.strokeLine(0, y + 0.5, width, y + 0.5);
        }
    }

    /* ZOOM LOGIC */

    public void zoomListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        zoomFactor = ((double) 1 / 3) * newValue.doubleValue();
        canvasGroup.setScaleX(zoomFactor); canvasGroup.setScaleY(zoomFactor);
        //grid.setHeight(canvas.getHeight()); grid.setWidth(canvas.getWidth());

    }

    /* SCROLLABLE CANVAS AND CLIPPING CANVAS LOGIC */

    private void setCanvasScrollableAndResizable() {
        setCanvasClippable();
        bindTextFieldToCanvasSize();
    }

    private void setCanvasClippable() {
        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(canvas.widthProperty());
        clipRect.heightProperty().bind(canvas.heightProperty());
        canvas.setClip(clipRect);
    }

    private void bindTextFieldToCanvasSize() {
        DoubleProperty customWidthProperty = new SimpleDoubleProperty(canvas.getPrefWidth());
        DoubleProperty customHeightProperty = new SimpleDoubleProperty(canvas.getPrefHeight());
        canvas.prefWidthProperty().bind(customWidthProperty);
        canvas.prefHeightProperty().bind(customHeightProperty);



        canvas.prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            canvasGroup.prefHeight(newValue.doubleValue());

            //canvasGroup.setTranslateX(- scrollableCanvasContainer.getPrefWidth() / 2);
        });
        canvas.prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            canvasGroup.prefWidth(newValue.doubleValue());

            System.out.println("New grid size: " + grid.getWidth() + ", " + grid.getHeight());
            //canvasGroup.setTranslateY(- scrollableCanvasContainer.getPrefHeight() / 2);
        });
        Bindings.bindBidirectional(canvasWidthInput.textProperty(), customWidthProperty, doubleConverter);
        Bindings.bindBidirectional(canvasHeightInput.textProperty(), customHeightProperty, doubleConverter);

        canvasWidthInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                canvasWidthInput.setText(newValue.replaceAll("\\D", ""));
            }
            if (Double.parseDouble(newValue) > 10000) canvasWidthInput.setText("9999");
        });
        canvasHeightInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                canvasHeightInput.setText(newValue.replaceAll("\\D", ""));
            }
            if (Double.parseDouble(newValue) > 10000) canvasHeightInput.setText("9999");
        });


    }

    StringConverter<Number> doubleConverter = new StringConverter<>() {
        @Override
        public String toString(Number object) {
            return String.format("%.0f", object.doubleValue());
        }

        @Override
        public Number fromString(String string) {
            try {
                return parseDouble(string);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

    };

    /* DRAGGABLE WINDOW */

    private void setOnTitleBarPressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void setOnTitleBarDragged(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void addFocusListener() {
        stage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                rootPane.setStyle("-fx-background-color: #525355;");
                titleBar.getStyleClass().add("watercolorTitleBar");
                titleBar.getStyleClass().remove("unfocused");
                minimizeButton.getGraphic().setStyle("-fx-fill: #fffffe");
                closeButton.getGraphic().setStyle("-fx-fill: #fffffe");
                undoButton.getGraphic().setStyle("-fx-fill: #fffffe");
                title.setTextFill(Color.valueOf("#fffffe"));
            } else {
                rootPane.setStyle("-fx-background-color: #5a5b5e;");
                titleBar.getStyleClass().remove("watercolorTitleBar");
                titleBar.getStyleClass().add("unfocused");
                minimizeButton.getGraphic().setStyle("-fx-fill: #797979");
                closeButton.getGraphic().setStyle("-fx-fill: #797979");
                undoButton.getGraphic().setStyle("-fx-fill: #797979");
                title.setTextFill(Color.valueOf("#797979"));
            }
        });

    }

    /* FILES TAB METHODS */

    @FXML
    public void onNewCanvasButtonAction(ActionEvent actionEvent) {
        commandManager.clear();
        canvasModel.clear();

    }

    @FXML
    public void onSaveFileButtonAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        fileChooser.setInitialFileName("newproject.canvas");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("File Canvas", "*.canvas")
        );

        File fileToSave = fileChooser.showSaveDialog(stage);

        if (fileToSave != null) {
            canvasModel.save(fileToSave);
        }
    }

    @FXML
    public void onOpenFileButtonAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("File Canvas", "*.canvas")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            commandManager.clear();
            canvasModel.load(selectedFile);
            title.setText(selectedFile.getName());

        }
    }

    /* SHAPES TAB METHODS */

    @FXML
    public void onEllipseButtonSelected(ActionEvent actionEvent) {
        if(ellipseButton.isSelected()) {
            rectangleButton.setSelected(false);
            lineButton.setSelected(false);
            selectToolButton.setSelected(false);
            setFactory(new EllipseDataFactory());
            setCurrentState(drawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onRectangleButtonSelected(ActionEvent actionEvent) {
        if (rectangleButton.isSelected()) {
            ellipseButton.setSelected(false);
            lineButton.setSelected(false);
            selectToolButton.setSelected(false);
            setFactory(new RectangleDataFactory());
            setCurrentState(drawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onLineButtonSelected(ActionEvent actionEvent) {
        if(lineButton.isSelected()) {
            rectangleButton.setSelected(false);
            ellipseButton.setSelected(false);
            selectToolButton.setSelected(false);
            setFactory(new LineDataFactory());
            setCurrentState(drawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onSelectToolButtonSelected(ActionEvent actionEvent) {
        if(selectToolButton.isSelected()) {
            rectangleButton.setSelected(false);
            ellipseButton.setSelected(false);
            lineButton.setSelected(false);
            setCurrentState(selectionState);
        } else {
            setCurrentState(idleState);
        }
    }

    /* CANVAS HANDLER METHODS */

    @FXML
    public void setOnMousePressed(MouseEvent event) {
        currentState.handleMousePressed(event, this);
    }

    @FXML
    public void setOnMouseDragged(MouseEvent event) {
        currentState.handleMouseDragged(event, this);
    }

    @FXML
    public void setOnMouseReleased(MouseEvent event) {
        currentState.handleMouseReleased(event, this);
    }

    /* CLIPBOARD TAB METHODS */

    @FXML
    public void onEraseShapeButtonAction(ActionEvent actionEvent) {
        DeleteShapeCommand command = new DeleteShapeCommand(canvasModel);
        commandManager.executeCommand(command);
    }

    @FXML
    public void onCopyShapeButtonAction(ActionEvent actionEvent) {
        CopyShapeCommand command = new CopyShapeCommand(canvasModel);
        commandManager.executeCommand(command);
    }

    @FXML
    public void onPasteButtonAction(ActionEvent actionEvent) {
        PasteShapeCommand command = new PasteShapeCommand(canvasModel);
        commandManager.executeCommand(command);
    }

    @FXML
    public void onCutShapeButtonAction(ActionEvent actionEvent) {
        CutShapeCommand command = new CutShapeCommand(canvasModel);
        commandManager.executeCommand(command);
    }

    @FXML
    public void onFillColorToChangePickerAction(ActionEvent actionEvent) {
        EditShapesFillColourCommand command = new EditShapesFillColourCommand(
                canvasModel, editFillColorPicker.getValue().toString()
        );
        commandManager.executeCommand(command);
    }

    @FXML
    public void onStrokeColorToChangePickerAction(ActionEvent actionEvent) {
        EditShapesStrokeColourCommand command = new EditShapesStrokeColourCommand(
                canvasModel, editStrokeColorPicker.getValue().toString()
        );
        commandManager.executeCommand(command);
    }

    @FXML
    public void onBringToFrontAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new BringToFrontCommand(canvasModel));
    }

    @FXML
    public void onSendToBackAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new SendToBackCommand(canvasModel));
    }

    /************** GETTERS *****************/

    public Pane getCanvas() {
        return canvas;
    }

    public ColorPicker getStrokeColorPicker() {
        return strokeColorPicker;
    }

    public ColorPicker getFillColorPicker() {
        return fillColorPicker;
    }

    public Slider getStrokeSlider() {
        return strokeSlider;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public CanvasModel getCanvasModel() {
        return canvasModel;
    }

    public CanvasView getCanvasView() {
        return canvasView;
    }

    public ShapeDataFactory getFactory() {
        return factory;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public ToggleButton getSelectToolButton() {
        return selectToolButton;
    }

    public ToggleButton getLineButton() {
        return lineButton;
    }

    public ToggleButton getEllipseButton() {
        return ellipseButton;
    }

    public ToggleButton getRectangleButton() {
        return rectangleButton;
    }

    public State getSelectionState() {
        return selectionState;
    }

    public State getMovingState() {
        return movingState;
    }

    public State getResizingState() {
        return resizingState;
    }

    /********************* SETTERS ******************/

    public void setCurrentState(State newState) {
        if (currentState != null) {
            currentState.exitState(this);
        }
        currentState = newState;
        currentState.enterState(this);
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public void setFactory(ShapeDataFactory factory) {
        this.factory = factory;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

}