package estacionamentos;

import java.time.LocalDateTime;

import estacionamentos.interfaces.CalcularUsoDeVaga;
import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;

public class UsoDeVagaMensalista implements CalcularUsoDeVaga {

    public final double MENSALIDADE = 500.0;

    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
        return MENSALIDADE;
    }

}
