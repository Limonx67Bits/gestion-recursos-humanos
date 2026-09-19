package main.java.com.pbcorporations.gestion.recursos.humanos.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Asistencia {

    private int idAsistencia;
    private String idEmpleado;
    private LocalDate fecha;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private double horasExtraTrabajadas;
    private String estado;

  
    private String nombreEmpleado;

    public Asistencia() {
    }

    public Asistencia(String idEmpleado, LocalDate fecha, LocalTime horaEntrada, String estado) {
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.estado = estado;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getHorasExtraTrabajadas() {
        return horasExtraTrabajadas;
    }

    public void setHorasExtraTrabajadas(double horasExtraTrabajadas) {
        this.horasExtraTrabajadas = horasExtraTrabajadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
}
