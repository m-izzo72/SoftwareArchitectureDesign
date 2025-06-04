package org.softwarearchitecturedesigngroup10.model.shapesdata;

import javafx.scene.text.Font;
import javafx.scene.text.TextBoundsType;

public class TextData extends ShapeData {
    private String text;
    private double fontSize;
    private String fontFamily;

    public TextData() {
        this.fontSize = 16;
        this.fontFamily = "System";
        setType("TD");
    }

    @Override
    public void setStrokeWidth(double width) {
        // DO NOTHING as stroke width isn't editable
    }

    @Override
    public void setStrokeColor(String strokeColor) {
        // DO NOTHING as stroke color isn't editable
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public double getFontSize() { return fontSize; }
    public void setFontSize(double fontSize) { this.fontSize = fontSize; }

    public String getFontFamily() { return fontFamily; }
    public void setFontFamily(String fontFamily) { this.fontFamily = fontFamily; }

    @Override
    public double getWidth() {
        if (text == null || text.isEmpty()) return 0;
        javafx.scene.text.Text temp = new javafx.scene.text.Text(this.text);
        temp.setFont(Font.font(this.fontFamily, this.fontSize));
        temp.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        return temp.getLayoutBounds().getWidth();
    }

    @Override
    public double getHeight() {
        if (text == null || text.isEmpty()) return 0;
        javafx.scene.text.Text temp = new javafx.scene.text.Text(this.text);
        temp.setFont(Font.font(this.fontFamily, this.fontSize));
        temp.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        return temp.getLayoutBounds().getHeight();
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        double oldHeight = getHeight();
        if (oldHeight > 0 && newHeight > 0) {
            double scaleFactor = newHeight / oldHeight;
            setFontSize(Math.max(5, getFontSize() * scaleFactor));
        } else if (newHeight > 0) {
            setFontSize(Math.max(5, newHeight));
        }
    }
}
