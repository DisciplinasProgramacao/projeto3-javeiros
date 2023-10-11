import java.util.Arrays;

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
		gerarVagas();
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		for (Cliente c : id) {
			if (idCli.equals(c.getId())) {
				c.addVeiculo(veiculo);
			}
		}
	}

	public void addCliente(Cliente cliente) {
		for (int i = 0; i < contClientes; i++) {
			id[i] = cliente;
		}
	}

	private void gerarVagas() {
		int tam = quantFileiras * vagasPorFileira;
		vagas = new Vaga[tam];
	}

	public void estacionar(String placa) {

	}

	public double sair(String placa) {

	}

	public double totalArrecadado() {

	}

	public double arrecadacaoNoMes(int mes) {

	}

	public double valorMedioPorUso() {
		double resposta = 0.0;
		for (Cliente c : id) {
			resposta = c.arrecadadoTotal() / (double) c.totalDeUsos();
		}
		return resposta;
	}

	public String top5Clientes(int mes) {

		double maiorValor = 0.0;
		String topClientes[] = new String[5];

		for (Cliente c : id) {
				double valorDoCliente = c.arrecadadoNoMes(mes);
            if(valorDoCliente > maiorValor){
				for (int i = 0; i < topClientes.length; i++) {
                    if(i < topClientes.length -1)
					{
					 topClientes[i + 1] = topClientes[i];
                }
                topClientes[i] = c.getNome();
            }
            maiorValor = valorDoCliente;
        }
    }

    return Arrays.toString(topClientes);
}

}
