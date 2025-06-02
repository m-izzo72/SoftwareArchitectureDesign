package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.commands.AddShapeCommand;


import java.util.ArrayList;
import java.util.Objects;

public class PolygonDrawingState implements State {

    Image cursorImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cursor.png")));
    ImageCursor drawingCursor = new ImageCursor(cursorImage, 0, 0);
    ArrayList<Double> points;

    private final Button confirmPolygonButton;
    private final Pane polygonAlert;
    private Controller context;


    public PolygonDrawingState(Controller context) {
        this.context = context;
        points = new ArrayList<>();
        confirmPolygonButton = new Button("Confirm");
        confirmPolygonButton.setOnAction(this::confirmPolygon);
        polygonAlert = context.getCanvasView().createPolygonAlertPane(confirmPolygonButton);
    }

    public void confirmPolygon(Event event) {
        ArrayList<Double> vertices = new ArrayList<>(points);
        if (points.size() > 2) {
            context.getCommandManager().executeCommand(
                    new AddShapeCommand(
                            context.getCanvasModel(),
                            context.getFactory().createShapeData(
                                    vertices,
                                    context.getFillColorPicker().getValue().toString(),
                                    context.getStrokeColorPicker().getValue().toString(),
                                    context.getStrokeSlider().getValue(),
                                    0)
                    )
            );
        }

        points.clear();
    }

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        if (points.size() < 23) {
            points.add(event.getX());
            points.add(event.getY());
            context.getCanvasView().setPolygonVerticesPreview(event.getX(), event.getY());
            event.consume();
            System.out.println(points);
        }

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
        context.getSelectToolButton().setSelected(false);
        context.getCanvas().setCursor(drawingCursor);
        context.getCanvasView().insertPolygonAlertPane(polygonAlert);
        //context.getHelperStackPane().getChildren().add(polygonAlert);
        //this.context = context;
    }

    @Override
    public void exitState(Controller context) {
        context.getCanvas().setCursor(Cursor.DEFAULT);
        context.getCanvasView().removePolygonAlertPane(polygonAlert);
        //context.getHelperStackPane().getChildren().remove(polygonAlert);
        points.clear();
        context.getCanvasView().erasePolygonVerticesPreview();

    }
}
