package main.java.com.pbcorporations.gestion.recursos.humanos.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Detalles_nomina;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;


/**
 *
 * @author Fernafloo
 * 
 */
public class NominasRepository {
    
    private Connection conexion;
    
    public NominasRepository(Connection conexion){
        this.conexion = conexion;
    }
    
    
    public List<Detalles_nomina> listarTodos() throws SQLException {
        List<Detalles_nomina> lista = new ArrayList<>();
        String sql = "Select dn.id_detalle, dn.dias_trabajados, dn.salario_base, dn.monto_horas_extra, dn.descuento_igss, dn.descuento_isr, dn.salario_neto,\n" +
"				n.periodo_mes, n.periodo_anio, n.fecha_generacion, n.monto_total_planilla, n.estado\n" +
"                FROM detalles_nomina dn\n" +
"                INNER JOIN nominas n ON n.id_nomina = dn.id_nomina;";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Detalles_nomina det = new Detalles_nomina(
                        rs.getInt("id_detalle"),
                        rs.getInt("dias_trabajados"),
                        rs.getBigDecimal("salario_base"),
                        rs.getDouble("monto_horas_extra"),
                        rs.getDouble("descuento_igss"),
                        rs.getDouble("descuento_isr"),
                        rs.getDouble("salario_neto"),
                        rs.getInt("periodo_mes"),
                        rs.getInt("periodo_anio"),
                        rs.getObject("fecha_generacion", LocalDateTime.class),
                        rs.getDouble("monto_total_planilla"),
                        rs.getString("estado")
                );
                lista.add(det);
            }
        }
        return lista;
    }
    
    
   public void guardarNomina(Detalles_nomina dtnm, String id_empleado) throws SQLException {
    
    String sqlNomina = "INSERT INTO nominas (periodo_mes, periodo_anio, fecha_generacion, monto_total_planilla, estado) VALUES (?, ?, NOW(), ?, ?)";
    String sqlDetalle = "INSERT INTO detalles_nomina (id_nomina, id_empleado, dias_trabajados, salario_base, monto_horas_extra, descuento_igss, descuento_isr, salario_neto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DataBaseConnection.getConnection()) {
      
        conn.setAutoCommit(false);

        long idNominaGenerada = 0;

        
      try (PreparedStatement psNomina = conn.prepareStatement(sqlNomina, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            psNomina.setInt(1, dtnm.getPeriodo_mes());
            psNomina.setInt(2, dtnm.getPeriodo_anio());
            psNomina.setDouble(3, dtnm.getMonto_total_planilla());
            psNomina.setString(4, dtnm.getEstado());
            
            psNomina.executeUpdate();

           
            try (ResultSet rs = psNomina.getGeneratedKeys()) {
                if (rs.next()) {
                    idNominaGenerada = rs.getLong(1);
                }
            }
        }

    
        try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {
            psDetalle.setLong(1, idNominaGenerada); 
            psDetalle.setString(2, id_empleado);
            psDetalle.setInt(3, dtnm.getDias_trabajados());
            psDetalle.setBigDecimal(4, dtnm.getSalario_base());
            psDetalle.setDouble(5, dtnm.getMonto_horas_extra());
            psDetalle.setDouble(6, dtnm.getDescuento_igss());
            psDetalle.setDouble(7, dtnm.getDescuento_isr());
            psDetalle.setDouble(8, dtnm.getSalario_neta());

            psDetalle.executeUpdate();
        }

        
        conn.commit();

    } catch (SQLException e) {
       
        e.printStackTrace();
        throw e;
    }
}
   
   public void eliminarDetalleNomina(Detalles_nomina dtnm) throws SQLException {
    String sql = "DELETE FROM detalles_nomina WHERE id_detalle = ?";

    try (Connection conn = DataBaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        
        ps.setInt(1, dtnm.getId_detalle());
        
        
        int filasAfectadas = ps.executeUpdate();
        
        if (filasAfectadas >  0) {
            System.out.println("El detalle de nómina fue eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún registro con ese ID.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
   
    public boolean validar(String idEmpleado, int periodoMes, int periodoAnio) {
    String query = "SELECT COUNT(*) FROM detalles_nomina d " +
                   "JOIN nominas n ON d.id_nomina = n.id_nomina " +
                   "WHERE d.id_empleado = ? AND n.periodo_mes = ? AND n.periodo_anio = ?";

    try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
        pstmt.setString(1, idEmpleado);
        pstmt.setInt(2, periodoMes);
        pstmt.setInt(3, periodoAnio);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; 
}
    
    public boolean actualizarEstadoNomina(int idNomina, String nuevoEstado) throws SQLException {
        String sql = "UPDATE nominas SET estado = ? WHERE id_nomina = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idNomina);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Long obtenerIdNomina(String idEmpleado, int periodoMes, int periodoAnio) {
        String sql = "SELECT d.id_nomina FROM detalles_nomina d " +
                     "JOIN nominas n ON d.id_nomina = n.id_nomina " +
                     "WHERE d.id_empleado = ? AND n.periodo_mes = ? AND n.periodo_anio = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, idEmpleado);
            pstmt.setInt(2, periodoMes);
            pstmt.setInt(3, periodoAnio);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id_nomina"); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<EmpleadoModel> listarPorPagar() throws SQLException {
    List<EmpleadoModel> lista = new ArrayList<>();
    String sql = "SELECT DISTINCT e.id_empleado, e.dpi, e.primer_nombre, e.segundo_nombre, e.primer_apellido, e.segundo_apellido, "
            + "e.telefono, e.correo, e.tipo_vialidad, e.numero_vialidad, e.numero_casa_lote, e.colonia_barrio, "
            + "e.zona, e.municipio, e.departamento AS depto_dir, e.codigo_postal, "
            + "p.nombre_puesto, d.nombre_departamento, p.salario_base, e.estado "
            + "FROM empleados e "
            + "INNER JOIN puestos p ON e.id_puesto = p.id_puesto "
            + "INNER JOIN departamentos d ON p.id_departamento = d.id_departamento "
            + "INNER JOIN detalles_nomina dn ON e.id_empleado = dn.id_empleado "
            + "INNER JOIN nominas n ON dn.id_nomina = n.id_nomina "
            + "WHERE n.estado = 'generada'";

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
    
}
