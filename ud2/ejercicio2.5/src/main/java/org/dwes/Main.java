package org.dwes;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Dispositivo> dispositivos = new ArrayList<>();

        dispositivos.add(new Termostato());
        dispositivos.add(new Ascensor());
        dispositivos.add(new Radio());

        int opcion;

        do {

            mostrarEstados(dispositivos);
            mostrarMenu();

            opcion = leerEntero(sc, "Elige una opción: ");

            Dispositivo dispositivo;

            switch (opcion) {
                case 1:
                    dispositivo = seleccionarDispositivo(sc, dispositivos);
                    subirDispositivo(dispositivo);
                    break;
                case 2:
                    dispositivo = seleccionarDispositivo(sc, dispositivos);
                    bajarDispositivo(dispositivo);
                    break;
                case 3:
                    dispositivo = seleccionarDispositivo(sc, dispositivos);
                    resetearDispositivo(dispositivo);
                    break;
                case 4:
                    dispositivo = seleccionarDispositivo(sc, dispositivos);
                    revisarTermostato(dispositivo);
                    break;
                case 0:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("ERROR: Opción incorrecta.");
            }
        } while (opcion != 0);
        sc.close();
    }


    // Muestra las opciones del menú
    private static void mostrarMenu() {
        System.out.println("\n==============================");
        System.out.println("     DISPOSITIVOS DOMÓTICOS");
        System.out.println("==============================");
        System.out.println("1. Subir dispositivo");
        System.out.println("2. Bajar dispositivo");
        System.out.println("3. Resetear dispositivo");
        System.out.println("4. Revisar termostato");
        System.out.println("0. Salir");
    }


    // Muestra el estado de todos los dispositivos
    private static void mostrarEstados(ArrayList<Dispositivo> dispositivos) {
        System.out.println("\n--- ESTADO DE LOS DISPOSITIVOS ---");
        for (int i = 0; i < dispositivos.size(); i++) {
            System.out.println(i + ". " + dispositivos.get(i).verEstado());
        }
    }


    // Incrementa el valor del dispositivo
    private static void subirDispositivo(Dispositivo dispositivo) {
        boolean resultado = dispositivo.subir();
        if (resultado) {
            System.out.println("Dispositivo incrementado correctamente.");
        } else {
            System.out.println("ERROR: El dispositivo ya se encuentra en su valor máximo.");
        }
    }


    // Decrementa el valor del dispositivo
    private static void bajarDispositivo(Dispositivo dispositivo) {
        boolean resultado = dispositivo.bajar();
        if (resultado) {
            System.out.println("Dispositivo decrementado correctamente.");
        } else {
            System.out.println("ERROR: El dispositivo ya se encuentra en su valor mínimo.");
        }
    }


    // Devuelve el dispositivo a su estado inicial
    private static void resetearDispositivo(Dispositivo dispositivo) {

        dispositivo.reset();

        System.out.println("Dispositivo reseteado correctamente.");
    }


    // Revisa el dispositivo si es un termostato
    private static void revisarTermostato(Dispositivo dispositivo) {

        if (dispositivo instanceof Termostato) {

            Termostato termostato = (Termostato) dispositivo;
            termostato.revisar();

            System.out.println("Termostato revisado correctamente.");

        } else {
            System.out.println("ERROR: El dispositivo seleccionado no es un termostato.");
        }
    }


    // Solicita una posición válida y devuelve el dispositivo seleccionado
    private static Dispositivo seleccionarDispositivo(Scanner sc, ArrayList<Dispositivo> dispositivos) {
        int posicion;
        do {
            posicion = leerEntero(sc, "Selecciona un dispositivo (0-" + (dispositivos.size() - 1) + "): ");
            if (posicion < 0 || posicion >= dispositivos.size()) {
                System.out.println("ERROR: El dispositivo seleccionado no existe.");
            }
        } while (posicion < 0 || posicion >= dispositivos.size());
        return dispositivos.get(posicion);
    }


    // Lee un número entero y vuelve a pedirlo si no es válido
    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes introducir un número entero.");
            }
        }
    }
}