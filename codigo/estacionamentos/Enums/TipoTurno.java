package estacionamentos.Enums;
import java.time.LocalTime;

public enum TipoTurno {
    MANHA(LocalTime.of(8, 0), LocalTime.of(12, 0)),
    TARDE(LocalTime.of(12, 1), LocalTime.of(18, 0)),
    NOITE(LocalTime.of(18, 1), LocalTime.of(23, 59));

    private final LocalTime inicio;
    private final LocalTime fim;

    TipoTurno(LocalTime inicio, LocalTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public boolean estaNoTurno(LocalTime hora) {
        return !hora.isBefore(inicio) && !hora.isAfter(fim);
    }
}
