package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.ChatMessage;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.ChatMessageRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PartidoRepository;

@Service
public class ChatBotService {
	@Autowired
    private ChatMessageRepository chatRepo;
    
    @Autowired
    private PartidoRepository partidoRepo;  // Tus repositorios existentes
    
    public ChatMessage procesarPregunta(String pregunta) {
        // Guardar pregunta del usuario
        ChatMessage userMsg = new ChatMessage(null,pregunta, "user", LocalDateTime.now());
        chatRepo.save(userMsg);
        
        // Generar respuesta
        String respuesta = generarRespuesta(pregunta);
        ChatMessage botMsg = new ChatMessage(null,respuesta, "bot", LocalDateTime.now());
        chatRepo.save(botMsg);
        
        return botMsg;
    }
    
    private String generarRespuesta(String pregunta) {
        pregunta = pregunta.toLowerCase();
        
        if (pregunta.contains("resultado")) {
            return procesarResultados(pregunta);
        } else if (pregunta.contains("tabla")) {
            return "La tabla de posiciones es: ..."; // Implementa esto
        } else {
            return "No entendí. Pregunta sobre resultados o la tabla de posiciones.";
        }
    }
    
    private String procesarResultados(String pregunta) {
        // Ejemplo: "¿Cuál fue el resultado entre Boca y River?"
        // Extraer equipos y buscar en partidoRepo
        return "El último partido terminó 2-1"; // Cambia esto
    }
}
