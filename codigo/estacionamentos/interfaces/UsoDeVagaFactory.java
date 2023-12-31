package estacionamentos.interfaces;

import estacionamentos.UsoDeVaga;
import estacionamentos.UsoDeVagaHoristaFactory;
import estacionamentos.UsoDeVagaMensalistaFactory;
import estacionamentos.UsoDeVagaTurnoFactory;
import estacionamentos.Vaga;

public interface UsoDeVagaFactory {

    /**
     * Cria um uso de vaga para a vaga passada como parâmetro.
     * @param vaga Vaga para a qual o uso de vaga será criado.
     * @return Uso de vaga criado.
     */
    public UsoDeVaga criarUsoDeVaga(Vaga vaga);

    /**
     * Método estático para criar instâncias da fábrica de Vagas para mensalistas
     * @return uma instância da fabrica de uso de vaga para mensalista
     */
    static UsoDeVagaFactory criarMensalistaFactory() {
        return new UsoDeVagaMensalistaFactory();
    }

    /**
     * Método estático para criar instâncias da fábrica de Vagas para Horistas
     * @return uma instância da fabrica de uso de vaga para horista
     */
    static UsoDeVagaFactory criarHoristaFactory() {
        return new UsoDeVagaHoristaFactory();
    }
    
    /**
     * Método estático para criar instâncias da fábrica de Vagas para Turnos
     * @return uma instância da fabrica de uso de vaga para turnos
     */
    static UsoDeVagaFactory criarTurnoFactory() {
        return new UsoDeVagaTurnoFactory();
    }
    
}
