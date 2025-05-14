package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapes.Ellipse;
import org.softwarearchitecturedesigngroup10.model.shapes.Shape;

import javafx.scene.paint.Color;

public class EllipseFactory implements ShapeFactory{

    @Override
    public Shape createShape() {
        Ellipse ellipse = new Ellipse();
        ellipse.setShapePosition(0, 0);
        ellipse.setShapeBorderColor(Color.BLACK);
        ellipse.setShapeFillColor(Color.LIGHTGRAY);
        ellipse.setSelected(false);
        ellipse.setRadiusX(50);
        ellipse.setRadiusY(30);
        return ellipse;
    }
}

