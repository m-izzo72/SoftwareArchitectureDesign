package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapes.Line;
import org.softwarearchitecturedesigngroup10.model.shapes.Shape;

import javafx.scene.paint.Color;

public class LineFactory implements ShapeFactory{

    @Override
    public Shape createShape() {
        Line line = new Line();
        line.setShapePosition(0, 0);
        line.setShapeBorderColor(Color.BLACK);
        line.setSelected(false);
        line.setX2(100); // fine linea
        line.setY2(0);
        return line;
    }
}
