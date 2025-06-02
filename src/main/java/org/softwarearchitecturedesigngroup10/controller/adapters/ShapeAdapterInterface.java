package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.Node;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public interface ShapeAdapterInterface {

    Node toFXShape(ShapeData data);
}
