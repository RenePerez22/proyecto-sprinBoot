package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;
import java.util.Map;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.TablaPosicion;

public interface PartidoService {
	List<Partido> listarPartidos();
    Partido obtenerPartidoPorId(Long id);
    Partido crearPartido(Partido partido);
    Partido actualizarPartido(Long id, Partido partido);
    void eliminarPartido(Long id);
    List<Partido> listarPorTemporada(Long temporadaId);
    List<Partido> listarPorEquipo(Long equipoId);
    List<Partido> listarPorEstado(String estado);
    Map<String, Object> obtenerDetallesPartido(Long partidoId); // contiene goles y tarjetas
    Map<Equipo, TablaPosicion> generarTablaPosiciones(Long temporadaId);
    Partido registrarResultado(Long partidoId, int golesLocal, int golesVisitante);


}
