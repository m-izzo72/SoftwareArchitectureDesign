package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class ResizeShapeCommand implements Command {

    private final CanvasModel receiver;
    private final String shapeId;
    private final double oldWidth;
    private final double oldHeight;
    private final double newWidth;
    private final double newHeight;

    public ResizeShapeCommand(CanvasModel receiver, String shapeId, double oldWidth, double oldHeight, double newWidth, double newHeight) {
        this.receiver = receiver;
        this.shapeId = shapeId;
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public void execute() {
        receiver.resizeShape(shapeId, newWidth, newHeight);
    }

    @Override
    public void undo() {
        receiver.resizeShape(shapeId, oldWidth, oldHeight);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
