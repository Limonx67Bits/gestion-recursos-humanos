package main.java.com.pbcorporations.gestion.recursos.humanos.repository;

import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolRepository {
    public Rol findById(int idRol) {
        String sql = "select * from roles where id_rol = ?";
        try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, idRol);
            
            try (ResultSet rs = pstm.executeQuery()) {
                
                if (rs.next()) {
                    return new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre_rol"),
                    rs.getString("descripcion"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        return null;
    }
    
    public List<Rol> getAll() {
        List<Rol> lista = new ArrayList<>();
        String sql = "select * from roles";
        try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre_rol"),
                    rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        return lista;
    }
}
