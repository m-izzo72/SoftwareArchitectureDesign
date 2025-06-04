package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.commands.AddShapeCommand;
import org.softwarearchitecturedesigngroup10.model.factories.EllipseDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.LineDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.RectangleDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.ShapeDataFactory;

import java.util.ArrayList;
import java.util.Objects;

public class RegularDrawingState implements State {

    Image cursorImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cursor.png")));
    ImageCursor drawingCursor = new ImageCursor(cursorImage, 0, 0);

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        context.setStartX(event.getX());
        context.setStartY(event.getY());

        if(context.getCanvasView().isPreviewShapeNotNull()) {
            context.getCanvasView().deletePreview();

        }

        ShapeDataFactory factory = context.getFactory();
        if (factory instanceof LineDataFactory) {
            context.getCanvasView().setPreviewShape(new Line());
        } else if (factory instanceof RectangleDataFactory) {
            context.getCanvasView().setPreviewShape(new Rectangle());
        } else if (factory instanceof EllipseDataFactory) {
            context.getCanvasView().setPreviewShape(new Ellipse());
        }


        if (context.getCanvasView().isPreviewShapeNotNull()) {
            context.getCanvasView().stylePreviewShape();
            context.getCanvasView().drawPreview();
        }

        event.consume();
    }

    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        if (context.getCanvasView().isPreviewShapeNotNull()) {
            context.getCanvasView().deletePreview();
            context.getCanvasView().setPreviewShape(null);
        }

        ArrayList<Double> points = new ArrayList<>();
        points.add(context.getStartX());
        points.add(context.getStartY());
        points.add(event.getX());
        points.add(event.getY());

        context.getCommandManager().executeCommand(
                new AddShapeCommand(
                        context.getCanvasModel(),
                        context.getFactory().createShapeData(
                                points,
                                context.getFillColorPicker().getValue().toString(),
                                context.getStrokeColorPicker().getValue().toString(),
                                context.getStrokeSlider().getValue(),
                                0)
                )
        );

        event.consume();
    }

    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {

        if (context.getCanvasView().isPreviewShapeNotNull()) {
            context.getCanvasView().updatePreviewShapeGeometry(
                    context.getStartX(), context.getStartY(),
                    event.getX(), event.getY()
            );

        }
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
