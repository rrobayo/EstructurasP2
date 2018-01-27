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
 * Modela un polígono en el espacio bidimensional. Conserva una lista de los
 * vértices. Se asume que sólo hay aristas entre vértices adyacentes de la
 * lista, más una arista entre el primer vértice y el último
 *
 * @author Reyes Ruiz
 */
public class Poligono {

    private List<Punto> puntos = new ArrayList<>();

    /**
     * Inicializa un Poligono sin puntos
     */
    public Poligono() {
        // Constructor por defecto, vacío
    }

    /**
     * Inicializa un Poligono con una lista de puntos dados
     *
     * @param puntos Una serie de Puntos que serán los vértices del Poligono
     */
    public Poligono(Punto... puntos) {
        this.puntos.addAll(Arrays.asList(puntos)); //Guardar los puntos
    }

    /**
     * Agregar un Punto a la lista de vértices
     *
     * @param p El nuevo Punto a agregar
     */
    public void addPunto(Punto p) {
        puntos.add(p);
    }

    /**
     * Devuelve la lista de vértices del Poligono
     *
     * @return una lista de Puntos que son los vértices del Poligono
     */
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

    /**
     * Calcula y retorna el centroide (centro de masa) del Poligono
     *
     * @return un Punto situado en el centroide del Poligono
     */
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

    /**
     * Prueba si la Linea dada corta al Poligono en algún punto
     *
     * @param l La Linea a probar
     * @return <code>true</code> si la Linea corta a cualquiera de los lados del
     * Poligono
     */
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
