package adapter;// src/test/java/org/softwarearchitecturedesigngroup10/controller/adapters/ShapeConverterTest.java

import javafx.scene.Node;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.controller.adapters.ShapeConverter;
import org.softwarearchitecturedesigngroup10.model.shapesdata.*;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShapeConverterTest {

    private ShapeConverter shapeConverter;

    @BeforeEach
    void setUp() {
        shapeConverter = new ShapeConverter();
    }

    @Test
    void convert_LineData_ReturnsJavaFXLine() {
        LineData lineData = new LineData();
        lineData.setX(10); lineData.setY(10);
        lineData.setEndX(20); lineData.setEndY(20);
        lineData.setStrokeColor("black");
        lineData.setStrokeWidth(1);

        Node fxNode = shapeConverter.convert(lineData);
        assertTrue(fxNode instanceof Line);
        Line fxLine = (Line) fxNode;
        assertEquals(10, fxLine.getStartX());
        assertEquals(20, fxLine.getEndY());
    }

    @Test
    void convert_RectangleData_ReturnsJavaFXRectangle() {
        RectangleData rectData = new RectangleData();
        rectData.setX(5); rectData.setY(5);
        rectData.setWidth(15); rectData.setHeight(25);
        rectData.setFillColor("blue");
        rectData.setStrokeColor("darkblue");
        rectData.setStrokeWidth(2);

        Node fxNode = shapeConverter.convert(rectData);
        assertTrue(fxNode instanceof Rectangle);
        Rectangle fxRect = (Rectangle) fxNode;
        assertEquals(5, fxRect.getX());
        assertEquals(15, fxRect.getWidth());
        assertNotNull(fxRect.getFill());
    }

    @Test
    void convert_EllipseData_ReturnsJavaFXEllipse() {
        EllipseData ellipseData = new EllipseData();
        ellipseData.setCenterX(30); ellipseData.setCenterY(30);
        ellipseData.setRadiusX(10); ellipseData.setRadiusY(5);
        ellipseData.setFillColor("green");
        ellipseData.setStrokeWidth(0); // No stroke

        Node fxNode = shapeConverter.convert(ellipseData);
        assertTrue(fxNode instanceof Ellipse);
        Ellipse fxEllipse = (Ellipse) fxNode;
        assertEquals(30, fxEllipse.getCenterX());
        assertEquals(10, fxEllipse.getRadiusX());
        assertNotNull(fxEllipse.getFill());
    }

    @Test
    void convert_PolygonData_ReturnsJavaFXPolygon() {
        PolygonData polygonData = new PolygonData();
        polygonData.setPoints(new ArrayList<>(Arrays.asList(0.0,0.0, 10.0,0.0, 5.0,10.0)));
        polygonData.setFillColor("yellow");

        Node fxNode = shapeConverter.convert(polygonData);
        assertTrue(fxNode instanceof Polygon);
        Polygon fxPolygon = (Polygon) fxNode;
        assertEquals(6, fxPolygon.getPoints().size()); // 3 pairs of coordinates
        assertNotNull(fxPolygon.getFill());
    }

    @Test
    void convert_TextData_ReturnsJavaFXText() {
        TextData textData = new TextData();
        textData.setX(5); textData.setY(15);
        textData.setText("Hello");
        textData.setFontFamily("Arial");
        textData.setFontSize(12);
        textData.setFillColor("red");

        Node fxNode = shapeConverter.convert(textData);
        assertTrue(fxNode instanceof Text);
        Text fxText = (Text) fxNode;
        assertEquals("Hello", fxText.getText());
        assertEquals(5, fxText.getX());
        assertNotNull(fxText.getFill());
    }

    @Test
    void convert_GroupedShapesData_ReturnsJavaFXGroup() {
        GroupedShapesData groupData = new GroupedShapesData();
        LineData childLine = new LineData();
        childLine.setX(0); childLine.setY(0); childLine.setEndX(1); childLine.setEndY(1);
        childLine.setStrokeColor("black");
        groupData.add(childLine);

        RectangleData childRect = new RectangleData();
        childRect.setX(5); childRect.setY(5); childRect.setWidth(2); childRect.setHeight(2);
        childRect.setFillColor("red");
        groupData.add(childRect);

        Node fxNode = shapeConverter.convert(groupData);
        assertTrue(fxNode instanceof javafx.scene.Group);
        javafx.scene.Group fxGroup = (javafx.scene.Group) fxNode;
        assertEquals(2, fxGroup.getChildren().size());
        assertTrue(fxGroup.getChildren().get(0) instanceof Line);
        assertTrue(fxGroup.getChildren().get(1) instanceof Rectangle);
    }


    @Test
    void convert_UnknownShapeData_ThrowsIllegalArgumentException() {
        // Create a dummy ShapeData subclass not registered in the converter
        class UnknownShapeData extends ShapeData {
            @Override public double getWidth() { return 0;}
            @Override public double getHeight() { return 0; }
            @Override public void resize(double newWidth, double newHeight) {}
        }
        UnknownShapeData unknownData = new UnknownShapeData();
        assertThrows(IllegalArgumentException.class, () -> shapeConverter.convert(unknownData));
    }
}