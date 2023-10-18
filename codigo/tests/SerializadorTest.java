package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import estacionamentos.*;

public class SerializadorTest {
    Serializador s;
    String meucaminho;
    Cliente kimberly;
    Veiculo honda;
    Deserializador d;

    @BeforeEach
    public void setUp(){
        s = new Serializador();
        meucaminho = "./dados"; 
        kimberly = new Cliente ("Kimberly", "122");
        d = new Deserializador();
    }

    @Test
    public void testandoSerializadorResultadoValido() throws Exception{
        s.serializar(meucaminho, kimberly.getNome());
        Object resultado = d.deserializar(meucaminho);
        System.out.println(resultado);

        assertEquals("Kimberly", resultado);
    }

    @Test
    public void testandoSerializadorResultadoIncoerente() throws Exception{   
        s.serializar(meucaminho, kimberly.getNome());
        Object resultado = d.deserializar(meucaminho);
        System.out.println(resultado);

        assertNotEquals("kimberly", resultado);
    }
}
