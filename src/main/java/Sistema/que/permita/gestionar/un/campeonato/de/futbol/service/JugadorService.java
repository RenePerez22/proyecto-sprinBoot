package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;

import java.util.Optional;


import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;


public interface JugadorService {
	 List<Jugador> listarJugadores();
	    Jugador obtenerJugadorPorId(Long id);
	    Jugador crearJugador(Jugador jugador);
	    Jugador actualizarJugador(Long id, Jugador jugador);
	    void eliminarJugador(Long id);
	    List<Jugador> listarPorEquipo(Long equipoId);
	    Optional<Jugador> buscarPorDni(String dni);
	    
	   
	    

}
