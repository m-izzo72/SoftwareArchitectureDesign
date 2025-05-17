package org.softwarearchitecturedesigngroup10.model.factories;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleFactory implements ShapeFactory{

    @Override
    public Shape createShape() {
        return new Rectangle();
    }
}
