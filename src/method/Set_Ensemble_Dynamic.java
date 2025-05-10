package method;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import model.Resultado;
import model.ResultadoIdeal;
import util.Constantes;
import util.GeradorAleatorioDados;
import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import util.Utilidade;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import data.Padrao;

public class Set_Ensemble_Dynamic extends SetEnsembleDynamicTecnica {
	
	private static final String REGRESSOR1 = "REGRESSOR1";
	private static final String REGRESSOR2 = "REGRESSOR2";
	private static final String REGRESSOR3 = "REGRESSOR3";
	
	private Classifier regressor1;
	private Classifier regressor2;
	private Classifier regressor3;

	public Set_Ensemble_Dynamic(Classifier regressor1, Classifier regressor2, Classifier regressor3) {
		this.regressor1 = regressor1;
		this.regressor2 = regressor2;
		this.regressor3 = regressor3;
	}
	
	private void avaliarMetodo(Padrao padrao, Resultado resultadoRegressor1, Resultado resultadoRegressor2,
			Resultado resultadoRegressor3, Integer tipoClassificador1, Integer tipoClassificador2, Integer tipoClassificador3, Integer tipoClassificador4, Integer tipoClassificador5, Integer tipoClassificador6, Integer tipoClassificador7, Integer tipoClassificador8, Integer tipoClassificador9, Integer tipoClassificador10, TipoMetodoCombinacao tipoSelecaoDinamica, TipoValidacao tipoValidacao, int i) throws Exception{
		
		// se for hold out
		if(tipoValidacao == TipoValidacao.HOLD_OUT){
			if(Constantes.BASE_VALIDACAO){
				// carrega os dados de validação nas instancias principais
				configurarDados(Utilidade.getCaminhoValidacao(null, padrao, i));
			}
			if(!Constantes.BASE_VALIDACAO){
				// carrega os dados de validação nas instancias principais
				configurarDados(Utilidade.getCaminhoTeste(null, padrao, i));
			}
		}
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			if(Constantes.BASE_VALIDACAO){
				// carrega os dados de validação nas instancias principais
				configurarDados(Utilidade.getCaminhoValidacaoLeaveOneOut(null, padrao, i));
			}
			if(!Constantes.BASE_VALIDACAO){
				// carrega os dados de validação nas instancias principais
				configurarDados(Utilidade.getCaminhoTesteLeaveOneOut(null, padrao, i));
			}
		}

