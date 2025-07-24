package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.JugadorSuspendido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TarjetaService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;

@Controller
@RequestMapping("/suspendidos")
public class SuspensionesController {
	 private final TarjetaService tarjetaService;
	    private final TemporadaService temporadaService;

	    public SuspensionesController(TarjetaService tarjetaService, TemporadaService temporadaService) {
	        this.tarjetaService = tarjetaService;
	        this.temporadaService = temporadaService;
	    }

	    @GetMapping
	    public String selectorTemporada(Model model) {
	        model.addAttribute("temporadas", temporadaService.listarTemporadas());
	        return "suspendidos/selector";
	    }

	    @PostMapping
	    public String mostrarSuspendidos(@RequestParam Long temporadaId, Model model) {
	        List<JugadorSuspendido> suspendidos = tarjetaService.obtenerJugadoresSuspendidos(temporadaId);
	        model.addAttribute("suspendidos", suspendidos);
	        return "suspendidos/tabla";
	    }

}
