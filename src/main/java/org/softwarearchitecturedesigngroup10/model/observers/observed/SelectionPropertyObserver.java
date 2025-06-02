package org.softwarearchitecturedesigngroup10.model.observers.observed;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.clipboard.ShapesClipboard;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.TextData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;
import org.softwarearchitecturedesigngroup10.view.CircularSlider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class SelectionPropertyObserver implements ModelObserver {

    CanvasModel canvasModel;
    ArrayList<Node> selectionBoundNodes;

    public SelectionPropertyObserver(CanvasModel canvasModel, Node ... node) {
        this.canvasModel = canvasModel;
        this.selectionBoundNodes = new ArrayList<>(List.of(node));
    }

    @Override
    public void update() {
        LinkedHashMap<String, ShapeData> shapes = canvasModel.getOrderedSelectedShapes();
        System.out.println(shapes.size());
        if(shapes.isEmpty()) {
            selectionBoundNodes.forEach(node -> node.setDisable(true));
        } else {

            /* LIST ELEMENTS:
                0 editFillColourIcon,   1 editFillColorPicker,
                2 editStrokeColourIcon, 3 editStrokeColorPicker,
                4 copyShapeButton,      5 eraseShapeButton,
                6 cutShapeButton,       7 pasteShapeButton,
                8 sendToBackButton,     9 bringToFrontButton,
                10 editStrokeWidthIcon, 11 editStrokeWidthSlider,
                12 flipXButton,         13 flipYButton,
                14 rotationSlider,
                15 groupButton,         16 ungroupButton
             */

            selectionBoundNodes.forEach(node -> node.setDisable(false));

            if(shapes.size() < 2) selectionBoundNodes.get(15).setDisable(true);
            if(!(shapes.size() == 1 && shapes.values().stream().anyMatch(data -> data instanceof GroupedShapesData))) selectionBoundNodes.get(16).setDisable(true);

            if(shapes.values().stream().anyMatch(data -> data instanceof LineData || (data instanceof GroupedShapesData gd && gd.getChildren().stream().anyMatch(shape -> shape instanceof LineData))) ) {
                selectionBoundNodes.get(0).setDisable(true);
                selectionBoundNodes.get(1).setDisable(true);
            }
            if(shapes.values().stream().anyMatch(data -> data instanceof TextData || (data instanceof GroupedShapesData gd && gd.getChildren().stream().anyMatch(shape -> shape instanceof TextData))) ) {
                selectionBoundNodes.get(2).setDisable(true);
                selectionBoundNodes.get(3).setDisable(true);
                selectionBoundNodes.get(10).setDisable(true);
                selectionBoundNodes.get(11).setDisable(true);
            }

            ShapeData lastSelectedShape = Objects.requireNonNull(shapes.entrySet().stream().skip(shapes.size() - 1).findFirst().orElse(null)).getValue();
            System.out.println("\t\tLast selected shape: " + lastSelectedShape +"\n\t\t\t" + lastSelectedShape.getFillColor());

            //assert lastSelectedShape != null;
            if(lastSelectedShape.getFillColor() != null)
                ((ColorPicker) selectionBoundNodes.get(1)).setValue(Color.valueOf(lastSelectedShape.getFillColor()));
            if(lastSelectedShape.getStrokeColor() != null)
                ((ColorPicker) selectionBoundNodes.get(3)).setValue(Color.valueOf(lastSelectedShape.getStrokeColor()));
            if(lastSelectedShape.getStrokeWidth() != 0)
                ((Slider) selectionBoundNodes.get(11)).setValue(lastSelectedShape.getStrokeWidth());
            ((CircularSlider) selectionBoundNodes.get(14)).setAngle(lastSelectedShape.getRotationAngle());

        }

        if(ShapesClipboard.clipboard.isEmpty()) selectionBoundNodes.get(7).setDisable(true); else selectionBoundNodes.get(7).setDisable(false);
        //selectionBoundNodes.get(7).setDisable(canvasModel.getShapesClipboard().isEmpty());
    }
}
