package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

	List<Partido> findByTemporadaId(Long temporadaId);
    List<Partido> findByEquipoLocalIdOrEquipoVisitanteId(Long equipoLocalId, Long equipoVisitanteId);
	List<Partido> findByEstadoIgnoreCase(String estado);
	List<Partido> findByTemporadaIdOrderByFechaAsc(Long temporadaId);
	List<Partido> findByFecha(LocalDate fecha);

	
	@Query("SELECT COUNT(p) FROM Partido p WHERE LOWER(p.estado) = LOWER('Pendiente')")
    long countPartidosProgramados();
	

}
