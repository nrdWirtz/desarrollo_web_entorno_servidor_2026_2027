# Aplicación Tareas TO-DO Proyecto inicial

Aplicación web de gestión de tareas con Spring Boot y Thymeleaf. 
Proyecto de DWES para aprender a organizar el código y evolucionar paso a paso hacia una arquitectura por capas.

La aplicación permite **listar, crear, completar y eliminar tareas**. 
Trabajaremos con una lista en memoria.

Cada tarea tendrá los siguientes datos:

- Identificador.
- Título.
- Prioridad.
- Fecha límite.
- Estado: boolean `true` completada, `false` pendiente.

## Peticiones que atiende el controlador

| Petición | Operación                      |
| --- |--------------------------------|
| `GET /tareas` | Mostrar el listado.            |
| `GET /tareas/nueva` | Mostrar el formulario de alta. |
| `POST /tareas` | Crear una tarea.               |
| `POST /tareas/{id}/completar` | Completar una tarea.           |
| `POST /tareas/{id}/eliminar` | Eliminar una tarea.            |

## Datos del formulario

- El **título** es obligatorio y tiene un máximo de **80 caracteres**.
- La **prioridad** es obligatoria.
- La **fecha límite** es obligatoria.

## Reglas de negocio

- La fecha límite puede ser **hoy o una fecha posterior**.
- No se admiten **títulos repetidos entre tareas pendientes**. Se ignoran las diferencias entre mayúsculas y minúsculas y los espacios de los extremos.
- Puede haber como máximo **tres tareas pendientes de prioridad ALTA**. Las prioridades MEDIA y BAJA no tienen ese límite.
- El servidor asigna el **identificador** y las tareas nuevas empiezan **pendientes**.
- Una tarea completada **no puede completarse de nuevo**.
- Para completar o eliminar una tarea, **esta debe existir**.

Deberemos extraer la diferentes responsabilidades del controlador y llevarnoslas a las capas que correspondan.


