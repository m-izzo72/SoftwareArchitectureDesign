package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
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

    @Override
    public void clear() {
        canvas.getChildren().clear();
    }

    @Override
    public void repaintAll(LinkedHashMap<String, Shape> shapes) {
        for (Map.Entry<String, Shape> entry : shapes.entrySet()) {
            entry.getValue().setId(entry.getKey());
            canvas.getChildren().add(entry.getValue());
        }

    }
}
