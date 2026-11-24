package com.dwes.alumnos.repositorio;

import com.dwes.alumnos.domain.Alumno;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@ConditionalOnProperty(name = "app.repositorio", havingValue = "bbdd")
public interface AlumnoRepositorioJpaImpl extends JpaRepository<Alumno, Long>, AlumnoRepositorio{
}
