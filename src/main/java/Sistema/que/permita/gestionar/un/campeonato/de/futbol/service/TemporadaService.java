package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;

public interface TemporadaService {
	List<Temporada> listarTemporadas();
    Temporada obtenerTemporadaPorId(Long id);
    Temporada crearTemporada(Temporada temporada);
    Temporada actualizarTemporada(Long id, Temporada temporada);
    void eliminarTemporada(Long id);

}
