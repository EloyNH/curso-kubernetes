package com.dcp.msvsusuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "msvc-cursos", url = "localhost:8002")
//@FeignClient(name = "msvc-cursos", url = "${msvc.cursos.url}")
@FeignClient(name = "msvc-cursos")
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);

}
