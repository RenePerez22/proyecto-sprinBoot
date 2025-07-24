package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Gol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.TablaPosicion;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Tarjeta;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.GolRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TarjetaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TemporadaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.PartidoService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class PartidoServiceImpl implements PartidoService {
	@Autowired
	 private final PartidoRepository partidoRepository;
	    private final EquipoRepository equipoRepository;
	    private final TemporadaRepository temporadaRepository;
	    private final GolRepository golRepository;
	    private final TarjetaRepository tarjetaRepository;

	    public PartidoServiceImpl(PartidoRepository partidoRepository, EquipoRepository equipoRepository, TemporadaRepository temporadaRepository, GolRepository golRepository, TarjetaRepository tarjetaRepository) {
	        this.partidoRepository = partidoRepository;
	        this.equipoRepository = equipoRepository;
	        this.temporadaRepository = temporadaRepository;
			this.golRepository = golRepository;
			this.tarjetaRepository = tarjetaRepository;
	    }

	@Override
	public List<Partido> listarPartidos() {
		return partidoRepository.findAll();
	}

	@Override
	public Partido obtenerPartidoPorId(Long id) {
		 return partidoRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con id: " + id));
	}

	@Override
	public Partido crearPartido(Partido partido) {
		 validarEquipos(partido.getEquipoLocal().getId(), partido.getEquipoVisitante().getId());

	        Equipo local = equipoRepository.findById(partido.getEquipoLocal().getId()).orElseThrow();
	        Equipo visitante = equipoRepository.findById(partido.getEquipoVisitante().getId()).orElseThrow();
	        Temporada temporada = temporadaRepository.findById(partido.getTemporada().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Temporada no válida"));

	        partido.setEquipoLocal(local);
	        partido.setEquipoVisitante(visitante);
	        partido.setTemporada(temporada);

	        return partidoRepository.save(partido);
	}

	@Override
	public Partido actualizarPartido(Long id, Partido partido) {
		  Partido partidoExistente = obtenerPartidoPorId(id);

	        validarEquipos(partido.getEquipoLocal().getId(), partido.getEquipoVisitante().getId());

	        Equipo local = equipoRepository.findById(partido.getEquipoLocal().getId()).orElseThrow();
	        Equipo visitante = equipoRepository.findById(partido.getEquipoVisitante().getId()).orElseThrow();
	        Temporada temporada = temporadaRepository.findById(partido.getTemporada().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Temporada no válida"));

	        partidoExistente.setFecha(partido.getFecha());
	        partidoExistente.setHora(partido.getHora());
	        partidoExistente.setEstadio(partido.getEstadio());
	        partidoExistente.setArbitro(partido.getArbitro());
	        partidoExistente.setResultadoLocal(partido.getResultadoLocal());
	        partidoExistente.setResultadoVisitante(partido.getResultadoVisitante());
	        partidoExistente.setEstado(partido.getEstado());

	        partidoExistente.setEquipoLocal(local);
	        partidoExistente.setEquipoVisitante(visitante);
	        partidoExistente.setTemporada(temporada);

	        return partidoRepository.save(partidoExistente);
	}

	@Override
	public void eliminarPartido(Long id) {
		 if (!partidoRepository.existsById(id)) {
	            throw new EntityNotFoundException("Partido no encontrado con id: " + id);
	        }
	        partidoRepository.deleteById(id);
		
	}

	@Override
	public List<Partido> listarPorTemporada(Long temporadaId) {
		return partidoRepository.findByTemporadaId(temporadaId);
	}

	@Override
	public List<Partido> listarPorEquipo(Long equipoId) {
		return partidoRepository.findByEquipoLocalIdOrEquipoVisitanteId(equipoId, equipoId);
	}
	private void validarEquipos(Long idLocal, Long idVisitante) {
        if (idLocal.equals(idVisitante)) {
            throw new IllegalArgumentException("El equipo local y el visitante no pueden ser el mismo");
        }
    }

	@Override
	public List<Partido> listarPorEstado(String estado) {
		return partidoRepository.findByEstadoIgnoreCase(estado);
	}

	@Override
	public Map<String, Object> obtenerDetallesPartido(Long partidoId) {
		Partido partido = obtenerPartidoPorId(partidoId);
        List<Gol> goles = golRepository.findByPartidoId(partidoId);
        List<Tarjeta> tarjetas = tarjetaRepository.findByPartidoId(partidoId);

        Map<String, Object> detalles = new HashMap<>();
        detalles.put("partido", partido);
        detalles.put("goles", goles);
        detalles.put("tarjetas", tarjetas);

        return detalles;
	}
	// Validaciones adicionales si quieres evitar partidos duplicados o inválidos
    private void validarPartido(Partido partido) {
        if (partido.getEquipoLocal().getId().equals(partido.getEquipoVisitante().getId())) {
            throw new IllegalArgumentException("El equipo local y visitante no pueden ser el mismo.");
        }
        
    }

	@Override
	public Map<Equipo, TablaPosicion> generarTablaPosiciones(Long temporadaId) {
		List<Partido> partidos = partidoRepository.findByTemporadaId(temporadaId);
	    Map<Equipo, TablaPosicion> tabla = new HashMap<>();

	    for (Partido p : partidos) {
	        for (Equipo e : Arrays.asList(p.getEquipoLocal(), p.getEquipoVisitante())) {
	            tabla.putIfAbsent(e, new TablaPosicion(e));
	        }
	        TablaPosicion local = tabla.get(p.getEquipoLocal());
	        TablaPosicion visitante = tabla.get(p.getEquipoVisitante());
	        
	        local.jugarPartido(p.getResultadoLocal(), p.getResultadoVisitante());
	        visitante.jugarPartido(p.getResultadoVisitante(), p.getResultadoLocal());
	    }
	    return tabla;
	}
	
	@Override
	public Partido registrarResultado(Long partidoId, int golesLocal, int golesVisitante) {
	    Partido partido = obtenerPartidoPorId(partidoId);
	    partido.setResultadoLocal(golesLocal);
	    partido.setResultadoVisitante(golesVisitante);
	    partido.setEstado("Finalizado"); // Cambiar el estado

	    return partidoRepository.save(partido);
	}


}
