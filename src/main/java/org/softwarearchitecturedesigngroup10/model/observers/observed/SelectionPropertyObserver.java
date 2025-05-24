package org.softwarearchitecturedesigngroup10.model.observers.observed;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;

import java.util.ArrayList;
import java.util.Objects;

public class SelectionPropertyObserver implements ModelObserver {

    CanvasModel canvasModel;
    ArrayList<Node> selectionBoundNodes;

    public SelectionPropertyObserver(CanvasModel canvasModel, ArrayList<Node> selectionBoundNodes) {
        this.canvasModel = canvasModel;
        this.selectionBoundNodes = selectionBoundNodes;
    }

    @Override
    public void update() {
        if(canvasModel.getSelectedShapes().isEmpty()) {
            selectionBoundNodes.forEach(node -> node.setDisable(true));
        } else if(!canvasModel.getSelectedShapes().isEmpty()) {
            selectionBoundNodes.forEach(node -> node.setDisable(false));
            ((ColorPicker) selectionBoundNodes.get(1)).setValue(Color.valueOf(Objects.requireNonNull(canvasModel.getSelectedShapes().entrySet().stream().skip(canvasModel.getSelectedShapes().size() - 1).findFirst().orElse(null)).getValue().getFillColor()));
            ((ColorPicker) selectionBoundNodes.get(3)).setValue(Color.valueOf(Objects.requireNonNull(canvasModel.getSelectedShapes().entrySet().stream().skip(canvasModel.getSelectedShapes().size() - 1).findFirst().orElse(null)).getValue().getStrokeColor()));
        }
    }
}
