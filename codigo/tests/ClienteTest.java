package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import estacionamentos.Cliente;
import estacionamentos.Veiculo;
import excecoes.ExcecaoVeiculoJaCadastrado;

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

    @Test
    public void testAdicionarVeiculoJaCadastrado() {
        malu.addVeiculo(bmw);
        assertThrows(ExcecaoVeiculoJaCadastrado.class, () -> {
            malu.addVeiculo(bmw);
        });
    }

    @Test
    public void testRemoverVeiculo() {
        malu.addVeiculo(bmw);
        malu.removeVeiculo(bmw);
        assertNull(malu.possuiVeiculo("3121"));
    }

    @Test
    public void testRemoverVeiculoNaoCadastrado() {
        assertThrows(RuntimeException.class, () -> {
            malu.removeVeiculo(volvo);
        });
    }

    @Test
    public void testTotalDeVeiculos() {
        malu.addVeiculo(bmw);
        malu.addVeiculo(volvo);
        assertEquals(2, malu.getVeiculos().size());
    }

    @Test
    public void testPossuiVeiculoComPlacaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            malu.possuiVeiculo("");
        });
    }
}
