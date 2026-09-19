package main.java.com.pbcorporations.gestion.recursos.humanos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Asistencia;

public class AsistenciaRepository {

   
    public boolean marcarEntrada(Asistencia a) {
        String sql = "INSERT INTO asistencias (id_empleado, fecha, hora_entrada, estado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setString(1, a.getIdEmpleado());
            ps.setObject(2, a.getFecha());
            ps.setObject(3, a.getHoraEntrada());
            ps.setString(4, a.getEstado());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al marcar entrada: " + e.getMessage());
            return false;
        }
    }


    public Asistencia buscarAsistenciaAbierta(String idEmpleado, LocalDate fecha) {
        String sql = "SELECT * FROM asistencias " +
                     "WHERE id_empleado = ? AND fecha = ? AND hora_salida IS NULL " +
                     "LIMIT 1";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setString(1, idEmpleado);
            ps.setObject(2, fecha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAsistencia(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar asistencia abierta: " + e.getMessage());
        }
        return null;
    }

    public boolean marcarSalida(int idAsistencia, java.time.LocalTime horaSalida) {
        String sql = "UPDATE asistencias SET hora_salida = ? WHERE id_asistencia = ?";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setObject(1, horaSalida);
            ps.setInt(2, idAsistencia);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al marcar salida: " + e.getMessage());
            return false;
        }
    }


    public List<Asistencia> listarAsistenciasPorFecha(LocalDate fecha) {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT a.*, CONCAT(e.primer_nombre, ' ', e.primer_apellido) AS nombre_empleado " +
                     "FROM asistencias a " +
                     "INNER JOIN empleados e ON a.id_empleado = e.id_empleado " +
                     "WHERE a.fecha = ? " +
                     "ORDER BY a.hora_entrada DESC";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setObject(1, fecha);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Asistencia a = mapearAsistencia(rs);
                    a.setNombreEmpleado(rs.getString("nombre_empleado"));
                    lista.add(a);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar asistencias del día: " + e.getMessage());
        }
        return lista;
    }


    public List<Asistencia> listarAsistenciasParaHorasExtra() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT a.*, CONCAT(e.primer_nombre, ' ', e.primer_apellido) AS nombre_empleado " +
                     "FROM asistencias a " +
                     "INNER JOIN empleados e ON a.id_empleado = e.id_empleado " +
                     "WHERE a.hora_salida IS NOT NULL " +
                     "ORDER BY a.fecha DESC, a.hora_entrada DESC";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Asistencia a = mapearAsistencia(rs);
                a.setNombreEmpleado(rs.getString("nombre_empleado"));
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar asistencias para horas extra: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarHorasExtra(int idAsistencia, double horasExtra) {
        String sql = "UPDATE asistencias SET horas_extra_trabajadas = ? WHERE id_asistencia = ?";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setDouble(1, horasExtra);
            ps.setInt(2, idAsistencia);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar horas extra: " + e.getMessage());
            return false;
        }
    }

    private Asistencia mapearAsistencia(ResultSet rs) throws SQLException {
        Asistencia a = new Asistencia();
        a.setIdAsistencia(rs.getInt("id_asistencia"));
        a.setIdEmpleado(rs.getString("id_empleado"));
        a.setFecha(rs.getObject("fecha", LocalDate.class));
        a.setHoraEntrada(rs.getObject("hora_entrada", java.time.LocalTime.class));

        java.time.LocalTime salida = rs.getObject("hora_salida", java.time.LocalTime.class);
        a.setHoraSalida(salida);

        a.setHorasExtraTrabajadas(rs.getDouble("horas_extra_trabajadas"));
        a.setEstado(rs.getString("estado"));
        return a;
    }
}
