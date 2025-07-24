package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Jugador;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Partido;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.EquipoRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.GolRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.JugadorRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;

@RestController
@RequestMapping("/api/chatbot")
public class ChatBotController {
	@Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private PartidoRepository partidoRepository;
    
    @Autowired
    private JugadorRepository jugadorRepository;
    
    @Autowired
    private GolRepository golRepository;
    

    @PostMapping
    public ResponseEntity<Map<String, String>> procesarMensaje(@RequestBody Map<String, String> body) {
        String mensaje = body.get("message");
        String respuesta = generarRespuesta(mensaje);
        Map<String, String> respuestaJson = new HashMap<>();
        respuestaJson.put("reply", respuesta);
        return ResponseEntity.ok(respuestaJson);
    }

    public String generarRespuesta(String msg) {
        msg = msg.toLowerCase();
        
        List<String> keywordsEquipos = List.of(
        		"equipo", "equipos", "registrados","hay","existen",
        		"inscritos", "participan", "compiten");
        List<String> keywordsConteo = List.of( "total",
        		"cuántos", "cuantos", "hay","Cuantos","Cuantos",
        		"existen", "total", "cantidad", "disponibles");
        List<String> keywordsTemporada = List.of(
        		"temporada", "torneo","temporadas","Temporada",
        		"apertura", "clausura", "actual","Temporadas");
        List<String> keywordsJugador = List.of(
        		"jugador", "Jugador","jugadores","Jugadores",
        		"participantes", "peloteros","inscritos","alineacion",
        		"registrados", "existen"
        		);
        List<String> keywordsPartidos = List.of(
        		"partidos","Partidos","Juegos","juegos");
        List<String> keywordsGoles = List.of(
        		"gol", "goles", "anotaciones"
        		);
        		

        boolean hablaDeEquipos = contieneAlguna(msg, keywordsEquipos);
        boolean preguntaCantidad = contieneAlguna(msg, keywordsConteo);
        boolean mencionaTemporada = contieneAlguna(msg, keywordsTemporada);
        boolean dicePartidos = contieneAlguna(msg, keywordsPartidos);
        boolean mencionaJugadores = contieneAlguna(msg, keywordsJugador);
        boolean diceGoles = contieneAlguna(msg, keywordsGoles);
        
        if (hablaDeEquipos) {
        	long total = equipoRepository.count();
            return "Actualmente hay " + total + " equipos registrados en el sistema.";
        }
        if (hablaDeEquipos && preguntaCantidad) {
            long total = equipoRepository.count();
            return "Actualmente hay " + total + " equipos registrados en el sistema.";
        }
        if(mencionaTemporada) {
        	String nombreTemp = extraerNombreTemporada(msg); // Puedes crear esta función básica
            List<Equipo> equipos = equipoRepository.findByTemporadaNombreContainingIgnoreCase(nombreTemp);

            if (equipos.isEmpty()) {
                return "No se encontraron equipos para la temporada '" + nombreTemp + "'.";
            } else {
                return "Hay " + equipos.size() + " equipo(s) registrados en la temporada '" + nombreTemp + "'.";
            }
        }
        if(preguntaCantidad && hablaDeEquipos && mencionaTemporada) {
        	String nombreTemp = extraerNombreTemporada(msg); // Puedes crear esta función básica
            List<Equipo> equipos = equipoRepository.findByTemporadaNombreContainingIgnoreCase(nombreTemp);

            if (equipos.isEmpty()) {
                return "No se encontraron equipos para la temporada '" + nombreTemp + "'.";
            } else {
                return "Hay " + equipos.size() + " equipo(s) registrados en la temporada '" + nombreTemp + "'.";
            }
        }
        if (hablaDeEquipos && preguntaCantidad && mencionaTemporada) {
            // Buscar nombre de temporada en el mensaje
            String nombreTemp = extraerNombreTemporada(msg); // Puedes crear esta función básica
            List<Equipo> equipos = equipoRepository.findByTemporadaNombreContainingIgnoreCase(nombreTemp);

            if (equipos.isEmpty()) {
                return "No se encontraron equipos para la temporada '" + nombreTemp + "'.";
            } else {
                return "Hay " + equipos.size() + " equipo(s) registrados en la temporada '" + nombreTemp + "'.";
            }
            
        }
        

        if (dicePartidos && preguntaCantidad && mencionaTemporada ) {
            List<Partido> partidos = partidoRepository.findByFecha(LocalDate.now());
            return partidos.isEmpty()
              ? "No hay partidos programados para hoy."
              : "Hay " + partidos.size() + " partidos programados hoy.";
        }
        
        if (dicePartidos && preguntaCantidad) {
            List<Partido> partidos = partidoRepository.findByFecha(LocalDate.now());
            return partidos.isEmpty()
              ? "No hay partidos programados para hoy."
              : "Hay " + partidos.size() + " partidos programados hoy.";
        }
        if (preguntaCantidad && dicePartidos) {
            List<Partido> partidos = partidoRepository.findByFecha(LocalDate.now());
            return partidos.isEmpty()
              ? "No hay partidos programados para hoy."
              : "Hay " + partidos.size() + " partidos programados hoy.";
        }
        if (dicePartidos && mencionaTemporada ) {
            List<Partido> partidos = partidoRepository.findByFecha(LocalDate.now());
            return partidos.isEmpty()
              ? "No hay partidos programados para hoy."
              : "Hay " + partidos.size() + " partidos programados hoy.";
        }
        if (dicePartidos) {
            List<Partido> partidos = partidoRepository.findByFecha(LocalDate.now());
            return partidos.isEmpty()
              ? "No hay partidos programados para hoy."
              : "Hay " + partidos.size() + " partidos programados hoy.";
        }
        if (mencionaJugadores) {
            long total = jugadorRepository.count();
            return "Actualmente hay " + total + " jugador(es) registrados.";
        }
        
        
        
        if (preguntaCantidad && mencionaJugadores) {
            long total = jugadorRepository.count();
            return "Actualmente hay " + total + " jugador(es) registrados.";
        }
        
        if (mencionaJugadores && preguntaCantidad && !msg.contains("equipo")) {
            long total = jugadorRepository.count();
            return "Actualmente hay " + total + " jugadores registrados en el sistema.";
        }
        if (mencionaJugadores && msg.contains("equipo")) {
            String nombreEquipo = extraerNombreDesde(msg, "equipo"); // función que puedes adaptar
            Optional<Equipo> equipo = equipoRepository.findByNombreIgnoreCase(nombreEquipo);

            if (equipo.isPresent()) {
                List<Jugador> jugadores = jugadorRepository.findByEquipo(equipo.get());
                return "El equipo " + nombreEquipo + " tiene " + jugadores.size() + " jugador(es) registrados.";
            } else {
                return "No se encontró el equipo '" + nombreEquipo + "'.";
            }
        }
        
        
        if (preguntaCantidad && diceGoles) {
            List<Object[]> datos = golRepository.contarGolesPorJugador();
            StringBuilder respuesta = new StringBuilder("📊 Goles por jugador:\n");

            for (Object[] fila : datos) {
                String nombre = (String) fila[0];
                Long goles = (Long) fila[1];
                respuesta.append("• ").append(nombre).append(": ").append(goles).append(" goles\n");
            }

            return respuesta.toString();
        }
        
        if (diceGoles) {
            List<Object[]> datos = golRepository.contarGolesPorJugador();
            StringBuilder respuesta = new StringBuilder("📊 Goles por jugador:\n");

            for (Object[] fila : datos) {
                String nombre = (String) fila[0];
                Long goles = (Long) fila[1];
                respuesta.append("• ").append(nombre).append(": ").append(goles).append(" goles\n");
            }

            return respuesta.toString();
        }
        
        if (diceGoles && dicePartidos) {
            List<Object[]> datos = golRepository.contarGolesPorPartido();
            if (datos.isEmpty()) {
                return "⚠️ No se han registrado goles en ningún partido aún.";
            }

            StringBuilder respuesta = new StringBuilder("📊 Goles por partido:\n");

            for (Object[] fila : datos) {
                Long partidoId = (Long) fila[0];
                LocalDate fecha = (LocalDate) fila[1];
                String local = (String) fila[2];
                String visitante = (String) fila[3];
                Long goles = (Long) fila[4];

                respuesta.append("• Partido #").append(partidoId).append(" — ")
                        .append(local).append(" vs ").append(visitante).append(" (")
                        .append(fecha).append("): ").append(goles).append(" goles\n");
            }

            return respuesta.toString();
        }
        
        if (preguntaCantidad && diceGoles && dicePartidos) {
            List<Object[]> datos = golRepository.contarGolesPorPartido();
            if (datos.isEmpty()) {
                return "⚠️ No se han registrado goles en ningún partido aún.";
            }

            StringBuilder respuesta = new StringBuilder("📊 Goles por partido:\n");

            for (Object[] fila : datos) {
                Long partidoId = (Long) fila[0];
                LocalDate fecha = (LocalDate) fila[1];
                String local = (String) fila[2];
                String visitante = (String) fila[3];
                Long goles = (Long) fila[4];

                respuesta.append("• Partido #").append(partidoId).append(" — ")
                        .append(local).append(" vs ").append(visitante).append(" (")
                        .append(fecha).append("): ").append(goles).append(" goles\n");
            }

            return respuesta.toString();
        }
        
        if (msg.contains("total") && diceGoles) {
        	long total = golRepository.contarTotalDeGoles();
            return "⚽ Se han registrado un total de " + total + " goles en el sistema.";

        }
        
        //  condicionales 
        
        return "Lo siento, no entendí la pregunta. Puedes consultar sobre equipos, partidos, jugadores...";
    }
    private boolean contieneAlguna(String msg, List<String> palabras) {
        return palabras.stream().anyMatch(msg::contains);
    }

    public String extraerNombreTemporada(String msg) {
      
        int index = msg.toLowerCase().indexOf("temporada");
        if (index != -1 && index + 9 < msg.length()) {
            return msg.substring(index + 9).trim(); 
        }
        return "actual"; // fallback
    }
    
    private String extraerNombreDesde(String msg, String palabraClave) {
        int index = msg.toLowerCase().indexOf(palabraClave);
        if (index != -1 && index + palabraClave.length() < msg.length()) {
            return msg.substring(index + palabraClave.length()).trim().split(" ")[0];
        }
        return "";
    }
}
