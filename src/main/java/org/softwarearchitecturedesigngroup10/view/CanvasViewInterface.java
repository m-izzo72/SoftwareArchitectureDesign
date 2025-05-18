package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.LinkedHashMap;

public interface CanvasViewInterface {
    public void paint(Shape shape);

    public void deleteShapes(Shape shape);

    public void clear();

    public void repaintAll(LinkedHashMap<String, Shape> shapes);
}
