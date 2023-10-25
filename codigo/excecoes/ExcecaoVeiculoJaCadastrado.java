package excecoes;

public class ExcecaoVeiculoJaCadastrado extends RuntimeException {

    public ExcecaoVeiculoJaCadastrado(String msg){
        super(msg);
    }
}
