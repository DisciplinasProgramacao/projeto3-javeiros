import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

import excecoes.ExcecaoNenhumClienteCadastrado;
import excecoes.ExcecaoSaidaJaFinalizada;
import estacionamentos.*;
import estacionamentos.Enums.TipoUso;
import estacionamentos.interfaces.UsoDeVagaFactory;
import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoClienteNaoCadastrado;
import excecoes.ExcecaoEstacionamentoNaoCadastrado;
import excecoes.ExcecaoNaoEhPossivelSairDaVaga;
import excecoes.ExcecaoNaoPossuiVagasDisponiveis;
import excecoes.ExcecaoVeiculoJaCadastrado;
import excecoes.ExcecaoVeiculoJaEstacionado;
import excecoes.ExcecaoVeiculoNaoCadastrado;
import excecoes.ExcecaoEstacionamentoNaoCadastrado;
import excecoes.ExcecaoOpicaoInvalida;
import excecoes.ExcecaoRelatorioVazio;

public class App {

    private static Scanner teclado = new Scanner(System.in);
    private static Estacionamento[] estacionamentos = new Estacionamento[40];
    private static Estacionamento estacionamentoHelper;
    private static List<Estacionamento> todosEstacionamentos = new ArrayList<Estacionamento>();
    private static UsoDeVagaFactory usoDeVagaFactory;

    public static void main(String args[]) {
        try {
            menu();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // !CRIAÇÃO DE DADOS
    // todo: criar dados
    // todo: salvar dados de Clientes em arquivos
    // todo: salvar dados de Veículos em arquivos
    // Carrega dados iniciais
    private static void carregarDadosIniciais() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("dados.dat"))) {
            todosEstacionamentos = (List<Estacionamento>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Arquivo de dados não encontrado
            // Cria dados iniciais manualmente
            // criarDadosIniciais();
        }
    }

    /**
     * Cria um novo uso de vaga de acordo com o tipo de uso do veículo.
     * 
     * @param vaga Vaga a ser utilizada.
     * @return Uso de vaga criado.
     */
    private UsoDeVaga criarUsoDeVaga(Vaga vaga) {
        return usoDeVagaFactory.criarUsoDeVaga(vaga);
    }

