package main;

import java.util.ArrayList;
import java.util.Date;

import method.Knora_DCS_Set_Ensemble_Dynamic;
import method.Set_Ensemble_Dynamic;
import util.Constantes;
import util.TipoMetodoCombinacao;
import util.TipoMetricaAvaliacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Stacking;
import data.Padrao;
import data.PadraoCocomoNasaV2;
import data.PadraoDesharnais;
import data.PadraoISBSG5;
import data.PadraoKitchenham;
import data.PadraoMaxwell;
import data.PadraoMiyazaki94;

public class TesteExperimento {
	
	
	static Padrao padrao;
	ArrayList<Integer> arrayConstantesClassificadores = new ArrayList<Integer>();
	

	
	public static void main(String []args){
		

		Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS = true;
		Utilidade.METRICA_AVALIACAO = Constantes.MAR;
		Utilidade.TIPO_METRICA_AVALIACAO = TipoMetricaAvaliacao.MAR;
		Utilidade.QUANTIDADE_REGRESSOR = 3;
		Utilidade.ATIVA_PROBABILIDADES = false;
		Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR = 1;
	

		String experimento = "Experimento PEETACO";
		String avaliacao;

		if(Constantes.BASE_VALIDACAO){
			Utilidade.AVALIACAO = "Validação";
		}else{
			Utilidade.AVALIACAO = "Teste";
		}
		avaliacao = Utilidade.AVALIACAO;
		
		
		System.out.println("Avaliação de validação dos métodos:"+ Constantes.BASE_VALIDACAO + " - " + avaliacao + " " + experimento);


		
		
		padrao = new PadraoISBSG5();

		Classifier regressor1 = new WekaExperiment().createClassifier(padrao.getR1());
		Classifier regressor2 = new WekaExperiment().createClassifier(padrao.getR2());
		Classifier regressor3 = new WekaExperiment().createClassifier(padrao.getR3());
		Classifier regressor4 = null;
		Classifier regressor5 = null;

		
		
		//runMetodosConcorrentes(args, regressor1, regressor2, regressor3);
		
		
		runPEETACO(experimento, avaliacao, regressor1, regressor2, regressor3);

		
	

	
	}

