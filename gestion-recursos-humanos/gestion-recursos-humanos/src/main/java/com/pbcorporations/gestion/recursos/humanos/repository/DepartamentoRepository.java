package main.java.com.pbcorporations.gestion.recursos.humanos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Departamento;

public class DepartamentoRepository {

    public List<Departamento> listarDepartamentos() {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM departamentos ORDER BY nombre_departamento";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Departamento d = new Departamento();
                d.setIdDepartamento(rs.getInt("id_departamento"));
                d.setNombreDepartamento(rs.getString("nombre_departamento"));
                d.setDescripcion(rs.getString("descripcion"));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar departamentos: " + e.getMessage());
        }
        return lista;
    }

    public boolean guardarDepartamento(Departamento d) {
        String sql = "INSERT INTO departamentos (nombre_departamento, descripcion) VALUES (?, ?)";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setString(1, d.getNombreDepartamento());
            ps.setString(2, d.getDescripcion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar departamento: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarDepartamento(Departamento d) {
        String sql = "UPDATE departamentos SET nombre_departamento = ?, descripcion = ? WHERE id_departamento = ?";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setString(1, d.getNombreDepartamento());
            ps.setString(2, d.getDescripcion());
            ps.setInt(3, d.getIdDepartamento());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar departamento: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarDepartamento(int idDepartamento) {
        String sql = "DELETE FROM departamentos WHERE id_departamento = ?";

        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ps.setInt(1, idDepartamento);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar departamento: " + e.getMessage());
            return false;
        }
    }
}
