package org.softwarearchitecturedesigngroup10.model.commands.clipboard;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class CopyShapeCommand implements Command {
    private final CanvasModel receiver;


    public CopyShapeCommand(CanvasModel receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.copyShapes();
    }

    @Override
    public void undo() {
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
