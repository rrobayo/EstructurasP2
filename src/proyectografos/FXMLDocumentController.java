/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectografos;

import geometry.Linea;
import geometry.Poligono;
import geometry.Punto;
import graphs.Dijkstra;
import graphs.GrafoP;
import graphs.GraphException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.Util;

/**
 *
 * @author Reyes Ruiz
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private MenuItem calcMenu;
    @FXML
    private MenuItem simMenu;

    private List<Punto> puntos;
    private GrafoP<Punto> visuales;
    private List<Poligono> poligonosReales;
    private List<Poligono> poligonosExtendidos;

    private Node robot;
    private List<Punto> ruta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada que hacer aquí
    }

    @SuppressWarnings("squid:S1172")
    @FXML
    private void cargar(ActionEvent event) {
        FileChooser dialog = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Archivos TXT (*.txt)", "*.txt");
        dialog.getExtensionFilters().add(filter);
        dialog.setInitialDirectory(new File("."));

        File file = dialog.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            puntos = new ArrayList<>();
            poligonosReales = new ArrayList<>();
            Util.parseFile(file, puntos, poligonosReales);
            dibujar();
            calcMenu.setDisable(false);
        }
    }

    @SuppressWarnings("squid:S1172")
    @FXML
    private void calcular(ActionEvent event) {
        crearPoligonosExtendidos();
        crearGrafo();
        try {
            ruta = Dijkstra.getRutaMasCorta(visuales, 0, 1);
            dibujar(ruta, 2);
            simMenu.setDisable(false);
        } catch (GraphException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        abrirVentana(ruta);
    }

    @SuppressWarnings("squid:S1172")
    @FXML
    private void simular(ActionEvent event) {
        Path path = new Path(new MoveTo(ruta.get(0).getX(), ruta.get(0).getY()));
        ruta.forEach(p -> path.getElements().add(new LineTo(p.getX(), p.getY())));
        PathTransition pt = new PathTransition(Duration.millis(8000), path, robot);
        pt.setInterpolator(Interpolator.LINEAR);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.play();
    }

    private void dibujar(List<Punto> ruta, double width) {
        Polyline poly = new Polyline();
        poly.setStroke(Color.RED);
        poly.setStrokeWidth(width);
        ruta.forEach(p -> poly.getPoints().addAll((double) p.getX(), (double) p.getY()));
        pane.getChildren().add(poly);
    }

    private void dibujar(Poligono p, Color color) {
        Polygon poly = new Polygon();
        p.getPuntos().forEach(punto -> poly.getPoints().addAll((double) punto.getX(), (double) punto.getY()));
        poly.setFill(color);
        pane.getChildren().add(poly);
    }

    private void dibujar() {
        robot = new Circle(puntos.get(0).getX(), puntos.get(0).getY(), 10, Color.RED);
        pane.getChildren().add(robot);
        pane.getChildren().add(new Circle(puntos.get(1).getX(), puntos.get(1).getY(), 8, Color.BLUE));
        poligonosReales.forEach(p -> dibujar(p, Color.GREEN));
        robot.toFront();
    }

    private void crearPoligonosExtendidos() {
        poligonosExtendidos = new ArrayList<>();
        for (Poligono poly : poligonosReales) {
            Poligono polyExt = new Poligono();
            poly.getPuntos().forEach(p -> polyExt.addPunto(p.extender(poly.getCentro(), 15)));
            poligonosExtendidos.add(polyExt);
        }
    }

    private void crearGrafo() {
        visuales = new GrafoP<>(puntos.size());
        try {
            visuales.agregarVertice(puntos.get(0));
            visuales.agregarVertice(puntos.get(1));
            for (Poligono poly : poligonosExtendidos) {
                for (Punto p : poly.getPuntos()) {
                    visuales.agregarVertice(p);
                }
            }
        } catch (GraphException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Punto p : visuales.getVertices()) {
            for (Punto q : visuales.getVertices()) {
                if (p == q) {
                    continue;
                }
                Linea l = new Linea(p, q);
                boolean candidata = true;
                for (Poligono poly : poligonosExtendidos) {
                    if (poly.esSecante(l)) {
                        candidata = false;
                        break;
                    }
                }
                if (candidata) {
                    try {
                        visuales.agregarArco(p, q, p.getDistancia(q));
                    } catch (GraphException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private Stage abrirVentana(List<Punto> ruta) {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SecondWindow.fxml"));
            stage.setScene(new Scene((Pane) loader.load()));
            SecondWindowController controller = loader.<SecondWindowController>getController();
            controller.initData(visuales, ruta, poligonosReales);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return stage;
    }
}
