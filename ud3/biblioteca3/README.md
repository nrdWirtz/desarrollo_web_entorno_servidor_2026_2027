# Mini biblioteca

Solución del ejercicio **biblioteca3**

La aplicación permite consultar el catálogo, filtrar los libros según su estado, ver una ficha y prestar o devolver libros. 

Los **16 libros de ejemplo** se crean directamente en memoria en el constructor del controlador.

Toda la lógica de la aplicación está en `BibliotecaController`: carga inicial, filtrado, búsqueda por identificador y cambio de estado. 

`Libro` contiene los datos, el constructor y sus getters y setters. 

`BibliotecaApplication` es únicamente la clase de arranque de Spring Boot.
