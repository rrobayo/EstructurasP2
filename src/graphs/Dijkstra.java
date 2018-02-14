/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa el algoritmo de Dijkstra para grafos ponderados no dirigidos
 *
 * @author Reyes Ruiz
 */
public class Dijkstra {

    private Dijkstra() {
    }

    /**
     * Ejecuta el algoritmo de Dijkstra en el grafo indicado, a partir del
     * vértice especificado
     *
     * @param g El GrafoP sobre el cual se aplicará el algoritmo
     * @param inicio El vértice a partir del cual se aplicará el algoritmo
     * @return un array que contiene, para cada vértice, el vértice que va antes
     * de él en la ruta más corta. Ignorar que pred[inicio] es siempre 0
     * @throws GraphException si alguno de los vértices no existe en el grafo
     */
    public static int[] dijkstra(GrafoP<?> g, int inicio) throws GraphException {
        final float[] dist = new float[g.numVertices()]; // shortest known distance from "s"
        final int[] pred = new int[g.numVertices()]; // preceeding node in path
        final boolean[] visited = new boolean[g.numVertices()]; // all false initially

        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[inicio] = 0;

        for (int i = 0; i < dist.length; i++) {
            final int next = minVertex(dist, visited);
            visited[next] = true;
            // The shortest path to next is dist[next] and via pred[next].

            final int[] n = g.adyacentesIndice(next);
            for (int j = 0; j < n.length; j++) {
                final int v = n[j];
                final float d = dist[next] + g.getPesoIndices(next, v);
                if (dist[v] > d) {
                    dist[v] = d;
                    pred[v] = next;
                }
            }
        }
        return pred;  // (ignore pred[s]==0!)
    }

    /**
     * Calcula el vértice predecesor de <code>fin</code> en la ruta más corta
     * desde <code>inicio</code>
     *
     * @param g El GrafoP sobre el cual se aplicará el algoritmo
     * @param inicio El vértice a partir del cual se aplicará el algoritmo
     * @param fin El vértice al que se quiere llegar
     * @return el índice del vértice predecesor de <code>fin</code>
     * @throws GraphException si alguno de los vértices no existe en el grafo
     */
    public static float dijkstra(GrafoP g, int inicio, int fin) throws GraphException {
        return dijkstra(g, inicio)[fin];
    }

    private static int minVertex(float[] dist, boolean[] v) {
        float x = Float.MAX_VALUE;
        int y = -1;   // graph not connected, or no unvisited vertices
        for (int i = 0; i < dist.length; i++) {
            if (!v[i] && dist[i] < x) {
                y = i;
                x = dist[i];
            }
        }
        return y;
    }

    /**
     * Imprime la ruta más corta desde <code>inicio</code> hasta
     * <code>fin</code>
     *
     * @param <T> El tipo de datos del grafo
     * @param g El grafo en el que se busca la ruta más corta
     * @param inicio El vértice de inicio
     * @param fin El vértice final
     * @throws GraphException si alguno de los vértices no existe en el grafo
     */
    public static <T> void imprimirRutaMasCorta(GrafoP<T> g, int inicio, int fin) throws GraphException {
        System.out.println(getRutaMasCorta(g, inicio, fin));
    }

    /**
     * Imprime la ruta más corta desde <code>inicio</code> hasta
     * <code>fin</code>
     *
     * @param <T> El tipo de datos del grafo
     * @param g El grafo en el que se busca la ruta más corta
     * @param inicio El vértice de inicio
     * @param fin El vértice final
     * @return una lista de vértices que van desde <code>inicio</code> hasta
     * <code>fin</code>
     * @throws GraphException si alguno de los vértices no existe en el grafo
     */
    public static <T> List<T> getRutaMasCorta(GrafoP<T> g, int inicio, int fin) throws GraphException {
        int[] pred = dijkstra(g, inicio);
        final List<T> path = new ArrayList<>();
        int x = fin;
        while (x != inicio) {
            path.add(0, g.getVertices().get(x));
            x = pred[x];
        }
        path.add(0, g.getVertices().get(inicio));
        return path;
    }

    /**
     * Calcula la longitud de la ruta más corta desde <code>inicio</code> hasta
     * <code>fin</code>
     *
     * @param g El grafo en el que se busca la ruta más corta
     * @param inicio El vértice de inicio
     * @param fin El vértice final
     * @return la longitud de la ruta más corta desde <code>inicio</code> hasta
     * <code>fin</code>
     * @throws GraphException si alguno de los vértices no existe en el grafo
     */
    public static float longitudRuta(GrafoP<?> g, int inicio, int fin) throws GraphException {
        int[] pred = dijkstra(g, inicio);
        int x = fin;
        int y = pred[fin];
        float longitud = g.getPesoIndices(x, y);
        while (y != inicio) {
            y = pred[y];
            x = pred[x];
            longitud += g.getPesoIndices(x, y);
        }
        return longitud;
    }
}
