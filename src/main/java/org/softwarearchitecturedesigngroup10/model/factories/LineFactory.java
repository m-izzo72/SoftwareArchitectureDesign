package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapes.Line;
import org.softwarearchitecturedesigngroup10.model.shapes.Shape;

import javafx.scene.paint.Color;

public class LineFactory implements ShapeFactory{

    @Override
    public Shape createShape() {

        return  new Line();
    }
}
