package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;

public class DeselectAllShapeCommand implements Command {

    private final CanvasModel receiver;

    public DeselectAllShapeCommand(CanvasModel canvas) {
        this.receiver = canvas;
    }

    @Override
    public void execute() {
        receiver.deselectAllShapes();
    }

//    @Override
//    public void undo() {
//
//    }
}
