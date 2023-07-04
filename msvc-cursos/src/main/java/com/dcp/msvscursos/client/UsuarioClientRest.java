package com.dcp.msvscursos.client;

import com.dcp.msvscursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
//@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
@FeignClient(name = "msvc-usuarios")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios")
    List<Usuario> ontenerAlumnosporCurso(@RequestParam Iterable<Long> ids);


}
