package estacionamentos;
import java.time.Duration;
import java.time.LocalDateTime;

import estacionamentos.Enums.TipoServico;
import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoSaidaJaFinalizada;
import excecoes.ExcecaoServicoJaContratado;
import excecoes.ExcecaoTempoMinimoNaoAtingido;

public class UsoDeVaga {

    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;

	private TipoServico servico;

    /**
     * Construtor da classe UsoDeVaga.
     * 
     * @param vaga Vaga que será utilizada.
     * @param entrada Data e hora da entrada do veículo na vaga.
     */
    public UsoDeVaga(Vaga vaga) {//*ok */
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
        this.saida = null;
        this.valorPago = 0.0;
    }

    /**
     * Registra a saída do veículo da vaga e calcula o valor a ser pago.
     * 
     * @param saida Data e hora da saída do veículo da vaga.
     */
    public double sair() {//*ok 
        if(saida != null){

            throw new ExcecaoSaidaJaFinalizada();
        }
        if(vaga.sair()){
            this.saida = LocalDateTime.now();
            return this.valorPago = valorPago();
        } else {
            throw new ExcecaoNaoEhPossivelSairDaVaga();
        }
    }


    /**
     * Calcula o valor a ser pago com base no tempo que o veículo permaneceu estacionado.
     */
    private double valorPago() {//*ok */
        if (saida != null) {
            long minutosEstacionados = entrada.until(saida, java.time.temporal.ChronoUnit.MINUTES);
            double valor = minutosEstacionados / 15.0 * VALOR_FRACAO;
            if(servico != null){
                return valorPago = Math.min(valor, VALOR_MAXIMO) + servico.getValor();
            }
        }
        return 0.0;
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

	/**
	 * Contra um serviço vinculado ao uso de vaga com tempo mínimo de permanência dependento do tipo de serviço
	 * @param servico o serviço pode ser MANOBRISTA, LAVAGEM, POLIMENTO
	 * @return retorna o valor do serviço.
	 */
	public Double contratarServico(TipoServico servico) {

		if(this.servico != null){
			throw new ExcecaoServicoJaContratado();
		}

		Duration duration = Duration.between(entrada, saida);

		if(duration.toHours() >= servico.getTempo()){
			this.servico = servico;
			return servico.getValor();
		}

		throw new ExcecaoTempoMinimoNaoAtingido();
	}

}