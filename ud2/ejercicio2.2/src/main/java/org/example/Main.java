package org.example;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Integer> mapaContador = new TreeMap<>();

        System.out.println("Introduce una cadena: ");
        String cadena = scanner.nextLine();

        int cantVecesLetraActual;
        for (int i = 0; i < cadena.length(); i++) {
            char letraActual = cadena.charAt(i);
            cantVecesLetraActual = mapaContador.getOrDefault(letraActual, 0) + 1;
            mapaContador.put(letraActual, cantVecesLetraActual);
        }
        System.out.println(mapaContador);
        scanner.close();

    }
}
