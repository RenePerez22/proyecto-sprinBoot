package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
	Optional<Rol> findByNombre(String nombre);

}
