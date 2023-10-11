package estacionamento;

public class UsoDeVaga {

	public UsoDeVaga(Vaga vaga) {
		// TODO Auto-generated constructor stub
	}

	public UsoDeVaga() {
		// TODO Auto-generated constructor stub
	}

	public boolean estaDentroDoMes(int mes) {
		// TODO Auto-generated method stub
		return false;
	}

	public double valorPago() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double sair() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public double servico(TipoServico servico, int tempo) {
		double totalDoServico = 0;
		
		if( servico == TipoServico.MANOBRISTA && tempo >= servico.getTempo() ) {
			totalDoServico = servico.getValor();
		}else if( servico == TipoServico.LAVAGEM && tempo > 1 ) {
			totalDoServico = servico.getValor();
		} else if( servico == TipoServico.POLIMENTO && tempo > 2) {
			totalDoServico = servico.getValor();
		}
		
		return totalDoServico;
	}

}
