package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape; // Classe base per il tipo di ritorno di toFXShape
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text; // Nodo Text di JavaFX
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.TextData;

public class TextAdapter implements ShapeAdapterInterface {
    @Override
    public Shape toFXShape(ShapeData data) {
        if (!(data instanceof TextData td)) {
            throw new IllegalArgumentException("Tipo di dati non valido per TextAdapter.");
        }
        Text fxText = new Text(td.getX(), td.getY(), td.getText());

        // Imposta il font
        // Per ora, usiamo solo la famiglia e la dimensione. Potresti aggiungere fontWeight e fontPosture a TextData.
        fxText.setFont(Font.font(td.getFontFamily(), td.getFontSize()));

        // Imposta il colore di riempimento
        if (td.getFillColor() != null && !td.getFillColor().trim().isEmpty()) {
            try {
                fxText.setFill(Color.valueOf(td.getFillColor()));
            } catch (IllegalArgumentException e) {
                System.err.println("Colore di riempimento non valido per il testo: " + td.getFillColor() + " - Uso nero di default.");
                fxText.setFill(Color.BLACK);
            }
        } else {
            fxText.setFill(Color.BLACK); // Riempimento nero di default se non specificato
        }

        // Imposta il colore e lo spessore del bordo (stroke)
        if (td.getStrokeColor() != null && !td.getStrokeColor().trim().isEmpty() && td.getStrokeWidth() > 0) {
            try {
                fxText.setStroke(Color.valueOf(td.getStrokeColor()));
                fxText.setStrokeWidth(td.getStrokeWidth());
            } catch (IllegalArgumentException e) {
                System.err.println("Colore del bordo non valido per il testo: " + td.getStrokeColor() + " - Bordo non applicato.");
                fxText.setStroke(null);
            }
        } else {
            fxText.setStroke(null); // Nessun bordo se non specificato o spessore <= 0
        }

        // Imposta la rotazione
        fxText.setRotate(td.getRotationAngle());

        // Gestione flip (mirroring)
        // Nota: lo scaling di un nodo Text può comportare una leggera distorsione visiva.
        // Per il testo, il "flip" potrebbe essere interpretato diversamente (es. testo al contrario).
        // Qui applichiamo uno scaling semplice.
        double scaleX = 1.0;
        double scaleY = 1.0;
        if (td.isXFlipped()) scaleX = -1.0;
        if (td.isYFlipped()) scaleY = -1.0;
        fxText.setScaleX(scaleX);
        fxText.setScaleY(scaleY);


        // L'origine (x,y) per javafx.scene.text.Text è la baseline sinistra.
        // Per la selezione e il trascinamento, potrebbe essere utile considerare il getLayoutBounds().
        // Il nodo Text estende javafx.scene.shape.Shape, quindi può essere restituito direttamente.
        return fxText;
    }
}