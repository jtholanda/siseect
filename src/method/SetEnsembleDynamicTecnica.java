package method;

import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import data.Padrao;


public abstract class SetEnsembleDynamicTecnica extends Tecnica {

	


	public void run(Padrao padrao, Integer tipoClassificador) {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void run(Padrao padrao, Integer tipoClassificador1,
			Integer tipoClassificador2, Integer tipoClassificador3,
			Integer tipoClassificador4, Integer tipoClassificador5,
			Integer tipoClassificador6, Integer tipoClassificador7,TipoMetodoCombinacao tipoSelecaoDinamica);
	
	public abstract void run(Padrao padrao, Integer tipoClassificador1,
			Integer tipoClassificador2, Integer tipoClassificador3,
			Integer tipoClassificador4, Integer tipoClassificador5,
			Integer tipoClassificador6, Integer tipoClassificador7,
			TipoMetodoCombinacao tipoSelecaoDinamica, TipoValidacao tipoValidacao);

	public void run(Padrao padrao, TipoMetodoCombinacao tipoSelecaoDinamica,
			TipoValidacao tipoValidacao) {
		// TODO Auto-generated method stub
		
	}

	public void run(Padrao padrao, TipoMetodoCombinacao tipoSelecaoDinamica) {
		// TODO Auto-generated method stub
		
	}

	public void run(Padrao padrao, Integer tipoClassificador1,
			Integer tipoClassificador2, Integer tipoClassificador3,
			Integer tipoClassificador4, Integer tipoClassificador5,
			Integer tipoClassificador6, Integer tipoClassificador7,
			Integer tipoClassificador8, Integer tipoClassificador9,
			Integer tipoClassificador10,
			TipoMetodoCombinacao tipoSelecaoDinamica,
			TipoValidacao tipoValidacao) {
		// TODO Auto-generated method stub
		
	}

	public void run(Padrao padrao, Integer tipoClassificador1,
			Integer tipoClassificador2, Integer tipoClassificador3,
			Integer tipoClassificador4, Integer tipoClassificador5,
			Integer tipoClassificador6, Integer tipoClassificador7,
			Integer tipoClassificador8, Integer tipoClassificador9,
			Integer tipoClassificador10,
			TipoMetodoCombinacao tipoSelecaoDinamica) {
		// TODO Auto-generated method stub
		
	}

	/*
	protected double calcularValorParaAll04(List<Double> valoresEstimados) {
		
		Collections.sort(valoresEstimados);
		double primeiroValor = valoresEstimados.get(0);
		if(primeiroValor<1){primeiroValor=1;};
		double segundoValor = valoresEstimados.get(1);
		if(segundoValor<1){segundoValor=1.0;};
		double terceiroValor = valoresEstimados.get(2);
		if(terceiroValor<1){terceiroValor=1;};
		double quartoValor = valoresEstimados.get(3);
		if(quartoValor<1){quartoValor=1;};
		double quintoValor = valoresEstimados.get(4);
		if(quintoValor<1){quintoValor=1;};
		double sextoValor = valoresEstimados.get(5);
		if(sextoValor<1){sextoValor=1;};
		double setimoValor = valoresEstimados.get(6);
		if(setimoValor<1){setimoValor=1;};
		
		double calibre = (primeiroValor/segundoValor*segundoValor/terceiroValor*terceiroValor/quartoValor*quartoValor/quintoValor*quintoValor/sextoValor*sextoValor/setimoValor);
		if(calibre==0){
			calibre = 1;
		}
		return calibre*quartoValor; 
		
	}

	protected double calcularValorParaAll05(List<Double> valoresEstimados) {
		
		Collections.sort(valoresEstimados);
		double primeiroValor = valoresEstimados.get(0);
		double segundoValor = valoresEstimados.get(1);
		double terceiroValor = valoresEstimados.get(2);
		double quartoValor = valoresEstimados.get(3);
		double quintoValor = valoresEstimados.get(4);
		double sextoValor = valoresEstimados.get(5);
		double setimoValor = valoresEstimados.get(6);
		
		
		double calibre = (primeiroValor/segundoValor*segundoValor/terceiroValor*terceiroValor/quartoValor*quartoValor/quintoValor*quintoValor/sextoValor*sextoValor/setimoValor);
		return calibre*quartoValor; 
		
	}
	*/
}
