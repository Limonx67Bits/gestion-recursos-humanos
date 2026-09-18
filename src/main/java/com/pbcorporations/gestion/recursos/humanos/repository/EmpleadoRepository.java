package main.java.com.pbcorporations.gestion.recursos.humanos.repository;

import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmpleadoRepository {

    public List<EmpleadoModel> listarTodos() throws SQLException {
        List<EmpleadoModel> lista = new ArrayList<>();
        String sql = "SELECT id_empleado, nombre, dpi, telefono, puesto, departamento FROM empleados";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                EmpleadoModel emp = new EmpleadoModel(
                    rs.getString("id_empleado"),
                    rs.getString("nombre"),
                    rs.getString("dpi"),
                    rs.getString("telefono"),
                    rs.getString("puesto"),
                    rs.getString("departamento")
                );
                lista.add(emp);
            }
        }
        return lista;
    }

    public boolean guardar(EmpleadoModel emp) throws SQLException {
        String sql = "INSERT INTO empleados (id_empleado, nombre, dpi, telefono, puesto, departamento) VALUES (?, ?, ?, ?, ?, ?)";
        String nuevoId = UUID.randomUUID().toString();
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoId);
            ps.setString(2, emp.getNombre());
            ps.setString(3, emp.getDpi());
            ps.setString(4, emp.getTelefono());
            ps.setString(5, emp.getPuesto());
            ps.setString(6, emp.getDepartamento());
            
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(EmpleadoModel emp) throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, dpi = ?, telefono = ?, puesto = ?, departamento = ? WHERE id_empleado = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getDpi());
            ps.setString(3, emp.getTelefono());
            ps.setString(4, emp.getPuesto());
            ps.setString(5, emp.getDepartamento());
            ps.setString(6, emp.getIdEmpleado());
            
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(String idEmpleado) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idEmpleado);
            return ps.executeUpdate() > 0;
        }
    }
    
    public List<EmpleadoModel> noUser() {
        List<EmpleadoModel> lista = new ArrayList<>();
        String sql = "select e.* from empleados as e "
                + "left join usuarios as u on e.id_empleado = u.id_empleado "
                + "where u.id_usuario is null";
        try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new EmpleadoModel(
                rs.getString("id_empleado"),
                rs.getString("primer_nombre"),
                rs.getString("dpi"),
                rs.getString("telefono"),
                rs.getString("id_puesto"),
                rs.getString("departamento")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        return lista;
    }
}