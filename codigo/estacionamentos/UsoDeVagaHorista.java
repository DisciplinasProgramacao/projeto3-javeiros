package estacionamentos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import estacionamentos.interfaces.CalcularUsoDeVaga;

public class UsoDeVagaHorista implements CalcularUsoDeVaga {

    private final double VALOR_POR_FRACAO = 4.0;
    private final long FRACAO_TEMPO_MINUTOS = 15;

    /**
     * Calcula o valor a ser pago pelo uso da vaga com base em horas utilizadas no estacionamento.
     * @param getEntrada Hora de entrada do veículo na vaga.
     * @param getSaida Hora de saída do veículo da vaga.
     * @return O valor a ser pago pelo uso da vaga.
     * */
    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
        long minutosEstacionados = ChronoUnit.MINUTES.between(getEntrada, getSaida);
        long totalFracoes = (minutosEstacionados + FRACAO_TEMPO_MINUTOS - 1) / FRACAO_TEMPO_MINUTOS;
        return totalFracoes * VALOR_POR_FRACAO;
    }

}

