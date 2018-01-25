/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import com.sun.javafx.geom.Point2D;

/**
 *
 * @author Reyes Ruiz
 */
public class Punto {

    private float x;
    private float y;

    private static final float EQUALS_EPSILON = 1e-10f;

    public Punto(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Punto(Punto p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    /**
     * Procesa una cadena en el formato "(<num1>, <num2>)" y devuelve el punto
     * que representa
     *
     * @param str
     * @return
     */
    public static Punto parse(String str) {
        String strX = str.split(",")[0];
        String strY = str.split(",")[1];
        return new Punto(
                Float.parseFloat(strX.substring(1)),
                Float.parseFloat(strY.substring(0, strY.length() - 1))
        );
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDistancia(Punto otro) {
        return Point2D.distance(x, y, otro.getX(), otro.getY());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Float.floatToIntBits(this.x);
        hash = 89 * hash + Float.floatToIntBits(this.y);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Punto other = (Punto) obj;
        return Math.abs(this.x - other.x) < EQUALS_EPSILON
                && Math.abs(this.y - other.y) < EQUALS_EPSILON;
    }

    public Punto extender(Punto centro, float longitud) {
        Linea l = new Linea(new Punto(centro), new Punto(this));
        l.extender(longitud);
        return l.getEnd();
    }
}
