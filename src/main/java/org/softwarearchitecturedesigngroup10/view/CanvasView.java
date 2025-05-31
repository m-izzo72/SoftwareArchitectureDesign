package org.softwarearchitecturedesigngroup10.view;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node; // Importa Node
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.view.helper.Highlighter;

import java.util.LinkedHashMap;
import java.util.List; // Importa List
import java.util.stream.Collectors; // Importa Collectors

public class CanvasView implements CanvasViewInterface {

    Pane canvas;
    private static final double DIMMED_OPACITY = 0.3;
    private Rectangle resizeHandle;
    private static final double HANDLE_SIZE = 8.0;
    public static final String RESIZE_HANDLE_ID = "resizeHandle";

    private Shape previewShape;

    public CanvasView(Pane canvas) {
        this.canvas = canvas;
        this.previewShape = null;
        this.resizeHandle = new Rectangle(HANDLE_SIZE, HANDLE_SIZE, Color.BLUE);
        this.resizeHandle.setStroke(Color.WHITE);
        this.resizeHandle.setId(RESIZE_HANDLE_ID);
        this.resizeHandle.setCursor(Cursor.SE_RESIZE);
        this.resizeHandle.setVisible(false); // Nascosto di default
        this.canvas.getChildren().add(this.resizeHandle); // Aggiungilo subito e tienilo
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

    public void setSelectedEffect(Shape shape) {
        if (shape != null) {
            shape.setOpacity(DIMMED_OPACITY);
        }

    }

    public void setUnselectedState(Shape shape) {
        if (shape != null) {
            shape.setOpacity(1.0);
        }

    }

    public void paint(Shape shape) {
        canvas.getChildren().add(shape);
        resizeHandle.toFront(); // Assicura che la maniglia sia sopra
    }

    public void paintPreview() {
        canvas.getChildren().add(previewShape);
    }

    public void erase(Shape shape) {
        canvas.getChildren().remove(shape);
    }

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



    @Override
    public void clear() {
        canvas.getChildren().clear();
        canvas.getChildren().add(resizeHandle); // Riaggiungi la maniglia
        resizeHandle.setVisible(false);
    }

    @Override
    public void paintAllFromScratch(LinkedHashMap<String, Shape> shapes) {
        // Rimuovi tutte le forme (tranne la maniglia che è già presente)
        List<Node> shapesToRemove = canvas.getChildren().stream()
                .filter(node -> node != resizeHandle && !(node instanceof Shape && node.getId() != null && shapes.containsKey(node.getId())))
                .toList();
        canvas.getChildren().removeAll(shapesToRemove);

        // Aggiungi/Aggiorna le forme
        shapes.forEach((key, value) -> {
            value.setId(key);
            // Rimuovi la vecchia versione se esiste, poi aggiungi la nuova
            canvas.getChildren().removeIf(node -> node.getId() != null && node.getId().equals(key));
            canvas.getChildren().add(value);
        });

        // Rimuovi le forme che non sono più nel modello
        List<Node> toRemove = canvas.getChildren().stream()
                .filter(node -> node != resizeHandle && node.getId() != null && !shapes.containsKey(node.getId()))
                .toList();
        canvas.getChildren().removeAll(toRemove);

        resizeHandle.toFront(); // Assicura che la maniglia sia sempre in cima
    }


    public void updateResizeHandle(Shape shape) {
        if (shape != null) {
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
                    element.setRotate(angle);
                });
    }

}