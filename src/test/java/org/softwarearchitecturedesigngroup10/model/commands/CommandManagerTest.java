package org.softwarearchitecturedesigngroup10.model.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandManagerTest {
    private CommandManager commandManager;
    private static class DummyCommand implements Command {
        boolean executed = false;
        @Override
        public void execute() {
            executed = true;
        }
        public boolean isExecuted() {
            return executed;
        }
    }

    @BeforeEach
    void setup() {
        commandManager = new CommandManager();
    }
    @Test
    void testExecuteCommand() {
        DummyCommand command = new DummyCommand();
        assertFalse(command.isExecuted(), "Command should not be executed before calling executeCommand");

        commandManager.executeCommand(command);
        assertTrue(command.isExecuted(), "Command should be executed after calling executeCommand");
    }
    }

