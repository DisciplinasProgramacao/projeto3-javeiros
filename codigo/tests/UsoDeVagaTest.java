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


    @Test
    void testTempoEstacionamento() {
        Vaga vaga = new Vaga('A', 1);
        LocalDateTime entrada = LocalDateTime.now();
        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga, entrada);

        LocalDateTime saida = entrada.plusHours(2);
        usoDeVaga.registrarSaida(saida);

        assertEquals(2, ChronoUnit.HOURS.between(usoDeVaga.getEntrada(), usoDeVaga.getSaida()));
    }

    @Test
    void testValorPagoComTempoMaximo() {
        Vaga vaga = new Vaga('A', 1);
        LocalDateTime entrada = LocalDateTime.now();
        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga, entrada);

        LocalDateTime saida = entrada.plusHours(10); // Supondo que 10 horas seja o tempo máximo
        usoDeVaga.registrarSaida(saida);

        assertEquals(usoDeVaga.getValorMaximo(), usoDeVaga.getValorPago());
    }

}
