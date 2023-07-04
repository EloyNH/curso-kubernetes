package com.dcp.msvscursos.service;

import com.dcp.msvscursos.models.Usuario;
import com.dcp.msvscursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();

    Optional<Curso> porId(Long id);
    Optional<Curso> porIdConUsuarios(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long idCurso);
    Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long idCurso);
}
