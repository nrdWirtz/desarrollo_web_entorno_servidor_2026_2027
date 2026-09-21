package org.example;


import java.util.Objects;

public class Persona {
    private String dni;
    private String nombre;
    private int edad;

    // Constructor con todos los atributos
    public Persona(String dni, String nombre, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    // Método toString
    @Override
    public String toString() {
        return "Persona{DNI='" + dni + "', Nombre='" + nombre + "', Edad=" + edad + "}";
    }

    // Método equals basado únicamente en el DNI
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Persona persona = (Persona) o;
        return Objects.equals(dni, persona.dni);
    }

}
