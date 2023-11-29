package estacionamentos;

import estacionamentos.interfaces.UsoDeVagaFactory;

public class UsoDeVagaHoristaFactory implements UsoDeVagaFactory {
    @Override
    /**
     * Cria um uso de vaga para a vaga passada como parâmetro.
     * Nesse caso, é uma vaga de Horista
     */
    public UsoDeVaga criarUsoDeVaga(Vaga vaga) {
        return new UsoDeVaga(vaga, new UsoDeVagaHorista());
    }
    
}