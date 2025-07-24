package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;


import java.time.LocalDate;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;

public interface TemporadaRepository extends JpaRepository<Temporada, Long> {
	Optional<Temporada> findByNombre(String nombre);
	
	 
	   @Query("SELECT COUNT(t) FROM Temporada t WHERE t.fechaFin >= CURRENT_DATE")
	    long countTemporadasActivas();
}
