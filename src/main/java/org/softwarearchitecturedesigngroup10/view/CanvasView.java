package org.softwarearchitecturedesigngroup10.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static java.lang.Double.parseDouble;

public class CanvasView implements CanvasViewInterface {

    Pane canvas;
    StackPane canvasStackPane;
    ScrollPane scrollableCanvasContainer;
    Canvas grid;
    double gridSize;
    private static final double DIMMED_OPACITY = 0.3;
    private final Rectangle resizeHandle;
    private static final double HANDLE_SIZE = 8.0;
    public static final String RESIZE_HANDLE_ID = "resizeHandle";
    public final ArrayList<Shape> vertices = new ArrayList<>();

    private Shape previewShape;

    public CanvasView(Pane canvas, StackPane canvasStackPane, ScrollPane scrollableCanvasContainer, Canvas grid) {
        this.canvas = canvas;
        this.canvasStackPane = canvasStackPane;
        this.scrollableCanvasContainer = scrollableCanvasContainer;
        this.grid = grid;

        this.previewShape = null;
        this.resizeHandle = new Rectangle(HANDLE_SIZE, HANDLE_SIZE, Color.BLUE);
        this.resizeHandle.setStroke(Color.WHITE);
        this.resizeHandle.setId(RESIZE_HANDLE_ID);
        this.resizeHandle.setCursor(Cursor.SE_RESIZE);
        this.resizeHandle.setVisible(false);
        this.canvas.getChildren().add(this.resizeHandle);

        this.gridSize = 20; // default value
    }

    /** CANVAS ZOOM AND SCROLL */

    public void zoomListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double zoomFactor = newValue.doubleValue() / 3.0;
        if (zoomFactor <= 0) zoomFactor = 0.1;

        canvasStackPane.setScaleX(zoomFactor);
        canvasStackPane.setScaleY(zoomFactor);

