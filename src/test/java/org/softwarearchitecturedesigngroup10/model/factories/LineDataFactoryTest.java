package org.softwarearchitecturedesigngroup10.model.factories;

import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import static org.junit.jupiter.api.Assertions.*;

class LineDataFactoryTest {

    @Test
    void createShapeData() {
        double startX = 1.0;
        double startY = 2.0;
        double endX = 10.0;
        double endY = 20.0;
        String fillColor = "#FFFFFF";
        String strokeColor = "#000000";
        double strokeWidth = 2.0;
        double rotationAngle = 0.0;

        LineDataFactory factory = new LineDataFactory();

        ShapeData shapeData = factory.createShapeData(startX, startY, endX, endY, fillColor, strokeColor, strokeWidth, rotationAngle);
        assertTrue(shapeData instanceof LineData);

        LineData lineData = (LineData) shapeData;

        assertEquals(startX, lineData.getX());
        assertEquals(startY, lineData.getY());
        assertEquals(endX, lineData.getEndX());
        assertEquals(endY, lineData.getEndY());
        assertEquals(fillColor, lineData.getFillColor());
        assertEquals(strokeColor, lineData.getStrokeColor());
        assertEquals(strokeWidth, lineData.getStrokeWidth());

    }
}