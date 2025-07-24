package Sistema.que.permita.gestionar.un.campeonato.de.futbol.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JugadorSuspendido {
	 private Jugador jugador;
	    private int amarillas;
	    private int rojas;
	    private String motivo;
	    private LocalDate fechaHabilitado; // NUEVO CAMPO

}
