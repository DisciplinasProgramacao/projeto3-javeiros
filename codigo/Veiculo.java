public class Veiculo {
	private int count;
	private String placa;
	private UsoDeVaga[] usos;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		String verificacao = "^[A-Z]{3}-\\d{4}$";
		
		if(placa.matches(verificacao)){
			this.placa = placa;
		}
	}
	
	public UsoDeVaga getUsoDeVaga(int n) {
		return this.usos[n];
	}

	public Veiculo(String placa) {
		setPlaca(placa);
	}

	/**
	 * Estacionar veiculo e adiciona na array de uso de vagas
	 * @param vaga Classe vaga que contem adisponibilidade de estacionamento
	 */
	public void estacionar(Vaga vaga) {
		if(vaga.disponivel()){
			UsoDeVaga usoDeVaga = new UsoDeVaga(vaga);
			usos[++count] = usoDeVaga; 
		}
	}

	/**
	 * Sair veiculo da vaga de estacionamento
	 * @return retorna o valor do veiculo 
	 */
	public double sair() {
		return usos[count].sair();
	}

	/**
	 * Valor total de todo o historico do veiculo
	 * @return retorna o valor
	 */
	public double totalArrecadado() {
		
		double total = 0;

		for(UsoDeVaga uso : usos){
			total += uso.valorPago();
		}
		
		return total;
	}

	/**
	 * Retorna o valor total arrecado em um determinado mes
	 * @param mes recebe o mês como parametro entre 1 e 12.
	 * @return
	 */
	public double arrecadadoNoMes(int mes) {
		
		double total = 0;
		
		for(UsoDeVaga uso : usos){
			if(uso.estaDentroDoMes(mes)){
				total += uso.valorPago();
			}
		}

		return total;
	}

	/**
	 * Total de usos pelo veiculo
	 * @return retorna o valor como int, sendo o total de uso
	 */
	public int totalDeUsos() {
		return count;
	}

	@Override
	public String toString(){
		return "Placa " + placa;
	}

}
