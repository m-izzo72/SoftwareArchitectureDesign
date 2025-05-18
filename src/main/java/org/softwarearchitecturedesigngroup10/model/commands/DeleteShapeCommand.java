package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;

public class DeleteShapeCommand implements Command {
    private final CanvasModel receiver;


    public DeleteShapeCommand(CanvasModel receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.deleteShapes();
    }

//    @Override
//    public void undo() {
//    }
}
