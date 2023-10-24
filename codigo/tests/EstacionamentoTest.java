package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstacionamentoTest {
    private Estacionamento estacionamento;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setUp(){
        this.estacionamento = new Estacionamento("Xulambs", 5, 2);
        this.cliente = new Cliente("Alice", "1");
        this.veiculo = new Veiculo("ABC1D234");

    }

    @Test
    void testAddCliente() {
        estacionamento.addCliente(cliente);

        Cliente[] resultado = estacionamento.getId();

        assertEquals(resultado[0], cliente);
    }

    @Test
    void testAddVeiculo() {

        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        Veiculo verificarVeiculo = cliente.possuiVeiculo("ABC1D234");

        assertEquals(veiculo, verificarVeiculo);
    }


    @Test
    void testEstacionar() {
        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        estacionamento.estacionar("ABC1D234");

        UsoDeVaga usos = veiculo.getUsoDeVaga(0);

        Vaga vagaUtilizada = usos.getVaga();

        assertFalse(vagaUtilizada.disponivel());
    }

    @Test
    void testGetId() {
        estacionamento.addCliente(cliente);

        Cliente[] clienteid = estacionamento.getId();

        assertEquals(clienteid[0], clienteid);
    }

    @Test
    void testSair() {
        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        estacionamento.estacionar("ABC1D234");

        UsoDeVaga usos = veiculo.getUsoDeVaga(0);

        Vaga vagaUtilizada = usos.getVaga();

        assertFalse(vagaUtilizada.disponivel());

        estacionamento.sair("ABC1D234");

        assertTrue(vagaUtilizada.disponivel());

    }

    @Test
    void testTop5Clientes() {
        
    }

    @Test
    void testTotalArrecadado() {
        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        estacionamento.estacionar("ABC1D234");

        estacionamento.sair("ABC1D234");


        double total = estacionamento.totalArrecadado();

       //Não consegui passar datas personalizadas para simular o tempo estacionado 
    }

    @Test
    void testValorMedioPorUso() {
        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        estacionamento.estacionar("ABC1D234");

        estacionamento.sair("ABC1D234");

        double valorMedio = estacionamento.valorMedioPorUso();

        //depende do teste de total Arrecadado

    }

    @Test
    void testArrecadacaoNoMes() {
        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        estacionamento.estacionar("ABC1D234");

        estacionamento.sair("ABC1D234");

        double arrecadacaoNoMes = estacionamento.arrecadacaoNoMes(0);
    }
    
}