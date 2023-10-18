package estacionamentos;


public class Vaga {

	private String id;
	private boolean disponivel;

<<<<<<< HEAD
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
=======
	public Vaga(int fila, int numero) {
		
	}

	public boolean estacionar() {
		
	}

	public boolean sair() {
		
	}

	public boolean disponivel() {
		
	}

}
>>>>>>> feature-pedro
