package excecoes;

public class ExcecaoNenhumClienteCadastrado extends RuntimeException {
    
    public ExcecaoNenhumClienteCadastrado(){
        super("Nenhum cliente foi cadastrado previamente.");
    }
}
