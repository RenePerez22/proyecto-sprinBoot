package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Gol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Goleador;

public interface GolService {
	List<Gol> listarGoles();
    Gol obtenerGolPorId(Long id);
    Gol crearGol(Gol gol);
    Gol actualizarGol(Long id, Gol gol);
    void eliminarGol(Long id);
    List<Gol> listarPorPartido(Long partidoId);
    List<Gol> listarPorJugador(Long jugadorId);
    List<Gol> listarPorEquipo(Long equipoId);
    List<Goleador> obtenerTablaGoleadores(Long temporadaId);

}
