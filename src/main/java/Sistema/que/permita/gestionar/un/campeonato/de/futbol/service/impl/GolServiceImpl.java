package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Gol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Goleador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.GolRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.JugadorRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.GolService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class GolServiceImpl implements GolService {
	@Autowired
	 private final GolRepository golRepository;
	    private final JugadorRepository jugadorRepository;
	    private final PartidoRepository partidoRepository;
	    private final EquipoRepository equipoRepository;

	    public GolServiceImpl(GolRepository golRepository,
	                          JugadorRepository jugadorRepository,
	                          PartidoRepository partidoRepository,
	                          EquipoRepository equipoRepository) {
	        this.golRepository = golRepository;
	        this.jugadorRepository = jugadorRepository;
	        this.partidoRepository = partidoRepository;
	        this.equipoRepository = equipoRepository;
	    }

	@Override
	public List<Gol> listarGoles() {
		return golRepository.findAll();
	}

	@Override
	public Gol obtenerGolPorId(Long id) {
		return golRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gol no encontrado con id: " + id));
	}

	@Override
	public Gol crearGol(Gol gol) {
		 Jugador jugador = jugadorRepository.findById(gol.getJugador().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Jugador no válido"));

	        Partido partido = partidoRepository.findById(gol.getPartido().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Partido no válido"));

	        Equipo equipo = equipoRepository.findById(gol.getEquipo().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Equipo no válido"));

	        gol.setJugador(jugador);
	        gol.setPartido(partido);
	        gol.setEquipo(equipo);

	        return golRepository.save(gol);
	}

	@Override
	public Gol actualizarGol(Long id, Gol gol) {
		 Gol golExistente = obtenerGolPorId(id);

	        Jugador jugador = jugadorRepository.findById(gol.getJugador().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Jugador no válido"));

	        Partido partido = partidoRepository.findById(gol.getPartido().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Partido no válido"));

	        Equipo equipo = equipoRepository.findById(gol.getEquipo().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Equipo no válido"));

	        golExistente.setMinuto(gol.getMinuto());
	        golExistente.setTipo(gol.getTipo());
	        golExistente.setJugador(jugador);
	        golExistente.setPartido(partido);
	        golExistente.setEquipo(equipo);

	        return golRepository.save(golExistente);
	}

	@Override
	public void eliminarGol(Long id) {
		 if (!golRepository.existsById(id)) {
	            throw new EntityNotFoundException("Gol no encontrado con id: " + id);
	        }
	        golRepository.deleteById(id);
		
	}

	@Override
	public List<Gol> listarPorPartido(Long partidoId) {
		return golRepository.findByPartidoId(partidoId);
	}

	@Override
	public List<Gol> listarPorJugador(Long jugadorId) {
		return golRepository.findByJugadorId(jugadorId);
	}

	@Override
	public List<Gol> listarPorEquipo(Long equipoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goleador> obtenerTablaGoleadores(Long temporadaId) {
		// Obtener todos los goles de partidos de una temporada
	    List<Gol> goles = golRepository.findByPartidoTemporadaId(temporadaId);

	    Map<Jugador, Long> conteo = goles.stream()
	        .collect(Collectors.groupingBy(Gol::getJugador, Collectors.counting()));

	    return conteo.entrySet().stream()
	        .map(e -> new Goleador(e.getKey(), e.getValue()))
	        .sorted(Comparator.comparingLong(Goleador::getGoles).reversed())
	        .collect(Collectors.toList());
	}

}
