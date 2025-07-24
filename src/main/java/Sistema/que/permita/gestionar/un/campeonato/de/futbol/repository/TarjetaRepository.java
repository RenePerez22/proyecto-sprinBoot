package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Tarjeta;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
	
	List<Tarjeta> findByJugadorId(Long jugadorId);
    List<Tarjeta> findByPartidoId(Long partidoId);
    List<Tarjeta> findByPartidoTemporadaId(Long temporadaId);
}
