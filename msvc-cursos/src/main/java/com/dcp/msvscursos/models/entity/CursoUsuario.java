package com.dcp.msvscursos.models.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cursos_usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (!(obj instanceof CursoUsuario o)){
            return false;
        }

        return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);

    }
}
