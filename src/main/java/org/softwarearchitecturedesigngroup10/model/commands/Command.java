package org.softwarearchitecturedesigngroup10.model.commands;

public interface Command {
    void execute();

    void undo();

    boolean isUndoable();
}
