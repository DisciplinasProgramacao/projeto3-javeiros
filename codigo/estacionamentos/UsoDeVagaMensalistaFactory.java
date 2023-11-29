package estacionamentos;

import estacionamentos.interfaces.UsoDeVagaFactory;

public class UsoDeVagaMensalistaFactory implements UsoDeVagaFactory {

    @Override
    public UsoDeVaga criarUsoDeVaga(Vaga vaga) {
        return new UsoDeVaga(vaga, new UsoDeVagaMensalista());
    }
    
}