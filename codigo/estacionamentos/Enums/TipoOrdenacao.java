package estacionamentos.Enums;

public enum TipoOrdenacao {

    DATA("porDataDecrescente"),
    VALOR("porValorCrescente");

    public static TipoOrdenacao getData() {
        return DATA;
    }

    public static TipoOrdenacao getValor() {
        return VALOR;
    }

    TipoOrdenacao(String porValorCrescente) {
    }
}
