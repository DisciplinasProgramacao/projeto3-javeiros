package estacionamentos;

import java.time.LocalDateTime;
import java.time.LocalTime;

import estacionamentos.interfaces.CalcularUsoDeVaga;
import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;

public class UsoDeVagaTurno implements CalcularUsoDeVaga {

    public final double VALOR_FRACAO = 4.0;

    @Override
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

    }
}
