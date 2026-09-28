package es.curso.empleados;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoH2 {


    public List<Empleado> listarTodos() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT * FROM empleados";
        try(Connection connection = ConexionBD.obtenerConexion();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet result = ps.executeQuery()){
            while (result.next()){
                Empleado empleado = new Empleado(
                        result.getInt("id"),
                        result.getString("nombre_completo"),
                        result.getDouble("salario")
                );
                empleados.add(empleado);
            }
        }
        return empleados;
    }


    public boolean insertar(Empleado empleado) throws SQLException {
        String query = """
            INSERT INTO empleados
                (id, nombre_completo, salario)
            VALUES (?, ?, ?)
            """;
        try (Connection connection = ConexionBD.obtenerConexion();
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, empleado.getId());
            ps.setString(2, empleado.getNombreCompleto());
            ps.setDouble(3, empleado.getSalario());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }


    public boolean eliminar(int id) throws SQLException {
        String query = "DELETE FROM empelados where id = ?";
        try(Connection connection = ConexionBD.obtenerConexion();
        PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return  filasAfectadas>0;
        }
    }


    public boolean modificar(int id, double salario) throws SQLException {
        String query = "UPDATE empleados SET salario = ? WHERE id = ?";

        try(Connection connection = ConexionBD.obtenerConexion();
        PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(2, id);
            ps.setDouble(1, salario);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }


    public List<Empleado> buscarPorSalario(double salarioMinimo, double salarioMaximo)
            throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query =  "SELECT * FROM empleados WHERE salario BETWEEN ? AND ?";

        try(Connection connection = ConexionBD.obtenerConexion();
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setDouble(1, salarioMinimo);
            ps.setDouble(2, salarioMaximo);
            ResultSet result = ps.executeQuery();

            while (result.next()){
                Empleado empleado = new Empleado(
                        result.getInt("id"),
                        result.getString("nombre_completo"),
                        result.getDouble("salario")
                );
                empleados.add(empleado);
            }
        }
        return empleados;
        }

    private UnsupportedOperationException pendiente(String metodo) {
        return new UnsupportedOperationException(
                "Método " + metodo + " pendiente de implementar"
        );
    }
}
