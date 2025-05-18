package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class EllipseDataFactory implements ShapeDataFactory{

    public ShapeData createShapeData(double startX, double startY, double endX, double endY, String fillColor, String strokeColor, double strokeWidth, double rotationAngle){
        EllipseData ellipseData = new EllipseData();

        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;
        double radiusX = Math.abs(endX - startX) / 2;
        double radiusY = Math.abs(endY - startY) / 2;

        ellipseData.setCenterX(centerX);
        ellipseData.setCenterY(centerY);
        ellipseData.setRadiusX(radiusX);
        ellipseData.setRadiusY(radiusY);

        // Imposta lo stile
        ellipseData.setFillColor(fillColor);
        ellipseData.setStrokeColor(strokeColor);
        ellipseData.setStrokeWidth(strokeWidth);

        return ellipseData;
    }
}
