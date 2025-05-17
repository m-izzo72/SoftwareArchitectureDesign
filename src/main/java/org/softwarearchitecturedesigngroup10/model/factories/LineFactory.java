package org.softwarearchitecturedesigngroup10.model.factories;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LineFactory implements ShapeFactory{

    @Override
    public Shape createShape() {

        return  new Line();
    }
}
