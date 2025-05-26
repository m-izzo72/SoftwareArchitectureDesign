package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.view.helper.Highlighter;

import java.util.LinkedHashMap;

public class CanvasView implements CanvasViewInterface {

    Pane canvas;
    private static final double DIMMED_OPACITY = 0.3;

    private Shape previewShape;

    public CanvasView(Pane canvas) {
        this.canvas = canvas;
        this.previewShape = null;
    }

    public void deletePreview() {
        if (this.previewShape != null) {
            canvas.getChildren().remove(this.previewShape);
        }
    }

    public boolean isPreviewShapeNotNull() {
        return previewShape != null;
    }

//    public void highlight(Shape shape) {
//        Highlighter.highlightShape(shape);
//    }
//
//    public void unHighlightAll() {
//        for (Node shape : canvas.getChildren()) {
//            Highlighter.unhighlightShape((Shape) shape);
//        }
//    }

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
        // This is needed otherwise when drawing a shape preview there will be some artifacts and weird effects
        canvas.getChildren().remove(this.previewShape);
        canvas.getChildren().add(this.previewShape);
    }

    @Override
    public void clear() {
        canvas.getChildren().clear();
    }

    @Override
    public void paintAllFromScratch(LinkedHashMap<String, Shape> shapes) {
        canvas.getChildren().clear();

        shapes.forEach((key, value) -> {
            value.setId(key);
            canvas.getChildren().add(value);
        });
    }

}
