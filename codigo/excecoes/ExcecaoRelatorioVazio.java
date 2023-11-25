package excecoes;

public class ExcecaoRelatorioVazio extends RuntimeException {
    public ExcecaoRelatorioVazio() {
        super("Não há registros de uso para gerar o relatório.");
    }
}
