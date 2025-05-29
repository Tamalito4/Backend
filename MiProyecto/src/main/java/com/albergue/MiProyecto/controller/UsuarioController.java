package com.albergue.MiProyecto.controller;

import com.albergue.MiProyecto.model.Usuario;
import com.albergue.MiProyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @PostMapping("/registro")
    public Map<String, Object> registrar(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();

        if (usuarioRepo.findByUsuario(usuario.getUsuario()).isPresent()) {
            response.put("status", "error");
            response.put("message", "El usuario ya existe.");
            return response;
        }

        usuario.setRol("USER"); // Por defecto
        usuarioRepo.save(usuario);

        response.put("status", "ok");
        response.put("message", "Registro exitoso.");
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> datos) {
        String usuario = datos.get("usuario");
        String contrasena = datos.get("contrasena");
        Map<String, Object> response = new HashMap<>();

        return usuarioRepo.findByUsuarioAndContrasena(usuario, contrasena)
                .map(u -> {
                    response.put("status", "ok");
                    response.put("rol", u.getRol());
                    response.put("message", "Inicio de sesión exitoso.");
                    return response;
                })
                .orElseGet(() -> {
                    response.put("status", "error");
                    response.put("message", "Credenciales incorrectas.");
                    return response;
                });
    }
}
