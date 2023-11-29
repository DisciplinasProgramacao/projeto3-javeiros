package excecoes;

public class ExcecaoVeiculoNaoCadastrado extends RuntimeException {
    public ExcecaoVeiculoNaoCadastrado(String msg) {
        super(msg);
    }
}