		// calcula as estimativas combinadas utilizando os resultados de KNN e os dados de classificação para o melhor algoritimo 
		calcularEstimativas(padrao, resultadoRegressor1, resultadoRegressor2, resultadoRegressor3, tipoClassificador1, tipoClassificador2, tipoClassificador3, tipoClassificador4, tipoClassificador5, tipoClassificador6, tipoClassificador7,  tipoClassificador8, tipoClassificador9, tipoClassificador10, tipoSelecaoDinamica, tipoValidacao, i);
		
		
		// imprimi os resultados em tela
		//imprimirResultadosMRE();
		calcularResultadosGerais();
		
	}

	private void calcularEstimativas(Padrao padrao, Resultado resultadoRegressor1,
			Resultado resultadoRegressor2, Resultado resultadoRegressor3, Integer tipoClassificador1, Integer tipoClassificador2, Integer tipoClassificador3, Integer tipoClassificador4,Integer tipoClassificador5, Integer tipoClassificador6,Integer tipoClassificador7, Integer tipoClassificador8, Integer tipoClassificador9,Integer tipoClassificador10, TipoMetodoCombinacao tipoMetodoCombinacao, TipoValidacao tipoValidacao, int i)
			throws Exception {

		
		double valorEstimado = 0, valorEstimadoComparativo = 0;
		double valorReal = 0;
		double erroAbsoluto, marlog, mreAjustado;
		Instance instancia;
		resultado = new Resultado();
		resultado.setNomeModelo(toString() + " - " + padrao.toString());
		
		Classifier classificador1 = null, 
				classificador2 = null, 
				classificador3 = null, 
				classificador4 = null, 
				classificador5 = null, 
				classificador6 = null, 
				classificador7 = null,
				classificador8 = null,
				classificador9 = null,
				classificador10 = null;
			
		
		//obtem o modelo regressor1 criado
		ObjectInputStream oisRegressor1 = new ObjectInputStream(new FileInputStream(resultadoRegressor1.getNomeModelo()));
		Classifier regressor1 = (Classifier) oisRegressor1.readObject();
		oisRegressor1.close();

		//obtem o modelo regressor2 criado
		ObjectInputStream oisRegressor2 = new ObjectInputStream(new FileInputStream(resultadoRegressor2.getNomeModelo()));
		Classifier regressor2 = (Classifier) oisRegressor2.readObject();
		oisRegressor2.close();
		
		//obtem o modelo regressor3 criado
		ObjectInputStream oisRegressor3 = new ObjectInputStream(new FileInputStream(resultadoRegressor3.getNomeModelo()));
		Classifier regressor3 = (Classifier) oisRegressor3.readObject();
		oisRegressor3.close();
		
		// se for validação com hold out
		if(tipoValidacao == TipoValidacao.HOLD_OUT){

			if(Constantes.BASE_VALIDACAO){
				// configura as instancias com o classificador de melhor tecnica e sem o classificador 		
				File arquivoValidacaoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoValidacaoClassificacao(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
				
				// se o arquivo de validação do classificador não existir 
				if(!arquivoValidacaoClassificador.exists()){
					// converte os dados de validação para uma lista de projetos e coloca na variável listaProjetosValidacao
					setListaProjetosValidacao(Padrao.converteInstanciasParaProjetos(padrao, Utilidade.getCaminhoValidacao(null, padrao, i)));
					
					// gera os dados de validação do classificador baseado na lista de projetos de validação definida anteriormente
					GeradorAleatorioDados.gerarValidacaoClassificador(getListaProjetosValidacao(), null, padrao, i);
				}
		
				// define os dados de treinamento e validação dos classificadores, de acordo com o que os classificadores escolherem como regressor ideal para realizar a previsão
				configurarDadosClassificacao(Utilidade.getCaminhoTreinamentoClassificacao(null, padrao, Utilidade.QUANTIDADE_REGRESSOR,  Utilidade.TIPO_METRICA_AVALIACAO, i), Utilidade.getCaminhoValidacaoClassificacao(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
			}

			// se for base de testes
			if(!Constantes.BASE_VALIDACAO){
				// configura as instancias com o classificador de melhor tecnica e sem o classificador 		
				File arquivoTesteClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTesteClassificacao(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
				
				// se o arquivo de teste do classificador ainda não existir no SO
				if(!arquivoTesteClassificador.exists()){
					
					// converte os dados de teste para uma lista de projetos e coloca na variável listaProjetosTeste
					setListaProjetosTeste(Padrao.converteInstanciasParaProjetos(padrao, Utilidade.getCaminhoTeste(null, padrao, i)));

					// gera os dados de validação do classificador baseado na lista de projetos de validação definida anteriormente
					GeradorAleatorioDados.gerarTesteClassificador(getListaProjetosTeste(), null, padrao, i);
				}
		
				// define os dados de treinamento e teste dos classificadores, de acordo com o que os classificadores escolherem como regressor ideal para realizar a previsão
				configurarDadosClassificacao(Utilidade.getCaminhoTreinamentoClassificacaoComValidacao(null, padrao,Utilidade.QUANTIDADE_REGRESSOR, Utilidade.TIPO_METRICA_AVALIACAO, i), Utilidade.getCaminhoTesteClassificacao(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
			}
		}
		
		// se for validação com leave one out
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){

			// se for avaliar na validação
			if(Constantes.BASE_VALIDACAO){
				// configura as instancias com o classificador de melhor tecnica e sem o classificador 		
				File arquivoValidacaoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoValidacaoClassificacaoLeaveOneOut(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
				
				// se o arquivo de validação do classificador para leave one out não existir 
				if(!arquivoValidacaoClassificador.exists()){
					// converte os dados de validação para uma lista de projetos e coloca na variável listaProjetosValidacao
					setListaProjetosValidacao(Padrao.converteInstanciasParaProjetos(padrao, Utilidade.getCaminhoValidacaoLeaveOneOut(null, padrao, i)));

					// gera os dados de validação do classificador baseado na lista de projetos de validação definida anteriormente
					GeradorAleatorioDados.gerarValidacaoClassificadorLeaveOneOut(getListaProjetosValidacao(), null, padrao, i);
				}
		
				// define os dados de treinamento e validação dos classificadores, de acordo com o que os classificadores escolherem como regressor ideal para realizar a previsão
				configurarDadosClassificacao(Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, Utilidade.QUANTIDADE_REGRESSOR, Utilidade.TIPO_METRICA_AVALIACAO, i), Utilidade.getCaminhoValidacaoClassificacaoLeaveOneOut(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
				
			}

			// se for avaliar nos testes
			if(!Constantes.BASE_VALIDACAO){
				// configura as instancias com o classificador de melhor tecnica e sem o classificador 		
				File arquivoTesteClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTesteClassificacaoLeaveOneOut(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
				if(!arquivoTesteClassificador.exists()){
					// converte os dados de teste para uma lista de projetos e coloca na variável listaProjetosTeste
					setListaProjetosTeste(Padrao.converteInstanciasParaProjetos(padrao, Utilidade.getCaminhoTesteLeaveOneOut(null, padrao, i)));

					// gera os dados de validação do classificador baseado na lista de projetos de validação definida anteriormente
					GeradorAleatorioDados.gerarTesteClassificadorLeaveOneOut(getListaProjetosTeste(), null, padrao, i);
				}
		
				// define os dados de treinamento e teste dos classificadores, de acordo com o que os classificadores escolherem como regressor ideal para realizar a previsão
				configurarDadosClassificacao(Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, Utilidade.QUANTIDADE_REGRESSOR, Utilidade.TIPO_METRICA_AVALIACAO, i), Utilidade.getCaminhoTesteClassificacaoLeaveOneOut(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
			}
		}

		// obtem os classificadores treinados com as instancias que foram configuradas
		if(tipoClassificador1 != null){
			classificador1 = getClassificadorTreinado(tipoClassificador1);
		}
		if(tipoClassificador2 != null){
			classificador2 = getClassificadorTreinado(tipoClassificador2);
		}
		if(tipoClassificador3 != null){
			classificador3 = getClassificadorTreinado(tipoClassificador3);
		}
		if(tipoClassificador4 != null){
			classificador4 = getClassificadorTreinado(tipoClassificador4);
		}
		if(tipoClassificador5 != null){
			classificador5 = getClassificadorTreinado(tipoClassificador5);
		}
		if(tipoClassificador6 != null){
			classificador6 = getClassificadorTreinado(tipoClassificador6);
		}
		if(tipoClassificador7 != null){
			classificador7 = getClassificadorTreinado(tipoClassificador7);
		}
		if(tipoClassificador8 != null){
			classificador8 = getClassificadorTreinado(tipoClassificador8);
		}
		if(tipoClassificador9 != null){
			classificador9 = getClassificadorTreinado(tipoClassificador9);
		}
		if(tipoClassificador10 != null){
			classificador10 = getClassificadorTreinado(tipoClassificador10);
		}

		String algoritmoEscolhidoPeloClassificador = null;
		Instance instanciaSemClassificador;
		List<Double> valoresEstimados = new ArrayList<Double>();
		

		
		//calcular a distância de cada elemento da lista de projetos validação para a lista de projetos avaliados
		for(int j = 0 ; j < instancias.numInstances() ; j++){
			
			ResultadoIdeal resultadoIdeal;
			
			//pega cada instancia do conjunto de validação com rotulo de esforço
			instancia = instancias.instance(j);
			
			//pega cada instancia do conjunto de validação com rotulo de melhor algoritimo vazio
			instanciaSemClassificador = instanciasSemClassificacao.instance(j);
			
			valoresEstimados = new ArrayList<Double>();
			
			switch (Utilidade.QUANTIDADE_CLASSIFICADOR) {
			
			
			case 10:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador1, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);

			case 9:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador2, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);


			case 8:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador3, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);

			case 7:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador4, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);

			case 6:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador5, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);

			case 5:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador6, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);
				
			case 4:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador7, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);
				
			case 3:				
				valorEstimado = calculaEstimativaDinamica(padrao, classificador8, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);
				
			case 2:	
				valorEstimado = calculaEstimativaDinamica(padrao, classificador9, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);
				
			case 1:	
				valorEstimado = calculaEstimativaDinamica(padrao, classificador10, instanciaSemClassificador, algoritmoEscolhidoPeloClassificador, regressor1, regressor2, regressor3, instancia);
				valoresEstimados.add(valorEstimado);

			}

			// pega o valor de acordo com o tipo de regra de combinação usada
			valorEstimado = getEstimativaEnsemble(tipoMetodoCombinacao, valoresEstimados);
			
			// recupera o valor real do esforço
			valorReal = (double) instancia.classValue();
			
			// Processo para encontrar o melhor algoritimo na verdade e comparar com o escolhido			
			resultadoIdeal = obtemResultadoIdeal(valorReal,
					instancia, regressor1,  regressor2, regressor3, algoritmoEscolhidoPeloClassificador);

			if (resultadoIdeal.isAcertouAlgoritmo()){
				resultado.addAcerto();
			}else{
				resultado.addErro();
			}

			Utilidade.calculaMetricas(resultado, valorEstimado, valorReal, valorEstimadoComparativo);
				
		}
	}

	
	private ResultadoIdeal obtemResultadoIdeal(double valorReal,
			Instance instancia, Classifier regressor1, Classifier regressor2, Classifier regressor3,
			String algoritmoEscolhidoPeloClassificador) throws Exception {

		double valorEstimado, melhorValorEstimado;
		double erroAbs;
		double erroMre, menorErroMre;
		String melhorAlgoritmo;
		ResultadoIdeal resultadoIdeal = new ResultadoIdeal();

		
		// Classificamos esta instância com o algoritimo relativo ao valor para o knn
		valorEstimado = (double) (regressor1.classifyInstance(instancia));
		erroAbs = Math.abs(valorReal - (valorEstimado));
		erroMre = erroAbs / valorReal;
		
		melhorValorEstimado = valorEstimado;
		melhorAlgoritmo = REGRESSOR1;
		menorErroMre = erroMre;
		

		// Classificamos esta instância com o algoritimo relativo ao valor para o regressor21
		valorEstimado = (double)(regressor2.classifyInstance(instancia));
		erroAbs = Math.abs(valorReal - (valorEstimado));
		erroMre = erroAbs / valorReal;
		if (erroMre < menorErroMre){
			melhorValorEstimado = valorEstimado;
			melhorAlgoritmo = REGRESSOR2;
			menorErroMre = erroMre;
		}

		// Classificamos esta instância com o algoritimo relativo ao valor para o regressor3
		valorEstimado = (double)(regressor3.classifyInstance(instancia));
		erroAbs = Math.abs(valorReal - (valorEstimado));
		erroMre = erroAbs / valorReal;
		if (erroMre < menorErroMre){
			melhorValorEstimado = valorEstimado;
			melhorAlgoritmo = REGRESSOR3;
			menorErroMre = erroMre;
		}

		resultadoIdeal.setMelhorAlgoritmo(melhorAlgoritmo);
		resultadoIdeal.setMelhorValorEstimado(melhorValorEstimado);
		resultadoIdeal.setMenorErroEstimado(menorErroMre);
		
//		System.out.print("	" + resultadoIdeal.getMelhorAlgoritmo());
//		System.out.print("	" +  algoritmoEscolhidoPeloClassificador);
//		System.out.println();
		
		if(melhorAlgoritmo.equals(algoritmoEscolhidoPeloClassificador)){
			resultadoIdeal.setAcertouAlgoritmo(true);
		}else{
			resultadoIdeal.setAcertouAlgoritmo(false);
		}
		
		return resultadoIdeal;
	}
	
	private Double calculaEstimativaDinamica(Padrao padrao,
			Classifier classificador, Instance instanciaSemClassificador,
			String algoritmoEscolhidoPeloClassificador, Classifier regressor1, Classifier regressor2, Classifier regressor3,
			Instance instancia) throws Exception {
		
		// configura o rótulo
		double rotuloClassificador = 0;
		Attribute rotulo = instanciasClassificadas.attribute(padrao.getIndiceRotuloClassificador());
		double valorEstimado;
		double[] probabilidades = null; 
		
		// classifica dinâmicamente a instancia com o melhor regressor usando o
		// algoritimo de classificação
		rotuloClassificador = classificador.classifyInstance(instanciaSemClassificador);
		// converte a classificação obtida para valor de caracteres que
		// representa o melho algoritimo
		if(Utilidade.ATIVA_PROBABILIDADES){
			algoritmoEscolhidoPeloClassificador = new String();
			probabilidades = classificador.distributionForInstance(instanciaSemClassificador);
		}else{
			algoritmoEscolhidoPeloClassificador = rotulo.value((int) rotuloClassificador);
			
		}

		// verifica o valor do classificador estimado
		if (algoritmoEscolhidoPeloClassificador.equals(REGRESSOR1)) {

			// Classificamos esta instância com o algoritimo relativo ao valor
			// knn
			valorEstimado = (double) (regressor1.classifyInstance(instancia));

		} else if (algoritmoEscolhidoPeloClassificador.equals(REGRESSOR2)) {

			// Classificamos esta instância com o algoritimo relativo ao valor
			// regressor2
			valorEstimado = (double) (regressor2.classifyInstance(instancia));

		} else if (algoritmoEscolhidoPeloClassificador.equals(REGRESSOR3)) {

			// Classificamos esta instância com o algoritimo relativo ao valor
			// regressor3
			valorEstimado = (double) (regressor3.classifyInstance(instancia));

		} else if (Utilidade.ATIVA_PROBABILIDADES){

			
			valorEstimado = (double) (regressor1.classifyInstance(instancia)* probabilidades[0]) + 
					(regressor2.classifyInstance(instancia)* probabilidades[1]) + 
					(regressor3.classifyInstance(instancia)* probabilidades[2]);
		
		} else {
			System.out.println(algoritmoEscolhidoPeloClassificador);
			throw new Exception("Melhor Algoritmo não encontrado");
		}
		return valorEstimado;

	}
	
	private Resultado obtemResultadosInternos(Classifier regressor, Instances instancias, int fold) throws Exception {
		
		Instances instanciasCV = instancias;
		Instances instanciasTreino;
		Instances instanciasTeste;
		
		//Evaluation evaluation;
		
		Double erroAbsoluto, marlog, mreAjustado, valorEstimado, valorReal;
		Resultado resultadoCV = new Resultado();
		
		if(fold == 0){
			fold = instanciasCV.numInstances();
		}
		
		for(int i=0; i < fold; i++){
			
			if(fold > 1){
				instanciasTreino = instanciasCV.trainCV(fold, i);
				instanciasTeste = instanciasCV.testCV(fold, i);
			}else{
				instanciasTreino = instanciasCV;
				instanciasTeste = instanciasCV;
			}
			
			regressor.buildClassifier(instanciasTreino);
			
			//evaluation = new Evaluation(instanciasTreino);
			
			for (int j = 0; j < instanciasTeste.numInstances(); j++) {

				// desabilitado porque vai testar a partição completa e gravar apenas a média dos erros
				//evaluation.evaluateModel(regressor, instanciasTeste);

				valorEstimado = (double) regressor.classifyInstance(instanciasTeste.instance(j));
				valorReal = (double) instanciasTeste.instance(j).classValue();
				
				// calcula os erros de acordo com as métricas
				
				erroAbsoluto = Math.abs(valorEstimado - valorReal);
				
				marlog = Math.log10(erroAbsoluto); 
						
							
				mreAjustado = ((erroAbsoluto / valorReal) + (erroAbsoluto / Math.abs(valorEstimado))/2);
				
				
				// adiciona os erros a uma lista de métricas
				
				resultadoCV.getErrosAbsolutos().add(erroAbsoluto);
				resultadoCV.getMarlogs().add(marlog);
				resultadoCV.getMresAjustados().add(mreAjustado);
				
				// adiciona as estimativas do modelo			
				resultadoCV.getEstimativas().add(valorEstimado);
				
				// adiciona os valores reais das instâncias
				resultadoCV.getValoresReais().add(valorReal);

			}
			
			

			//resultadoCV.getErrosAbsolutos().add(evaluation.meanAbsoluteError());
			
			//regressorIndividualGeral1.avaliarModelo(regressor1, instanciasTeste, regressorIndividualGeral1.getResultado().getNomeModelo());
		}
		
		resultadoCV.calculaMediaErroAbsoluto();
		resultadoCV.calculaMediaMarLog();
		resultadoCV.calculaMediaMREAjustado();
		resultadoCV.calculaMediaMRE();
		resultadoCV.calculaMediaBRE();
		resultadoCV.calculaMediaErrosQuadraticos();
		resultadoCV.calculaRaizMediaErrosQuadraticos();;
		resultadoCV.calculaMediaErrosLogQuadraticos();
		resultadoCV.calculaRQuadrado();
		resultadoCV.calculaGanhoRelativo();
		
		
		return resultadoCV;
	}
	
	@Override
	public void run(Padrao padrao, Integer tipoClassificador1, Integer tipoClassificador2, Integer tipoClassificador3, Integer tipoClassificador4, Integer tipoClassificador5, Integer tipoClassificador6, Integer tipoClassificador7, Integer tipoClassificador8, Integer tipoClassificador9, Integer tipoClassificador10, TipoMetodoCombinacao tipoSelecaoDinamica) {

		// arquivo de treinamento
		File arquivo = null;
		
		// adiciona informações do banco de dados no arquivo de saída
		Utilidade.adicionaCabecalhoDados(padrao);

		try {
			
			// obtém arquivo com os dados de treinamento utilizados
			arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento(null, padrao, Constantes.INDICE_INICIAL));
			
			// se o arquivo já existir no SO não é gerado 
			if(!arquivo.exists()){
				// gerada dados de treinamento e teste
				GeradorAleatorioDados.gerarTreinamentoTeste(null, padrao);
			}

			for(int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++){
			
			//treina o regressor1
			RegressorIndividualGeral regressorIndividualGeral1 = new RegressorIndividualGeral(regressor1);
			regressorIndividualGeral1.run(Utilidade.getCaminhoTreinamento(null, padrao, i),
					Utilidade.getCaminhoTreinamento(null, padrao, i));

			//treina o regressor2
			RegressorIndividualGeral regressorIndividualGeral2 = new RegressorIndividualGeral(regressor2);
			regressorIndividualGeral2.run(Utilidade.getCaminhoTreinamento(null, padrao, i), 
					Utilidade.getCaminhoTreinamento(null, padrao, i));

			//treina o regressor3
			RegressorIndividualGeral regressorIndividualGeral3 = new RegressorIndividualGeral(regressor3);
			regressorIndividualGeral3.run(Utilidade.getCaminhoTreinamento(null, padrao, i),
					Utilidade.getCaminhoTreinamento(null, padrao, i));
			
			
			//carrega a lista de projetos que foram testados
			setListaProjetos(Padrao.converteInstanciasParaProjetos(padrao, Utilidade.getCaminhoTreinamento(null, padrao, i)));
			
			//adiciona os campos melhor algoritimo e menor erro a lista de projetos
			adicionarCamposListaProjetosAvaliados(padrao, regressorIndividualGeral1.getResultado(), regressorIndividualGeral2.getResultado(), regressorIndividualGeral3.getResultado(), "CONSTANTE", TipoValidacao.HOLD_OUT, i);
			
			//avalia o modelo combinado
			avaliarMetodo(padrao, regressorIndividualGeral1.getResultado(), regressorIndividualGeral2.getResultado(), regressorIndividualGeral3.getResultado(), tipoClassificador1, tipoClassificador2, tipoClassificador3, tipoClassificador4, tipoClassificador5, tipoClassificador6, tipoClassificador7, tipoClassificador8, tipoClassificador9, tipoClassificador10, tipoSelecaoDinamica, TipoValidacao.HOLD_OUT, i);
			
			}
			
			// calcula médidas das métricas de avaliação e coloca no arquivo de saída
			Utilidade.calculaValoresCentraisDasMetricas();
			// reinicia as médias para avaliar o novo conjunto de dados
			Utilidade.zerarMediasAuxiliares();
			// formata o arquivo de saída com uma linha abaixo
			Utilidade.adicionaLinhaArquivo();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	@Override
	public void run(Padrao padrao, TipoValidacao tipoValidacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Padrao padrao, Integer tipoClassificador1, Integer tipoClassificador2, Integer tipoClassificador3, Integer tipoClassificador4, Integer tipoClassificador5, Integer tipoClassificador6, Integer tipoClassificador7, Integer tipoClassificador8, Integer tipoClassificador9, Integer tipoClassificador10,
			TipoMetodoCombinacao tipoSelecaoDinamica, TipoValidacao tipoValidacao) {
		try {
			
			// arquivo de treinamento
			File arquivo = null;
			String caminhoTreinamento = null;
			
			// adiciona informações do banco de dados no arquivo de saída
			Utilidade.adicionaCabecalhoDados(padrao);
			
			// se for validação com hold out
			if(tipoValidacao == TipoValidacao.HOLD_OUT){
				
				// tenta recuperar arquivo da primeira iteração
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento(null, padrao, Constantes.INDICE_INICIAL));
				
				// se o arquivo de dados existir ele não vai ser gerado novamente 
				if(!arquivo.exists()){
					
					// gera os arquivos de treinamento e de avaliação para o conjunto de dados representado em padrao
					GeradorAleatorioDados.gerarTreinamentoValidacaoTeste(null, padrao);
				}
				

	
				// percorrer todas as iterações geradas
				for(int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++){

					
				// define a variável com valor false para os resultados individuais não aparecerem na tela do console	
				Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS = false;

				// verifica o tipo de avaliação para configurar corretamente o caminho de treinamento do regressor
				if(Constantes.BASE_VALIDACAO){
					caminhoTreinamento = Utilidade.getCaminhoTreinamento(null, padrao, i);
				}else{
					caminhoTreinamento = Utilidade.getCaminhoTreinamentoEValidacao(null, padrao, i);
				}
				
				//treina o regressor1
				RegressorIndividualGeral regressorIndividualGeral1 = new RegressorIndividualGeral(regressor1);
				configurarDados(caminhoTreinamento);
				Resultado resultado1 = obtemResultadosInternos(regressor1, instancias, Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR);
				regressorIndividualGeral1.criarModelo(caminhoTreinamento);
				resultado1.setNomeModelo(regressorIndividualGeral1.getNomeModeloCriado());
				regressorIndividualGeral1.setResultado(resultado1);

	
				//treina o regressor2
				RegressorIndividualGeral regressorIndividualGeral2 = new RegressorIndividualGeral(regressor2);
				configurarDados(caminhoTreinamento);
				Resultado resultado2 = obtemResultadosInternos(regressor2, instancias, Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR);
				regressorIndividualGeral2.criarModelo(caminhoTreinamento);
				resultado2.setNomeModelo(regressorIndividualGeral2.getNomeModeloCriado());
				regressorIndividualGeral2.setResultado(resultado2);

	
				//treina o regressor3
				RegressorIndividualGeral regressorIndividualGeral3 = new RegressorIndividualGeral(regressor3);
				configurarDados(caminhoTreinamento);
				Resultado resultado3 = obtemResultadosInternos(regressor3, instancias, Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR);
				regressorIndividualGeral3.criarModelo(caminhoTreinamento);
				resultado3.setNomeModelo(regressorIndividualGeral3.getNomeModeloCriado());
				regressorIndividualGeral3.setResultado(resultado3);

				Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS = true;
				
				//carrega a lista de projetos que foram testados
				setListaProjetos(Padrao.converteInstanciasParaProjetos(padrao, caminhoTreinamento));
				
				//adiciona os campos melhor algoritimo e menor erro a lista de projetos
				adicionarCamposListaProjetosAvaliados(padrao, regressorIndividualGeral1.getResultado(), regressorIndividualGeral2.getResultado(), regressorIndividualGeral3.getResultado(), null, tipoValidacao, i);
				
				//avalia o modelo combinado
				avaliarMetodo(padrao, regressorIndividualGeral1.getResultado(), regressorIndividualGeral2.getResultado(), regressorIndividualGeral3.getResultado(), tipoClassificador1, tipoClassificador2, tipoClassificador3, tipoClassificador4, tipoClassificador5, tipoClassificador6, tipoClassificador7, tipoClassificador8, tipoClassificador9, tipoClassificador10, tipoSelecaoDinamica, tipoValidacao, i);
				
				}
			}
			
			// se for validação com leave one out
			if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
				
				// recupera um arquivo com dados de treinamento da primeira iteração
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, Constantes.INDICE_INICIAL));
				
				// se o arquivo existir no SO
				if(!arquivo.exists()){
					// gera os dados de treinamento, teste e validação
					GeradorAleatorioDados.gerarTreinamentoValidacaoTesteLeaveOneOut(null, padrao);
				}
	
				
				// percorre do indice inicial até o último elemento 
				for(int i = Constantes.INDICE_INICIAL; i <= padrao.getTamanhoBase(); i++){

				Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS = false;

				// verifica o tipo de avaliação para configurar corretamente o caminho de treinamento do regressor
				if(Constantes.BASE_VALIDACAO){
					caminhoTreinamento = Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, i);
				}else{
					caminhoTreinamento = Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null, padrao, i);
				}

				//treina o Regressor1
				RegressorIndividualGeral regressorIndividualGeral1 = new RegressorIndividualGeral(regressor1);
				configurarDados(caminhoTreinamento);
				Resultado resultado1 = obtemResultadosInternos(regressor1, instancias, Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR);
				regressorIndividualGeral1.criarModelo(caminhoTreinamento);
				resultado1.setNomeModelo(regressorIndividualGeral1.getNomeModeloCriado());
				regressorIndividualGeral1.setResultado(resultado1);
	
				//treina o Regressor2
				RegressorIndividualGeral regressorIndividualGeral2 = new RegressorIndividualGeral(regressor2);
				configurarDados(caminhoTreinamento);
				Resultado resultado2 = obtemResultadosInternos(regressor2, instancias, Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR);
				regressorIndividualGeral2.criarModelo(caminhoTreinamento);
				resultado2.setNomeModelo(regressorIndividualGeral2.getNomeModeloCriado());
				regressorIndividualGeral2.setResultado(resultado2);
	
				//treina o Regressor3
				RegressorIndividualGeral regressorIndividualGeral3 = new RegressorIndividualGeral(regressor3);
				configurarDados(caminhoTreinamento);
				Resultado resultado3 = obtemResultadosInternos(regressor3, instancias, Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR);
				regressorIndividualGeral3.criarModelo(caminhoTreinamento);
				resultado3.setNomeModelo(regressorIndividualGeral3.getNomeModeloCriado());
				regressorIndividualGeral3.setResultado(resultado3);
				
				Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS = true;
		
				
				//carrega a lista de projetos que foram testados
				setListaProjetos(Padrao.converteInstanciasParaProjetos(padrao, caminhoTreinamento));
				
				//adiciona os campos melhor algoritimo e menor erro a lista de projetos
				adicionarCamposListaProjetosAvaliados(padrao, regressorIndividualGeral1.getResultado(), regressorIndividualGeral2.getResultado(), regressorIndividualGeral3.getResultado(), "CONSTANTE", tipoValidacao, i);
				
				//avalia o modelo combinado
				avaliarMetodo(padrao, regressorIndividualGeral1.getResultado(), regressorIndividualGeral2.getResultado(), regressorIndividualGeral3.getResultado(), tipoClassificador1, tipoClassificador2, tipoClassificador3, tipoClassificador4, tipoClassificador5, tipoClassificador6, tipoClassificador7, tipoClassificador8, tipoClassificador9, tipoClassificador10, tipoSelecaoDinamica, tipoValidacao, i);
				
				}
			}
			
			// calcula médidas das métricas de avaliação e coloca no arquivo de saída
			Utilidade.calculaValoresCentraisDasMetricas();
			// reinicia as médias para avaliar o novo conjunto de dados
			Utilidade.zerarMediasAuxiliares();
			// formata o arquivo de saída com uma linha abaixo
			Utilidade.adicionaLinhaArquivo();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	@Override
	public void run(String treino, String teste) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Padrao padrao) {
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
		
		run(padrao, null, null, null, tipoClassificador1, tipoClassificador2, tipoClassificador3, tipoClassificador4, tipoClassificador5, tipoClassificador6, tipoClassificador7, tipoSelecaoDinamica, tipoValidacao);
	}

	@Override
	public void run(Padrao padrao, Integer tipoClassificador1,
			Integer tipoClassificador2, Integer tipoClassificador3,
			Integer tipoClassificador4, Integer tipoClassificador5,
			Integer tipoClassificador6, Integer tipoClassificador7,
			TipoMetodoCombinacao tipoSelecaoDinamica) {
		// TODO Auto-generated method stub
		
	}
	
}
