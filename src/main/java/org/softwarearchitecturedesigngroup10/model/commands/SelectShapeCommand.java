package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;

public class SelectShapeCommand implements Command {

    private final CanvasModel receiver;
    private final String selectedShape;

    public SelectShapeCommand(CanvasModel canvasModel, String shapeSelected) {
        this.receiver = canvasModel;
        this.selectedShape = shapeSelected;
    }

    @Override
    public void execute() {
        receiver.selectShape(this.selectedShape);
    }

//    @Override
//    public void undo() {
//
//    }
}
