package estacionamentos;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import estacionamentos.Enums.TipoUso;

public class Cliente {

	private String nome;
	private String id;
	private LinkedList<Veiculo> veiculos;
	private TipoUso tipoUso;

	/**
	 * Construtor para a criação de um Cliente.
	 * 
	 * @param nome Nome do cliente
	 * @param id   Identificador único do cliente.
	 */
	public Cliente(String nome, String id) {
		setNome(nome);
		setId(id);
		setTipoUso(null);
		veiculos = new LinkedList<>();
	}

	public Cliente() {
	};

	/**
	 * Construtor para a criação de um Cliente especificando o Enum tipo de uso.
	 *
	 * @param nome    Nome do cliente.
	 * @param id      Identificador único do cliente.
	 * @param tipoUso Tipo de uso associado ao cliente.
	 */
	public Cliente(String nome, String id, TipoUso tipoUso) {
		setNome(nome);
		setId(id);
		setTipoUso(tipoUso);
		veiculos = new LinkedList<>();
	}

	public TipoUso getTipoUso() {
		return tipoUso;
	}

	public void setTipoUso(TipoUso tipoUso) {
		this.tipoUso = tipoUso;
		notifyTipoUsoVeiculo();
	}

	public String getNome() {
		return this.nome;
	}

	private void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return this.id;
	}

	private void setId(String id) {
		this.id = id;
	}

	/**
	 * Método para adicionar um novo veículo à lista de veículos de um Cliente
	 * específico.
	 * 
	 * @param veiculo do tipo Veiculo
	 */
	public void addVeiculo(Veiculo veiculo) {
		this.veiculos.add(veiculo);
	}

	/**
	 * Método para obter a lista de todos os veículos do Cliente.
	 *
	 * @return uma lista de objetos Veiculo
	 */
	public List<Veiculo> getVeiculos() {
		return this.veiculos;
	}

	/**
	 * Método que verifica se o Cliente possui um determinado carro a partir da
	 * verificação da placa na lista de veículos do mesmo.
	 * 
	 * @param placa do tipo String
	 * @return veiculo do tipo Veiculo caso a placa for encontrada ou nulo caso não
	 *         exista na lista de veículos a placa informada.
	 */
	public Veiculo possuiVeiculo(String placa) {
		for (int i = 0; i < veiculos.size(); i++) {
			if ((veiculos.get(i).getPlaca()).equals(placa)) {
				return veiculos.get(i);
			}
		}
		return null;
	}

	/**
	 * Método que realiza a soma do total de usos de todos os veículos de um Cliente
	 * 
	 * @return usos do tipo int com a soma de usos dos veículos
	 */
	public int totalDeUsos() {
		int usos = 0;
		for (int i = 0; i < veiculos.size(); i++) {
			if ((veiculos.get(i).totalDeUsos()) != 0) {
				usos += this.veiculos.get(i).totalDeUsos();
			}
		}
		return usos;
	}

	/**
	 * Método que informa o valor arrecadado com um veículo específico de um
	 * Cliente, a partir da verificação se o Cliente possui aquele veículo.
	 * 
	 * @param placa do tipo String
	 * @return total arrecadado do tipo double
	 */
	public double arrecadadoPorVeiculo(String placa) {
		Veiculo veiculo = possuiVeiculo(placa);

		if (veiculo != null) {
			return veiculo.totalArrecadado();
		}

		return 0.0;
	}

	/**
	 * Método que realiza a soma do total arrecadado de todos os veículos de um
	 * Cliente
	 * 
	 * @return double com o total arrecadado dos veículos do Cliente
	 */
	public double arrecadadoTotal() {
		double soma = 0;
		for (int i = 0; i < veiculos.size(); i++) {
			soma += veiculos.get(i).totalArrecadado();
		}
		return soma;
	}

	/**
	 * Método que realiza a soma do valor arrecadado com os veículos de um Cliente
	 * em um determinado mês
	 * 
	 * @param mes do tipo int
	 * @return soma do tipo double com o resultado das somas
	 */
	public double arrecadadoNoMes(int mes, int ano) {
		double soma = 0;
		for (int i = 0; i < veiculos.size(); i++) {
			if ((veiculos.get(i).arrecadadoNoMes(mes, ano)) != 0) {
				soma += this.veiculos.get(i).arrecadadoNoMes(mes, ano);
			}
		}
		return soma;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + "\nVeículos: " + veiculos;
	}

	/**
	 * Gera um histórico consolidado de todos os usos de vaga dos veículos
	 * associados a este cliente,
	 * no intervalo de datas especificado.
	 *
	 * @param dataInicio Data de início do período desejado.
	 * @param dataFim    Data de fim do período desejado.
	 * @return String contendo o histórico consolidado de todos os usos de vaga no
	 *         intervalo de datas,
	 *         formatado de maneira legível.
	 */
	public String historico(LocalDateTime dataInicio, LocalDateTime dataFim) {
		String historico = "**********************\n" + this.nome + "\n\n";
		for (Veiculo veiculo : veiculos) {
			historico += veiculo.historico(dataInicio, dataFim);
		}
		historico += "**********************\n";
		return historico;
	}

	public void notifyTipoUsoVeiculo() {
		if (veiculos != null) {
			for (Veiculo veiculo : veiculos) {
				veiculo.setTipoUso(this.tipoUso);
			}
		}

	}

	/**
	 * Obtém a quantidade total de usos mensais de todos os veículos associados ao
	 * cliente no mês corrente.
	 *
	 * Este método calcula a soma total dos usos mensais de todos os veículos
	 * associados ao cliente
	 * que ocorreram no mês corrente. Retorna o resultado.
	 *
	 * @return A quantidade total de usos mensais de todos os veículos no mês
	 *         corrente.
	 */
	public long usoMensalCorrente() {
		if (veiculos != null)
			return veiculos.stream().mapToLong(v -> v.usoMensalCorrente()).sum();
		else
			return 0;
	}

	/**
	 * Total de usos de todos os veiculos do cliente
	 * 
	 * @param mes valor do mes que foi usado.
	 * @param ano valor do ano que foi usado.
	 * @return quantidade de usos
	 */
	public int totalDeUsoNoMesAnoCliente(int mes, int ano) {

		int total = 0;

		for (Veiculo veiculo : veiculos) {
			total += veiculo.totalDeUsoNoMesAno(mes, ano);
		}

		return total;
	}

	/**
	 * Método que retorna o valor arrecadado com os usos fora do turno de um Cliente em um determinado mês e ano.
	 *
	 * @param mes valor do mes que foi usado.
	 * @param ano valor do ano que foi usado.
	 * @return valor arrecadado com usos fora do turno no mês e ano especificados.
	 */
	public double arrecadadoNoMesForaDoTurno(int mes, int ano) {
		double soma = 0;
		for (Veiculo veiculo : veiculos) {
			for (UsoDeVaga uso : veiculo.getListUsoDeVaga()) {
				if (uso.getEntrada().getMonthValue() == mes && uso.getEntrada().getYear() == ano) {
					if (tipoUso == TipoUso.TURNO) {
						soma += uso.getValorPago();
					} else if (tipoUso != TipoUso.TURNO) {
						soma += uso.getValorPago();
					}
				}
			}
		}
		return soma;
	}
	

}
