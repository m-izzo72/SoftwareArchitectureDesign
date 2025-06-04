package org.softwarearchitecturedesigngroup10.model.factories;

import javafx.scene.paint.Color;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.TextData;

import java.util.ArrayList;

public class TextDataFactory implements ShapeDataFactory {

    public ShapeData createShapeData(double x, double y, String text, String fontFamily, double fontSize,
                                     String fillColor, String strokeColor, double strokeWidth, double rotationAngle) {
        TextData textData = new TextData();
        textData.setX(x);
        textData.setY(y);
        textData.setText(text);
        textData.setFontFamily(fontFamily);
        textData.setFontSize(fontSize);
        textData.setFillColor(fillColor);
        textData.setStrokeColor(Color.TRANSPARENT.toString());
        textData.setStrokeWidth(0);
        textData.setRotationAngle(rotationAngle);
        return textData;
    }

    @Override
    public ShapeData createShapeData(ArrayList<Double> points, String fillColor, String strokeColor, double strokeWidth, double rotationAngle) {
        if (points == null || points.size() < 2) {
            throw new IllegalArgumentException();
        }
        TextData textData = new TextData();
        textData.setX(points.get(0));
        textData.setY(points.get(1));
        textData.setText("DefaultText");
        textData.setFontSize(16);
        textData.setFillColor(fillColor);
        textData.setStrokeColor(Color.TRANSPARENT.toString());
        textData.setStrokeWidth(0);
        textData.setRotationAngle(rotationAngle);
        return textData;
    }
}