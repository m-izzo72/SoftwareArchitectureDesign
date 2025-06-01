package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

import java.util.LinkedHashMap;

public interface CanvasViewInterface {

    void clear();

    void draw(Shape shape);

    void erase(Shape shape);

    void drawAllFromScratch(LinkedHashMap<String, Node> shapes);
}
