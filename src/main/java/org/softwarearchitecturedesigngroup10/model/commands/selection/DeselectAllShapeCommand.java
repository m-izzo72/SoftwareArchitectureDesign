package org.softwarearchitecturedesigngroup10.model.commands.selection;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

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
