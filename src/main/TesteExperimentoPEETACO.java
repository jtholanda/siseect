package main;

import method.Set_Ensemble_Dynamic;
import util.Constantes;
import util.TipoMetodoCombinacao;
import util.TipoMetricaAvaliacao;
import util.TipoValidacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import data.PadraoMiyazaki94;

public class TesteExperimentoPEETACO {


	public static void main(String []args){


		Utilidade.METRICA_AVALIACAO = Constantes.MAR;
		Utilidade.TIPO_METRICA_AVALIACAO = TipoMetricaAvaliacao.MAR;
	
		String experimento = "PEETACO";
		String avaliacao = "Teste";


	
		Classifier regressor1 = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ);
		Classifier regressor2 = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION);
		Classifier regressor3 = new WekaExperiment().createClassifier(WekaExperiment.M5P);


		runPeetacoDS(experimento, regressor1, regressor2, regressor3, avaliacao);

	
		Utilidade.QUANTIDADE_CLASSIFICADOR = 3;		
		runPeetacoDES(experimento, regressor1, regressor2, regressor3, avaliacao, TipoMetodoCombinacao.MEDIA);

	


	}

	private static void runPeetacoDS(String experimento, Classifier regressor1,
			Classifier regressor2, Classifier regressor3, String avaliacao) {

		Utilidade.QUANTIDADE_CLASSIFICADOR = 1;
		TipoMetodoCombinacao metodoDeCombinacao = TipoMetodoCombinacao.SD;

		Utilidade.inicializaListasMetricas();
		new Set_Ensemble_Dynamic(regressor1, regressor2, regressor3).run(new PadraoMiyazaki94(), null, null, null, null, null, null, WekaExperiment.KNN_4,  metodoDeCombinacao, TipoValidacao.LEAVE_ONE_OUT);
		Utilidade.gerarArquivosMassa(0, "SET_DYNAMIC_SELECTION" + experimento + "_" + metodoDeCombinacao + "_PEETACODS_" +  avaliacao);

	}


	private static void runPeetacoDES(String experimento, Classifier regressor1, Classifier regressor2, Classifier regressor3, String avaliacao, TipoMetodoCombinacao metodoDeCombinacao) {



		Utilidade.inicializaListasMetricas();
		new Set_Ensemble_Dynamic(regressor1, regressor2, regressor3).run(new PadraoMiyazaki94(), null, null, null, null, WekaExperiment.KNN_1, WekaExperiment.KNN_7, WekaExperiment.KNN_4,  metodoDeCombinacao, TipoValidacao.LEAVE_ONE_OUT);
		Utilidade.gerarArquivosMassa(0, "SET_ENSEMBLE_DYNAMIC_" + experimento + "_" + metodoDeCombinacao + "_PEETACODES_" + Utilidade.QUANTIDADE_CLASSIFICADOR + "CLASSIFICADORES" + avaliacao);

	}


}
