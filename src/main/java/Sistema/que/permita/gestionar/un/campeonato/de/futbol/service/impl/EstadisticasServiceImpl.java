package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.JugadorRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TemporadaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EstadisticasService;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstadisticasServiceImpl implements EstadisticasService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final PartidoRepository partidoRepository;
    private final TemporadaRepository temporadaRepository;
    

    @Override
    public Map<String, Long> obtenerEstadisticasRapidas() {
        Map<String, Long> stats = new LinkedHashMap<>();
        
        stats.put("equiposRegistrados", equipoRepository.count());
        stats.put("jugadoresActivos", jugadorRepository.countJugadoresActivos());
        stats.put("partidosProgramados", partidoRepository.countPartidosProgramados());
        stats.put("temporadasActivas", temporadaRepository.countTemporadasActivas());
        
        return stats;
    }
    
}
