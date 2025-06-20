package org.softwarearchitecturedesigngroup10.model;

import org.softwarearchitecturedesigngroup10.model.commands.clipboard.ShapesClipboard;
import org.softwarearchitecturedesigngroup10.model.filesmanager.FileManager;
import org.softwarearchitecturedesigngroup10.model.shapesdata.*;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;


import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CanvasModel implements CanvasModelInterface {
    LinkedHashMap<String, ShapeData> shapes;
    LinkedHashMap<String, ShapeData> selectedShapes;
    private final ShapesClipboard shapesClipboard;
    FileManager fileManager;
    ArrayList<ModelObserver> observers;


    public CanvasModel() {
        observers = new ArrayList<>();
        shapes = new LinkedHashMap<>();
        selectedShapes = new LinkedHashMap<>();
        fileManager = new FileManager();
        shapesClipboard = new ShapesClipboard();

    }

    public void clear() {
        shapes.clear();
        notifyObservers();
    }

    public void bringToFront() {
        LinkedHashMap<String, ShapeData> selectedShapes = getSelectedShapes();
        selectedShapes.forEach( (key, value) -> {
            System.out.println("Bringing to front (selected shape): " + key);
            System.out.println("Bringing to front (shape): " + shapes.containsKey(key) + key);
            shapes.remove(key); });
        shapes.putAll(selectedShapes);
        System.out.println("Bringing to front (new shapes): " + shapes.keySet());
        notifyObservers();
    }

    public void sendToBack() {
        LinkedHashMap<String, ShapeData> selectedShapes = getSelectedShapes();
        selectedShapes.forEach( (key, value) -> shapes.remove(key));
        selectedShapes.putAll(shapes);
        shapes = selectedShapes;
        notifyObservers();
    }

    public void addShape(ShapeData shapeData) {
        shapes.put(UUID.randomUUID().toString(), shapeData);
        notifyObservers();
    }

    public void addShapeByKeepingKeys(String key, ShapeData shapeData) {
        shapes.put(key, shapeData);
        notifyObservers();
    }

    public void toggleShapeSelection(String shapeId) {
        shapes.get(shapeId).setSelected(!shapes.get(shapeId).isSelected());
        if (shapes.get(shapeId).isSelected()) selectedShapes.put(shapeId, shapes.get(shapeId));
        else selectedShapes.remove(shapeId);
        notifyObservers();
    }

    public void moveSelectedShapes(double dx, double dy) {
        AtomicBoolean moved = new AtomicBoolean(false);
        getSelectedShapes().forEach((key, value) -> {
            moveShapeData(key, dx, dy);
            moved.set(true);
        });

        if (moved.get()) {
            notifyObservers();
        }
    }

    private void moveShapeData(String shapeID, double dx, double dy) {
        if (shapes.get(shapeID) instanceof LineData ld) {
            ld.setX(ld.getX() + dx);
            ld.setY(ld.getY() + dy);
            ld.setEndX(ld.getEndX() + dx);
            ld.setEndY(ld.getEndY() + dy);
        } else if (shapes.get(shapeID) instanceof RectangleData rd) {
            rd.setX(rd.getX() + dx);
            rd.setY(rd.getY() + dy);
        } else if (shapes.get(shapeID) instanceof EllipseData ed) {
            ed.setCenterX(ed.getCenterX() + dx);
            ed.setCenterY(ed.getCenterY() + dy);
            ed.setX(ed.getCenterX() - ed.getRadiusX());
            ed.setY(ed.getCenterY() - ed.getRadiusY());
        } else if (shapes.get(shapeID) instanceof PolygonData pd) {
            pd.setX(pd.getX() + dx);
            pd.setY(pd.getY() + dy);
        } else if (shapes.get(shapeID) instanceof TextData td) {
            td.setX(td.getX() + dx);
            td.setY(td.getY() + dy);
        } else if (shapes.get(shapeID) instanceof GroupedShapesData gd) {
            gd.setX(gd.getX() + dx);
            gd.setY(gd.getY() + dy);
        }
    }

    public void moveShapeDataByIDs(ArrayList<String> shapes, double dx, double dy) {
        shapes.forEach(id -> {
            moveShapeData(id, dx, dy);
            notifyObservers();
        });
    }

    public void deselectAllShapes() {
        shapes.forEach((key, value) -> value.setSelected(false));
        selectedShapes.clear();
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
        notifyObservers();
    }

    public void cutShapes() {
        shapesClipboard.copyToClipboard(getSelectedShapes());
        deleteShapes();
    }

    public void pasteShapes() {
        deselectAllShapes();
        shapesClipboard.getClipboard().forEach(shapeData -> {
            String key = UUID.randomUUID().toString();
            addShapeByKeepingKeys(key, shapeData);
            double offset = shapesClipboard.getPasteOffset();
            moveShapeData(key, offset, offset); // Changes paste position so shapes aren't pasted onto the original ones
            deselectAllShapes();
        });

    }

//    public ArrayList<ShapeData> getShapesClipboard() {
//        return shapesClipboard.getActualShapesClipboard();
//    }

    /* SELECTED SHAPES */

    public void resizeShape(String shapeId, double newWidth, double newHeight) {
        ShapeData shape = shapes.get(shapeId);
        if (shape != null) {
            System.out.println("CanvasModel: Resizing " + shapeId + " to W:" + newWidth + " H:" + newHeight);
            shape.resize(newWidth, newHeight);
            notifyObservers();
        } else {
            System.out.println("CanvasModel: Shape " + shapeId + " not found for resize.");
        }
    }

    public LinkedHashMap<String, ShapeData> getSelectedShapes() {
        return new LinkedHashMap<String, ShapeData>(shapes.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public LinkedHashMap<String, ShapeData> getOrderedSelectedShapes() {
        return selectedShapes;
    }

    public void editShapesFillColour(String newFillColour) {
        shapes.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSelected())
                .forEach(entry -> {
                    entry.getValue().setFillColor(newFillColour);
                    //entry.getValue().setStrokeColor(newStrokeColour);
                });
        notifyObservers();
    }

    public void editShapesStrokeColour(String newStrokeColour) {
        shapes.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSelected())
                .forEach(entry -> {
                    //entry.getValue().setFillColor(newFillColour);
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

    public void rotateShape(double angle) {
        getSelectedShapes().forEach((key, value) -> {
            value.setRotationAngle(angle);
            System.out.println("CanvasModel: Rotating " + key + " to angle " + angle);
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

    public void yFlip() {
        getSelectedShapes().forEach((key, value) -> value.setYFlipped());
        notifyObservers();
    }

    public void xFlip() {
        getSelectedShapes().forEach((key, value) -> value.setXFlipped());
        notifyObservers();
    }

    public void groupSelectedShapes() {
        // Group only if at least 2 shapes are selected
        LinkedHashMap<String, ShapeData> selectedShapes = getSelectedShapes();
        if (selectedShapes.size() < 2) { return; }

        GroupedShapesData groupedShapesData = new GroupedShapesData();
        //List<String> shapesIDsToRemove = new ArrayList<>();

        selectedShapes.forEach((key, selectedShape) -> {
            groupedShapesData.add(selectedShape);
            //shapesIDsToRemove.add(key);
            deleteShape(key);
        });

        deselectAllShapes();
        groupedShapesData.setSelected(true);
        addShape(groupedShapesData);
        deselectAllShapes();

        //notifyObservers();
    }

    public void ungroupSelectedShapes() {
        LinkedHashMap<String, ShapeData> selectedShapes = getSelectedShapes();
        ArrayList<String> groupIDsToUngroup = new ArrayList<>();
        selectedShapes.entrySet()
                .stream()
                .filter( entry -> entry.getValue() instanceof GroupedShapesData)
                .forEach(entry -> groupIDsToUngroup.add(entry.getKey()));

        if(groupIDsToUngroup.isEmpty()) {
            return;
        }

        groupIDsToUngroup.forEach(groupID -> {
            ShapeData data = shapes.get(groupID);
            if(data instanceof GroupedShapesData group) {
                group.getChildren().forEach(child -> {
                    child.setSelected(true);
                    addShape(child);
                });
                deleteShape(groupID);
            }
        });

        notifyObservers();
    }
}
