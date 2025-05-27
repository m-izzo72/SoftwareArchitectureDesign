package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.Iterator;
import java.util.Map;

public class AddShapeCommand implements Command {
    private final CanvasModel receiver;
    private final ShapeData shapeToAdd;

    public AddShapeCommand(CanvasModel receiver, ShapeData shapeToAdd) {
        if (receiver == null) {
            throw new IllegalArgumentException();
        }
        if (shapeToAdd == null) {
            throw new IllegalArgumentException();
        }
        this.receiver = receiver;
        this.shapeToAdd = shapeToAdd;
    }

    @Override
    public void execute() {
        receiver.addShape(shapeToAdd);
    }

    @Override
    public void undo() {
        Map.Entry<String, ShapeData> shapeToDelete = null;
        Iterator<Map.Entry<String, ShapeData>> iterator = receiver.getShapes().entrySet().iterator();
        while (iterator.hasNext()) { shapeToDelete = iterator.next(); }
        assert shapeToDelete != null;
        receiver.deleteShape(shapeToDelete.getKey());
        System.out.println("Trying to undo the shape " + shapeToAdd);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}


