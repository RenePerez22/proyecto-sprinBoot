package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;


public interface JugadorRepository extends JpaRepository<Jugador, Long> {
	List<Jugador> findByEquipoId(Long equipoId);
    Optional<Jugador> findByDni(String dni);
    List<Jugador> findByEquipo(Equipo equipo);
    long countByEquipo(Equipo equipo);
    List<Jugador> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
    List<Jugador> findByPosicionIgnoreCase(String posicion);
    List<Jugador> findByEquipoAndPosicion(Equipo equipo, String posicion);
    
    @Query("SELECT e.nombre, COUNT(j) FROM Equipo e JOIN e.jugadores j GROUP BY e.nombre")
    List<Object[]> contarJugadoresPorEquipo();
    
    @Query("SELECT COUNT(j) FROM Jugador j WHERE LOWER(j.estado) = LOWER('ACTIVO')")
    long countJugadoresActivos();
}
