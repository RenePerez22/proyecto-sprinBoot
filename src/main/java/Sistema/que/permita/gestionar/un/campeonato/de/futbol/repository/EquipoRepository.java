package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
	 List<Equipo> findByTemporadaId(Long temporadaId);
	 List<Equipo> findByTemporadaNombreContainingIgnoreCase(String nombreTemporada);
	 Optional<Equipo> findByNombreIgnoreCase(String nombre);

	 long count(); 
}
