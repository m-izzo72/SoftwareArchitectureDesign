package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineDataTest {

    @Test
    void testGetSetEndX() {
        LineData lineData = new LineData();
        lineData.setEndX(12.34);
        assertEquals(12.34, lineData.getEndX());
    }

    @Test
    void testGetSetEndY() {
        LineData lineData = new LineData();
        lineData.setEndY(-7.89);
        assertEquals(-7.89, lineData.getEndY());
    }
}