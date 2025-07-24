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

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Tarjeta;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.JugadorService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.PartidoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TarjetaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tarjetas")
public class TarjetaController {
	  private final TarjetaService tarjetaService;
	    private final JugadorService jugadorService;
	    private final PartidoService partidoService;

	    public TarjetaController(TarjetaService tarjetaService,
	                                      JugadorService jugadorService,
	                                      PartidoService partidoService) {
	        this.tarjetaService = tarjetaService;
	        this.jugadorService = jugadorService;
	        this.partidoService = partidoService;
	    }

	    @GetMapping
	    public String listarTarjetas(Model model) {
	        List<Tarjeta> tarjetas = tarjetaService.listarTarjetas();
	        model.addAttribute("tarjetas", tarjetas);
	        return "tarjetas/listar";
	    }

	    @GetMapping("/nueva")
	    public String mostrarFormularioCrear(Model model) {
	        model.addAttribute("tarjeta", new Tarjeta());
	        model.addAttribute("jugadores", jugadorService.listarJugadores());
	        model.addAttribute("partidos", partidoService.listarPartidos());
	        return "tarjetas/crear";
	    }

	    @PostMapping
	    public String crearTarjeta(@Valid @ModelAttribute Tarjeta tarjeta, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("jugadores", jugadorService.listarJugadores());
	            model.addAttribute("partidos", partidoService.listarPartidos());
	            return "tarjetas/crear";
	        }
	        tarjetaService.crearTarjeta(tarjeta);
	        return "redirect:/tarjetas";
	    }

	    @GetMapping("/editar/{id}")
	    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
	        Tarjeta tarjeta = tarjetaService.obtenerTarjetaPorId(id);
	        model.addAttribute("tarjeta", tarjeta);
	        model.addAttribute("jugadores", jugadorService.listarJugadores());
	        model.addAttribute("partidos", partidoService.listarPartidos());
	        return "tarjetas/editar";
	    }

	    @PostMapping("/editar/{id}")
	    public String actualizarTarjeta(@PathVariable Long id, @Valid @ModelAttribute Tarjeta tarjeta, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("jugadores", jugadorService.listarJugadores());
	            model.addAttribute("partidos", partidoService.listarPartidos());
	            return "tarjetas/editar";
	        }
	        tarjetaService.actualizarTarjeta(id, tarjeta);
	        return "redirect:/tarjetas";
	    }

	    @GetMapping("/eliminar/{id}")
	    public String eliminarTarjeta(@PathVariable Long id) {
	        tarjetaService.eliminarTarjeta(id);
	        return "redirect:/tarjetas";
	    }

	    @GetMapping("/jugador/{jugadorId}")
	    public String listarPorJugador(@PathVariable Long jugadorId, Model model) {
	        model.addAttribute("tarjetas", tarjetaService.listarPorJugador(jugadorId));
	        return "tarjetas/listar";
	    }

	    @GetMapping("/partido/{partidoId}")
	    public String listarPorPartido(@PathVariable Long partidoId, Model model) {
	        model.addAttribute("tarjetas", tarjetaService.listarPorPartido(partidoId));
	        return "tarjetas/listar";
	    }

}
