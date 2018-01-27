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
 * Modela un grafo dirigido o no dirigido con pesos
 *
 * @author Reyes Ruiz
 */
public class GrafoP<T> {

    private final int maxVertices;
    private List<T> vertices;
    private float[][] matrizAdy;
    private final boolean dirigido;

    /**
     * Crea un grafo vacío que puede contener máximo 20 vértices
     */
    public GrafoP() {
        this(20, false);
    }

    /**
     * Crea un grafo vacío que puede contener máximo <code>maxVertices</code>
     * vértices
     *
     * @param maxVertices El número máximo de vértices
     */
    public GrafoP(int maxVertices) {
        this(maxVertices, false);
    }

    /**
     * Crea un grafo vacío que puede contener máximo <code>maxVertices</code>
     * vértices, especificando si es o no dirigido
     *
     * @param maxVertices El número máximo de vértices
     * @param dirigido Si el grafo es dirigido o no dirigido
     */
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

    /**
     * Retorna la lista de vértices del grafo (ATENCIÓN: no una copia!)
     *
     * @return una Lista que contiene los vértices del grafo
     */
    public List<T> getVertices() {
        return vertices;
    }

    /**
     * Retorna la lista de aristas del grafo (aristas con peso MAYOR a cero)
     *
     * @return una Lista de Listas de T. Cada elemento es una lista de
     * EXACTAMENTE 2 elementos, que son los elementos de origen y destino de la
     * arista
     */
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

    /**
     * Agrega un vértice nuevo al grafo
     *
     * @param v El elemento a agregar al grafo
     * @throws GraphException si no se pueden agregar más vértices o si un
     * elemento idéntico ya existe
     */
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

    /**
     * Agrega una arista nueva al grafo
     *
     * @param v1 El vértice de origen de la arista
     * @param v2 El vértice de destino de la arista
     * @param peso El peso de la arista
     * @throws GraphException si alguno de los vértices no está en el grafo
     */
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

    /**
     * Prueba si los vértices dados son adyacentes (si existe una arista que los
     * conecta)
     *
     * @param v1 El vértice de origen de la arista
     * @param v2 El vértice de destino de la arista
     * @return <code>true</code> si existe una arista que conecta los vértices
     * dados
     * @throws GraphException si alguno de los vértices no está en el grafo
     */
    public boolean sonAdyacentes(T v1, T v2) throws GraphException {
        return sonAdyacentesIndices(numVertice(v1), numVertice(v2));
    }

    private boolean sonAdyacentesIndices(int v1, int v2) throws GraphException {
        if (v1 < 0 || v2 < 0) {
            throw new GraphException("Uno de los vértices no existe");
        }
        return matrizAdy[v1][v2] > 0;
    }

    /**
     * Devuelve el peso de la arista entre los vértices dados
     *
     * @param v1 El vértice de origen de la arista
     * @param v2 El vértice de destino de la arista
     * @return el peso de la arista entre los vértices dados
     * @throws GraphException si alguno de los vértices no está en el grafo
     */
    public float getPeso(T v1, T v2) throws GraphException {
        return getPesoIndices(numVertice(v1), numVertice(v2));
    }

    /**
     * Devuelve el peso de la arista entre los vértices dados, por índices (USAR
     * CON CUIDADO!)
     *
     * @param v1 El índice del vértice de origen de la arista
     * @param v2 El índice del vértice de destino de la arista
     * @return el peso de la arista entre los vértices dados
     * @throws GraphException si alguno de los vértices no está en el grafo
     */
    public float getPesoIndices(int v1, int v2) throws GraphException {
        if (v1 < 0 || v2 < 0) {
            throw new GraphException("Uno de los vértices no existe");
        }
        return matrizAdy[v1][v2];
    }

    /**
     * Devuelve una lista con los índices de los vértices que son adyacentes al
     * vértice dado
     *
     * @param v El vértice al que buscar vértices adyacentes
     * @return un array que contiene los índices de los vértices adyacentes al
     * vértice dado
     */
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

    /**
     * Elimina el vértice dado del grafo, y todas sus aristas
     *
     * @param v El vértice a eliminar del grafo
     * @throws GraphException si alguno de los vértices no está en el grafo
     */
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

    /**
     * Elimina el arco dado del grafo
     *
     * @param v1 El vértice de origen de la arista
     * @param v2 El vértice de destino de la arista
     * @throws GraphException si alguno de los vértices no está en el grafo
     */
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

    /**
     * Imprime la matriz de adyacencia del grafo
     */
    public void printAdjMat() {
        for (float[] i : matrizAdy) {
            for (float j : i) {
                System.out.print(String.format("%.02f", j) + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Imprime la lista de vértices del grafo
     */
    public void printVertices() {
        System.out.println(vertices);
    }

    /**
     * Devuelve el número actual de vértices del grafo
     *
     * @return El número de vértices del grafo (no el máximo)
     */
    public int numVertices() {
        return vertices.size();
    }

    /**
     * Calcula cuántas aristas con peso mayor a 0 hay en el grafo
     *
     * @return El número de aristas en el grafo
     */
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
