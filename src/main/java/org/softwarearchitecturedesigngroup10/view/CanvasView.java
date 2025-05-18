package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CanvasView implements CanvasViewInterface {

    Pane canvas;

    public CanvasView(Pane canvas) {
        this.canvas = canvas;
    }

    @Override
    public void paint(Shape shape) {

    }

    @Override
    public void deleteShapes(Shape shape) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void repaintAll(LinkedHashMap<String, Shape> shapes) {
        for (Map.Entry<String, Shape> entry : shapes.entrySet()) {
            entry.getValue().setId(entry.getKey());
            canvas.getChildren().add(entry.getValue());
        }

    }
}
