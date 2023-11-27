package estacionamentos.interfaces;

import java.time.LocalDateTime;

public interface CalcularUsoDeVaga {

    /**
    * Calcular o valor do uso da vaga
    * @param getEntrada Data e hora do uso da vaga de entrada.
    * @param getSaida Data e hora do uso da vaga de saida.
    * @return Resultado valor do uso da vaga.
    * */

    double valorPago(LocalDateTime getEntrada, LocalDateTime getSaida);

}
