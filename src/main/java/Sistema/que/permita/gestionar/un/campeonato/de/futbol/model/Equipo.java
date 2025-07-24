package Sistema.que.permita.gestionar.un.campeonato.de.futbol.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "equipos")
public class Equipo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String nombre;

    private String escudoUrl;

    @NotBlank(message = "El nombre del director técnico es obligatorio")
    private String directorTecnico;

    @NotNull(message = "La temporada es obligatoria")
    @ManyToOne
    @JoinColumn(name = "temporada_id")
    private Temporada temporada;

    @OneToMany(mappedBy = "equipo")
    private List<Jugador> jugadores;

}
