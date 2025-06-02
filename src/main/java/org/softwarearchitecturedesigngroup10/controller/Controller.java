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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
import org.softwarearchitecturedesigngroup10.model.commands.selection.groups.GroupShapesCommand;
import org.softwarearchitecturedesigngroup10.model.commands.selection.groups.UngroupShapesCommand;
import org.softwarearchitecturedesigngroup10.model.commands.shapeediting.*;
import org.softwarearchitecturedesigngroup10.model.factories.*;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;
import org.softwarearchitecturedesigngroup10.model.observers.observed.SelectionPropertyObserver;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;
import org.softwarearchitecturedesigngroup10.view.CanvasView;
import org.softwarearchitecturedesigngroup10.view.CircularSlider;
import org.softwarearchitecturedesigngroup10.view.helper.Highlighter;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Double.parseDouble;

public class Controller implements ModelObserver {

    /****************** FXML COMPONENTS ******************/
    @FXML private Pane rootPane;
    @FXML private ToggleButton ellipseButton;
    @FXML private ToggleButton lineButton;
    @FXML private ToggleButton rectangleButton;
    @FXML private Button saveFileButton;
    @FXML private Button newCanvasButton;
    @FXML private Button openFileButton;
    @FXML private Pane canvas;
    @FXML private Pane bottomPane;
    @FXML private HBox mainArea;
    @FXML private Tab fileTab;
    @FXML private Pane titleBar;
    @FXML private TabPane tab;
    @FXML private VBox operatingArea;
    @FXML private Tab shapesTab;
    @FXML private Tab clipboardTab;
    @FXML private Button minimizeButton;
    @FXML private AnchorPane clipboardTabContainer;
    @FXML private AnchorPane fileTabContainer;
    @FXML private AnchorPane shapesTabContainer;
    @FXML private Button closeButton;
    @FXML private Label title;
    @FXML private ColorPicker strokeColorPicker;
    @FXML private ColorPicker fillColorPicker;
    @FXML private Label canvasInfoLabel;
    @FXML private Button eraseShapeButton;
    @FXML private ToggleButton selectToolButton;
    @FXML private Button bringToFrontButton;
    @FXML private TextField canvasWidthInput;
    @FXML private Button copyShapeButton;
    @FXML private Button pasteShapeButton;
    @FXML private Slider strokeSlider;
    @FXML private Button cutShapeButton;
    @FXML private Button undoButton;
    @FXML private TextField canvasHeightInput;
    @FXML private Button sendToBackButton;
    @FXML private ScrollPane scrollableCanvasContainer;
    @FXML private Slider zoomSlider;
    @FXML private Slider editStrokeWidthSlider;
    @FXML private Pane canvasQuickToolbar;
    @FXML private Group canvasGroup;
    @FXML private ToggleButton textButton;
    @FXML private ToggleButton polygonButton;
    @FXML private Button flipXButton;
    @FXML private CircularSlider rotationSlider;
    @FXML private Button flipYButton;
    @FXML private ToggleButton toggleGridButton;
    @FXML private Group editStrokeColourIcon;
    @FXML private Group editFillColourIcon;
    @FXML private Group editStrokeWidthIcon;
    @FXML private ColorPicker editStrokeColorPicker;
    @FXML private ColorPicker editFillColorPicker;
    @FXML private Canvas grid;
    @FXML private Button selectToEditButton;
    @FXML private StackPane stackPane;
    @FXML private Slider gridSizeSlider;
    @FXML private AnchorPane helperStackPane;

    /****************** CONTROLLER STATE & SERVICES ******************/
    private Stage stage;
    private CanvasModel canvasModel;
    private CanvasView canvasView;
    private CommandManager commandManager;
    private ShapeConverter shapeConverter;
    private ShapeDataFactory factory;

    private double xOffset = 0, yOffset = 0; // For window dragging
    private double startX, startY; // For shape drawing/manipulation
    private double gridSize = 20;

