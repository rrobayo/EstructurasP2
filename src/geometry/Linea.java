/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import com.sun.javafx.geom.Line2D;
import util.Util;

/**
 * Modela una línea en el espacio bidimensional. 
 * @author Reyes Ruiz
 */
public class Linea {

    private Punto start;
    private Punto end;

    /**
     * Crea una Linea con las coordenadas de inicio y final especificadas
     *
     * @param startX Coordenada X del inicio de la línea
     * @param startY Coordenada Y del inicio de la línea
     * @param endX Coordenada X del final de la línea
     * @param endY Coordenada Y del final de la línea
     */
    public Linea(float startX, float startY, float endX, float endY) {
        this(new Punto(startX, startY), new Punto(endX, endY));
    }

    /**
     * Crea una Linea con los Puntos de inicio y final especificados
     *
     * @param start Punto de inicio de la línea
     * @param end Punto de final de la línea
     */
    public Linea(Punto start, Punto end) {
        this.start = start;
        this.end = end;
    }

    public Punto getStart() {
        return start;
    }

    public void setStart(Punto start) {
        this.start = start;
    }

    public Punto getEnd() {
        return end;
    }

    public void setEnd(Punto end) {
        this.end = end;
    }

    /**
     * Calcula la pendiente de la línea (o DivisioByZero si la línea es
     * vertical)
     *
     * @return la pendiente de la línea
     */
    public float getPendiente() {
        return (end.getY() - start.getY()) / (end.getX() - start.getX());
    }

    /**
     * Calcula el intercepto Y de la Linea (el valor Y que tomaría cuando su
     * valor X fuese 0)
     *
     * @return el intercepto-Y de la Linea
     */
    public float getInterceptoY() {
        return Util.map(0, start.getX(), end.getX(), start.getY(), end.getY());
    }

    /**
     * Devuelve si dos líneas son paralelas
     *
     * @param otra La Linea a comparar con esta
     * @return
     */
    public boolean esParalela(Linea otra) {
        return Util.fEquals(getPendiente(), otra.getPendiente());
    }

    /**
     * Prueba si dos Lineas se intersecan
     *
     * @param otra La otra Linea a probar
     * @return <code>true</code> si las Lineas se intersecan, <code>false</code>
     * si no se cortan o si solo dos de sus extremos coinciden
     */
    public boolean interseca(Linea otra) {
        if (this.start.equals(otra.getStart())
                || this.start.equals(otra.getEnd())
                || this.end.equals(otra.getStart())
                || this.end.equals(otra.getEnd())) {
            return false;
        }

        return Line2D.linesIntersect(start.getX(), start.getY(),
                end.getX(), end.getY(),
                otra.getStart().getX(), otra.getStart().getY(),
                otra.getEnd().getX(), otra.getEnd().getY());
    }

    @Override
    public String toString() {
        return start + ", " + end;
    }

    /**
     * Extiende el punto de fin de la Linea una longitud dada, conservando la
     * pendiente
     *
     * @param longitud La longitud que se debe extender la linea
     */
    public void extender(float longitud) {
        float deltaX = this.end.getX() - this.start.getX();
        float deltaY = this.end.getY() - this.start.getY();
        float delta = this.start.getDistancia(this.end);
        this.end.setX(this.end.getX() + (deltaX / delta) * longitud);
        this.end.setY(this.end.getY() + (deltaY / delta) * longitud);
    }
}
