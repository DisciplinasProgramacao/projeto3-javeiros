package estacionamentos;

import java.time.LocalDateTime;
import java.time.Duration;
import excecoes.ExcecaoTempoMinimoLavagem;
import excecoes.ExcecaoTempoMinimoPolimento;

public class UsoDeVaga {

    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;
    private boolean lavagem;
    private boolean polimento;

    /**
     * Construtor da classe UsoDeVaga.
     * 
     * @param vaga Vaga que será utilizada.
     * @param entrada Data e hora da entrada do veículo na vaga.
     */
    public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = null;
        this.valorPago = 0.0;
        this.lavagem = false;
        this.polimento = false;
    }

    /**
     * Registra a saída do veículo da vaga e calcula o valor a ser pago.
     * 
     * @param saida Data e hora da saída do veículo da vaga.
     */
    public void registrarSaida(LocalDateTime saida) {
        this.saida = saida;
        calcularValorPago();
    }

    /**
     * Verifica se a entrada ou saída do veículo está dentro do mês especificado.
     * 
     * @param mes Mês a ser verificado.
     * @return true se estiver dentro do mês, false caso contrário.
     */
    public boolean estaDentroDoMes(int mes) {
        if (entrada != null && (entrada.getMonthValue() == mes || (saida != null && saida.getMonthValue() == mes))) {
            return true;
        }
        return false;
    }

    /**
     * Calcula o valor a ser pago com base no tempo que o veículo permaneceu estacionado.
     */
    private void calcularValorPago() {
        if (saida != null) {
            long minutosEstacionados = entrada.until(saida, java.time.temporal.ChronoUnit.MINUTES);
            double valor = minutosEstacionados / 15.0 * VALOR_FRACAO;
            valorPago = Math.min(valor, VALOR_MAXIMO);
        }
    }

    /**
     * Calcula o tempo de permanência do veículo em minutos.
     * 
     * @return long representando os minutos de permanência.
     */
    private long getPermanencia() {
        if (saida != null) {
            return Duration.between(entrada, saida).toMinutes();
        }
        return 0;
    }

    public void contratarLavagem() throws ExcecaoTempoMinimoLavagem {
        if (getPermanencia() < 60) {
            throw new ExcecaoTempoMinimoLavagem("Tempo mínimo de 1h para lavagem não atendido.");
        }
        this.lavagem = true;
        this.valorPago += 20.0;
    }

    public void contratarPolimento() throws ExcecaoTempoMinimoPolimento {
        if (getPermanencia() < 120) {
            throw new ExcecaoTempoMinimoPolimento("Tempo mínimo de 2h para polimento não atendido.");
        }
        this.polimento = true;
        this.valorPago += 45.0;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public double getValorPago() {
        return valorPago;
    }
}
