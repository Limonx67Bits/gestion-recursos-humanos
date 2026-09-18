package main.java.com.pbcorporations.gestion.recursos.humanos.model;

public class EmpleadoModel {
    private String idEmpleado;
    private String nombre;
    private String dpi;
    private String telefono;
    private String puesto;
    private String departamento;

    public EmpleadoModel() {}

    public EmpleadoModel(String idEmpleado, String nombre, String dpi, String telefono, String puesto, String departamento) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.dpi = dpi;
        this.telefono = telefono;
        this.puesto = puesto;
        this.departamento = departamento;
    }

    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    @Override
    public String toString() {
        return "EMPLEADO " + nombre + " || DPI " + dpi;
    }
}