package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EllipseDataTest {

    @Test
    void testGetSetCenterX() {
        EllipseData ellipse = new EllipseData();
        ellipse.setCenterX(10.5);
        assertEquals(10.5, ellipse.getCenterX());
    }

    @Test
    void testGetSetCenterY() {
        EllipseData ellipse = new EllipseData();
        ellipse.setCenterY(-3.2);
        assertEquals(-3.2, ellipse.getCenterY());
    }

    @Test
    void testGetSetRadiusX() {
        EllipseData ellipse = new EllipseData();
        ellipse.setRadiusX(15.0);
        assertEquals(15.0, ellipse.getRadiusX());
    }

}