package org.softwarearchitecturedesigngroup10.model.factories;

import javafx.scene.paint.Color;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.TextData;

import java.util.ArrayList;

public class TextDataFactory implements ShapeDataFactory {

    // Metodo specifico per creare TextData, più comodo per TextDrawingState
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

    // Implementazione del metodo dell'interfaccia (meno diretto per il testo)
    @Override
    public ShapeData createShapeData(ArrayList<Double> points, String fillColor, String strokeColor, double strokeWidth, double rotationAngle) {
        if (points == null || points.size() < 2) {
            throw new IllegalArgumentException("Punti insufficienti per TextData (necessari almeno X, Y).");
        }
        // Questo metodo non è ideale per il testo perché manca la stringa di testo e i dettagli del font.
        // Si potrebbe passare il testo come un "punto" extra o usare valori di default.
        // Per ora, creiamo con testo e font di default.
        TextData textData = new TextData();
        textData.setX(points.get(0));
        textData.setY(points.get(1));
        textData.setText("Testo di Default"); // Testo di default
        //textData.setFontFamily("System");     // Font di default
        textData.setFontSize(16);             // Dimensione font di default
        textData.setFillColor(fillColor);
        textData.setStrokeColor(Color.TRANSPARENT.toString());
        textData.setStrokeWidth(0);
        textData.setRotationAngle(rotationAngle);
        return textData;
    }
}