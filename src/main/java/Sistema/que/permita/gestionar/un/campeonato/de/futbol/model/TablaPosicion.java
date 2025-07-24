package Sistema.que.permita.gestionar.un.campeonato.de.futbol.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TablaPosicion {
    private final Equipo equipo;
    private int jugados = 0;
    private int ganados = 0;
    private int empatados = 0;
    private int perdidos = 0;
    private int golesFavor = 0;
    private int golesContra = 0;
    private int puntos = 0;

    // Método para actualizar estadísticas tras un partido
    public void jugarPartido(int gf, int gc) {
        jugados++;
        golesFavor += gf;
        golesContra += gc;
        if (gf > gc) {
            ganados++;
            puntos += 3;
        } else if (gf == gc) {
            empatados++;
            puntos += 1;
        } else {
            perdidos++;
        }
    }

    // Getter personalizado para diferencia de goles
    public int getDiferencia() {
        return golesFavor - golesContra;
    }
}