    /**
     * Inicializa os dados do aplicativo com a criação de estacionamentos, clientes
     * e veículos, além de simular usos de estacionamento.
     * Este método configura o ambiente inicial do aplicativo, incluindo:
     * - A criação de 3 estacionamentos.
     * - A adição de 10 clientes, utilizando todos os planos de uso disponíveis
     * (Horista, Mensalista, Turno).
     * - A inclusão de 15 veículos, distribuídos entre os clientes.
     * - A simulação de 50 usos de estacionamento, contratando os serviços
     * disponíveis.
     * Além disso, prepara o sistema para o salvamento de dados de Clientes e
     * Veículos em arquivos.
     * 
     * @throws ExcecaoClienteJaCadastrado       Se um cliente já está cadastrado no
     *                                          estacionamento.
     * @throws ExcecaoVeiculoJaCadastrado       Se um veículo já está cadastrado
     *                                          para um cliente.
     * @throws ExcecaoVeiculoNaoCadastrado      Se um veículo não está cadastrado no
     *                                          estacionamento.
     * @throws ExcecaoNaoPossuiVagasDisponiveis Se não há vagas disponíveis no
     *                                          estacionamento.
     */
    public static void criarDadosIniciais() throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado,
            ExcecaoVeiculoNaoCadastrado, ExcecaoNaoPossuiVagasDisponiveis {

        // Criação de 3 estacionamentos
        Estacionamento estacionamentoA = new Estacionamento("Estacionamento A");
        Estacionamento estacionamentoB = new Estacionamento("Estacionamento B");
        Estacionamento estacionamentoC = new Estacionamento("Estacionamento C");

        todosEstacionamentos.add(estacionamentoA);
        todosEstacionamentos.add(estacionamentoB);
        todosEstacionamentos.add(estacionamentoC);

        // Criação de 10 clientes com os diferentes tipos de uso disponíveis
        Cliente cliente1 = new Cliente("Cliente1", "ID1", TipoUso.HORISTA);
        Cliente cliente2 = new Cliente("Cliente2", "ID2", TipoUso.HORISTA);
        Cliente cliente3 = new Cliente("Cliente3", "ID3", TipoUso.HORISTA);
        Cliente cliente4 = new Cliente("Cliente4", "ID4", TipoUso.HORISTA);
        Cliente cliente5 = new Cliente("Cliente5", "ID5", TipoUso.HORISTA);

        Cliente cliente6 = new Cliente("Cliente6", "ID6", TipoUso.TURNO);
        Cliente cliente7 = new Cliente("Cliente7", "ID7", TipoUso.TURNO);
        Cliente cliente8 = new Cliente("Cliente8", "ID8", TipoUso.TURNO);

        Cliente cliente9 = new Cliente("Cliente9", "ID9", TipoUso.MENSALISTA);
        Cliente cliente10 = new Cliente("Cliente10", "ID10", TipoUso.MENSALISTA);

        estacionamentoA.addCliente(cliente1);
        estacionamentoA.addCliente(cliente2);
        estacionamentoA.addCliente(cliente3);
        estacionamentoA.addCliente(cliente4);
        estacionamentoA.addCliente(cliente5);
        estacionamentoB.addCliente(cliente6);
        estacionamentoB.addCliente(cliente7);
        estacionamentoB.addCliente(cliente8);
        estacionamentoC.addCliente(cliente9);
        estacionamentoC.addCliente(cliente10);

        // Criação de 15 veículos
        Veiculo veiculo1 = new Veiculo("PlacaH1", TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        Veiculo veiculo2 = new Veiculo("PlacaH2", TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        Veiculo veiculo3 = new Veiculo("PlacaH3", TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        Veiculo veiculo4 = new Veiculo("PlacaH4", TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());
        Veiculo veiculo5 = new Veiculo("PlacaH5", TipoUso.HORISTA, UsoDeVagaFactory.criarHoristaFactory());

        Veiculo veiculo6 = new Veiculo("PlacaM1", TipoUso.MENSALISTA, UsoDeVagaFactory.criarMensalistaFactory());
        Veiculo veiculo7 = new Veiculo("PlacaM2", TipoUso.MENSALISTA, UsoDeVagaFactory.criarMensalistaFactory());
        Veiculo veiculo8 = new Veiculo("PlacaM3", TipoUso.MENSALISTA, UsoDeVagaFactory.criarMensalistaFactory());
        Veiculo veiculo9 = new Veiculo("PlacaM4", TipoUso.MENSALISTA, UsoDeVagaFactory.criarMensalistaFactory());
        Veiculo veiculo10 = new Veiculo("PlacaM5", TipoUso.MENSALISTA, UsoDeVagaFactory.criarMensalistaFactory());

        Veiculo veiculo11 = new Veiculo("PlacaT1", TipoUso.TURNO, UsoDeVagaFactory.criarTurnoFactory());
        Veiculo veiculo12 = new Veiculo("PlacaT2", TipoUso.TURNO, UsoDeVagaFactory.criarTurnoFactory());
        Veiculo veiculo13 = new Veiculo("PlacaT3", TipoUso.TURNO, UsoDeVagaFactory.criarTurnoFactory());
        Veiculo veiculo14 = new Veiculo("PlacaT4", TipoUso.TURNO, UsoDeVagaFactory.criarTurnoFactory());
        Veiculo veiculo15 = new Veiculo("PlacaT5", TipoUso.TURNO, UsoDeVagaFactory.criarTurnoFactory());

        // 50 usos de estacionamento contratando os serviços disponíveis, com base nos
        // dados criados acima.
        for (int i = 0; i < 50; i++) {
            Estacionamento estacionamentoEscolhido = todosEstacionamentos
                    .get(new Random().nextInt(todosEstacionamentos.size()));
            List<Cliente> clientes = new ArrayList<>(estacionamentoEscolhido.getId().values());
            Cliente clienteEscolhido = clientes.get(new Random().nextInt(clientes.size()));
            Veiculo veiculoEscolhido = escolherVeiculoAleatorio(clienteEscolhido);

            if (veiculoEscolhido != null) {
                try {
                    // Vai simular a entrada de um veículo
                    estacionamentoEscolhido.estacionar(veiculoEscolhido.getPlaca());

                    // Simula a saída do veículo após algumas horas
                    Thread.sleep(1000); // Uma pausa breve para simular o tempo de estacionamento
                    estacionamentoEscolhido.sair(veiculoEscolhido.getPlaca());
                } catch (ExcecaoVeiculoJaEstacionado | ExcecaoVeiculoNaoCadastrado
                        | ExcecaoNaoPossuiVagasDisponiveis e) {
                    System.out.println("Erro ao estacionar/sair: " + e.getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupção durante a simulação de estacionamento.");
                }
            }
        }
    }

    /**
     * Gera um horário aleatório dentro dos últimos 30 dias e 24 horas.
     * Este método é utilizado para simular horários de entrada e saída dos veículos
     * no estacionamento.
     *
     * @return LocalDateTime representando um horário aleatório.
     */
    private static LocalDateTime gerarHorarioAleatorio() {
        LocalDateTime agora = LocalDateTime.now();
        long diasAleatorios = new Random().nextInt(30);
        long horasAleatorias = new Random().nextInt(24);
        return agora.minus(diasAleatorios, ChronoUnit.DAYS).minus(horasAleatorias, ChronoUnit.HOURS);
    }

    /**
     * Escolhe aleatoriamente um veículo de um cliente específico.
     * Este método é utilizado para selecionar um veículo aleatório de um cliente
     * para simular o uso do estacionamento.
     *
     * @param cliente O cliente do qual um veículo será escolhido aleatoriamente.
     * @return Veiculo escolhido aleatoriamente ou null se o cliente não tiver
     *         veículos.
     */
    private static Veiculo escolherVeiculoAleatorio(Cliente cliente) {
        List<Veiculo> veiculos = cliente.getVeiculos();
        if (veiculos.isEmpty()) {
            return null;
        }
        return veiculos.get(new Random().nextInt(veiculos.size()));
    }

    // Criação dos clientes e veículos
    /*
     * for (Estacionamento estacionamento : todosEstacionamentos) {
     * for (int i = 0; i < 2; i++) {
     * Cliente cliente = new Cliente("Cliente" + (i + 1), "ID" + (i + 1));
     * cliente.addVeiculo(
     * new Veiculo("PlacaH" + (i + 1), TipoUso.HORISTA,
     * UsoDeVagaFactory.criarHoristaFactory()));
     * cliente.addVeiculo(
     * new Veiculo("PlacaM" + (i + 1), TipoUso.MENSALISTA,
     * UsoDeVagaFactory.criarMensalistaFactory()));
     * cliente.addVeiculo(
     * new Veiculo("PlacaT" + (i + 1), TipoUso.TURNO,
     * UsoDeVagaFactory.criarTurnoFactory()));
     * estacionamento.addCliente(cliente);
     * }
     * }
     * 
     * // Criação de usos de vagas
     * for (int i = 0; i < 50; i++) {
     * int estacionamentoIndex = i % 3;
     * Estacionamento estacionamento =
     * todosEstacionamentos.get(estacionamentoIndex);
     * 
     * int clienteIndex = i % 2; // 2 clientes por estacionamento
     * Cliente cliente = estacionamento.id.values().toArray(new
     * Cliente[0])[clienteIndex];
     * 
     * estacionamento.estacionar(cliente.getVeiculos().get(0).getPlaca());
     * estacionamento.sair(cliente.getVeiculos().get(0).getPlaca());
     * }
     */

    

    // Método para salvar os dados
    private static void salvarDados() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dados.dat"))) {
            out.writeObject(todosEstacionamentos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu() throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado,
            ExcecaoVeiculoNaoCadastrado, ExcecaoNaoPossuiVagasDisponiveis, ExcecaoEstacionamentoNaoCadastrado,
            ExcecaoOpicaoInvalida {

        int i = 0;

        do {
            try {
                System.out.println("|----------------------------------------------------|");
                System.out.println("|                    Menu Principal                  |");
                System.out.println("|----------------------------------------------------|");
                System.out.println("| 1. Criar um Estacionamento                         |");
                System.out.println("| 2. Acessar um Estacionamento                       |");
                System.out.println("| 3. Relatorio Arrecadacao Estacionamentos por Mes   |");
                System.out.println("| 4. Sair                                            |");
                System.out.println("|----------------------------------------------------|");
                System.out.print("Digite uma das opções acima: ");

                i = Integer.parseInt(teclado.nextLine());

                switch (i) {
                    case 1:
                        addEstacionamento();
                        break;
                    case 2:
                        Estacionamento estc = selecionarEstacionamentos();
                        App.switchMenuEstacionameto(estc);
                        break;
                    case 3:
                        exibirArrecadacaoTotalPorEstacionamento();
                        break;
                    case 4:
                        System.out.println("Encerrando...");
                        break;
                    default:
                        throw new ExcecaoOpicaoInvalida("Opicao digitada invalida");
                }
            } catch (ExcecaoOpicaoInvalida e) {
                System.out.println(e);
            }
        } while (i != 4);

    }

    public static void addEstacionamento() {

        String nome;
        int fileiras;
        int veiculosFileiras;
        Estacionamento estacionamento;

        System.out.println("Digite o nome do Estacionamento:");
        nome = teclado.nextLine();
        estacionamento = new Estacionamento(nome);

        todosEstacionamentos.add(estacionamento);
        System.out.println("Estacionamento " + estacionamento.getNome() + " criado com sucesso!");

    }

    public static Estacionamento selecionarEstacionamentos() throws ExcecaoEstacionamentoNaoCadastrado {
        Estacionamento estacionamentoSelecionado = null;

        // if(estacionamentos.length < 1){
        // throw Alguma coisa
        // }

        while (estacionamentoSelecionado == null) {
            System.out.println("Digite o nome do estacionamento que você quer acessar:");
            int i = 0;
            for (Estacionamento estacionamento : todosEstacionamentos) {
                i++;
                System.out.println(i + "- " + estacionamento.getNome());
            }

            String nometmp = teclado.nextLine();
            i = 0;

            for (Estacionamento estacionamento : todosEstacionamentos) {
                if (estacionamento.getNome().equals(nometmp)) {
                    estacionamentoSelecionado = estacionamento;
                }
            }
            if (estacionamentoSelecionado == null) {
                throw new ExcecaoEstacionamentoNaoCadastrado("O estacionamento escrito não está cadastrado no sistema");
            }
        }
        ;

        return estacionamentoSelecionado;

    }

    public static void switchMenuEstacionameto(Estacionamento estacionamento) {
        int option = 1;
        while (option != 0) {
            try {
                System.out.println("\nEstacionamento: " + estacionamento.getNome().toUpperCase());
                System.out.println("|---------------------------------------------------------------------------|");
                System.out.println("|    Selecione uma das Opcões                                               |");
                System.out.println("|---------------------------------------------------------------------------|");
                System.out.println("| 1. Adicionar Cliente                                                      |");
                System.out.println("| 2. Adicionar Veiculo                                                      |");
                System.out.println("| 3. Estacionar                                                             |");
                System.out.println("| 4. Sair da vaga                                                           |");
                System.out.println("| 5. Gerar vagas                                                            |");
                System.out.println("| --------------------------------------------------------------------------|");
                System.out.println("| RELATORIOS:                                                               |");
                System.out.println("| 6. Total Arrecadado                                                       |");
                System.out.println("| 7. Arracadação no Mes                                                     |");
                System.out.println("| 8. Valor Médio por Uso                                                    |");
                System.out.println("| 9. Top 5 clientes                                                         |");
                System.out.println("| 10. Média de utilização dos clientes mensalistas                          |");
                System.out.println("| 11. Arrecadação média gerada pelos clientes horistas no mês corrente      |");
                System.out.println("| 12. Relatório do Veiculo                                                  |");
                System.out.println("| 13. Histórico do Cliente                                                  |");
                System.out.println("| --------------------------------------------------------------------------|");
                System.out.println("| 14. Alterar plano do Cliente                                              |");
                System.out.println("| 0 . Sair                                                                  |");
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
                        gerarVagas(estacionamento);
                        break;
                    case 6:
                        totalArrecadado(estacionamento);
                        break;
                    case 7:
                        arrecadadoNoMes(estacionamento);
                        break;
                    case 8:
                        valorMedioUso(estacionamento);
                        break;
                    case 9:
                        topClientes(estacionamento);
                        break;
                    case 10:
                        mediaUsosMensalistasMesCorrente(estacionamento);
                        break;
                    case 11:
                        arrecadacaoMediaClientesHoristasNoMesCorrente(estacionamento);
                        break;
                    case 12:
                        relatorioDoVeiculo(estacionamento);
                        break;
                    case 13:
                        historicoCliente(estacionamento);
                        break;
                    case 14:
                        mudarTipoUsoCliente(estacionamento);
                        break;
                    case 0:
                        break;
                    default:
                        throw new ExcecaoOpicaoInvalida("A opicao digitada e invalida");
                }
            } catch (Exception e) {
                System.out.println(e);
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

        UsoDeVagaFactory usoDeVagaFactory;
        switch (tipoUso) {
            case HORISTA:
                usoDeVagaFactory = UsoDeVagaFactory.criarHoristaFactory();
                break;
            case MENSALISTA:
                usoDeVagaFactory = UsoDeVagaFactory.criarMensalistaFactory();
                break;
            case TURNO:
                usoDeVagaFactory = UsoDeVagaFactory.criarTurnoFactory();
                break;
            default:
                throw new IllegalArgumentException("Tipo de uso inválido");
        }
        estacionamento.addVeiculo(placa, idCli, tipoUso, usoDeVagaFactory);
    }

    public static void estacionar(Estacionamento estacionamento) throws ExcecaoNaoPossuiVagasDisponiveis {
        System.out.println("Digite a placa do veiculo: ");
        String placa = teclado.nextLine();
        estacionamento.estacionar(placa);
        System.out.println("Veiculo estacionado com sucesso!");
    }

    public static void sair(Estacionamento estacionamento) {
        System.out.println("Digite a placa da vaga na qual o veiculo irá sair");
        try {
            String placa = teclado.nextLine();
            Double valor = estacionamento.sair(placa);
            if (valor != 0.0) {
                System.out.println("Veículo retirado. Valor pago = " + valor);
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
        // !Implementar todos os métodos necessários
        int mesAtual = LocalDate.now().getMonthValue();
        double soma = 0;
        int cont = 0;
        for (int i = mesAtual; i > 0; i--) {
            soma += estacionamento.arrecadacaoNoMes(i);
            cont++;
        }

        System.out.println("Valor medio uso: " + (soma / cont));
    }

    public static void topClientes(Estacionamento estacionamento) {
        System.out.println("Digite o número do mes, para saber quais foram os top 5 clientes em determinado mês:\n");
        int mes = Integer.parseInt(teclado.nextLine());
        System.out.println(estacionamento.top5Clientes(mes));

    }

    /**
     * Função para exibir o arrecadado total do estacionamento em ordem decrescente
     * de valores
     * 
     * @return seu retorno é uma saida no console com os valores em ordem
     *         decrescente.
     */
    public static void exibirArrecadacaoTotalPorEstacionamento() {
        System.out.println("Digite o mes de arrecadação:");

        int mes = Integer.parseInt(teclado.nextLine());

        List<Estacionamento> estacionametosOrdenados = new ArrayList<>();

        for (int i = 0; i > todosEstacionamentos.size(); i++) {
            estacionametosOrdenados.add(todosEstacionamentos.get(i));
        }

        Comparator<Estacionamento> c = (e1, e2) -> {
            return Double.compare(e1.arrecadacaoNoMes(mes), e2.arrecadacaoNoMes(mes));
        };

        estacionametosOrdenados.sort(c);

        for (int i = estacionametosOrdenados.size() - 1; i >= 0; i--) {
            Estacionamento estacionamento = estacionametosOrdenados.get(i);
            System.out.println(i + "- " + estacionamento.getNome() + ": " + estacionamento.arrecadacaoNoMes(mes));
        }

    }

    /**
     * Calcula e imprime a média de usos mensais para clientes mensalistas no mês
     * corrente.
     *
     * Este método recebe um objeto Estacionamento e utiliza o método
     * mediaUsosClientesMensalistas()
     * desse objeto para calcular a média de usos mensaxis para clientes mensalistas
     * no mês corrente.
     * A média calculada é então impressa no console.
     *
     * @param estacionamento O objeto Estacionamento que contém as informações sobre
     *                       os clientes e seus usos.
     */
    public static void mediaUsosMensalistasMesCorrente(Estacionamento estacionamento) {
        System.out.println("A média dos usuos dos cliente mensalistas no mes correte foi de: "
                + estacionamento.mediaUsoClienteMensalista()+ "\n O valor coparado ao total de utilização representa: " + estacionamento.percentualUsoMesalistaMesCorrente() + "%");
    }

    public static void arrecadacaoMediaClientesHoristasNoMesCorrente(Estacionamento estacionamento)
            throws ExcecaoNenhumClienteCadastrado {

        double resultado = estacionamento.arrecadacaoClientesHoristas();

        System.out.println(
                "O valor médio Arrecadado pelos clientes horistas do estacionamento \"" + estacionamento.getNome()
                        + "\" no mês é: " + resultado);
    }

    public static void gerarVagas(Estacionamento estacionamento) {
        int vagas;
        System.out.println("Digite o numero de vagas os qual deseja gerar?");
        vagas = Integer.parseInt(teclado.nextLine());
        estacionamento.gerarVagas(vagas);
        ;
    }

    public static void relatorioDoVeiculo(Estacionamento estacionamento) throws ExcecaoOpicaoInvalida {

        System.out.println("Digite a placa do veiculo");
        String placa = teclado.nextLine();
        System.out.println("|----------------------------------|");
        System.out.println("| Selecione o método de ordenação: |");
        System.out.println("| 1: Ordem crescente de data       |");
        System.out.println("| 2: Ordem decrescente de valor    |");
        System.out.println("|----------------------------------|");

        int metodoOrdenar = Integer.parseInt(teclado.nextLine());

        try{
        String relatorio = estacionamento.relatorioVeiculo(placa, metodoOrdenar);
        System.out.println(relatorio);
        } catch (ExcecaoVeiculoNaoCadastrado | ExcecaoRelatorioVazio e) {
        System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void historicoCliente(Estacionamento estacionamento)
            throws ExcecaoOpicaoInvalida, ExcecaoClienteNaoCadastrado {
        String idCliente;
        String dataInicio = "";
        String dataFim = "";

        System.out.println("Digite o Id do Cliente");
        idCliente = teclado.nextLine();
        System.out.println("Deseja filtar por data?");
        System.out.println("0 - SIM");
        System.out.println("1 - NAO");
        int option = Integer.parseInt(teclado.nextLine());

        switch (option) {
            case 0:
                dataInicio = "01/01/1900";
                dataFim = "30/12/2100";
                break;

            case 1:
                System.out.println("|  Digite a data de início e fim da pesquisa  |");
                System.out.println("Utilize o seguinte formato dd/MM/aaaa");
                System.out.println("Data inicio: ");
                dataInicio = teclado.nextLine();
                System.out.println("Data Fim: ");
                dataFim = teclado.nextLine();

                dataInicio += " 00:00:00";
                dataFim += " 00:00:00";
                break;

            default:
                throw new ExcecaoOpicaoInvalida("Opcao selecionada inválida");
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dataIncioLocalDateTime;
        LocalDateTime dataFimLocalDateTime;

        try {
            // Convertendo a string para um objeto LocalDate
            dataIncioLocalDateTime = LocalDateTime.parse(dataInicio, formato);
            dataFimLocalDateTime = LocalDateTime.parse(dataFim, formato);
            System.out
                    .println(estacionamento.historicoCliente(idCliente, dataIncioLocalDateTime, dataFimLocalDateTime));
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data: " + e.getMessage());
        }

    }


    public static void mudarTipoUsoCliente(Estacionamento estacionamento){
        String id;
        System.out.println("Digite o id do cliente: ");
        id = teclado.nextLine();
        System.out.println("Digite o tipo de uso (HORISTA, MENSALISTA OU TURNO): ");
        TipoUso tipoUso = TipoUso.valueOf(teclado.nextLine().toUpperCase());
        estacionamento.alteraTipoUsoCliente(tipoUso, id);
    }



}