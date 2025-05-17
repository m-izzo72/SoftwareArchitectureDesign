package org.softwarearchitecturedesigngroup10.model.helper;

import javafx.scene.paint.Color;

import java.util.Map;
import java.util.HashMap;

public class ShapeData {
    private String type;
    private Map<String, Double> properties; // Per proprietà specifiche (x, y, width, height, etc.)
    private String fillColor; // Esempio: #RRGGBB o "0xff0000ff"
    private String strokeColor;
    private double strokeWidth;

    @Override
    public String toString() {
        return "ShapeData{" +
                "type='" + type + '\'' +
                ", properties=" + properties +
                ", fillColor='" + fillColor + '\'' +
                ", strokeColor='" + strokeColor + '\'' +
                ", strokeWidth=" + strokeWidth +
                '}';
    }

    public ShapeData() {
        this.properties = new HashMap<>();
    }

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public void addProperty(String name, double value) { this.properties.put(name, value); }
    public double getProperty(String name) { return this.properties.getOrDefault(name, 0.0); } // Gestire null o default

    public Map<String, Double> getProperties() { return properties; } // Necessario per la serializzazione/deserializzazione

    public String getFillColor() { return fillColor; }
    public void setFillColor(String fillColor) { this.fillColor = fillColor; }

    public String getStrokeColor() { return strokeColor; }
    public void setStrokeColor(String strokeColor) { this.strokeColor = strokeColor; }

    public double getStrokeWidth() { return strokeWidth; }
    public void setStrokeWidth(double strokeWidth) { this.strokeWidth = strokeWidth; }
}