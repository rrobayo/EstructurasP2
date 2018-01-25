/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Reyes Ruiz
 */
public class GrafoP<T> {

    private final int maxVertices;
    private List<T> vertices;
    private float[][] matrizAdy;
    private final boolean dirigido;

    public GrafoP() {
        this(20, false);
    }

    public GrafoP(int maxVertices) {
        this(maxVertices, false);
    }

    public GrafoP(int maxVertices, boolean dirigido) {
        this.maxVertices = maxVertices;
        this.dirigido = dirigido;
        vertices = new ArrayList<>();
        matrizAdy = new float[maxVertices][maxVertices];

        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                matrizAdy[i][j] = 0;
            }
        }
    }

    public List<T> getVertices() {
        return vertices;
    }

    public List<List<T>> getAristas() {
        List<List<T>> aristas = new LinkedList<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (matrizAdy[i][j] > 0) {
                    aristas.add(Arrays.asList(vertices.get(i), vertices.get(j)));
                }
            }
        }
        return aristas;
    }

    private int numVertice(T v) {
        return vertices.indexOf(v);
    }

    public void agregarVertice(T v) throws GraphException {
        if (vertices.size() >= maxVertices) {
            throw new GraphException("Matriz ya tiene el número máximo de vértices");
        }
        if (numVertice(v) == -1) {
            vertices.add(v);
        } else {
            throw new GraphException("El vértice ya existe");
        }
    }

    public void agregarArco(T v1, T v2, float peso) throws GraphException {
        agregarArcoIndices(numVertice(v1), numVertice(v2), peso);
    }

    private void agregarArcoIndices(int v1, int v2, float peso) throws GraphException {
        if (v1 < 0 || v2 < 0) {
            throw new GraphException("Uno de los vértices no existe");
        }
        matrizAdy[v1][v2] = peso;
        if (!dirigido) {
            matrizAdy[v2][v1] = peso;
        }
    }

    public boolean sonAdyacentes(T v1, T v2) throws GraphException {
        return sonAdyacentesIndices(numVertice(v1), numVertice(v2));
    }

    private boolean sonAdyacentesIndices(int v1, int v2) throws GraphException {
        if (v1 < 0 || v2 < 0) {
            throw new GraphException("Uno de los vértices no existe");
        }
        return matrizAdy[v1][v2] > 0;
    }

    public float getPeso(T v1, T v2) throws GraphException {
        return getPesoIndices(numVertice(v1), numVertice(v2));
    }

    public float getPesoIndices(int v1, int v2) throws GraphException {
        if (v1 < 0 || v2 < 0) {
            throw new GraphException("Uno de los vértices no existe");
        }
        return matrizAdy[v1][v2];
    }

    public int[] adyacentesIndice(int v) {
        int count = 0;
        for (int i = 0; i < matrizAdy[v].length; i++) {
            if (matrizAdy[v][i] > 0) {
                count++;
            }
        }
        final int[] answer = new int[count];
        count = 0;
        for (int i = 0; i < matrizAdy[v].length; i++) {
            if (matrizAdy[v][i] > 0) {
                answer[count++] = i;
            }
        }
        return answer;
    }

    public void borrarVertice(T v) throws GraphException {
        borrarVerticeIndice(numVertice(v));
    }

    private void borrarVerticeIndice(int v) throws GraphException {
        if (v < 0) {
            throw new GraphException("El vértice no existe");
        }
        vertices.remove(v);
        for (int i = v + 1; i <= vertices.size(); i++) {
            for (int j = 0; j <= vertices.size(); j++) {
                matrizAdy[i - 1][j] = matrizAdy[i][j];
            }
        }
        for (int i = 0; i <= vertices.size(); i++) {
            for (int j = v + 1; j <= vertices.size(); j++) {
                matrizAdy[i][j - 1] = matrizAdy[i][j];
            }
        }
        for (int i = 0; i <= vertices.size(); i++) {
            matrizAdy[vertices.size()][i] = 0;
            matrizAdy[i][vertices.size()] = 0;
        }
    }

    public void borrarArco(T v1, T v2) throws GraphException {
        borrarArcoIndices(numVertice(v1), numVertice(v2));
    }

    private void borrarArcoIndices(int v1, int v2) throws GraphException {
        if (v1 < 0 || v2 < 0) {
            throw new GraphException("Uno de los vértices no existe");
        }
        matrizAdy[v1][v2] = 0;
        if (!dirigido) {
            matrizAdy[v2][v1] = 0;
        }
    }

    public void printAdjMat() {
        for (float[] i : matrizAdy) {
            for (float j : i) {
                System.out.print(String.format("%.02f", j) + "  ");
            }
            System.out.println();
        }
    }

    public void printVertices() {
        System.out.println(vertices);
    }

    public int numVertices() {
        return vertices.size();
    }

    public int numAristas() {
        int n = 0;
        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                if (matrizAdy[i][j] > 0) {
                    n++;
                }
            }
        }
        if (!dirigido) {
            n /= 2;
        }
        return n;
    }
}
