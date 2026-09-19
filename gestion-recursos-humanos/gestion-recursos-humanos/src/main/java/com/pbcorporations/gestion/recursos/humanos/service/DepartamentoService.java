package main.java.com.pbcorporations.gestion.recursos.humanos.service;

import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.Departamento;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.DepartamentoRepository;

public class DepartamentoService {

    private final DepartamentoRepository repository;

    public DepartamentoService(DepartamentoRepository repository) {
        this.repository = repository;
    }

    public List<Departamento> listarDepartamentos() {
        return repository.listarDepartamentos();
    }

    public boolean guardarDepartamento(Departamento d) {
        if (d.getNombreDepartamento() == null || d.getNombreDepartamento().isBlank()) {
            throw new RuntimeException("El nombre del departamento no puede estar vacío");
        }
        return repository.guardarDepartamento(d);
    }

    public boolean actualizarDepartamento(Departamento d) {
        if (d.getIdDepartamento() <= 0) {
            throw new RuntimeException("Selecciona un departamento de la tabla antes de actualizar");
        }
        if (d.getNombreDepartamento() == null || d.getNombreDepartamento().isBlank()) {
            throw new RuntimeException("El nombre del departamento no puede estar vacío");
        }
        return repository.actualizarDepartamento(d);
    }

    public boolean eliminarDepartamento(int idDepartamento) {
        if (idDepartamento <= 0) {
            throw new RuntimeException("Selecciona un departamento de la tabla antes de eliminar");
        }
        return repository.eliminarDepartamento(idDepartamento);
    }
}
