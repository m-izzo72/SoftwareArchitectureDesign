package org.softwarearchitecturedesigngroup10.model.commands.clipboard;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.LinkedHashMap;
import java.util.UUID;

public class DeleteShapeCommand implements Command {
    private final CanvasModel receiver;
    private final LinkedHashMap<String, ShapeData> previousState;

    public DeleteShapeCommand(CanvasModel receiver) {
        this.receiver = receiver;
        this.previousState = new LinkedHashMap<>();
        getPreviousState();
    }

    private void getPreviousState() {
        receiver.getShapes().forEach((key, value) -> previousState.put(key, value.clone()));
    }

    @Override
    public void execute() {
        receiver.deleteShapes();
    }

    @Override
    public void undo() {
        receiver.clear();
        previousState.forEach((key, value) -> receiver.addShape(value));
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
