package org.softwarearchitecturedesigngroup10.model;

import org.softwarearchitecturedesigngroup10.model.shapes.Shape;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas {

    private List<Shape> shapes;

    public Canvas() {
        shapes = new ArrayList<>();
    }

    public void addShape(Shape s) {
        if (s != null) {
            shapes.add(s);
        }
    }

    public void deleteShape(Shape s) {
        shapes.remove(s);
    }

    public void save(File f) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(shapes);
            System.out.println("Saved Successfully.");
        } catch (IOException e) {
            System.err.println("Error during the saving " + e.getMessage());
        }
    }

    public void load(File f) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                shapes = (List<Shape>) obj;
                System.out.println("Canvas loaded successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during the loading" + e.getMessage());
        }
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
}
