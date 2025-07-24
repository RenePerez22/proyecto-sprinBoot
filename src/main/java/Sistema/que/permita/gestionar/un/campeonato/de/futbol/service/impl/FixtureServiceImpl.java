package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.TemporadaRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.FixtureService;

@Service
public class FixtureServiceImpl implements FixtureService {

    private final EquipoRepository equipoRepository;
    private final PartidoRepository partidoRepository;
    private final TemporadaRepository temporadaRepository;

    public FixtureServiceImpl(EquipoRepository equipoRepository,
                              PartidoRepository partidoRepository,
                              TemporadaRepository temporadaRepository) {
        this.equipoRepository = equipoRepository;
        this.partidoRepository = partidoRepository;
        this.temporadaRepository = temporadaRepository;
    }

    @Override
    public void generarFixture(Long temporadaId, String fechaInicioStr) {
        Temporada temporada = temporadaRepository.findById(temporadaId)
                .orElseThrow(() -> new RuntimeException("Temporada no encontrada"));

        List<Equipo> equipos = equipoRepository.findAll();
        if (equipos.size() < 2) {
            throw new RuntimeException("Se necesitan al menos 2 equipos para generar un fixture");
        }

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        int numEquipos = equipos.size();

        boolean impar = numEquipos % 2 != 0;
        if (impar) {
            equipos.add(new Equipo()); // equipo ficticio
        }

        int rondas = equipos.size() - 1;
        int partidosPorRonda = equipos.size() / 2;

        List<Equipo> equiposTemp = new ArrayList<>(equipos);
        Equipo fijo = equiposTemp.remove(0);

        for (int ronda = 0; ronda < rondas; ronda++) {
            LocalDate fecha = fechaInicio.plusWeeks(ronda);
            for (int i = 0; i < partidosPorRonda; i++) {
                Equipo local = (i == 0) ? fijo : equiposTemp.get(i - 1);
                Equipo visitante = equiposTemp.get(equiposTemp.size() - i - 1);

                if (!local.getNombre().equals("DESCANSA") && !visitante.getNombre().equals("DESCANSA")) {
                    Partido partido = new Partido();
                    partido.setFecha(fecha);
                    partido.setHora(LocalTime.of(15, 0));
                    partido.setEquipoLocal(local);
                    partido.setEquipoVisitante(visitante);
                    partido.setEstadio("Por definir");
                    partido.setArbitro("Por asignar");
                    partido.setTemporada(temporada);
                    partido.setEstado("Pendiente");
                    partido.setResultadoLocal(0);
                    partido.setResultadoVisitante(0);

                    partidoRepository.save(partido);
                }
            }

            // Rotar
            equiposTemp.add(0, equiposTemp.remove(equiposTemp.size() - 1));
        }
    }
}