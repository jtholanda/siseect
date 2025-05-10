package method;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import main.TesteKnoraDCSSetEnsembleDynamic;
import model.Resultado;
import model.ResultadoIdeal;
import util.Constantes;
import util.GeradorAleatorioDados;
import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import util.Utilidade;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.LinearNNSearch;
import data.Padrao;

public class Knora_DCS_Set_Ensemble_Dynamic extends SetEnsembleDynamicTecnica {
	
	private Classifier regressor1;
	private Classifier regressor2;
	private Classifier regressor3;
	
	public static int K_SELECAO_DINAMICA;
	public static double KNORA;

	public Knora_DCS_Set_Ensemble_Dynamic() {
	}

	public Knora_DCS_Set_Ensemble_Dynamic(Classifier regressor1, Classifier regressor2, Classifier regressor3) {
		this.regressor1 = regressor1;
		this.regressor2 = regressor2;
		this.regressor3 = regressor3;
	}

	private void avaliarMetodo(Padrao padrao, Resultado resultado1, Resultado resultado2,
			Resultado resultado3, TipoMetodoCombinacao tipoSelecaoDinamica, TipoValidacao tipoValidacao, int i) throws Exception{
		
		// define a configuração dos dados que serão avaliados
		defineAvaliacao(padrao, tipoValidacao, i);

		// calcula as estimativas combinadas utilizando os resultados de KNN e os dados de classificação para o melhor algoritimo 
		calcularEstimativas(padrao, resultado1, resultado2, resultado3, tipoSelecaoDinamica, tipoValidacao, i);
	
		// adiciona para cada métrica usada as médias de erros
		calcularResultadosGerais();

	}



