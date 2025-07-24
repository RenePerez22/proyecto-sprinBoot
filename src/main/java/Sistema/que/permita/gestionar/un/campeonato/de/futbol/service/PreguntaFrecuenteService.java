package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.PreguntaFrecuenta;

public interface PreguntaFrecuenteService {
	List<PreguntaFrecuenta> listarPreguntas();
	PreguntaFrecuenta obtenerPorId(Long id);
	PreguntaFrecuenta crear(PreguntaFrecuenta pregunta);
	PreguntaFrecuenta actualizar(Long id, PreguntaFrecuenta pregunta);
    void eliminar(Long id);
    List<PreguntaFrecuenta> buscarPorPalabraClave(String palabraClave);

}
