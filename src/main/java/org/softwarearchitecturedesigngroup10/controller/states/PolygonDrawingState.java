package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.commands.AddShapeCommand;
import org.softwarearchitecturedesigngroup10.model.factories.EllipseDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.LineDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.RectangleDataFactory;
import org.softwarearchitecturedesigngroup10.model.factories.ShapeDataFactory;


import java.util.ArrayList;
import java.util.Objects;

public class PolygonDrawingState implements State {

    Image cursorImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cursor.png")));
    ImageCursor drawingCursor = new ImageCursor(cursorImage, 0, 0);
    ArrayList<Double> points;

    private Button confirmPolygonButton;
    private final Pane polygonAlert;
    private Controller context;

    public Pane createPolygonAlertPane() {
        Pane polygonAlertPane = new Pane();
        polygonAlertPane.setId("polygonAlert");
        polygonAlertPane.setLayoutY(15);
        polygonAlertPane.setLayoutX(268);

        polygonAlertPane.setMaxHeight(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setMinHeight(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setMinWidth(Double.NEGATIVE_INFINITY);
        polygonAlertPane.setPrefHeight(85.0);
        polygonAlertPane.setPrefWidth(360.0);


        Text instructionText = new Text();
//        instructionText.setFill(Color.getColor(w));
        instructionText.setLayoutX(7.0);
        instructionText.setLayoutY(18.0);
        instructionText.setFill(Color.valueOf("#797b86"));

        instructionText.setStrokeType(StrokeType.OUTSIDE);
        instructionText.setStrokeWidth(0.0);
        instructionText.setText("To draw a custom polygon, press the mouse button on the canvas to save the vertices. You may have save up to 12 vertices. Once you're done click Confirm to draw a polygon.");
        instructionText.setWrappingWidth(344);

        confirmPolygonButton = new Button("Draw");
        confirmPolygonButton.setTextFill(Color.valueOf("#797b86"));
        confirmPolygonButton.setId("confirmPolygon");
        confirmPolygonButton.setLayoutX(300);
        confirmPolygonButton.setLayoutY(55);
        confirmPolygonButton.setMnemonicParsing(false);

        polygonAlertPane.getChildren().addAll(instructionText, confirmPolygonButton);

        return polygonAlertPane;
    }


    public PolygonDrawingState() {
        points = new ArrayList<>();
        polygonAlert = createPolygonAlertPane();
        confirmPolygonButton.setOnAction(this::confirmPolygon);

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
        context.getHelperStackPane().getChildren().add(polygonAlert);
        this.context = context;


    }

    @Override
    public void exitState(Controller context) {
        context.getCanvas().setCursor(Cursor.DEFAULT);
        context.getHelperStackPane().getChildren().remove(polygonAlert);
        points.clear();
        context.getCanvasView().erasePolygonVerticesPreview();

    }
}
