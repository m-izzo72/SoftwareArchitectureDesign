package org.softwarearchitecturedesigngroup10.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.command.Command;
import org.softwarearchitecturedesigngroup10.model.command.CommandManager;
import org.softwarearchitecturedesigngroup10.model.command.PaintCommand;
import org.softwarearchitecturedesigngroup10.model.command.DeleteShapeCommand;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CanvasModel {
    private final Pane canvas;
    private final CommandManager commandManager;
    private final Map<String, ShapeConverter> converters;

    public CanvasModel(Pane canvas) {
        this.canvas = canvas;
        this.commandManager = new CommandManager();
        this.converters = new HashMap<>();

        // Registrazione dei converter
        registerConverter(new RectangleConverter());
        registerConverter(new EllipseConverter());
        registerConverter(new LineConverter());
    }

    public void paint(Shape shape) {
        Command paintCommand = new PaintCommand(canvas, shape);
        commandManager.executeCommand(paintCommand);

    }

    public void deleteShape(Shape shape) {
        Command deleteCommand = new DeleteShapeCommand(canvas, shape);
        commandManager.executeCommand(deleteCommand);
    }

    public void undo() {

        commandManager.undo();
    }


    public void save(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Creiamo una lista di dati serializzabili delle forme
//            List<ShapeData> shapesData = new ArrayList<>();
//
//            // Convertiamo ogni Shape in ShapeData usando la strategia appropriata
//            ArrayList<Shape> shapesList = new ArrayList<>();
//            for( Node node : canvas.getChildren()) shapesList.add((Shape)node);
//            //List<Shape> shapes = (Shape) canvas.getChildren();
//            for ( Shape shape : shapesList) {
//                ShapeConverter converter = getConverterForShape(shape);
//                if (converter != null) {
//                    ShapeData shapeData = converter.convertToData(shape);
//                    shapesData.add(shapeData);
//
//                }
//            }

            System.out.println(canvas.getChildren().toString());
            StringBuilder sb = new StringBuilder();
            for(Node shape : canvas.getChildren()) sb.append(shape.toString()).append("\n");

            // Salviamo la lista di ShapeData
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Shape> parseShapesFromFile(String filePath) throws IOException {
        List<Shape> shapes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) { // Ignora righe vuote o commenti (opzionale)
                    continue;
                }
                try {
                    Shape shape = parseShapeFromString(line);
                    if (shape != null) {
                        shapes.add(shape);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Errore di parsing alla riga " + lineNumber + ": '" + line + "'. Dettagli: " + e.getMessage() + ". Riga saltata.");
                } catch (Exception e) {
                    System.err.println("Errore imprevisto alla riga " + lineNumber + ": '" + line + "'. Dettagli: " + e.getMessage() + ". Riga saltata.");
                    // e.printStackTrace(); // Per debug più dettagliato
                }
            }
        }
        return shapes;
    }

    /**
     * Esegue il parsing di una singola stringa per creare un oggetto Shape.
     *
     * @param line La stringa che rappresenta la forma.
     * @return Un oggetto Shape.
     * @throws IllegalArgumentException Se la stringa è malformata o il tipo di forma è sconosciuto.
     */
    private Shape parseShapeFromString(String line) throws IllegalArgumentException {
        int firstBracket = line.indexOf('[');
        int lastBracket = line.lastIndexOf(']');

        if (firstBracket == -1 || lastBracket == -1 || lastBracket != line.length() - 1) {
            throw new IllegalArgumentException("Formato linea non valido. Parentesi '[' o ']' mancanti o mal posizionate.");
        }

        String shapeType = line.substring(0, firstBracket);
        String propsString = line.substring(firstBracket + 1, lastBracket);

        Map<String, String> properties = new HashMap<>();
        String[] propPairs = propsString.split(",");
        for (String pair : propPairs) {
            String[] keyValue = pair.split("=", 2); // Splitta solo sul primo '='
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("Proprietà malformata: '" + pair + "'. Deve essere nel formato 'chiave=valore'.");
            }
            properties.put(keyValue[0].trim(), keyValue[1].trim());
        }

        switch (shapeType) {
            case "Ellipse":
                return createEllipse(properties);
            case "Rectangle":
                return createRectangle(properties);
            case "Line":
                return createLine(properties);
            default:
                throw new IllegalArgumentException("Tipo di forma sconosciuto: " + shapeType);
        }
    }

    private Ellipse createEllipse(Map<String, String> props) throws IllegalArgumentException {
        try {
            double centerX = Double.parseDouble(props.get("centerX"));
            double centerY = Double.parseDouble(props.get("centerY"));
            double radiusX = Double.parseDouble(props.get("radiusX"));
            double radiusY = Double.parseDouble(props.get("radiusY"));
            Color fill = Color.valueOf(props.get("fill"));
            Color stroke = Color.valueOf(props.get("stroke"));
            double strokeWidth = Double.parseDouble(props.get("strokeWidth"));

            Ellipse ellipse = new Ellipse(centerX, centerY, radiusX, radiusY);
            ellipse.setFill(fill);
            ellipse.setStroke(stroke);
            ellipse.setStrokeWidth(strokeWidth);
            return ellipse;
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException("Proprietà mancante o non valida per Ellipse. Verifica i valori e la presenza di: centerX, centerY, radiusX, radiusY, fill, stroke, strokeWidth. Dettaglio errore: " + e.getMessage(), e);
        }
    }

    private Rectangle createRectangle(Map<String, String> props) throws IllegalArgumentException {
        try {
            double x = Double.parseDouble(props.get("x"));
            double y = Double.parseDouble(props.get("y"));
            double width = Double.parseDouble(props.get("width"));
            double height = Double.parseDouble(props.get("height"));
            Color fill = Color.valueOf(props.get("fill"));
            Color stroke = Color.valueOf(props.get("stroke"));
            double strokeWidth = Double.parseDouble(props.get("strokeWidth"));

            Rectangle rectangle = new Rectangle(x, y, width, height);
            rectangle.setFill(fill);
            rectangle.setStroke(stroke);
            rectangle.setStrokeWidth(strokeWidth);
            return rectangle;
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException("Proprietà mancante o non valida per Rectangle. Verifica i valori e la presenza di: x, y, width, height, fill, stroke, strokeWidth. Dettaglio errore: " + e.getMessage(), e);
        }
    }

    private Line createLine(Map<String, String> props) throws IllegalArgumentException {
        try {
            double startX = Double.parseDouble(props.get("startX"));
            double startY = Double.parseDouble(props.get("startY"));
            double endX = Double.parseDouble(props.get("endX"));
            double endY = Double.parseDouble(props.get("endY"));
            Color stroke = Color.valueOf(props.get("stroke")); // Le linee non hanno 'fill' nel template
            double strokeWidth = Double.parseDouble(props.get("strokeWidth"));

            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(stroke);
            line.setStrokeWidth(strokeWidth);
            return line;
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException("Proprietà mancante o non valida per Line. Verifica i valori e la presenza di: startX, startY, endX, endY, stroke, strokeWidth. Dettaglio errore: " + e.getMessage(), e);
        }
    }
    public void load(File file) {
        try {
            System.out.println("Tentativo di parsing del file: " + file.getAbsolutePath());
            // Usa il metodo della classe ShapeParser
            List<Shape> formeCaricate = parseShapesFromFile(file.getAbsolutePath());
            System.out.println("Parse completato: " + formeCaricate.size() + " forme caricate.");
            canvas.getChildren().addAll(formeCaricate);
        } catch (IOException e) {

        } catch (Exception e) {

        }
    }

    /**
     * Registra un converter nel modello
     */
    private void registerConverter(ShapeConverter converter) {
        converters.put(converter.getSupportedType(), converter);
    }

    /**
     * Ottiene il converter appropriato per una forma
     */
    private ShapeConverter getConverterForShape(Shape shape) {
        for (ShapeConverter converter : converters.values()) {
            try {
                // Prova a convertire la forma e vedi se funziona
                converter.convertToData(shape);
                return converter;
            } catch (IllegalArgumentException e) {
                // Questo converter non è compatibile con questa forma
                continue;
            }
        }
        return null;
    }
}
