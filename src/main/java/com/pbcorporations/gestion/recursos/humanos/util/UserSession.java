package main.java.com.pbcorporations.gestion.recursos.humanos.util;

import main.java.com.pbcorporations.gestion.recursos.humanos.model.Usuario;

public class UserSession {
    
    private static UserSession instance;
    private Usuario usuarioActual;
    private String nombreRol;
    
    private UserSession(){
    }
    
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    public void login(Usuario usuario, String nombreRol) {
        this.usuarioActual = usuario;
        this.nombreRol = nombreRol;
    }
    
    public void logout() {
        this.usuarioActual = null;
        this.nombreRol = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public String getNombreRol() {
        return nombreRol;
    }
    
    public boolean isAdmin() {
        if (usuarioActual != null) {
            return usuarioActual.getIdRol() == 1;
        }
        return "administrador".equalsIgnoreCase(nombreRol);
    }
}
