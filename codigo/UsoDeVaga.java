import java.time.LocalDateTime;


public class UsoDeVaga {
    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;

    public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = null; // A saída ainda não foi registrada
        this.valorPago = 0.0; // O valor pago a princípo será nulo
    }

    public void registrarSaida(LocalDateTime saida) {
        this.saida = saida;
        calcularValorPago();
    }

	public boolean estaDentroDoMes(int mes) {
		if (entrada != null && (entrada.getMonthValue() == mes || (saida != null && saida.getMonthValue() == mes))) {
			return true;
		}
		return false;
	}

    private void calcularValorPago() {
        if (saida != null) {
            long minutosEstacionados = entrada.until(saida, java.time.temporal.ChronoUnit.MINUTES);
            double valor = minutosEstacionados / 15.0 * VALOR_FRACAO;
            valorPago = Math.min(valor, VALOR_MAXIMO);
        }
    }
	
	

    public Vaga getVaga() {
        return vaga;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public double getValorPago() {
        return valorPago;
    }
}
