package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;

public interface EquipoService {
	List<Equipo> listarEquipos();
    Equipo obtenerEquipoPorId(Long id);
    Equipo crearEquipo(Equipo equipo);
    Equipo actualizarEquipo(Long id, Equipo equipo);
    void eliminarEquipo(Long id);
    List<Equipo> listarPorTemporada(Long temporadaId);

}
