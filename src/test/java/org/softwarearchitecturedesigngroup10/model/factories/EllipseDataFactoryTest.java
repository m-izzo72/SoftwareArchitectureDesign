package org.softwarearchitecturedesigngroup10.model.factories;

import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import static org.junit.jupiter.api.Assertions.*;

class EllipseDataFactoryTest {

    @Test
    void testCreateShapeData() {
        double startX = 2.0;
        double startY = 4.0;
        double endX = 6.0;
        double endY = 10.0;
        String fillColor = "black";
        String strokeColor = "red";
        double strokeWidth = 3.0;
        double rotationAngle = 0.0;

        EllipseDataFactory factory = new EllipseDataFactory();

        ShapeData shapeData = factory.createShapeData(startX, startY, endX, endY, fillColor, strokeColor, strokeWidth, rotationAngle);

        assertTrue(shapeData instanceof EllipseData);

        EllipseData ellipse = (EllipseData) shapeData;

        // Center Test
        assertEquals((startX + endX)/2, ellipse.getCenterX(), 0.0001);
        assertEquals((startY + endY)/2, ellipse.getCenterY(), 0.0001);

        // Radius Test
        assertEquals(Math.abs(endX - startX)/2, ellipse.getRadiusX(), 0.0001);
        assertEquals(Math.abs(endY - startY)/2, ellipse.getRadiusY(), 0.0001);

        // Color and Thickness Test
        assertEquals(fillColor, ellipse.getFillColor());
        assertEquals(strokeColor, ellipse.getStrokeColor());
        assertEquals(strokeWidth, ellipse.getStrokeWidth());
    }
}