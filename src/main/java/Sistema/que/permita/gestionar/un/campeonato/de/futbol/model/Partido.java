package Sistema.que.permita.gestionar.un.campeonato.de.futbol.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "partidos")
public class Partido {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha del partido es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora del partido es obligatoria")
    private LocalTime hora;

    @NotNull(message = "El equipo local es obligatorio")
    @ManyToOne
    @JoinColumn(name = "equipo_local_id")
    private Equipo equipoLocal;

    @NotNull(message = "El equipo visitante es obligatorio")
    @ManyToOne
    @JoinColumn(name = "equipo_visitante_id")
    private Equipo equipoVisitante;

    @NotBlank(message = "El nombre del estadio es obligatorio")
    private String estadio;

    @NotBlank(message = "El árbitro es obligatorio")
    private String arbitro;

    @NotNull(message = "La temporada es obligatoria")
    @ManyToOne
    @JoinColumn(name = "temporada_id")
    private Temporada temporada;

    @Min(value = 0, message = "El resultado local no puede ser negativo")
    private int resultadoLocal;

    @Min(value = 0, message = "El resultado visitante no puede ser negativo")
    private int resultadoVisitante;

    @NotBlank(message = "El estado del partido es obligatorio")
    private String estado;

}
