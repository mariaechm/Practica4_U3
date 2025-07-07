package com.unl.practica4.base.controller.data_struct.graphs;

// This will be the single, unified Point class
public class Point {
    public int r; // Changed to 'r' for consistency with maze generation context
    public int c; // Changed to 'c' for consistency with maze generation context
    public Point parent;

    public Point(int r, int c, Point parent) {
        this.r = r;
        this.c = c;
        this.parent = parent;
    }

    // Method for Prim's maze generation
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

    // Optional: For debugging or display
    @Override
    public String toString() {
        return "(" + r + "," + c + ")";
    }
}
