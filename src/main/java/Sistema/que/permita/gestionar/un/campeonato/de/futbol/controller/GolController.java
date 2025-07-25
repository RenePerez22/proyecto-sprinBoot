package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Gol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EquipoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.GolService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.JugadorService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.PartidoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/goles")
public class GolController {
	
	 private final GolService golService;
	    private final JugadorService jugadorService;
	    private final PartidoService partidoService;
	    private final EquipoService equipoService;

	    public GolController(GolService golService,
	                                  JugadorService jugadorService,
	                                  PartidoService partidoService,
	                                  EquipoService equipoService) {
	        this.golService = golService;
	        this.jugadorService = jugadorService;
	        this.partidoService = partidoService;
	        this.equipoService = equipoService;
	    }

	    @GetMapping
	    public String listarGoles(Model model) {
	        List<Gol> goles = golService.listarGoles();
	        model.addAttribute("goles", goles);
	        return "goles/listar";
	    }

	    @GetMapping("/nuevo")
	    public String mostrarFormularioCrear(Model model) {
	        model.addAttribute("gol", new Gol());
	        model.addAttribute("jugadores", jugadorService.listarJugadores());
	        model.addAttribute("partidos", partidoService.listarPartidos());
	        model.addAttribute("equipos", equipoService.listarEquipos());
	        return "goles/crear";
	    }

	    @PostMapping
	    public String crearGol(@Valid @ModelAttribute Gol gol, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("jugadores", jugadorService.listarJugadores());
	            model.addAttribute("partidos", partidoService.listarPartidos());
	            model.addAttribute("equipos", equipoService.listarEquipos());
	            return "goles/crear";
	        }
	        golService.crearGol(gol);
	        return "redirect:/goles";
	    }

	    @GetMapping("/editar/{id}")
	    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
	        Gol gol = golService.obtenerGolPorId(id);
	        model.addAttribute("gol", gol);
	        model.addAttribute("jugadores", jugadorService.listarJugadores());
	        model.addAttribute("partidos", partidoService.listarPartidos());
	        model.addAttribute("equipos", equipoService.listarEquipos());
	        return "goles/editar";
	    }

	    @PostMapping("/editar/{id}")
	    public String actualizarGol(@PathVariable Long id, @Valid @ModelAttribute Gol gol, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("jugadores", jugadorService.listarJugadores());
	            model.addAttribute("partidos", partidoService.listarPartidos());
	            model.addAttribute("equipos", equipoService.listarEquipos());
	            return "goles/editar";
	        }
	        golService.actualizarGol(id, gol);
	        return "redirect:/goles";
	    }

	    @GetMapping("/eliminar/{id}")
	    public String eliminarGol(@PathVariable Long id) {
	        golService.eliminarGol(id);
	        return "redirect:/goles";
	    }

	    // Opcional: filtrar por partido o jugador
	    @GetMapping("/partido/{partidoId}")
	    public String listarPorPartido(@PathVariable Long partidoId, Model model) {
	        model.addAttribute("goles", golService.listarPorPartido(partidoId));
	        return "goles/listar";
	    }
	    
	  

}
