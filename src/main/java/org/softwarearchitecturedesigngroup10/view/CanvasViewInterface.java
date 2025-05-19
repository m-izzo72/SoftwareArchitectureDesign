package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.shape.Shape;

import java.util.LinkedHashMap;

public interface CanvasViewInterface {

    void clear();

    void paint(Shape shape);

    void erase(Shape shape);

    void paintAllFromScratch(LinkedHashMap<String, Shape> shapes);
}
