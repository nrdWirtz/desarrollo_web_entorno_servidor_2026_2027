package es.curso.empleados;

import java.util.Objects;

public class Empleado {
    private int id;
    private String nombreCompleto;
    private double salario;

    public Empleado(int id, String nombreCompleto, double salario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-32s %10.2f €", id, nombreCompleto, salario);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Empleado empleado)) {
            return false;
        }
        return id == empleado.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
