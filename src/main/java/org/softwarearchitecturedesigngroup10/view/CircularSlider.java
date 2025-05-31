package org.softwarearchitecturedesigngroup10.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty; // Importa ObjectProperty
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty; // Importa SimpleObjectProperty
import javafx.event.EventHandler; // Importa EventHandler
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircularSlider extends Pane {

    private final Circle track = new Circle();
    private final Circle thumb = new Circle();

    private double radius;
    private double centerX;
    private double centerY;

    private final DoubleProperty angle = new SimpleDoubleProperty(0);

    // 1. Definisci le proprietà per gli event handler personalizzati
    private final ObjectProperty<EventHandler<? super MouseEvent>> onThumbMouseDragged =
            new SimpleObjectProperty<>(this, "onThumbMouseDragged");

    private final ObjectProperty<EventHandler<? super MouseEvent>> onThumbMouseReleased =
            new SimpleObjectProperty<>(this, "onThumbMouseReleased");


    public CircularSlider() {
        setupUI();
        setupEvents();
    }

    private void setupUI() {
        track.setStroke(Color.LIGHTGRAY);
        track.setStrokeWidth(5);
        track.setFill(null);

        thumb.setFill(Color.DODGERBLUE);
        thumb.setStroke(Color.WHITE);
        thumb.setStrokeWidth(0);
        thumb.setRadius(10);

        getChildren().addAll(track, thumb);
        thumb.setCursor(Cursor.HAND);
    }

    private void setupEvents() {
        // Modifica i gestori esistenti per chiamare anche gli handler personalizzati
        thumb.setOnMouseDragged(event -> {
            handleMouseDragInternal(event); // Logica interna per muovere lo slider
            // Chiama l'handler personalizzato, se impostato
            if (getOnThumbMouseDragged() != null) {
                getOnThumbMouseDragged().handle(event);
            }
        });

        // Gestisce anche il click iniziale come un drag per aggiornare la posizione
        thumb.setOnMousePressed(event -> {
            handleMouseDragInternal(event); // Logica interna per muovere lo slider
            // Nota: di solito onMousePressed non scatena un onMouseDragged handler
            // Ma per coerenza con il drag, potremmo chiamarlo qui o avere un handler separato "onThumbMousePressed"
            // Per ora, ci concentriamo su onDragged e onReleased come richiesto.
        });

        track.setOnMouseClicked(event -> {
            // Calcola le coordinate locali al Pane
            Point2D mousePointInPane = sceneToLocal(event.getSceneX(), event.getSceneY());
            updateAngle(mousePointInPane.getX(), mousePointInPane.getY());
            updateThumbPosition();
            // Se si clicca sulla traccia, potremmo voler scatenare un "released" o un evento di cambio valore
            // Per ora, solo l'aggiornamento interno.
        });

        // Aggiungi il gestore per il rilascio del mouse sulla maniglia
        thumb.setOnMouseReleased(event -> {
            // Qualsiasi logica interna al rilascio (es. snapping) potrebbe andare qui
            // ...

            // Chiama l'handler personalizzato, se impostato
            if (getOnThumbMouseReleased() != null) {
                getOnThumbMouseReleased().handle(event);
            }
        });
    }

    // Rinominato per chiarezza, questa è la logica interna
    private void handleMouseDragInternal(MouseEvent event) {
        Point2D mousePoint = sceneToLocal(event.getSceneX(), event.getSceneY());
        updateAngle(mousePoint.getX(), mousePoint.getY());
        updateThumbPosition();
    }

    private void updateAngle(double mouseX, double mouseY) {
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double rad = Math.atan2(dy, dx);
        double deg = Math.toDegrees(rad);
        double normalizedDeg = (deg + 360) % 360;
        angle.set(normalizedDeg);
    }

    private void updateThumbPosition() {
        double currentAngleRad = Math.toRadians(angle.get());
        double thumbX = centerX + radius * Math.cos(currentAngleRad);
        double thumbY = centerY + radius * Math.sin(currentAngleRad);
        thumb.setCenterX(thumbX);
        thumb.setCenterY(thumbY);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double width = getWidth();
        double height = getHeight();
        centerX = width / 2;
        centerY = height / 2;
        radius = Math.min(width, height) / 2 * 0.8;
        track.setCenterX(centerX);
        track.setCenterY(centerY);
        track.setRadius(radius);
        updateThumbPosition();
    }

    // --- Metodi per l'angolo ---
    public double getAngle() {
        return angle.get();
    }

    public void setAngle(double value) {
        angle.set(Math.max(0, Math.min(360, value)));
        updateThumbPosition();
    }

    public DoubleProperty angleProperty() {
        return angle;
    }

    // --- 2. Metodi Getter, Setter e Property per onThumbMouseDragged ---
    public final EventHandler<? super MouseEvent> getOnThumbMouseDragged() {
        return onThumbMouseDragged.get();
    }

    public final void setOnThumbMouseDragged(EventHandler<? super MouseEvent> handler) {
        onThumbMouseDragged.set(handler);
    }

    public final ObjectProperty<EventHandler<? super MouseEvent>> onThumbMouseDraggedProperty() {
        return onThumbMouseDragged;
    }

    // --- 3. Metodi Getter, Setter e Property per onThumbMouseReleased ---
    public final EventHandler<? super MouseEvent> getOnThumbMouseReleased() {
        return onThumbMouseReleased.get();
    }

    public final void setOnThumbMouseReleased(EventHandler<? super MouseEvent> handler) {
        onThumbMouseReleased.set(handler);
    }

    public final ObjectProperty<EventHandler<? super MouseEvent>> onThumbMouseReleasedProperty() {
        return onThumbMouseReleased;
    }
}