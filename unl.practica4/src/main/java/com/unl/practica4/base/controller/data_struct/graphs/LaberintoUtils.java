package com.unl.practica4.base.controller.data_struct.graphs;

import com.unl.practica4.base.controller.data_struct.list.LinkedList;

public class LaberintoUtils {

    // Constantes para representar los diferentes tipos de celdas en el laberinto
    private static final char PARED = '0';
    private static final char PASILLO = '1';
    private static final char INICIO = 'S';
    private static final char FIN = 'E';
    private static final char CAMINO = '*';

    // Clase interna para devolver el resultado del algoritmo
    public static class SolucionLaberinto {
        public char[][] laberinto;
        public String camino;
        public boolean tieneSolucion;
    }

    // Metodo principal resuelve el laberinto con Dijkstra
    public static SolucionLaberinto resolverLaberinto(char[][] lab) {
        SolucionLaberinto solucion = new SolucionLaberinto();
        solucion.laberinto = copiarLaberinto(lab); // Clonamos para no modificar el original

        try {
            // Buscamos las coordenadas de inicio (S) y fin (E)
            Point inicio = encontrarPunto(lab, INICIO);
            Point fin = encontrarPunto(lab, FIN);

            if (inicio == null || fin == null)
                throw new Exception("El laberinto debe tener punto inicio (S) y fin (E)");

            // Convertimos el laberinto a grafo no dirigido con etiquetas
            UndirectedLabelGraph<String> grafo = convertirLaberintoAGrafo(lab);
            int cols = lab[0].length;

            // Obtenemos los índices internos del grafo a partir de las etiquetas "x,y"
            String etiquetaInicio = inicio.x + "," + inicio.y;
            String etiquetaFin = fin.x + "," + fin.y;
            Integer idxInicio = grafo.getVertex(etiquetaInicio);
            Integer idxFin = grafo.getVertex(etiquetaFin);

            if (idxInicio == null || idxFin == null)
                throw new Exception("No se encontraron los índices de inicio o fin en el grafo");

            // Ejecutamos Dijkstra desde el índice de inicio
            var resultado = UndirectedLabelGraph.DijkstraSolver.solve(
                grafo.getAdjacencyList(), idxInicio - 1, grafo.nro_vertex());

            // Si no hay camino, se indica
            if (resultado.distancias[idxFin - 1] == Integer.MAX_VALUE) {
                solucion.camino = "No existe camino entre S y E";
                solucion.tieneSolucion = false;
            } else {
                // Si hay camino, lo reconstruimos como texto y lo marcamos en el laberinto
                solucion.camino = reconstruirCaminoTexto(resultado.padres, idxInicio, idxFin, cols, resultado.distancias[idxFin - 1]);
                marcarCamino(solucion.laberinto, resultado.padres, idxInicio, idxFin, cols);
                solucion.tieneSolucion = true;
            }

        } catch (Exception e) {
            solucion.camino = "Error: " + e.getMessage();
            solucion.tieneSolucion = false;
        }

        return solucion;
    }

    // Marca el camino en el laberinto usando '*'
    private static void marcarCamino(char[][] lab, int[] padres, int inicioIdx, int finIdx, int cols) {
        int actual = finIdx - 1;
        while (actual != (inicioIdx - 1)) {
            int r = actual / cols;
            int c = actual % cols;
            if (lab[r][c] != INICIO && lab[r][c] != FIN)
                lab[r][c] = CAMINO;
            actual = padres[actual];
        }
    }

    // Convierte la matriz del laberinto a un grafo etiquetado
    private static UndirectedLabelGraph<String> convertirLaberintoAGrafo(char[][] lab) {
        int filas = lab.length;
        int cols = lab[0].length;
        UndirectedLabelGraph<String> grafo = new UndirectedLabelGraph<>(filas * cols, String.class);

        // Etiquetamos cada celda del laberinto con "fila,columna"
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < cols; j++)
                grafo.label_vertex(i * cols + j + 1, i + "," + j);

        // Conectamos las celdas que no son paredes
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < cols; j++)
                if (lab[i][j] != PARED)
                    conectarVecinos(grafo, lab, i, j);

        return grafo;
    }

    // Conecta una celda con sus vecinos (arriba, abajo, izquierda, derecha)
    private static void conectarVecinos(UndirectedLabelGraph<String> grafo, char[][] lab, int i, int j) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int filas = lab.length;
        int cols = lab[0].length;

        for (int[] dir : dirs) {
            int ni = i + dir[0], nj = j + dir[1];
            if (ni >= 0 && ni < filas && nj >= 0 && nj < cols && lab[ni][nj] != PARED)
                grafo.insert_label(i + "," + j, ni + "," + nj, 1.0f);
        }
    }

    // Reconstruye el camino en texto desde los padres del algoritmo de Dijkstra
    private static String reconstruirCaminoTexto(int[] padres, int inicioIdx, int finIdx, int cols, int pasos) {
        StringBuilder camino = new StringBuilder();
        int actual = finIdx - 1;

        camino.append("\n\nCamino desde S hasta E (").append(pasos).append(" pasos):\n");
        while (actual != -1 && actual != (inicioIdx - 1)) {
            int r = actual / cols;
            int c = actual % cols;
            camino.insert(0, " -> (" + r + "," + c + ")");
            actual = padres[actual];
        }

        camino.insert(0, "(S)");
        camino.append(" -> (E)");
        return camino.toString();
    }

    // Representa un punto del laberinto (fila, columna)
    public static class Point {
        public int x, y;
        public Point parent;

        public Point(int x, int y, Point parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    // Busca una celda específica (por ejemplo, la 'S' o la 'E')
    public static Point encontrarPunto(char[][] lab, char punto) {
        for (int i = 0; i < lab.length; i++)
            for (int j = 0; j < lab[i].length; j++)
                if (lab[i][j] == punto)
                    return new Point(i, j, null);
        return null;
    }

    // Clona la matriz del laberinto
    private static char[][] copiarLaberinto(char[][] original) {
        char[][] copia = new char[original.length][];
        for (int i = 0; i < original.length; i++)
            copia[i] = original[i].clone();
        return copia;
    }
}
