package tests;

import estacionamentos.UsoDeVaga;
import estacionamentos.Vaga;
import estacionamentos.interfaces.CalcularUsoDeVaga;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsoDeVagaTest {

    private UsoDeVaga usoDeVaga;
    private Vaga vaga;
    private CalcularUsoDeVaga calcularUsoDeVaga;

    @BeforeEach
    void setUp() {
        vaga = new Vaga('Y', 1);
        calcularUsoDeVaga = (entrada, saida) -> ChronoUnit.MINUTES.between(entrada, saida) * 0.25;
        usoDeVaga = new UsoDeVaga(vaga, calcularUsoDeVaga);
    }

    @Test
    void testUsoDeVaga() {
        LocalDateTime entrada = LocalDateTime.now();
        assertEquals(vaga, usoDeVaga.getVaga());
        assertNotNull(usoDeVaga.getEntrada());
        assertNull(usoDeVaga.getSaida());
    }

    @Test
    void testRegistrarSaida() {
        LocalDateTime saida = LocalDateTime.now().plusHours(1);
        usoDeVaga.setSaida(saida);
        assertEquals(saida, usoDeVaga.getSaida());
    }

    @Test
    void testValorPago() {
        LocalDateTime saida = LocalDateTime.now().plusHours(1);
        usoDeVaga.setSaida(saida);
        assertEquals(15.0, usoDeVaga.getValorPago());
    }

    @Test
    void testEstaDentroDoMes() {
        assertTrue(usoDeVaga.estaDentroDoMes(LocalDateTime.now().getMonthValue()));
        assertFalse(usoDeVaga.estaDentroDoMes(LocalDateTime.now().getMonthValue() - 1));
    }

    @Test
    void testValorMaximo() {
        LocalDateTime saida = LocalDateTime.now().plusHours(13);
        usoDeVaga.setSaida(saida);
        assertEquals(50.0, usoDeVaga.getValorPago());
    }

    @Test
    void testTempoEstacionamento() {
        LocalDateTime saida = LocalDateTime.now().plusHours(2);
        usoDeVaga.setSaida(saida);
        assertEquals(2, ChronoUnit.HOURS.between(usoDeVaga.getEntrada(), usoDeVaga.getSaida()));
    }

    @Test
    void testValorPagoComTempoMaximo() {
        LocalDateTime saida = LocalDateTime.now().plusHours(10); // Supondo que 10 horas seja o tempo máximo
        usoDeVaga.setSaida(saida);
        assertTrue(usoDeVaga.getValorPago() <= 50.0);
    }
}
