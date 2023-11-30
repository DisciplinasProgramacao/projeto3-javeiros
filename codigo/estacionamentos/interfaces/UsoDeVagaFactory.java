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

    // Método estático para criar instâncias da fábrica
    static UsoDeVagaFactory criarMensalistaFactory() {
        return new UsoDeVagaMensalistaFactory();
    }

    // Método estático para criar instâncias da fábrica
    static UsoDeVagaFactory criarHoristaFactory() {
        return new UsoDeVagaHoristaFactory();
    }
    
    static UsoDeVagaFactory criarTurnoFactory() {
        return new UsoDeVagaTurnoFactory();
    }
    
}
