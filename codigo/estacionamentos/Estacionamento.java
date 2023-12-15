package estacionamentos;

import java.util.Scanner;

import estacionamentos.Enums.TipoServico;
import estacionamentos.Enums.TipoTurno;
// import estacionamentos.Enums.TipoOrdenacao;
import estacionamentos.Enums.TipoUso;
import estacionamentos.interfaces.UsoDeVagaFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoNaoPossuiVagasDisponiveis;
import excecoes.ExcecaoNenhumClienteCadastrado;
import excecoes.ExcecaoOpicaoInvalida;
import excecoes.ExcecaoVeiculoJaCadastrado;
import excecoes.ExcecaoVeiculoJaEstacionado;
import excecoes.ExcecaoVeiculoNaoCadastrado;
import excecoes.ExcecaoClienteNaoCadastrado;

public class Estacionamento {

	Comparator<UsoDeVaga> compData = Comparator.comparing(UsoDeVaga::getEntrada);
    Comparator<UsoDeVaga> compValor = (u1, u2) -> Double.compare(u2.getValorPago(), u1.getValorPago());



	static Scanner teclado = new Scanner(System.in);
	// private int contClientes = 1;
	private String nome;
	public Map<String, Cliente> id;
	private LinkedList<Vaga> vagas;

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

	/**
	 * Método responsável por gerar uma quantidade determinada de novas vagas ao estacionamento
	 * @param numeroVagas um inteiro que representa a quantidade de vagas que serão criadas e adicionadas a este estacionamento
	 */
	public void gerarVagas(int numeroVagas) {
		vagas.clear();

		for (int i = 1; i <= numeroVagas; i++) {
			vagas.add(new Vaga('i', i));
		}
	}

