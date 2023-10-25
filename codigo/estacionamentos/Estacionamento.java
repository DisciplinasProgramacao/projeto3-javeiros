package estacionamentos;

import java.time.LocalDate;
import java.util.Scanner;

import java.util.Arrays;

import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoVeiculoJaCadastrado;

public class Estacionamento {

	static Scanner teclado = new Scanner(System.in);
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

	public void addVeiculo(String placa, String idCli) throws ExcecaoVeiculoJaCadastrado {
		Cliente clienteEncontrado = null;
		for (Cliente c : id) {
			if (idCli.equals(c.getId())) {
				clienteEncontrado = c;
				break;
			}
		}

		if (clienteEncontrado.possuiVeiculo(placa)) {
			throw new ExcecaoVeiculoJaCadastrado("Veículo já cadastrado para este cliente");
		} else {
			clienteEncontrado.addVeiculo(new Veiculo(placa));
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

	public void estacionar(String placa) {
		Veiculo veiculo = null;

		for (Cliente cliente : id) {
			if (cliente.possuiVeiculo(placa)) {
				veiculo = cliente.getVeiculo(placa);
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

	public double sair(String placa) {
		Veiculo veiculo = null;

		for (Cliente cliente : id) {
			if (cliente.possuiVeiculo(placa)) {
				veiculo = cliente.getVeiculo(placa);
				break;
			}
		}

		if (veiculo != null) {
			return veiculo.sair();
		}

		return 0.0; // Retorna 0.0 se o veículo não for encontrado
	}

	public double totalArrecadado() {
		double total = 0.0;

		for (Cliente cliente : id) {
			total = total + cliente.arrecadadoTotal();
		}
		return total;
	}

	public double arrecadacaoNoMes(int mes) {
		double total = 0.0;

		for (Cliente cliente : id) {
			total = total + cliente.arrecadadoNoMes(mes);
		}
		return total;
	}

	public Cliente[] getId() {
		return null;
	}

	public String getNome() {
		return this.nome;
	}

}
