package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Goleador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.GolService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;

@Controller
@RequestMapping("/goleadores")
public class GoleadoresController {
	private final GolService golService;
    private final TemporadaService temporadaService;

    public GoleadoresController(GolService golService, TemporadaService temporadaService) {
        this.golService = golService;
        this.temporadaService = temporadaService;
    }

    @GetMapping
    public String selector(Model model) {
        model.addAttribute("temporadas", temporadaService.listarTemporadas());
        return "goleadores/selector";
    }

    @PostMapping
    public String mostrarGoleadores(@RequestParam Long temporadaId, Model model) {
        List<Goleador> tabla = golService.obtenerTablaGoleadores(temporadaId);
        model.addAttribute("goleadores", tabla);
        return "goleadores/tabla";
    }

}
