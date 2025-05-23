package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;

public class BringToFrontCommand implements Command {
    private final CanvasModel receiver;

    public BringToFrontCommand(CanvasModel receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.bringToFront();
    }
}
