package org.dwes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {

    private ArrayList<Habitacion> habitaciones;

    public Hotel() {

        habitaciones = new ArrayList<>();

        // 5 habitaciones Lowcost
        for (int i = 1; i <= 5; i++) {
            habitaciones.add(new HabitacionLowcost(i));
        }

        // 3 habitaciones Dobles
        for (int i = 6; i <= 8; i++) {
            habitaciones.add(new HabitacionDoble(i));
        }

        // 2 habitaciones Suite
        for (int i = 9; i <= 10; i++) {
            habitaciones.add(new HabitacionSuite(i));
        }
    }

    // Busca una habitación por su número
    private Habitacion buscarHabitacion(int numero) {

        for (Habitacion habitacion : habitaciones) {

            if (habitacion.getNumero() == numero) {
                return habitacion;
            }
        }

        return null;
    }

    // CHECK-IN
    public void checkIn(int numero, LocalDate fechaEntrada) {

        Habitacion habitacion = buscarHabitacion(numero);

        if (habitacion == null) {
            System.out.println("ERROR: La habitación no existe.");

        } else if (habitacion.estaOcupada()) {
            System.out.println("ERROR: La habitación ya está ocupada.");

        } else {
            habitacion.checkIn(fechaEntrada);
            System.out.println("Check-in realizado correctamente.");
        }
    }

    // CHECK-OUT
    public void checkOut(int numero, LocalDate fechaSalida) {

        Habitacion habitacion = buscarHabitacion(numero);

        if (habitacion == null) {
            System.out.println("ERROR: La habitación no existe.");

        } else if (!habitacion.estaOcupada()) {
            System.out.println("ERROR: La habitación está libre.");

        } else {

            double precio = habitacion.checkOut(fechaSalida);

            if (precio == -1) {
                System.out.println(
                        "ERROR: La fecha de salida no puede ser anterior a la fecha de entrada."
                );

            } else {
                System.out.printf("Check-out realizado correctamente.%n Importe a pagar: %.2f euros%n", precio);
            }
        }
    }

    // Mostrar habitaciones libres
    public void listarLibres() {

        System.out.println("\n--- HABITACIONES LIBRES ---");

        for (Habitacion habitacion : habitaciones) {

            if (!habitacion.estaOcupada()) {
                System.out.println(habitacion);
            }
        }
    }

    // Mostrar habitaciones ocupadas
    public void listarOcupadas() {

        System.out.println("\n--- HABITACIONES OCUPADAS ---");

        for (Habitacion habitacion : habitaciones) {

            if (habitacion.estaOcupada()) {
                System.out.println(habitacion);
            }
        }
    }
}