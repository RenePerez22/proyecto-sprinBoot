package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Gol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.GolService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.PartidoService;

@Controller
@RequestMapping("/golporpartido")
public class GolPorPartidoController {

    private final GolService golService;
    private final PartidoService partidoService;

    public GolPorPartidoController(GolService golService, PartidoService partidoService) {
        this.golService = golService;
        this.partidoService = partidoService;
    }

    // Mostrar formulario con select de partidos
    @GetMapping("/seleccionar-partido")
    public String seleccionarPartido(Model model) {
        List<Partido> partidos = partidoService.listarPartidos(); // Asegúrate de tener este método
        model.addAttribute("partidos", partidos);
        return "seleccionar_partido"; // plantilla para elegir un partido
    }

    // Mostrar goles del partido seleccionado
    @GetMapping("/partido/{partidoId}")
    public String listarGolesPorPartido(@PathVariable Long partidoId, Model model) {
        List<Gol> goles = golService.listarPorPartido(partidoId);
        model.addAttribute("goles", goles);
        model.addAttribute("partidoId", partidoId);
        return "goles_por_partido"; // plantilla con la lista de goles
    }
}
