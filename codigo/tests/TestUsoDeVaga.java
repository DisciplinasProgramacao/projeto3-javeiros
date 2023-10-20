package tests;

import estacionamentos.UsoDeVaga;
import estacionamentos.Vaga;
import excecoes.ExcecaoTempoMinimoLavagem;
import excecoes.ExcecaoTempoMinimoPolimento;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testContratarLavagem() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);
        LocalDateTime saida = LocalDateTime.of(2023, 10, 10, 9, 30);
        uso.registrarSaida(saida);

        assertDoesNotThrow(() -> uso.contratarLavagem());
        assertEquals(36.0, uso.getValorPago()); // 16.0 (estacionamento) + 20.0 (lavagem)
    }

    @Test
    void testContratarPolimento() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);
        LocalDateTime saida = LocalDateTime.of(2023, 10, 10, 10, 30);
        uso.registrarSaida(saida);

        assertDoesNotThrow(() -> uso.contratarPolimento());
        assertEquals(61.0, uso.getValorPago()); // 16.0 (estacionamento) + 45.0 (polimento)
    }

    @Test
    void testExcecaoTempoMinimoLavagem() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);
        LocalDateTime saida = LocalDateTime.of(2023, 10, 10, 8, 30);
        uso.registrarSaida(saida);

        assertThrows(ExcecaoTempoMinimoLavagem.class, () -> uso.contratarLavagem());
    }

    @Test
    void testExcecaoTempoMinimoPolimento() {
        Vaga vaga = new Vaga('Y', 8);
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 10, 8, 0);
        UsoDeVaga uso = new UsoDeVaga(vaga, entrada);
        LocalDateTime saida = LocalDateTime.of(2023, 10, 10, 9, 30);
        uso.registrarSaida(saida);

        assertThrows(ExcecaoTempoMinimoPolimento.class, () -> uso.contratarPolimento());
    }
}
