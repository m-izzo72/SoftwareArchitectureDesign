package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class SendToBackCommand implements Command {
    private final CanvasModel receiver;

    public SendToBackCommand(CanvasModel receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.sendToBack();
    }
}
