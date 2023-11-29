package estacionamentos;

import java.time.LocalTime;

public class Turno {
    private LocalTime inicio;
    private LocalTime fim;

    /**
     * Construtor para a classe Turno.
     *
     * @param inicio Horário de início do turno.
     * @param fim    Horário de fim do turno.
     * @throws IllegalArgumentException Se o turno não for válido.
     */
    public Turno(LocalTime inicio, LocalTime fim) {
        if (isTurnoValido(inicio, fim)) {
            this.inicio = inicio;
            this.fim = fim;
        } else {
            throw new IllegalArgumentException("Turno inválido. Deve escolher entre manhã, tarde ou noite dentro dos horários pré-estabelecidos. Faça o pagamento como horista.");
        }
    }

    /**
     * Método que verifica se o turno é válido, considerando os horários pré-estabelecidos para manhã, tarde e noite.
     *
     * @param inicio Horário de início do turno.
     * @param fim    Horário de fim do turno.
     * @return true se o turno for válido, false caso o contrário.
     */
    public boolean isTurnoValido(LocalTime inicio, LocalTime fim) {
        return (
        ((inicio.isAfter(LocalTime.of(7, 59)) && inicio.isBefore(LocalTime.of(12, 0))) && fim.isBefore(LocalTime.of(12, 1))) 
        ||
        ((inicio.isAfter(LocalTime.of(12, 0)) && inicio.isBefore(LocalTime.of(18, 0))) && fim.isBefore(LocalTime.of(18, 1)))
        ||
        ((inicio.isAfter(LocalTime.of(18, 0)) && inicio.isBefore(LocalTime.of(23, 59))) && fim.isBefore(LocalTime.of(24, 0))));
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }
}