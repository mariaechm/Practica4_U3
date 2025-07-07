package com.unl.practica4.base.controller;

import com.unl.practica4.base.controller.data_struct.graphs.LaberintoUtils;
import com.unl.practica4.base.controller.data_struct.graphs.Prim2;

import java.util.Scanner;

public class Practica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int dimension = 0;

        // Pedir al usuario una dimensión válida entre 30 y 100
        while (true) {
            System.out.print("\nIngrese dimensión del laberinto (30-100): ");
            try {
                dimension = Integer.parseInt(sc.nextLine());
                if (dimension >= 30 && dimension <= 100) break;
                System.out.println("Error: Debe ser entre 30 y 100");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
            }
        }

        try {
            // Generar el laberinto como texto y convertirlo a matriz
            Prim2 generador = new Prim2();
            String laberintoStr = generador.generar(dimension, dimension);
            char[][] laberinto = parseLaberinto(laberintoStr, dimension);

            // Mostrar el laberinto generado
            System.out.println("\nLaberinto generado:");
            printLaberinto(laberinto);

            // Resolver el laberinto usando Dijkstra
            System.out.println("\nResolviendo con Dijkstra...");
            LaberintoUtils.SolucionLaberinto solucion = LaberintoUtils.resolverLaberinto(laberinto);

            // Mostrar el camino y el laberinto resuelto si hay solución
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

    // Convierte el laberinto en texto a una matriz de caracteres
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

    // Imprime el laberinto usando símbolos visuales
    private static void printLaberinto(char[][] lab) {
        for (char[] fila : lab) {
            for (char c : fila) {
                switch (c) {
                    case '0' -> System.out.print("▓▓"); // Pared
                    case '1' -> System.out.print("  "); // Camino
                    case 'S' -> System.out.print("S "); // Inicio
                    case 'E' -> System.out.print("E "); // Fin
                    case '*' -> System.out.print("★ "); // Camino resuelto
                    default -> System.out.print("??");  // Carácter no reconocido
                }
            }
            System.out.println();
        }
    }
}

