package main.java.com.pbcorporations.gestion.recursos.humanos.service;

import java.sql.SQLException;
import java.util.List;
import main.java.com.pbcorporations.gestion.recursos.humanos.model.EmpleadoModel;
import main.java.com.pbcorporations.gestion.recursos.humanos.repository.EmpleadoRepository;

public class EmpleadoService {

    private final EmpleadoRepository repository = new EmpleadoRepository();

    public List<EmpleadoModel> obtenerEmpleados() throws SQLException {
        return repository.listarTodos();
    }

    public boolean guardarEmpleado(EmpleadoModel emp) throws SQLException {
 
        if (emp.getNombre() == null || emp.getNombre().trim().isEmpty() ||
            emp.getDpi() == null || emp.getDpi().trim().isEmpty() ||
            emp.getTelefono() == null || emp.getTelefono().trim().isEmpty() ||
            emp.getPuesto() == null || emp.getPuesto().trim().isEmpty() ||
            emp.getDepartamento() == null || emp.getDepartamento().trim().isEmpty()) {
            
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }


        String regexLetras = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        
        if (!emp.getNombre().matches(regexLetras)) {
            throw new IllegalArgumentException("El campo Nombre solo debe contener letras.");
        }
        if (!emp.getPuesto().matches(regexLetras)) {
            throw new IllegalArgumentException("El campo Puesto solo debe contener letras.");
        }
        if (!emp.getDepartamento().matches(regexLetras)) {
            throw new IllegalArgumentException("El campo Departamento solo debe contener letras.");
        }

      
        String regexNumeros = "^[0-9]+$";
        
        if (!emp.getDpi().matches(regexNumeros)) {
            throw new IllegalArgumentException("El campo DPI solo debe contener números.");
        }
        if (!emp.getTelefono().matches(regexNumeros)) {
            throw new IllegalArgumentException("El campo Teléfono solo debe contener números.");
        }

        return repository.guardar(emp);
    }

    public boolean eliminarEmpleado(String idEmpleado) throws SQLException {
        return repository.eliminar(idEmpleado);
    }
}