package estacionamentos;

import java.time.LocalDateTime;

import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;

public class UsoDeVagaHorista extends UsoDeVaga {
    
    public UsoDeVagaHorista(Vaga vaga) {
        super(vaga);
    }

    @Override
    public double valorPago() {
        if (getSaida() != null) {
            long minutosEstacionados = getEntrada().until(getSaida(), java.time.temporal.ChronoUnit.MINUTES);
            return minutosEstacionados / 15.0 * VALOR_FRACAO;
        }
        return 0.0;
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

}

