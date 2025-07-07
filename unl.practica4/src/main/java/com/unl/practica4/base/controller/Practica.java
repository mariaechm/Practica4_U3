package com.unl.practica4.base.controller;

import com.unl.practica4.base.controller.data_struct.graphs.LaberintoUtils;
import com.unl.practica4.base.controller.data_struct.graphs.Prim2;

import java.util.Scanner;

public class Practica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int dimension = 0;

        // Validación de dimensión mejorada
        while (true) {
            System.out.print("Ingrese dimensión del laberinto (30-100): ");
            try {
                dimension = Integer.parseInt(sc.nextLine());
                if (dimension >= 30 && dimension <= 100) break;
                System.out.println("Error: Debe ser entre 30 y 100");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
            }
        }

        try {
            // 1. Generar laberinto (usando el método original generar() de Prim2)
            Prim2 generador = new Prim2();
            String laberintoStr = generador.generar(dimension, dimension);
            char[][] laberinto = parseLaberinto(laberintoStr, dimension);

            // 2. Mostrar laberinto
            System.out.println("\nLaberinto generado:");
            printLaberinto(laberinto);

            // 3. Resolver laberinto
            System.out.println("\nResolviendo con Dijkstra...");
            LaberintoUtils.SolucionLaberinto solucion = LaberintoUtils.resolverLaberinto(laberinto);

            // 4. Mostrar solución
            System.out.println("\n" + solucion.camino);
            if (solucion.tieneSolucion) {
                System.out.println("\nLaberinto resuelto:");
                printLaberinto(solucion.laberinto);
            }

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    // Método para convertir el String del laberinto a matriz char[][]
    private static char[][] parseLaberinto(String labStr, int dim) {
        String[] filas = labStr.split("\n");
        char[][] laberinto = new char[dim][dim];
        
        for (int i = 0; i < dim; i++) {
            String[] valores = filas[i].split(",");
            for (int j = 0; j < dim; j++) {
                laberinto[i][j] = valores[j].charAt(0);
            }
        }
        return laberinto;
    }

    // Método de visualización mejorado pero manteniendo esencia original
    private static void printLaberinto(char[][] lab) {
        for (char[] fila : lab) {
            for (char c : fila) {
                switch (c) {
                    case '0' -> System.out.print("▓▓");
                    case '1' -> System.out.print("  ");
                    case 'S' -> System.out.print("S ");
                    case 'E' -> System.out.print("E ");
                    case '*' -> System.out.print("★ ");
                    default -> System.out.print("??");
                }
            }
            System.out.println();
        }
    }
}
