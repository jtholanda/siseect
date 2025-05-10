package main;

import util.Constantes;
import util.TipoMetodoCombinacao;
import util.TipoMetricaAvaliacao;
import util.Utilidade;
import util.WekaExperiment;
import data.PadraoDesharnais;
import data.PadraoKitchenham;
import data.PadraoMaxwell;
import data.PadraoMiyazaki94;

public class TesteIndividualClassificadores {
	

	public static void main(String []args){
		
		Utilidade.QUANTIDADE_REGRESSOR = 3;
		Utilidade.QUANTIDADE_CLASSIFICADOR = 1;		
		Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR = 20;
		Utilidade.ATIVA_PROBABILIDADES = true;

		String experimento = "Experimento Classificadores Individuais";
		String avaliacao;

		if(Constantes.BASE_VALIDACAO){
			Utilidade.AVALIACAO = "Validação";
		}else{
			Utilidade.AVALIACAO = "Teste";
		}
		avaliacao = Utilidade.AVALIACAO;

		System.out.println("Avaliação de validação dos métodos:"+ Constantes.BASE_VALIDACAO + " - " + avaliacao + " " + experimento);
		System.out.println("Treinamento do classificador com fold:"+ Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR);

		TesteSetEnsembleDynamicValidacao.padrao = new PadraoMiyazaki94();
		
		TesteSetEnsembleDynamicValidacao.regressor1 = new WekaExperiment().createClassifier(TesteSetEnsembleDynamicValidacao.padrao.getR1());
		TesteSetEnsembleDynamicValidacao.regressor2 = new WekaExperiment().createClassifier(TesteSetEnsembleDynamicValidacao.padrao.getR2());
		TesteSetEnsembleDynamicValidacao.regressor3 = new WekaExperiment().createClassifier(TesteSetEnsembleDynamicValidacao.padrao.getR3());
		TesteSetEnsembleDynamicValidacao.regressor4 = null;
		TesteSetEnsembleDynamicValidacao.regressor5 = null;

		
		TesteSetEnsembleDynamicValidacao.experimento = "ClassificadoresIndividuais"+ TesteSetEnsembleDynamicValidacao.padrao.getNomePadrao();
		TesteSetEnsembleDynamicValidacao.metodoDeCombinacao = TipoMetodoCombinacao.SD;
		
		Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS = true;
		Utilidade.TIPO_METRICA_AVALIACAO = TipoMetricaAvaliacao.MAR;
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.ADABOOST);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.ADABOOST_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.ADABOOST_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.ADABOOST_3);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.ADABOOST_4);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.ADABOOST_5);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.DECISION_TABLE);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.DECISION_TABLE_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.DECISION_TABLE_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.DECISION_TABLE_3);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.DECISION_TABLE_4);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.DECISION_TABLE_5);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.NAIVE_BAYES);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.NAIVE_BAYES_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.NAIVE_BAYES_2);	
		TesteSetEnsembleDynamicValidacao.main(args);

		
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.J48);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.J48_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.LOGISTIC_REGRESSION);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_3);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_4);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_5);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_6);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_7);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_8);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KNN_9);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.MLP_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.MLP_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.MLP_3);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.MLP_4);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.MLP_5);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BAGGING);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BAGGING_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BAGGING_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BAGGING_3);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BAGGING_4);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BAGGING_5);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.KSTAR);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.DECISION_STUMP);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.RANDOM_FOREST);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.RANDOM_FOREST_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.REP_TREE);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.REP_TREE_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.REP_TREE_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.REP_TREE_3);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.REP_TREE_4);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.REP_TREE_5);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.ONER);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.RBF_NETWORK);
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.RBF_NETWORK_1);
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.SVM);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.SVM_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.SVM_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.SVM_3);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.SVM_4);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.SVM_5);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.LOCALLY_WEIGHTED_LEARNING);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.LOCALLY_WEIGHTED_LEARNING_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BEST_FIRST_TREE);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BEST_FIRST_TREE_1);	
		TesteSetEnsembleDynamicValidacao.main(args);
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BEST_FIRST_TREE_2);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.BAYES_NET);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.J_RIP);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.LMT);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
		
		TesteSetEnsembleDynamicValidacao.classificador = (WekaExperiment.LAD_TREE);	
		TesteSetEnsembleDynamicValidacao.main(args);
		
}

}
