package main.java.com.pbcorporations.gestion.recursos.humanos.service;

import java.sql.SQLException;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.EmpleadoRepository;

public class EmpleadoService {

    private EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    
    
    public List<EmpleadoModel> obtenerEmpleados() throws SQLException {
        return repository.listarTodos();
    }

    public boolean guardarEmpleado(EmpleadoModel emp) throws SQLException {
        validarEmpleado(emp);

        int idPuesto = repository.obtenerIdPuestoPorNombre(emp.getPuesto());
        if (idPuesto == -1) {
            throw new IllegalArgumentException("El puesto especificado no existe en la base de datos.");
        }

        return repository.guardar(emp, idPuesto);
    }

    public boolean actualizarEmpleado(EmpleadoModel emp) throws SQLException {
        if (emp.getIdEmpleado() == null || emp.getIdEmpleado().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del empleado es requerido para actualizar.");
        }

        validarEmpleado(emp);

        int idPuesto = repository.obtenerIdPuestoPorNombre(emp.getPuesto());
        if (idPuesto == -1) {
            throw new IllegalArgumentException("El puesto especificado no existe en la base de datos.");
        }

        return repository.actualizar(emp, idPuesto);
    }

    public boolean eliminarEmpleado(String idEmpleado) throws SQLException {
        if (idEmpleado == null || idEmpleado.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un empleado válido para eliminar.");
        }
        return repository.eliminar(idEmpleado);
    }

    private void validarEmpleado(EmpleadoModel emp) {
        if (emp.getPrimerNombre() == null || emp.getPrimerNombre().trim().isEmpty() ||
            emp.getPrimerApellido() == null || emp.getPrimerApellido().trim().isEmpty() ||
            emp.getDpi() == null || emp.getDpi().trim().isEmpty() ||
            emp.getTelefono() == null || emp.getTelefono().trim().isEmpty() ||
            emp.getPuesto() == null || emp.getPuesto().trim().isEmpty()) {

            throw new IllegalArgumentException("Los campos obligatorios (Nombre, Apellido, DPI, Teléfono, Puesto) deben ser completados.");
        }

        String regexLetras = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        if (!emp.getPrimerNombre().matches(regexLetras) || !emp.getPrimerApellido().matches(regexLetras)) {
            throw new IllegalArgumentException("Los campos de Nombres y Apellidos solo deben contener letras.");
        }

        if (emp.getDepartamento() != null && !emp.getDepartamento().trim().isEmpty()) {
            if (!emp.getDepartamento().matches(regexLetras)) {
                throw new IllegalArgumentException("El campo Departamento solo debe contener letras.");
            }
        }

        String regexNumeros = "^[0-9]+$";
        if (!emp.getDpi().matches(regexNumeros)) {
            throw new IllegalArgumentException("El campo DPI solo debe contener números.");
        }
        if (!emp.getTelefono().matches(regexNumeros)) {
            throw new IllegalArgumentException("El campo Teléfono solo debe contener números.");
        }

        if (emp.getCorreo() != null && !emp.getCorreo().trim().isEmpty()) {
            String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (!emp.getCorreo().matches(regexEmail)) {
                throw new IllegalArgumentException("El formato del correo electrónico es inválido.");
            }
        }
    }
}