package main.java.com.pbcorporations.gestion.recursos.humanos.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Asistencia;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Detalles_nomina;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.EmpleadoRepository;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.NominasRepository;

/**
 *
 * @author Marbin Aquino
 */
public class NominasService {
    
    private NominasRepository nominasRepository;
    private EmpleadoRepository empleadoRepository;
    
    public NominasService(){
        
    }
    public NominasService(NominasRepository nominasRepository, EmpleadoRepository empleadoRepository){
        this.nominasRepository = nominasRepository;
        this.empleadoRepository = empleadoRepository;
        
    }
    
    public List<Detalles_nomina> listarDetallesNomina() throws SQLException {
        List<Detalles_nomina> listaNominas = new ArrayList<>();
        listaNominas = nominasRepository.listarTodos();
        return listaNominas;
    }
    
    public void eliminarDetalleNomina(Detalles_nomina dtnm) throws SQLException {
        nominasRepository.eliminarDetalleNomina(dtnm);
    }
    
    
    
 public boolean crearNomina(int periodoMes, int periodoAnio) {
    try {
        List<EmpleadoModel> empleados = empleadoRepository.listarTodos();
        if (empleados == null || empleados.isEmpty()) {
            return false;
        }

        BigDecimal horasMensualesEstandar = new BigDecimal("160.00");
        BigDecimal porcentajeIgss = new BigDecimal("0.0483");
        boolean seCrearonNuevas = false;

        for (EmpleadoModel empleado : empleados) {
            boolean yaTieneNomina = nominasRepository.validar(empleado.getIdEmpleado(), periodoMes, periodoAnio);
            if (yaTieneNomina) {
                continue; 
            }

            BigDecimal salarioBase = empleado.getSalarioBase();
            if (salarioBase == null) {
                salarioBase = BigDecimal.ZERO;
            }

            BigDecimal horasExtra = BigDecimal.ZERO;
            BigDecimal valorHoraExtra = BigDecimal.ZERO;
            if (horasMensualesEstandar.compareTo(BigDecimal.ZERO) > 0) {
                valorHoraExtra = salarioBase.divide(horasMensualesEstandar, 4, RoundingMode.HALF_UP);
            }
            
            BigDecimal montoHorasExtra = horasExtra.multiply(valorHoraExtra).setScale(2, RoundingMode.HALF_UP);
            BigDecimal descuentoIgss = salarioBase.multiply(porcentajeIgss).setScale(2, RoundingMode.HALF_UP);
            BigDecimal descuentoIsr = BigDecimal.ZERO; 

            BigDecimal salarioNeto = salarioBase.add(montoHorasExtra).subtract(descuentoIgss).subtract(descuentoIsr);
            if (salarioNeto.compareTo(BigDecimal.ZERO) < 0) {
                salarioNeto = BigDecimal.ZERO;
            }

            Detalles_nomina detalle = new Detalles_nomina();
            detalle.setDias_trabajados(30);
            detalle.setSalario_base(salarioBase);
            detalle.setMonto_horas_extra(montoHorasExtra.doubleValue());
            detalle.setDescuento_igss(descuentoIgss.doubleValue());
            detalle.setDescuento_isr(descuentoIsr.doubleValue());
            detalle.setSalario_neta(salarioNeto.doubleValue());
            detalle.setPeriodo_anio(periodoAnio);
            detalle.setPeriodo_mes(periodoMes);
            detalle.setFecha_generacion(LocalDateTime.now());
            detalle.setMonto_total_planilla(salarioNeto.doubleValue()); // O el cálculo global que lleves
            detalle.setEstado("generada");
            
            nominasRepository.guardarNomina(detalle, empleado.getIdEmpleado());
            seCrearonNuevas = true;
        }

        return seCrearonNuevas;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
  
  public boolean editarNomina(String idEmpleado, int periodoMes, int periodoAnio, String NuevoEstado) throws SQLException{
      Long longId =nominasRepository.obtenerIdNomina(idEmpleado, periodoMes, periodoAnio);
      if(longId != null){
      int idNomina = longId.intValue();
    
      boolean editar;
      editar = nominasRepository.actualizarEstadoNomina(idNomina, NuevoEstado);
      return editar;
      }else{
          return false;
      }
  }
  
   public List<EmpleadoModel> obtenerListaDeNominas() throws SQLException {
        return nominasRepository.listarPorPagar();
    }
    
}
