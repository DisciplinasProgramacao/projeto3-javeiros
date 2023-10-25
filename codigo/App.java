import java.time.LocalDate;
import java.util.Scanner;

import estacionamentos.*;
import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoVeiculoJaCadastrado;

public class App {

    private static Scanner teclado = new Scanner(System.in);
    private static Estacionamento[] estacionamentos = new Estacionamento[40];
    private static Estacionamento estacionamentoHelper;

    public static void menu() throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado {

        int i = 0;
        while (i != 3) {
            System.out.println("|-------------------------------|");
            System.out.println("|        Menu Principal         |");
            System.out.println("|-------------------------------|");
            System.out.println("| 1. Criar um Estacionamento    |");
            System.out.println("| 2. Acessar um Estacionamento  |");
            System.out.println("| 3. Sair                       |");
            System.out.println("|-------------------------------|");
            System.out.print("Digite uma das opções acima: ");

            i = Integer.parseInt(teclado.nextLine());

            switch (i) {

                case 1:
                    addEstacionamento();
                    break;
                case 2:
                    Estacionamento estc = selecionarEstacionamentos();
                    //int optionMenu = menuEstacionamento();
                    switchMenuEstacionameto(estc);
                    break;
            }

        }
    }

    public static void addEstacionamento() {

        String nome;
        int fileiras;
        int veiculosFileiras;
        Estacionamento estacionamento;

        System.out.println("Digite o nome do Estacionamento: ");
        nome = teclado.nextLine();
        fileiras = 1;
        veiculosFileiras = 10; //10 vagas totais
        estacionamento = new Estacionamento(nome, fileiras, veiculosFileiras);
        for (int i = 0; i < estacionamentos.length; i++) {
            if (estacionamentos[i] == null) {
                estacionamentos[i] = estacionamento;
                System.out.println("Estacionamento " + estacionamentos[i].getNome() + " criado com sucesso!");
                break;
            }
        }
    }


    public static Estacionamento selecionarEstacionamentos() {
        System.out.println("Digite o nome do estacionamento que você quer acessar:");
        String nometmp = teclado.nextLine();
        for (int i = 0; i < estacionamentos.length; i++) {
            if (estacionamentos[i].getNome().equals(nometmp)) {
                return estacionamentos[i];
            }
        }
        return null;
    }

    public static void switchMenuEstacionameto(Estacionamento estacionamento) throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado {
        Estacionamento estAtual = estacionamento;
        int option = 0;
        while(option != 9){
            System.out.println("\nEstacionamento: "+ estacionamento.getNome().toUpperCase());
            System.out.println("|-------------------------------|");
            System.out.println("|    Selecione uma das Opcões   |");
            System.out.println("|-------------------------------|");
            System.out.println("| 1. Adicionar Cliente          |");
            System.out.println("| 2. Adicionar Veiculo          |");
            System.out.println("| 3. Estacionar                 |");
            System.out.println("| 4. Sair da vaga               |");
            System.out.println("| 5. Total Arrecadado           |");
            System.out.println("| 6. Arracadação no Mes         |");
            System.out.println("| 7. Valor Médio por Uso        |");
            System.out.println("| 8. Top 5 clientes             |");
            System.out.println("| 9. Sair                       |");
            System.out.println("|-------------------------------|");
            option = Integer.parseInt(teclado.nextLine());

            switch (option) {
                case 1:
                    addCliente(estacionamento);
                    break;
                case 2:
                    addVeiculo(estacionamento);  //!ver se essa logica vai funcionar [passar o estacionamento como parametro]
                    break;
                case 3:
                    estacionar(estacionamento);
                    break;
                case 4:
                    sair(estacionamento);
                    break;
                case 5:
                    totalArrecadado(estacionamento);
                    break;
                case 6:
                    arrecadadoNoMes(estacionamento);
                    break;
                case 7:
                    valorMedioUso(estacionamento);
                    break;
                case 8:
                    topClientes(estacionamento);
                    break;
                case 9:
                    break;
            }
            System.out.println("Digite 9 para sair do menu do estacionamento ou outro valor para acessar as opções:");
            option = Integer.parseInt(teclado.nextLine());
        }
    }

    public static void addCliente(Estacionamento estacionamento) throws ExcecaoClienteJaCadastrado {
        String nome;
        String id;

        System.out.println("Digite o nome do Cliente: ");
        nome = teclado.nextLine();
        System.out.println("Digite o id do cliente: ");
        id = teclado.nextLine();
        estacionamento.addCliente(new Cliente(nome, id));

    }

     public static void addVeiculo(Estacionamento estacionamento) throws ExcecaoVeiculoJaCadastrado {

        System.out.println("Digite o id do cliente no qual deseja cadastrar o veiculo: ");
        String idCli = teclado.nextLine();
        System.out.println("Digite a placa do veículo: ");
        String placa = teclado.nextLine();
        estacionamento.addVeiculo(placa, idCli);

    }

    public static void estacionar(Estacionamento estacionamento) {
        System.out.println("Digite a placa do veiculos: ");
        String placa = teclado.nextLine();
        estacionamento.estacionar(placa);
    }

    public static void sair(Estacionamento estacionamento) {
        System.out.println("Digite a placa do veiculo:");
        String placa = teclado.nextLine();
        Double valor = estacionamento.sair(placa);
        if(valor != 0.0){
            System.out.println("Veículo retirado. Valor pago = "+ valor);
        }
    }

    public static void totalArrecadado(Estacionamento estacionamento) {
        System.out.println("Valor total: " + estacionamento.totalArrecadado());
    }    

    public static void arrecadadoNoMes(Estacionamento estacionamento) {
        int mesAtual = LocalDate.now().getMonthValue();

        for (int i = mesAtual; i > 0; i--) {
            System.out.println("| Mês " + i + " -  Valor arrecadado:  " + estacionamento.arrecadacaoNoMes(i) + " |");
        }
    }

    public static void valorMedioUso(Estacionamento estacionamento) {
        int mesAtual = LocalDate.now().getMonthValue();
        double soma = 0;
        int cont = 0;
        for (int i = mesAtual; i > 0; i--) {
            soma += estacionamento.arrecadacaoNoMes(i);
            cont++;
        }

        System.out.println("Valor medio uso: " + (soma/cont));
    }

    public static void topClientes(Estacionamento estacionamento) {
        System.out.println("Digite o número do mes, para saber quais foram os top 5 clientes em determinado mês:\n");
        int mes = Integer.parseInt(teclado.nextLine());
        System.out.println(estacionamentoHelper.top5Clientes(mes));
    }

    public static void main(String args[]) throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado {
        menu();

    }

}