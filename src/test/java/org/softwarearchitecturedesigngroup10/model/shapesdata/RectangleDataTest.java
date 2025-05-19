package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleDataTest {
    @Test
    void testGetSetWidth(){
        RectangleData rectangleData = new RectangleData();
        rectangleData.setWidth(20.5);
        assertEquals(20.5, rectangleData.getWidth());
    }

    @Test
    void testGetSetHeight(){
        RectangleData rectangleData = new RectangleData();
        rectangleData.setHeight(15.75);
        assertEquals(15.75, rectangleData.getHeight());
    }
}