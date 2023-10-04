

public class Vaga {

	private String id;
	private boolean disponivel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public Vaga(int fila, int numero) {
	
	}

	public boolean estacionar() throws Exception {
		throw new Exception("Ainda não implementado");
	}

	public boolean sair() throws Exception {
		throw new Exception("Ainda não implementado");
	}
	public boolean Disponivel() {
		return disponivel;
	}

}
