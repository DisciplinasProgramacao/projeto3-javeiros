package estacionamentos;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import estacionamentos.Enums.TipoUso;
import estacionamentos.interfaces.UsoDeVagaFactory;
import excecoes.ExcecaoRelatorioVazio;

public class Veiculo {
	private int count;
	private String placa;
	private LinkedList<UsoDeVaga> usoDeVagas;
	private UsoDeVagaFactory usoDeVagaFactory;
	private TipoUso tipoUso;

	public TipoUso getTipoUso() {
		return tipoUso;
	}

	public void setTipoUso(TipoUso tipoUso) {
		this.tipoUso = tipoUso;
	}

	public String getPlaca() {
		return placa;
	}x

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	/**
	 * Retorna o uso de vaga do veículo para uma vaga específica.
	 * @param vaga Vaga a ser pesquisada. 
	 * @return Uso de vaga do veículo para a vaga pesquisada.
	 */
	public UsoDeVaga getUsoDeVaga(Vaga vaga) {
		for (int i = 0; i < usoDeVagas.size(); i++) {
			if ((usoDeVagas.get(i)).getVaga().equals(vaga)) {
				return usoDeVagas.get(i);
			}
		}
		return null;
	}

	/**
	 * Retorna uma lista imutável de usos de vaga do veículo.
	 * @return Lista imutável de usos de vaga do veículo.
	 */
	public List<UsoDeVaga> getListUsoDeVaga() {
		return List.copyOf(this.usoDeVagas);
	}

	public Veiculo(String placa, TipoUso tipoUso, UsoDeVagaFactory usoDeVagaFactory) {
		this.placa = placa;
		this.tipoUso = tipoUso;
		this.usoDeVagas = new LinkedList<>();
		this.usoDeVagaFactory = usoDeVagaFactory;
	}

	/**
	 * Estacionar veiculo e adiciona na lista de uso de vagas
	 * 
	 * @param vaga Classe vaga que contem a disponibilidade de estacionamento
	 */
	public void estacionar(Vaga vaga) {
		UsoDeVaga usoDeVaga = criarUsoDeVaga(vaga);
		usoDeVagas.add(usoDeVaga);
	}

	/**
	 * Cria um novo uso de vaga de acordo com o tipo de uso do veículo.
	 * 
	 * @param vaga Vaga a ser utilizada.
	 * @return Uso de vaga criado.
	 */
	public UsoDeVaga criarUsoDeVaga(Vaga vaga) {
		return usoDeVagaFactory.criarUsoDeVaga(vaga);
	}

	/**
	 * Sair veiculo da vaga de estacionamento
	 * 
	 * @return retorna o valor do veiculo
	 */
	public double sair() {

		for (UsoDeVaga usoDeVaga : usoDeVagas) {
			if (usoDeVaga.getSaida() == null) {
				return usoDeVaga.sair();
			}
		}
		return 0.0;
	}

	/**
	 * Valor total de todo o historico do veiculo
	 * 
	 * @return retorna o valor
	 */
	public double totalArrecadado() {

		double total = 0;

		for (UsoDeVaga uso : usoDeVagas) {
			total += uso.getValorPago();
		}

		return total;
	}

	/**
	 * Retorna o valor total arrecado em um determinado mes
	 * 
	 * @param mes recebe o mês como parametro entre 1 e 12.
	 * @return
	 */
	public double arrecadadoNoMes(int mes) {

		double total = 0;

		for (UsoDeVaga uso : usoDeVagas) {
			if (uso.getEntrada().getMonthValue() == mes) {
				total += uso.getValorPago();
			}
		}

		return total;
	}

	/**
	 * Retorna o valor total arrecado em um determinado mes
	 * 
	 * @return retorna o valor do mes atual
	 */
	public int usoMensalCorrente() {

		int total = 0;

		if (tipoUso != TipoUso.MENSALISTA) {
			return total;
		}

		for (UsoDeVaga uso : usoDeVagas) {
			if (uso.getEntrada().getMonthValue() == LocalDateTime.now().getMonthValue()
					&& uso.getEntrada().getYear() == LocalDateTime.now().getYear()) {
				total++;
			}
		}

		return total;
	}

	/**
	 * Total de usos pelo veiculo
	 * 
	 * @return retorna o valor como int, sendo o total de uso
	 */
	public int totalDeUsos() {
		count = usoDeVagas.size();
		return count;
	}

	/**
	 * Método para gerar um relatório detalhado de todos os usos de vaga do veículo.
	 * 
	 * @return String contendo o relatório.
	 */
	public String gerarRelatorio() {
		// ! implementar ordenação ordem crescente de data ou decrescente de valor
		if (usoDeVagas.isEmpty()) {
			throw new ExcecaoRelatorioVazio();
		}

		StringBuilder relatorio = new StringBuilder();
		relatorio.append("Relatório de Uso para Veículo com Placa: ").append(placa).append("\n");

		for (UsoDeVaga uso : usoDeVagas) {
			relatorio.append(" \n Vaga: ").append(uso.getVaga().toString())
					.append(",\n Entrada: ").append(uso.getEntrada())
					.append(",\n Saída: ").append(uso.getSaida())
					.append(",\n Tipo de Uso: ").append(uso.getClass().getSimpleName())
					.append(",\n Valor Pago: R$").append(uso.getValorPago())
					.append("\n");
		}

		return relatorio.toString();
	}

	@Override
	public String toString() {
		return "Placa " + placa;
	}


	public String historico(LocalDateTime dataInicio, LocalDateTime dataFim){
		String historico = "*******************\nVEICULO\nPLACA: " + this.placa + "\n\n";
		for(UsoDeVaga uso : usoDeVagas){
			if(uso.ocorrenciaEntreDatas(dataInicio, dataFim)){
				historico += uso.toString();
			}
		}
		historico += "*******************";
		return historico;
	}

}
