package tests;

import estacionamentos.Cliente;
import estacionamentos.Estacionamento;
import estacionamentos.Veiculo;
import estacionamentos.Enums.TipoUso;
import estacionamentos.interfaces.UsoDeVagaFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstacionamentoTest {
    private Estacionamento estacionamento;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento("Estacionamento Teste");
        cliente = new Cliente("Alice", "1", TipoUso.HORISTA);
        veiculo = new Veiculo("ABC1234", TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        estacionamento.gerarVagas(10); 
    }

    @Test
    void testAddCliente() {
        estacionamento.addCliente(cliente);
        assertEquals(cliente, estacionamento.getId().get(cliente.getId()));
    }

    @Test
    void testAddVeiculo() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo("ABC1234", cliente.getId(), TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        assertNotNull(cliente.possuiVeiculo("ABC1234"));
    }

    @Test
    void testEstacionar() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo("ABC1234", cliente.getId(), TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        estacionamento.estacionar("ABC1234");
        assertFalse(veiculo.getUsoDeVaga().getVaga().disponivel());
    }

    @Test
    void testEstacionarVeiculoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            estacionamento.estacionar("XYZ1234");
        });
    }

    @Test
    void testSair() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo("ABC1234", cliente.getId(), TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        estacionamento.estacionar("ABC1234");
        double valorPago = estacionamento.sair("ABC1234");
        assertTrue(valorPago >= 0);
    }

    @Test
    void testTop5Clientes() {
        for (int i = 1; i <= 6; i++) {
            Cliente clienteTeste = new Cliente("Cliente" + i, String.valueOf(i), TipoUso.HORISTA);
            estacionamento.addCliente(clienteTeste);
            Veiculo veiculoTeste = new Veiculo("ABC" + i, TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
            estacionamento.addVeiculo("ABC" + i, clienteTeste.getId(), TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
            estacionamento.estacionar("ABC" + i);
            estacionamento.sair("ABC" + i);
        }

        String top5 = estacionamento.top5Clientes(LocalDateTime.now().getMonthValue());
        assertNotNull(top5);
        assertTrue(top5.contains("Cliente1"));
    }

    @Test
    void testTotalArrecadado() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo("ABC1234", cliente.getId(), TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        estacionamento.estacionar("ABC1234");
        estacionamento.sair("ABC1234");
        double total = estacionamento.totalArrecadado();
        assertTrue(total >= 0);
    }

    @Test
    void testValorMedioPorUso() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo("ABC1234", cliente.getId(), TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        estacionamento.estacionar("ABC1234");
        estacionamento.sair("ABC1234");
        double valorMedio = estacionamento.valorMedioPorUso();
        assertTrue(valorMedio >= 0);
    }

    @Test
    void testArrecadacaoNoMes() {
        estacionamento.addCliente(cliente);
        estacionamento.addVeiculo("ABC1234", cliente.getId(), TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        estacionamento.estacionar("ABC1234");
        estacionamento.sair("ABC1234");
        double arrecadacaoNoMes = estacionamento.arrecadacaoNoMes(LocalDateTime.now().getMonthValue());
        assertTrue(arrecadacaoNoMes >= 0);
    }
}
