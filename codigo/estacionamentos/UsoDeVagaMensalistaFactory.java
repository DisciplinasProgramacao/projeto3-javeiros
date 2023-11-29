package estacionamentos;

import estacionamentos.interfaces.UsoDeVagaFactory;

public class UsoDeVagaMensalistaFactory implements UsoDeVagaFactory {

    @Override
    /**
     * Cria um uso de vaga para a vaga passada como parâmetro.
     * Nesse caso, é uma vaga de Mensalista
     */
    public UsoDeVaga criarUsoDeVaga(Vaga vaga) {
        return new UsoDeVaga(vaga, new UsoDeVagaMensalista());
    }
    
}