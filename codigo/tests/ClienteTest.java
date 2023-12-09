package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import estacionamentos.Cliente;
import estacionamentos.Veiculo;

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
        Veiculo result = malu.possuiVeiculo("3121");
        assertEquals(bmw, result);
    }

    @Test
    public void testandoBuscarUmVeiculoNaoCadastrado() {
        assertNull(malu.possuiVeiculo("2247"));
    }

    @Test
    void testAdicionarVeiculoNull() {
        assertThrows(NullPointerException.class, () -> {
            malu.addVeiculo(null);
        });
    }
}
