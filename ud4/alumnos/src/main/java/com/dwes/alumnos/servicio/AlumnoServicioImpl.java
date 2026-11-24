package com.dwes.alumnos.servicio;

import com.dwes.alumnos.domain.Alumno;
import com.dwes.alumnos.excepcion.AccionNoPermitidaException;
import com.dwes.alumnos.excepcion.AlumnoNoEncontradoException;
import com.dwes.alumnos.excepcion.NombreUsuarioDuplicadoException;
import com.dwes.alumnos.repositorio.AlumnoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServicioImpl implements AlumnoServicio {

    private final AlumnoRepositorio repositorio;

    public AlumnoServicioImpl(AlumnoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Alumno> listar(String filtroNombre) {
        if (filtroNombre == null || filtroNombre.isBlank()) {
            return repositorio.findAll();
        }
        return repositorio.findByNombreContainingIgnoreCase(filtroNombre.trim().toLowerCase());
    }

    @Override
    public Alumno buscarPorId(Long id) {

        return repositorio.findById(id)
                .orElseThrow(() -> new AlumnoNoEncontradoException(id));
    }

    @Override
    public Alumno crear(Alumno alumno) {
        comprobarNombreUsuarioLibre(alumno.getNombreUsuario(), null);
        alumno.setId(null);
        alumno.setActivo(true);
        return repositorio.save(alumno);
    }

    @Override
    public Alumno actualizar(Long id, Alumno datos) {
        Alumno alumno = buscarPorId(id);
        comprobarNombreUsuarioLibre(datos.getNombreUsuario(), id);
        alumno.setNombre(datos.getNombre());
        alumno.setEdad(datos.getEdad());
        alumno.setNombreUsuario(datos.getNombreUsuario());
        return repositorio.save(alumno);
    }

    @Override
    public Alumno activar(Long id) {
        Alumno alumno = buscarPorId(id);
        if (alumno.isActivo()) {
            throw new AccionNoPermitidaException(
                    "El alumno " + alumno.getNombre() + " ya está activo");
        }
        alumno.setActivo(true);
        return repositorio.save(alumno);
    }

    @Override
    public Alumno desactivar(Long id) {
        Alumno alumno = buscarPorId(id);
        if (!alumno.isActivo()) {
            throw new AccionNoPermitidaException(
                    "El alumno " + alumno.getNombre() + " ya está inactivo");
        }
        alumno.setActivo(false);
        return repositorio.save(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = buscarPorId(id);
        if (alumno.isActivo()) {
            throw new AccionNoPermitidaException(
                    "No se puede eliminar a " + alumno.getNombre() + " porque está activo");
        }
        repositorio.deleteById(id);
    }

    // idPropio es el id del alumno que se está editando (null en el alta):
    // un alumno puede conservar su usuario, pero no usar el de otro.
    private void comprobarNombreUsuarioLibre(String nombreUsuario, Long idPropio) {
        repositorio.findByNombreUsuarioIgnoreCase(nombreUsuario.trim())
                .ifPresent(existente -> {
                    if (!existente.getId().equals(idPropio)) {
                        throw new NombreUsuarioDuplicadoException(nombreUsuario);
                    }
                });
    }
}
