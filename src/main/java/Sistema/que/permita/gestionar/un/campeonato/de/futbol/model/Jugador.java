package Sistema.que.permita.gestionar.un.campeonato.de.futbol.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "jugadores")
public class Jugador {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del jugador es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La posición del jugador es obligatoria")
    private String posicion;

    @NotBlank(message = "El estado del jugador es obligatorio")
    private String estado;

    @NotNull(message = "El equipo es obligatorio")
    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

}
