package estacionamentos.interfaces;

import estacionamentos.UsoDeVaga;
import estacionamentos.Vaga;

public interface UsoDeVagaFactory {

    /**
     * Cria um uso de vaga para a vaga passada como parâmetro.
     * @param vaga Vaga para a qual o uso de vaga será criado.
     * @return Uso de vaga criado.
     */
    public UsoDeVaga criarUsoDeVaga(Vaga vaga);
    
}
