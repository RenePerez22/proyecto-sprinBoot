package Sistema.que.permita.gestionar.un.campeonato.de.futbol.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Equipo;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.EquipoService;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.TemporadaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoService equipoService;
    private final TemporadaService temporadaService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/escudos/";


    public EquipoController(EquipoService equipoService, TemporadaService temporadaService) {
        this.equipoService = equipoService;
        this.temporadaService = temporadaService;
    }

    @GetMapping
    public String listarEquipos(Model model) {
        List<Equipo> equipos = equipoService.listarEquipos();
        model.addAttribute("equipos", equipos);
        return "equipos/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("equipo", new Equipo());
        model.addAttribute("temporadas", temporadaService.listarTemporadas());
        return "equipos/crear";
    }

    @PostMapping
    public String crearEquipo(
            @Valid @ModelAttribute Equipo equipo,
            BindingResult result,
            @RequestParam("escudoFile") MultipartFile escudoFile,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("temporadas", temporadaService.listarTemporadas());
            return "equipos/crear";
        }

        if (escudoFile != null && !escudoFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + escudoFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                escudoFile.transferTo(filePath.toFile());

                equipo.setEscudoUrl("/uploads/escudos/" + fileName);// Guarda la URL relativa
            } catch (IOException e) {
                e.printStackTrace();
                result.rejectValue("escudoUrl", "error.equipo", "Error al guardar el archivo");
                model.addAttribute("temporadas", temporadaService.listarTemporadas());
                return "equipos/crear";
            }
        }

        equipoService.crearEquipo(equipo);
        return "redirect:/equipos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Equipo equipo = equipoService.obtenerEquipoPorId(id);
        model.addAttribute("equipo", equipo);
        model.addAttribute("temporadas", temporadaService.listarTemporadas());
        return "equipos/editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarEquipo(
            @PathVariable Long id,
            @Valid @ModelAttribute Equipo equipo,
            BindingResult result,
            @RequestParam(value = "escudoFile", required = false) MultipartFile escudoFile,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("temporadas", temporadaService.listarTemporadas());
            return "equipos/editar";
        }

        if (escudoFile != null && !escudoFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + escudoFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);

                Path uploadPath1 = Paths.get(System.getProperty("user.dir"), "uploads", "escudos");

                if (!Files.exists(uploadPath1)) {
                    Files.createDirectories(uploadPath1);
                }


                Path filePath = uploadPath1.resolve(fileName);
                escudoFile.transferTo(filePath.toFile());

                equipo.setEscudoUrl("/uploads/escudos/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
                result.rejectValue("escudoUrl", "error.equipo", "Error al guardar el archivo");
                model.addAttribute("temporadas", temporadaService.listarTemporadas());
                return "equipos/editar";
            }
        }

        equipoService.actualizarEquipo(id, equipo);
        return "redirect:/equipos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminarEquipo(id);
        return "redirect:/equipos";
}
}
