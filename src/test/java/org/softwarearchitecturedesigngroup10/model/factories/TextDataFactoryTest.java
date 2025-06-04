package org.softwarearchitecturedesigngroup10.model.factories;

import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.TextData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TextDataFactoryTest {

    @Test
    void testCreateShapeDataWithSpecifics() {
        TextDataFactory factory = new TextDataFactory();
        double x = 10.0;
        double y = 20.0;
        String text = "Hello Test";
        String fontFamily = "Arial";
        double fontSize = 12.0;
        String fillColor = "black";
        String strokeColorParam = "red";
        double strokeWidthParam = 1.0;
        double rotationAngle = 15.0;

        ShapeData shapeData = factory.createShapeData(x, y, text, fontFamily, fontSize, fillColor, strokeColorParam, strokeWidthParam, rotationAngle);

        assertTrue(shapeData instanceof TextData);
        TextData textData = (TextData) shapeData;

        assertEquals(x, textData.getX());
        assertEquals(y, textData.getY());
        assertEquals(text, textData.getText());
        assertEquals(fontFamily, textData.getFontFamily());
        assertEquals(fontSize, textData.getFontSize());
        assertEquals(fillColor, textData.getFillColor());
        assertNull(textData.getStrokeColor());
        assertEquals(0.0, textData.getStrokeWidth());
        assertEquals(rotationAngle, textData.getRotationAngle());
        assertEquals("TD", textData.getType());
    }

    @Test
    void testCreateShapeDataWithPoints() {
        TextDataFactory factory = new TextDataFactory();
        ArrayList<Double> points = new ArrayList<>(Arrays.asList(50.0, 75.0));
        String fillColor = "red";
        String strokeColorParam = "IGNORED";
        double strokeWidthParam = 5.0;
        double rotationAngle = 0.0;

        ShapeData shapeData = factory.createShapeData(points, fillColor, strokeColorParam, strokeWidthParam, rotationAngle);

        assertTrue(shapeData instanceof TextData);
        TextData textData = (TextData) shapeData;

        assertEquals(50.0, textData.getX());
        assertEquals(75.0, textData.getY());
        assertEquals("DefaultText", textData.getText());
        assertEquals(16, textData.getFontSize());
        assertEquals(fillColor, textData.getFillColor());
        assertNull(textData.getStrokeColor());
        assertEquals(0.0, textData.getStrokeWidth());
        assertEquals(rotationAngle, textData.getRotationAngle());
    }

    @Test
    void testCreateShapeDataWithPoints_NullOrShortPoints_ThrowsException() {
        TextDataFactory factory = new TextDataFactory();
        String fillColor = "blue";
        String strokeColor = "yellow";
        double strokeWidth = 1.0;
        double rotationAngle = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            factory.createShapeData(null, fillColor, strokeColor, strokeWidth, rotationAngle);
        });

        ArrayList<Double> shortPoints = new ArrayList<>(Arrays.asList(10.0));
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createShapeData(shortPoints, fillColor, strokeColor, strokeWidth, rotationAngle);
        });
    }
}