	private void calcularEstimativas(Padrao padrao, Resultado resultado1,
			Resultado resultado2, Resultado resultado3, TipoMetodoCombinacao tipoSelecaoDinamica, TipoValidacao tipoValidacao, int i)
			throws Exception {

		
		double valorEstimado = 0, valorEstimadoComparativo = 0;
		double valorReal = 0;
		Instance instancia;
		
		resultado = new Resultado();
		resultado.setNomeModelo(toString() + " - " + padrao.toString());
			
		//obtem o modelo 1 criado
		ObjectInputStream objectInputStream1 = new ObjectInputStream(new FileInputStream(resultado1.getNomeModelo()));
		Classifier regressor1 = (Classifier) objectInputStream1.readObject();
		objectInputStream1.close();

		//obtem o modelo 2 criado
		ObjectInputStream objectInputStream2 = new ObjectInputStream(new FileInputStream(resultado2.getNomeModelo()));
		Classifier regressor2 = (Classifier) objectInputStream2.readObject();
		objectInputStream2.close();
		
		//obtem o modelo 3 criado
		ObjectInputStream objectInputStream3 = new ObjectInputStream(new FileInputStream(resultado3.getNomeModelo()));
		Classifier regressor3 = (Classifier) objectInputStream3.readObject();
		objectInputStream3.close();
		
		// cria o modelo de seleção dinâmica que vai aprender sobre o treinamento e escolher um regressor
		KNN_Knora knnSelector = new KNN_Knora();
		
		if(tipoValidacao == TipoValidacao.HOLD_OUT){

			// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
			if(Constantes.BASE_VALIDACAO){
				// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
				knnSelector.criarModeloKNORA(K_SELECAO_DINAMICA, tipoSelecaoDinamica, Utilidade.getCaminhoTreinamento(null, padrao, i), i, padrao);
			}else{
				// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
					knnSelector.criarModeloKNORA(K_SELECAO_DINAMICA, tipoSelecaoDinamica,  Utilidade.getCaminhoTreinamentoEValidacao(null, padrao, i), i, padrao);
			}

		}

		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			if(Constantes.BASE_VALIDACAO){
				knnSelector.criarModeloKNORA(K_SELECAO_DINAMICA, tipoSelecaoDinamica, Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, i), i, padrao);
			}else{
				knnSelector.criarModeloKNORA(K_SELECAO_DINAMICA, tipoSelecaoDinamica, Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null, padrao, i), i, padrao);				
			}
		}

		//calcular a distância de cada elemento da lista de instâncias de validação para a instância avaliada
		for(int j = 0 ; j < instancias.numInstances() ; j++){
			
			
			
			//pega cada instancia do conjunto de validação com rotulo de esforço
			instancia = instancias.instance(j);
	
			valorEstimadoComparativo = Utilidade.REGRESSOR_COMPARATIVO.classifyInstance(instancia);

			// calcula a estimativa de acordo com o tipo de seleção dinâmica
			//valorEstimado = Math.abs(calculaEstimativaDinamica(padrao, regressor1, regressor2, regressor3, tipoSelecaoDinamica, tipoValidacao, instancia, i));
			valorEstimado = calculaEstimativaDinamica(padrao, regressor1, regressor2, regressor3, tipoSelecaoDinamica, tipoValidacao, instancia, knnSelector,  i);

			
			// recupera o valor real do esforço
			valorReal = (double) instancia.classValue();
			

			Utilidade.calculaMetricas(resultado, valorEstimado, valorReal, valorEstimadoComparativo);

			if(Constantes.IMPRIMIR_RELATORIO_RESULTADO_DINAMICO_IDEAL){

				// Processo para encontrar o melhor algoritimo na verdade e comparar com o escolhido			
				ResultadoIdeal resultadoIdeal = obtemResultadoIdeal(instancia, regressor1,  regressor2, regressor3, valorEstimado);
				
				System.out.print("Melhor algoritmo:\t" + resultadoIdeal.getMelhorAlgoritmo()+ "\t");
				System.out.print("Melhor valor estimado:\t" + resultadoIdeal.getMelhorValorEstimado() + "\t");
				System.out.print("Menor erro:\t" + resultadoIdeal.getMenorErroEstimado()+ "\t") ;
				System.out.print("Valor estimado:\t" +  valorEstimado+ "\t");
				System.out.print("Erro estimado:\t" +  Math.abs(valorEstimado-valorReal) + "\t");
				
			}

		}
	}

	
	private ResultadoIdeal obtemResultadoIdeal(Instance instancia, Classifier regressor1, Classifier regressor2, Classifier regressor3, double valorEstimado) throws Exception {

		double valorReal, valorEstimado1, valorEstimado2, valorEstimado3, melhorValorEstimado;
		double erroAbs;
		double erroMre, menorErroMre;
		String melhorAlgoritmo;
		ResultadoIdeal resultadoIdeal = new ResultadoIdeal();

		valorReal = (double) instancia.classValue();
		
		// Classificamos esta instância com o algoritimo relativo ao valor para o regressor1
		valorEstimado1 = (double) (regressor1.classifyInstance(instancia));
		erroAbs = Math.abs(valorReal - (valorEstimado1));
		erroMre = erroAbs / valorReal;
		
		melhorValorEstimado = valorEstimado1;
		melhorAlgoritmo = regressor1.getClass().getName();
		menorErroMre = erroMre;
		

		// Classificamos esta instância com o algoritimo relativo ao valor para o regressor2
		valorEstimado2 = (double)(regressor2.classifyInstance(instancia));
		erroAbs = Math.abs(valorReal - (valorEstimado2));
		erroMre = erroAbs / valorReal;
		if (erroMre < menorErroMre){
			melhorValorEstimado = valorEstimado2;
			melhorAlgoritmo = regressor2.getClass().getName();
			menorErroMre = erroMre;
		}

		// Classificamos esta instância com o algoritimo relativo ao valor para o regressor3
		valorEstimado3 = (double)(regressor3.classifyInstance(instancia));
		erroAbs = Math.abs(valorReal - (valorEstimado3));
		erroMre = erroAbs / valorReal;
		if (erroMre < menorErroMre){
			melhorValorEstimado = valorEstimado3;
			melhorAlgoritmo = regressor3.getClass().getName();
			menorErroMre = erroMre;
		}

		resultadoIdeal.setMelhorAlgoritmo(melhorAlgoritmo);
		resultadoIdeal.setMelhorValorEstimado(melhorValorEstimado);
		resultadoIdeal.setMenorErroEstimado(menorErroMre);
		
		if (resultadoIdeal.isAcertouAlgoritmo(valorEstimado)){
			resultado.addAcerto();
		}else{
			resultado.addErro();
		}

		
		return resultadoIdeal;
	}
	
	private Double calculaEstimativaDinamica(Padrao padrao, Classifier regressor1, Classifier regressor2, Classifier regressor3, TipoMetodoCombinacao tipoSelecaoDinamica, TipoValidacao tipoValidacao,
			Instance instancia, KNN_Knora knnSelector, int i) throws Exception {
		


		// recupera o modelo que foi criado no passo anterior de acordo com o caminho informado na variável nomeModeloCriado de Tecnica
		ObjectInputStream oisKNNSelector = new ObjectInputStream(new FileInputStream(knnSelector.getNomeModeloCriado()));
		IBk knnKNORA = (IBk) oisKNNSelector.readObject();
		oisKNNSelector.close();

		// recupera as instâncias mais próximas de instância baseado no valor de k_selecao_dinamica definido em Constantes e cria um array de distâncias 
		LinearNNSearch nns = (LinearNNSearch)knnKNORA.getNearestNeighbourSearchAlgorithm();
		Instances instanciasK = nns.kNearestNeighbours(instancia, K_SELECAO_DINAMICA);
		double[] distancias = (nns.getDistances());
		
		double valorEstimado = 0;
		double valorReal;
		Instance instanciaAtual;
		
		List<Boolean> acertos1 = new ArrayList<>();
		List<Boolean> acertos2 = new ArrayList<>();
		List<Boolean> acertos3 = new ArrayList<>();
		
	

		// se a seleção for de acordo com a acurácia local dos regressores
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.DCS_LA){
			
			
			List<Double> erros = new ArrayList<Double>();
			erros.add(0,0.0);
			erros.add(1,0.0);
			erros.add(2,0.0);
			
			// avaliando o melhor regressor entre as instâncias mais próximas
			for(int j = 0; j < instanciasK.numInstances(); j++){
				
				instanciaAtual = instanciasK.instance(j);
				valorReal = instanciaAtual.classValue();
				
				// realiza a previsão do regressor1 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e popula a variável lista de erros
				erros.set(0, erros.get(0) + getMetricaErroEstimativaInstanciaAvaliada(regressor1, instanciaAtual, valorReal));
	
				// realiza a previsão do regressor2 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e popula a variável lista de erros
				erros.set(1, erros.get(1) + getMetricaErroEstimativaInstanciaAvaliada(regressor2, instanciaAtual, valorReal));
	
				// realiza a previsão do regressor3 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e popula a variável lista de erros
				erros.set(2, erros.get(2) + getMetricaErroEstimativaInstanciaAvaliada(regressor3, instanciaAtual, valorReal));
	
				
		
			}
	
			// baseado nos valores que foram estimados e na lista de erros respectivos desses valores, o método retorna o valor estimado referente ao regressor com menor média de erro na validação
			valorEstimado = obtemMelhorEstimativaAcuraciaLocal(instanciasK.numInstances(), regressor1,
					regressor2, regressor3, instancia, erros); 
			
			


		}

		if(tipoSelecaoDinamica == TipoMetodoCombinacao.DCS_LAW){
			
			
			List<Double> erros = new ArrayList<Double>();
			erros.add(0,0.0);
			erros.add(1,0.0);
			erros.add(2,0.0);

			
			// avaliando o melhor regressor entre as instâncias mais próximas
			for(int j = 0; j < instanciasK.numInstances(); j++){
				
				instanciaAtual = instanciasK.instance(j);
				valorReal = instanciaAtual.classValue();
				
	
				// realiza a previsão do regressor1 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada
				erros.set(0, erros.get(0) + getMetricaErroEstimativaInstanciaAvaliada(regressor1, instanciaAtual, valorReal, j, distancias));
	
				// realiza a previsão do regressor2 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada
				erros.set(1, erros.get(1) + getMetricaErroEstimativaInstanciaAvaliada(regressor2, instanciaAtual, valorReal, j, distancias));
	
				// realiza a previsão do regressor3 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada
				erros.set(2, erros.get(2) + getMetricaErroEstimativaInstanciaAvaliada(regressor3, instanciaAtual, valorReal, j, distancias));

			
			}
	
			// baseado nos valores que foram estimados e na lista de erros ponderados respectivos desses valores, o método retorna o valor estimado referente ao regressor com menor média de erro ponderado pela distancia na validação
			valorEstimado = obtemMelhorEstimativaAcuraciaLocal(instanciasK.numInstances(), regressor1,
					regressor2, regressor3, instancia, erros); 
		
		}


		if(tipoSelecaoDinamica == TipoMetodoCombinacao.KNORA_U){
		
			
			for(int j = 0; j < instanciasK.numInstances(); j++){
				
				instanciaAtual = instanciasK.instance(j);
				valorReal = instanciaAtual.classValue();
		
				// realiza a previsão do regressor1 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e define uma lista de acertos referente ao regressor usado
				setAcertosRegressor(regressor1, instanciaAtual, valorReal, acertos1, padrao);
	
				// realiza a previsão do regressor2 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e define uma lista de acertos referente ao regressor usado
				setAcertosRegressor(regressor2, instanciaAtual, valorReal, acertos2, padrao);

				// realiza a previsão do regressor3 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e define uma lista de acertos referente ao regressor usado
				setAcertosRegressor(regressor3, instanciaAtual, valorReal, acertos3, padrao);

			}
			
			// baseado nos valores que foram estimados e na lista de acertos, o método retorna o valor estimado referente aos regressores que acertaram na validação
			valorEstimado = obtemMelhorEstimativaPorKnoraU(regressor1,
					regressor2, regressor3, instancia, acertos1, acertos2, acertos3);
		}
		
		
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.KNORA_E){
	
	

			for(int j = 0; j < instanciasK.numInstances(); j++){
				
				instanciaAtual = instanciasK.instance(j);
				valorReal = instanciaAtual.classValue();
				
				
				// realiza a previsão do regressor1 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliadae define uma lista de acertos referente ao regressor usado
				setAcertosRegressor(regressor1, instanciaAtual, valorReal, acertos1, padrao);
	
				// realiza a previsão do regressor2 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e define uma lista de acertos referente ao regressor usado
				setAcertosRegressor(regressor2, instanciaAtual, valorReal, acertos2, padrao);
	
				// realiza a previsão do regressor3 na instância corrente da validação de acordo com as instâncias mais próximas de instância que sendo avaliada e define uma lista de acertos referente ao regressor usado
				setAcertosRegressor(regressor3, instanciaAtual, valorReal, acertos3, padrao);
				
		
			}
	
			// baseado nos valores que foram estimados e na lista de acertos, o método retorna o valor estimado referente aos regressores que não foram eliminados na validação
			valorEstimado = obtemMelhorEstimativaPorKnoraE(regressor1,
					regressor2, regressor3, instancia, acertos1,
					acertos2, acertos3);
		
		}


		
		return valorEstimado;

	}


	
	
	private double getMetricaErroEstimativaInstanciaAvaliada(
			Classifier regressor, Instance instanciaAtual, double valorReal) throws Exception {
		
		double valorEstimado = 0;
		double erro = 0;
		
		//valorEstimado1 = Math.abs(regressor1.classifyInstance(instanciasK.instance(j)));
		valorEstimado = regressor.classifyInstance(instanciaAtual);
		
		if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MAR))
			erro = Math.abs(valorReal - valorEstimado);
		if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MARLOG))
			erro = Math.log10(Math.abs(valorReal - valorEstimado));
		if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MREAJUSTADO)){
			erro = (Math.abs(valorReal - valorEstimado) / valorReal + Math.abs(valorReal - valorEstimado) / Math.abs(valorEstimado)) / 2;
		}
		return erro;
		

	}


	private double getMetricaErroEstimativaInstanciaAvaliada(
			Classifier regressor, Instance instanciaAtual, double valorReal, int j, double[] distancias) throws Exception {
		
		double valorEstimado = 0;
		double erro = 0;

		//valorEstimado1 = Math.abs(regressor1.classifyInstance(instanciasK.instance(j)));
		valorEstimado = regressor.classifyInstance(instanciaAtual);
		
		if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MAR)){
			erro = Math.abs(valorReal - valorEstimado) / distancias[j];					
		}
		if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MARLOG)){
			erro = Math.log10(Math.abs(valorReal - valorEstimado)) / distancias[j];
		}
		if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MREAJUSTADO)){
			erro = (Math.abs(valorReal - valorEstimado) / valorReal + Math.abs(valorReal - valorEstimado) / valorEstimado) / 2 / distancias[j];
		}


		return erro;
	}

	private double setAcertosRegressor(
			Classifier regressor, Instance instanciaAtual, double valorReal, List<Boolean> acertos, Padrao padrao) throws Exception {
		
		double valorEstimado;
		double erro;
		double percentualRelativo;

		//valorEstimado1 = Math.abs(regressor1.classifyInstance(instanciasK.instance(j)));
		valorEstimado = regressor.classifyInstance(instanciaAtual);
		

		

			if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MAR)){
				erro = Math.abs(valorReal - valorEstimado);
				percentualRelativo = erro/valorReal;
				if (erro < padrao.getKnoraMediaMar() || percentualRelativo < KNORA){
					acertos.add(true);
				}else{
					acertos.add(false);
				}
			}
			if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MARLOG)){
				erro =  Math.log10(Math.abs(valorReal - valorEstimado));
				percentualRelativo = erro/Math.log10(valorReal);

				if (erro < padrao.getKnoraMediaMarlog() || percentualRelativo < KNORA){
					acertos.add(true);
				}else{
					acertos.add(false);
				}
			}
			if(Utilidade.METRICA_AVALIACAO.equals(Constantes.MREAJUSTADO)){
				erro = ((Math.abs(valorReal - valorEstimado) / valorReal) + (Math.abs(valorReal - valorEstimado) / valorEstimado)) / 2;
				percentualRelativo = (Math.abs(valorReal - valorEstimado)/valorReal + Math.abs(valorReal - valorEstimado)/Math.abs(valorEstimado))/2;
				if (erro < padrao.getKnoraMediaMreajustado() || percentualRelativo < KNORA){
					acertos.add(true);
				}else{
					acertos.add(false);
				}
			}
			
		
			

		return valorEstimado;
	}

	// Baseado no erros que foram calculados para cada regressor no conjunto de validação retorna um valor estimado
	private double obtemMelhorEstimativaAcuraciaLocal(int numeroDeInstanciasInstanciasK, Classifier regressor1,
			Classifier regressor2, Classifier regressor3, Instance instancia,
			List<Double> erros) throws Exception {

		// calcula a média de erros dos regressores			
		erros.set(0, erros.get(0)/numeroDeInstanciasInstanciasK);
		erros.set(1, erros.get(1)/numeroDeInstanciasInstanciasK);
		erros.set(2, erros.get(2)/numeroDeInstanciasInstanciasK);
		
		int indiceSelecionado = 0;
		int indice = 0;
		double valorEstimado = 0;
		Double menorErro = erros.get(indice);
		for (indice = 0; indice < erros.size(); indice++) {
			if(erros.get(indice) < menorErro){
				menorErro = erros.get(indice);
				indiceSelecionado = indice; 
			}
				
		}
		
		switch (indiceSelecionado) {
		case 0:
			valorEstimado = regressor1.classifyInstance(instancia);
			break;
		case 1:
			valorEstimado = regressor2.classifyInstance(instancia);
			break;
		case 2:
			valorEstimado = regressor3.classifyInstance(instancia);
			break;
		default:
			break;
		}
		
		return valorEstimado;
	}
	
	private double obtemMelhorEstimativaPorKnoraU(Classifier regressor1,
			Classifier regressor2, Classifier regressor3, Instance instancia, List<Boolean> acertos1,
			List<Boolean> acertos2, List<Boolean> acertos3) throws Exception {

		int numeroDeSelecionados = 0;
		double valorEstimado = 0;
		
		if (acertos1.contains(true)){
			valorEstimado += regressor1.classifyInstance(instancia);
			numeroDeSelecionados++;
		}
		if (acertos2.contains(true)){
			valorEstimado += regressor2.classifyInstance(instancia);
			numeroDeSelecionados++;
		}
		if (acertos3.contains(true)){
			valorEstimado += regressor3.classifyInstance(instancia);
			numeroDeSelecionados++;
		}
		
		if(acertos1.contains(true) || acertos2.contains(true) || acertos3.contains(true)){
		
			valorEstimado /= numeroDeSelecionados;
			
		}else{
			valorEstimado = regressor1.classifyInstance(instancia);
			valorEstimado += regressor2.classifyInstance(instancia);
			valorEstimado += regressor3.classifyInstance(instancia);

			valorEstimado /= 3;
		}

		return valorEstimado;
	}

	private double obtemMelhorEstimativaPorKnoraE(Classifier regressor1,
			Classifier regressor2, Classifier regressor3, Instance instancia, List<Boolean> acertos1,
			List<Boolean> acertos2, List<Boolean> acertos3) throws Exception {
		
		double valorEstimado = 0;
		
		double valorEstimado1;
		double valorEstimado2;
		double valorEstimado3;
		
		int max = K_SELECAO_DINAMICA;
		int numeroDeSelecionados = 0;

		boolean isRegressor1 = false;
		boolean isRegressor2 = false;
		boolean isRegressor3 = false;
		
		do {
			
			for (int indice = 0; indice < max; indice++) {
				if (acertos1.get(indice)){
					isRegressor1 = true;
				}else{
					isRegressor1 = false;
					break;
				}
			}

			for (int indice = 0; indice < max; indice++) {
				if (acertos2.get(indice)){
					isRegressor2 = true;
				}else{
					isRegressor2 = false;
					break;
				}
			}

			for (int indice = 0; indice < max; indice++) {
				if (acertos3.get(indice)){
					isRegressor3 = true;
				}else{
					isRegressor3 = false;
					break;
				}
			}

			if(isRegressor1 || isRegressor2 || isRegressor3){
				break;
			}
			max--;
			
		} while (max >= 0);
		
		if (isRegressor1){
			valorEstimado += regressor1.classifyInstance(instancia);
			numeroDeSelecionados++;
		}
		
		if(isRegressor2){
			valorEstimado += regressor2.classifyInstance(instancia);
			numeroDeSelecionados++;
		}
		
		if(isRegressor3){
			valorEstimado += regressor3.classifyInstance(instancia);
			numeroDeSelecionados++;
		}
		
		valorEstimado /= numeroDeSelecionados;
		
		
		if(isRegressor1 == false && isRegressor2 == false && isRegressor3 == false){
			valorEstimado1 = regressor1.classifyInstance(instancia);
			valorEstimado2 = regressor2.classifyInstance(instancia);
			valorEstimado3 = regressor3.classifyInstance(instancia);
			
			valorEstimado = valorEstimado1 + valorEstimado2 + valorEstimado3;
			valorEstimado /= 3;
		}
		
		return valorEstimado;
	}


	@Override
	public void run(Padrao padrao, TipoMetodoCombinacao tipoSelecaoDinamica, TipoValidacao tipoValidacao) {

		try {
			
			File arquivo = null;
			Utilidade.adicionaCabecalhoDados(padrao);
			
			// verifica se o método de validação é hold out e pega o caminho do arquivo de treinamento corrente
			if(tipoValidacao == TipoValidacao.HOLD_OUT){
			
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento(null, padrao, 1));
				
				// gera os arquivos se ainda não tiverem sido gerado em outro teste
				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTeste(null, padrao);
				}
	
				for(int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++){
				
				//treina o regressor1
				RegressorIndividualGeral regressor1 = new RegressorIndividualGeral(this.regressor1);
				
				// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
				if(Constantes.BASE_VALIDACAO){
					// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
					regressor1.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
				}else{
					// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
					regressor1.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
				}
	
				//treina o regressor2
				RegressorIndividualGeral regressor2 = new RegressorIndividualGeral(this.regressor2);

				// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
				if(Constantes.BASE_VALIDACAO){
					// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
					regressor2.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
				}else{
					// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
					regressor2.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
				}
	
				//treina o regressor3
				RegressorIndividualGeral regressor3 = new RegressorIndividualGeral(this.regressor3);

				// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
				if(Constantes.BASE_VALIDACAO){
					// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
					regressor3.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
				}else{
					// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
					regressor3.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
				}
				
				
				
				//avalia o modelo combinado por seleção de acordo com o tipo de seleção dinâmica
				avaliarMetodo(padrao, regressor1.getResultado(), regressor2.getResultado(), regressor3.getResultado(), tipoSelecaoDinamica, tipoValidacao, i);
				
				}
			}

			if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
				
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, 1));
				
				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTesteLeaveOneOut(null, padrao);
				}
	
				for(int i = 1; i <= padrao.getTamanhoBase(); i++){
				
				//treina o regressor1
				RegressorIndividualGeral regressor1 = new RegressorIndividualGeral(this.regressor1);
				
				if(Constantes.BASE_VALIDACAO){
					regressor1.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, i) , i, padrao);
				}else{
					regressor1.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null, padrao, i) , i, padrao);					
				}
				
				//treina o regressor2
				RegressorIndividualGeral regressor2 = new RegressorIndividualGeral(this.regressor2);
				if(Constantes.BASE_VALIDACAO){
					regressor2.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, i), i, padrao);
				}else{
					regressor2.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null, padrao, i), i, padrao);					
				}
	
				//treina o regressor3
				RegressorIndividualGeral regressor3 = new RegressorIndividualGeral(this.regressor3);
				if(Constantes.BASE_VALIDACAO){
					regressor3.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, i), i, padrao);
				}else{
					regressor3.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null, padrao, i), i, padrao);
				}
				
				
				//avalia o modelo combinado
				avaliarMetodo(padrao, regressor1.getResultado(), regressor2.getResultado(), regressor3.getResultado(), tipoSelecaoDinamica, tipoValidacao, i);
				
				}
			}
			
			// calcula a média de erros para cada iteração ou individual quando é leave one out considerando a base de dados 
			Utilidade.calculaValoresCentraisDasMetricas();
			// reinicia as variáveis que guardam os resultados
			Utilidade.zerarMediasAuxiliares();
			// adiciona linha no arquivo de resultados para formatar a apresentação dos erros da próxima base de dados
			Utilidade.adicionaLinhaArquivo();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	@Override
	public void run(Padrao padrao, TipoMetodoCombinacao tipoSelecaoDinamica) {

		try {
			
			File arquivo = null;
			Utilidade.adicionaCabecalhoDados(padrao);
			
			
			arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento(null, padrao, 1));
			
			if(!arquivo.exists()){
				GeradorAleatorioDados.gerarTreinamentoValidacaoTeste(null, padrao);
			}

			for(int i = 1; i <= 50; i++){
			
			//treina o regressor1
			RegressorIndividualGeral regressor1 = new RegressorIndividualGeral(this.regressor1);
			regressor1.criarModelo(Utilidade.getCaminhoTreinamento(null, padrao, i), i, padrao);

			//treina o regressor2
			RegressorIndividualGeral regressor2 = new RegressorIndividualGeral(this.regressor2);
			regressor2.criarModelo(Utilidade.getCaminhoTreinamento(null, padrao, i), i, padrao);

			//treina o regressor3
			RegressorIndividualGeral regressor3 = new RegressorIndividualGeral(this.regressor3);
			regressor3.criarModelo(Utilidade.getCaminhoTreinamento(null, padrao, i), i, padrao );
			
			
			
			//avalia o modelo combinado
			avaliarMetodo(padrao, regressor1.getResultado(), regressor2.getResultado(), regressor3.getResultado(), tipoSelecaoDinamica, TipoValidacao.HOLD_OUT,  i);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public String toString() {
		return "Knora_DCS_Set_Ensemble_Dynamic " + TesteKnoraDCSSetEnsembleDynamic.experimento;
	}


	@Override
	public void run(Padrao padrao, TipoValidacao tipoValidacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Padrao padrao, Integer tipoClassificador1,
			Integer tipoClassificador2, Integer tipoClassificador3,
			Integer tipoClassificador4, Integer tipoClassificador5,
			Integer tipoClassificador6, Integer tipoClassificador7,
			TipoMetodoCombinacao tipoSelecaoDinamica) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Padrao padrao, Integer tipoClassificador1,
			Integer tipoClassificador2, Integer tipoClassificador3,
			Integer tipoClassificador4, Integer tipoClassificador5,
			Integer tipoClassificador6, Integer tipoClassificador7,
			TipoMetodoCombinacao tipoSelecaoDinamica,
			TipoValidacao tipoValidacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(String treino, String teste) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Padrao padrao) {
		// TODO Auto-generated method stub
		
	}
	
}
