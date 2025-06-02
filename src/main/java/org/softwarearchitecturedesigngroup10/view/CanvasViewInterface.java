package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

import java.util.LinkedHashMap;

public interface CanvasViewInterface {

    void clear();

    void paint(Node shape);

    void erase(Node shape);

    void paintAllFromScratch(LinkedHashMap<String, Node> shapes);
}
