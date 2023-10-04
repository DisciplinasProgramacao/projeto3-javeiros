/*O cliente pode ser identificado com nome e identificador ou “anônimo” (nome e identificador neutros).
• Um cliente pode ter cadastrados para si mais de um veículo.
Um cliente identificado tem acesso a seu histórico de uso do estacionamento.
Este histórico pode ser completo ou filtrado por datas de início e fim */

import java.util.LinkedList;

public class Cliente {

	private String nome;
	private String id;
	private LinkedList<Veiculo> veiculos;

	public Cliente(String nome, String id) {
		setNome(nome);
		setId(id);
	}

	public String getNome(){
		return this.nome;
	}
	private void setNome(String nome){
		this.nome = nome;
	}
	public String getId(){
		return this.id;
	}
	private void setId(String id){
		this.id = id;
	}

	public void addVeiculo(Veiculo veiculo) {
		veiculos.add(veiculo);
	}

	
	public Veiculo possuiVeiculo(String placa) {
		for(int i = 0; i < veiculos.size(); i++){
			if((veiculos.get(i).getPlaca()).equals(placa)){
				return this.veiculos.get(i);
			}
		}
		return null;
	}

	public int totalDeUsos() { //em relação a cada uso de cada veículo
		
	}

	public double arrecadadoPorVeiculo(String placa) {
		Veiculo veiculo = possuiVeiculo(placa);
		return veiculo.totalArrecadado();		
	}

	public double arrecadadoTotal() {
		
	}

	public double arrecadadoNoMes(int mes) {
		
	}

}
