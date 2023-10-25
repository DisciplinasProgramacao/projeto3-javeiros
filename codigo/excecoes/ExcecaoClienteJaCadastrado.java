package excecoes;

public class ExcecaoClienteJaCadastrado extends RuntimeException {

    public ExcecaoClienteJaCadastrado(String msg){
        super(msg);
    }
}