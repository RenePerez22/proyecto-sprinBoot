package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
            .username(usuario.getCorreo())
            .password(usuario.getPassword())
            .disabled(!usuario.isActivo()) // Verifica si el usuario está activo
            .roles(obtenerRol(usuario.getRol().getId()))
            .build();
    }

    private String obtenerRol(Long rolId) {
        // Asigna roles basado en el ID de rol
        if (rolId == 1) return "ADMIN";
        if (rolId == 2) return "JUGADOR";
        return "USER";
    }
}