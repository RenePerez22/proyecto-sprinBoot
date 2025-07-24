package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PasswordUpdateService {
	 @Autowired
	    private UsuarioRepository usuarioRepository;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Transactional
	    public void updateAllPasswords() {
	        List<Usuario> usuarios = usuarioRepository.findAll();
	        usuarios.forEach(usuario -> {
	            String rawPassword = usuario.getPassword();
	            if (!rawPassword.startsWith("$2a$")) { // Solo actualiza si no es BCrypt
	                String encodedPassword = passwordEncoder.encode(rawPassword);
	                usuario.setPassword(encodedPassword);
	                usuarioRepository.save(usuario);
	            }
	        });
	    }
}
