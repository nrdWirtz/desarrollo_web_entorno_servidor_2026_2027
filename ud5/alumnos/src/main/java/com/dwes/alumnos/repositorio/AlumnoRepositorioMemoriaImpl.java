package com.dwes.alumnos.repositorio;

import com.dwes.alumnos.domain.Alumno;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "app.repositorio", havingValue = "memoria")
public class AlumnoRepositorioMemoriaImpl implements AlumnoRepositorio {

    private final List<Alumno> alumnos = new ArrayList<>();

    private Long siguienteId = 1L;

    public AlumnoRepositorioMemoriaImpl() {
        alumnos.add(new Alumno(siguienteId++, "Ana García", 15, "anagarcia", true));
        alumnos.add(new Alumno(siguienteId++, "Luis Pérez", 16, "luisp", false));
        alumnos.add(new Alumno(siguienteId++, "Marta Ruiz", 14, "martar", true));
        alumnos.add(new Alumno(siguienteId++, "Pablo Sanz", 17, "pablos", true));
    }

    @Override
    public List<Alumno> findAll() {
        return new ArrayList<>(alumnos);
    }

    @Override
    public List<Alumno> findByNombreContainingIgnoreCase(String nombre) {
        List<Alumno> filterAlumno = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            if (alumno.getNombreUsuario().toLowerCase().contains(nombre)) {
                filterAlumno.add(alumno);
            }
        }
        return filterAlumno;
    }

    @Override
    public Optional<Alumno> findById(Long id) {
        for (Alumno alumno : alumnos) {
            if (alumno.getId().equals(id)) {
                return Optional.of(alumno);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Alumno> findByNombreUsuarioIgnoreCase(String nombreUsuario) {
        for (Alumno alumno : alumnos) {
            if (alumno.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return Optional.of(alumno);
            }
        }
        return Optional.empty();
    }

    @Override
    public Alumno save(Alumno alumno) {
        if (alumno.getId() == null) {
            alumno.setId(siguienteId++);
            alumnos.add(alumno);
        } else {
            for (int i = 0; i < alumnos.size(); i++) {
                if (alumnos.get(i).getId().equals(alumno.getId())) {
                    alumnos.set(i, alumno);
                    return alumno;
                }
            }
            alumnos.add(alumno);
        }
        return alumno;
    }

    @Override
    public void deleteById(Long id) {
        alumnos.removeIf(alumno -> alumno.getId().equals(id));
    }
}
