package org.dwes;

import java.time.LocalDate;

public abstract class Habitacion {

    private int numero;
    private boolean ocupada;
    private LocalDate fechaEntrada;

    public Habitacion(int numero) {
        this.numero = numero;
        this.ocupada = false;
        this.fechaEntrada = null;
    }

    public int getNumero() {
        return numero;
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    // Realiza el check-in
    public void checkIn(LocalDate fechaEntrada) {
        ocupada = true;
        this.fechaEntrada = fechaEntrada;
    }

    // Realiza el check-out y devuelve el precio
    public double checkOut(LocalDate fechaSalida) {

        // La fecha de salida no puede ser anterior a la de entrada
        if (fechaSalida.isBefore(fechaEntrada)) {
            return -1;
        }

        int dias = calcularDias(fechaEntrada, fechaSalida);

        // Si entra y sale el mismo día, se cobra un día
        if (dias == 0) {
            dias = 1;
        }

        double precio = calcularPrecio(dias);

        // Una vez realizado el check-out,
        // la habitación vuelve a estar libre
        ocupada = false;
        fechaEntrada = null;

        return precio;
    }

    // Calcula los días entre dos fechas
    private int calcularDias(LocalDate fechaInicio, LocalDate fechaFin) {

        int dias = 0;
        LocalDate fecha = fechaInicio;

        while (fecha.isBefore(fechaFin)) {
            fecha = fecha.plusDays(1);
            dias++;
        }

        return dias;
    }

    // Cada tipo de habitación calcula el precio de forma diferente
    public abstract double calcularPrecio(int dias);

    public abstract String getTipo();

    @Override
    public String toString() {
        String estado;
        if (ocupada) {
            estado = "OCUPADA";
        } else {
            estado = "LIBRE";
        }
        return "Habitación " + numero + " - " + getTipo() + " - " + estado;
    }
}

