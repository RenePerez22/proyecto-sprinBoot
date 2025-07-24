package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EstadisticasService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	  private final EstadisticasService estadisticasService;

	  @GetMapping("/menu")
	    public String menuAdmin(Model model) {
	        // Obtener estadísticas
	        Map<String, Long> estadisticas = estadisticasService.obtenerEstadisticasRapidas();
	        
	        // Agregar cada estadística individualmente al modelo
	        model.addAttribute("totalEquipos", estadisticas.get("equiposRegistrados"));
	        model.addAttribute("jugadoresActivos", estadisticas.get("jugadoresActivos"));
	        model.addAttribute("partidosProgramados", estadisticas.get("partidosProgramados"));
	        model.addAttribute("temporadasActivas", estadisticas.get("temporadasActivas"));
	        
	        return "menu-admin";
	    }
	    
	//  @GetMapping("/menu")
	  //  public String menuAdmin() {
	    //    return "menu-admin";
	    //}

}
