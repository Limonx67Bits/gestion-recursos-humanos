package main.java.com.pbcorporations.gestion.recursos.humanos.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Asistencia;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.AsistenciaRepository;

public class AsistenciaService {

    private final AsistenciaRepository repository;

    public AsistenciaService(AsistenciaRepository repository) {
        this.repository = repository;
    }

    public boolean marcarEntrada(String idEmpleado) {
        if (idEmpleado == null || idEmpleado.isBlank()) {
            throw new RuntimeException("Selecciona un empleado antes de marcar entrada");
        }

        LocalDate hoy = LocalDate.now();

        Asistencia abierta = repository.buscarAsistenciaAbierta(idEmpleado, hoy);
        if (abierta != null) {
            throw new RuntimeException("Este empleado ya tiene una entrada marcada hoy sin salida registrada");
        }

        Asistencia nueva = new Asistencia(idEmpleado, hoy, LocalTime.now(), "presente");
        return repository.marcarEntrada(nueva);
    }

    public boolean marcarSalida(String idEmpleado) {
        if (idEmpleado == null || idEmpleado.isBlank()) {
            throw new RuntimeException("Selecciona un empleado antes de marcar salida");
        }

        LocalDate hoy = LocalDate.now();
        Asistencia abierta = repository.buscarAsistenciaAbierta(idEmpleado, hoy);

        if (abierta == null) {
            throw new RuntimeException("Este empleado no tiene una entrada abierta hoy");
        }

        return repository.marcarSalida(abierta.getIdAsistencia(), LocalTime.now());
    }

    public List<Asistencia> listarAsistenciasDeHoy() {
        return repository.listarAsistenciasPorFecha(LocalDate.now());
    }

    public List<Asistencia> listarAsistenciasParaHorasExtra() {
        return repository.listarAsistenciasParaHorasExtra();
    }

    public boolean registrarHorasExtra(int idAsistencia, double horasExtra) {
        if (idAsistencia <= 0) {
            throw new RuntimeException("Selecciona un registro de asistencia de la tabla");
        }
        if (horasExtra < 0) {
            throw new RuntimeException("Las horas extra no pueden ser negativas");
        }
        if (horasExtra > 12) {
            throw new RuntimeException("Revisa el valor: no puede haber más de 12 horas extra en un día");
        }
        return repository.actualizarHorasExtra(idAsistencia, horasExtra);
    }
}
