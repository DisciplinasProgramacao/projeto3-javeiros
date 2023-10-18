package tests;

import codigo.Vaga;
import codigo.Veiculo;

import static org.junit.jupiter.api.Assertions.assertTrue; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VagaTest {

    Veiculo carro;
    Vaga vaga;

    @BeforeAll
    void setUp(){
        carro = new Veiculo("QNP-6607");
        vaga = new Vaga(1,1);
    }
    
    @Test
    public void testEstacionar(){
        carro.estacionar(vaga);
        assertFalse(vaga.estacionar());
    }

    @Test
    public void testSair(){
        carro.sair();
        assertTrue(vaga.sair());
    }

    @Test 
    public void testDisponibilidade(){
        assertTrue(vaga.Disponivel());
    }
}
