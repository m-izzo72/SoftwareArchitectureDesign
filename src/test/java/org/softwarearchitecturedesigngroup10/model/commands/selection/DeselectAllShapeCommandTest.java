package org.softwarearchitecturedesigngroup10.model.commands.selection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;

import static org.junit.jupiter.api.Assertions.*;

class DeselectAllShapeCommandTest {

    static class FakeCanvasModel extends CanvasModel {
        boolean deselectAllShapesCalled = false;

        @Override
        public void deselectAllShapes() {
            deselectAllShapesCalled = true;
        }
    }

    private FakeCanvasModel fakeCanvas;
    private DeselectAllShapeCommand command;

    @BeforeEach
    void setUp() {
        fakeCanvas = new FakeCanvasModel();
        command = new DeselectAllShapeCommand(fakeCanvas);
    }

    @Test
    void testExecuteCallsDeselectAllShapes() {
        command.execute();
        assertTrue(fakeCanvas.deselectAllShapesCalled);
    }

    @Test
    void testIsUndoableReturnsFalse() {
        assertFalse(command.isUndoable());
    }

    @Test
    void testUndoDoesNothing() {
        command.undo();
        assertFalse(fakeCanvas.deselectAllShapesCalled);
    }
}
