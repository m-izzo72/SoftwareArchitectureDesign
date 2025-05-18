package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.shape.Shape;

import java.util.LinkedHashMap;

public interface CanvasViewInterface {

    void clear();

    void repaintAll(LinkedHashMap<String, Shape> shapes);
}
