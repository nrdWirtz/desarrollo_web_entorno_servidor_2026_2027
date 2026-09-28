package es.curso.empleados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD {
    private static final String URL = "jdbc:h2:./datos/empleados";
    private static final String USUARIO = "sa";
    private static final String CLAVE = "";

    private ConexionBD() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
