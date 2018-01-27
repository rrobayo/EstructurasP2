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
 * Contiene métodos utilitarios variados
 *
 * @author Reyes Ruiz
 */
public class Util {

    /**
     * Constante que define a partir de qué diferencia se consideran iguales dos
     * números de punto flotante. Específicamente, <code>fEquals(a, b)</code>
     * devuelve <code>true</code> si y sólo si la diferencia entre
     * <code>a</code> y <code>b</code> es menor que <code>FLOAT_DELTA</code>
     */
    public static final float FLOAT_DELTA = 1e-10f;

    private Util() {
    }

    /**
     * Reasigna linealmente un valor del rango [inMin, inMax] al rango [outMin,
     * outMax]. x debe estar ENTRE inMin e inMax
     *
     * @param x El valor a reasignar
     * @param inMin El límite inferior del rango de entrada
     * @param inMax El límite superior del rango de entrada
     * @param outMin El límite inferior del rango de salida
     * @param outMax El límite superior del rango de salida
     * @return el valor que correspondería a x, en el rango de salida
     */
    public static float map(float x, float inMin, float inMax, float outMin, float outMax) {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    /**
     * Prueba si dos valores de punto flotante están "suficientemente cerca"
     * para considerarse iguales. El valor por defecto es FLOAT_DELTA
     *
     * @param a El primer número a probar
     * @param b El segundo número a probar
     * @return <code>true</code> si el valor absoluto de la diferencia entre los
     * números es menor que FLOAT_DELTA
     */
    public static boolean fEquals(float a, float b) {
        return fEquals(a, b, FLOAT_DELTA);
    }

    /**
     * Prueba si dos valores de punto flotante están "suficientemente cerca"
     * para considerarse iguales.
     *
     * @param a El primer número a probar
     * @param b El segundo número a probar
     * @param delta El valor por debajo del cual los números se consideran el
     * mismo
     * @return <code>true</code> si el valor absoluto de la diferencia entre los
     * números es menor que <code>delta</code>
     */
    public static boolean fEquals(float a, float b, float delta) {
        return Math.abs(a - b) < delta;
    }

    /**
     * Procesa una archivo de texto para el proyecto y llena las listas
     * <code>puntos</code> y <code>poligonos</code>
     *
     * @param file La dirección del archivo de texto
     * @param puntos La lista de Puntos (debe estar inicializada, los contenidos
     * no serán borrados)
     * @param poligonos La lista de Poligonos (debe estar inicializada, los
     * contenidos no serán borrados)
     */
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

    /**
     * Procesa una archivo de texto para el proyecto y llena las listas
     * <code>puntos</code> y <code>poligonos</code>
     *
     * @param file Un objeto File que apunta al archivo de texto
     * @param puntos La lista de Puntos (debe estar inicializada, los contenidos
     * no serán borrados)
     * @param poligonos La lista de Poligonos (debe estar inicializada, los
     * contenidos no serán borrados)
     */
    public static void parseFile(File file, List<Punto> puntos, List<Poligono> poligonos) {
        parseFile(file.getPath(), puntos, poligonos);
    }
}
