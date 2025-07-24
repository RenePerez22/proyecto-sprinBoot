package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;



import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.GolRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.JugadorRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TarjetaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.JugadorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;




@Service
@Transactional
public class JugadorServiceImpl implements JugadorService {

	private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;


    public JugadorServiceImpl(JugadorRepository jugadorRepository, EquipoRepository equipoRepository,
    		GolRepository golRepository, TarjetaRepository tarjetaRepository, PartidoRepository partidoRepository) {
    	super();
    	this.jugadorRepository = jugadorRepository;
    	this.equipoRepository = equipoRepository;
    }
   

	@Override
	public List<Jugador> listarJugadores() {
		return jugadorRepository.findAll();
	}

	@Override
	public Jugador obtenerJugadorPorId(Long id) {
		 return jugadorRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado con id: " + id));
	}

	@Override
	public Jugador crearJugador(Jugador jugador) {
		 if (jugadorRepository.findByDni(jugador.getDni()).isPresent()) {
	            throw new IllegalArgumentException("Ya existe un jugador con ese DNI");
	        }

	        Equipo equipo = equipoRepository.findById(jugador.getEquipo().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Equipo no válido"));
	        jugador.setEquipo(equipo);

	        return jugadorRepository.save(jugador);
	}

	@Override
	public Jugador actualizarJugador(Long id, Jugador jugador) {
		 Jugador jugadorExistente = obtenerJugadorPorId(id);

	        jugadorExistente.setNombreCompleto(jugador.getNombreCompleto());
	        jugadorExistente.setDni(jugador.getDni());
	        jugadorExistente.setFechaNacimiento(jugador.getFechaNacimiento());
	        jugadorExistente.setPosicion(jugador.getPosicion());
	        jugadorExistente.setEstado(jugador.getEstado());

	        Equipo equipo = equipoRepository.findById(jugador.getEquipo().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Equipo no válido"));
	        jugadorExistente.setEquipo(equipo);

	        return jugadorRepository.save(jugadorExistente);
	}

	@Override
	public void eliminarJugador(Long id) {
		if (!jugadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Jugador no encontrado con id: " + id);
        }
        jugadorRepository.deleteById(id);
		
	}

	@Override
	public List<Jugador> listarPorEquipo(Long equipoId) {
		return jugadorRepository.findByEquipoId(equipoId);
	}

	@Override
	public Optional<Jugador> buscarPorDni(String dni) {
		return jugadorRepository.findByDni(dni);
	}

}
