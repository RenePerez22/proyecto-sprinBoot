package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Gol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;

public interface GolRepository extends JpaRepository<Gol, Long> {
	
	List<Gol> findByJugadorId(Long jugadorId);
    List<Gol> findByPartidoId(Long partidoId);
    List<Gol> findByPartidoTemporadaId(Long temporadaId);
    
    long countByJugador(Jugador jugador);
    @Query("SELECT g.jugador.nombreCompleto, COUNT(g) FROM Gol g GROUP BY g.jugador.nombreCompleto")
    List<Object[]> contarGolesPorJugador();
    
    @Query("SELECT g.partido.id, g.partido.fecha, g.partido.equipoLocal.nombre, g.partido.equipoVisitante.nombre, COUNT(g) " +
    	       "FROM Gol g GROUP BY g.partido.id, g.partido.fecha, g.partido.equipoLocal.nombre, g.partido.equipoVisitante.nombre " +
    	       "ORDER BY g.partido.fecha ASC")
    	List<Object[]> contarGolesPorPartido();
    
    @Query("SELECT COUNT(g) FROM Gol g")
    long contarTotalDeGoles();


   
}
