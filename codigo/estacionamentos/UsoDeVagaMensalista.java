package estacionamentos;

import java.time.LocalDateTime;
import java.time.LocalTime;

import estacionamentos.Enums.TipoTurno;
import estacionamentos.interfaces.CalcularUsoDeVaga;

public class UsoDeVagaMensalista implements CalcularUsoDeVaga {

    public final double MENSALIDADE = 500.0;

    /**
     * Retorna o valor Mensal do uso da vaga.
     * @param getEntrada Hora de entrada do veículo na vaga.
     * @param getSaida Hora de saída do veículo da vaga.
     * @return O valor a ser pago pelo uso da vaga.
     * */
    @Override
    public double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida) {
        return MENSALIDADE;
    }

}
