package es.curso.empleados;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Se encarga de preparar la base de datos al iniciar la aplicación.
 * Primero crea la tabla de empleados, si todavía no existe, y después
 * carga los datos iniciales cuando la tabla está vacía.
 */
public final class InicializadorBD {

    /**
     * Constructor privado para impedir que se creen objetos de esta clase.
     * Todos sus métodos son estáticos, por lo que no es necesario
     * instanciarla.
     */
    private InicializadorBD() {
    }

    /**
     * Inicializa la estructura y los datos de la base de datos.
     *
     * El archivo schema.sql se ejecuta siempre porque contiene una
     * sentencia CREATE TABLE IF NOT EXISTS.
     *
     * El archivo datos.sql solamente se ejecuta cuando la tabla
     * de empleados está vacía.
     *
     * @throws SQLException si se produce un error al acceder a la base de datos
     * @throws IOException  si no se puede leer alguno de los archivos SQL
     */
    public static void inicializar() throws SQLException, IOException {
        try (Connection conexion = ConexionBD.obtenerConexion()) {

            // Crea la tabla si todavía no existe.
            ejecutarScript(conexion, "/schema.sql");

            // Los datos iniciales solo se insertan cuando no hay empleados.
            if (tablaVacia(conexion)) {
                ejecutarScript(conexion, "/datos.sql");
            }
        }
    }

    /**
     * Comprueba si la tabla de empleados contiene algún registro.
     *
     * @param conexion conexión abierta con la base de datos
     * @return true si la tabla está vacía; false en caso contrario
     * @throws SQLException si se produce un error al ejecutar la consulta
     */
    private static boolean tablaVacia(Connection conexion)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM empleados";

        try (Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            // COUNT(*) siempre devuelve una fila con el número de registros.
            resultado.next();

            return resultado.getInt(1) == 0;
        }
    }

    /**
     * Lee un archivo SQL situado en src/main/resources y ejecuta las sentencias
     *
     * @param conexion conexión abierta con la base de datos
     * @param recurso  ruta del archivo dentro de los recursos del proyecto
     * @throws SQLException si se produce un error al ejecutar una sentencia
     * @throws IOException  si el archivo no existe o no puede leerse
     */
    private static void ejecutarScript(Connection conexion, String recurso) throws SQLException, IOException {

        /*
         * Al comenzar la ruta por "/", el archivo se busca desde
         * la raíz de src/main/resources.
         */
        try (InputStream entrada = InicializadorBD.class.getResourceAsStream(recurso)) {

            if (entrada == null) {
                throw new IOException(
                        "No se encuentra el recurso " + recurso
                );
            }

            // Se lee todo el contenido del archivo utilizando UTF-8.
            String contenido = new String(entrada.readAllBytes(), StandardCharsets.UTF_8);

            /*
             * Se separan las sentencias utilizando el punto y coma.
             * Esta solución es suficiente para los scripts sencillos
             * utilizados en este ejercicio.
             */
            for (String sentenciaSql : contenido.split(";")) {

                // Evita ejecutar fragmentos vacíos.
                if (!sentenciaSql.isBlank()) {
                    try (Statement sentencia =
                                 conexion.createStatement()) {

                        sentencia.execute(sentenciaSql);
                    }
                }
            }
        }
    }
}