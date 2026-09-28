# Plantilla: gestión de empleados con JDBC y H2

Proyecto inicial para el ejercicio de gestión de empleados. La aplicación crea una base de datos H2 local, genera la tabla `empleados` y carga varios registros de prueba.

## Qué se proporciona

- Proyecto Maven configurado para Java 25 y H2.
- Clase de dominio `Empleado`.
- Conexión y creación automática de la base de datos.
- Menú de consola y validación básica de datos.
- Interfaz `EmpleadoDAO` con las operaciones requeridas.
- Implementación `EmpleadoDAOH2` con los métodos pendientes.

## Trabajo que debe realizar el alumnado

Completar los cinco métodos de `EmpleadoDAOH2`:

- `listarTodos()`
- `insertar(Empleado empleado)`
- `eliminar(int id)`
- `modificar(Empleado empleado)`
- `buscarPorSalario(double salarioMinimo, double salarioMaximo)`

Las operaciones deben utilizar JDBC, consultas parametrizadas y cierre automático de recursos mediante `try-with-resources`.

## Ejecución

Desde la carpeta del proyecto:

```bash
mvn compile exec:java
```

La base de datos se guarda en la carpeta `datos/`, que se crea al ejecutar el programa. Para recuperar los datos iniciales, cierra la aplicación, elimina esa carpeta y vuelve a ejecutar el proyecto.

> Los métodos pendientes lanzan inicialmente `UnsupportedOperationException`. El menú captura esta excepción para que el proyecto pueda arrancar mientras se desarrolla la solución.
