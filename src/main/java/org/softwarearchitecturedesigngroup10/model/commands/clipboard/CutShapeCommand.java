package org.softwarearchitecturedesigngroup10.model.commands.clipboard;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class CutShapeCommand implements Command {
    private final CanvasModel receiver;


    public CutShapeCommand(CanvasModel receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.cutShapes();
    }

//    @Override
//    public void undo() {
//    }
}