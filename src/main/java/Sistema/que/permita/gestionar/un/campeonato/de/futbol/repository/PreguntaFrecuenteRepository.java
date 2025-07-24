package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.PreguntaFrecuenta;


public interface PreguntaFrecuenteRepository extends JpaRepository<PreguntaFrecuenta, Long> {
	
	List<PreguntaFrecuenta> findByPreguntaContainingIgnoreCase(String texto);
	@Query("SELECT p FROM PreguntaFrecuenta p WHERE LOWER(p.pregunta) LIKE LOWER(CONCAT('%', :texto, '%'))")
	List<PreguntaFrecuenta> buscarPorTexto(@Param("texto") String texto);
}
