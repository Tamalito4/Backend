package com.albergue.MiProyecto.repository;

import com.albergue.MiProyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuarioAndContrasena(String usuario, String contrasena);
    Optional<Usuario> findByUsuario(String usuario);
}