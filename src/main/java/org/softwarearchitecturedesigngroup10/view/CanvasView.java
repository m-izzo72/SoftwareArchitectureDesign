package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.view.helper.Highlighter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CanvasView implements CanvasViewInterface {

    Pane canvas;

    public CanvasView(Pane canvas) {
        this.canvas = canvas;
    }

    public void highlight(Shape shape) {
        Highlighter.highlightShape(shape);
    }

    public void unHighlightAll() {
        for (Node shape : canvas.getChildren()) {
            Highlighter.unhighlightShape((Shape) shape);
        }
    }

    public void paint(Shape shape) {
        canvas.getChildren().add(shape);
    }

    public void erase(Shape shape) {
        canvas.getChildren().remove(shape);
    }

    public void stylePreviewShape(Shape shape) {
        shape.setStroke(Color.DARKGRAY);
        shape.getStrokeDashArray().setAll(5.0, 5.0);
        if (shape instanceof Rectangle || shape instanceof Ellipse) {
            shape.setFill(Color.TRANSPARENT);
        }
        shape.setMouseTransparent(true);
    }

    public void updatePreviewShapeGeometry(Shape previewShape, double currentX, double currentY, double startX, double startY) {
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
            line.setEndX(currentX);
            line.setEndY(currentY);
        }
    }

    @Override
    public void clear() {
        canvas.getChildren().clear();
    }

    @Override
    public void paintAllFromScratch(LinkedHashMap<String, Shape> shapes) {
        for (Map.Entry<String, Shape> entry : shapes.entrySet()) {
            entry.getValue().setId(entry.getKey());
            canvas.getChildren().add(entry.getValue());
        }

    }

    public double getCanvasWidth() {
        return canvas.getWidth();
    }
}
