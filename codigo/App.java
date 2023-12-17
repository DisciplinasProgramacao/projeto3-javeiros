import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import excecoes.ExcecaoNenhumClienteCadastrado;
import excecoes.ExcecaoSaidaJaFinalizada;
import estacionamentos.*;
import estacionamentos.Enums.TipoServico;
import estacionamentos.Enums.TipoTurno;
import estacionamentos.Enums.TipoUso;
import estacionamentos.interfaces.CalcularUsoDeVaga;
import estacionamentos.interfaces.UsoDeVagaFactory;
import excecoes.ExcecaoClienteJaCadastrado;
import excecoes.ExcecaoClienteNaoCadastrado;
import excecoes.ExcecaoEstacionamentoNaoCadastrado;
import excecoes.ExcecaoNaoPossuiVagasDisponiveis;
import excecoes.ExcecaoVeiculoJaCadastrado;
import excecoes.ExcecaoVeiculoNaoCadastrado;
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
            criarDadosIniciais();
            salvarDados();
            menu();
        } catch (Exception e) {
            System.out.println(e);
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
private static void criarDadosIniciais() throws ExcecaoClienteJaCadastrado, ExcecaoVeiculoJaCadastrado,
ExcecaoVeiculoNaoCadastrado, ExcecaoNaoPossuiVagasDisponiveis {
        List<Estacionamento> todosEstacionamentos = criarEstacionamentos();
        List<Cliente> clientes = criarClientes(todosEstacionamentos);
        List<Veiculo> veiculos = criarVeiculos(clientes);

        realizarUsosDeEstacionamento(clientes, todosEstacionamentos);
    }

    private static List<Estacionamento> criarEstacionamentos() {
        return List.of(
                new Estacionamento("Estacionamento A"),
                new Estacionamento("Estacionamento B"),
                new Estacionamento("Estacionamento C")
        );
    }

    private static List<Cliente> criarClientes(List<Estacionamento> estacionamentos) {
        List<Cliente> clientes = new LinkedList<>();
        clientes.add(new Cliente("Cliente1", "ID1", TipoUso.HORISTA));
        clientes.add(new Cliente("Cliente2", "ID2", TipoUso.HORISTA));
        clientes.add(new Cliente("Cliente3", "ID3", TipoUso.HORISTA));
        clientes.add(new Cliente("Cliente4", "ID4", TipoUso.HORISTA));
        clientes.add(new Cliente("Cliente5", "ID5", TipoUso.HORISTA));
        clientes.add(new Cliente("Cliente6", "ID6", TipoUso.TURNO));
        clientes.add(new Cliente("Cliente7", "ID7", TipoUso.TURNO));
        clientes.add(new Cliente("Cliente8", "ID8", TipoUso.TURNO));
        clientes.add(new Cliente("Cliente9", "ID9", TipoUso.MENSALISTA));
        clientes.add(new Cliente("Cliente10", "ID10", TipoUso.MENSALISTA));

        adicionarClientesAEstacionamentos(clientes, estacionamentos);
        return clientes;
    }

    private static void adicionarClientesAEstacionamentos(List<Cliente> clientes, List<Estacionamento> estacionamentos) {
        for (Estacionamento estacionamento : estacionamentos) {
            for (Cliente cliente : clientes) {
                estacionamento.addCliente(cliente);
            }
        }
    }

    private static List<Veiculo> criarVeiculos(List<Cliente> clientes) {
        List<Veiculo> veiculos = new LinkedList<>();
        Random random = new Random();

        for (Cliente cliente : clientes) {
            for (int i = 0; i < 5; i++) {
                String placa = "Placa" + cliente.getTipoUso().name().charAt(0) + (i + 1);
                Veiculo veiculo = new Veiculo(placa, cliente.getTipoUso(), usoDeVagaFactory);
                cliente.addVeiculo(veiculo);
                veiculos.add(veiculo);
            }
        }

        return veiculos;
    }

    private static void realizarUsosDeEstacionamento(List<Cliente> clientes, List<Estacionamento> estacionamentos) {
        Random random = new Random();
        int numUsos = 50;

        for (int i = 0; i < numUsos; i++) {
            Cliente cliente = clientes.get(random.nextInt(clientes.size()));
            Veiculo veiculo = cliente.getVeiculos().get(random.nextInt(cliente.getVeiculos().size()));
            Estacionamento estacionamento = estacionamentos.get(random.nextInt(estacionamentos.size()));
            char letraVaga = (char) ('A' + random.nextInt(3));
            int numeroVaga = 1 + random.nextInt(10);
            LocalDateTime entrada;
            LocalDateTime saida;
            CalcularUsoDeVaga calcularUsoDeVaga;

            UsoDeVaga usoDeVaga = new UsoDeVaga(new Vaga(letraVaga, numeroVaga), calcularUsoDeVaga.valorPago(entrada, saida));
            veiculo.estacionar(new Vaga(letraVaga, numeroVaga));
            usoDeVaga.setSaida(LocalDateTime.now().plusHours(random.nextInt(6)));
            veiculo.sair();
        }
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

    /**
     * Método para salvar os dados do sistema em um arquivo.
     *
     * @return 
     */
    public static void salvarDados() {
        try (ObjectOutputStream outClientes = new ObjectOutputStream(new FileOutputStream("arquivos/clientes.dat"));
                ObjectOutputStream outVeiculos = new ObjectOutputStream(new FileOutputStream("arquivos/veiculos.dat"))) {
            //dados clientes
            for (Estacionamento estacionamento : todosEstacionamentos) {
                for (Cliente cliente : estacionamento.getId().values()) {
                    outClientes.writeObject(cliente);
                }
            }
            //dados veículos
            for (Estacionamento estacionamento : todosEstacionamentos) {
                for (Cliente cliente : estacionamento.getId().values()) {
                    for (Veiculo veiculo : cliente.getVeiculos()) {
                        outVeiculos.writeObject(veiculo);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * método responsável por mostrar o menu princípal de opções para o usuário escolher seu caminho.
     * 
     * @throws ExcecaoClienteJaCadastrado exceção lançada caso um clinte já tenha sido cadastrado 
     * @throws ExcecaoVeiculoJaCadastrado excção lançada caso um Veiculo já tenha sido cadastrado
     * @throws ExcecaoVeiculoNaoCadastrado exceção lançada quando tenta encontrar um veículo que não existe no sistema
     * @throws ExcecaoNaoPossuiVagasDisponiveis exceção lançada quando se tenta estacionar um veículo porém não há vagas livres
     * @throws ExcecaoEstacionamentoNaoCadastrado exceção lançada quando se tenta fazer alguma ação em um estacionamento que não existe no sistema
     * @throws ExcecaoOpicaoInvalida exceção lançada quando uma opção inválida é selecionada.
     */
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

    /**
     * método responsável por cadastrar um novo estacionamento no sistema.
     */
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

    /**
     * Método responsável por selecionar um estacionamento para realizar ações no mesmo posteriormente através de seu nome.
     * @return um objeto Estacionamento no qual foi selecionado.
     * @throws ExcecaoEstacionamentoNaoCadastrado exceção lançada quando se tenta fazer alguma ação em um estacionamento que não existe no sistema
     */
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

    /**
     * Método responsável por mostrar ao usuário um menu de ações que podem ser realizadas em um estacionamento
     * @param estacionamento estacionamento que sofrerá as ações selecionadas pelo cliente
     */
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

    /**
     * método responsável por criar e adicionar um cliente à um estacionamento
     * @param estacionamento estacionamento que receberá o novo cliente criado.
     * @throws ExcecaoClienteJaCadastrado exceção lançada caso um clinte já tenha sido cadastrado 
     */
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

    /**
     * método responsável por criar e adicionar um veículo à um estacionamento especifico.
     * @param estacionamento estacionamento que irá receber o novo veículo criado.
     * @throws ExcecaoVeiculoJaCadastrado excção lançada caso um Veiculo já tenha sido cadastrado
     * @throws ExcecaoOpicaoInvalida exceção lançada quando uma opção inválida é selecionada.
     */
    public static void addVeiculo(Estacionamento estacionamento) throws ExcecaoVeiculoJaCadastrado, ExcecaoOpicaoInvalida {

        System.out.println("Digite o id do cliente no qual deseja cadastrar o veiculo: ");
        String idCli = teclado.nextLine();
        System.out.println("Digite a placa do veículo: ");
        String placa = teclado.nextLine();
        System.out.println("Digite o tipo de uso (HORISTA, MENSALISTA OU TURNO): ");
        TipoUso tipoUso = TipoUso.valueOf(teclado.nextLine().toUpperCase());

        Cliente cliente = estacionamento.possuiCliente(idCli);

        UsoDeVagaFactory usoDeVagaFactory;

        if (cliente.getTipoUso() != tipoUso) {
            throw new ExcecaoOpicaoInvalida("Tipo de uso do veiculo diferente do cliente cadastrado.");
        }
        TipoTurno tipoTurno= null;

        switch (tipoUso) {
            case HORISTA:
                usoDeVagaFactory = UsoDeVagaFactory.criarHoristaFactory();
                break;
            case MENSALISTA:
                usoDeVagaFactory = UsoDeVagaFactory.criarMensalistaFactory();
                break;
            case TURNO:
                tipoTurno = selecionarTurno();
                usoDeVagaFactory = UsoDeVagaFactory.criarTurnoFactory();
                break;
            default:
                throw new IllegalArgumentException("Tipo de uso inválido");
        }
            //! add veiculo tem que ser atualiado para poder aceitar os tipoTurno caso não seja Turnista
            estacionamento.addVeiculo(placa, idCli, tipoUso, usoDeVagaFactory, tipoTurno);

    }

    /**
     * Método responsável por adicionar um veículo a partir de sua placa à um estacionamento
     * @param estacionamento estacionamento no qual irá receber a placa de um veículo para estacionar
     * @throws ExcecaoNaoPossuiVagasDisponiveis exceção lançada quando se tenta estacionar um veículo porém não há vagas livres
     */
    public static void estacionar(Estacionamento estacionamento) throws ExcecaoNaoPossuiVagasDisponiveis, ExcecaoOpicaoInvalida  {
        System.out.println("Digite a placa do veiculo: ");
        String placa = teclado.nextLine();
        System.out.println("Veiculo estacionado com sucesso!");
        TipoServico servico = selecionarServico();
        //! estacionar precisa ser atualizar para poder aceitar o servicos prestados pelo estacionamento
        estacionamento.estacionar(placa,servico);
    }

    /**
     * Este método solicita ao usuário a seleção de um tipo de serviço e retorna o TipoServico correspondente.
     * Os tipos de serviço disponíveis são MANOBRISTA, LAVAGEM, POLIMENTO ou NENHUM.
     *
     * @return O TipoServico selecionado pelo usuário.
     * @throws ExcecaoOpicaoInvalida Se o usuário inserir uma opção inválida.
     */
    public static TipoServico selecionarServico() throws ExcecaoOpicaoInvalida{
        System.out.println("Selecione o serviço que deseja utiliar: ");
        System.out.println("1: MANOBRISTA 5$");
        System.out.println("2: LAVAGEM 20$");
        System.out.println("3: POLIMENTO 25$");
        System.out.println("4: NENHUM");
        int tipoServicoInt = Integer.parseInt(teclado.nextLine());

        switch (tipoServicoInt) {
            case 1:
                return TipoServico.MANOBRISTA;

            case 2:
                return TipoServico.LAVAGEM;

            case 3:
                return TipoServico.POLIMENTO;

            case 4: 
                return null;
        
            default:
                throw new ExcecaoOpicaoInvalida("ERRO: servico digitado invalido");
        }
    }

    /**
     * método para retirar um carro de um estacionamento a partir da placa do carro
     * @param estacionamento estacionamento no qual o carro está e será retirado
     */
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

    public static TipoTurno selecionarTurno() throws ExcecaoOpicaoInvalida{
        System.out.println("Digite o turno:");
        System.out.println("MANHA, TARDE, NOITE");
        String turno = teclado.nextLine().toUpperCase();
        if( TipoTurno.valueOf(turno) != null){
                    return TipoTurno.valueOf(turno);
        }else{
            throw new ExcecaoOpicaoInvalida("Turno digitado inválido");
        }
    }


    /**
     * Método para mostrar ao usuário o total arrecado por um estacionamento até o momento
     * @param estacionamento estacionamento que será analisado o total de arrecadação.
     */
    public static void totalArrecadado(Estacionamento estacionamento) {
        System.out.println("Valor total: " + estacionamento.totalArrecadado());
    }

    /**
     * Método para mostrar ao usuário o total arrecado por um estacionamento em um determinado mês informado pelo usuário
     * @param estacionamento estacionamento que será analisado o total de arrecadação.
     */
    public static void arrecadadoNoMes(Estacionamento estacionamento) {
        
        System.out.println("Digite o ano desejado: (aaaa)");
        int ano = Integer.parseInt(teclado.nextLine());
        System.out.println("Digite o mes desejado: (mm)");
        int mes = Integer.parseInt(teclado.nextLine());

        System.out.println("| Mês " + mes + " | Ano " + ano + " | " +  "-  Valor arrecadado:  " + estacionamento.arrecadacaoNoMes(mes, ano) + " |");

    }


    /**
     * Método responsável por mostrar ao usuário uma média feita com o total arrecadado em um Mês com a quantidade
     * de vagas que foram utilizadas nesse mês. O mês é informado pelo usuário no decorrer desse método
     * @param estacionamento estacionamento que será analisado o total de arrecadação e a quantidade de usos de vaga.
     */
    public static void valorMedioUso(Estacionamento estacionamento) {
        //! Implementar todos os métodos necessários

        System.out.println("Digite o ano desejado: (aaaa)");
        int ano = Integer.parseInt(teclado.nextLine());
        System.out.println("Digite o mes desejado: (mm)");
        int mes = Integer.parseInt(teclado.nextLine());

        double arrecadacao = estacionamento.arrecadacaoNoMes(mes, ano);

        int quantidadeUso = estacionamento.totalDeUsoNoMesAnoEstacionamento(mes, ano);

        System.out.println("Valor medio uso: " + (arrecadacao / quantidadeUso));
    }


    /**
     * método responsável por buscar os 5 clientes com mais usos no estacionamento em um determinado mês.
     * @param estacionamento estacionamento no qual será buscado os 5 clientes com mais usos em um determinado mês.
     */
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
        int ano = LocalDate.now().getYear();
        
        System.out.println("Digite o mes de arrecadação:");

        int mes = Integer.parseInt(teclado.nextLine());

        List<Estacionamento> estacionametosOrdenados = new ArrayList<>();

        for (int i = 0; i > todosEstacionamentos.size(); i++) {
            estacionametosOrdenados.add(todosEstacionamentos.get(i));
        }

        Comparator<Estacionamento> c = (e1, e2) -> {
            return Double.compare(e1.arrecadacaoNoMes(mes, ano), e2.arrecadacaoNoMes(mes, ano));
        };

        estacionametosOrdenados.sort(c);

        for (int i = estacionametosOrdenados.size() - 1; i >= 0; i--) {
            Estacionamento estacionamento = estacionametosOrdenados.get(i);
            System.out.println(i + "- " + estacionamento.getNome() + ": " + estacionamento.arrecadacaoNoMes(mes, ano));
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

    /**
     * Método responsável por mostrar ao usuário a arrecadação média do estacionamento em um mês por clientes
     * que são horistas
     * @param estacionamento estacionamento que será analisado o total de arrecadação por clientes horistas.
     * @throws ExcecaoNenhumClienteCadastrado excecao lançada caso não haja nenhum cliente cadastrado pois iria resultar em uma
     * divisão com zero no denominador quando a média fosse calculada.
     */
    public static void arrecadacaoMediaClientesHoristasNoMesCorrente(Estacionamento estacionamento)
            throws ExcecaoNenhumClienteCadastrado {

        double resultado = estacionamento.arrecadacaoClientesHoristas();

        System.out.println(
                "O valor médio Arrecadado pelos clientes horistas do estacionamento \"" + estacionamento.getNome()
                        + "\" no mês é: " + resultado);
    }

    /**
     * método responsável por ler do usuário uma quantidade de vagas e adicionar estas vagas a um estacionamento especificado
     * @param estacionamento estacionamento que irá receber as novas espaços de vagas que forem geradas.
     */
    public static void gerarVagas(Estacionamento estacionamento) {
        int vagas;
        System.out.println("Digite o numero de vagas os qual deseja gerar?");
        vagas = Integer.parseInt(teclado.nextLine());
        estacionamento.gerarVagas(vagas);
        ;
    }

    /**
     * Método responsável por mostrar ao cliente um Menu de opções relacionadas a funções de relatório de um veículo específico.
     * @param estacionamento estacionamento no qual será buscado o veículo com a placa informada pelo usuário.
     * @throws ExcecaoOpicaoInvalida exceção lançada caso o usuário selecione uma opção que não seja válida.
     */
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

    /**
     * Método responsável por buscar o histórico de um cliente especificado pelo seu ID e mostrar para o usuário no console do sistema
     * @param estacionamento estacionamento no qual irá buscar pelo cliente especificado
     * @throws ExcecaoOpicaoInvalida exceção lançada caso o usuário selecione uma opção que não seja válida.
     * @throws ExcecaoClienteNaoCadastrado exceção lançada no caso do cliente buscado não existir.
     */
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
                dataInicio = "01/01/1990 00:00:00";
                dataFim = "30/12/2100 00:00:00";
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

    /**
     * Método responsável por alterar o tipo de um cliente que pode ser HORISTA, MENSALISTA ou TURNO
     * @param estacionamento estacionamento no qual será buscado o cliente informado pelo seu ID para que seu tipo seja alterado.
     */
    public static void mudarTipoUsoCliente(Estacionamento estacionamento){
        String id;
        System.out.println("Digite o id do cliente: ");
        id = teclado.nextLine();
        System.out.println("Digite o tipo de uso (HORISTA, MENSALISTA OU TURNO): ");
        TipoUso tipoUso = TipoUso.valueOf(teclado.nextLine().toUpperCase());
        estacionamento.alteraTipoUsoCliente(tipoUso, id);
    }



}