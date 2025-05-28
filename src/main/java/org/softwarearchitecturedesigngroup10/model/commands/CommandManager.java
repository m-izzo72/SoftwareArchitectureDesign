package org.softwarearchitecturedesigngroup10.model.commands;

import java.util.Stack;

public class CommandManager {
    private final Stack<Command> undoStack;

    public CommandManager() {
        this.undoStack = new Stack<>();
    }

    public void executeCommand(Command command) {
        if(command.isUndoable()) undoStack.push(command);
        command.execute();
        System.out.println("Command executed. Current stack: " + undoStack.size());
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            System.out.println("Command undid. Current stack: " + undoStack.size());
        }
    }

    public boolean isUndoStackEmpty() {
        return undoStack.isEmpty();
    }

    public void clear() { undoStack.clear(); }
}
