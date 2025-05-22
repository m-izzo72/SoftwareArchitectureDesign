package org.softwarearchitecturedesigngroup10.model;

import org.softwarearchitecturedesigngroup10.model.filesmanager.FileManager;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CanvasModel implements CanvasModelInterface {
    LinkedHashMap<String, ShapeData> shapes;
    FileManager fileManager;
    ArrayList<ModelObserver> observers;

    public CanvasModel() {
        observers = new ArrayList<>();
        shapes = new LinkedHashMap<>();
        fileManager = new FileManager();
    }

    public void clear() {
        shapes.clear();
        notifyObservers();
    }

    public void addShape(ShapeData shapeData) {
        shapes.put(UUID.randomUUID().toString(), shapeData);
        notifyObservers();
    }

    public void selectShape(String shapeId) {
        shapes.get(shapeId).setSelected(!shapes.get(shapeId).isSelected());
    }

    public void deselectAllShapes() {
        shapes.forEach((key, value) -> value.setSelected(false));
    }

    public void deleteShapes() {
        shapes.entrySet().removeIf(entry -> entry.getValue().isSelected());
        notifyObservers();
    }

    public HashMap<String, ShapeData> getSelectedShapes() {
        return new HashMap<String, ShapeData>(shapes.entrySet().stream()
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
