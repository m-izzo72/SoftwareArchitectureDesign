package org.softwarearchitecturedesigngroup10.model.factories;


import javafx.scene.shape.Shape;

import javafx.scene.paint.Color;
public interface ShapeFactory {
    Shape createShape(double startX, double startY, double endX, double endY, Color fill, Color stroke, double strokeWidth);
}
