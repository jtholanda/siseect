package main;

import util.Constantes;
import util.WekaExperiment;
import data.PadraoChina;
import data.PadraoCocomo81;
import data.PadraoCocomoNasaV1;
import data.PadraoCocomoNasaV2;
import data.PadraoDesharnais;
import data.PadraoKitchenham;
import data.PadraoMaxwell;
import data.PadraoMiyazaki94;
import data.PadraoTaxaEvasaoMedio;
import data.PadraoTaxaReprovacaoMedio;

public class TesteRegressoresIndividuais {
	
	
	public static void main(String []args){
		
		System.out.println("Avaliação de validação:"+ Constantes.BASE_VALIDACAO);

		
		System.out.println("Validação Modelos Individuais");
		
		TesteIndividualGeral.padrao = new PadraoMiyazaki94();
		
/*		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.CONJUNCTIVE_RULES);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.CONJUNCTIVE_RULES_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.CONJUNCTIVE_RULES_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.CONJUNCTIVE_RULES_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.CONJUNCTIVE_RULES_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.CONJUNCTIVE_RULES_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.CONJUNCTIVE_RULES_6);	
		TesteIndividualGeral.idRegressor = 6;
		TesteIndividualGeral.main(args);

		System.out.println(TesteIndividualGeral.regressor);
		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.DECISION_TABLE);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.DECISION_TABLE_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.DECISION_TABLE_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.DECISION_TABLE_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.DECISION_TABLE_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.DECISION_TABLE_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);

		
		System.out.println(TesteIndividualGeral.regressor);
		
		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.GP);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.GP_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.GP_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.GP_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.GP_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.GP_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);


		System.out.println(TesteIndividualGeral.regressor);

		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_6);	
		TesteIndividualGeral.idRegressor = 6;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_7);	
		TesteIndividualGeral.idRegressor = 7;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_8);	
		TesteIndividualGeral.idRegressor = 8;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.KNN_9);	
		TesteIndividualGeral.idRegressor = 9;
		TesteIndividualGeral.main(args);
		

		System.out.println(TesteIndividualGeral.regressor);

		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);


		System.out.println(TesteIndividualGeral.regressor);

		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LINEAR_REGRESSION);
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LINEAR_REGRESSION_1);
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);


		System.out.println(TesteIndividualGeral.regressor);
		
		
		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LOCALLY_WEIGHTED_LEARNING);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.LOCALLY_WEIGHTED_LEARNING_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);


		System.out.println(TesteIndividualGeral.regressor);

		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5P);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5P_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5P_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5P_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5P_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5P_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);


		System.out.println(TesteIndividualGeral.regressor);
		
		
		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5R);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5R_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5R_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5R_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5R_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.M5R_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);


		System.out.println(TesteIndividualGeral.regressor);

		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.MLP);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.MLP_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.MLP_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.MLP_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.MLP_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.MLP_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);
		

		System.out.println(TesteIndividualGeral.regressor);
		
		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.RBF_NETWORK);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.RBF_NETWORK);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		

		System.out.println(TesteIndividualGeral.regressor);
		
		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.REP_TREE);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.REP_TREE_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.REP_TREE_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.REP_TREE_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.REP_TREE_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.REP_TREE_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);

		
		System.out.println(TesteIndividualGeral.regressor);

		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION);	
		TesteIndividualGeral.idRegressor = 0;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION_1);	
		TesteIndividualGeral.idRegressor = 1;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION_2);	
		TesteIndividualGeral.idRegressor = 2;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION_3);	
		TesteIndividualGeral.idRegressor = 3;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION_4);	
		TesteIndividualGeral.idRegressor = 4;
		TesteIndividualGeral.main(args);
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION_5);	
		TesteIndividualGeral.idRegressor = 5;
		TesteIndividualGeral.main(args);

		
		System.out.println(TesteIndividualGeral.regressor);
*/
		
		TesteIndividualGeral.regressor = new WekaExperiment().createClassifier(WekaExperiment.ZEROR);	
		TesteIndividualGeral.main(args);
		
		System.out.println(TesteIndividualGeral.regressor);

		
	}

	
}
