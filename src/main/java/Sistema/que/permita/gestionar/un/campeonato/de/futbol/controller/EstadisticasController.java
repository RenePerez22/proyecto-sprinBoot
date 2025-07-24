package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstadisticasController {
    
    @GetMapping("/estadisticas")
    public String mostrarEstadisticas() {
        // Lógica para estadísticas si es necesario
        return "estadisticas"; // Nombre del archivo HTML sin extensión
    }
}