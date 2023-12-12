package estacionamentos;

import java.time.LocalDateTime;
import java.time.LocalTime;

import estacionamentos.Enums.TipoTurno;
import estacionamentos.interfaces.CalcularUsoDeVaga;

public class UsoDeVagaTurno implements CalcularUsoDeVaga {

    private final double VALOR_FRACAO = 4.0;
    private final double VALOR_TURNO = 200.0;
    private TipoTurno tipoTurno;


    /**
     * Calcula o valor a ser pago pelo uso da vaga com base no período de estacionamento.
     * Este método verifica se o veículo está estacionado durante um turno específico (manhã ou tarde).
     * Se estiver dentro de um turno, um valor fixo é cobrado. Caso contrário, o valor é calculado
     * com base no tempo estacionado fora do turno, utilizando uma taxa por fração de tempo.
     *
     * @param getEntrada Hora de entrada do veículo na vaga.
     * @param getSaida   Hora de saída do veículo da vaga.
     * @return O valor a ser pago pelo uso da vaga.
     */
    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {

        LocalTime tempoEntrada = getEntrada.toLocalTime();
        LocalTime tempoSaida = getSaida.toLocalTime();

        // Verifica se o veículo está estacionado durante um turno específico
        if (tipoTurno.estaNoTurno(tempoEntrada)) {
            return VALOR_TURNO;
        } else {
            // Calcula o valor com base no tempo estacionado fora do turno
            long minutosEstacionados = getEntrada.until(getSaida, java.time.temporal.ChronoUnit.MINUTES);
            if(minutosEstacionados < 15){
                return VALOR_FRACAO;
            }
            return minutosEstacionados / 15.0 * VALOR_FRACAO;
        }
    }
    
}
