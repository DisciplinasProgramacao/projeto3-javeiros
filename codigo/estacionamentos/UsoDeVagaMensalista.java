package estacionamentos;

import java.time.LocalDateTime;

import estacionamentos.interfaces.CalcularUsoDeVaga;

public class UsoDeVagaMensalista implements CalcularUsoDeVaga {

    private static final double MENSALIDADE = 500.0;

    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
        return MENSALIDADE;
    }

}
