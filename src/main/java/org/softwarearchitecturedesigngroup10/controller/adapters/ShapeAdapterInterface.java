package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public interface ShapeAdapterInterface {

    Shape toFXShape(ShapeData data);
}
