package main.java.com.pbcorporations.gestion.recursos.humanos.repository;

import main.java.com.pbcorporations.gestion.recursos.humanos.config.DataBaseConnection;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
    public Usuario findByUsername(String username) {
        String sql = "select * from usuarios where username = ?";
        try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, username);
            
            try (ResultSet rs = pstm.executeQuery()) {
                
                if (rs.next()) {
                    return new Usuario(
                    rs.getString("id_usuario"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("estado"),
                    rs.getInt("id_rol"),
                    rs.getString("id_empleado"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        return null;
    }
    
    public boolean register(Usuario usuario) {
        String sql = "insert into usuarios values(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement pstm = conn.prepareStatement(sql);) {
            pstm.setString(1, usuario.getIdUsuario());
            pstm.setString(2, usuario.getUsername());
            pstm.setString(3, usuario.getPasswordHash());
            pstm.setString(4, usuario.getEstado());
            pstm.setInt(5, usuario.getIdRol());
            pstm.setString(6, usuario.getIdEmpleado());
            
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
            return false;
        }
    }
}
