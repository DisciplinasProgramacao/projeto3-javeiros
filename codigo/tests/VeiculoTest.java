package tests;

import estacionamentos.Veiculo;
import estacionamentos.Vaga;
import estacionamentos.UsoDeVaga;
import estacionamentos.interfaces.UsoDeVagaFactory;
import estacionamentos.Enums.TipoUso;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeiculoTest {
    private Veiculo veiculo;
    private Vaga vaga;
    private UsoDeVagaFactory usoDeVagaFactory;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo("ABC-1234", TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        vaga = new Vaga('A', 1);
    }

    @Test
    void verificarSeOMetadoEstacionarEstaImplementadoCorretamente() {
        veiculo.estacionar(vaga);
        assertEquals(1, veiculo.totalDeUsos());
        assertNotNull(veiculo.getUsoDeVaga(vaga));
    }

    @Test
    void verificarSeOVeiculoSaiDoEstacionamentoComOValor() {
        veiculo.estacionar(vaga);
        double valorPago = veiculo.sair();
        assertTrue(valorPago >= 0);
    }

    @Test
    void VerificarOValorTotalArrecadadoDoVeiculo() {
        veiculo.estacionar(vaga);
        veiculo.sair();
        double totalArrecadado = veiculo.totalArrecadado();
        assertTrue(totalArrecadado >= 0);
    }

    @Test
    void VerificarOValorTotalArrecadadoDoMes() {
        veiculo.estacionar(vaga);
        veiculo.sair();
        double arrecadadoNoMes = veiculo.arrecadadoNoMes(LocalDateTime.now().getMonthValue());
        assertTrue(arrecadadoNoMes >= 0);
    }
}
