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
import estacionamentos.Enums.TipoServico;
import estacionamentos.Enums.TipoTurno;
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

        cliente1.addVeiculo(veiculo1);

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
        // Uso de Vaga 1 - Javeiros
        UsoDeVaga uso1Veiculo1 = new UsoDeVaga(new Vaga('A', 1), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 1));
        uso1Veiculo1.setSaida(LocalDateTime.now().plusHours(2));
        veiculo1.sair();

        // Uso de Vaga 2 - Javeiros 
        UsoDeVaga uso2Veiculo1 = new UsoDeVaga(new Vaga('A', 2), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 2));
        uso2Veiculo1.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(30));
        veiculo1.sair();

        // Uso de Vaga 3 - Javeiros
        UsoDeVaga uso1Veiculo6 = new UsoDeVaga(new Vaga('B', 1), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 1));
        uso1Veiculo6.setSaida(LocalDateTime.now().plusDays(1));
        veiculo6.sair();

        // Uso de Vaga 4 - Javeiros
        UsoDeVaga uso1Veiculo11 = new UsoDeVaga(new Vaga('C', 1), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 1));
        uso1Veiculo11.setSaida(LocalDateTime.now().plusHours(4));
        veiculo11.sair();

        // Uso de Vaga 5 - Javeiros
        UsoDeVaga uso2Veiculo6 = new UsoDeVaga(new Vaga('B', 2), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 2));
        uso2Veiculo6.setSaida(LocalDateTime.now().plusDays(2));
        veiculo6.sair();

        // Uso de Vaga 6 - Javeiros
        UsoDeVaga uso2Veiculo11 = new UsoDeVaga(new Vaga('C', 2), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 2));
        uso2Veiculo11.setSaida(LocalDateTime.now().plusHours(3));
        veiculo11.sair();

        // Uso de Vaga 7 - Javeiros
        UsoDeVaga uso3Veiculo1 = new UsoDeVaga(new Vaga('A', 3), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 3));
        uso3Veiculo1.setSaida(LocalDateTime.now().plusHours(2).plusMinutes(15));
        veiculo1.sair();

        // Uso de Vaga 8 - Javeiros
        UsoDeVaga uso3Veiculo6 = new UsoDeVaga(new Vaga('B', 3), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 3));
        uso3Veiculo6.setSaida(LocalDateTime.now().plusDays(1).plusHours(5));
        veiculo6.sair();

        // Uso de Vaga 9 - Javeiros
        UsoDeVaga uso3Veiculo11 = new UsoDeVaga(new Vaga('C', 3), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 3));
        uso3Veiculo11.setSaida(LocalDateTime.now().plusHours(6));
        veiculo11.sair();

        // Uso de Vaga 10 - Javeiros
        UsoDeVaga uso4Veiculo1 = new UsoDeVaga(new Vaga('A', 4), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 4));
        uso4Veiculo1.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(45));
        veiculo1.sair();

        // Uso de Vaga 11 - Javeiros
        UsoDeVaga uso1Veiculo2 = new UsoDeVaga(new Vaga('A', 5), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 5));
        uso1Veiculo2.setSaida(LocalDateTime.now().plusHours(3));
        veiculo2.sair();

        // Uso de Vaga 12 - Javeiros
        UsoDeVaga uso2Veiculo2 = new UsoDeVaga(new Vaga('A', 6), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 6));
        uso2Veiculo2.setSaida(LocalDateTime.now().plusHours(2).plusMinutes(30));
        veiculo2.sair();

        // Uso de Vaga 13 - Javeiros
        UsoDeVaga uso4Veiculo6 = new UsoDeVaga(new Vaga('B', 4), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 4));
        uso4Veiculo6.setSaida(LocalDateTime.now().plusDays(3));
        veiculo6.sair();

        // Uso de Vaga 14 - Javeiros
        UsoDeVaga uso4Veiculo11 = new UsoDeVaga(new Vaga('C', 4), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 4));
        uso4Veiculo11.setSaida(LocalDateTime.now().plusHours(5));
        veiculo11.sair();

        // Uso de Vaga 15 - Javeiros
        UsoDeVaga uso5Veiculo1 = new UsoDeVaga(new Vaga('A', 7), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 7));
        uso5Veiculo1.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(15));
        veiculo1.sair();

        // Uso de Vaga 16 - Javeiros
        UsoDeVaga uso5Veiculo6 = new UsoDeVaga(new Vaga('B', 5), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 5));
        uso5Veiculo6.setSaida(LocalDateTime.now().plusDays(1).plusHours(4));
        veiculo6.sair();

        // Uso de Vaga 17 - Javeiros
        UsoDeVaga uso5Veiculo11 = new UsoDeVaga(new Vaga('C', 5), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 5));
        uso5Veiculo11.setSaida(LocalDateTime.now().plusHours(4));
        veiculo11.sair();

        // Uso de Vaga 18 - Javeiros
        UsoDeVaga uso3Veiculo2 = new UsoDeVaga(new Vaga('A', 8), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 8));
        uso3Veiculo2.setSaida(LocalDateTime.now().plusHours(2));
        veiculo2.sair();

        // Uso de Vaga 19 - Javeiros
        UsoDeVaga uso6Veiculo1 = new UsoDeVaga(new Vaga('A', 9), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 9));
        uso6Veiculo1.setSaida(LocalDateTime.now().plusHours(3).plusMinutes(30));
        veiculo1.sair();

        // Uso de Vaga 20 - Javeiros
        UsoDeVaga uso6Veiculo6 = new UsoDeVaga(new Vaga('B', 6), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 6));
        uso6Veiculo6.setSaida(LocalDateTime.now().plusDays(2));
        veiculo6.sair();

        // Uso de Vaga 21 - Javeiros
        UsoDeVaga uso6Veiculo11 = new UsoDeVaga(new Vaga('C', 6), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 6));
        uso6Veiculo11.setSaida(LocalDateTime.now().plusHours(3));
        veiculo11.sair();

        // Uso de Vaga 22 - Javeiros
        UsoDeVaga uso4Veiculo2 = new UsoDeVaga(new Vaga('A', 10), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 10));
        uso4Veiculo2.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(45));
        veiculo2.sair();

        // Uso de Vaga 23 - Javeiros
        UsoDeVaga uso7Veiculo1 = new UsoDeVaga(new Vaga('A', 11), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 11));
        uso7Veiculo1.setSaida(LocalDateTime.now().plusHours(2));
        veiculo1.sair();

        // Uso de Vaga 24 - Javeiros
        UsoDeVaga uso7Veiculo6 = new UsoDeVaga(new Vaga('B', 7), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 7));
        uso7Veiculo6.setSaida(LocalDateTime.now().plusDays(1));
        veiculo6.sair();

        // Uso de Vaga 25 - Javeiros
        UsoDeVaga uso7Veiculo11 = new UsoDeVaga(new Vaga('C', 7), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 7));
        uso7Veiculo11.setSaida(LocalDateTime.now().plusHours(4));
        veiculo11.sair();

        // Uso de Vaga 26 - Javeiros
        UsoDeVaga uso5Veiculo2 = new UsoDeVaga(new Vaga('A', 12), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 12));
        uso5Veiculo2.setSaida(LocalDateTime.now().plusHours(3));
        veiculo2.sair();

        // Uso de Vaga 27 - Javeiros
        UsoDeVaga uso8Veiculo1 = new UsoDeVaga(new Vaga('A', 13), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 13));
        uso8Veiculo1.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(30));
        veiculo1.sair();

        // Uso de Vaga 28 - Javeiros
        UsoDeVaga uso8Veiculo6 = new UsoDeVaga(new Vaga('B', 8), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 8));
        uso8Veiculo6.setSaida(LocalDateTime.now().plusDays(2).plusHours(5));
        veiculo6.sair();

        // Uso de Vaga 29 - Javeiros
        UsoDeVaga uso8Veiculo11 = new UsoDeVaga(new Vaga('C', 8), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 8));
        uso8Veiculo11.setSaida(LocalDateTime.now().plusHours(6));
        veiculo11.sair();

        // Uso de Vaga 30 - Javeiros
        UsoDeVaga uso6Veiculo2 = new UsoDeVaga(new Vaga('A', 14), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 14));
        uso6Veiculo2.setSaida(LocalDateTime.now().plusHours(2).plusMinutes(15));
        veiculo2.sair();

        // Uso de Vaga 31 - Javeiros
        UsoDeVaga uso9Veiculo1 = new UsoDeVaga(new Vaga('A', 15), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 15));
        uso9Veiculo1.setSaida(LocalDateTime.now().plusHours(3));
        veiculo1.sair();

        // Uso de Vaga 32 - Javeiros
        UsoDeVaga uso9Veiculo6 = new UsoDeVaga(new Vaga('B', 9), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 9));
        uso9Veiculo6.setSaida(LocalDateTime.now().plusDays(1).plusHours(4));
        veiculo6.sair();

        // Uso de Vaga 33 - Javeiros
        UsoDeVaga uso9Veiculo11 = new UsoDeVaga(new Vaga('C', 9), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 9));
        uso9Veiculo11.setSaida(LocalDateTime.now().plusHours(5));
        veiculo11.sair();

        // Uso de Vaga 34 - Javeiros
        UsoDeVaga uso7Veiculo2 = new UsoDeVaga(new Vaga('A', 16), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 16));
        uso7Veiculo2.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(45));
        veiculo2.sair();

        // Uso de Vaga 35 - Javeiros
        UsoDeVaga uso10Veiculo1 = new UsoDeVaga(new Vaga('A', 17), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 17));
        uso10Veiculo1.setSaida(LocalDateTime.now().plusHours(2));
        veiculo1.sair();

        // Uso de Vaga 36 - Javeiros
        UsoDeVaga uso10Veiculo6 = new UsoDeVaga(new Vaga('B', 10), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 10));
        uso10Veiculo6.setSaida(LocalDateTime.now().plusDays(2));
        veiculo6.sair();

        // Uso de Vaga 37 - Javeiros
        UsoDeVaga uso10Veiculo11 = new UsoDeVaga(new Vaga('C', 10), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 10));
        uso10Veiculo11.setSaida(LocalDateTime.now().plusHours(4));
        veiculo11.sair();

        // Uso de Vaga 38 - Javeiros
        UsoDeVaga uso8Veiculo2 = new UsoDeVaga(new Vaga('A', 18), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 18));
        uso8Veiculo2.setSaida(LocalDateTime.now().plusHours(3));
        veiculo2.sair();

        // Uso de Vaga 39 - Javeiros
        UsoDeVaga uso11Veiculo1 = new UsoDeVaga(new Vaga('A', 19), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 19));
        uso11Veiculo1.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(30));
        veiculo1.sair();

        // Uso de Vaga 40 - Javeiros
        UsoDeVaga uso11Veiculo6 = new UsoDeVaga(new Vaga('B', 11), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 11));
        uso11Veiculo6.setSaida(LocalDateTime.now().plusDays(1).plusHours(5));
        veiculo6.sair();

        // Uso de Vaga 41 - Javeiros
        UsoDeVaga uso11Veiculo11 = new UsoDeVaga(new Vaga('C', 11), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 11));
        uso11Veiculo11.setSaida(LocalDateTime.now().plusHours(6));
        veiculo11.sair();

        // Uso de Vaga 42 - Javeiros
        UsoDeVaga uso9Veiculo2 = new UsoDeVaga(new Vaga('A', 20), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 20));
        uso9Veiculo2.setSaida(LocalDateTime.now().plusHours(2).plusMinutes(15));
        veiculo2.sair();

        // Uso de Vaga 43 - Javeiros
        UsoDeVaga uso12Veiculo1 = new UsoDeVaga(new Vaga('A', 21), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 21));
        uso12Veiculo1.setSaida(LocalDateTime.now().plusHours(3));
        veiculo1.sair();

        // Uso de Vaga 44 - Javeiros
        UsoDeVaga uso12Veiculo6 = new UsoDeVaga(new Vaga('B', 12), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 12));
        uso12Veiculo6.setSaida(LocalDateTime.now().plusDays(2).plusHours(4));
        veiculo6.sair();

        // Uso de Vaga 45 - Javeiros
        UsoDeVaga uso12Veiculo11 = new UsoDeVaga(new Vaga('C', 12), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 12));
        uso12Veiculo11.setSaida(LocalDateTime.now().plusHours(5));
        veiculo11.sair();

        // Uso de Vaga 46 - Javeiros
        UsoDeVaga uso10Veiculo2 = new UsoDeVaga(new Vaga('A', 22), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 22));
        uso10Veiculo2.setSaida(LocalDateTime.now().plusHours(1).plusMinutes(45));
        veiculo2.sair();

        // Uso de Vaga 47 - Javeiros
        UsoDeVaga uso13Veiculo1 = new UsoDeVaga(new Vaga('A', 23), new UsoDeVagaHorista());
        veiculo1.estacionar(new Vaga('A', 23));
        uso13Veiculo1.setSaida(LocalDateTime.now().plusHours(2));
        veiculo1.sair();

        // Uso de Vaga 48 - Javeiros
        UsoDeVaga uso13Veiculo6 = new UsoDeVaga(new Vaga('B', 13), new UsoDeVagaMensalista());
        veiculo6.estacionar(new Vaga('B', 13));
        uso13Veiculo6.setSaida(LocalDateTime.now().plusDays(1));
        veiculo6.sair();

        // Uso de Vaga 49 - Javeiros
        UsoDeVaga uso13Veiculo11 = new UsoDeVaga(new Vaga('C', 13), new UsoDeVagaTurno());
        veiculo11.estacionar(new Vaga('C', 13));
        uso13Veiculo11.setSaida(LocalDateTime.now().plusHours(4));
        veiculo11.sair();

        // Uso de Vaga 50 - Javeiros
        UsoDeVaga uso11Veiculo2 = new UsoDeVaga(new Vaga('A', 24), new UsoDeVagaHorista());
        veiculo2.estacionar(new Vaga('A', 24));
        uso11Veiculo2.setSaida(LocalDateTime.now().plusHours(3));
        veiculo2.sair();

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