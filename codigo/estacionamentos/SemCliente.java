package estacionamentos;

import estacionamentos.Enums.TipoUso;

public class SemCliente extends Cliente{

    public SemCliente(String nome, String id) {
        super(nome, id);
    }

    public SemCliente(String nome, String id, TipoUso tipoUso) {
        super(nome, id, tipoUso);
    }

    @Override
    public String getNome(){
        return "Sem Cliente";
    }

    public SemCliente(){};

    @Override
    public String toString(){
        return "Não possui cliente nessa classificação";
    }

}
