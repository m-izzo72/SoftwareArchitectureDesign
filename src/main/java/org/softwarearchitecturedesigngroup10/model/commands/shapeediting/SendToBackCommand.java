package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.LinkedHashMap;

public class SendToBackCommand implements Command {
    private final CanvasModel receiver;
    private final LinkedHashMap<String, ShapeData> previousState;

    public SendToBackCommand(CanvasModel receiver) {
        this.receiver = receiver;
        this.previousState = new LinkedHashMap<>();
        getPreviousState();
    }

    private void getPreviousState() {
        receiver.getShapes().forEach((key, value) -> previousState.put(key, value.clone()));
    }

    @Override
    public void execute() {
        receiver.sendToBack();
    }



    @Override
    public void undo() {
        receiver.clear();
        previousState.forEach(receiver::addShapeByKeepingKeys);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
