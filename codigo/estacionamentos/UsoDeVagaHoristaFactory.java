package estacionamentos;

import estacionamentos.interfaces.UsoDeVagaFactory;

public class UsoDeVagaHoristaFactory implements UsoDeVagaFactory {

    @Override
    public UsoDeVaga criarUsoDeVaga(Vaga vaga) {
        return new UsoDeVaga(vaga, new UsoDeVagaHorista());
    }
    
}