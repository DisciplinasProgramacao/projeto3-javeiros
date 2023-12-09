package estacionamentos;

import java.time.LocalDateTime;
import java.time.LocalTime;

import estacionamentos.interfaces.CalcularUsoDeVaga;

public class UsoDeVagaTurno implements CalcularUsoDeVaga {

    public final double VALOR_FRACAO = 4.0;
    public final double VALOR_TURNO = 200.0;

    /**
     * Calcula o valor a ser pago pelo uso da vaga com base no período de estacionamento.
     * Este método verifica se o veículo está estacionado durante um turno específico (manhã ou tarde).
     * Se estiver dentro de um turno, um valor fixo é cobrado. Caso contrário, o valor é calculado
     * com base no tempo estacionado fora do turno, utilizando uma taxa por fração de tempo.
     *
     * @param entrada Hora de entrada do veículo na vaga.
     * @param saida   Hora de saída do veículo da vaga.
     * @return O valor a ser pago pelo uso da vaga.
     */
    @Override
    public double valorPago(LocalDateTime entrada, LocalDateTime saida) {
        // Verifica se o veículo está estacionado durante um turno específico
        if (estaNoTurno(entrada, saida)) {
            return VALOR_TURNO;
        } else {
            // Calcula o valor com base no tempo estacionado fora do turno
            long minutosEstacionados = entrada.until(saida, java.time.temporal.ChronoUnit.MINUTES);
            return minutosEstacionados / 15.0 * VALOR_FRACAO;
        }
    }

    /**
     * Verifica se o período de estacionamento está dentro de um turno específico.
     * 
     * @param entrada Hora de entrada do veículo.
     * @param saida   Hora de saída do veículo.
     * @return true se estiver dentro do turno, false caso contrário.
     */
    private boolean estaNoTurno(LocalDateTime entrada, LocalDateTime saida) {
        LocalTime horaEntrada = entrada.toLocalTime();
        LocalTime horaSaida = saida.toLocalTime();

        // Aqui define os horários de início e fim dos turnos
        LocalTime inicioManha = LocalTime.of(8, 0);
        LocalTime fimManha = LocalTime.of(12, 0);
        LocalTime inicioTarde = LocalTime.of(12, 1);
        LocalTime fimTarde = LocalTime.of(18, 0);

        // Analisa se o per[iodo está dentro de um dos turnos
        return (horaEntrada.isAfter(inicioManha) && horaSaida.isBefore(fimManha)) ||
               (horaEntrada.isAfter(inicioTarde) && horaSaida.isBefore(fimTarde));
    }



    /*@Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
        LocalDateTime horarioSaida = getSaida;

        if ((horarioSaida.toLocalTime().isAfter(LocalTime.of(8, 0))
                && horarioSaida.toLocalTime().isBefore(LocalTime.of(12, 0)))
                || (horarioSaida.toLocalTime().isAfter(LocalTime.of(12, 1))
                        && horarioSaida.toLocalTime().isBefore(LocalTime.of(18, 0)))) {
            return 0.0;
        } else {

            long minutosEstacionados = getEntrada.until(getSaida, java.time.temporal.ChronoUnit.MINUTES);
            return minutosEstacionados / 15.0 * VALOR_FRACAO;
        }

    }*/
}
