package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.JugadorSuspendido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Tarjeta;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.JugadorRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TarjetaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TarjetaService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class TarjetaServiceImpl implements TarjetaService {
	 private final TarjetaRepository tarjetaRepository;
	    private final JugadorRepository jugadorRepository;
	    private final PartidoRepository partidoRepository;

	    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository,
	                              JugadorRepository jugadorRepository,
	                              PartidoRepository partidoRepository) {
	        this.tarjetaRepository = tarjetaRepository;
	        this.jugadorRepository = jugadorRepository;
	        this.partidoRepository = partidoRepository;
	    }

	@Override
	public List<Tarjeta> listarTarjetas() {
		return tarjetaRepository.findAll();
	}

	@Override
	public Tarjeta obtenerTarjetaPorId(Long id) {
		return tarjetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarjeta no encontrada con id: " + id));
	}

	@Override
	public Tarjeta crearTarjeta(Tarjeta tarjeta) {
		 Jugador jugador = jugadorRepository.findById(tarjeta.getJugador().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Jugador no válido"));
	        Partido partido = partidoRepository.findById(tarjeta.getPartido().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Partido no válido"));

	        tarjeta.setJugador(jugador);
	        tarjeta.setPartido(partido);

	        return tarjetaRepository.save(tarjeta);
	}

	@Override
	public Tarjeta actualizarTarjeta(Long id, Tarjeta tarjeta) {
		 Tarjeta tarjetaExistente = obtenerTarjetaPorId(id);

	        Jugador jugador = jugadorRepository.findById(tarjeta.getJugador().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Jugador no válido"));
	        Partido partido = partidoRepository.findById(tarjeta.getPartido().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Partido no válido"));

	        tarjetaExistente.setMinuto(tarjeta.getMinuto());
	        tarjetaExistente.setTipo(tarjeta.getTipo());
	        tarjetaExistente.setMotivo(tarjeta.getMotivo());
	        tarjetaExistente.setJugador(jugador);
	        tarjetaExistente.setPartido(partido);

	        return tarjetaRepository.save(tarjetaExistente);
	}

	@Override
	public void eliminarTarjeta(Long id) {
		if (!tarjetaRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarjeta no encontrada con id: " + id);
        }
        tarjetaRepository.deleteById(id);
		
	}

	@Override
	public List<Tarjeta> listarPorJugador(Long jugadorId) {
		 return tarjetaRepository.findByJugadorId(jugadorId);
	}

	@Override
	public List<Tarjeta> listarPorPartido(Long partidoId) {
		return tarjetaRepository.findByPartidoId(partidoId);
	}
	
	public List<JugadorSuspendido> obtenerJugadoresSuspendidos(Long temporadaId) {
	    List<Tarjeta> tarjetas = tarjetaRepository.findByPartidoTemporadaId(temporadaId);
	    Map<Jugador, List<Tarjeta>> agrupadas = tarjetas.stream()
	        .collect(Collectors.groupingBy(Tarjeta::getJugador));

	    List<JugadorSuspendido> suspendidos = new ArrayList<>();

	    for (Map.Entry<Jugador, List<Tarjeta>> entry : agrupadas.entrySet()) {
	        Jugador jugador = entry.getKey();
	        int amarillas = 0, rojas = 0;
	        LocalDate fechaUltimaSancion = null;

	        for (Tarjeta t : entry.getValue()) {
	            if (t.getTipo().equalsIgnoreCase("amarilla")) amarillas++;
	            else if (t.getTipo().equalsIgnoreCase("roja")) rojas++;

	            LocalDate fechaTarjeta = t.getPartido().getFecha();
	            if (fechaUltimaSancion == null || fechaTarjeta.isAfter(fechaUltimaSancion)) {
	                fechaUltimaSancion = fechaTarjeta;
	            }
	        }

	        if (rojas >= 1 || amarillas >= 3) {
	            String motivo = rojas >= 1 ? "Roja directa" : "Acumulación de amarillas";

	            LocalDate finalFechaUltimaSancion = fechaUltimaSancion; // necesario para lambda

	            LocalDate fechaHabilitado = partidoRepository
	                .findByTemporadaIdOrderByFechaAsc(temporadaId).stream()
	                .filter(p -> finalFechaUltimaSancion != null &&
	                    p.getFecha().isAfter(finalFechaUltimaSancion) &&
	                    (p.getEquipoLocal().getId().equals(jugador.getEquipo().getId()) ||
	                     p.getEquipoVisitante().getId().equals(jugador.getEquipo().getId())))
	                .map(p -> p.getFecha().plusDays(1))
	                .findFirst()
	                .orElse(null);

	            suspendidos.add(new JugadorSuspendido(jugador, amarillas, rojas, motivo, fechaHabilitado));
	        }
	    }

	    return suspendidos;
	}



}
