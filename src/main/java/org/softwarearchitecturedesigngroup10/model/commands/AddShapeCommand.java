package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

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

//    @Override
//    public void undo() {
//    }
}


