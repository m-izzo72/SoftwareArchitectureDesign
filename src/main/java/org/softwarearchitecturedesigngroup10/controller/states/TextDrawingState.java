package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.commands.AddShapeCommand;
import org.softwarearchitecturedesigngroup10.model.factories.ShapeDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.TextDataFactory;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.TextData;

public class TextDrawingState implements State {
    private TextField activeTextField;
    private double textStartX, textStartY;

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        if (activeTextField != null) {
            finalizeTextInput(context, true);
        }

        textStartX = event.getX();
        textStartY = event.getY();

        activeTextField = new TextField();
        activeTextField.setLayoutX(textStartX);
        activeTextField.setLayoutY(textStartY);

        activeTextField.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-padding: 3;");
        activeTextField.setPromptText("Write text...");

        activeTextField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                finalizeTextInput(context, false);
                keyEvent.consume();
            } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                cancelTextInput(context);
                keyEvent.consume();
            }
        });

        activeTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && activeTextField != null) {
                finalizeTextInput(context, false);
            }
        });

        Pane canvasPane = context.getCanvas();
        if (canvasPane != null) {
            canvasPane.getChildren().add(activeTextField);
            activeTextField.requestFocus();
        } else {
            activeTextField = null;
        }
        event.consume();
    }

    private void finalizeTextInput(Controller context, boolean dueToNewMousePress) {
        if (activeTextField == null) return;

        String text = activeTextField.getText();
        Pane canvasPane = context.getCanvas();
        if (canvasPane != null) {
            canvasPane.getChildren().remove(activeTextField);
        }

        TextField tempTextField = activeTextField;
        activeTextField = null;

        if (text != null && !text.trim().isEmpty()) {
            ShapeDataFactory factoryGeneric = context.getFactory();
            TextDataFactory textFactory;
            if (factoryGeneric instanceof TextDataFactory) {
                textFactory = (TextDataFactory) factoryGeneric;
            } else {
                textFactory = new TextDataFactory();
            }

            String currentFontFamily = "System";
            double currentFontSize = 16;

            double baselineY = textStartY + currentFontSize;

            ShapeData textData = textFactory.createShapeData(
                    textStartX,
                    baselineY,
                    text,
                    currentFontFamily,
                    currentFontSize,
                    context.getFillColorPicker().getValue().toString(),
                    context.getStrokeColorPicker().getValue().toString(),
                    context.getStrokeSlider().getValue(),
                    0
            );
            context.getCommandManager().executeCommand(new AddShapeCommand(context.getCanvasModel(), textData));
        }

        if (!dueToNewMousePress) {
            context.setCurrentState(context.getIdleState()); // O SelectionState
        }
    }

    private void cancelTextInput(Controller context) {
        if (activeTextField == null) return;
        Pane canvasPane = context.getCanvas();
        if (canvasPane != null) {
            canvasPane.getChildren().remove(activeTextField);
        }
        activeTextField = null;
        context.setCurrentState(context.getIdleState());
    }


    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        event.consume();
    }

    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        if (context.getCanvas() != null) {
            context.getCanvas().setCursor(Cursor.TEXT);
        }

        if (!(context.getFactory() instanceof TextDataFactory)) {
            context.setFactory(new TextDataFactory());
        }

        if (context.getTextButton() != null) {
            context.getTextButton().setSelected(true);
        }
    }

    @Override
    public void exitState(Controller context) {
        if (activeTextField != null) {
            Pane canvasPane = context.getCanvas();
            if (canvasPane != null) {
                canvasPane.getChildren().remove(activeTextField);
            }
            activeTextField = null;
        }
        if (context.getCanvas() != null) {
            context.getCanvas().setCursor(Cursor.DEFAULT);
        }
        if (context.getTextButton() != null) {
            context.getTextButton().setSelected(false);
        }
    }
}