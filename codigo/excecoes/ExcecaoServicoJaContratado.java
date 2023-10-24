package excecoes;
/**
 * Exceção lançada quando já existe serviço.
 */
public class ExcecaoServicoJaContratado extends RuntimeException {
    
    public ExcecaoServicoJaContratado(){
        super("O uso de vaga já possui um serviço contratado.");
    }

}