	private static void runPEETACO(String experimento, String avaliacao,
			Classifier regressor1, Classifier regressor2, Classifier regressor3) {
		
		if(Utilidade.ATIVA_PROBABILIDADES){

			Utilidade.QUANTIDADE_CLASSIFICADOR = 1;
			System.out.println("Teste PEETACO-DES 1 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, null, null, null, padrao.getClassificadorComProbabilidade1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 2;		
			System.out.println("Teste PEETACO-DES 2 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, null, null, padrao.getClassificadorComProbabilidade2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificadorComProbabilidade1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 3;		
			System.out.println("Teste PEETACO-DES 3 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, null, padrao.getClassificadorComProbabilidade3(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificadorComProbabilidade2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificadorComProbabilidade1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 4;		
			System.out.println("Teste PEETACO-DES 4 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, padrao.getClassificadorComProbabilidade4(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificadorComProbabilidade3(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificadorComProbabilidade2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificadorComProbabilidade1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 5;		
			System.out.println("Teste PEETACO-DES 5 classificadores em "  + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, padrao.getClassificadorComProbabilidade5(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificadorComProbabilidade4(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificadorComProbabilidade3(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificadorComProbabilidade2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificadorComProbabilidade1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);
	
		}else{
			
			Utilidade.QUANTIDADE_CLASSIFICADOR = 1;
			System.out.println("Teste PEETACO-DES 1 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, null, null, null, padrao.getClassificador1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 2;		
			System.out.println("Teste PEETACO-DES 2 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, null, null, padrao.getClassificador2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificador1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 3;		
			System.out.println("Teste PEETACO-DES 3 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, null, padrao.getClassificador3(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificador2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificador1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 4;		
			System.out.println("Teste PEETACO-DES 4 classificadores em " + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, null, padrao.getClassificador4(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificador3(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificador2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificador1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

			Utilidade.QUANTIDADE_CLASSIFICADOR = 5;		
			System.out.println("Teste PEETACO-DES 5 classificadores em "  + padrao.getNomePadrao() + " - " + (new Date().getTime()/1000)/60);
			runPeetacoDES(experimento, null, null, padrao.getClassificador5(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), padrao.getClassificador4(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificador3(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificador2(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR),  padrao.getClassificador1(Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR), regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

		}
		
	}

	private static void runMetodosConcorrentes(String[] args, Classifier regressor1, Classifier regressor2, Classifier regressor3) {
		
		// Bagging
		
		Bagging bagging1 = new Bagging();
		Bagging bagging2 = new Bagging();
		//bagging2.setNumIterations(5);
		//bagging2.setBagSizePercent(75);
		Bagging bagging3 = new Bagging();
		
		AdditiveRegression additiveRegression1 = new AdditiveRegression();
		AdditiveRegression additiveRegression2 = new AdditiveRegression();
		//additiveRegression2.setNumIterations(5);
		AdditiveRegression additiveRegression3 = new AdditiveRegression();

		Stacking stacking = new Stacking();
		stacking.setMetaClassifier(new SMOreg());
		try {
			stacking.setNumFolds(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Classifier[] classifiers = new Classifier[3];
		classifiers[0] = regressor1;
		classifiers[1] = regressor2;
		classifiers[2] = regressor3;
		stacking.setClassifiers(classifiers);

		
		TesteIndividualGeral.padrao = padrao;
		
		TesteIndividualGeral.regressor = regressor1;	
		TesteIndividualGeral.main(args);

		TesteIndividualGeral.regressor = regressor2;	
		TesteIndividualGeral.main(args);

		TesteIndividualGeral.regressor = regressor3;	
		TesteIndividualGeral.main(args);
		
		TesteEnsembleEstatico.padrao = padrao;
		
		TesteEnsembleEstatico.regressor1 = regressor1;	
		TesteEnsembleEstatico.regressor2 = regressor2;	
		TesteEnsembleEstatico.regressor3 = regressor3;	
		
		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MEDIA;
		TesteEnsembleEstatico.main(args);

		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MEDIANA;
		TesteEnsembleEstatico.main(args);

		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MEDIA_DAS_PONTAS;
		TesteEnsembleEstatico.main(args);

		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MAXIMO;
		TesteEnsembleEstatico.main(args);

		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MINIMO;
		TesteEnsembleEstatico.main(args);
		
		
		bagging1.setClassifier(regressor1);
		TesteIndividualGeral.regressor = bagging1;	
		TesteIndividualGeral.main(args);

		bagging2.setClassifier(regressor2);
		TesteIndividualGeral.regressor = bagging2;	
		TesteIndividualGeral.main(args);

		bagging3.setClassifier(regressor3);
		TesteIndividualGeral.regressor = bagging3;	
		TesteIndividualGeral.main(args);

		additiveRegression1.setClassifier(regressor1);
		TesteIndividualGeral.regressor = additiveRegression1;	
		TesteIndividualGeral.main(args);

		additiveRegression2.setClassifier(regressor2);
		TesteIndividualGeral.regressor = additiveRegression2;	
		TesteIndividualGeral.main(args);

		additiveRegression3.setClassifier(regressor3);
		TesteIndividualGeral.regressor = additiveRegression3;	
		TesteIndividualGeral.main(args);
	
		TesteIndividualGeral.regressor = stacking;	
		TesteIndividualGeral.main(args);
		
		TesteKnoraDCSSetEnsembleDynamic.padrao = padrao;
		
		TesteKnoraDCSSetEnsembleDynamic.regressor1 = regressor1;	
		TesteKnoraDCSSetEnsembleDynamic.regressor2 = regressor2;	
		TesteKnoraDCSSetEnsembleDynamic.regressor3 = regressor3;	


		Knora_DCS_Set_Ensemble_Dynamic.K_SELECAO_DINAMICA = 11;
		Knora_DCS_Set_Ensemble_Dynamic.KNORA = 0.50;


		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LA;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LAW;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_E;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_U;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		
		Knora_DCS_Set_Ensemble_Dynamic.K_SELECAO_DINAMICA = 9;
		Knora_DCS_Set_Ensemble_Dynamic.KNORA = 0.50;


		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LA;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LAW;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_E;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_U;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		
		Knora_DCS_Set_Ensemble_Dynamic.K_SELECAO_DINAMICA = 7;
		Knora_DCS_Set_Ensemble_Dynamic.KNORA = 0.50;


		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LA;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LAW;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_E;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_U;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		
		Knora_DCS_Set_Ensemble_Dynamic.K_SELECAO_DINAMICA = 5;
		Knora_DCS_Set_Ensemble_Dynamic.KNORA = 0.50;


		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LA;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LAW;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_E;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_U;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		
		Knora_DCS_Set_Ensemble_Dynamic.K_SELECAO_DINAMICA = 3;
		Knora_DCS_Set_Ensemble_Dynamic.KNORA = 0.50;


		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LA;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.DCS_LAW;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_E;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		TesteKnoraDCSSetEnsembleDynamic.metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_U;
		TesteKnoraDCSSetEnsembleDynamic.main(args);

		
		
		Utilidade.QUANTIDADE_REGRESSOR = 3;
		Utilidade.QUANTIDADE_CLASSIFICADOR = 1;
		
		TesteExperimentoNucci.padrao = padrao;
		TesteExperimentoNucci.regressor1 = regressor1;
		TesteExperimentoNucci.regressor2 = regressor2;
		TesteExperimentoNucci.regressor3 = regressor3;

		TesteExperimentoNucci.main(args);
	}
	
	private static void runPeetacoDS(String experimento, Integer constanteClassificador, Classifier regressor1,
			Classifier regressor2, Classifier regressor3, String avaliacao) {
		
		long inicio = System.currentTimeMillis();
		String nomeExperimento;
		TipoMetodoCombinacao metodoDeCombinacao = TipoMetodoCombinacao.SD;
		
		Utilidade.inicializaListasMetricas();

		nomeExperimento =  "SIMPLE_DYNAMIC_SELECTION" + experimento + "_" + metodoDeCombinacao + "_PEETACODS_" + padrao.getNomePadrao() + "_" + Utilidade.QUANTIDADE_CLASSIFICADOR + "_CLASSIFICADORE_" + constanteClassificador + "_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_CLASSIFICADOR_" + avaliacao;		
		System.out.println("----- " + nomeExperimento + " ------ " );

		new Set_Ensemble_Dynamic(regressor1, regressor2, regressor3).run(padrao, null, null, null, null, null, null, constanteClassificador,  metodoDeCombinacao, padrao.getTipoValidacao());

		long tempo = System.currentTimeMillis() - inicio;
		
		Utilidade.gerarArquivosMassa(tempo, nomeExperimento);
	}


	private static void runPeetacoDES(String experimento, Integer tipoClassificador1, Integer tipoClassificador2, Integer tipoClassificador3, Integer tipoClassificador4, Integer tipoClassificador5, Integer tipoClassificador6, Integer tipoClassificador7, Classifier regressor1, Classifier regressor2, Classifier regressor3, String avaliacao, TipoMetodoCombinacao metodoDeCombinacao) {
		
		long inicio = System.currentTimeMillis();

		
		Utilidade.inicializaListasMetricas();
		String nomeExperimento;		
	
		nomeExperimento = "SET_ENSEMBLE_DYNAMIC_" + experimento + "_" + metodoDeCombinacao + "_PEETACODES_" + padrao.getNomePadrao() + "_" + Utilidade.QUANTIDADE_CLASSIFICADOR + "_CLASSIFICADORES_" + tipoClassificador1 + "_" + tipoClassificador2 + "_" + tipoClassificador3 + "_" + tipoClassificador4 + "_" + tipoClassificador5 + "_" + tipoClassificador6 + "_" + tipoClassificador7 + "_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_CLASSIFICADOR_" + avaliacao;
		System.out.println("----- " + nomeExperimento + " ------ ");

		new Set_Ensemble_Dynamic(regressor1, regressor2, regressor3).run(padrao, tipoClassificador1, tipoClassificador2, tipoClassificador3, tipoClassificador4, tipoClassificador5, tipoClassificador6, tipoClassificador7, metodoDeCombinacao, padrao.getTipoValidacao());

		long tempo = System.currentTimeMillis() - inicio;
		
		Utilidade.gerarArquivosMassa(tempo, nomeExperimento);
	}

	private static void runPeetacoDES(String experimento, Integer tipoClassificador1, Integer tipoClassificador2, Integer tipoClassificador3, Integer tipoClassificador4, Integer tipoClassificador5, Integer tipoClassificador6, Integer tipoClassificador7, Integer tipoClassificador8, Integer tipoClassificador9, Integer tipoClassificador10, Classifier regressor1, Classifier regressor2, Classifier regressor3, String avaliacao, TipoMetodoCombinacao metodoDeCombinacao) {
		
		long inicio = System.currentTimeMillis();

		
		Utilidade.inicializaListasMetricas();
		String nomeExperimento;		
	
		nomeExperimento = "SET_ENSEMBLE_DYNAMIC_" + experimento + "_" + metodoDeCombinacao + "_PEETACODES_" + padrao.getNomePadrao() + "_" + Utilidade.QUANTIDADE_CLASSIFICADOR + "_CLASSIFICADORES_" + tipoClassificador1 + "_" + tipoClassificador2 + "_" + tipoClassificador3 + "_" + tipoClassificador4 + "_" + tipoClassificador5 + "_" + tipoClassificador6 + "_" + tipoClassificador7 + "_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_CLASSIFICADOR_" + avaliacao;
		System.out.println("----- " + nomeExperimento + " ------ ");

		new Set_Ensemble_Dynamic(regressor1, regressor2, regressor3).run(padrao, tipoClassificador1, tipoClassificador2, tipoClassificador3, tipoClassificador4, tipoClassificador5, tipoClassificador6, tipoClassificador7, tipoClassificador8, tipoClassificador9, tipoClassificador10, metodoDeCombinacao, padrao.getTipoValidacao());

		long tempo = System.currentTimeMillis() - inicio;
		
		Utilidade.gerarArquivosMassa(tempo, nomeExperimento);
	}


}
