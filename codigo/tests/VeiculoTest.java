package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import codigo.UsoDeVaga;
import codigo.Vaga;
import codigo.Veiculo;

public class VeiculoTest {
    Veiculo veiculo;
	
	UsoDeVaga usoDeVaga;
	
	@Test
	void verificarSeOMetadoEstacionarEstaImplementadoCorretamente() {
		Vaga vaga = new Vaga();
		
		veiculo.estacionar( vaga );
		
		usoDeVaga = new UsoDeVaga();
		
		assertEquals(1, veiculo.totalDeUsos());
		
		assertEquals(usoDeVaga, veiculo.getUsoDeVaga(1));
		
	}
	
	@Test
	void verificarSeOVeiculoSaiDoEstacionamentoComOValor() {
		
		double expected = usoDeVaga.valorPago();
		
		double result = veiculo.sair();
		
		assertEquals(expected, result);
	}
	
	@Test
	void VerificarOValorTotalArrecadadoDoVeiculo() {
		
		double expected = usoDeVaga.valorPago();
		
		double result = veiculo.totalArrecadado();
		
		assertEquals(expected, result);
		
	}

	@Test
	void VerificarOValorTotalArrecadadoDoMes() {
		
		double expected = usoDeVaga.valorPago();
		
		double result = veiculo.arrecadadoNoMes(11);
		
		assertEquals(expected, result);
	}
}
