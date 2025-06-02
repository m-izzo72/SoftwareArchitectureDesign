package org.softwarearchitecturedesigngroup10.model.shapesdata;

import java.util.ArrayList;

public class PolygonData extends ShapeData {

    private ArrayList<Double> points;

    public ArrayList<Double> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Double> points) {
        this.points = points;
    }
    public PolygonData() {
        setType("PD");
    }

    @Override
    public void setX(double newAbsoluteReferenceX) {
        double currentAbsoluteReferenceX = super.getX();
        double deltaXToApplyToPoints = newAbsoluteReferenceX - currentAbsoluteReferenceX;

        if (this.points != null && deltaXToApplyToPoints != 0) {
            for (int i = 0; i < points.size(); i++) {
                if (i % 2 == 0) { // X coord
                    points.set(i, points.get(i) + deltaXToApplyToPoints);
                }
            }
        }
        super.setX(newAbsoluteReferenceX);
    }

    @Override
    public void setY(double newAbsoluteReferenceY) {
        double currentAbsoluteReferenceY = super.getY();
        double deltaYToApplyToPoints = newAbsoluteReferenceY - currentAbsoluteReferenceY;

        if (this.points != null && deltaYToApplyToPoints != 0) {
            for (int i = 0; i < points.size(); i++) {
                if (i % 2 == 1) { // Y coord
                    points.set(i, points.get(i) + deltaYToApplyToPoints);
                }
            }
        }
        super.setY(newAbsoluteReferenceY);
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void resize(double newWidth, double newHeight) {

    }
}
