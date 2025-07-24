package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service;

import java.util.List;
import java.util.Optional;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;

public interface UsuarioService {
	  List<Usuario> listarUsuarios();
	    Usuario obtenerUsuarioPorId(Long id);
	    Usuario crearUsuario(Usuario usuario);
	    Usuario actualizarUsuario(Long id, Usuario usuario);
	    void eliminarUsuario(Long id);
	    Optional<Usuario> buscarPorCorreoYPassword(String correo, String password);

}
