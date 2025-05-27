package org.softwarearchitecturedesigngroup10.model.shapesdata;

public class EllipseData extends ShapeData {

    private double radiusX;
    private double radiusY;
    private double centerX;
    private double centerY;

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(double radiusX) {
        this.radiusX = radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(double radiusY) {
        this.radiusY = radiusY;
    }

    @Override
    public double getWidth() {
        return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
        return getRadiusY() * 2;
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        double newRadiusX = newWidth / 2.0;
        double newRadiusY = newHeight / 2.0;

        // Mantieni X/Y (top-left) fisso e aggiorna centro e raggi.
        setRadiusX(newRadiusX);
        setRadiusY(newRadiusY);
        setCenterX(getX() + newRadiusX);
        setCenterY(getY() + newRadiusY);
    }
}