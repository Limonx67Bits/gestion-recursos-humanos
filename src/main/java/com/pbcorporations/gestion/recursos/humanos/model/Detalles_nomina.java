package main.java.com.pbcorporations.gestion.recursos.humanos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Koito Yuu
 */

public class Detalles_nomina {
    
    private int id_detalle;
    private int id_nomina;
    private String id_empleado;
    private int dias_trabajados;
    private BigDecimal salario_base;
    private double monto_horas_extra;
    private double descuento_igss;
    private double descuento_isr;
    private double salario_neta;
    private int periodo_mes;
    private int periodo_anio;
    private LocalDateTime fecha_generacion;
    private double monto_total_planilla;
    private String estado;
    
    
    public Detalles_nomina() {
    }

    public Detalles_nomina(int id_detalle, int dias_trabajados, BigDecimal salario_base, double monto_horas_extra, double descuento_igss, double descuento_isr, double salario_neta, int periodo_mes, int periodo_anio, LocalDateTime fecha_generacion, double monto_total_planilla, String estado) {
        this.id_detalle = id_detalle;
        this.dias_trabajados = dias_trabajados;
        this.salario_base = salario_base;
        this.monto_horas_extra = monto_horas_extra;
        this.descuento_igss = descuento_igss;
        this.descuento_isr = descuento_isr;
        this.salario_neta = salario_neta;
        this.periodo_mes = periodo_mes;
        this.periodo_anio = periodo_anio;
        this.fecha_generacion = fecha_generacion;
        this.monto_total_planilla = monto_total_planilla;
        this.estado = estado;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public int getId_nomina() {
        return id_nomina;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public int getDias_trabajados() {
        return dias_trabajados;
    }

    public BigDecimal getSalario_base() {
        return salario_base;
    }

    public double getMonto_horas_extra() {
        return monto_horas_extra;
    }

    public double getDescuento_igss() {
        return descuento_igss;
    }

    public double getDescuento_isr() {
        return descuento_isr;
    }

    public double getSalario_neta() {
        return salario_neta;
    }

    public int getPeriodo_mes() {
        return periodo_mes;
    }

    public int getPeriodo_anio() {
        return periodo_anio;
    }

    public LocalDateTime getFecha_generacion() {
        return fecha_generacion;
    }

    public double getMonto_total_planilla() {
        return monto_total_planilla;
    }

    public String getEstado() {
        return estado;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public void setId_nomina(int id_nomina) {
        this.id_nomina = id_nomina;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setDias_trabajados(int dias_trabajados) {
        this.dias_trabajados = dias_trabajados;
    }

    public void setSalario_base(BigDecimal salario_base) {
        this.salario_base = salario_base;
    }

    public void setMonto_horas_extra(double monto_horas_extra) {
        this.monto_horas_extra = monto_horas_extra;
    }

    public void setDescuento_igss(double descuento_igss) {
        this.descuento_igss = descuento_igss;
    }

    public void setDescuento_isr(double descuento_isr) {
        this.descuento_isr = descuento_isr;
    }

    public void setSalario_neta(double salario_neta) {
        this.salario_neta = salario_neta;
    }

    public void setPeriodo_mes(int periodo_mes) {
        this.periodo_mes = periodo_mes;
    }

    public void setPeriodo_anio(int periodo_anio) {
        this.periodo_anio = periodo_anio;
    }

    public void setFecha_generacion(LocalDateTime fecha_generacion) {
        this.fecha_generacion = fecha_generacion;
    }

    public void setMonto_total_planilla(double monto_total_planilla) {
        this.monto_total_planilla = monto_total_planilla;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
