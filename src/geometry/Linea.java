/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import com.sun.javafx.geom.Line2D;
import util.Util;

/**
 *
 * @author Reyes Ruiz
 */
public class Linea {

    private Punto start;
    private Punto end;

    public Linea(float startX, float startY, float endX, float endY) {
        this(new Punto(startX, startY), new Punto(endX, endY));
    }

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

    public float getPendiente() {
        return (end.getY() - start.getY()) / (end.getX() - start.getX());
    }

    public float getInterceptoY() {
        return Util.map(0, start.getX(), end.getX(), start.getY(), end.getY());
    }

    public boolean esParalela(Linea otra) {
        return Util.fEquals(getPendiente(), otra.getPendiente());
    }

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

    public void extender(float longitud) {
        float deltaX = this.end.getX() - this.start.getX();
        float deltaY = this.end.getY() - this.start.getY();
        float delta = this.start.getDistancia(this.end);
        this.end.setX(this.end.getX() + (deltaX / delta) * longitud);
        this.end.setY(this.end.getY() + (deltaY / delta) * longitud);
    }
}
