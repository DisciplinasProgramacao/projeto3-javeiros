package estacionamentos;

public class UsuarioHorista implements Usuario{
    private float valorHora;
    private float tempoEstacionado;

    /**
     * Construtor UsuarioHorista que recebe o valor por hora para usuários horistas.
     * @param valorHora Valor a ser cobrado por hora de estacionamento.
     */
    public UsuarioHorista(float valorHora) {
        this.valorHora = valorHora;
    }

    /**
     * Cálculo do valor total do estacionamentoa partir do tempo estacionado e do valor por hora.
     * @return Valor total do estacionamento.
     */
    private float calcularValorEstacionamento() {
        return valorHora * tempoEstacionado;
    }

    /**
     * Implementação do método etacionar para usuários horistas.
     */
    @Override
    public void estacionar() {
        System.out.println("Horas estacionadas: " + tempoEstacionado);
        System.out.println("Valor do estacionamento: R$" + valorHora);  
    }
    
}
