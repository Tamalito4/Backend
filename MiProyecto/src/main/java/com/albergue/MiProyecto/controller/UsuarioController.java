package com.albergue.MiProyecto.controller;


import com.albergue.MiProyecto.model.Usuario;
import com.albergue.MiProyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

@PostMapping("/registro")
public Map<String, Object> registrar(@RequestBody Map<String, Object> data) {
    Map<String, Object> response = new HashMap<>();
    String usuario = (String) data.get("usuario");

    if (usuarioRepository.findByUsuario(usuario).isPresent()) {
        response.put("status", "error");
        response.put("message", "El usuario ya está registrado.");
        return response;
    }

    Usuario nuevo = new Usuario();
    nuevo.setNombreCompleto((String) data.get("nombreCompleto"));
    nuevo.setLugarNacimiento((String) data.get("lugarNacimiento"));
    nuevo.setFechaNacimiento(LocalDate.parse((String) data.get("fechaNacimiento")));
    nuevo.setCorreo((String) data.get("correo"));
    nuevo.setTelefono((String) data.get("telefono"));
    nuevo.setUsuario(usuario);
    nuevo.setContrasena((String) data.get("contrasena"));

    usuarioRepository.save(nuevo);

    response.put("status", "ok");
    response.put("message", "Registro exitoso.");
    return response;
}

@PostMapping("/login")
public Map<String, Object> login(@RequestBody Map<String, String> data) {
    String usuario = data.get("usuario");
    String contrasena = data.get("contrasena");

    Map<String, Object> response = new HashMap<>();

    return usuarioRepository.findByUsuario(usuario)
            .filter(u -> u.getContrasena().equals(contrasena))
            .map(u -> {
                response.put("status", "ok");
                response.put("message", "Login exitoso.");
                return response;
            })
            .orElseGet(() -> {
                response.put("status", "error");
                response.put("message", "Usuario o contraseña incorrectos.");
                return response;
            });
}

}