    // State Pattern
    private State currentState;
    private final State idleState = new IdleState();
    private final State selectionState = new SelectionState();
    private final State regularDrawingState = new RegularDrawingState();
    private final State polygonDrawingState = new PolygonDrawingState();
    private final State movingState = new MovingState();
    private final State resizingState = new ResizingState();
    private final State textDrawingState = new TextDrawingState();

    // Color constants for focus listener
    private static final Color FOCUSED_BACKGROUND_COLOR = Color.valueOf("#525355");
    private static final Color UNFOCUSED_BACKGROUND_COLOR = Color.valueOf("#5a5b5e");
    private static final Color FOCUSED_ICON_COLOR = Color.valueOf("#fffffe");
    private static final Color UNFOCUSED_ICON_COLOR = Color.valueOf("#797979");
    @FXML
    private Button unGroupButton;
    @FXML
    private Button groupButton;

    /****************** INITIALIZATION ******************/
    @FXML
    public void initialize() {


        canvasModel = new CanvasModel();
        canvasView = new CanvasView(canvas);
        commandManager = new CommandManager();
        shapeConverter = new ShapeConverter();

//        defaultCanvasHeight = canvas.getPrefHeight();
//        defaultCanvasWidth = canvas.getPrefWidth();

        // Selects the "Shapes" tab by default
        tab.getSelectionModel().select(1);

        setCurrentState(idleState);

        canvasModel.addObserver(this);

        // Nodes to enable/disable based on selection
//        ArrayList<Node> nodesToBind = new ArrayList<>();
//        Collections.addAll(nodesToBind,
//                );
        SelectionPropertyObserver selectionPropertyObserver = new SelectionPropertyObserver(canvasModel,
                editFillColourIcon, editFillColorPicker,
                editStrokeColourIcon, editStrokeColorPicker,
                copyShapeButton, eraseShapeButton,
                cutShapeButton, pasteShapeButton,
                sendToBackButton, bringToFrontButton,
                editStrokeWidthIcon, editStrokeWidthSlider,
                flipXButton, flipYButton, rotationSlider);
                //nodesToBind);
        canvasModel.addObserver(selectionPropertyObserver);

        setupListeners();
        canvasInfoLabel.setText("No shapes on the canvas");
        setCanvasScrollableAndResizable();
    }

