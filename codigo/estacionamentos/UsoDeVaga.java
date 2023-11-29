package estacionamentos;

import java.time.Duration;
import java.time.LocalDateTime;

import estacionamentos.Enums.TipoServico;
import estacionamentos.interfaces.CalcularUsoDeVaga;
import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;
import excecoes.ExcecaoServicoJaContratado;
import excecoes.ExcecaoTempoMinimoNaoAtingido;

public class UsoDeVaga {

    public final double FRACAO_USO = 0.25;
    public final double VALOR_FRACAO = 4.0;
    public final double VALOR_MAXIMO = 50.0;
    public final double MENSALIDADE = 500.0;

    private final CalcularUsoDeVaga calcularUsoDeVaga;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    public double valorPago;
    private TipoServico servico;

    /**
     * Construtor da classe UsoDeVaga.
     * 
     * @param vaga    Vaga que será utilizada.
     * @param entrada Data e hora da entrada do veículo na vaga.
     */
    public UsoDeVaga(Vaga vaga, CalcularUsoDeVaga calcularUsoDeVaga) {// *ok */
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
        this.saida = null;
        this.valorPago = 0.0;
        this.calcularUsoDeVaga = calcularUsoDeVaga;
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

    public TipoServico getTipoServico() {
        return servico;
    }

    public void setTipoServico(TipoServico servico) {
        this.servico = servico;
    }

    /**
     * Contra um serviço vinculado ao uso de vaga com tempo mínimo de permanência
     * dependento do tipo de serviço
     * 
     * @param servico o serviço pode ser MANOBRISTA, LAVAGEM, POLIMENTO
     * @return retorna o valor do serviço.
     */
    public Double contratarServico(TipoServico servico) {

        if (this.servico != null) {
            throw new ExcecaoServicoJaContratado();
        }

        Duration duration = Duration.between(entrada, saida);

        if (duration.toHours() >= servico.getTempo()) {
            this.servico = servico;
            return servico.getValor();
        }

        throw new ExcecaoTempoMinimoNaoAtingido();
    }

    @Override
    public String toString() {
        return "UsoDeVaga{" +
                "vaga=" + vaga +
                ", entrada=" + entrada +
                ", saida=" + saida +
                ", valorPago=" + valorPago +
                ", servico=" + servico + "}";
    }

    public LocalDateTime setSaida(LocalDateTime saida) {
        return this.saida = saida;
    }

    /**
     * sair da vaga
     * @return valor pago pelo uso da vaga
     */
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

    /**
     * Calcula o valor a ser pago pelo uso da vaga.
     * @return Valor a ser pago pelo uso da vaga.
     */
    public double valorPago(){
        return calcularUsoDeVaga.valorPago(getEntrada(), getSaida());
    }


}
