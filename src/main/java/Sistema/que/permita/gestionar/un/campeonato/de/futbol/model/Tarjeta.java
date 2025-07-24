package Sistema.que.permita.gestionar.un.campeonato.de.futbol.model;

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
@Table(name = "tarjetas")
public class Tarjeta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "El minuto debe ser positivo")
    private int minuto;

    @NotBlank(message = "El tipo de tarjeta es obligatorio")
    private String tipo;

    private String motivo;

    @NotNull(message = "El jugador es obligatorio")
    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @NotNull(message = "El partido es obligatorio")
    @ManyToOne
    @JoinColumn(name = "partido_id")
    private Partido partido;

}
