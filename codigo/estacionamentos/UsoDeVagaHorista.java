package estacionamentos;

import java.time.LocalDateTime;

import estacionamentos.interfaces.CalcularUsoDeVaga;
import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;

public class UsoDeVagaHorista implements CalcularUsoDeVaga {

    public final double VALOR_FRACAO = 4.0;

    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
//        if (getSaida != null) {
//            long minutosEstacionados = getEntrada.until(getSaida, java.time.temporal.ChronoUnit.MINUTES);
//            return minutosEstacionados / 15.0 * VALOR_FRACAO;
//        }
//        return 0.0;

        return 15.0;
    }

}

