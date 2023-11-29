import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import excecoes.ExcecaoNenhumClienteCadastrado;
import excecoes.ExcecaoSaidaJaFinalizada;
import estacionamentos.*;
import estacionamentos.Enums.TipoUso;
import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoEstacionamentoNaoCadastrado;
import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoNaoPossuiVagasDisponiveis;
import excecoes.ExcecaoVeiculoJaCadastrado;
import excecoes.ExcecaoVeiculoJaEstacionado;
import excecoes.ExcecaoVeiculoNaoCadastrado;
import excecoes.ExcecaoEstacionamentoNaoCadastrado;

public class App {

    private static Scanner teclado = new Scanner(System.in);
    private static Estacionamento[] estacionamentos = new Estacionamento[40];
    private static Estacionamento estacionamentoHelper;
    private static List<Estacionamento> todosEstacionamentos = new ArrayList<Estacionamento>();

    // Carrega dados iniciais
    private static void carregarDadosIniciais() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("dados.dat"))) {
            todosEstacionamentos = (List<Estacionamento>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Arquivo de dados não encontrado
            // Cria dados iniciais manualmente
            //criarDadosIniciais();
        }
    }

    // Cria dados iniciais manualmente
    public static void criarDadosIniciais() throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado, ExcecaoVeiculoNaoCadastrado, ExcecaoNaoPossuiVagasDisponiveis {
        // Criação dos estacionamentos
        Estacionamento estacionamento1 = new Estacionamento("Estacionamento A");
        Estacionamento estacionamento2 = new Estacionamento("Estacionamento B");
        Estacionamento estacionamento3 = new Estacionamento("Estacionamento C");
    
        todosEstacionamentos.add(estacionamento1);
        todosEstacionamentos.add(estacionamento2);
        todosEstacionamentos.add(estacionamento3);
    
        // Criação dos clientes e veículos
        for (Estacionamento estacionamento : todosEstacionamentos) {
            for (int i = 0; i < 2; i++) {
                Cliente cliente = new Cliente("Cliente" + (i + 1), "ID" + (i + 1));
                cliente.addVeiculo(new Veiculo("PlacaH" + (i + 1), TipoUso.HORISTA));
                cliente.addVeiculo(new Veiculo("PlacaM" + (i + 1), TipoUso.MENSALISTA));
                cliente.addVeiculo(new Veiculo("PlacaT" + (i + 1), TipoUso.TURNO));
                estacionamento.addCliente(cliente);
            }
        }
    
        // Criação de usos de vagas
        for (int i = 0; i < 50; i++) {
            int estacionamentoIndex = i % 3;
            Estacionamento estacionamento = todosEstacionamentos.get(estacionamentoIndex);
    
            int clienteIndex = i % 2;  // 2 clientes por estacionamento
            Cliente cliente = estacionamento.id.values().toArray(new Cliente[0])[clienteIndex];
    
            estacionamento.estacionar(cliente.getVeiculos().get(0).getPlaca());
            estacionamento.sair(cliente.getVeiculos().get(0).getPlaca());
        }
    }

    // Método para salvar os dados
    private static void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dados.dat"))) {
            out.writeObject(todosEstacionamentos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu() throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado, ExcecaoVeiculoNaoCadastrado, ExcecaoNaoPossuiVagasDisponiveis,  ExcecaoEstacionamentoNaoCadastrado {

        int i = 0;
        do {
            System.out.println("|-------------------------------|");
            System.out.println("|        Menu Principal         |");
            System.out.println("|-------------------------------|");
            System.out.println("| 1. Criar um Estacionamento    |");
            System.out.println("| 2. Acessar um Estacionamento  |");
            System.out.println("| 3. Sair                       |");
            System.out.println("|-------------------------------|");
            System.out.print("Digite uma das opções acima: ");

            // i = Integer.parseInt(teclado.nextLine());

            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, insira um número válido.");
                teclado.nextLine();
            }

            i = teclado.nextInt();

        
            switch (i) {
                case 1:
                    addEstacionamento();
                    break;
                case 2:
                    Estacionamento estc = selecionarEstacionamentos();
                    App.switchMenuEstacionameto(estc);
                    break;
                case 3:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (i != 3);

    }

    public static void addEstacionamento() {

        String nome;
        int fileiras;
        int veiculosFileiras;
        Estacionamento estacionamento;

        System.out.println("Digite o nome do Estacionamento:");
        nome = teclado.next();
        estacionamento = new Estacionamento(nome);

        todosEstacionamentos.add(estacionamento);
        System.out.println("Estacionamento " + estacionamento.getNome() + " criado com sucesso!");

    }


    public static Estacionamento selecionarEstacionamentos() throws ExcecaoEstacionamentoNaoCadastrado {
        Estacionamento estacionamentoSelecionado = null;

        // if(estacionamentos.length < 1){
        //     throw Alguma coisa
        // }

        while(estacionamentoSelecionado == null) {
            System.out.println("Digite o nome do estacionamento que você quer acessar:");
            int i = 0;
            for (Estacionamento estacionamento : todosEstacionamentos) {
                i++;
                System.out.println(i + "- "+ estacionamento.getNome());
            }

            String nometmp = teclado.next();
            i = 0;

            for (Estacionamento estacionamento : todosEstacionamentos) {
                if (estacionamento.getNome().equals(nometmp)) {
                    estacionamentoSelecionado = estacionamento;
                }
            }
            if(estacionamentoSelecionado == null){
                throw new ExcecaoEstacionamentoNaoCadastrado("O estacionamento escrito não está cadastrado no sistema");
            }
        } ;

        return estacionamentoSelecionado;
        
    }

    public static void switchMenuEstacionameto(Estacionamento estacionamento){
        Estacionamento estAtual = estacionamento;
        int option = 0;
        while(option != 9){
            try{
                System.out.println("\nEstacionamento: "+ estacionamento.getNome().toUpperCase());
                System.out.println("|---------------------------------------------------------------------------|");
                System.out.println("|    Selecione uma das Opcões                                               |");
                System.out.println("|---------------------------------------------------------------------------|");
                System.out.println("| 1. Adicionar Cliente                                                      |");
                System.out.println("| 2. Adicionar Veiculo                                                      |");
                System.out.println("| 3. Estacionar                                                             |");
                System.out.println("| 4. Sair da vaga                                                           |");
                System.out.println("| --------------------------------------------------------------------------|");
                System.out.println("| RELATORIOS:                                                               |");
                System.out.println("| 5. Total Arrecadado                                                       |");
                System.out.println("| 6. Arracadação no Mes                                                     |");
                System.out.println("| 7. Valor Médio por Uso                                                    |");
                System.out.println("| 8. Top 5 clientes                                                         |");
                System.out.println("| 9. Arrecadação total de cada um dos estacionamentos, em ordem decrescente |");
                System.out.println("| 10. Média de utilização dos clientes mensalistas                          |");  
                System.out.println("| 11. Arrecadação média gerada pelos clientes horistas no mês corrente      |");
                System.out.println("| 12. Gerar vagas                                                           |");
                System.out.println("| 13. Relatório do Veiculo                                                  |");
                System.out.println("| 14. Histórico do Cliente                                                  |");
                System.out.println("| 14. Sair                                                                  |");
                System.out.println("|---------------------------------------------------------------------------|");
                option = Integer.parseInt(teclado.nextLine());

                switch (option) {
                    case 1:
                        addCliente(estacionamento);
                        break;
                    case 2:
                        addVeiculo(estacionamento); 
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
                        exibirArrecadacaoTotalPorEstacionamento();
                        break;
                    case 10:
                        mediaUsosMensalistasMesCorrente(estacionamento);
                        break;
                    case 11:
                        arrecadacaoMediaClientesHoristasNoMesCorrente(estacionamento);
                        break;
                    case 12:
                        gerarVagas(estacionamento);
                        break;
                    case 13:
                        relatorioDoVeiculo(estacionamento);
                        break;
                    case 14:
                        historicoCliente(estacionamento);
                        break;                   
                    case 15:
                        break;
                }

                System.out.println("Digite 9 para sair do menu do estacionamento ou outro valor para acessar as opções:");
                option = Integer.parseInt(teclado.nextLine());
            }catch(Exception e){
                System.out.println( e);
            }
        }
    }

    public static void addCliente(Estacionamento estacionamento) throws ExcecaoClienteJaCadastrado {
        String nome;
        String id;

        System.out.println("Digite o nome do Cliente: ");
        nome = teclado.nextLine();
        System.out.println("Digite o id do cliente: ");
        id = teclado.nextLine();
        System.out.println("Digite o tipo de uso (HORISTA, MENSALISTA OU TURNO): ");
        TipoUso tipoUso = TipoUso.valueOf(teclado.nextLine().toUpperCase());
        estacionamento.addCliente(new Cliente(nome, id, tipoUso));

    }

     public static void addVeiculo(Estacionamento estacionamento) throws ExcecaoVeiculoJaCadastrado {

        System.out.println("Digite o id do cliente no qual deseja cadastrar o veiculo: ");
        String idCli = teclado.nextLine();
        System.out.println("Digite a placa do veículo: ");
        String placa = teclado.nextLine();
        System.out.println("Digite o tipo de uso (HORISTA, MENSALISTA OU TURNO): ");
        TipoUso tipoUso = TipoUso.valueOf(teclado.nextLine().toUpperCase());
                estacionamento.addVeiculo(placa, idCli, tipoUso);
    }

    public static void estacionar(Estacionamento estacionamento) throws ExcecaoNaoPossuiVagasDisponiveis{
        System.out.println("Digite a placa do veiculos: ");
        try {
            String placa = teclado.nextLine();
            estacionamento.estacionar(placa);
        } catch (ExcecaoVeiculoNaoCadastrado veiculoNaoCadastrado) {
            System.out.println("Esse veiculo não está cadastrado, deseja cadastrar? 1 - Sim | 2 - Não");
            String resposta = teclado.nextLine();

            if(resposta.equals("1")){
                App.addVeiculo(estacionamento);
            }
        } catch (ExcecaoVeiculoJaEstacionado veiculoJaEstacionado){
            System.out.println(veiculoJaEstacionado.getMessage());
        }
 
    }

    public static void sair(Estacionamento estacionamento) {
        System.out.println("Digite a placa da vaga na qual o veiculo irá sair");
        try {
            String placa = teclado.nextLine();
            Double valor = estacionamento.sair(placa);
            if(valor != 0.0){
                System.out.println("Veículo retirado. Valor pago = "+ valor);
            }
        } catch (ExcecaoSaidaJaFinalizada e) {
            System.out.println("Esse veículo não está estacionado.");
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
        System.out.println(estacionamento.top5Clientes(mes));

    }

    
    /**
	 * Função para exibir o arrecadado total do estacionamento em ordem decrescente de valores
	 * 
	 * @return seu retorno é uma saida no console com os valores em ordem decrescente.
	 */
    public static void exibirArrecadacaoTotalPorEstacionamento() {
        //! implementar ordenação decrescente
        System.out.println("Digite o mes de arrecadação:");
        int mes = Integer.parseInt(teclado.nextLine());

        Map<String, Double> listaArrecadado = new TreeMap<>();

        for(Estacionamento estac : estacionamentos){
            if(estac != null){
                listaArrecadado.put(estac.getNome(), estac.arrecadacaoNoMes(mes));
            }
        }

        for(String nome : listaArrecadado.keySet()){
            System.out.println(nome + ": " + listaArrecadado.get(nome));
        }

    }

    /**
     * Calcula e imprime a média de usos mensais para clientes mensalistas no mês corrente.
     *
     * Este método recebe um objeto Estacionamento e utiliza o método mediaUsosClientesMensalistas()
     * desse objeto para calcular a média de usos mensaxis para clientes mensalistas no mês corrente.
     * A média calculada é então impressa no console.
     *
     * @param estacionamento O objeto Estacionamento que contém as informações sobre os clientes e seus usos.
     */
    public static void mediaUsosMensalistasMesCorrente(Estacionamento estacionamento){
        System.out.println("A média dos usuos dos cliente mensalistas no mes correte foi de: "+ estacionamento.mediaUsoClienteMensalista());
    }

    public static void main(String args[]) {
        try{
            menu();
        }catch(Exception e){
            System.out.println(e);
        }
        
    }


    
    public static void arrecadacaoMediaClientesHoristasNoMesCorrente(Estacionamento estacionamento) throws ExcecaoNenhumClienteCadastrado {
        
        double resultado = estacionamento.arrecadacaoClientesHoristas();

        System.out.println(
            "O valor médio Arrecadado pelos clientes horistas do estacionamento \"" + estacionamento.getNome() + "\" no mês é: " + resultado
        );
    }

    public static void gerarVagas(Estacionamento  estacionamento){
        int vagas;
        System.out.println("Digite o numero de vagas os qual deseja gerar?");
        vagas = Integer.parseInt(teclado.nextLine());
        estacionamento.gerarVagas(vagas);; 
    }

    public static void relatorioDoVeiculo(Estacionamento estacionamento){
        
        //! implementar ordenação ordem crescente de data ou decrescente de valor
        System.out.println("Digite a placa do veiculo");
        String placa =  teclado.next();
        System.out.println("Selecione o método de ordenação:");
        System.out.println("01: Ordem crescente de data");
        System.out.println("02: Ordem decrescente de valor");
        int metodo =  Integer.parseInt(teclado.nextLine());

        //! implementar mudança
        String relatorio  = estacionamento.relatorioDoVeiculo(placa);
        System.out.println(relatorio);
    }


    public static void historicoCliente(Estacionamento estacionamento){
        
        //! implementar 
        System.out.println("Digite o Id do Cliente");
        String id =  teclado.next();
        System.out.println("|  Digite a data de início e fim da pesquisa  |");
        System.out.println("Utilize o seguinte formato d/m/aaaa");
        System.out.println("Data inicio: ");
        String dataIncio = teclado.nextLine();
        System.out.println("Data Fim: ");
        String dataFim = teclado.nextLine();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy");

        try {
            // Convertendo a string para um objeto LocalDate
            LocalDate dataIncioLocalDate = LocalDate.parse(dataIncio, formato);
            LocalDate dataFimLocalDate = LocalDate.parse(dataFim, formato);
            
            //! implementar mudanças
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data: " + e.getMessage());
        }

    }

    public static void valorMedioUtilizacao(){
        //!Implementar todos os métodos necessários
    }
    
}