

public class Vaga {

	private String id;
	private boolean disponivel;

    public Vaga(char fila, int numero) {
        this.id = Character.toString(fila).toUpperCase() + String.format("%02d", numero);
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

	public String toString() {
        return id;
    }
}
