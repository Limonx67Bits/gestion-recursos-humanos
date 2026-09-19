package main.java.com.pbcorporations.gestion.recursos.humanos.model;


public class EmpleadoResumen {

    private String idEmpleado;
    private String nombreCompleto;
    private String nombrePuesto;
    private String nombreDepartamento;

    public EmpleadoResumen() {
    }

    public EmpleadoResumen(String idEmpleado, String nombreCompleto, String nombrePuesto, String nombreDepartamento) {
        this.idEmpleado = idEmpleado;
        this.nombreCompleto = nombreCompleto;
        this.nombrePuesto = nombrePuesto;
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    
    @Override
    public String toString() {
        return nombreCompleto + "  —  " + nombrePuesto;
    }
}
