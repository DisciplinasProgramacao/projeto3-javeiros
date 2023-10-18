package estacionamentos.Enums;
public enum TipoServico {
	MANOBRISTA(5.0, 0),
	LAVAGEM(20.0, 1),
	POLIMENTO(45.0, 2);
	
	private double valor;
	private double tempoMinimo;

	TipoServico(double valor, double tempoMinimo) {
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public double getTempo() {
		return tempoMinimo;
	}
	
}
