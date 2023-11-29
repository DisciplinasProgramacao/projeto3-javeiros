package excecoes;

public class ExcecaoVeiculoJaEstacionado extends RuntimeException{
    public ExcecaoVeiculoJaEstacionado(){
        super("Esse veículo já está estacionado");
    }
}
