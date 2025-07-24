package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/temporadas")
public class TemporadaController {
	private final TemporadaService temporadaService;

    public TemporadaController(TemporadaService temporadaService) {
        this.temporadaService = temporadaService;
    }

    @GetMapping
    public String listarTemporadas(Model model) {
        model.addAttribute("temporadas", temporadaService.listarTemporadas());
        return "temporadas/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("temporada", new Temporada());
        return "temporadas/crear";
    }

    @PostMapping
    public String crearTemporada(@Valid @ModelAttribute Temporada temporada, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "temporadas/crear";
        }
        temporadaService.crearTemporada(temporada);
        return "redirect:/temporadas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Temporada temporada = temporadaService.obtenerTemporadaPorId(id);
        model.addAttribute("temporada", temporada);
        return "temporadas/editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarTemporada(@PathVariable Long id, @Valid @ModelAttribute Temporada temporada, BindingResult result) {
        if (result.hasErrors()) {
            return "temporadas/editar";
        }
        temporadaService.actualizarTemporada(id, temporada);
        return "redirect:/temporadas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTemporada(@PathVariable Long id) {
        temporadaService.eliminarTemporada(id);
        return "redirect:/temporadas";
    }

}
