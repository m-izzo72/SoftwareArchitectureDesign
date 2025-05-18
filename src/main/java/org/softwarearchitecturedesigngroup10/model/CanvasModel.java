package org.softwarearchitecturedesigngroup10.model;

import org.softwarearchitecturedesigngroup10.model.filesmanager.FileManager;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.observer.ModelObserver;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CanvasModel implements CanvasModelInterface {
    LinkedHashMap<String, ShapeData> shapes;
    FileManager fileManager;
    List<ModelObserver> observers;

    public CanvasModel() {
    }

    public void addShape(ShapeData shapeData) {

    }

    public void deleteShapes(HashMap<String, ShapeData> shapes) {

    }

    public LinkedHashMap<String, ShapeData> getShapes() {
        return shapes;
    }

    public void addObserver(ModelObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Model: Observer aggiunto: " + observer.getClass().getSimpleName());
        }

    }

    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
        System.out.println("Model: Observer rimosso: " + observer.getClass().getSimpleName());
    }

    public void notifyObservers() {
        if (observers.isEmpty()) {
            System.out.println("Model: Nessun observer da notificare.");
            return;
        }
        // Crea una copia della lista per evitare ConcurrentModificationException
        // se un observer prova a deregistrarsi durante la notifica.
        List<ModelObserver> observersCopy = new ArrayList<>(observers);
        System.out.println("Model: Notifico " + observersCopy.size() + " observer(s).");
        for (ModelObserver observer : observersCopy) {
            observer.update();
        }
    }

    public void save(File file) {

    }


    public void load(File file) {

    }


}
