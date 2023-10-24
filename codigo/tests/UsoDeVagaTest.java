package tests;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import estacionamentos.UsoDeVaga;
import estacionamentos.Vaga;

class UsoDeVagaTest {

    @Test
    void testUsoDeVaga() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);

        assertEquals(vaga, uso.getVaga());
        assertEquals(entrada, uso.getEntrada());
        assertNull(uso.getSaida());
    }

    @Test
    void testRegistrarSaida() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);

        LocalDateTime saida = LocalDateTime.of(2023, 10, 10, 9, 0);
        uso.registrarSaida(saida);

        assertEquals(saida, uso.getSaida());
    }

    @Test
    void testValorPago() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);

        LocalDateTime saida = LocalDateTime.of(2023, 10, 10, 9, 0);
        uso.registrarSaida(saida);

        assertEquals(16.0, uso.getValorPago());
    }

    @Test
    void testEstaDentroDoMes() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);

        assertTrue(uso.estaDentroDoMes(10));
        assertFalse(uso.estaDentroDoMes(9));
    }

    @Test
    void testValorMaximo() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);

        LocalDateTime saida = entrada.plus(13, ChronoUnit.HOURS);
        uso.registrarSaida(saida);

        assertEquals(50.0, uso.getValorPago());
    }
}
