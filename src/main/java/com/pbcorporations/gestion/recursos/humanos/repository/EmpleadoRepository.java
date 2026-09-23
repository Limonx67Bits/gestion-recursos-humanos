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
        String sql = "SELECT e.id_empleado, e.dpi, e.primer_nombre, e.segundo_nombre, e.primer_apellido, e.segundo_apellido, "
                + "e.telefono, e.correo, e.tipo_vialidad, e.numero_vialidad, e.numero_casa_lote, e.colonia_barrio, "
                + "e.zona, e.municipio, e.departamento AS depto_dir, e.codigo_postal, "
                + "p.nombre_puesto, d.nombre_departamento, p.salario_base, e.estado "
                + "FROM empleados e "
                + "INNER JOIN puestos p ON e.id_puesto = p.id_puesto "
                + "INNER JOIN departamentos d ON p.id_departamento = d.id_departamento";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EmpleadoModel emp = new EmpleadoModel(
                        rs.getString("id_empleado"),
                        rs.getString("dpi"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("tipo_vialidad"),
                        rs.getString("numero_vialidad"),
                        rs.getString("numero_casa_lote"),
                        rs.getString("colonia_barrio"),
                        rs.getInt("zona"),
                        rs.getString("municipio"),
                        rs.getString("depto_dir"),
                        rs.getString("codigo_postal"),
                        rs.getString("nombre_puesto"),
                        rs.getString("nombre_departamento"),
                        rs.getBigDecimal("salario_base"),
                        rs.getString("estado")
                );
                lista.add(emp);
            }
        }
        return lista;
    }

    public boolean guardar(EmpleadoModel emp, int idPuesto) throws SQLException {
        String sql = "INSERT INTO empleados ("
                + "id_empleado, dpi, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, telefono, correo, "
                + "tipo_vialidad, numero_vialidad, numero_casa_lote, colonia_barrio, zona, "
                + "municipio, departamento, codigo_postal, fecha_contratacion, estado, id_puesto"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), 'activo', ?)";

        String nuevoId = (emp.getIdEmpleado() != null && !emp.getIdEmpleado().isEmpty())
                ? emp.getIdEmpleado()
                : UUID.randomUUID().toString();

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoId);
            ps.setString(2, emp.getDpi());
            ps.setString(3, emp.getPrimerNombre());
            ps.setString(4, emp.getSegundoNombre() != null ? emp.getSegundoNombre() : "");
            ps.setString(5, emp.getPrimerApellido());
            ps.setString(6, emp.getSegundoApellido() != null ? emp.getSegundoApellido() : "");
            ps.setString(7, emp.getTelefono());
            ps.setString(8, emp.getCorreo());
            ps.setString(9, emp.getTipoVialidad());
            ps.setString(10, emp.getNumeroVialidad());
            ps.setString(11, emp.getNumeroCasaLote());
            ps.setString(12, emp.getColoniaBarrio());
            ps.setInt(13, emp.getZona());
            ps.setString(14, emp.getMunicipio());
            ps.setString(15, emp.getDepartamentoDir());
            ps.setString(16, emp.getCodigoPostal());
            ps.setInt(17, idPuesto);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(EmpleadoModel emp, int idPuesto) throws SQLException {
        String sql = "UPDATE empleados SET dpi = ?, primer_nombre = ?, segundo_nombre = ?, "
                + "primer_apellido = ?, segundo_apellido = ?, telefono = ?, correo = ?, "
                + "tipo_vialidad = ?, numero_vialidad = ?, numero_casa_lote = ?, "
                + "colonia_barrio = ?, zona = ?, municipio = ?, departamento = ?, "
                + "codigo_postal = ?, id_puesto = ? WHERE id_empleado = ?";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getDpi());
            ps.setString(2, emp.getPrimerNombre());
            ps.setString(3, emp.getSegundoNombre() != null ? emp.getSegundoNombre() : "");
            ps.setString(4, emp.getPrimerApellido());
            ps.setString(5, emp.getSegundoApellido() != null ? emp.getSegundoApellido() : "");
            ps.setString(6, emp.getTelefono());
            ps.setString(7, emp.getCorreo());
            ps.setString(8, emp.getTipoVialidad());
            ps.setString(9, emp.getNumeroVialidad());
            ps.setString(10, emp.getNumeroCasaLote());
            ps.setString(11, emp.getColoniaBarrio());
            ps.setInt(12, emp.getZona());
            ps.setString(13, emp.getMunicipio());
            ps.setString(14, emp.getDepartamentoDir());
            ps.setString(15, emp.getCodigoPostal());
            ps.setInt(16, idPuesto);
            ps.setString(17, emp.getIdEmpleado());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(String idEmpleado) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idEmpleado);
            return ps.executeUpdate() > 0;
        }
    }

    public int obtenerIdPuestoPorNombre(String nombrePuesto) throws SQLException {
        String sql = "SELECT id_puesto FROM puestos WHERE nombre_puesto = ?";
        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombrePuesto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_puesto");
                }
            }
        }
        return -1;
    }

    public List<EmpleadoModel> noUser() {
        List<EmpleadoModel> lista = new ArrayList<>();
        String sql = "SELECT e.id_empleado, e.dpi, e.primer_nombre, e.segundo_nombre, e.primer_apellido, e.segundo_apellido, "
                + "e.telefono, e.correo, e.tipo_vialidad, e.numero_vialidad, e.numero_casa_lote, e.colonia_barrio, "
                + "e.zona, e.municipio, e.departamento AS depto_dir, e.codigo_postal, "
                + "p.nombre_puesto, d.nombre_departamento, p.salario_base, e.estado "
                + "FROM empleados e "
                + "INNER JOIN puestos p ON e.id_puesto = p.id_puesto "
                + "INNER JOIN departamentos d ON p.id_departamento = d.id_departamento "
                + "LEFT JOIN usuarios u ON e.id_empleado = u.id_empleado "
                + "WHERE u.id_usuario IS NULL";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                EmpleadoModel emp = new EmpleadoModel(
                        rs.getString("id_empleado"),
                        rs.getString("dpi"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("tipo_vialidad"),
                        rs.getString("numero_vialidad"),
                        rs.getString("numero_casa_lote"),
                        rs.getString("colonia_barrio"),
                        rs.getInt("zona"),
                        rs.getString("municipio"),
                        rs.getString("depto_dir"),
                        rs.getString("codigo_postal"),
                        rs.getString("nombre_puesto"),
                        rs.getString("nombre_departamento"),
                        rs.getBigDecimal("salario_base"),
                        rs.getString("estado")
                );
                lista.add(emp);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener empleados sin usuario: " + e.getMessage());
        }
        return lista;
    }
}
