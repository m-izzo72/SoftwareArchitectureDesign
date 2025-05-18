package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class LineDataFactory implements ShapeDataFactory{

    @Override
    public ShapeData createShapeData(double startX, double startY, double endX, double endY, String fillColor, String strokeColor, double strokeWidth, double rotationAngle){
        LineData lineData = new LineData();

        lineData.setX(startX);
        lineData.setY(startY);
        lineData.setEndX(endX);
        lineData.setEndY(endY);

        // Imposta lo stile
        lineData.setFillColor(fillColor);
        lineData.setStrokeColor(strokeColor);
        lineData.setStrokeWidth(strokeWidth);

        return lineData;
    }
}
