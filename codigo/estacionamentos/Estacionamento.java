package estacionamentos;

import java.time.LocalDate;
import java.util.Scanner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoVeiculoJaCadastrado;

public class Estacionamento {

	static Scanner teclado = new Scanner(System.in);
	// private int contClientes = 1;
	private String nome;
	public Map<String, Cliente> id;
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
		this.id = new HashMap<>();
		this.vagas = new LinkedList<>();
	}

	public Map<String, Cliente> getId() {
		return id;
	}

	public void setId(Map<String, Cliente> id) {
		this.id = id;
	}

	public void gerarVagas( int numeroVagas){
		vagas.clear();

		for(int i = 1; i <= numeroVagas; i++){
			vagas.add(new Vaga('i',i));
		}
	}

	/**
	 * Função para adicionar veiculo ao cliente
	 * 
	 * @param placa
	 * @param idCli
	 * @throws ExcecaoVeiculoJaCadastrado
	 */
	public void addVeiculo(String placa, String idCli) throws ExcecaoVeiculoJaCadastrado {
        Cliente clienteEncontrado = id.get(idCli);

        if (clienteEncontrado != null && clienteEncontrado.possuiVeiculo(placa) != null) {
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
        if (id.containsKey(cliente.getId())) {
            throw new ExcecaoClienteJaCadastrado("Cliente já cadastrado no sistema!");
        } else {
            id.put(cliente.getId(), cliente);
        }
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

		for (Map.Entry<String, Cliente> c : id.entrySet()) {
			if (c != null) {
				resposta += c.getValue().arrecadadoTotal();
				totalUsos += c.getValue().totalDeUsos();
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

		for (Map.Entry<String, Cliente> c : id.entrySet()) {
			if (c != null) {
				double valorDoCliente = c.getValue().arrecadadoNoMes(mes);

				for (int i = 0; i < 5; i++) {
					if (topClientes[i] == null || valorDoCliente > topClientes[i].arrecadadoNoMes(mes)) {
						for (int j = 4; j > i; j--) {
							topClientes[j] = topClientes[j - 1];
						}
						topClientes[i] = c.getValue();
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

		for (Map.Entry<String, Cliente> cliente : id.entrySet()) {
			veiculo = cliente.getValue().possuiVeiculo(placa);
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

		for (Map.Entry<String, Cliente> cliente : id.entrySet()) {
			Veiculo v = cliente.getValue().possuiVeiculo(placa);
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

		for (Map.Entry<String, Cliente> cliente : id.entrySet()) {
			total = total + cliente.getValue().arrecadadoTotal();
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

		for (Map.Entry<String, Cliente> cliente : id.entrySet()) {
			total = total + cliente.getValue().arrecadadoNoMes(mes);
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


	/**
 	* Calcula a média de usos no mesCorrente para clientes mensalistas.
 	*
 	* Este método itera sobre um conjunto de clientes, identifica os clientes mensalistas
 	* e calcula a média de seus usos mensais. Retorna a média resultante.
 	*
 	* @return A média de usos mensais para clientes mensalistas.
 	*/
	public double mediaUsoClienteMensalista(){
		int count;
		int usos;

		/*for (Map.Entry<String, Cliente> cliente : id.entrySet()) {
            
            if(cliente.getValue() instanceof UsuarioMensalista){
				usos = usos + cliente.getValue().usoMensalCorrente();
				count++;
       		}
		}
		*/
		id.values().stream()
			.filter(cliente -> cliente.getTipoUso() == "Mensalista").forEach( cliente -> {
				count++;
				usos = usos + cliente.getValue().usoMensalCorrente();	
			});	
		return usos/count;
	}

	
}
