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
