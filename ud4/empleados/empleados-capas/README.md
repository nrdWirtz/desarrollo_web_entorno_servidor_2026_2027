# Gestión de empleados con arquitectura por capas

Solución del ejercicio de gestión de empleados. La aplicación permite dar de alta, listar, buscar, modificar y borrar empleados.

Los datos se guardan en un `ArrayList<Empleado>` dentro del repositorio. **El listado empieza vacío y se pierde al reiniciar la aplicación.** No hay base de datos, DTO ni mappers.

El [enunciado original](docs/enunciado.pdf) está incluido en la carpeta `docs`.


## Qué permite hacer

- Ver el listado con identificador, nombre, email, salario y botones de edición y borrado.
- Dar de alta un empleado desde el botón «Nuevo empleado», situado debajo del listado.
- Modificar sus datos utilizando el mismo formulario que en el alta.
- Borrar un empleado.
- Buscar por cualquier parte del nombre, sin distinguir mayúsculas y minúsculas.
- Restablecer el listado completo.

El identificador se asigna en el servidor. Durante la edición se muestra como un campo de solo lectura que no se envía con el formulario. Al crear, el servicio construye un empleado sin identificador; al modificar, utiliza el identificador de la ruta. Por eso, enviar manualmente otro identificador en los datos del formulario no cambia qué empleado se crea o modifica.

## Reglas y validación

El nombre, el email y el salario son obligatorios. El email debe tener un formato válido. Estas condiciones se declaran mediante las anotaciones de la clase `Empleado`.

El controlador utiliza `@Valid` y consulta `BindingResult`. Si hay errores, vuelve a mostrar el formulario; solo llama al servicio cuando los datos han superado esta validación. En este flujo no necesitamos repetir en el servicio las comprobaciones de campos vacíos.

Las anotaciones no se ejecutan automáticamente al llamar a cualquier método Java. Si más adelante se llama al servicio desde otro punto de entrada, ese punto también tendrá que garantizar que los datos sean válidos.

Las reglas de negocio están en el servicio:

- El salario debe ser **igual o superior a 18.000 €**, tanto en el alta como en la modificación.
- No se admiten empleados con el mismo email. Se eliminan los espacios de los extremos y se ignoran las diferencias entre mayúsculas y minúsculas.
- Al modificar, un empleado puede conservar su propio email.
- Para consultar, modificar o borrar un empleado por su identificador, este debe existir.

`comprobarReglasNegocio` coordina las comprobaciones. Cada regla tiene su propio método: `comprobarSalarioMinimo` y `comprobarEmailDisponible`. Así se utilizan las mismas reglas en el alta y en la modificación.

Todas las comprobaciones se realizan antes de guardar. Una modificación rechazada deja los datos anteriores tal como estaban.

## Cómo se reparte el código

| Elemento | Qué hace |
| --- | --- |
| `EmpleadoController` · `@Controller` | Recibe las peticiones, valida los datos del formulario, llama al servicio, captura los errores de negocio y prepara las vistas o redirecciones. |
| `GlobalExceptionHandler` · `@ControllerAdvice` | Captura `EmpleadoNoEncontradoException` y muestra una página de error con estado HTTP 404. |
| `EmpleadoService` | Declara las operaciones disponibles para el controlador. |
| `EmpleadoServiceImpl` · `@Service` | Implementa las operaciones y comprueba las reglas de negocio. |
| `EmpleadoRepository` | Declara las operaciones de acceso a los datos. |
| `EmpleadoRepositoryImplMemoria` · `@Repository` | Guarda los empleados en un `ArrayList`, genera los identificadores y realiza las consultas. |
| `Empleado` | Representa los datos de un empleado y declara la validación de los campos. |
| `ReglaNegocioException` | Comunica que una regla de negocio no se ha cumplido. |
| `EmpleadoNoEncontradoException` | Comunica que no existe el empleado solicitado. |

El controlador conoce el servicio, y el servicio conoce el repositorio. Las dependencias se reciben mediante el constructor. Las anotaciones de Spring están en las clases que las implementan.

## Cómo llegan los mensajes al usuario

- **Errores de los campos:** `@Valid` comprueba las anotaciones de `Empleado`. `BindingResult` recoge los errores y Thymeleaf los muestra junto al campo correspondiente con `th:errors`.
- **Errores de negocio:** el servicio lanza `ReglaNegocioException`. El controlador la captura, añade el mensaje al modelo con `model.addAttribute("error", ex.getMessage())` y vuelve a mostrar el formulario con los datos introducidos. La vista muestra ese mensaje en un recuadro rojo.
- **Operaciones correctas:** el controlador añade un mensaje flash y redirige al listado. Allí se muestra la confirmación del alta, modificación o borrado.
- **Empleado inexistente:** el servicio lanza `EmpleadoNoEncontradoException`. El controlador deja que se propague y el método con `@ExceptionHandler` de `GlobalExceptionHandler` la captura. Este método añade el mensaje al modelo y devuelve la vista `error/404`. Su anotación `@ResponseStatus(HttpStatus.NOT_FOUND)` establece el estado HTTP 404.

## Rutas

| Método | Ruta | Operación |
| --- | --- | --- |
| GET | `/empleados` | Listar todos los empleados. |
| GET | `/empleados?busqueda=ana` | Buscar por parte del nombre. |
| GET | `/empleados/nuevo` | Mostrar el formulario de alta. |
| POST | `/empleados` | Crear un empleado. |
| GET | `/empleados/{id}/editar` | Mostrar el formulario de edición. |
| POST | `/empleados/{id}` | Guardar los cambios de un empleado. |
| POST | `/empleados/{id}/eliminar` | Borrar un empleado. |

Las peticiones GET consultan o muestran formularios. Las operaciones que cambian los datos utilizan POST.

