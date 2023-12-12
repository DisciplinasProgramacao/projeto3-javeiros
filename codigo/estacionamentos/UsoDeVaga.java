package estacionamentos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

import estacionamentos.Enums.TipoServico;
import estacionamentos.Enums.TipoUso;
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
    private double valorPago;
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
            return "UsoDeVaga \n" +
                    "Vaga:" + vaga + ",\n"+
                    "Entrada: " + entrada +",\n"+
                    "Saida: " + saida +",\n"+
                    "ValorPago: " + valorPago +",\n"+
                    "Servico: " + servico + ",\n";
        }

    public LocalDateTime setSaida(LocalDateTime saida) {
        return this.saida = saida;
    }

    /**
     * sair da vaga
     * @return valor pago pelo uso da vaga
     */
    public double sair(TipoUso tipoUso) {
        if (getSaida() != null) {
            throw new ExcecaoSaidaJaFinalizada();
        }

        if (getVaga().sair()) {
            setSaida(LocalDateTime.now());
            return this.valorPago = valorPago(tipoUso);
        } else {
            throw new ExcecaoNaoEhPossivelSairDaVaga();
        }
    }

    /**
     * Calcula o valor a ser pago pelo uso da vaga.
     * @return Valor a ser pago pelo uso da vaga.
     */
    public double valorPago(TipoUso tipoUso){

        CalcularUsoDeVaga calcularUsoDeVaga;

        switch (tipoUso) {
            case HORISTA:
                calcularUsoDeVaga = new UsoDeVagaHorista();
                return calcularUsoDeVaga.valorPago(entrada, saida);
            case TURNO:
                calcularUsoDeVaga = new UsoDeVagaTurno();
                return calcularUsoDeVaga.valorPago(entrada, saida);
            case MENSALISTA:
                calcularUsoDeVaga = new UsoDeVagaMensalista();
                return calcularUsoDeVaga.valorPago(entrada, saida);
            default:
                return 0.0;
        }

    }

    /**
    * Verifica se a entrada do veículo na vaga ocorreu entre duas datas especificadas.
    * 
    * @param dataInicio Data de início do intervalo a ser verificado.
    * @param dataFim    Data de fim do intervalo a ser verificado.
    * @return           Retorna true se a entrada ocorreu entre as datas especificadas, caso contrário, retorna false.
    */
    public boolean ocorrenciaEntreDatas(LocalDateTime dataInicio, LocalDateTime dataFim){
        return this.entrada.isAfter(dataInicio) && this.entrada.isBefore(dataFim);
    }
    

    public static class valorPagoComparator implements Comparator<UsoDeVaga> {

        @Override
        public int compare(UsoDeVaga usoDeVAga1, UsoDeVaga usoDeVAga2) {
            return Double.compare(usoDeVAga1.getValorPago(), usoDeVAga2.getValorPago());
        }
    }

    public static class DateComparator implements Comparator<UsoDeVaga> {
        @Override
        public int compare(UsoDeVaga usoDeVaga1, UsoDeVaga usoDeVaga2) {
            return usoDeVaga1.getEntrada().compareTo(usoDeVaga2.getEntrada());
        }
    }

}
