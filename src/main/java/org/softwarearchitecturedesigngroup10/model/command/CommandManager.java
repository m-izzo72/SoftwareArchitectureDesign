package org.softwarearchitecturedesigngroup10.model.command;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack;

    public CommandManager() {
        this.undoStack = new Stack<>();
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
        }
    }
}
