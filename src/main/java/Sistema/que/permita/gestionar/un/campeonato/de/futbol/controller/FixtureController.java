package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.FixtureService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;

@Controller
@RequestMapping("/fixture")
public class FixtureController {

    private final FixtureService fixtureService;
    private final TemporadaService temporadaService;

    public FixtureController(FixtureService fixtureService, TemporadaService temporadaService) {
        this.fixtureService = fixtureService;
        this.temporadaService = temporadaService;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("temporadas", temporadaService.listarTemporadas());
        return "fixture/generar";
    }

    @PostMapping
    public String generarFixture(@RequestParam Long temporadaId,
                                 @RequestParam String fechaInicio) {
        fixtureService.generarFixture(temporadaId, fechaInicio);
        return "redirect:/partidos";
    }

}
