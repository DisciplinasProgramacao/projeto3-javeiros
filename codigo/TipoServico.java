package estacionamento;

public enum TipoServico {
	MANOBRISTA(5.0, 0),
	LAVAGEM(20.0, 1),
	POLIMENTO(45.0, 2);
	
	private double valor;
	private double tempo;

	TipoServico(double valor, double tempo) {
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public double getTempo() {
		return tempo;
	}
	
}
