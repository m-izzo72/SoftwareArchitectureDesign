package org.softwarearchitecturedesigngroup10.model.commands.clipboard;

import javafx.scene.input.Clipboard;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class ShapesClipboard {
    //private static Clipboard clipboard;
    private ArrayList<ShapeData> clipboard;
    double offset;
    //private final CanvasModel canvasModel;

    public ShapesClipboard(CanvasModel canvasModel) {
        this.clipboard = new ArrayList<>();

        //this.canvasModel = canvasModel;
    }

    public void copyToClipboard(HashMap<String, ShapeData> shapesData) {
        clipboard.clear(); offset = 50;
        shapesData.forEach(
                (key, value) -> {
                    clipboard.add(value.clone()); // Adds a cloned shapeData
                });
        System.out.println("Clipboard: " + clipboard);
    }

    public ArrayList<ShapeData> getClipboard() {
        // Clones the clipboard, otherwise a shape with the same reference will be added (resulting in bugs)
        ArrayList<ShapeData> clonedClipboard = new ArrayList<>();
        clipboard.forEach(entry -> clonedClipboard.add(entry.clone()));
        return clonedClipboard;
    }

    public ArrayList<ShapeData> getActualShapesClipboard() {
        // Clones the clipboard, otherwise a shape with the same reference will be added (resulting in bugs)
        // ArrayList<ShapeData> clonedClipboard = new ArrayList<>();
        // clipboard.forEach(entry -> clonedClipboard.add(entry.clone()));
        return clipboard;
    }


}
