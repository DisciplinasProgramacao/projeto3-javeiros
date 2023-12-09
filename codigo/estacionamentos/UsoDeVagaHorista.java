package estacionamentos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import estacionamentos.interfaces.CalcularUsoDeVaga;

public class UsoDeVagaHorista implements CalcularUsoDeVaga {

    private static final double VALOR_POR_FRACAO = 4.0;
    private static final long FRACAO_TEMPO_MINUTOS = 15;

    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
        long minutosEstacionados = ChronoUnit.MINUTES.between(getEntrada, getSaida);
        long totalFracoes = (minutosEstacionados + FRACAO_TEMPO_MINUTOS - 1) / FRACAO_TEMPO_MINUTOS; // Arredonda para cima
        return totalFracoes * VALOR_POR_FRACAO;
    }

}

