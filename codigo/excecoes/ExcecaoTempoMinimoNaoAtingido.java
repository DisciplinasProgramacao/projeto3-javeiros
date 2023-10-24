package excecoes;
/**
 * Exceção lançada quando o tempo mínimo de permanência pra fazer o serviço não é atingido.
 */
public class ExcecaoTempoMinimoNaoAtingido extends RuntimeException {
    
    public ExcecaoTempoMinimoNaoAtingido(){
        super("Tempo minimo exigido não atingido");
    }
}
