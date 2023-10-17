import java.time.LocalDate;
import java.util.Scanner;

public class Estacionamento {

    static Scanner teclado = new Scanner(System.in);
    private int contClientes = 1;
    private String nome;
    private Cliente[] id;
    private Vaga[] vagas;
    private int quantFileiras;
    private int vagasPorFileira;

    public Estacionamento(String nome, int fileiras, int vagasPorFila) {
        
    }

    public void addVeiculo(Veiculo veiculo, String idCli) {
        
    }

    public void addCliente(Cliente cliente) {
        
    }

    private void gerarVagas() {
        
    }

    public void estacionar(String placa) {
        Veiculo veiculo = null;

        for (Cliente cliente : id) {
            veiculo = cliente.possuiVeiculo(placa);
        }

        if(veiculo == null) 
        return;

        for(Vaga vaga: vagas){
            if(vaga.disponivel()){
                veiculo.estacionar(vaga);
                break;
            }
        }


        
    }
    

    public double sair(String placa) {

        Veiculo veiculo = null;

        for(Cliente cliente : id){
            cliente.possuiVeiculo(placa);
        }

        if(veiculo == null){

        }
        

        veiculo.sair();
    }

    public double totalArrecadado() {
        double total = 0.0;
        for(Cliente cliente: id){
            for(Veiculo veiculo: cliente.getVeiculos()){
                for(UsoDeVaga usodevaga: veiculo.getUsos()){
                    total = total + usodevaga.valorPago();
                }
            }
        }
        return total;
    }

    public double arrecadacaoNoMes(int mes) {
        double total = 0.0;
        LocalDate dataAtual = LocalDate.now();


        for(Cliente cliente: id){
            for(Veiculo veiculo: cliente.getVeiculos()){
                for(UsoDeVaga usodevaga: veiculo.getUsos()){
                    LocalDate dataSaida = usodevaga.getSaida();
                    LocalDate dataComparacao = dataAtual.minusMonths(mes);
                    if (dataSaida.getYear() == dataComparacao.getYear() && dataSaida.getMonthValue() == dataComparacao.getMonthValue()) {
                        total += usodevaga.getValorPago();
                    }
                }
            }
        }
        return total;
    }

    public double valorMedioPorUso() {
        
    }

    public String top5Clientes(int mes) {
        
    }

    public Cliente[] getId() {
        return null;
    }

}
