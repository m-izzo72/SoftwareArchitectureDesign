package org.softwarearchitecturedesigngroup10.view;

import javafx.scene.Node;

import java.util.LinkedHashMap;

public interface CanvasViewInterface {

    void clear();

    void draw(Node shape);

    void erase(Node shape);

    void drawAllFromScratch(LinkedHashMap<String, Node> shapes);
}
