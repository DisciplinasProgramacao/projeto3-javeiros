package excecoes;

public class ExcecaoNaoEhPossivelSairDaVaga extends RuntimeException {
    
    public ExcecaoNaoEhPossivelSairDaVaga(){
        super("O carro não está estacionado na vaga.");
    }
}
