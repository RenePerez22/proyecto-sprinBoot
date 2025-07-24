package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TemporadaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class TemporadaServiceImpl implements TemporadaService {
	 private final TemporadaRepository temporadaRepository;

	    public TemporadaServiceImpl(TemporadaRepository temporadaRepository) {
	        this.temporadaRepository = temporadaRepository;
	    }

	@Override
	public List<Temporada> listarTemporadas() {
		return temporadaRepository.findAll();
	}

	@Override
	public Temporada obtenerTemporadaPorId(Long id) {
		 return temporadaRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Temporada no encontrada con id: " + id));
	}

	@Override
	public Temporada crearTemporada(Temporada temporada) {
		return temporadaRepository.save(temporada);
	}

	@Override
	public Temporada actualizarTemporada(Long id, Temporada temporada) {
		 Temporada temporadaExistente = obtenerTemporadaPorId(id);
	        temporadaExistente.setNombre(temporada.getNombre());
	        temporadaExistente.setFechaInicio(temporada.getFechaInicio());
	        temporadaExistente.setFechaFin(temporada.getFechaFin());
	        return temporadaRepository.save(temporadaExistente);
	}

	@Override
	public void eliminarTemporada(Long id) {
		 if (!temporadaRepository.existsById(id)) {
	            throw new EntityNotFoundException("Temporada no encontrada con id: " + id);
	        }
	        temporadaRepository.deleteById(id);
		
	}

}
