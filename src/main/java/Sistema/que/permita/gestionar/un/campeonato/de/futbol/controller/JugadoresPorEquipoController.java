package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.JugadorRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EquipoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.JugadorService;

@Controller
@RequestMapping("/jugadoresporequipo")
public class JugadoresPorEquipoController {

    private final JugadorService jugadorService;
    private final EquipoService equipoService;
    private final EquipoRepository equipoRepository;

    public JugadoresPorEquipoController(JugadorService jugadorService, EquipoService equipoService,
			EquipoRepository equipoRepository, JugadorRepository jugadorRepository) {
		super();
		this.jugadorService = jugadorService;
		this.equipoService = equipoService;
		this.equipoRepository = equipoRepository;
	}

    //  Mostrar formulario con select de equipos
    @GetMapping("/seleccionar-equipo")
    public String seleccionarEquipo(Model model) {
        List<Equipo> equipos = equipoService.listarEquipos(); 
        model.addAttribute("equipos", equipos);
        return "seleccionar_equipo"; // 
    }

    //  Mostrar jugadores del equipo seleccionado
    @GetMapping("/equipo/{equipoId}")
    public String listarPorEquipo(@PathVariable Long equipoId, Model model) {
    	 String nombreEquipo = equipoRepository.findById(equipoId)
    		        .map(Equipo::getNombre)
    		        .orElse("Equipo no encontrado");
        List<Jugador> jugadores = jugadorService.listarPorEquipo(equipoId);
        model.addAttribute("nombreEquipo", nombreEquipo);
        model.addAttribute("jugadores", jugadores);
        return "jugadores_por_equipo"; // 
    }
    
   


	
}
