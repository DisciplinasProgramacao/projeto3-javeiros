package estacionamentos.Enums;
import java.time.Duration;
import java.time.LocalTime;

public enum TipoUso {
    HORISTA,
    TURNO,
    MENSALISTA;

    private double valor;
    private Turno turno;

    /**
     * Construtor padrão para o Enum TipoUso.
     */
    TipoUso(){
    }

    /**
     * Construtor para Enum TipoUso com valor pagamento definido.
     *
     * @param valor Valor associado ao tipo de uso.
     */
    TipoUso(double valor) {
        this.valor = valor;
    }

    /**
     * Construtor para Enum TipoUso com valor pagamento e turno definidos.
     *
     * @param valor Valor associado ao tipo de uso.
     * @param turno Turno associado ao tipo de uso.
     */
    TipoUso(double valor, Turno turno) {
        this.valor = valor;
        this.turno = turno;
    }   

    public double getValor() {
        return valor;
    }

    public Turno getTurno() {
        return turno;
    }
    
    /**
     * Método que calcula o valor do estacionamento com base no tipo de uso.
     *
     * @param inicio Horário de início do estacionamento.
     * @param fim    Horário de fim do estacionamento.
     * @return Valor calculado do estacionamento.
     */
    public double calcularValor(LocalTime inicio, LocalTime fim) {
        if (this == TURNO && turno != null && turno.isTurnoValido(inicio, fim)) {
            return this.valor;
        } else if (this == MENSALISTA){
            return this.valor;
        } else {
            long duracaoHoras = (Duration.between(inicio, fim).toMinutes())/60;
            return valor * duracaoHoras;
        }
    }

   
}
