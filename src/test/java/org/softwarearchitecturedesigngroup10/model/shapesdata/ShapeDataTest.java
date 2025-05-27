package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeDataTest {

    private ShapeData createShapeData(){
        return new ShapeData() {
            @Override
            public double getWidth() {
                return 0;
            }

            @Override
            public double getHeight() {
                return 0;
            }

            @Override
            public void resize(double newWidth, double newHeight) {

            }
        };
    }
    @Test
    void testSetGetX(){
        ShapeData shape = createShapeData();
        shape.setX(12.5);
        assertEquals(12.5, shape.getX());
    }

    @Test
    void testGetSetY() {
        ShapeData shape = createShapeData();
        shape.setY(-5.3);
        assertEquals(-5.3, shape.getY());
    }

    @Test
    void testSetGetFillColor(){
        ShapeData shape = createShapeData();
        shape.setFillColor("red");
        assertEquals("red", shape.getFillColor());
    }

    @Test
    void testGetSetStrokeColor() {
        ShapeData shape = createShapeData();
        shape.setStrokeColor("#00ff00");
        assertEquals("#00ff00", shape.getStrokeColor());
    }

    @Test
    void testGetSetStrokeWidth() {
        ShapeData shape = createShapeData();
        shape.setStrokeWidth(2.5);
        assertEquals(2.5, shape.getStrokeWidth());
    }

    @Test
    void testIsSelected(){
        ShapeData shape = createShapeData();
        shape.setSelected(true);
        assertTrue(shape.isSelected());
    }

}