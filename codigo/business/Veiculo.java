package business;
import java.util.LinkedList;

public class Veiculo {

	private String placa;
	private LinkedList<UsoDeVaga> usos;

	public Veiculo(String placa) {
		this.placa = placa;
		usos = new LinkedList<>();
		
	}

	public String getPlaca() {
		return this.placa;
	}

	public void estacionar(Vaga vaga) {
		
	}
	public double totalArrecadado() {
		return 1;//*provisório
	}

	public double arrecadadoNoMes(int mes) {
		return 1;//*provisório
	}

	public int totalDeUsos() {
		return 1;//*provisório
	}


}
