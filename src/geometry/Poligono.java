/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Reyes Ruiz
 */
public class Poligono {

    private List<Punto> puntos = new ArrayList<>();

    public Poligono() {
        // Constructor por defecto, vacío
    }

    public Poligono(Punto... puntos) {
        this.puntos.addAll(Arrays.asList(puntos)); //Guardar los puntos
    }

    public void addPunto(Punto p) {
        puntos.add(p);
    }

    public List<Punto> getPuntos() {
        return puntos;
    }

    private List<Linea> getLados() {
        List<Linea> lados = new LinkedList<>();
        for (int i = 0; i < puntos.size(); i++) {
            lados.add(new Linea(puntos.get(i), puntos.get((i + 1) % puntos.size())));
        }
        return lados;
    }

    public Punto getCentro() {
        float centroX = 0;
        float centroY = 0;
        for (Punto p : puntos) {
            centroX += p.getX();
            centroY += p.getY();
        }
        centroX /= puntos.size();
        centroY /= puntos.size();
        return new Punto(centroX, centroY);
    }

    public boolean esSecante(Linea l) {
        int startIndex = puntos.indexOf(l.getStart());
        int endIndex = puntos.indexOf(l.getEnd());
        if (startIndex != -1 && endIndex != -1) { // La linea es una diagonal del poligono
            // Solo permitir si es un lado
            return Math.abs(startIndex - endIndex) != 1 && Math.abs(startIndex - endIndex) != puntos.size() - 1;
        }
        for (Linea lado : getLados()) {
            if (lado.interseca(l)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return puntos.toString();
    }

}
