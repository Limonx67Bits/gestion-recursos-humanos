package main.java.com.pbcorporations.gestion.recursos.humanos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoResumen;


public class EmpleadoRepository {

    public List<EmpleadoResumen> listarEmpleadosActivos() {
        List<EmpleadoResumen> lista = new ArrayList<>();
        String sql = "SELECT id_empleado, nombre_completo, nombre_puesto, nombre_departamento " +
                     "FROM vista_empleados_detalle " +
                     "WHERE estado = 'activo' " +
                     "ORDER BY nombre_completo";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EmpleadoResumen e = new EmpleadoResumen();
                e.setIdEmpleado(rs.getString("id_empleado"));
                e.setNombreCompleto(rs.getString("nombre_completo"));
                e.setNombrePuesto(rs.getString("nombre_puesto"));
                e.setNombreDepartamento(rs.getString("nombre_departamento"));
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }
}
