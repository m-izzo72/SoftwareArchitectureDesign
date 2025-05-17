package org.softwarearchitecturedesigngroup10.model.converter;

import org.softwarearchitecturedesigngroup10.model.helper.ShapeData;
import javafx.scene.shape.Shape;

public interface ShapeConverter {

    ShapeData convertToData(Shape shape);
    Shape convertFromData(ShapeData shapeData);
    String getSupportedType();
}
