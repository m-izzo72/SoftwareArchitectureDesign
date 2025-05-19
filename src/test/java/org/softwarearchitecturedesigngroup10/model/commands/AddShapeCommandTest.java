package org.softwarearchitecturedesigngroup10.model.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class AddShapeCommandTest {

    private static class DummyCanvasModel extends CanvasModel {
        private final LinkedHashMap<String, ShapeData> shapes = new LinkedHashMap<>();
        private int counter = 0;

        @Override
        public void addShape(ShapeData shape) {
            shapes.put("shape" + counter++, shape); // usa chiavi dummy per test
        }

        @Override
        public LinkedHashMap<String, ShapeData> getShapes() {
            return shapes;
        }
    }

    private DummyCanvasModel dummyCanvas;
    private ShapeData shape;

    @BeforeEach
    void setup() {
        dummyCanvas = new DummyCanvasModel();
        shape = new LineData();
    }

    @Test
    void testConstructorThrowsOnNullReceiver() {
        assertThrows(IllegalArgumentException.class, () -> new AddShapeCommand(null, shape));
    }

    @Test
    void testConstructorThrowsOnNullShape() {
        assertThrows(IllegalArgumentException.class, () -> new AddShapeCommand(dummyCanvas, null));
    }

    @Test
    void testExecuteAddsShapeToCanvas() {
        AddShapeCommand command = new AddShapeCommand(dummyCanvas, shape);
        assertEquals(0, dummyCanvas.getShapes().size());

        command.execute();

        assertEquals(1, dummyCanvas.getShapes().size());
        assertTrue(dummyCanvas.getShapes().containsValue(shape));
    }
}
