# Aplicación de alumnos (DWES)

Spring Boot 4.1.1 + Thymeleaf, Java 25. Los datos viven en memoria: al reiniciar vuelven los cuatro alumnos iniciales.

## Requisitos
- JDK 25
- Maven 3.9+ (o abrir el proyecto en IntelliJ / Eclipse / VS Code)

## Ejecutar
```
mvn spring-boot:run
```
Después abre http://localhost:8080

## Estructura
```
com.dwes.alumnos
├── AlumnosApplication
├── domain/Alumno
├── repositorio/AlumnoRepositorio, AlumnoRepositorioMemoriaImpl
├── servicio/AlumnoServicio, AlumnoServicioImpl
├── controlador/InicioController, AlumnoController
└── excepcion/AlumnoNoEncontradoException, NombreUsuarioDuplicadoException,
              AccionNoPermitidaException, ManejadorDeExcepciones
templates/ inicio, alumnos, alumno, alumno-form, error/404
```

## Rutas
| Método | Ruta | Qué hace |
|---|---|---|
| GET | `/` | Inicio |
| GET | `/alumnos?filtroNombre=` | Listado y filtro por nombre |
| GET | `/alumnos/{id}` | Ficha (404 si no existe) |
| GET / POST | `/alumnos/nuevo` | Formulario y alta |
| GET / POST | `/alumnos/{id}/editar` | Formulario y edición |
| POST | `/alumnos/{id}/activar` | Activar |
| POST | `/alumnos/{id}/desactivar` | Desactivar |
| POST | `/alumnos/{id}/eliminar` | Eliminar (solo inactivos) |
