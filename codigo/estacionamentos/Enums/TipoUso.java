package estacionamentos.Enums;
import java.time.Duration;
import java.time.LocalTime;

public enum TipoUso {
    HORISTA(4.00),
    TURNO(200.00),
    MENSALISTA(500.00);

    private double valor;

    TipoUso(){

    }

    /**
     * Construtor para Enum TipoUso
     * 
     * @param valor Valor associado ao tipo de uso.
     */
    TipoUso(double valor) {
        setValor(valor);
    }   

    public double getValor() {
        return valor;
    }

    public void setValor(double valor){
        this.valor = valor;
    }

    /*public double calcularValor(LocalTime inicio, LocalTime fim) {
        if (this == TURNO && turno != null && turno.isTurnoValido(inicio, fim)) {
            return this.valor;
        } else if (this == MENSALISTA){
            return this.valor;
        } else {
            long duracaoHoras = (Duration.between(inicio, fim).toMinutes())/60;
            return valor * duracaoHoras;
        }
    }*/

    //4 reais a cada 15 minutos
    //máximo 50 reais  (não tem tempo máximo)

   
}
