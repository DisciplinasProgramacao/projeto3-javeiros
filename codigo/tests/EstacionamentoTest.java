package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import estacionamentos.Cliente;
import estacionamentos.Estacionamento;
import estacionamentos.UsoDeVaga;
import estacionamentos.Vaga;
import estacionamentos.Veiculo;

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
    void testEstacionarVeiculoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
        estacionamento.estacionar("XYZ1234");
        });
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
        for (int i = 1; i <= 6; i++) {
            Cliente cliente = new Cliente("Cliente" + i, String.valueOf(i));
            estacionamento.addCliente(cliente);
            Veiculo veiculo = new Veiculo("ABC" + i);
            estacionamento.addVeiculo(veiculo, cliente.getId());
            estacionamento.estacionar(veiculo.getPlaca());
            estacionamento.sair(veiculo.getPlaca());
        }
    
        String top5 = estacionamento.top5Clientes(0); // Supondo que o mês atual é 0
        assertNotNull(top5);
        assertTrue(top5.contains("Cliente1"));
        // Verificar se contém os nomes dos 5 clientes com maior gasto
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

    //Outra forma de executar esse teste
    // @Test
    // void testTotalArrecadado() {
    //     estacionamento.addCliente(cliente);
    //     estacionamento.addVeiculo(veiculo, cliente.getId());
    //     estacionamento.estacionar(veiculo.getPlaca());
    //     estacionamento.sair(veiculo.getPlaca());

    // double total = estacionamento.totalArrecadado();
    // assertTrue(total > 0);
    // }




    @Test
    void testValorMedioPorUso() {
        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        estacionamento.estacionar("ABC1D234");

        estacionamento.sair("ABC1D234");

        double valorMedio = estacionamento.valorMedioPorUso();

        //depende do teste de total Arrecadado

    }

    //Outra forma de executar esse teste
    // @Test
    // oid testValorMedioPorUso() {
    //     estacionamento.addCliente(cliente);
    //     estacionamento.addVeiculo(veiculo, cliente.getId());

    //     for (int i = 0; i < 5; i++) {
    //         estacionamento.estacionar(veiculo.getPlaca());
    //         estacionamento.sair(veiculo.getPlaca());
    //     }

    //     double valorMedio = estacionamento.valorMedioPorUso();
    //     assertTrue(valorMedio > 0);
    // }



    @Test
    void testArrecadacaoNoMes() {
        estacionamento.addCliente(cliente);

        estacionamento.addVeiculo(veiculo, cliente.getId());

        estacionamento.estacionar("ABC1D234");

        estacionamento.sair("ABC1D234");

        double arrecadacaoNoMes = estacionamento.arrecadacaoNoMes(0);
    }
    
    //Outra forma de executar esse teste
    // @Test
    // void testArrecadacaoNoMes() {
    //     estacionamento.addCliente(cliente);
    //     estacionamento.addVeiculo(veiculo, cliente.getId());
    //     estacionamento.estacionar(veiculo.getPlaca());
    //     estacionamento.sair(veiculo.getPlaca());

    //     double arrecadacaoNoMes = estacionamento.arrecadacaoNoMes(0); // Supondo que o mês atual é 0
    //     assertTrue(arrecadacaoNoMes > 0);
    // }
    

}