/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import geometry.Poligono;
import geometry.Punto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reyes Ruiz
 */
public class Util {

    public static final float FLOAT_DELTA = 1e-10f;

    private Util() {
    }

    public static float map(float x, float inMin, float inMax, float outMin, float outMax) {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    public static boolean fEquals(float a, float b) {
        return fEquals(a, b, FLOAT_DELTA);
    }

    public static boolean fEquals(float a, float b, float delta) {
        return Math.abs(a - b) < delta;
    }

    public static void parseFile(String file, List<Punto> puntos, List<Poligono> poligonos) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                if (lineNum == 0) { // Primera linea
                    String[] inicioYFin = line.split(";");
                    assert inicioYFin.length == 2;
                    puntos.add(Punto.parse(inicioYFin[0]));
                    puntos.add(Punto.parse(inicioYFin[1]));
                } else { // Resto de lineas, un poligono por cada una
                    Poligono poly = new Poligono();
                    String[] puntosStr = line.split(";");
                    for (String puntoStr : puntosStr) {
                        Punto p = Punto.parse(puntoStr);
                        puntos.add(p);
                        poly.addPunto(p);
                    }
                    poligonos.add(poly);
                }
                lineNum++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parseFile(File file, List<Punto> puntos, List<Poligono> poligonos) {
        parseFile(file.getPath(), puntos, poligonos);
    }
}
