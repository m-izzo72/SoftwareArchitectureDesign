package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape{
    private Color colorStroke, colorFill;
    private double x2, y2;

    public Line(double x1, double y1, double x2, double y2, Color borderColor, boolean isSelected) {
        super(y1, x1, borderColor, null, isSelected);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    void draw(GraphicsContext gc) {
        gc.setStroke(borderColor);
        gc.setLineWidth(isSelected ? 3 : 1); // linea più spessa se selezionata
        gc.strokeLine(x, y, x2, y2);
    }

    public Color getColorStroke() {
        return colorStroke;
    }

    public void setColorStroke(Color colorStroke) {
        this.colorStroke = colorStroke;
    }

    public Color getColorFill() {
        return colorFill;
    }

    public void setColorFill(Color colorFill) {
        this.colorFill = colorFill;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
