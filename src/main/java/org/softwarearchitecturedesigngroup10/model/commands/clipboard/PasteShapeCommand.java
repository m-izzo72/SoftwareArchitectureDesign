package org.softwarearchitecturedesigngroup10.model.commands.clipboard;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class PasteShapeCommand implements Command {
    private final CanvasModel receiver;


    public PasteShapeCommand(CanvasModel receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.pasteShapes();
    }

//    @Override
//    public void undo() {
//    }
}
