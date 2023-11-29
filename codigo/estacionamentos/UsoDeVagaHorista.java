package estacionamentos;

import java.time.LocalDateTime;

import estacionamentos.interfaces.CalcularUsoDeVaga;

public class UsoDeVagaHorista implements CalcularUsoDeVaga {

    public final double VALOR_FRACAO = 4.0;

    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
        return 15.0;
    }

}

