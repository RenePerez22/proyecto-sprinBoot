package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Rol;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.RolRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.RolService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class RolServiceImpl implements RolService {
	 private final RolRepository rolRepository;
	 
	 public RolServiceImpl(RolRepository rolRepository) {
	        this.rolRepository = rolRepository;
	    }

	@Override
	public List<Rol> listarRoles() {
		 return rolRepository.findAll();
	}

	@Override
	public Rol obtenerRolPorId(Long id) {
		return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
	}

	@Override
	public Rol crearRol(Rol rol) {
		if (rolRepository.findByNombre(rol.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un rol con ese nombre");
        }
        return rolRepository.save(rol);
	}

	@Override
	public Rol actualizarRol(Long id, Rol rol) {
		 Rol rolExistente = obtenerRolPorId(id);
	        rolExistente.setNombre(rol.getNombre());
	        return rolRepository.save(rolExistente);
	}

	@Override
	public void eliminarRol(Long id) {
		 if (!rolRepository.existsById(id)) {
	            throw new EntityNotFoundException("Rol no encontrado con id: " + id);
	        }
	        rolRepository.deleteById(id);
		
	}

}
