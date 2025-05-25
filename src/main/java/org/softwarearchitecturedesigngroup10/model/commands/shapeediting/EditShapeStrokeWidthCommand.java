package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class EditShapeStrokeWidthCommand implements Command {

    private final CanvasModel receiver;
    private final double newStrokeWidth;

    public EditShapeStrokeWidthCommand(CanvasModel receiver, double newStrokeWidth) {
        this.receiver = receiver;
        this.newStrokeWidth = newStrokeWidth;
    }


    @Override
    public void execute() {
        receiver.editShapesStrokeWidth(newStrokeWidth);
    }

    @Override
    public void undo() {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
