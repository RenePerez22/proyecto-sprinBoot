package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    
}
