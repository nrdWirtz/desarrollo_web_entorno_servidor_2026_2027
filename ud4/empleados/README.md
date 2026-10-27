# Gestión de empleados (proyecto de partida)

Proyecto Spring Boot (Java 25, Maven) con las plantillas HTML ya maquetadas.

## Qué incluye

- `templates/listado.html`: buscador, tabla de empleados y botón «Nuevo empleado».
- `templates/formulario.html`: formulario único para alta y edición.
- Bootstrap cargado desde CDN, sin ficheros CSS propios.
- Puerto `9000` en `application.properties`.

## Qué falta

Las plantillas son HTML estático: no tienen atributos de Thymeleaf, ni rutas, ni nombres de campo.
Tampoco existen la entidad, el controlador, el servicio ni el repositorio. Todo eso es el ejercicio.

Puedes abrir las plantillas directamente en el navegador para ver cómo deben quedar.
