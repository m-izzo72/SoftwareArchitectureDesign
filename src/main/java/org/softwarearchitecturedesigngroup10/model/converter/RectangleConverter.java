package org.softwarearchitecturedesigngroup10.model.converter;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape; // Import the base Shape class
import javafx.scene.paint.Color; // Import Color specifically
import org.softwarearchitecturedesigngroup10.model.helper.ShapeData;

public class RectangleConverter implements ShapeConverter {
    private static final String TYPE = "rectangle";

    @Override
    public ShapeData convertToData(Shape shape) {
        if (!(shape instanceof Rectangle)) {
            throw new IllegalArgumentException("La forma non è un rettangolo");
        }

        Rectangle rectangle = (Rectangle) shape;
        ShapeData data = new ShapeData();
        data.setType(TYPE);

        // Conversione delle proprietà geometriche
        data.addProperty("x", rectangle.getX());
        data.addProperty("y", rectangle.getY());
        data.addProperty("width", rectangle.getWidth());
        data.addProperty("height", rectangle.getHeight());

        // Conversione delle proprietà stilistiche
        data.setFillColor(convertColorToString(rectangle.getFill() instanceof Color ? (Color) rectangle.getFill() : Color.TRANSPARENT));
        data.setStrokeColor(convertColorToString(rectangle.getStroke() instanceof Color ? (Color) rectangle.getStroke() : Color.BLACK));
        data.setStrokeWidth(rectangle.getStrokeWidth());

        return data;
    }

    @Override
    public Shape convertFromData(ShapeData shapeData) {
        if (!TYPE.equals(shapeData.getType())) {
            throw new IllegalArgumentException("Il tipo di dati non è compatibile con questo converter");
        }

        // Creazione di un nuovo rettangolo con le proprietà geometriche
        Rectangle rectangle = new Rectangle(
                shapeData.getProperty("x"),
                shapeData.getProperty("y"),
                shapeData.getProperty("width"),
                shapeData.getProperty("height")
        );

        // Impostazione delle proprietà stilistiche
        rectangle.setFill(convertStringToColor(shapeData.getFillColor()));
        rectangle.setStroke(convertStringToColor(shapeData.getStrokeColor()));
        rectangle.setStrokeWidth(shapeData.getStrokeWidth());

        return rectangle;
    }

    @Override
    public String getSupportedType() {
        return TYPE;
    }

    // Metodo di utilità per convertire Color in String
    private String convertColorToString(Color color) {
        return String.format("#%02X%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                (int) (color.getOpacity() * 255));
    }

    // Metodo di utilità per convertire String in Color
    private Color convertStringToColor(String colorString) {
        if (colorString == null || colorString.isEmpty()) {
            return Color.TRANSPARENT;
        }
        try {
            return Color.web(colorString);
        } catch (IllegalArgumentException e) {
            return Color.BLACK;
        }
    }
}
