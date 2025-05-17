package org.softwarearchitecturedesigngroup10.model.converter;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.helper.ShapeData;

public class EllipseConverter implements ShapeConverter{
    private static final String TYPE = "ellipse";

    @Override
    public ShapeData convertToData(Shape shape) {
        if (!(shape instanceof Ellipse)) {
            throw new IllegalArgumentException("La forma non è un'ellisse");
        }

        Ellipse ellipse = (Ellipse) shape;
        ShapeData data = new ShapeData();
        data.setType(TYPE);

        // Conversione delle proprietà geometriche
        data.addProperty("centerX", ellipse.getCenterX());
        data.addProperty("centerY", ellipse.getCenterY());
        data.addProperty("radiusX", ellipse.getRadiusX());
        data.addProperty("radiusY", ellipse.getRadiusY());


        // Conversione delle proprietà stilistiche
        data.setFillColor(convertColorToString(ellipse.getFill() instanceof Color ? (Color) ellipse.getFill() : Color.TRANSPARENT));
        data.setStrokeColor(convertColorToString(ellipse.getStroke() instanceof Color ? (Color) ellipse.getStroke() : Color.BLACK));
        data.setStrokeWidth(ellipse.getStrokeWidth());

        return data;
    }

    @Override
    public Shape convertFromData(ShapeData shapeData) {
        if (!TYPE.equals(shapeData.getType())) {
            throw new IllegalArgumentException("Il tipo di dati non è compatibile con questo converter");
        }

        // Creazione di una nuova ellisse con le proprietà geometriche
        Ellipse ellipse = new Ellipse(
                shapeData.getProperty("centerX"),
                shapeData.getProperty("centerY"),
                shapeData.getProperty("radiusX"),
                shapeData.getProperty("radiusY")
        );

        // Impostazione delle proprietà stilistiche
        ellipse.setFill(convertStringToColor(shapeData.getFillColor()));
        ellipse.setStroke(convertStringToColor(shapeData.getStrokeColor()));
        ellipse.setStrokeWidth(shapeData.getStrokeWidth());

        return ellipse;
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
