package com.unl.practica4.base.controller.data_struct.graphs;

import java.lang.reflect.Array;
import com.unl.practica4.base.controller.data_struct.list.LinkedList;

public class DirectLabelGraph<E> extends DirectGraph {
    protected E labels[]; //almacena a las etiquetas de cada vertice
    protected LinkedList<VertexPair<E>> vertexDictionary; //list mapea etiquetas con numeros de vertices
    private Class clazz;

    public DirectLabelGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex);
        this.clazz = clazz;
        this.labels = (E[]) Array.newInstance(this.clazz, nro_vertex + 1);
        this.vertexDictionary = new LinkedList<>();
    }

    private static class VertexPair<E> { // clase para etiquetar al numero con su vertice
        E label;
        Integer vertex;

        public VertexPair(E label, Integer vertex) {
            this.label = label;
            this.vertex = vertex;
        }
    }

    public Adjacency exist_edge_label(E o, E d) { //verificacion que halla una arista entre dos etiquetas
        return isLabelGraph() ? exist_edge(getVertex(o), getVertex(d)) : null; //verifica si es un grafo etiquetado
    }

    public void insert_label(E o, E d, Float weight) { //inserta una arista con peso
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d), weight);
        }
    }

    public void insert_label(E o, E d) {
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d), Float.NaN);
        }
    }

    public LinkedList<Adjacency> adjacencies_label(E o) { //retorn alas adayacencias de una etiqueta
        return isLabelGraph() ? adjacencies(getVertex(o)) : new LinkedList<>();
    }

    public void label_vertex(Integer vertex, E data) { //asigna la etiqueta al vertice
        labels[vertex] = data;
        vertexDictionary.add(new VertexPair<>(data, vertex));
    }

    public Boolean isLabelGraph() { //verifiacion si esta bien  etiquetado
        for (int i = 1; i <= nro_vertex(); i++) {
            if (labels[i] == null) {
                return false;
            }
        }
        return true;
    }

    public Integer getVertex(E label) { //obtine el numero de vertices por etiqueta
        VertexPair<E>[] pairs = vertexDictionary.toArray();
        for (VertexPair<E> pair : pairs) {
            if (pair.label.equals(label)) {
                return pair.vertex;
            }
        }
        return null;
    }

    public E getLabel(Integer i) {
        return labels[i];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= nro_vertex(); i++) {
            sb.append("Vertex = ").append(getLabel(i)).append(" \n");
            LinkedList<Adjacency> list = adjacencies(i);
            if (!list.isEmpty()) {
                Adjacency[] matrix = list.toArray();
                for (Adjacency adjacency : matrix) {
                    sb.append(" \tAdjacency ").append("\n").append(" \tVertex = ")
                      .append(String.valueOf(getLabel(adjacency.getDestiny())));
                    if (!adjacency.getWeight().isNaN()) {
                        sb.append("weight =").append(adjacency.getWeight().toString()).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }
}