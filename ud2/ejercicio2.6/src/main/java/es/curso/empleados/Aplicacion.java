package es.curso.empleados;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {
    private final Scanner teclado = new Scanner(System.in);
    private final EmpleadoH2 empleadoH2;

    public Aplicacion(EmpleadoH2 empleadoH2) {
        this.empleadoH2 = empleadoH2;
    }

    public static void main(String[] args) {
        try {
            InicializadorBD.inicializar();
            new Aplicacion(new EmpleadoH2()).iniciar();
        } catch (SQLException | IOException excepcion) {
            System.err.println("No se pudo iniciar la aplicación: " + excepcion.getMessage());
        }
    }

    /**
     * Ejecuta el menú principal de la aplicación hasta que el usuario
     * seleccione la opción de salir.
     */
    private void iniciar() {
        int opcion;

        do {
            mostrarEmpleados();
            mostrarMenu();
            opcion = leerEntero("Selecciona una opción: ");

            try {
                switch (opcion) {
                    case 1 -> alta();
                    case 2 -> baja();
                    case 3 -> modificacion();
                    case 4 -> consultaPorSalario();
                    case 0 -> System.out.println("Aplicación finalizada.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException excepcion) {
                System.err.println("No se pudo completar la operación: " + excepcion.getMessage()
                );
            } catch (UnsupportedOperationException excepcion) {
                // Esta excepción se produce mientras algún método todavía no haya sido implementado.
                System.err.println(excepcion.getMessage());
            }

        } while (opcion != 0);
    }

    /**
     * Muestra las opciones disponibles en el menú principal.
     */
    private void mostrarMenu() {
        System.out.println("""

            GESTIÓN DE EMPLEADOS
            1. Alta
            2. Baja
            3. Modificación
            4. Consulta por intervalo salarial
            0. Salir
            """);
    }

    /**
     * Recupera todos los empleados de la base de datos
     * y los muestra por pantalla.
     */
    private void mostrarEmpleados() {
        System.out.println("\nEMPLEADOS REGISTRADOS");
        System.out.printf(
                "%-5s %-32s %12s%n",
                "ID",
                "NOMBRE",
                "SALARIO"
        );
        System.out.println(
                "-----------------------------------------------------"
        );

        try {
            List<Empleado> empleados = empleadoH2.listarTodos();

            if (empleados.isEmpty()) {
                System.out.println("No hay empleados registrados.");
            } else {
                empleados.forEach(System.out::println);
            }
        } catch (SQLException | UnsupportedOperationException excepcion) {
            mostrarError(excepcion);
        }
    }

    /**
     * Solicita los datos de un nuevo empleado y pide al DAO
     * que lo inserte en la base de datos.
     *
     * @throws SQLException si se produce un error de acceso a la base de datos
     */
    private void alta() throws SQLException {
        int id = leerEnteroPositivo("Id: ");
        String nombre = leerTextoNoVacio("Nombre completo: ");
        double salario = leerDoubleNoNegativo("Salario: ");

        Empleado empleado = new Empleado(id, nombre, salario);
        boolean insertado = empleadoH2.insertar(empleado);

        if (insertado) {
            System.out.println("Empleado añadido.");
        } else {
            System.out.println("No se añadió ningún empleado.");
        }
    }

    /**
     * Solicita el identificador de un empleado y pide al DAO
     * que lo elimine de la base de datos.
     *
     * @throws SQLException si se produce un error de acceso a la base de datos
     */
    private void baja() throws SQLException {
        int id = leerEnteroPositivo(
                "Id del empleado que se eliminará: "
        );

        boolean eliminado = empleadoH2.eliminar(id);

        if (eliminado) {
            System.out.println("Empleado eliminado.");
        } else {
            System.out.println("No existe un empleado con ese id.");
        }
    }

    /**
     * Solicita el identificador y los nuevos datos de un empleado
     * y pide al DAO que actualice su registro.
     *
     * @throws SQLException si se produce un error de acceso a la base de datos
     */
    private void modificacion() throws SQLException {
        int id = leerEnteroPositivo(
                "Id del empleado que se modificará: "
        );
        double salario = leerDoubleNoNegativo("Nuevo salario: ");

        boolean modificado = empleadoH2.modificar(id, salario);

        if (modificado) {
            System.out.println("Empleado modificado.");
        } else {
            System.out.println("No existe un empleado con ese id.");
        }
    }

    /**
     * Solicita un intervalo salarial y muestra los empleados
     * cuyo salario se encuentra entre los límites indicados.
     *
     * @throws SQLException si se produce un error de acceso a la base de datos
     */
    private void consultaPorSalario() throws SQLException {
        double minimo = leerDoubleNoNegativo("Salario mínimo: ");
        double maximo = leerDoubleNoNegativo("Salario máximo: ");

        // No se ejecuta la consulta si el intervalo no es válido.
        if (minimo > maximo) {
            System.out.println(
                    "El salario mínimo no puede ser mayor que el máximo."
            );
            return;
        }

        List<Empleado> resultado =
                empleadoH2.buscarPorSalario(minimo, maximo);

        System.out.println("\nRESULTADO DE LA CONSULTA");

        if (resultado.isEmpty()) {
            System.out.println(
                    "No se encontraron empleados en ese intervalo."
            );
        } else {
            resultado.forEach(System.out::println);
        }
    }

    /**
     * Solicita un número entero y repite la lectura mientras
     * el usuario no introduzca un valor válido.
     *
     * @param mensaje mensaje que se muestra antes de leer el dato
     * @return número entero introducido por el usuario
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException excepcion) {
                System.out.println(
                        "Introduce un número entero válido."
                );
            }
        }
    }

    /**
     * Solicita un número entero mayor que cero.
     *
     * @param mensaje mensaje que se muestra antes de leer el dato
     * @return número entero positivo introducido por el usuario
     */
    private int leerEnteroPositivo(String mensaje) {
        int valor;

        do {
            valor = leerEntero(mensaje);

            if (valor <= 0) {
                System.out.println(
                        "El valor debe ser mayor que cero."
                );
            }
        } while (valor <= 0);

        return valor;
    }

    /**
     * Solicita un número decimal mayor o igual que cero.
     * Se permite utilizar tanto coma como punto decimal.
     *
     * @param mensaje mensaje que se muestra antes de leer el dato
     * @return número decimal no negativo
     */
    private double leerDoubleNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            // Se sustituye la coma para permitir entradas como 1250,50.
            String entrada = teclado.nextLine()
                    .trim()
                    .replace(',', '.');

            try {
                double valor = Double.parseDouble(entrada);

                if (valor >= 0) {
                    return valor;
                }

                System.out.println(
                        "El valor no puede ser negativo."
                );
            } catch (NumberFormatException excepcion) {
                System.out.println("Introduce un número válido.");
            }
        }
    }

    /**
     * Solicita un texto y repite la lectura mientras el usuario
     * introduzca una cadena vacía.
     *
     * @param mensaje mensaje que se muestra antes de leer el dato
     * @return texto no vacío introducido por el usuario
     */
    private String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("El texto no puede quedar vacío.");
        }
    }

    /**
     * Muestra un mensaje con la información de una excepción.
     *
     * @param excepcion excepción que ha provocado el error
     */
    private void mostrarError(Exception excepcion) {
        System.err.println(
                "No se pudo completar la operación: "
                        + excepcion.getMessage()
        );
    }
}

