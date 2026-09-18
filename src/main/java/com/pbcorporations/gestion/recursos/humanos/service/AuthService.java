package main.java.com.pbcorporations.gestion.recursos.humanos.service;

import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.pbcorporations.abarroteria.kinal.security.jbcrypt.BCrypt;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Rol;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Usuario;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.EmpleadoRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.RolRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.UsuarioRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.util.UserSession;

public class AuthService {

    private final UsuarioRepository uRepository;
    private final RolRepository rRepository;
    private final EmpleadoRepository eRepository;

    public AuthService(UsuarioRepository uRepository, RolRepository rRepository, EmpleadoRepository eRepository) {
        this.uRepository = uRepository;
        this.rRepository = rRepository;
        this.eRepository = eRepository;
    }

    public boolean login(String username, String pass) {
        if (username == null || username.trim().isEmpty()
                || pass == null || pass.trim().isEmpty()) {
            System.out.println("No se puede ingresar campos vacíos, revise su usuario y su contraseña.");
            return false;
        }

        Usuario usuario = uRepository.findByUsername(username);
        
        if (usuario == null) {
            System.out.println("No se encontró el usuario");
            return false;
        }
        
        if ("bloqueado".equalsIgnoreCase(usuario.getEstado())) {
            System.out.println("Usuario bloqueado, inicio de sesión denegado");
            return false;
        }

        if (usuario.getPasswordHash() == null) {
            throw new RuntimeException("No se logró terminar la operación");
        }
        
        if (BCrypt.checkpw(pass, usuario.getPasswordHash())) {
            Rol rol = rRepository.findById(usuario.getIdRol());
            String nombreRol = (rol != null) ? rol.getNombreRol() : "usuario";
            
            UserSession.getInstance().login(usuario, nombreRol);
            
            System.out.println("Inicio de sesión realizado con exito. Rol: " + nombreRol);
            return true;
        } else {
            System.out.println("Contraseña incorrecta");
            return false;
        }
    }
    
    public boolean registerUser(String username, String pass, Rol rol, EmpleadoModel empleado) {
        if (username == null || username.trim().isEmpty() 
                || pass == null || pass.trim().isEmpty()
                || rol == null || empleado == null) {
            System.out.println("No se puede ingresar campos vacios");
            return false;
        } 
        
        String passHash = BCrypt.hashpw(pass.trim(), BCrypt.gensalt(12));
        
        Usuario nuevoUsuario = new Usuario(
                UUID.randomUUID().toString(),
                username.trim(),
                passHash,
                "activo",
                rol.getIdRol(),
                empleado.getIdEmpleado()
        );
        
        return uRepository.register(nuevoUsuario);
    }
    
    public ObservableList<Rol> getRoles() {
        return FXCollections.observableArrayList(rRepository.getAll());
    }

    public ObservableList<EmpleadoModel> getEmpleadosSinUsuario() {
        return FXCollections.observableArrayList(eRepository.noUser());
    }
}
