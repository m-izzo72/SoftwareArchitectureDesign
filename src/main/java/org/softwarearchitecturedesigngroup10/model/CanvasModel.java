package org.softwarearchitecturedesigngroup10.model;

import org.softwarearchitecturedesigngroup10.model.commands.clipboard.ShapesClipboard;
import org.softwarearchitecturedesigngroup10.model.filesmanager.FileManager;
import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CanvasModel implements CanvasModelInterface {
    LinkedHashMap<String, ShapeData> shapes;
    private ShapesClipboard shapesClipboard;
    FileManager fileManager;
    ArrayList<ModelObserver> observers;

    public CanvasModel() {
        observers = new ArrayList<>();
        shapes = new LinkedHashMap<>();
        fileManager = new FileManager();
        shapesClipboard = new ShapesClipboard(this);
    }

    public void clear() {
        shapes.clear();
        notifyObservers();
    }

    public void bringToFront() {
        LinkedHashMap<String, ShapeData> selectedShapes = getSelectedShapes();
        selectedShapes.forEach( (key, value) -> { shapes.remove(key); });
        shapes.putAll(selectedShapes);
        notifyObservers();
    }

    public void sendToBack() {
        LinkedHashMap<String, ShapeData> selectedShapes = getSelectedShapes();
        selectedShapes.forEach( (key, value) -> { shapes.remove(key); });
        selectedShapes.putAll(shapes);
        shapes = selectedShapes;
        notifyObservers();
    }

    public void addShape(ShapeData shapeData) {
        shapes.put(UUID.randomUUID().toString(), shapeData);
        notifyObservers();
    }

    public void selectShape(String shapeId) {
        shapes.get(shapeId).setSelected(!shapes.get(shapeId).isSelected());
        notifyObservers();
    }

    public void moveSelectedShapes(double dx, double dy) {
        boolean moved = false;
        for (ShapeData shape : shapes.values()) {
            if (shape.isSelected()) {
                moveShapeData(shape, dx, dy);
                moved = true;
            }
        }

        if (moved) {
            notifyObservers();
        }
    }

    private void moveShapeData(ShapeData shapeData, double dx, double dy) {
        if (shapeData instanceof LineData ld) {
            ld.setX(ld.getX() + dx);
            ld.setY(ld.getY() + dy);
            ld.setEndX(ld.getEndX() + dx);
            ld.setEndY(ld.getEndY() + dy);
        } else if (shapeData instanceof RectangleData rd) {
            rd.setX(rd.getX() + dx);
            rd.setY(rd.getY() + dy);
        } else if (shapeData instanceof EllipseData ed) {
            ed.setCenterX(ed.getCenterX() + dx);
            ed.setCenterY(ed.getCenterY() + dy);
            ed.setX(ed.getCenterX() - ed.getRadiusX());
            ed.setY(ed.getCenterY() - ed.getRadiusY());
        } else {
            shapeData.setX(shapeData.getX() + dx);
            shapeData.setY(shapeData.getY() + dy);
        }
    }

    public void deselectAllShapes() {
        shapes.forEach((key, value) -> value.setSelected(false));
        notifyObservers();
    }

    /* CLIPBOARD */

    // Used to UNDO AddShapeCommand
    public void deleteShape(String shapeId) {
        shapes.remove(shapeId);
        notifyObservers();
    }

    public void deleteShapes() {
        shapes.entrySet().removeIf(entry -> entry.getValue().isSelected());
        notifyObservers();
    }

    public void copyShapes() {
        shapesClipboard.copyToClipboard(getSelectedShapes());
    }

    public void cutShapes() {
        shapesClipboard.copyToClipboard(getSelectedShapes());
        deleteShapes();
    }

    public void pasteShapes() {
        deselectAllShapes();
        shapesClipboard.getClipboard().forEach(shapeData -> {
            moveShapeData(shapeData, 50, 50); // Changes paste position so shapes aren't pasted onto the original ones
            addShape(shapeData);
        });

    }

    /* SELECTED SHAPES */

    public LinkedHashMap<String, ShapeData> getSelectedShapes() {
        return new LinkedHashMap<String, ShapeData>(shapes.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public void changeShapesColours(String newFillColour, String newStrokeColour) {
        shapes.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSelected())
                .forEach(entry -> {
                    entry.getValue().setFillColor(newFillColour);
                    entry.getValue().setStrokeColor(newStrokeColour);
                });
        notifyObservers();
    }

    public void editShapesStrokeWidth(double newStrokeWidth) {
        shapes.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSelected())
                .forEach(entry -> {
                    entry.getValue().setStrokeWidth(newStrokeWidth);
                });
        notifyObservers();
    }

    public LinkedHashMap<String, ShapeData> getShapes() {
        return shapes;
    }

    public void addObserver(ModelObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void notifyObservers() {
        if (observers.isEmpty()) {
            return;
        }
        List<ModelObserver> observersCopy = new ArrayList<>(observers);
        for (ModelObserver observer : observersCopy) {
            observer.update();
        }
    }

    public void save(File file) throws IOException {
        fileManager.save(this.shapes, file);
    }

    public void load(File file) throws IOException, ClassNotFoundException {
        this.shapes = fileManager.load(file);
        notifyObservers();
    }

}
