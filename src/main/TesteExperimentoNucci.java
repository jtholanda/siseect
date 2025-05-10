package main;

import method.Set_Ensemble_Dynamic;
import util.Constantes;
import util.TipoMetodoCombinacao;
import util.TipoMetricaAvaliacao;
import util.TipoValidacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import data.Padrao;
import data.PadraoISBSG5;
import data.PadraoMiyazaki94;

public class TesteExperimentoNucci {
	
	
	public static Classifier regressor1, regressor2, regressor3;
	public static TipoMetodoCombinacao metodoDeCombinacao;
	public static String experimento ="";
	public static String avaliacao = "";
	public static Padrao padrao;
	
	public static void main(String []args){
	
		experimento = "Experimento_RF";
		
		if(Constantes.BASE_VALIDACAO){
			avaliacao = "Validação";
		}else{
			avaliacao = "Teste";
		}
		
		Utilidade.METRICA_AVALIACAO = Constantes.MAR;
		Utilidade.TIPO_METRICA_AVALIACAO = TipoMetricaAvaliacao.MAR;
		
		Utilidade.QUANTIDADE_REGRESSOR = 3;
		Utilidade.QUANTIDADE_CLASSIFICADOR = 1;

		Utilidade.ATIVA_PROBABILIDADES  = true;
		Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR  = 1;

		metodoDeCombinacao = TipoMetodoCombinacao.SD;
		
		System.out.println("Avaliação de validação:"+ Constantes.BASE_VALIDACAO);
		System.out.println("Probabilidades Ativadas:"+ Utilidade.ATIVA_PROBABILIDADES);
		

		padrao = new PadraoISBSG5();

		
		Classifier regressor1 = new WekaExperiment().createClassifier(padrao.getR1());
		Classifier regressor2 = new WekaExperiment().createClassifier(padrao.getR2());
		Classifier regressor3 = new WekaExperiment().createClassifier(padrao.getR3());




		
		Utilidade.inicializaListasMetricas();
		

		new Set_Ensemble_Dynamic(regressor1, regressor2, regressor3).run(padrao, null, null, null, null, null, null, WekaExperiment.RANDOM_FOREST,  metodoDeCombinacao, padrao.getTipoValidacao());


		Utilidade.gerarArquivosMassa(0, "SET_DYNAMIC_SELECTION_RANDOM_FOREST_" + padrao.getNomePadrao() + "_" + metodoDeCombinacao + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" + avaliacao);

		
		
	}
	

	

}
