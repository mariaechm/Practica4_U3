package com.unl.practica4.base.controller.data_struct.graphs;

import com.unl.practica4.base.controller.data_struct.list.LinkedList;

public class Prim2 {

    // Genera un laberinto de r filas y c columnas
    public String generar(int r, int c) throws Exception {
        // Inicializa matriz llena de paredes '0'
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) s.append('0');
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) maz[x] = s.toString().toCharArray();

        // Punto inicial aleatorio marcado con 'S'
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        // Lista de frontera con vecinos ortogonales
        LinkedList<Point> frontier = new LinkedList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                try {
                    if (maz[st.r + x][st.c + y] == '1') continue;
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
                frontier.add(new Point(st.r + x, st.c + y, st));
            }
        }

        Point last = null;
        while (!frontier.isEmpty()) {
            int idx = (int) (Math.random() * frontier.getSize());
            Point cu = frontier.get(idx);
            frontier.delete(idx);

            Point op = cu.opposite();
            if (op == null) continue;

            try {
                if (cu.r >= 0 && cu.r < r && cu.c >= 0 && cu.c < c &&
                    op.r >= 0 && op.r < r && op.c >= 0 && op.c < c) {

                    if (maz[cu.r][cu.c] == '0' && maz[op.r][op.c] == '0') {
                        maz[cu.r][cu.c] = '1';
                        maz[op.r][op.c] = '1';
                        last = op;

                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                                try {
                                    if (maz[op.r + x][op.c + y] == '1') continue;
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    continue;
                                }
                                frontier.add(new Point(op.r + x, op.c + y, op));
                            }
                        }
                    }
                }
            } catch (Exception e) {}
        }

        // Marca la salida con 'E'
        if (last != null) {
            maz[last.r][last.c] = 'E';
        }

        // Convierte la matriz a String con formato
        s = new StringBuilder();
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
                aux += maz[i][j] + ",";
            }
            aux = aux.substring(0, aux.length() - 1);
            s.append(aux).append("\n");
        }
        return s.toString();
    }

    // Convierte el String generado en matriz char
    public char[][] generarLaberintoConSolucion(int r, int c) throws Exception {
        String mazeString = generar(r, c);
        String[] rows = mazeString.split("\n");
        char[][] maze = new char[r][c];
        for (int i = 0; i < r; i++) {
            String[] cells = rows[i].split(",");
            for (int j = 0; j < c; j++) {
                maze[i][j] = cells[j].charAt(0);
            }
        }
        return maze;
    }

    public static void main(String[] args) throws Exception {
        Prim2 p = new Prim2();
        char[][] maze = p.generarLaberintoConSolucion(20, 20);
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
