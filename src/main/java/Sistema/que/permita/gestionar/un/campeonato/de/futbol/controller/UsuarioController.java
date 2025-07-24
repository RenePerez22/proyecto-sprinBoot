package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.RolService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioService usuarioService;
    private final RolService rolService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuarios/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.listarRoles());
        return "usuarios/crear";
    }

    @PostMapping
    public String crearUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.listarRoles());
            return "usuarios/crear";
        }
        try {
            usuarioService.crearUsuario(usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", rolService.listarRoles());
            return "usuarios/crear";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listarRoles());
        return "usuarios/editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.listarRoles());
            return "usuarios/editar";
        }
        try {
            usuarioService.actualizarUsuario(id, usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", rolService.listarRoles());
            return "usuarios/editar";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }

}
