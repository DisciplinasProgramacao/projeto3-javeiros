package test;
import business.Cliente;
import business.Veiculo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;


public class ClienteTest {

    private Cliente malu;
    private LinkedList<Veiculo> veiculosMalu;
    private Veiculo bmw;
    private Veiculo volvo;

    @BeforeEach
    void setUp(){
        malu = new Cliente("Malu", "1");
        veiculosMalu = new LinkedList<>();
        bmw = new Veiculo("3121");
        volvo = new Veiculo("2247");
    }

    @Test
    void testandoAdicionarUmVeiculoCadastrado(){
        malu.addVeiculo(bmw);
        Veiculo result = malu.possuiVeiculo("3131");
        assertEquals(bmw, result);
    }

}

    
