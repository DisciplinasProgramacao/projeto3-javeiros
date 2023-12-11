package estacionamentos.interfaces;

import estacionamentos.Enums.TipoUso;

public interface Observer {
    public void update(TipoUso tipoUso);
}
