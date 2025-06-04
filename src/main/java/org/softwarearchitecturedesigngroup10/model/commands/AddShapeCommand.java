package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AddShapeCommand implements Command {
    private final CanvasModel receiver;
    private final ShapeData shapeToAdd;
    private final LinkedHashMap<String, ShapeData> previousState;

    public AddShapeCommand(CanvasModel receiver, ShapeData shapeToAdd) {
        if (receiver == null) {
            throw new IllegalArgumentException();
        }
        if (shapeToAdd == null) {
            throw new IllegalArgumentException();
        }
        this.receiver = receiver;
        this.shapeToAdd = shapeToAdd;
        this.previousState = new LinkedHashMap<>();
        getPreviousState();
    }

    private void getPreviousState() {
        receiver.getShapes().forEach((key, value) -> previousState.put(key, value.clone()));
    }

    @Override
    public void execute() {
        receiver.addShape(shapeToAdd);
    }

    @Override
    public void undo() {
//        Map.Entry<String, ShapeData> shapeToDelete = null;
//        Iterator<Map.Entry<String, ShapeData>> iterator = receiver.getShapes().entrySet().iterator();
//        while (iterator.hasNext()) { shapeToDelete = iterator.next(); }
//        assert shapeToDelete != null;
//        receiver.deleteShape(shapeToDelete.getKey());
//        System.out.println("Trying to undo the shape " + shapeToAdd);
        receiver.clear();
        previousState.forEach(receiver::addShapeByKeepingKeys);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}


