package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public interface ShapeDataFactory {

    public ShapeData createShapeData(double startX, double startY, double endX, double endY, String fillColor, String strokeColor, double strokeWidth, double rotationAngle);
}
