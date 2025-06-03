// src/test/java/org/softwarearchitecturedesigngroup10/model/shapesdata/LineDataTest.java
package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LineDataTest {

    private LineData line;

    @BeforeEach
    void setUp() {
        line = new LineData();
        line.setX(10.0);
        line.setY(20.0);
        line.setEndX(30.0);
        line.setEndY(50.0);
    }

    @Test
    void testDefaultType() {
        assertEquals("LD", new LineData().getType());
    }

    @Test
    void testGetSetEndX() {
        line.setEndX(12.34);
        assertEquals(12.34, line.getEndX());
    }

    @Test
    void testGetSetEndY() {
        line.setEndY(-7.89);
        assertEquals(-7.89, line.getEndY());
    }

    @Test
    void testGetWidth() {
        assertEquals(20.0, line.getWidth(), 0.001); // |30.0 - 10.0|
        line.setEndX(5.0); // X = 10, endX = 5
        assertEquals(5.0, line.getWidth(), 0.001); // |5.0 - 10.0|
    }

    @Test
    void testGetHeight() {
        assertEquals(30.0, line.getHeight(), 0.001); // |50.0 - 20.0|
        line.setEndY(15.0); // Y = 20, endY = 15
        assertEquals(5.0, line.getHeight(), 0.001); // |15.0 - 20.0|
    }

    @Test
    void testResize() {
        line.resize(50.0, 60.0); // newWidth, newHeight
        assertEquals(line.getX() + 50.0, line.getEndX(), 0.001);
        assertEquals(line.getY() + 60.0, line.getEndY(), 0.001);

        line.setX(0);
        line.setY(0);
        line.resize(10,20);
        assertEquals(10, line.getEndX());
        assertEquals(20, line.getEndY());
    }

    @Test
    void testClone() {
        line.setFillColor("red");
        line.setStrokeColor("blue");
        line.setStrokeWidth(2.5);
        line.setRotationAngle(30);
        line.setSelected(true);

        LineData clonedLine = (LineData) line.clone();

        assertNotSame(line, clonedLine);
        assertEquals(line.getX(), clonedLine.getX());
        assertEquals(line.getY(), clonedLine.getY());
        assertEquals(line.getEndX(), clonedLine.getEndX());
        assertEquals(line.getEndY(), clonedLine.getEndY());
        assertEquals(line.getFillColor(), clonedLine.getFillColor());
        assertEquals(line.getStrokeColor(), clonedLine.getStrokeColor());
        assertEquals(line.getStrokeWidth(), clonedLine.getStrokeWidth());
        assertEquals(line.getRotationAngle(), clonedLine.getRotationAngle());
        assertEquals(line.isSelected(), clonedLine.isSelected());
        assertEquals(line.getType(), clonedLine.getType());

        // Modify original, clone should be unaffected
        line.setEndX(100.0);
        line.setFillColor("green");

        assertEquals(30.0, clonedLine.getEndX(), 0.001); // Original endX for clone
        assertEquals("red", clonedLine.getFillColor());
    }
}