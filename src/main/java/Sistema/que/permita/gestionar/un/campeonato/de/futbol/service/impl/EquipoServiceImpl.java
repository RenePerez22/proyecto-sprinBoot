package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TemporadaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EquipoService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class EquipoServiceImpl implements EquipoService {
	 private final EquipoRepository equipoRepository;
	    private final TemporadaRepository temporadaRepository;

	    public EquipoServiceImpl(EquipoRepository equipoRepository, TemporadaRepository temporadaRepository) {
	        this.equipoRepository = equipoRepository;
	        this.temporadaRepository = temporadaRepository;
	    }

	@Override
	public List<Equipo> listarEquipos() {
		return equipoRepository.findAll();
	}

	@Override
	public Equipo obtenerEquipoPorId(Long id) {
		return equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con id: " + id));
	}

	@Override
	public Equipo crearEquipo(Equipo equipo) {
		Temporada temporada = temporadaRepository.findById(equipo.getTemporada().getId())
                .orElseThrow(() -> new EntityNotFoundException("Temporada no válida"));
        equipo.setTemporada(temporada);
        return equipoRepository.save(equipo);
	}

	@Override
	public Equipo actualizarEquipo(Long id, Equipo equipo) {
		 Equipo equipoExistente = obtenerEquipoPorId(id);
	        equipoExistente.setNombre(equipo.getNombre());
	        equipoExistente.setDirectorTecnico(equipo.getDirectorTecnico());
	        equipoExistente.setEscudoUrl(equipo.getEscudoUrl());

	        Temporada temporada = temporadaRepository.findById(equipo.getTemporada().getId())
	                .orElseThrow(() -> new EntityNotFoundException("Temporada no válida"));
	        equipoExistente.setTemporada(temporada);

	        return equipoRepository.save(equipoExistente);
	}

	@Override
	public void eliminarEquipo(Long id) {
		   if (!equipoRepository.existsById(id)) {
	            throw new EntityNotFoundException("Equipo no encontrado con id: " + id);
	        }
	        equipoRepository.deleteById(id);
		
	}

	@Override
	public List<Equipo> listarPorTemporada(Long temporadaId) {
		return equipoRepository.findByTemporadaId(temporadaId);
	}

}
