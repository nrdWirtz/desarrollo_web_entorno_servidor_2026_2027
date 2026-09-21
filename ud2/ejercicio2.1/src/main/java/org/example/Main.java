package org.example;

import java.util.ArrayList;

public class Main {
    static void main() {
        // Inicialización del ArrayList con 6 personas
        ArrayList<Persona> personas = new ArrayList<>();
        personas.add(new Persona("11111111A", "Ana", 25));
        personas.add(new Persona("22222222B", "Carlos", 17));
        personas.add(new Persona("33333333C", "Beatriz", 42));
        personas.add(new Persona("44444444D", "David", 15));
        personas.add(new Persona("55555555E", "Elena", 56));
        personas.add(new Persona("66666666F", "Fernando", 30));

        // Llamadas a los métodos y muestra de resultados
        System.out.println("--- RESULTADOS DEL ANÁLISIS ---");

        System.out.println("1. Edad del mayor: " + obtenerEdadMayor(personas));

        System.out.println("2. Edad media: " + obtenerEdadMedia(personas));

        System.out.println("3. Nombre del mayor: " + obtenerNombreMayor(personas));

        System.out.println("4. Persona mayor: " + obtenerPersonaMayor(personas));

        System.out.println("\n5. Mayores de edad (>= 18):");
        for (Persona p : obtenerMayoresDeEdad(personas)) {
            System.out.println("   - " + p);
        }

        System.out.println("\n6. Personas con edad mayor o igual a la media:");
        for (Persona p : obtenerMayoresOIgualesQueMedia(personas)) {
            System.out.println("   - " + p);
        }
    }

    // 1. Devuelve la Persona mayor
    public static Persona obtenerPersonaMayor(ArrayList<Persona> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        Persona mayor = lista.get(0);
        for (Persona p : lista) {
            if (p.getEdad() > mayor.getEdad()) {
                mayor = p;
            }
        }
        return mayor;
    }

    // 2. Devuelve la edad del mayor
    public static int obtenerEdadMayor(ArrayList<Persona> lista) {
        Persona mayor = obtenerPersonaMayor(lista);
        return (mayor != null) ? mayor.getEdad() : 0;
    }

    // 3. Devuelve el nombre del mayor
    public static String obtenerNombreMayor(ArrayList<Persona> lista) {
        Persona mayor = obtenerPersonaMayor(lista);
        return (mayor != null) ? mayor.getNombre() : "No hay datos";
    }

    // 4. Devuelve la edad media
    public static double obtenerEdadMedia(ArrayList<Persona> lista) {
        if (lista == null || lista.isEmpty()) {
            return 0.0;
        }

        double suma = 0;
        for (Persona p : lista) {
            suma += p.getEdad();
        }
        return suma / lista.size();
    }

    // 5. Devuelve todos los mayores de edad (>= 18)
    public static ArrayList<Persona> obtenerMayoresDeEdad(ArrayList<Persona> lista) {
        ArrayList<Persona> resultado = new ArrayList<>();
        if (lista == null) {
            return resultado;
        }

        for (Persona p : lista) {
            if (p.getEdad() >= 18) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // 6. Devuelve todos los que tienen una edad mayor o igual a la media
    public static ArrayList<Persona> obtenerMayoresOIgualesQueMedia(ArrayList<Persona> lista) {
        ArrayList<Persona> resultado = new ArrayList<>();
        if (lista == null) {
            return resultado;
        }

        double media = obtenerEdadMedia(lista);
        for (Persona p : lista) {
            if (p.getEdad() >= media) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
