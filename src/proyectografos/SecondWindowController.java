/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectografos;

import geometry.Poligono;
import geometry.Punto;
import graphs.GrafoP;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

/**
 * FXML Controller class
 *
 * @author Reyes Ruiz
 */
public class SecondWindowController implements Initializable {

    @FXML
    private Pane pane;

    private GrafoP<Punto> visuales;
    private List<Punto> ruta;
    private List<Poligono> referencias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada que hacer aquí
    }

    public void initData(GrafoP<Punto> visuales, List<Punto> ruta, List<Poligono> referencias) {
        this.visuales = visuales;
        this.ruta = ruta;
        this.referencias = referencias;
        dibujar();
    }

    private void dibujar() {
        for (Punto p : visuales.getVertices()) {
            pane.getChildren().add(new Circle(p.getX(), p.getY(), 3, Color.RED));
        }
        for (List<Punto> arista : visuales.getAristas()) {
            Line l = new Line(
                    arista.get(0).getX(), arista.get(0).getY(),
                    arista.get(1).getX(), arista.get(1).getY()
            );
            l.setStroke(Color.RED);
            pane.getChildren().add(l);
        }
        referencias.forEach(p -> dibujar(p, Color.GREEN.deriveColor(0, 1, 1, 0.5)));
        for (Punto p : ruta) {
            pane.getChildren().add(new Circle(p.getX(), p.getY(), 5, Color.BLUE));
        }
        Polyline rutaPoly = new Polyline();
        rutaPoly.setStroke(Color.BLUE);
        rutaPoly.setStrokeWidth(2);
        ruta.forEach(p -> rutaPoly.getPoints().addAll((double) p.getX(), (double) p.getY()));
        pane.getChildren().add(rutaPoly);
    }

    private void dibujar(Poligono p, Color color) {
        Polygon poly = new Polygon();
        p.getPuntos().forEach(punto -> poly.getPoints().addAll((double) punto.getX(), (double) punto.getY()));
        poly.setFill(color);
        pane.getChildren().add(poly);
    }
}
