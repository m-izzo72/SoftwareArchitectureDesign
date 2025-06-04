package org.softwarearchitecturedesigngroup10.model.commands.clipboard;

import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.HashMap;

public class ShapesClipboard {
    //private static Clipboard clipboard;
    public static ArrayList<ShapeData> clipboard;
    double offset;

    public ShapesClipboard() {
        clipboard = new ArrayList<>();
        this.offset = 20;
    }

    public void copyToClipboard(HashMap<String, ShapeData> shapesData) {
        clipboard.clear(); offset = 0;
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
        offset += 20;
        return clonedClipboard;
    }

//    public ArrayList<ShapeData> getActualShapesClipboard() {
//        // Clones the clipboard, otherwise a shape with the same reference will be added (resulting in bugs)
//        // ArrayList<ShapeData> clonedClipboard = new ArrayList<>();
//        // clipboard.forEach(entry -> clonedClipboard.add(entry.clone()));
//        return clipboard;
//    }

    public double getPasteOffset() {
        return offset;
    }


}
