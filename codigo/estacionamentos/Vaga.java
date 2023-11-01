package estacionamentos;


public class Vaga {

	private String id;
	private boolean disponivel;

    public Vaga(char fila, int numero) {
        this.id = Character.toString(fila).toUpperCase() + numero;
        this.disponivel = true;
    }

    public boolean estacionar() {
        if (disponivel) {
            disponivel = false;
            return true;
        }
        return false;
    }

    public boolean sair() {
        if (!disponivel) {
            disponivel = true;
            return true;
        }
        return false;
    }

    public boolean disponivel() {
        return disponivel;
    }

    @Override
	public String toString() {
        String disponibilidade = disponivel ? "disponível" : "ocupada";
        return "Vaga " + id + ", disponibilidade: " + disponibilidade;
    }
    
}
