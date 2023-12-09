package tests;

import estacionamentos.Vaga;
import estacionamentos.Veiculo;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VagaTest {

    private Veiculo carro;
    private Vaga vaga;

    @BeforeEach
    void setUp() {
        carro = new Veiculo("QNP-6607");
        vaga = new Vaga('A', 1);
    }
    
    @Test
    public void testEstacionar() {
        assertTrue(vaga.estacionar());
        assertFalse(vaga.estacionar());
    }

    @Test
    public void testSair() {
        vaga.estacionar();
        assertTrue(vaga.sair());
        assertFalse(vaga.sair()); 
    }

    @Test 
    public void testDisponibilidade() {
        assertTrue(vaga.disponivel());
        vaga.estacionar();
        assertFalse(vaga.disponivel());
    }
}
