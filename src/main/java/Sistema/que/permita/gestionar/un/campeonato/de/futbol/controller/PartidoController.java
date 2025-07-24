package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Temporada;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EquipoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.PartidoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/partidos")
public class PartidoController {
	 private final PartidoService partidoService;
	    private final EquipoService equipoService;
	    private final TemporadaService temporadaService;

	    public PartidoController(
	            PartidoService partidoService,
	            EquipoService equipoService,
	            TemporadaService temporadaService) {
	        this.partidoService = partidoService;
	        this.equipoService = equipoService;
	        this.temporadaService = temporadaService;
	    }

	    @GetMapping
	    public String listar(Model model) {
	        List<Partido> partidos = partidoService.listarPartidos();
	        model.addAttribute("partidos", partidos);
	        return "partidos/listar";
	    }

	    @GetMapping("/nuevo")
	    public String mostrarFormCrear(Model model) {
	        model.addAttribute("partido", new Partido());
	        model.addAttribute("equipos", equipoService.listarEquipos());
	        model.addAttribute("temporadas", temporadaService.listarTemporadas());
	        return "partidos/crear";
	    }

	    @PostMapping
	    public String crear(@Valid @ModelAttribute Partido partido, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("equipos", equipoService.listarEquipos());
	            model.addAttribute("temporadas", temporadaService.listarTemporadas());
	            return "partidos/crear";
	        }
	        partidoService.crearPartido(partido);
	        return "redirect:/partidos";
	    }

	    @GetMapping("/editar/{id}")
	    public String mostrarFormEditar(@PathVariable Long id, Model model) {
	        Partido p = partidoService.obtenerPartidoPorId(id);
	        model.addAttribute("partido", p);
	        model.addAttribute("equipos", equipoService.listarEquipos());
	        model.addAttribute("temporadas", temporadaService.listarTemporadas());
	        return "partidos/editar";
	    }

	    @PostMapping("/editar/{id}")
	    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute Partido partido, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("equipos", equipoService.listarEquipos());
	            model.addAttribute("temporadas", temporadaService.listarTemporadas());
	            return "partidos/editar";
	        }
	        partidoService.actualizarPartido(id, partido);
	        return "redirect:/partidos";
	    }

	    @GetMapping("/eliminar/{id}")
	    public String eliminar(@PathVariable Long id) {
	        partidoService.eliminarPartido(id);
	        return "redirect:/partidos";
	    }

	    @GetMapping("/detalles/{id}")
	    public String verDetalles(@PathVariable Long id, Model model) {
	        Map<String, Object> d = partidoService.obtenerDetallesPartido(id);
	        model.addAttribute("partido", d.get("partido"));
	        model.addAttribute("goles", d.get("goles"));
	        model.addAttribute("tarjetas", d.get("tarjetas"));
	        return "partidos/detalles";
	    }
	    
	    @GetMapping("/{id}/registrar-resultado")
	    public String mostrarFormularioResultado(@PathVariable("id") Long id, Model model) {
	        Partido partido = partidoService.obtenerPartidoPorId(id);
	        model.addAttribute("partido", partido);
	        return "partidos/registrarResultado";
	    }

	    @PostMapping("/{id}/registrar-resultado")
	    public String registrarResultado(
	            @PathVariable("id") Long id,
	            @RequestParam("golesLocal") int golesLocal,
	            @RequestParam("golesVisitante") int golesVisitante,
	            Model model) {

	        partidoService.registrarResultado(id, golesLocal, golesVisitante);
	        return "redirect:/partidos"; // Puedes cambiarlo para ir al detalle del partido si quieres
	    }
	    

	    @GetMapping("/por-temporada")
	    public String seleccionarTemporada(Model model) {
	        model.addAttribute("temporadas", temporadaService.listarTemporadas());
	        return "partidos/seleccionar-temporada";
	    }

	    @PostMapping("/por-temporada")
	    public String listarPorTemporada(@RequestParam Long temporadaId, Model model) {
	        List<Partido> partidos = partidoService.listarPorTemporada(temporadaId);
	        Temporada temporada = temporadaService.obtenerTemporadaPorId(temporadaId);
	        model.addAttribute("partidos", partidos);
	        model.addAttribute("temporada", temporada);
	        return "partidos/listar-por-temporada";
	    }
	    
		  

}
