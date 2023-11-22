package estacionamentos;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Cliente {

	private String nome;
	private String id;
	private LinkedList<Veiculo> veiculos;

	/**
	 * Construtor para a criação de um Cliente, o qual possuirá um nome próprio, um
	 * identificador e a instanciação de uma lista que conterá seus veículos.
	 * 
	 * @param nome do tipo String, contendo o nome do Cliente
	 * @param id   do tipo String, contendo o id do Cliente
	 */
	public Cliente(String nome, String id) {
		setNome(nome);
		setId(id);
		veiculos = new LinkedList<>();
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

		if(veiculo != null){
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
	public double arrecadadoNoMes(int mes) {
		double soma = 0;
		for (int i = 0; i < veiculos.size(); i++) {
			if ((veiculos.get(i).arrecadadoNoMes(mes)) != 0) {
				soma += this.veiculos.get(i).arrecadadoNoMes(mes);
			}
		}
		return soma;
	}



	public String historicoCompleto() {
		String historico = "";

		for (int i = 0; i < veiculos.size(); i++) {
			Veiculo veiculo = veiculos.get(i);
			Collection<UsoDeVaga> totalDeUsos = veiculo.getListUsoDeVaga();

			for(UsoDeVaga u : totalDeUsos){
				Vaga vaga = u.getVaga();

				historico = historico + "\n" + 
				"Placa do Veículo: " + veiculo.getPlaca() + " | " + 
				"Vaga utilizada: " + vaga.toString() + " | " +
				"Uso de Vaga: " + u.toString();
			}
		}
		return (!historico.equals("")) ? historico : "Nenhum histórico encontrado";
	}

	
	public String historicoDataInicio(LocalDateTime filtro) {
		String historico = "";

		for (int i = 0; i < veiculos.size(); i++) {
			Veiculo veiculo = veiculos.get(i);
			Collection<UsoDeVaga> totalDeUsos = veiculo.getListUsoDeVaga();

			for(UsoDeVaga u : totalDeUsos){
				Vaga vaga = u.getVaga();

				if(u.getEntrada().isEqual(filtro)){
					historico = historico + "\n" + 
					"Placa do Veículo: " + veiculo.getPlaca() + " | " + 
					"Vaga utilizada: " + vaga.toString() + " | " +
					"Uso de Vaga: " + u.toString();
				}
			}
		}

		return (!historico.equals("")) ? historico : "Nenhum histórico encontrado";
	}

	public String historicoDataFim(LocalDateTime filtro) {
		String historico = "";

		for (int i = 0; i < veiculos.size(); i++) {
			Veiculo veiculo = veiculos.get(i);
			Collection<UsoDeVaga> totalDeUsos = veiculo.getListUsoDeVaga();

			for(UsoDeVaga u : totalDeUsos){
				Vaga vaga = u.getVaga();
				if(u.getSaida().isEqual(filtro)){
					historico = historico + "\n" + 
					"Placa do Veículo: " + veiculo.getPlaca() + " | " + 
					"Vaga utilizada: " + vaga.toString() + " | " +
					"Uso de Vaga: " + u.toString();
				}
			}
		}

		return (!historico.equals("")) ? historico : "Nenhum histórico encontrado";
	}


	@Override
	public String toString(){
		return "Nome: " + nome + "\nVeículos: " + veiculos;
	}
}
