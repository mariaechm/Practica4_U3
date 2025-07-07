package com.unl.practica4.base.controller.data_struct.graphs;

public class Point {
    public int r;      // Fila
    public int c;      // Columna
    public Point parent; // Punto desde donde llegamos a este

    public Point(int r, int c, Point parent) {
        this.r = r;
        this.c = c;
        this.parent = parent;
    }

    // Devuelve el punto que sigue en la misma direccion del padre al actual
    public Point opposite() {
        if (parent == null) return null;

        if (this.r != parent.r) {
            return new Point(this.r + Integer.compare(this.r, parent.r), this.c, this);
        }
        if (this.c != parent.c) {
            return new Point(this.r, this.c + Integer.compare(this.c, parent.c), this);
        }
        return null;
    }

    @Override
    public String toString() {
        return "(" + r + "," + c + ")";
    }
}