    private void setupListeners() {
        editStrokeWidthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            final double roundedValue = Math.round(newValue.doubleValue());
            editStrokeWidthSlider.setValue(roundedValue); // Use setValue to avoid re-triggering
        });
        editStrokeWidthSlider.setOnMouseReleased(event ->
                commandManager.executeCommand(new EditShapeStrokeWidthCommand(canvasModel, editStrokeWidthSlider.getValue()))
        );

        zoomSlider.valueProperty().addListener(this::zoomListener);

        titleBar.setOnMousePressed(this::onTitleBarPressed);
        titleBar.setOnMouseDragged(this::onTitleBarDragged);

        rotationSlider.setOnThumbMouseReleased(this::onRotationSliderMouseReleased);
        rotationSlider.setOnThumbMouseDragged(this::onRotationSliderMouseDragged);

        gridSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gridSize = newValue.doubleValue();
            if(toggleGridButton.isSelected()) drawGrid(grid.getGraphicsContext2D());
        });
    }

    /****************** WINDOW CONTROL ACTIONS ******************/
    @FXML
    public void onMinimizeButtonAction(ActionEvent actionEvent) {
        if (stage != null) stage.setIconified(true);
    }

    @FXML
    public void onCloseButtonAction(ActionEvent actionEvent) {
        if (stage != null) stage.close();
    }

    private void onTitleBarPressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void onTitleBarDragged(MouseEvent event) {
        if (stage != null) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    }

    public void addFocusListener() {
        if (stage == null) return;
        stage.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) { // Focused
                rootPane.setStyle("-fx-background-color: " + toWebColor(FOCUSED_BACKGROUND_COLOR) + ";");
                titleBar.getStyleClass().removeAll("unfocused");
                titleBar.getStyleClass().add("watercolorTitleBar");
                minimizeButton.getGraphic().setStyle("-fx-fill: " + toWebColor(FOCUSED_ICON_COLOR));
                closeButton.getGraphic().setStyle("-fx-fill: " + toWebColor(FOCUSED_ICON_COLOR));
                undoButton.getGraphic().setStyle("-fx-fill: " + toWebColor(FOCUSED_ICON_COLOR));
                title.setTextFill(FOCUSED_ICON_COLOR);
            } else { // Unfocused
                rootPane.setStyle("-fx-background-color: " + toWebColor(UNFOCUSED_BACKGROUND_COLOR) + ";");
                titleBar.getStyleClass().removeAll("watercolorTitleBar");
                titleBar.getStyleClass().add("unfocused");
                minimizeButton.getGraphic().setStyle("-fx-fill: " + toWebColor(UNFOCUSED_ICON_COLOR));
                closeButton.getGraphic().setStyle("-fx-fill: " + toWebColor(UNFOCUSED_ICON_COLOR));
                undoButton.getGraphic().setStyle("-fx-fill: " + toWebColor(UNFOCUSED_ICON_COLOR));
                title.setTextFill(UNFOCUSED_ICON_COLOR);
            }
        });
    }

    private String toWebColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }

    /****************** MODEL OBSERVER IMPLEMENTATION ******************/
    @Override
    public void update() {
        LinkedHashMap<String, Node> viewShapes = new LinkedHashMap<>();
        LinkedHashMap<String, ShapeData> modelShapes = canvasModel.getShapes();
        LinkedHashMap<String, ShapeData> selectedModelShapes = canvasModel.getSelectedShapes();

        modelShapes.forEach((id, data) -> {
            Node fxShape = shapeConverter.convert(data);
            fxShape.setId(id);
            viewShapes.put(id, fxShape);
        });

        canvasView.paintAllFromScratch(viewShapes);

        boolean anyShapeSelected = !selectedModelShapes.isEmpty();

        viewShapes.forEach((id, fxShape) -> {
            boolean isThisShapeSelected = selectedModelShapes.containsKey(id);
            canvasView.setUnselectedState(fxShape);
            Highlighter.unhighlightShape(fxShape);

            if (anyShapeSelected) {
                if (isThisShapeSelected) {
                    Highlighter.highlightShape(fxShape);
                } else {
                    canvasView.setSelectedEffect(fxShape);
                }
            }
        });

        // Update resize handles
        if (selectedModelShapes.size() == 1) {
            Map.Entry<String, ShapeData> entry = selectedModelShapes.entrySet().iterator().next();
            ShapeData selectedData = entry.getValue();
            if (!(selectedData instanceof LineData) && selectedData.getWidth() > 5 && selectedData.getHeight() > 5) {
                canvasView.updateResizeHandle(viewShapes.get(entry.getKey()));
            } else {
                canvasView.updateResizeHandle(null);
            }
        } else {
            canvasView.updateResizeHandle(null);
        }

        // Update canvas info label
        String selectedShapeLabelText = anyShapeSelected ? " > " + selectedModelShapes.size() + " selected shape(s)" : "";
        canvasInfoLabel.setText(modelShapes.size() + " shape(s) on the canvas" + selectedShapeLabelText);

        // Update button states
        newCanvasButton.setDisable(modelShapes.isEmpty());
        undoButton.setDisable(commandManager.isUndoStackEmpty());
    }

    /****************** CANVAS MANIPULATION & TOOLS ******************/

    // --- Rotation ---
    private void onRotationSliderMouseDragged(MouseEvent event) {
        canvasView.rotatePreview(rotationSlider.getAngle());
    }

    private void onRotationSliderMouseReleased(MouseEvent event) {
        commandManager.executeCommand(new RotateShapeCommand(canvasModel, rotationSlider.getAngle()));
    }

    // --- Undo ---
    @FXML
    public void onUndoButtonAction(ActionEvent actionEvent) {
        commandManager.undo();
    }

    // --- Grid ---
    @FXML
    public void onToggleGridButtonAction(ActionEvent actionEvent) {
        if (grid == null || grid.getGraphicsContext2D() == null) return;
        GraphicsContext gc = grid.getGraphicsContext2D();
        if (toggleGridButton.isSelected()) {
            drawGrid(gc);
        } else {
            clearGrid(gc);
        }
    }

    private void clearGrid(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    private void drawGrid(GraphicsContext gc) {
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();

        if (width <= 0 || height <= 0) {
            System.err.println("Skipping grid draw: Invalid dimensions - W: " + width + ", H: " + height);
            return;
        }

        clearGrid(gc); // Clear previous grid

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        // Vertical lines
        for (double x = 0; x <= width; x += gridSize) {
            gc.strokeLine(x + 0.5, 0, x + 0.5, height);
        }
        // Horizontal lines
        for (double y = 0; y <= height; y += gridSize) {
            gc.strokeLine(0, y + 0.5, width, y + 0.5);
        }
    }

    // --- Zoom ---
    private void zoomListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

        //private double defaultCanvasWidth, defaultCanvasHeight;
        double zoomFactor = newValue.doubleValue() / 3.0;
        if (zoomFactor <= 0) zoomFactor = 0.1;

        stackPane.setScaleX(zoomFactor);
        stackPane.setScaleY(zoomFactor);

        scrollableCanvasContainer.setHvalue(0.5);
        scrollableCanvasContainer.setVvalue(0.5);

    }

    // --- Canvas Resizing and Scrolling ---
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

    private final StringConverter<Number> doubleConverter = new StringConverter<>() {
        @Override
        public String toString(Number object) {
            return String.format("%.0f", object.doubleValue());
        }

        @Override
        public Number fromString(String string) {
            try {
                double val = parseDouble(string);
                return Math.max(1, Math.min(val, 4096));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    };

    private void bindTextFieldToCanvasSize() {
        DoubleProperty customWidthProperty = new SimpleDoubleProperty(canvas.getPrefWidth());
        DoubleProperty customHeightProperty = new SimpleDoubleProperty(canvas.getPrefHeight());

        canvas.prefWidthProperty().bind(customWidthProperty);
        canvas.prefHeightProperty().bind(customHeightProperty);

        ChangeListener<Number> canvasSizeListener = (observable, oldValue, newValue) -> {
            if (grid != null && grid.getGraphicsContext2D() != null) { // Ensure grid is ready
                grid.setWidth(canvas.getPrefWidth()); // Match grid canvas to drawing canvas
                grid.setHeight(canvas.getPrefHeight());

                if (toggleGridButton.isSelected()) {
                    drawGrid(grid.getGraphicsContext2D());
                } else {
                    clearGrid(grid.getGraphicsContext2D());
                }
            }

            javafx.application.Platform.runLater(() -> {
                 scrollableCanvasContainer.setHvalue(0.5);
                 scrollableCanvasContainer.setVvalue(0.5);
             });
        };

        customWidthProperty.addListener(canvasSizeListener);
        customHeightProperty.addListener(canvasSizeListener);


        Bindings.bindBidirectional(canvasWidthInput.textProperty(), customWidthProperty, doubleConverter);
        Bindings.bindBidirectional(canvasHeightInput.textProperty(), customHeightProperty, doubleConverter);

        canvasWidthInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                canvasWidthInput.setText(newValue.replaceAll("\\D", ""));
            }
            if (Double.parseDouble(newValue) > 4092) {
                canvasWidthInput.setText("4092");
            }
        });
        canvasHeightInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                canvasHeightInput.setText(newValue.replaceAll("\\D", ""));
            }
            if (Double.parseDouble(newValue) > 4092) {
                canvasHeightInput.setText("4092");
            }
        });


    }

    /****************** FILE ACTIONS (FILE TAB) ******************/
    @FXML
    public void onNewCanvasButtonAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Clear Canvas");
        alert.setHeaderText("Are you sure you want to clear the canvas?");
        alert.setContentText("All unsaved work will be lost.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            commandManager.clear();
            canvasModel.clear();

            title.setText("New Canvas");
        }
    }

    @FXML
    public void onSaveFileButtonAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Canvas File");
        fileChooser.setInitialFileName("untitled.canvas");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Canvas File", "*.canvas")
        );
        File fileToSave = fileChooser.showSaveDialog(stage);
        if (fileToSave != null) {
            try {
                canvasModel.save(fileToSave);
                title.setText(fileToSave.getName());
            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
                //e.printStackTrace();
            }
        }
    }

    @FXML
    public void onOpenFileButtonAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Canvas File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Canvas File", "*.canvas")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                commandManager.clear();
                canvasModel.load(selectedFile);
                title.setText(selectedFile.getName());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error opening file: " + e.getMessage());
                //e.printStackTrace();
            }
        }
    }

    /****************** SHAPE SELECTION ACTIONS (SHAPES TAB) ******************/
    @FXML
    public void onEllipseButtonSelected(ActionEvent actionEvent) {
        if (ellipseButton.isSelected()) {
            deselectOtherShapeTools(ellipseButton);
            setFactory(new EllipseDataFactory());
            setCurrentState(regularDrawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onRectangleButtonSelected(ActionEvent actionEvent) {
        if (rectangleButton.isSelected()) {
            deselectOtherShapeTools(rectangleButton);
            setFactory(new RectangleDataFactory());
            setCurrentState(regularDrawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onLineButtonSelected(ActionEvent actionEvent) {
        if (lineButton.isSelected()) {
            deselectOtherShapeTools(lineButton);
            setFactory(new LineDataFactory());
            setCurrentState(regularDrawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onPolygonButtonSelected(ActionEvent actionEvent) {
        if (polygonButton.isSelected()) {
            deselectOtherShapeTools(polygonButton);
            setFactory(new PolygonDataFactory());
            setCurrentState(polygonDrawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onWriteTextButtonAction(ActionEvent actionEvent) {
        if(textButton.isSelected()) {
            deselectOtherShapeTools(textButton);
            setFactory(new TextDataFactory());
            setCurrentState(textDrawingState);
        } else {
            setCurrentState(idleState);
        }
    }

    @FXML
    public void onSelectToolButtonSelected(ActionEvent actionEvent) {
        if (selectToolButton.isSelected()) {
            deselectOtherShapeTools(selectToolButton);
            setCurrentState(selectionState);
        } else {
            setCurrentState(idleState);
        }
    }

    private void deselectOtherShapeTools(ToggleButton selectedTool) {
        if (selectedTool != ellipseButton) ellipseButton.setSelected(false);
        if (selectedTool != rectangleButton) rectangleButton.setSelected(false);
        if (selectedTool != lineButton) lineButton.setSelected(false);
        if (selectedTool != selectToolButton) selectToolButton.setSelected(false);
        if (selectedTool != polygonButton) polygonButton.setSelected(false);
        if (selectedTool != textButton) textButton.setSelected(false);
    }

    @FXML
    public void onSelectToEditButtonSelected(ActionEvent actionEvent) {
        tab.getSelectionModel().select(clipboardTab); // Or index of clipboard tab
        selectToolButton.setSelected(true);
        onSelectToolButtonSelected(actionEvent); // Trigger its logic
    }


    /****************** CANVAS EVENT HANDLERS (MOUSE INTERACTIONS) ******************/
    @FXML
    public void setOnMousePressed(MouseEvent event) {
        if (currentState != null) currentState.handleMousePressed(event, this);
    }

    @FXML
    public void setOnMouseDragged(MouseEvent event) {
        if (currentState != null) currentState.handleMouseDragged(event, this);
    }

    @FXML
    public void setOnMouseReleased(MouseEvent event) {
        if (currentState != null) currentState.handleMouseReleased(event, this);
    }

    /****************** CLIPBOARD & EDITING ACTIONS (CLIPBOARD TAB) ******************/
    @FXML
    public void onEraseShapeButtonAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new DeleteShapeCommand(canvasModel));
    }

    @FXML
    public void onCopyShapeButtonAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new CopyShapeCommand(canvasModel));
    }

    @FXML
    public void onPasteButtonAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new PasteShapeCommand(canvasModel));
    }

    @FXML
    public void onCutShapeButtonAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new CutShapeCommand(canvasModel));
    }

    @FXML
    public void onFillColorToChangePickerAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new EditShapesFillColourCommand(
                canvasModel, editFillColorPicker.getValue().toString()
        ));
    }

    @FXML
    public void onStrokeColorToChangePickerAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new EditShapesStrokeColourCommand(
                canvasModel, editStrokeColorPicker.getValue().toString()
        ));
    }

    @FXML
    public void onBringToFrontAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new BringToFrontCommand(canvasModel));
    }

    @FXML
    public void onSendToBackAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new SendToBackCommand(canvasModel));
    }

    @FXML
    public void onFlipHorizontallyAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new FlipShapeHorizontallyCommand(canvasModel));
    }

    @FXML
    public void onFlipVerticallyAction(ActionEvent actionEvent) {
        commandManager.executeCommand(new FlipShapeVerticallyCommand(canvasModel));
    }

    /****************** GETTERS ******************/
    //public Pane getCanvasPane() { return canvas; }
    public ColorPicker getStrokeColorPicker() { return strokeColorPicker; }
    public ColorPicker getFillColorPicker() { return fillColorPicker; }
    public Slider getStrokeSlider() { return strokeSlider; }
    public CommandManager getCommandManager() { return commandManager; }
    public CanvasModel getCanvasModel() { return canvasModel; }
    public CanvasView getCanvasView() { return canvasView; }
    public ShapeDataFactory getFactory() { return factory; }
    public double getStartX() { return startX; }
    public double getStartY() { return startY; }
    public ToggleButton getSelectToolButton() { return selectToolButton; }
    public ToggleButton getLineButton() { return lineButton; }
    public ToggleButton getEllipseButton() { return ellipseButton; }
    public ToggleButton getRectangleButton() { return rectangleButton; }
    public State getSelectionState() { return selectionState; }
    public State getMovingState() { return movingState; }
    public State getResizingState() { return resizingState; }
    //public Group getCanvasGroup() { return canvasGroup; } // Getter for canvasGroup

    /********************* SETTERS ******************/
    public void setCurrentState(State newState) {
        if (currentState != null) {
            currentState.exitState(this);
        }
        currentState = newState;
        if (currentState != null) {
            currentState.enterState(this);
        }
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

    public Pane getCanvas() {
        return canvas;
    }

//    public StackPane getStackPane() {
//        return stackPane;
//    }

    public AnchorPane getHelperStackPane() {
        return helperStackPane;
    }

    public ToggleButton getTextButton() {
        return textButton;
    }

    public State getIdleState() {
        return idleState;
    }

    @FXML
    public void onGroupButtonAction(ActionEvent actionEvent) {
        if (!canvasModel.getSelectedShapes().isEmpty() && canvasModel.getSelectedShapes().size() >= 2) {
            commandManager.executeCommand(new GroupShapesCommand(canvasModel));
        }
    }

    @FXML
    public void onUngroupButtonAction(ActionEvent actionEvent) {
        boolean groupSelected = false;
        for (ShapeData shape : canvasModel.getSelectedShapes().values()) {
            if (shape instanceof GroupedShapesData) {
                groupSelected = true;
                break;
            }
        }
        if (groupSelected) {
            commandManager.executeCommand(new UngroupShapesCommand(canvasModel));
        }
    }
}