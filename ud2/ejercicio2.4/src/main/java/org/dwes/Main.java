package org.dwes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        int opcion;

        do {
            System.out.println("\n==============================");
            System.out.println("       GESTIÓN DEL HOTEL");
            System.out.println("==============================");
            System.out.println("1. Check-in");
            System.out.println("2. Check-out");
            System.out.println("3. Listar habitaciones libres");
            System.out.println("4. Listar habitaciones ocupadas");
            System.out.println("0. Salir");

            opcion = leerEntero(sc, "Elige una opción: ");

            switch (opcion) {
                case 1:
                    realizarCheckIn(sc, hotel);
                    break;
                case 2:
                    realizarCheckOut(sc, hotel);
                    break;
                case 3:
                    hotel.listarLibres();
                    break;
                case 4:
                    hotel.listarOcupadas();
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

    // Solicita los datos necesarios para realizar un check-in
    private static void realizarCheckIn(Scanner sc, Hotel hotel) {
        int numero = leerEntero(sc, "Número de habitación: ");
        LocalDate fechaEntrada = leerFecha(sc, "Fecha de entrada (AAAA-MM-DD): ");
        hotel.checkIn(numero, fechaEntrada);
    }

    // Solicita los datos necesarios para realizar un check-out
    private static void realizarCheckOut(Scanner sc, Hotel hotel) {
        int numero = leerEntero(sc, "Número de habitación: ");
        LocalDate fechaSalida = leerFecha(sc, "Fecha de salida (AAAA-MM-DD): ");
        hotel.checkOut(numero, fechaSalida);
    }

    // Lee un número entero y vuelve a pedirlo si no es correcto
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

    // Lee una fecha y vuelve a pedirla si no es correcta
    private static LocalDate leerFecha(Scanner sc, String mensaje) {
        LocalDate fecha = null;

        while (fecha == null) {
            System.out.print(mensaje);
            String texto = sc.nextLine();
            try {
                fecha = LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: Introduce una fecha válida con formato AAAA-MM-DD.");
            }
        }
        return fecha;
    }
}