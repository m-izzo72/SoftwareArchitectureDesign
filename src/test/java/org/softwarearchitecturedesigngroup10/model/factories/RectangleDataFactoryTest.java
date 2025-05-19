package org.softwarearchitecturedesigngroup10.model.factories;

import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import static org.junit.jupiter.api.Assertions.*;

class RectangleDataFactoryTest {

    @Test
    void testCreateShapeData() {
        double startX = 3.0;
        double startY = 5.0;
        double endX = 8.0;
        double endY = 10.0;
        String fillColor = "#112233";
        String strokeColor = "#445566";
        double strokeWidth = 4.0;
        double rotationAngle = 0.0;

        RectangleDataFactory factory = new RectangleDataFactory();

        ShapeData shapeData = factory.createShapeData(startX, startY, endX, endY, fillColor, strokeColor, strokeWidth, rotationAngle);

        assertTrue(shapeData instanceof RectangleData);

        RectangleData rectangleData = (RectangleData) shapeData;

        // Dimesion Test
        assertEquals(Math.min(startX, endX), rectangleData.getX(), 0.0001);
        assertEquals(Math.min(startY, endY), rectangleData.getY(), 0.0001);
        assertEquals(Math.abs(endX - startX), rectangleData.getWidth(), 0.0001);
        assertEquals(Math.abs(endY - startY), rectangleData.getHeight(), 0.0001);

        // Color and Thickness Test
        assertEquals(fillColor, rectangleData.getFillColor());
        assertEquals(strokeColor, rectangleData.getStrokeColor());
        assertEquals(strokeWidth, rectangleData.getStrokeWidth());
    }
}