package com.dwes.empleados.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dwes.empleados.exception.EmpleadoNoEncontradoException;
import com.dwes.empleados.exception.ReglaNegocioException;
import com.dwes.empleados.model.Empleado;
import com.dwes.empleados.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("18000");

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> listar(String busqueda) {
        if (busqueda == null || busqueda.isBlank()) {
            return empleadoRepository.findAll();
        }
        return empleadoRepository.findByNombreContainingIgnoreCase(busqueda.trim());
    }

    @Override
    public Empleado buscarPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id);
        if (empleado == null) {
            throw new EmpleadoNoEncontradoException(id);
        }
        return empleado;
    }

    @Override
    public Empleado crear(Empleado datos) {
        comprobarReglasNegocio(datos, null);

        // Se crea un objeto nuevo: el identificador lo asigna el repositorio.
        Empleado empleado = new Empleado(
                datos.getNombre(),
                datos.getEmail(),
                datos.getSalario());

        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado actualizar(Long id, Empleado datos) {
        buscarPorId(id);
        comprobarReglasNegocio(datos, id);

        // No se cambian los datos almacenados hasta superar todas las reglas.
        Empleado empleado = new Empleado(
                datos.getNombre(),
                datos.getEmail(),
                datos.getSalario());
        empleado.setId(id);

        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminar(Long id) {
        Empleado empleadoBBDD = buscarPorId(id);
        empleadoRepository.delete(empleadoBBDD);
    }

    private void comprobarReglasNegocio(Empleado empleado, Long idActual) {
        comprobarSalarioMinimo(empleado.getSalario());
        comprobarEmailDisponible(empleado.getEmail(), idActual);
    }

    private void comprobarSalarioMinimo(BigDecimal salario) {
        if (salario.compareTo(SALARIO_MINIMO) < 0) {
            throw new ReglaNegocioException("El salario no puede ser inferior a " + SALARIO_MINIMO +" €");
        }
    }

    private void comprobarEmailDisponible(String email, Long idActual) {
        Empleado empleadoConEseEmail = empleadoRepository.findByEmail(email);

        if (empleadoConEseEmail != null
                && !empleadoConEseEmail.getId().equals(idActual)) {
            throw new ReglaNegocioException("Ya existe un empleado con ese email");
        }
    }

}