        scrollableCanvasContainer.setHvalue(0.5);
        scrollableCanvasContainer.setVvalue(0.5);
    }

    public void setCanvasScrollableAndResizable(TextField width, TextField height, ToggleButton selected) {
        setCanvasClippable();
        bindTextFieldToCanvasSize(width, height, selected);
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

    private void bindTextFieldToCanvasSize(TextField canvasWidthInput, TextField canvasHeightInput, ToggleButton selected) {
        DoubleProperty customWidthProperty = new SimpleDoubleProperty(canvas.getPrefWidth());
        DoubleProperty customHeightProperty = new SimpleDoubleProperty(canvas.getPrefHeight());

        canvas.prefWidthProperty().bind(customWidthProperty);
        canvas.prefHeightProperty().bind(customHeightProperty);

        ChangeListener<Number> canvasSizeListener = (observable, oldValue, newValue) -> {
            if (grid != null && grid.getGraphicsContext2D() != null) { // Ensure grid is ready
                grid.setWidth(canvas.getPrefWidth()); // Match grid canvas size to drawing canvas size
                grid.setHeight(canvas.getPrefHeight());
                if(selected.isSelected()) drawGrid();
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

    /** GRID */

    public void clearGrid() {
        grid.getGraphicsContext2D().clearRect(0, 0, grid.getWidth(), grid.getHeight());
    }

    public void drawGrid() {
        double width = grid.getWidth();
        double height = grid.getHeight();

        GraphicsContext gc = grid.getGraphicsContext2D();

        if (width <= 0 || height <= 0) {
            System.err.println("Skipping grid draw: Invalid dimensions - W: " + width + ", H: " + height);
            return;
        }

        clearGrid(); // Clear previous grid

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

    public void setGridSize(double gridSize) {
        this.gridSize = gridSize;
    }

    /** SELECTION EFFECTS */

    public void setSelectedEffect(Node shape) {
        if (shape != null) {
            shape.setOpacity(DIMMED_OPACITY);
        }

    }

    public void setUnselectedState(Node shape) {
        if (shape != null) {
            shape.setOpacity(1.0);
        }

    }

    /** SHAPE DRAWING */

    public void draw(Node shape) {
        canvas.getChildren().add(shape);
        resizeHandle.toFront(); // Assicura che la maniglia sia sopra
    }

    @Override
    public void drawAllFromScratch(LinkedHashMap<String, Node> shapes) {
        List<Node> shapesToRemove = canvas.getChildren().stream()
                .filter(node -> node != resizeHandle && !(node instanceof Shape && node.getId() != null && shapes.containsKey(node.getId())))
                .toList();
        canvas.getChildren().removeAll(shapesToRemove);

        shapes.forEach((key, value) -> {
            value.setId(key);
            canvas.getChildren().removeIf(node -> node.getId() != null && node.getId().equals(key));

            canvas.getChildren().add(value);
        });

        List<Node> toRemove = canvas.getChildren().stream()
                .filter(node -> node != resizeHandle && node.getId() != null && !shapes.containsKey(node.getId()))
                .toList();
        canvas.getChildren().removeAll(toRemove);

        resizeHandle.toFront();
    }

    public void drawPreview() {
        canvas.getChildren().add(previewShape);
    }

    public void erase(Node shape) {
        canvas.getChildren().remove(shape);
    }

    @Override
    public void clear() {
        canvas.getChildren().clear();
        canvas.getChildren().add(resizeHandle);
        resizeHandle.setVisible(false);
    }

    /** PREVIEWS */

    public void stylePreviewShape() {
        previewShape.setStroke(Color.DARKGRAY);
        previewShape.getStrokeDashArray().setAll(5.0, 5.0);
        if (previewShape instanceof Rectangle || previewShape instanceof Ellipse) {
            previewShape.setFill(Color.TRANSPARENT);
        }
        previewShape.setMouseTransparent(true);
    }

    public void updatePreviewShapeGeometry(double currentX, double currentY, double startX, double startY) {
        if (previewShape == null) return;

        if (previewShape instanceof Rectangle rect) {
            double x = Math.min(startX, currentX);
            double y = Math.min(startY, currentY);
            double width = Math.abs(currentX - startX);
            double height = Math.abs(currentY - startY);
            rect.setX(x);
            rect.setY(y);
            rect.setWidth(width);
            rect.setHeight(height);
        } else if (previewShape instanceof Ellipse ellipse) {
            double x = Math.min(startX, currentX);
            double y = Math.min(startY, currentY);
            double width = Math.abs(currentX - startX);
            double height = Math.abs(currentY - startY);
            ellipse.setCenterX(x + width / 2.0);
            ellipse.setCenterY(y + height / 2.0);
            ellipse.setRadiusX(width / 2.0);
            ellipse.setRadiusY(height / 2.0);
        } else if (previewShape instanceof Line line) {
            line.setStartX(startX);
            line.setStartY(startY);
            line.setEndX(currentX);
            line.setEndY(currentY);
        }
        canvas.getChildren().remove(this.previewShape);
        canvas.getChildren().add(this.previewShape);
    }

    public void setPolygonVerticesPreview(double x, double y) {
        Shape vertex = new Circle(3, Color.BLUE);
        vertex.setLayoutX(x); vertex.setLayoutY(y);
        vertices.add(vertex);
        canvas.getChildren().addAll(vertex);
    }

    public void erasePolygonVerticesPreview() {
        canvas.getChildren().removeAll(this.vertices);
    }

    public void deletePreview() {
        if (this.previewShape != null) {
            canvas.getChildren().remove(this.previewShape);
        }
    }

    public boolean isPreviewShapeNotNull() {
        return previewShape != null;
    }

    public void setPreviewShape(Shape previewShape) {
        this.previewShape = previewShape;
    }

    public void updateResizeHandle(Node shape) {
        if (shape != null && !(shape instanceof Group || shape instanceof Text)) {
            Bounds bounds = shape.getBoundsInParent();
            resizeHandle.setX(bounds.getMaxX() - HANDLE_SIZE / 2);
            resizeHandle.setY(bounds.getMaxY() - HANDLE_SIZE / 2);
            resizeHandle.setUserData(shape.getId());
            resizeHandle.setVisible(true);
            resizeHandle.toFront();
        } else {
            resizeHandle.setVisible(false);
        }
    }

    public Rectangle getResizeHandle() {
        return resizeHandle;
    }

    public void rotatePreview(double angle) {
        canvas.getChildren()
                .stream()
                .filter(element -> element.getEffect() != null)
                .forEach(element -> {
                    if(element instanceof Group group) group.getChildren().forEach(child -> child.setRotate(angle));
                    else element.setRotate(angle);
                });
    }

    public Pane createPolygonAlertPane(Button confirmPolygonButton) {
        Pane polygonAlertPane = getPolygonAlertPane();

        Text instructionText = new Text();
        instructionText.setLayoutX(7.0);
        instructionText.setLayoutY(18.0);
        instructionText.setFill(Color.valueOf("#797b86"));

        instructionText.setStrokeType(StrokeType.OUTSIDE);
        instructionText.setStrokeWidth(0.0);
        instructionText.setText("To draw a custom polygon, press the mouse button on the canvas to save the vertices. You may save up to 12 vertices. Once you're done click Confirm to draw a polygon.");
        instructionText.setWrappingWidth(344);


        confirmPolygonButton.setTextFill(Color.valueOf("#797b86"));
        confirmPolygonButton.setId("confirmPolygon");
        confirmPolygonButton.setLayoutX(300);
        confirmPolygonButton.setLayoutY(55);
        confirmPolygonButton.setMnemonicParsing(false);

        polygonAlertPane.getChildren().addAll(instructionText, confirmPolygonButton);

        return polygonAlertPane;
    }

    private Pane getPolygonAlertPane() {
        Pane polygonAlertPane = new Pane();
        polygonAlertPane.setId("polygonAlert");
        polygonAlertPane.setLayoutY(15);
        polygonAlertPane.setLayoutX(268);

        polygonAlertPane.setMaxHeight(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setMinHeight(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setMinWidth(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setPrefHeight(85.0);
        polygonAlertPane.setPrefWidth(360.0);
        return polygonAlertPane;
    }

    public void insertPolygonAlertPane(Pane polygonAlertPane, AnchorPane anchorPane) {
        anchorPane.getChildren().add(polygonAlertPane);
    }

    public void removePolygonAlertPane(Pane polygonAlertPane, AnchorPane anchorPane) {
        anchorPane.getChildren().remove(polygonAlertPane);
    }
}