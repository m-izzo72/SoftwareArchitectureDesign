package org.softwarearchitecturedesigngroup10.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class CanvasModelTest {

    private CanvasModel model;

    @BeforeEach
    void setUp() {
        model = new CanvasModel();
    }

    @Test
    void testAddShape() {
        ShapeData shape = new LineData();
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().containsValue(shape));
    }

    @Test
    void testClear() {
        ShapeData shape = new LineData();
        model.addShape(shape);
        assertFalse(model.getShapes().isEmpty());
        model.clear();
        assertTrue(model.getShapes().isEmpty());
    }

    @Test
    void testSelectShape() {
        ShapeData shape = new LineData();
        model.addShape(shape);
        String shapeId = model.getShapes().entrySet().iterator().next().getKey();
        boolean initiallySelected = shape.isSelected();
        model.selectShape(shapeId);
        assertEquals(!initiallySelected, model.getShapes().get(shapeId).isSelected());
    }

    @Test
    void testDeselectAllShapes() {
        ShapeData shape1 = new LineData();
        ShapeData shape2 = new LineData();
        shape1.setSelected(true);
        shape2.setSelected(true);
        model.addShape(shape1);
        model.addShape(shape2);
        model.deselectAllShapes();
        model.getShapes().values().forEach(shape -> assertFalse(shape.isSelected()));
    }

    @Test
    void testDeleteShapes() {
        ShapeData selectedShape = new LineData();
        selectedShape.setSelected(true);
        ShapeData unselectedShape = new LineData();
        unselectedShape.setSelected(false);

        model.addShape(selectedShape);
        model.addShape(unselectedShape);
        model.deleteShapes();

        assertEquals(1, model.getShapes().size());
        assertFalse(model.getShapes().containsValue(selectedShape));
    }

    @Test
    void testAddObserverAndNotifyObservers() {
        CanvasModel canvasModel = new CanvasModel();
        AtomicBoolean updated = new AtomicBoolean(false);

        canvasModel.addObserver(() -> updated.set(true));
        canvasModel.notifyObservers();

        assertTrue(updated.get());
    }

    @Test
    void testSaveAndLoad() throws IOException, ClassNotFoundException {
        LineData shape = new LineData();
        shape.setX(1);
        shape.setY(2);
        shape.setEndX(3);
        shape.setEndY(4);

        model.addShape(shape);
        File tempFile = File.createTempFile("test", ".tmp");
        tempFile.deleteOnExit();

        model.save(tempFile);
        model.clear();
        assertEquals(0, model.getShapes().size());

        model.load(tempFile);
        assertEquals(1, model.getShapes().size());
    }

    @Test
    void testAddObserverNullOrDuplicateIgnored() {
        ModelObserver observer = () -> {};
        model.addObserver(null);
        model.addObserver(observer);
        model.addObserver(observer);
        model.notifyObservers();
    }
}