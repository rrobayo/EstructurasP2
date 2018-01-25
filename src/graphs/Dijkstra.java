/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Reyes Ruiz
 */
public class Dijkstra {

    private Dijkstra() {
    }

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

    public static <T> void imprimirRutaMasCorta(GrafoP<T> g, int inicio, int fin) throws GraphException {
        System.out.println(getRutaMasCorta(g, inicio, fin));
    }

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

    public static float longitudRuta(GrafoP<?> g, int inicio, int fin) throws GraphException {
        int[] pred = dijkstra(g, inicio);
        int x = fin;
        int y = pred[fin];
        float longitud = g.getPesoIndices(x, y);
        while (y != inicio) {
            longitud += g.getPesoIndices(x, y);
            y = pred[y];
            x = pred[x];
        }
        return longitud;
    }
}
