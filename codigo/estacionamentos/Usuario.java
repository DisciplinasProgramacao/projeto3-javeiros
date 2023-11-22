package estacionamentos;

/**
 * Interface que define o contrato para diferentes tipos de usuários em um estacionamento
 */
public interface Usuario {

    /**
     * Realiza a operação de estacionar com lógica específica a depender do tipo de usuário
     */
    public void estacionar();

}
