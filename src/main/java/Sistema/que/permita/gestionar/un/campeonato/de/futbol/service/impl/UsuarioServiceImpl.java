package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Rol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.RolRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.UsuarioRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;



@Service
public class UsuarioServiceImpl implements UsuarioService {
	private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

 
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, 
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
this.usuarioRepository = usuarioRepository;
this.rolRepository = rolRepository;
this.passwordEncoder = passwordEncoder; // Inyecta PasswordEncoder
}

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("Correo ya registrado");
        }
        Rol rol = rolRepository.findById(usuario.getRol().getId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no válido"));
        usuario.setRol(rol);
        // Solo hashear para nuevos usuarios
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); 
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);
        usuarioExistente.setNombreCompleto(usuario.getNombreCompleto());
        usuarioExistente.setCorreo(usuario.getCorreo());

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
        	 // Encripta la nueva contraseña
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        Rol rol = rolRepository.findById(usuario.getRol().getId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no válido"));
        usuarioExistente.setRol(rol);
        usuarioExistente.setActivo(usuario.isActivo());

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarPorCorreoYPassword(String correo, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Verifica si la contraseña está hasheada (nuevos) o en texto plano (antiguos)
            if (usuario.getPassword().startsWith("$2a$")) {
                // Compara contraseña hasheada
                if (passwordEncoder.matches(password, usuario.getPassword())) {
                    return Optional.of(usuario);
                }
            } else {
                // Compara contraseña en texto plano (usuarios antiguos)
                if (usuario.getPassword().equals(password)) {
                    return Optional.of(usuario);
                }
            }
        }
        return Optional.empty();
    }
}