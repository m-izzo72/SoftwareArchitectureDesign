package org.softwarearchitecturedesigngroup10.model.commands.clipboard;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.LinkedHashMap;

public class PasteShapeCommand implements Command {
    private final CanvasModel receiver;
    public final LinkedHashMap<String, ShapeData> previousState;


    public PasteShapeCommand(CanvasModel receiver) {
        this.receiver = receiver;
        this.previousState = new LinkedHashMap<>();
        getPreviousState();
    }

    private void getPreviousState() {
        receiver.getShapes().forEach((key, value) -> previousState.put(key, value.clone()));
    }

    @Override
    public void execute() {
        receiver.pasteShapes();
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
