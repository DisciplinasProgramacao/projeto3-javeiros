package estacionamentos;

import estacionamentos.interfaces.UsoDeVagaFactory;

public class UsoDeVagaTurnoFactory implements UsoDeVagaFactory {

    @Override
    public UsoDeVaga criarUsoDeVaga(Vaga vaga) {
        return new UsoDeVaga(vaga, new UsoDeVagaTurno());
    }

}