	/**
	 * Função para adicionar veiculo ao cliente
	 * 
	 * @param placa
	 * @param idCli
	 * @throws ExcecaoVeiculoJaCadastrado
	 */
	public void addVeiculo(String placa, String idCli, TipoUso tipoUso, UsoDeVagaFactory usoDeVagaFactory, TipoTurno tipoTurno) throws ExcecaoVeiculoJaCadastrado {
        Cliente clienteEncontrado = id.get(idCli);

        if (clienteEncontrado != null && clienteEncontrado.possuiVeiculo(placa) != null) {
            throw new ExcecaoVeiculoJaCadastrado("Veículo já cadastrado para este cliente");
        } else {
            clienteEncontrado.addVeiculo(new Veiculo(placa, tipoUso, usoDeVagaFactory));
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
	 * @return o retorno é uma string com o nome dos 5 clientes que mais gastaram no mês
	 */
	public String top5Clientes(int mes) {
		Cliente[] topClientes = new Cliente[5];
		int ano = LocalDateTime.now().getYear();

		for (Map.Entry<String, Cliente> c : id.entrySet()) {
			if (c != null) {
				double valorDoCliente = c.getValue().arrecadadoNoMes(mes, ano);

				for (int i = 0; i < 5; i++) {
					if (topClientes[i] == null || valorDoCliente > topClientes[i].arrecadadoNoMes(mes, ano)) {
						for (int j = 4; j > i; j--) {
							topClientes[j] = topClientes[j - 1];
						}
						topClientes[i] = c.getValue();
						break;
					}
				}
			}
		}

		for (int i = 0; i < 5; i++) {
			if (topClientes[i] == null)
				topClientes[i] = new SemCliente();
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
	public void estacionar(String placa, TipoServico tipoServico) throws ExcecaoVeiculoNaoCadastrado, ExcecaoNaoPossuiVagasDisponiveis, ExcecaoVeiculoJaEstacionado {
		Veiculo veiculo = null;
		boolean vagaDisponivel = false;

		for (Map.Entry<String, Cliente> cliente : id.entrySet()) {
			veiculo = cliente.getValue().possuiVeiculo(placa);
			if (veiculo != null) {
				break;
			}

		}

		if (veiculo != null) {

			List<UsoDeVaga> usoDeVagas = veiculo.getListUsoDeVaga();

			for (Vaga vaga : vagas) {

				for(UsoDeVaga usoDeVaga : usoDeVagas){
					if(usoDeVaga.getVaga().equals(vaga) && !vaga.disponivel()){
						throw new ExcecaoVeiculoJaEstacionado();
					}
				}

				if (vaga.disponivel()) {
					veiculo.estacionar(vaga);
					vagaDisponivel = true;
					break;
				}
			}
		}else{
			throw new ExcecaoVeiculoNaoCadastrado("O veiculo com a placa: " + placa + " não esta cadastrado no sistema");
		}

		if(!vagaDisponivel){
			throw new ExcecaoNaoPossuiVagasDisponiveis("O estacionamento não possui vagas disponiveis");
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
		int ano = LocalDateTime.now().getYear();

		for (int i = 2000; i <= ano; i++) {
			for (int j = 1; j <= 12;  j++) {
				total += arrecadacaoNoMes(j, i);
			}
		}

		// for (Map.Entry<String, Cliente> cliente : id.entrySet()) {
		// 	if (cliente.getValue().getTipoUso() != TipoUso.MENSALISTA) {
		// 		total = total + cliente.getValue().arrecadadoTotal();
		// 	} else {
				
		// 	}
		// }
		return total;
	}

	/**
	 * Função para calcular o total arrecadado no mês
	 * 
	 * @param mes parâmetro do mês em específico
	 * @return retorna um valor double total arrecadado no mês
	 */
	public double arrecadacaoNoMes(int mes, int ano) {
		double total = 0.0;
		int mensalistas = 0;
		int turnistas = 0;

		// Horista e Turno
		for (Map.Entry<String, Cliente> cliente : id.entrySet()) {

			TipoUso tipoUso = cliente.getValue().getTipoUso();

			switch (tipoUso) {
				case HORISTA:
					total = total + cliente.getValue().arrecadadoNoMes(mes, ano);	
					break;
				case MENSALISTA:
					if(cliente.getValue().arrecadadoNoMes(mes, ano) != 0)
						mensalistas++;
					break;
				case TURNO:
					if(cliente.getValue().arrecadadoNoMes(mes, ano) != 0)
						turnistas++;
					total = total + ((Cliente) cliente).arrecadadoNoMesForaDoTurno(mes, ano);
					break;
			}

		}
		while ( mensalistas > 0) {
			total = total + 500;
		}

		while ( turnistas > 0) {
			total = total + 200;
		}

		return total;
	}

	public String getNome() {
		return this.nome;
	}

	/**
	 * Mostrar disponibilidades de vagas
	 */
	public void mostrarVagas() {
		for (Vaga v : vagas) {
			System.out.println("Vaga " + vagas.indexOf(v) + " - status: " + v.disponivel());
		}
	}

	/**
	 * Calcula a média de usos no mesCorrente para clientes mensalistas.
	 *
	 * Este método itera sobre um conjunto de clientes, identifica os clientes
	 * mensalistas
	 * e calcula a média de seus usos mensais. Retorna a média resultante.
	 *
	 * @return A média de usos mensais para clientes mensalistas.
	 */
	public double mediaUsoClienteMensalista() {
		int count = 0; // Inicialização de count
		double usos = 0.0; // Inicialização de usos como double

		for (Cliente cliente : id.values()) {
			if (cliente.getTipoUso() == TipoUso.MENSALISTA) {
				count++;
				usos += cliente.usoMensalCorrente();
			}
		}

		return usos / count;
	}

	/**
	 * Método responsável por calcular a arrecadação média dos horistas no
	 * especionamento passsado como parâmetro
	 * 
	 * @return a média do valor total arrecadado por todos clientes horistas
	 */
	public double arrecadacaoClientesHoristas() throws ExcecaoNenhumClienteCadastrado {
		Map<String, Cliente> clientes = this.getId();
		double totalArrecadado = 0.0;

		totalArrecadado = clientes.values().stream()
				.filter(c -> c.getTipoUso().equals(TipoUso.HORISTA))
				.mapToDouble(Cliente::arrecadadoTotal)
				.sum();

		if (clientes.size() < 1) {
			throw new ExcecaoNenhumClienteCadastrado();
		}

		return totalArrecadado / clientes.size();
	}

	/**
	 * Método responsável por calcular a arrecadação média dos mensalistas no
	 * especionamento passsado como parâmetro
	 * @param veiculo veículo a ser pesquisado seu relatório
	 * @return relatório do veiculo inserido
	 */

	//! IMPLEMENTAR ORDENACAO
	public  String relatorioVeiculo(String placa, int metodoOrdenar) throws ExcecaoOpicaoInvalida{

        

		//!IMPLEMENTAR ORDENACAO
		//! 1 - Ordernar por Data crescente;
		//! 2 - Ordernar por Valor decrescente;

        Veiculo veiculo = null;

        for (Cliente cliente : id.values()) {
            veiculo = cliente.possuiVeiculo(placa);
            if (veiculo != null) {
                break;
            }
        }

        if (veiculo == null) {
            throw new ExcecaoVeiculoNaoCadastrado("A placa informada não pertence a nenhum veículo em nosso sistema.");
        }

        Comparator<UsoDeVaga> comparator;
        if (metodoOrdenar == 1) {
            comparator = compData; // Ordenação por data crescente
        } else if (metodoOrdenar == 2) {
            comparator = compValor; // Ordenação por valor decrescente
        } else {
            throw new ExcecaoOpicaoInvalida("A opção digitada é inválida.");
        }

        return veiculo.relatorio(comparator);
    }
	
	/**
 	* Gera relatórios para todos os veículos no estacionamento.
 	* 
 	* Este método percorre todos os clientes cadastrados no estacionamento.
 	* Para cada cliente, ele itera sobre todos os veículos associados e
 	* imprime o relatório de cada veículo. Se não houver clientes ou veículos,
 	* uma mensagem é exibida indicando que o estacionamento está vazio.
 	*/
	 public void gerarRelatoriosDeTodosVeiculos() throws ExcecaoOpicaoInvalida {
        if (id.isEmpty()) {
            System.out.println("Não há clientes ou veículos no estacionamento.");
            return;
        }   

        for (Cliente cliente : id.values()) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                System.out.println(veiculo.relatorio(compData)); // Uso do comparador compData
            }
        }
    }


	/**
	* Retorna o histórico de um cliente específico no intervalo de datas fornecido.
	*
	* Este método recebe o identificador único de um cliente, a data de início e a data de fim
	* e retorna o histórico correspondente. O histórico inclui informações relevantes sobre os
	* veículos associados ao cliente durante o período especificado.
	*
	* @param idCliente Identificador único do cliente.
	* @param dataInicio Data de início do período para o histórico (inclusive).
	* @param dataFim Data de fim do período para o histórico (exclusive).
	* @return Uma string contendo o histórico do cliente no intervalo de datas fornecido.
	* @throws ExcecaoClienteNaoCadastrado Se não houver clientes cadastrados com o ID informado.
	**/
	public String historicoCliente(String idCliente,  LocalDateTime dataInicio, LocalDateTime dataFim) throws ExcecaoClienteNaoCadastrado{
		Cliente cliente = id.get(idCliente);
		if(cliente != null)
			return cliente.historico(dataInicio, dataFim);
		else
			throw new ExcecaoClienteNaoCadastrado("Nao ha clientes cadastrados com o id informado");
	}


	/**
 	* Altera o tipo de uso de um cliente no estacionamento.
 	*
 	* Este método recebe o tipo de uso desejado e o ID do cliente e altera o tipo de uso
 	* correspondente no estacionamento.
 	*
 	* @param tipoUso O novo tipo de uso a ser atribuído ao cliente.
 	* @param idCliente O identificador único do cliente.
 	*/
	public void alteraTipoUsoCliente(TipoUso tipoUso, String idCliente){
		Cliente cliente = id.get(idCliente);
		cliente.setTipoUso(tipoUso);
	};

	/**
 	* Calcula o percentual de uso mensalista no mês corrente em relação ao total de usos.
 	*
 	* Este método calcula o percentual de uso mensalista no mês corrente em relação à total de usos
 	* mensais de todos os clientes  no estacionamento. Retorna o resultado como um valor percentual.
 	*
 	* @return O percentual de uso mensalista no mês corrente em relação ao toal de usos.
 	*/
	public double percentualUsoMesalistaMesCorrente(){

		double total = 1;
		double usos = mediaUsoClienteMensalista();

		for (Cliente cliente : id.values()) {
			total = (double)cliente.usoMensalCorrente();
		}

		
		return usos/total * 100;
	}

	/**
	 * Verifica a existência de um cliente com o ID fornecido no estacionamento.
	 *
	 * Este método verifica se há um cliente no estacionamento com o ID fornecido.
	 * Retorna o cliente correspondente ou null se nenhum cliente for encontrado.
	 *
	 * @param id O identificador único do cliente.
	 * @return O cliente correspondente ao ID fornecido ou null se não encontrado.
	 */

	public Cliente possuiCliente(String id){
		return this.id.get(id);
	}


	/**
	 * Total de usos de todos os clientes
	 * @param mes valor do mes que foi usado.
	 * @param ano valor do ano que foi usado.
	 * @return quantidade de usos
	 * */
	public int totalDeUsoNoMesAnoEstacionamento(int mes, int ano){

		int total = 0;

		for (Cliente cliente : id.values()) {
			total += cliente.totalDeUsoNoMesAnoCliente(mes, ano);
		}

		return total;
	}

}
