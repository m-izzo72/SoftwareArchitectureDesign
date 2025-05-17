package org.softwarearchitecturedesigngroup10.model.factories;



import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class EllipseFactory implements ShapeFactory{

    @Override
    public Shape createShape() {
        return new Ellipse();
    }
}

