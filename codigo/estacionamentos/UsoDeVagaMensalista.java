package estacionamentos;

import java.time.LocalDateTime;

import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;

public class UsoDeVagaMensalista extends UsoDeVaga {

    public UsoDeVagaMensalista(Vaga vaga) {
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
        return MENSALIDADE;
    }

}
