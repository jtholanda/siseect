package main;

import util.Constantes;
import util.TipoMetodoCombinacao;
import util.TipoMetricaAvaliacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;

public class TesteExperimentoValidationStaticEnsemble {
	
	
	public static void main(String []args){
		
		Utilidade.TIPO_METRICA_AVALIACAO = TipoMetricaAvaliacao.MAR;
		Utilidade.METRICA_AVALIACAO = Constantes.MAR;
		Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS = true;

		String experimento = "Experimento_Ensemble_Estático";

		System.out.println("Avaliação de validação:"+ Constantes.BASE_VALIDACAO);

		// Primeiro teste
		
		Classifier regressor1 = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ);
		Classifier regressor2 = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION);
		Classifier regressor3 = new WekaExperiment().createClassifier(WekaExperiment.M5P);

		TesteEnsembleEstatico.experimento = experimento + "_" + regressor1.getClass().getSimpleName() + "_" + regressor2.getClass().getSimpleName() + "_" + regressor3.getClass().getSimpleName();

		TesteEnsembleEstatico.regressor1 = regressor1;	
		TesteEnsembleEstatico.regressor2 = regressor2;	
		TesteEnsembleEstatico.regressor3 = regressor2;	

		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MEDIANA;
		TesteEnsembleEstatico.main(args);

		/*
		// Segundo teste
		
		regressor1 = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ);
		regressor2 = new WekaExperiment().createClassifier(WekaExperiment.M5R);

		TesteEnsembleEstatico.experimento = experimento + "_" + regressor1.getClass().getSimpleName() + "_" + regressor2.getClass().getSimpleName();

				
		TesteEnsembleEstatico.regressor1 = regressor1;	
		TesteEnsembleEstatico.regressor2 = regressor2;	

		
		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MEDIANA;
		TesteEnsembleEstatico.main(args);

		// Terceiro teste
		
		regressor1 = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION);
		regressor2 = new WekaExperiment().createClassifier(WekaExperiment.M5R);

		TesteEnsembleEstatico.experimento = experimento + "_" + regressor1.getClass().getSimpleName() + "_" + regressor2.getClass().getSimpleName();

				
		TesteEnsembleEstatico.regressor1 = regressor1;	
		TesteEnsembleEstatico.regressor2 = regressor2;	

		
		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MEDIANA;
		TesteEnsembleEstatico.main(args);

		// Quarto teste
		
		regressor1 = new WekaExperiment().createClassifier(WekaExperiment.M5P);
		regressor2 = new WekaExperiment().createClassifier(WekaExperiment.M5R);

		TesteEnsembleEstatico.experimento = experimento + "_" + regressor1.getClass().getSimpleName() + "_" + regressor2.getClass().getSimpleName();

				
		TesteEnsembleEstatico.regressor1 = regressor1;	
		TesteEnsembleEstatico.regressor2 = regressor2;	

		
		TesteEnsembleEstatico.metodoDeCombinacao = TipoMetodoCombinacao.MEDIANA;
		TesteEnsembleEstatico.main(args);

*/
		
		
	}
	

}
