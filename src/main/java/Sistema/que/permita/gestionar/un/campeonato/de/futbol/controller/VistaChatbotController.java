package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaChatbotController {
	@GetMapping("/chatbot")
    public String mostrarChatbot() {
        return "chatbot"; // chatbot.html debe estar en templates
    }

}
