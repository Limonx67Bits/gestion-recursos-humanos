package main.java.com.pbcorporations.gestion.recursos.humanos.service;

import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoResumen;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.EmpleadoRepository;

public class EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public List<EmpleadoResumen> listarEmpleadosActivos() {
        return repository.listarEmpleadosActivos();
    }
}
