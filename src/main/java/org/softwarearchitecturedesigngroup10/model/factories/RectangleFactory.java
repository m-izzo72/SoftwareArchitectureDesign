package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapes.Rectangle;
import org.softwarearchitecturedesigngroup10.model.shapes.Shape;

import javafx.scene.paint.Color;

public class RectangleFactory implements ShapeFactory{

    @Override
    public Shape createShape() {
        Rectangle rectangle = new Rectangle();
        //rectangle.setShapePosition(0, 0); // posizione iniziale
        rectangle.setShapeBorderColor(Color.BLACK);
        rectangle.setShapeFillColor(Color.LIGHTGRAY);
        rectangle.setSelected(false);
        rectangle.setWidth(100);  // esempio valore iniziale
        rectangle.setHeight(50);
        return rectangle;
    }
}
