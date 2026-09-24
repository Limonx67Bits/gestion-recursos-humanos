package main.java.com.pbcorporations.gestion.recursos.humanos.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Departamento;

public class DepartamentoRepository {

    public List<Departamento> listarDepartamentos() throws SQLException {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM departamentos ORDER BY nombre_departamento";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Departamento d = new Departamento();
                d.setIdDepartamento(rs.getInt("id_departamento"));
                d.setNombreDepartamento(rs.getString("nombre_departamento"));
                d.setDescripcion(rs.getString("descripcion"));
                lista.add(d);
            }
        }
        return lista;
    }

    public boolean guardarDepartamento(Departamento d) throws SQLException {
        String sql = "INSERT INTO departamentos (nombre_departamento, descripcion) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNombreDepartamento());
            ps.setString(2, d.getDescripcion());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizarDepartamento(Departamento d) throws SQLException {
        String sql = "UPDATE departamentos SET nombre_departamento = ?, descripcion = ? WHERE id_departamento = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNombreDepartamento());
            ps.setString(2, d.getDescripcion());
            ps.setInt(3, d.getIdDepartamento());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarDepartamento(int idDepartamento) throws SQLException {
        String sql = "DELETE FROM departamentos WHERE id_departamento = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDepartamento);
            // Si tiene puestos asociados (FK id_departamento en "puestos" con
            // ON DELETE RESTRICT), MySQL lanza un SQLException aquí en vez de
            // borrar en cascada; el controlador lo atrapa y avisa al usuario.
            return ps.executeUpdate() > 0;
        }
    }
}
