package org.softwarearchitecturedesigngroup10.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.command.Command;
import org.softwarearchitecturedesigngroup10.model.command.CommandManager;
import org.softwarearchitecturedesigngroup10.model.command.PaintCommand;
import org.softwarearchitecturedesigngroup10.model.command.DeleteShapeCommand;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CanvasModel {
    private Pane canvas;
    //private ObservableList<Node> shapes;
    private CommandManager commandManager;

    public CanvasModel(Pane canvas) {
        this.canvas = canvas;
        //shapes = canvas.getChildren();
        commandManager = new CommandManager();
    }

    public void paint(Shape shape) {
        Command paintCommand = new PaintCommand(canvas, shape);
        commandManager.executeCommand(paintCommand);

    }

    public void deleteShape(Shape shape) {
        Command deleteCommand = new DeleteShapeCommand(canvas, shape);
        commandManager.executeCommand(deleteCommand);
    }

    public void undo() {

        commandManager.undo();
    }


    /*public void save() {

    }

    public void load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            // Pulisci il canvas e le forme esistenti
            canvas.getChildren().clear();
            //shapes.clear();

            List<ShapeData> shapesData = (List<ShapeData>) in.readObject();
            for (ShapeData shapeData : shapesData) {
                Shape shape = convertFromShapeData(shapeData);
                shapes.add(shape);
                canvas.getChildren().add(shape);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
