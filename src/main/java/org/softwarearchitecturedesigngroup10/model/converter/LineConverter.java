package org.softwarearchitecturedesigngroup10.model.converter;

import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.helper.ShapeData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineConverter implements ShapeConverter{

    private static final String TYPE = "line";

    @Override
    public ShapeData convertToData(Shape shape) {
        if (!(shape instanceof Line)) {
            throw new IllegalArgumentException("La forma non è una linea");
        }

        Line line = (Line) shape;
        ShapeData data = new ShapeData();
        data.setType(TYPE);

        // Conversione delle proprietà geometriche
        data.addProperty("startX", line.getStartX());
        data.addProperty("startY", line.getStartY());
        data.addProperty("endX", line.getEndX());
        data.addProperty("endY", line.getEndY());

        // Conversione delle proprietà stilistiche
        // Le linee in genere non hanno riempimento (fill), ma per coerenza lo gestiamo ugualmente
        data.setFillColor(convertColorToString(line.getFill() instanceof Color ? (Color) line.getFill() : Color.TRANSPARENT));
        data.setStrokeColor(convertColorToString(line.getStroke() instanceof Color ? (Color) line.getStroke() : Color.BLACK));
        data.setStrokeWidth(line.getStrokeWidth());

        return data;
    }

    @Override
    public Shape convertFromData(ShapeData shapeData) {
        if (!TYPE.equals(shapeData.getType())) {
            throw new IllegalArgumentException("Il tipo di dati non è compatibile con questo converter");
        }

        // Creazione di una nuova linea con le proprietà geometriche
        Line line = new Line(
                shapeData.getProperty("startX"),
                shapeData.getProperty("startY"),
                shapeData.getProperty("endX"),
                shapeData.getProperty("endY")
        );

        // Impostazione delle proprietà stilistiche
        line.setStroke(convertStringToColor(shapeData.getStrokeColor()));
        line.setStrokeWidth(shapeData.getStrokeWidth());

        return line;
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
