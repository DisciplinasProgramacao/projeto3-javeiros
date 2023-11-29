package estacionamentos.interfaces;

import estacionamentos.UsoDeVaga;
import estacionamentos.Vaga;

public interface UsoDeVagaFactory {

    public UsoDeVaga criarUsoDeVaga(Vaga vaga);
    
}
