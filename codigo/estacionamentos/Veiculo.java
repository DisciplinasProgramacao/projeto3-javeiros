package estacionamentos;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import estacionamentos.Enums.TipoUso;

public class Veiculo {
	private int count;
	private String placa;
	private LinkedList<UsoDeVaga> usoDeVagas;


	private TipoUso tipoUso; 

	public TipoUso getTipoUso() {
		return tipoUso;
	}

	public void setTipoUso(TipoUso tipoUso) {
		this.tipoUso = tipoUso;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public UsoDeVaga getUsoDeVaga(Vaga vaga) {
		for (int i = 0; i < usoDeVagas.size(); i++) {
			if ((usoDeVagas.get(i)).getVaga().equals(vaga)) {
				return usoDeVagas.get(i);
			}
		}
		return null;
	}

	// 
	public Collection<UsoDeVaga> getListUsoDeVaga(){
		return List.copyOf(this.usoDeVagas);
	}

	public Veiculo(String placa, TipoUso tipoUso) {
		this.placa = placa;
		this.tipoUso = tipoUso;
	}

	/**
	 * Estacionar veiculo e adiciona na array de uso de vagas
	 * @param vaga Classe vaga que contem adisponibilidade de estacionamento
	 */
	public void estacionar(Vaga vaga) {
		// if(vaga.disponivel()){
			UsoDeVaga usoDeVaga;
			
			switch ((tipoUso)) {
				case HORISTA:
					usoDeVaga = new UsoDeVagaHorista(vaga);
					usoDeVagas.add(usoDeVaga);
					break;
				case TURNO:
					usoDeVaga = new UsoDeVagaTurno(vaga);
					usoDeVagas.add(usoDeVaga);
					break;
				case MENSALISTA:
					usoDeVaga = new UsoDeVagaMensalista(vaga);
					usoDeVagas.add(usoDeVaga);
					break;

				default:
					break;
			}
		// }
	}

	/**
	 * Sair veiculo da vaga de estacionamento
	 * @return retorna o valor do veiculo 
	 */

	 public double sair() {

		for (UsoDeVaga usoDeVaga: usoDeVagas ) {
			if(usoDeVaga.getSaida() == null){
				return usoDeVaga.sair();
			}
		}
		return 0.0;
	}

	/**
	 * Valor total de todo o historico do veiculo
	 * @return retorna o valor
	 */
	public double totalArrecadado() {
		
		double total = 0;

		for(UsoDeVaga uso : usoDeVagas){
			total += uso.getValorPago();
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
		
		for(UsoDeVaga uso : usoDeVagas){
			if(uso.getEntrada().getMonthValue() == mes){
				total += uso.getValorPago();
			}
		}

		return total;
	}

	public int usoMensalCorrente() {
		
		int total = 0;
		
		for(UsoDeVaga uso : usoDeVagas){
			if(uso.getEntrada().getMonthValue() == LocalDateTime.now().getMonthValue() && uso.getEntrada().getYear() == LocalDateTime.now().getYear()){
				total++;
			}
		}

		return total;
	}

	/**
	 * Total de usos pelo veiculo
	 * @return retorna o valor como int, sendo o total de uso
	 */
	public int totalDeUsos() {
		count = usoDeVagas.size();
		return count;
	}


	/**
	 * Método responsável por gerar uma string com os detalhes de todos os usos de vago do veículo
	 * @return string com os detalhes de todos os usos de vago do veículo
	 */
	public String gerarRelatorio(){
		String relatorio = "";

		for(UsoDeVaga usoDeVaga : usoDeVagas){
			relatorio += usoDeVaga.toString() + "\n";
		}

		return relatorio;
	}


	@Override
	public String toString(){
		return "Placa " + placa;
	}

}
