package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.JugadorSuspendido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Tarjeta;

public interface TarjetaService {
	 List<Tarjeta> listarTarjetas();
	    Tarjeta obtenerTarjetaPorId(Long id);
	    Tarjeta crearTarjeta(Tarjeta tarjeta);
	    Tarjeta actualizarTarjeta(Long id, Tarjeta tarjeta);
	    void eliminarTarjeta(Long id);
	    List<Tarjeta> listarPorJugador(Long jugadorId);
	    List<Tarjeta> listarPorPartido(Long partidoId);
	    List<JugadorSuspendido> obtenerJugadoresSuspendidos(Long temporadaId);


}
