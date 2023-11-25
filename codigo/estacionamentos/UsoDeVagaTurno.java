package estacionamentos;

import java.time.LocalDateTime;
import java.time.LocalTime;

import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;

public class UsoDeVagaTurno extends UsoDeVaga {

    public UsoDeVagaTurno(Vaga vaga) {
        super(vaga);
    }

    @Override
    public double sair() {
        if (getSaida() != null) {
            throw new ExcecaoSaidaJaFinalizada();
        }

        if (getVaga().sair()) {
            setSaida(LocalDateTime.now());
            return this.valorPago = valorPago();
        } else {
            throw new ExcecaoNaoEhPossivelSairDaVaga();
        }
    }

    @Override
    public double valorPago() {
        LocalDateTime horarioSaida = getSaida();

        if ((horarioSaida.toLocalTime().isAfter(LocalTime.of(8, 0))
                && horarioSaida.toLocalTime().isBefore(LocalTime.of(12, 0)))
                || (horarioSaida.toLocalTime().isAfter(LocalTime.of(12, 1))
                        && horarioSaida.toLocalTime().isBefore(LocalTime.of(18, 0)))) {
            return 0.0;
        } else {

            long minutosEstacionados = getEntrada().until(getSaida(), java.time.temporal.ChronoUnit.MINUTES);
            return minutosEstacionados / 15.0 * VALOR_FRACAO;
        }

    }
}
