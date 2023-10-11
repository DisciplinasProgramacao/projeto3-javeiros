package test;

import business.Veiculo;
import business.Cliente;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ClienteTest {

    private Cliente malu;
    private Veiculo bmw;
    private Veiculo volvo;

    @BeforeEach
    public void setUp() {
        malu = new Cliente("Malu", "1");
        bmw = new Veiculo("3121");
        volvo = new Veiculo("2247");
    }

    @Test
    public void testandoAdicionarUmVeiculoCadastrado() {
        malu.addVeiculo(bmw);
        Veiculo result = malu.possuiVeiculo("3131");
        assertEquals(bmw, result);
    }

    @Test
    public void testandoBuscarUmVeiculoNãoCadastrado() {
        Veiculo result = malu.possuiVeiculo("2247");
        assertNotEquals(volvo, result);
    }

}
