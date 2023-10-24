package estacionamentos;

import java.util.Arrays;

import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoVeiculoJaCadastrado;

public class Estacionamento {
	private int contClientes = 1;
	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome = nome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		id = new Cliente[100]; // Inicialize o array de clientes com um tamanho inicial
		gerarVagas();
	}

	public void addVeiculo(Veiculo veiculo, String idCli) throws ExcecaoVeiculoJaCadastrado {
		Cliente clienteEncontrado = null;
		for (Cliente c : id) {
			if (idCli.equals(c.getId())) {
				clienteEncontrado = c;
				break;
			}
		}

		if (clienteEncontrado.possuiVeiculo(veiculo.getPlaca())) {
			throw new ExcecaoVeiculoJaCadastrado("Veículo já cadastrado para este cliente");
		} else {
			clienteEncontrado.addVeiculo(veiculo);
		}
	}

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
			id[contClientes - 1] = cliente;
			contClientes++;
		}
	}

	private void gerarVagas() {
		int tam = quantFileiras * vagasPorFileira;
		vagas = new Vaga[tam];
	}

	/* comentando métodos não implementados
	public void estacionar(String placa) {

	}

	public double sair(String placa) {

	}

	public double totalArrecadado() {

	}

	public double arrecadacaoNoMes(int mes) {

	}
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

}
