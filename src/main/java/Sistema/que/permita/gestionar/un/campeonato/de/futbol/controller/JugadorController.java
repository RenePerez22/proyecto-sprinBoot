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

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.JugadorRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EquipoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.JugadorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {
	  private final JugadorService jugadorService;
	    private final EquipoService equipoService;
	    

	    public JugadorController(JugadorService jugadorService, EquipoService equipoService,
				JugadorRepository jugadorRepository) {
			super();
			this.jugadorService = jugadorService;
			this.equipoService = equipoService;
			
		}


		
	    

	    @GetMapping
	    public String listarJugadores(Model model) {
	        List<Jugador> jugadores = jugadorService.listarJugadores();
	        model.addAttribute("jugadores", jugadores);
	        return "jugadores/listar";
	    }

	    @GetMapping("/nuevo")
	    public String mostrarFormularioCrear(Model model) {
	        model.addAttribute("jugador", new Jugador());
	        model.addAttribute("equipos", equipoService.listarEquipos());
	        return "jugadores/crear";
	    }

	    @PostMapping
	    public String crearJugador(@Valid @ModelAttribute Jugador jugador, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("equipos", equipoService.listarEquipos());
	            return "jugadores/crear";
	        }
	        jugadorService.crearJugador(jugador);
	        return "redirect:/jugadores";
	    }

	    @GetMapping("/editar/{id}")
	    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
	        Jugador jugador = jugadorService.obtenerJugadorPorId(id);
	        model.addAttribute("jugador", jugador);
	        model.addAttribute("equipos", equipoService.listarEquipos());
	        return "jugadores/editar";
	    }

	    @PostMapping("/editar/{id}")
	    public String actualizarJugador(@PathVariable Long id, @Valid @ModelAttribute Jugador jugador, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("equipos", equipoService.listarEquipos());
	            return "jugadores/editar";
	        }
	        jugadorService.actualizarJugador(id, jugador);
	        return "redirect:/jugadores";
	    }

	    @GetMapping("/eliminar/{id}")
	    public String eliminarJugador(@PathVariable Long id) {
	        jugadorService.eliminarJugador(id);
	        return "redirect:/jugadores";
	    }
	    @GetMapping("/menu")
	    public String menuJugador() {
	        return "menu-jugador";
	    }
	  


}
