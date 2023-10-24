package excecoes;

public class ExcecaoSaidaJaFinalizada extends RuntimeException {
    
    public ExcecaoSaidaJaFinalizada(){
        super("Saida já foi registrada");
    }
}
