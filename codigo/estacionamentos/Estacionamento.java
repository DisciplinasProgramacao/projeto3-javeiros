package estacionamentos;

import java.time.LocalDate;
import java.util.Scanner;

import java.util.Arrays;
import java.util.LinkedList;

import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoVeiculoJaCadastrado;

public class Estacionamento {

	static Scanner teclado = new Scanner(System.in);
	// private int contClientes = 1;
	private String nome;
	private LinkedList<Cliente> id;
	private LinkedList<Vaga> vagas;
	// private int quantFileiras;
	// private int vagasPorFileira;

	/**
	 * Construtor de Estacionamento
	 * 
	 * @param nome         nome do estacionamento
	 * @param fileiras     quantidade de fileiras
	 * @param vagasPorFila quantidade de vagas por fileira
	 */
	public Estacionamento(String nome) {
		this.nome = nome;
		id = new LinkedList<>();
		gerarVagas();
		// this.quantFileiras = fileiras;
		// this.vagasPorFileira = vagasPorFila;
		// id = new Cliente[100]; // Inicialize o array de clientes com um tamanho
		// inicial
	}

	/**
	 * Função para adicionar veiculo ao cliente
	 * 
	 * @param placa
	 * @param idCli
	 * @throws ExcecaoVeiculoJaCadastrado
	 */
	public void addVeiculo(String placa, String idCli) throws ExcecaoVeiculoJaCadastrado {
		Cliente clienteEncontrado = null;
		for (Cliente c : id) {
			if (idCli.equals(c.getId())) {
				clienteEncontrado = c;
				break;
			}
		}

		if (clienteEncontrado.possuiVeiculo(placa) != null) {
			throw new ExcecaoVeiculoJaCadastrado("Veículo já cadastrado para este cliente");
		} else {
			clienteEncontrado.addVeiculo(new Veiculo(placa));
		}
	}

	/**
	 * Função para adicionar cliente
	 * 
	 * @param cliente
	 * @throws ExcecaoClienteJaCadastrado
	 */
	public void addCliente(Cliente cliente) throws ExcecaoClienteJaCadastrado {

		Cliente clienteEncontrado = null;
		for (Cliente c : id) {
			if (cliente.equals(c.getId())) {
				clienteEncontrado = c;
				break;
			}
		}
		if (clienteEncontrado != null) {
			throw new ExcecaoClienteJaCadastrado("Cliente já cadastrado no sistema!");
		} else {
			id.add(cliente);
			// id[contClientes - 1] = cliente;
			// contClientes++;
		}
	}

	/**
	 * Função para gerar vagas do estacionamento
	 */
	private void gerarVagas() {
		vagas = new LinkedList<>();
		// for (int fila = 0; fila < quantFileiras; fila++) {
		// for (int numero = 1; numero <= vagasPorFileira; numero++) {
		// vagas[fila * vagasPorFileira + (numero - 1)] = new Vaga((char)('A' + fila),
		// numero);
		// }
		// }
	}

	/**
	 * Função que calcula o valor medio total do estacionamento
	 * 
	 * @return retorna um valor do tipo do double com a divisao entre o arrecadado
	 *         total sobre o total de usos no sistema
	 */
	public double valorMedioPorUso() {
		double resposta = 0.0;
		int totalUsos = 0;

		for (Cliente c : id) {
			if (c != null) {
				resposta += c.arrecadadoTotal();
				totalUsos += c.totalDeUsos();
			}
		}

		if (totalUsos > 0) {
			resposta /= totalUsos;
		}

		return resposta;
	}

	/**
	 * Função para calcular o top de 5 clientes do estacionamento.
	 * 
	 * @param mes insere o mês para calcular o top5
	 * @return o retorno é uma string com o nome dos 5 clientes que mais gastaram no
	 *         mês
	 */
	public String top5Clientes(int mes) {
		Cliente[] topClientes = new Cliente[5];

		for (Cliente c : id) {
			if (c != null) {
				double valorDoCliente = c.arrecadadoNoMes(mes);

				for (int i = 0; i < 5; i++) {
					if (topClientes[i] == null || valorDoCliente > topClientes[i].arrecadadoNoMes(mes)) {
						for (int j = 4; j > i; j--) {
							topClientes[j] = topClientes[j - 1];
						}
						topClientes[i] = c;
						break;
					}
				}
			}
		}

		// Agora topClientes contém os 5 principais clientes
		String[] nomesTopClientes = new String[5];
		for (int i = 0; i < 5; i++) {
			if (topClientes[i] != null) {
				nomesTopClientes[i] = topClientes[i].getNome();
			}
		}
		return Arrays.toString(nomesTopClientes);
	}

	/**
	 * Função para estacionar o veículo no estacionamento
	 * 
	 * @param placa placa do veículo do cliente
	 */
	public void estacionar(String placa) {
		Veiculo veiculo = null;

		for (Cliente cliente : id) {
			veiculo = cliente.possuiVeiculo(placa);
			if (veiculo != null) {
				break;
			}
		}

		if (veiculo != null) {
			for (Vaga vaga : vagas) {
				if (vaga.disponivel()) {
					veiculo.estacionar(vaga);
					break;
				}
			}
		}
	}

	/**
	 * Método para sair do estacionamento
	 * 
	 * @param placa placa do cliente em específico
	 * @return retorna o valor pago pelo cliente
	 */
	public double sair(String placa) {

		for (Cliente cliente : id) {
			Veiculo v = cliente.possuiVeiculo(placa);
			if (v != null) {
				return v.sair();
			}
		}

		return 0.0; // Retorna 0.0 se o veículo não for encontrado
	}

	/**
	 * Função para calcular o total arrecadado por todos os clientes do
	 * estacionamento
	 * * @return retorna o valor em double gasto em todo o estacionamento
	 */
	public double totalArrecadado() {
		double total = 0.0;

		for (Cliente cliente : id) {
			total = total + cliente.arrecadadoTotal();
		}
		return total;
	}

	/**
	 * Função para calcular o total arrecadado no mês
	 * 
	 * @param mes parâmetro do mês em específico
	 * @return retorna um valor double total arrecadado no mês
	 */
	public double arrecadacaoNoMes(int mes) {
		double total = 0.0;

		for (Cliente cliente : id) {
			total = total + cliente.arrecadadoNoMes(mes);
		}
		return total;
	}

	public String getNome() {
		return this.nome;
	}

	/**
	 * 
	 */
	public void mostrarVagas() {
		for (Vaga v : vagas) {
			System.out.println("Vaga " + vagas.indexOf(v) + " - status: " + v.disponivel());
		}
	}

}
