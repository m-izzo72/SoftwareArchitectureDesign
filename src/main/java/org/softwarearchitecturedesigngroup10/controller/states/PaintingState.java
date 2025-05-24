package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.commands.AddShapeCommand;

import java.util.Objects;

public class PaintingState implements State {

    Image cursorImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cursor.png")));

    ImageCursor drawingCursor = new ImageCursor(cursorImage, 0, 0);


    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        context.setStartX(event.getX());
        context.setStartY(event.getY());

        event.consume();
    }

    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        context.getCommandManager().executeCommand(
                new AddShapeCommand(
                        context.getCanvasModel(),
                        context.getFactory().createShapeData(
                                context.getStartX(), context.getStartY(),
                                event.getX(), event.getY(),
                                context.getFillColorPicker().getValue().toString(),
                                context.getStrokeColorPicker().getValue().toString(),
                                context.getStrokeSlider().getValue(),
                                0)
                )
        );
    }

    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        context.getSelectToolButton().setSelected(false);
        context.getCanvas().setCursor(drawingCursor);
    }

    @Override
    public void exitState(Controller context) {
        context.getCanvas().setCursor(Cursor.DEFAULT);

    }
}
