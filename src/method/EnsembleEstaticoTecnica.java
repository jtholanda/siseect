package method;

import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import data.Padrao;

public abstract class EnsembleEstaticoTecnica extends Tecnica {

	
	

	@Override
	public void run(Padrao padrao) {

	}
	public abstract void run(Padrao padrao, TipoMetodoCombinacao ensembleEstatico);
	public abstract void run(Padrao padrao, TipoMetodoCombinacao ensembleEstatico,
			TipoValidacao tipoValidacao);

	
}
