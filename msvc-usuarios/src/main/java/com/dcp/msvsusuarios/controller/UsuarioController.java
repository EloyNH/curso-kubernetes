package com.dcp.msvsusuarios.controller;

import com.dcp.msvsusuarios.entity.Usuario;
import com.dcp.msvsusuarios.services.UsuarioService;
import javax.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class UsuarioController {

    private final UsuarioService service;
    private final ApplicationContext context;
    private final Environment environment;

    public UsuarioController(UsuarioService service, ApplicationContext context, Environment environment) {
        this.service = service;
        this.context = context;
        this.environment = environment;
    }

    @GetMapping("/crash")
    public void crash() {
        ((ConfigurableApplicationContext) context).close();
    }

    @GetMapping
    public Map<String, Object> listar() {
        Map<String, Object> body = new HashMap<>();
        body.put("usuarios", service.listar());
        body.put("podinfo", environment.getProperty("MY_POD_NAME") + ": " + environment.getProperty("MY_POD_IP"));
        body.put("texto", environment.getProperty("config.texto"));
        return body;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuario = service.porId(id);
        return (usuario.isPresent()) ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        if (!usuario.getEmail().isEmpty() && service.porEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo electrónico!"));
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult bindingResult, @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()) {
            Usuario usuarioDB = o.get();

            if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDB.getEmail()) &&
                    service.porEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo electrónico!"));
            }

            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDB));
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuario = service.porId(id);
        if (usuario.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerAlumnosporCurso(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    @GetMapping("/authorized")
    public Map<String, Object> authorized(@RequestParam String code) {
        return Collections.singletonMap("code", code);
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errores.put(fieldError.getField(), "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
