package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Rol;

public interface RolService {
	 	List<Rol> listarRoles();
	    Rol obtenerRolPorId(Long id);
	    Rol crearRol(Rol rol);
	    Rol actualizarRol(Long id, Rol rol);
	    void eliminarRol(Long id);

}
