package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.UsuarioRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

    	Optional<Usuario> usuarioOpt = usuarioService.buscarPorCorreoYPassword(correo, password);

    	if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuario", usuario);
         // Opcional: Hashear la contraseña antigua en el primer login
            if (!usuario.getPassword().startsWith("$2a$")) {
                usuario.setPassword(passwordEncoder.encode(password));
                usuarioRepository.save(usuario);
            }
            // Redirección por rol
            String rol = usuario.getRol().getNombre().toUpperCase();
            if (rol.equals("ADMIN")) {
                return "redirect:/admin/menu";
            } else if (rol.equals("JUGADOR")) {
                return "redirect:/jugadores/menu";
            }
        }
        
        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }
}
