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

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Rol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.RolService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RolController {
	   private final RolService rolService;

	    public RolController (RolService rolService) {
	        this.rolService = rolService;
	    }

	    // Listar todos los roles
	    @GetMapping
	    public String listarRoles(Model model) {
	        List<Rol> roles = rolService.listarRoles();
	        model.addAttribute("roles", roles);
	        return "roles/listar";  // plantilla: roles/listar.html
	    }

	    // Mostrar formulario para crear un rol
	    @GetMapping("/nuevo")
	    public String mostrarFormularioCrearRol(Model model) {
	        model.addAttribute("rol", new Rol());
	        return "roles/crear";  // plantilla: roles/crear.html
	    }

	    // Guardar nuevo rol
	    @PostMapping
	    public String crearRol(@Valid @ModelAttribute Rol rol, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            return "roles/crear";
	        }
	        try {
	            rolService.crearRol(rol);
	        } catch (IllegalArgumentException e) {
	            model.addAttribute("error", e.getMessage());
	            return "roles/crear";
	        }
	        return "redirect:/roles";
	    }

	    // Mostrar formulario para editar rol
	    @GetMapping("/editar/{id}")
	    public String mostrarFormularioEditarRol(@PathVariable Long id, Model model) {
	        Rol rol = rolService.obtenerRolPorId(id);
	        model.addAttribute("rol", rol);
	        return "roles/editar";  // plantilla: roles/editar.html
	    }

	    // Actualizar rol
	    @PostMapping("/editar/{id}")
	    public String actualizarRol(@PathVariable Long id, @Valid @ModelAttribute Rol rol, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            return "roles/editar";
	        }
	        try {
	            rolService.actualizarRol(id, rol);
	        } catch (IllegalArgumentException e) {
	            model.addAttribute("error", e.getMessage());
	            return "roles/editar";
	        }
	        return "redirect:/roles";
	    }

	    // Eliminar rol
	    @GetMapping("/eliminar/{id}")
	    public String eliminarRol(@PathVariable Long id) {
	        rolService.eliminarRol(id);
	        return "redirect:/roles";
	    }

}
