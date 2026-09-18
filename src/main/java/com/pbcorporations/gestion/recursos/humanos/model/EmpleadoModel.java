package main.java.com.pbcorporations.gestion.recursos.humanos.model;

public class EmpleadoModel {

    private String idEmpleado;
    private String dpi;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;
    private String correo;
    private String tipoVialidad;
    private String numeroVialidad;
    private String numeroCasaLote;
    private String coloniaBarrio;
    private int zona;
    private String municipio;
    private String departamentoDir;
    private String codigoPostal;
    private String puesto;
    private String departamento;
    private String direccionCompleta;
    private double salarioBase;
    private String estado;

    public EmpleadoModel() {}

    public EmpleadoModel(String idEmpleado, String dpi, String primerNombre, String segundoNombre,
                         String primerApellido, String segundoApellido, String telefono, String correo,
                         String tipoVialidad, String numeroVialidad, String numeroCasaLote,
                         String coloniaBarrio, int zona, String municipio, String departamentoDir,
                         String codigoPostal, String puesto, String departamento, double salarioBase, String estado) {
        this.idEmpleado = idEmpleado;
        this.dpi = dpi;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.correo = correo;
        this.tipoVialidad = tipoVialidad;
        this.numeroVialidad = numeroVialidad;
        this.numeroCasaLote = numeroCasaLote;
        this.coloniaBarrio = coloniaBarrio;
        this.zona = zona;
        this.municipio = municipio;
        this.departamentoDir = departamentoDir;
        this.codigoPostal = codigoPostal;
        this.puesto = puesto;
        this.departamento = departamento;
        this.salarioBase = salarioBase;
        this.estado = estado;
    }

    // Getters y Setters existentes
    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }

    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }

    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTipoVialidad() { return tipoVialidad; }
    public void setTipoVialidad(String tipoVialidad) { this.tipoVialidad = tipoVialidad; }

    public String getNumeroVialidad() { return numeroVialidad; }
    public void setNumeroVialidad(String numeroVialidad) { this.numeroVialidad = numeroVialidad; }

    public String getNumeroCasaLote() { return numeroCasaLote; }
    public void setNumeroCasaLote(String numeroCasaLote) { this.numeroCasaLote = numeroCasaLote; }

    public String getColoniaBarrio() { return coloniaBarrio; }
    public void setColoniaBarrio(String coloniaBarrio) { this.coloniaBarrio = coloniaBarrio; }

    public int getZona() { return zona; }
    public void setZona(int zona) { this.zona = zona; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getDepartamentoDir() { return departamentoDir; }
    public void setDepartamentoDir(String departamentoDir) { this.departamentoDir = departamentoDir; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getDireccionCompleta() { return direccionCompleta; }
    public void setDireccionCompleta(String direccionCompleta) { this.direccionCompleta = direccionCompleta; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Propiedades calculadas para la visualización en TableView
    public String getNombres() {
        StringBuilder sb = new StringBuilder();
        if (primerNombre != null) sb.append(primerNombre).append(" ");
        if (segundoNombre != null && !segundoNombre.isEmpty()) sb.append(segundoNombre);
        return sb.toString().trim();
    }

    public String getApellidos() {
        StringBuilder sb = new StringBuilder();
        if (primerApellido != null) sb.append(primerApellido).append(" ");
        if (segundoApellido != null && !segundoApellido.isEmpty()) sb.append(segundoApellido);
        return sb.toString().trim();
    }

    public String getNombreCompleto() {
        return (getNombres() + " " + getApellidos()).trim();
    }
}