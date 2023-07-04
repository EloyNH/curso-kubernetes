package com.dcp.msvscursos.models.entity;

import com.dcp.msvscursos.models.Usuario;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;

    @Transient
    List<Usuario> usuarios;

    public Curso() {
        this.cursoUsuarios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void addCursoUsuario(CursoUsuario usuario) {
        this.cursoUsuarios.add(usuario);
    }

    public void removeCursoUsuario(CursoUsuario usuario) {
        this.cursoUsuarios.remove(usuario);
    }
}
