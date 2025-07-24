package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.TablaPosicion;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.PartidoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;

@Controller
@RequestMapping("/tabla")
public class TablaPosicionesController {
	 private final PartidoService partidoService;
	    private final TemporadaService temporadaService;

	    public TablaPosicionesController(PartidoService ps, TemporadaService ts) {
	        this.partidoService = ps;
	        this.temporadaService = ts;
	    }

	    @GetMapping
	    public String selectorTemporada(Model m) {
	        m.addAttribute("temporadas", temporadaService.listarTemporadas());
	        return "tabla/selector";
	    }

	    // POST: muestra la tabla de posiciones para una temporada específica
	    @PostMapping
	    public String mostrarTabla(@RequestParam Long temporadaId, Model model) {
	        // Obtenemos la tabla de posiciones mapeada por equipo
	        Map<Equipo, TablaPosicion> tabla = partidoService.generarTablaPosiciones(temporadaId);

	        // Convertimos el Map en una lista para poder ordenarla
	        List<TablaPosicion> posiciones = new ArrayList<>(tabla.values());

	        // Ordenamos por puntos (descendente), luego por diferencia de goles (descendente)
	        posiciones.sort(
	            Comparator.comparingInt(TablaPosicion::getPuntos).reversed()
	                      .thenComparingInt(TablaPosicion::getDiferencia).reversed()
	                      .thenComparingInt(TablaPosicion::getGolesFavor).reversed()
	        );

	        // Enviamos al modelo
	        model.addAttribute("posiciones", posiciones);
	        model.addAttribute("temporadaId", temporadaId); // opcional si lo usas en el HTML

	        return "tabla/tabla"; // HTML: src/main/resources/templates/tabla/tabla.html
	    }

}
