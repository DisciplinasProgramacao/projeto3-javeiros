package estacionamentos;


public class Vaga {

	private String id;
	private boolean disponivel;

    public Vaga(char fila, int numero) {
        this.id = Character.toString(fila).toUpperCase() + numero;
        this.disponivel = true;
    }

    /**
     * Estaciona um carro na vaga.
     * @return true se o carro foi estacionado, false caso contrário.
     */
    public boolean estacionar() {
        if (disponivel) {
            disponivel = false;
            return true;
        }
        return false;
    }

    /**
     * Sair da vaga.
     * @return true se o carro saiu da vaga, false caso contrário.
     */
    public boolean sair() {
        // true
        if (disponivel) { // false
            disponivel = true;
            return true;
        }
        return false;
    }

    /**
     * Verifica se a vaga está disponível.
     * @return true se a vaga está disponível, false caso contrário.
     */
    public boolean disponivel() {
        return disponivel;
    }

    @Override
	public String toString() {
        String disponibilidade = disponivel ? "disponível" : "ocupada";
        return "Vaga " + id + ", disponibilidade: " + disponibilidade;
    }
    
}
