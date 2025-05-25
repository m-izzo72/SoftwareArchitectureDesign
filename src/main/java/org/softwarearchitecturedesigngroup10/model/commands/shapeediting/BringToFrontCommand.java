package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class BringToFrontCommand implements Command {
    private final CanvasModel receiver;

    public BringToFrontCommand(CanvasModel receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.bringToFront();
    }

    @Override
    public void undo() {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
