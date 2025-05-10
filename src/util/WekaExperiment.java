package util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LeastMedSq;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.*;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.MultiClassClassifier;
import weka.classifiers.meta.Stacking;
import weka.classifiers.rules.ConjunctiveRule;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.LADTree;
import weka.classifiers.trees.LMT;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.attributeSelection.*;
import weka.classifiers.functions.supportVector.Puk;
import weka.core.neighboursearch.*;
import weka.core.ManhattanDistance;
/**
 * Classe que realiza experimento de treino/teste automaticamente, utilizando a
 * API Java do Weka. Trabalha, por enquanto, com classificação binária.
 * 
 * @author Alex
 * @version 1.3: metodos createClassifier(), novos classificadores [1.2]
 *          Alteracoes no método removeAttribute(), acrescimo do método
 *          saveDecisionTree(). [1.1] metodo loadARFFFile() sobrecarregado.
 *          Data: 20/08/2013
 */
public class WekaExperiment {
	// The training set object
	private Instances trainingData;
	// The training set object copy, to recover when necessary
	// private Instances trainingCopy;
	// The test set object
	private Instances testData;
	// Classifier
	private Classifier classifier;
	// Avaliador do treino ou teste
	public Evaluation eval;
	// Número de folds (para cross-validation)
	private int folds;
	// Número de seed (para cross-validation);
	private int seed;
	// Sinaliza se a última avaliação foi no arquivo de treino (true) ou de
	// teste (false)
	private boolean isTrainEvaluated = false;

	// Accuracy Matrix
	double accuracy[][];

	//
	private boolean trainingFileLoaded = false;
	private boolean testFileLoaded = false;
	public Classifier metaClassificador = new REPTree();

	public static final int TRAINING_FILE = 0;
	public static final int TEST_FILE = 1;

	public static final int J48 = 0;
	public static final int J48_1 = -1;
	
	public static final int SVM = 1;
	public static final int SVM_1 = 11;
	public static final int SVM_2 = 12;
	public static final int SVM_3 = 13;
	public static final int SVM_4 = 14;
	public static final int SVM_5 = 15;

	
	public static final int NAIVE_BAYES = 2;
	public static final int NAIVE_BAYES_1 = 201;
	public static final int NAIVE_BAYES_2 = 202;
	
	public static final int RANDOM_FOREST = 3;
	public static final int RANDOM_FOREST_1 = 31;
	
	public static final int ADABOOST = 4;
	public static final int ADABOOST_1 = 41;
	public static final int ADABOOST_2 = 42;
	public static final int ADABOOST_3 = 43;
	public static final int ADABOOST_4 = 44;
	public static final int ADABOOST_5 = 45;

	public static final int LOGISTIC_REGRESSION = 5;
	
	public static final int ONER = 6;
	
	public static final int KNN = 7;
	public static final int KNN_1 = 71;
	public static final int KNN_2 = 72;
	public static final int KNN_3 = 73;
	public static final int KNN_4 = 74;
	public static final int KNN_5 = 75;
	public static final int KNN_6 = 76;
	public static final int KNN_7 = 77;
	public static final int KNN_8 = 78;
	public static final int KNN_9 = 79;
	
	public static final int MLP = 120;
	public static final int MLP_1 = 121;
	public static final int MLP_2 = 122;
	public static final int MLP_3 = 123;
	public static final int MLP_4 = 124;
	public static final int MLP_5 = 125;
	
	public static final int ZEROR = 17;
	
	public static final int STACKING = 18;
	
	public static final int LINEAR_REGRESSION = 19;
	public static final int LINEAR_REGRESSION_1 = 191;
	
	public static final int GP = 200;
	public static final int GP_1 = 2001;
	public static final int GP_2 = 2002;
	public static final int GP_3 = 2003;
	public static final int GP_4 = 2004;
	public static final int GP_5 = 2005;
	
	public static final int M5R = 210;
	public static final int M5R_1 = 211;
	public static final int M5R_2 = 212;
	public static final int M5R_3 = 213;
	public static final int M5R_4 = 214;
	public static final int M5R_5 = 215;
	
	public static final int KSTAR = 22;
	
	public static final int ADDITIVE_REGRESSION = 23;
	
	public static final int SUPPORT_VECTOR_REGRESSION = 24;
	public static final int SUPPORT_VECTOR_REGRESSION_1 = 241;
	public static final int SUPPORT_VECTOR_REGRESSION_2 = 242;
	public static final int SUPPORT_VECTOR_REGRESSION_3 = 243;
	public static final int SUPPORT_VECTOR_REGRESSION_4 = 244;
	public static final int SUPPORT_VECTOR_REGRESSION_5 = 245;
	
	public static final int DECISION_TABLE = 25;
	public static final int DECISION_TABLE_1 = 251;
	public static final int DECISION_TABLE_2 = 252;
	public static final int DECISION_TABLE_3 = 253;
	public static final int DECISION_TABLE_4 = 254;
	public static final int DECISION_TABLE_5 = 255;
	
	public static final int REP_TREE = 26;
	public static final int REP_TREE_1 = 261;
	public static final int REP_TREE_2 = 262;
	public static final int REP_TREE_3 = 263;
	public static final int REP_TREE_4 = 264;
	public static final int REP_TREE_5 = 265;
	
	public static final int CONJUNCTIVE_RULES = 27;
	public static final int CONJUNCTIVE_RULES_1 = 271;
	public static final int CONJUNCTIVE_RULES_2 = 272;
	public static final int CONJUNCTIVE_RULES_3 = 273;
	public static final int CONJUNCTIVE_RULES_4 = 274;
	public static final int CONJUNCTIVE_RULES_5 = 275;
	public static final int CONJUNCTIVE_RULES_6 = 276;
	
	public static final int RBF_NETWORK = 28;
	public static final int RBF_NETWORK_1 = 281;
	
	public static final int BAGGING = 29;
	public static final int BAGGING_1 = 291;
	public static final int BAGGING_2 = 292;
	public static final int BAGGING_3 = 293;
	public static final int BAGGING_4 = 294;
	public static final int BAGGING_5 = 295;
	public static final int BAGGING_KNN_1 = 2971;
	public static final int BAGGING_KNN_2 = 2972;
	public static final int BAGGING_KNN_3 = 2973;
	public static final int BAGGING_MLP_1 = 29121;
	public static final int BAGGING_MLP_2 = 29122;
	public static final int BAGGING_MLP_3 = 29123;
	public static final int BAGGING_MLP_4 = 29124;
	public static final int BAGGING_MLP_5 = 29125;
	public static final int BAGGING_KSTAR = 2922;
	public static final int BAGGING_BAYES_NET = 2935;
	
	public static final int M5P = 30;
	public static final int M5P_1 = 301;
	public static final int M5P_2 = 302;
	public static final int M5P_3 = 303;
	public static final int M5P_4 = 304;
	public static final int M5P_5 = 305;
	
	public static final int DECISION_STUMP = 310;
	
	public static final int LEAST_MED_SQ = 320;
	public static final int LEAST_MED_SQ_1 = 321;
	public static final int LEAST_MED_SQ_2 = 322;
	public static final int LEAST_MED_SQ_3 = 323;
	public static final int LEAST_MED_SQ_4 = 324;
	public static final int LEAST_MED_SQ_5 = 325;
	
	public static final int LOCALLY_WEIGHTED_LEARNING = 33;
	public static final int LOCALLY_WEIGHTED_LEARNING_1 = 331;
	
	public static final int BEST_FIRST_TREE = 34;
	public static final int BEST_FIRST_TREE_1 = 341;
	public static final int BEST_FIRST_TREE_2 = 342;
	
	public static final int BAYES_NET = 35;
	
	public static final int J_RIP = 36;
	
	public static final int LMT = 37;
	
	public static final int LAD_TREE = 38;



	public WekaExperiment() {
		// Create an empty training set with a initial set capacity of 10
		trainingData = null;
		classifier = null;
		eval = null;
		accuracy = new double[2][6];
	}


	
	/**
	 * Treina o classificador de acordo com as instäncias de treinamento
	 * carregadas.
	 * 
	 * @param theClassifier
	 *            O classificador utilizado para construção do modelo
	 * @throws Exception
	 */
	public void buildClassifier(int theClassifier) throws Exception {
		if (!trainingFileLoaded)
			return;
		// classifier = (Classifier)new J48();
		if (!(classifier instanceof MultiClassClassifier))
			classifier = createClassifier(theClassifier);
		classifier.buildClassifier(trainingData);

	}

	/**
	 * Treina o classificador para as instäncias de treinamento carregadas. Este
	 * método só deve ser chamado se o classificador for MultiClasse.
	 * 
	 * @throws Exception
	 */
	public void buildClassifier() throws Exception {
		if (!(classifier instanceof MultiClassClassifier))
			System.err
					.println("O problema de classificação não foi definido como multiclasse.");
		else
			classifier.buildClassifier(trainingData);
	}

	/**
	 * Cria um classificador de acordo com o parâmetro especificado. Se
	 * "theClassifier" não for um classificador válido, devolve o defaulta: J48
	 * 
	 * @param theClassifier
	 *            Constante de classe referente ao classificador desejado.
	 * @return Uma instancia do classificador.
	 */
	public Classifier createClassifier(int theClassifier) {

		if (theClassifier == J48) {
			return new J48();
		} else if (theClassifier == J48_1) {
			J48 j48 = new J48();
			j48.setUnpruned(true);
			return j48;
		} else if (theClassifier == NAIVE_BAYES) {
			return new NaiveBayes();
		} else if (theClassifier == NAIVE_BAYES_1) {
			NaiveBayes nb = new NaiveBayes();
			nb.setUseKernelEstimator(true);
			return nb;
		} else if (theClassifier == NAIVE_BAYES_2) {
			NaiveBayes nb = new NaiveBayes();
			nb.setDisplayModelInOldFormat(true);
			nb.setUseKernelEstimator(true);
			return nb;
		} else if (theClassifier == RANDOM_FOREST) {
			return new RandomForest();
		} else if (theClassifier == RANDOM_FOREST_1) {
			RandomForest randomForest = new RandomForest();
			randomForest.setNumTrees(200);
			return randomForest;
		} else if (theClassifier == ADABOOST) {
			AdaBoostM1 adaboost = new AdaBoostM1();
			adaboost.setClassifier(new DecisionStump());
			return adaboost;
		} else if (theClassifier == ADABOOST_1) {
			AdaBoostM1 adaboost = new AdaBoostM1();
			adaboost.setClassifier(new REPTree());
			return adaboost;
		} else if (theClassifier == ADABOOST_2) {
			AdaBoostM1 adaboost = new AdaBoostM1();
			adaboost.setClassifier(new J48());
			return adaboost;
		} else if (theClassifier == ADABOOST_3) {
			AdaBoostM1 adaboost = new AdaBoostM1();
			adaboost.setClassifier(new LADTree());
			return adaboost;
		} else if (theClassifier == ADABOOST_4) {
			AdaBoostM1 adaboost = new AdaBoostM1();
			adaboost.setClassifier(new LMT());
			return adaboost;
		} else if (theClassifier == ADABOOST_5) {
			AdaBoostM1 adaboost = new AdaBoostM1();
			adaboost.setClassifier(new BFTree());
			return adaboost;
		} else if (theClassifier == LOGISTIC_REGRESSION) {
			return new Logistic();
		} else if (theClassifier == ONER){
			return new OneR();
		} else if (theClassifier == SVM) {
			LibSVM libSVM = new LibSVM();
			libSVM.setNormalize(true);
			return libSVM;
		}  else if (theClassifier == SVM_1) {
			LibSVM libSVM = new LibSVM();
			libSVM.setNormalize(true);
			libSVM.setCost(0.5);
			return libSVM;
		} else if (theClassifier == SVM_2) {
			LibSVM libSVM = new LibSVM();
			libSVM.setNormalize(true);
			libSVM.setCost(0.1);
			return libSVM;
		} else if (theClassifier == SVM_3) {
			LibSVM libSVM = new LibSVM();
			libSVM.setNormalize(true);
			libSVM.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_SIGMOID, LibSVM.TAGS_KERNELTYPE));
			return libSVM;
		}  else if (theClassifier == SVM_4) {
			LibSVM libSVM = new LibSVM();
			libSVM.setNormalize(true);
			libSVM.setCost(0.5);
			libSVM.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_SIGMOID, LibSVM.TAGS_KERNELTYPE));
			return libSVM;
		} else if (theClassifier == SVM_5) {
			LibSVM libSVM = new LibSVM();
			libSVM.setNormalize(true);
			libSVM.setCost(0.1);
			libSVM.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_SIGMOID, LibSVM.TAGS_KERNELTYPE));
			return libSVM;
		} else if (theClassifier == KNN) {
			return new IBk();
		} else if (theClassifier == KNN_1) {
			IBk ibk = new IBk(3);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_NONE, IBk.TAGS_WEIGHTING));
			return ibk;
		} else if (theClassifier == KNN_2) {
			IBk ibk = new IBk(3);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_INVERSE, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == KNN_3) {
			IBk ibk = new IBk(3);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_SIMILARITY, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == KNN_4) {
			IBk ibk = new IBk(5);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_NONE, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == KNN_5) {
			IBk ibk = new IBk(5);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_INVERSE, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == KNN_6) {
			IBk ibk = new IBk(5);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_SIMILARITY, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == KNN_7) {
			IBk ibk = new IBk(7);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_NONE, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == KNN_8) {
			IBk ibk = new IBk(7);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_INVERSE, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == KNN_9) {
			IBk ibk = new IBk(7);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_SIMILARITY, IBk.TAGS_WEIGHTING));
			return ibk;
		}else if (theClassifier == MLP) {
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			return mlp;
		}else if (theClassifier == MLP_1) {
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setLearningRate(0.1);
			return mlp;
		} else if (theClassifier == MLP_2) {
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setLearningRate(0.05);
			return mlp;
		} else if (theClassifier == MLP_3) {
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setHiddenLayers("a,2");
			return mlp;
		} else if (theClassifier == MLP_4) {
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setLearningRate(0.1);
			mlp.setHiddenLayers("a,2");
			return mlp;
		} else if (theClassifier == MLP_5) {
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setLearningRate(0.05);
			mlp.setHiddenLayers("a,2");
			return mlp;
		} else if (theClassifier == ZEROR) {
			ZeroR zeror = new ZeroR();
			return zeror;
		} else if (theClassifier == STACKING) {
			Stacking stacking = new Stacking();
			return stacking;
		} else if (theClassifier == LINEAR_REGRESSION) {
			LinearRegression lr = new LinearRegression();
			return lr;
		}  else if (theClassifier == LINEAR_REGRESSION_1) {
			LinearRegression lr = new LinearRegression();
			lr.setAttributeSelectionMethod(new SelectedTag(LinearRegression.SELECTION_GREEDY, LinearRegression.TAGS_SELECTION));
			return lr;
		}  else if (theClassifier == GP) {
			GaussianProcesses gp = new GaussianProcesses();
			return gp;
		}  else if (theClassifier == GP_1) {
			GaussianProcesses gp = new GaussianProcesses();
			gp.setFilterType(new SelectedTag(GaussianProcesses.FILTER_NONE, GaussianProcesses.TAGS_FILTER));
			return gp;
		}  else if (theClassifier == GP_2) {
			GaussianProcesses gp = new GaussianProcesses();
			gp.setFilterType(new SelectedTag(GaussianProcesses.FILTER_STANDARDIZE, GaussianProcesses.TAGS_FILTER));
			return gp;
		}  else if (theClassifier == GP_3) {
			GaussianProcesses gp = new GaussianProcesses();
			gp.setKernel(new Puk());
			return gp;
		}  else if (theClassifier == GP_4) {
			GaussianProcesses gp = new GaussianProcesses();
			gp.setFilterType(new SelectedTag(GaussianProcesses.FILTER_NONE, GaussianProcesses.TAGS_FILTER));
			gp.setKernel(new Puk());
			return gp;
		}  else if (theClassifier == GP_5) {
			GaussianProcesses gp = new GaussianProcesses();
			gp.setFilterType(new SelectedTag(GaussianProcesses.FILTER_STANDARDIZE, GaussianProcesses.TAGS_FILTER));
			gp.setKernel(new Puk());
			return gp;
		}  else if (theClassifier == M5R) {
			M5Rules m5R = new M5Rules();
			return m5R;
		}  else if (theClassifier == M5R_1) {
			M5Rules m5R = new M5Rules();
			m5R.setMinNumInstances(8);
			return m5R;
		}  else if (theClassifier == M5R_2) {
			M5Rules m5R = new M5Rules();
			m5R.setMinNumInstances(2);
			return m5R;
		}  else if (theClassifier == M5R_3) {
			M5Rules m5R = new M5Rules();
			m5R.setUnpruned(true);
			return m5R;
		}  else if (theClassifier == M5R_4) {
			M5Rules m5R = new M5Rules();
			m5R.setUnpruned(true);
			m5R.setMinNumInstances(8);
			return m5R;
		}  else if (theClassifier == M5R_5) {
			M5Rules m5R = new M5Rules();
			m5R.setUnpruned(true);
			m5R.setMinNumInstances(2);
			return m5R;
		}  else if (theClassifier == KSTAR) {
			KStar kStar = new KStar();
			SelectedTag newMode = new SelectedTag(KStar.M_DELETE, KStar.TAGS_MISSING);
			kStar.setMissingMode(newMode);
			return kStar;
		}  else if (theClassifier == ADDITIVE_REGRESSION) {
			AdditiveRegression additiveRegression = new AdditiveRegression();
			additiveRegression.setClassifier(new REPTree());
			return additiveRegression;
		}  	else if (theClassifier == SUPPORT_VECTOR_REGRESSION) {
			SMOreg supportVectorRegression = new SMOreg();
			return supportVectorRegression;
		}  	else if (theClassifier == SUPPORT_VECTOR_REGRESSION_1) {
			SMOreg supportVectorRegression = new SMOreg();
			supportVectorRegression.setC(0.1);
			return supportVectorRegression;
		}  	else if (theClassifier == SUPPORT_VECTOR_REGRESSION_2) {
			SMOreg supportVectorRegression = new SMOreg();
			supportVectorRegression.setC(0.05);
			return supportVectorRegression;
		}  	else if (theClassifier == SUPPORT_VECTOR_REGRESSION_3) {
			SMOreg supportVectorRegression = new SMOreg();
			supportVectorRegression.setKernel(new RBFKernel());
			return supportVectorRegression;
		}  	else if (theClassifier == SUPPORT_VECTOR_REGRESSION_4) {
			SMOreg supportVectorRegression = new SMOreg();
			supportVectorRegression.setC(0.1);
			supportVectorRegression.setKernel(new RBFKernel());
			return supportVectorRegression;
		}  	else if (theClassifier == SUPPORT_VECTOR_REGRESSION_5) {
			SMOreg supportVectorRegression = new SMOreg();
			supportVectorRegression.setC(0.05);
			supportVectorRegression.setKernel(new RBFKernel());
			return supportVectorRegression;
		}  	else if (theClassifier == DECISION_TABLE) {
			DecisionTable decisionTable = new DecisionTable();
			return decisionTable;
		} 	else if (theClassifier == DECISION_TABLE_1) {
			DecisionTable decisionTable = new DecisionTable();
			decisionTable.setSearch(new GeneticSearch());
			return decisionTable;
		}  else if (theClassifier == DECISION_TABLE_2) {
			DecisionTable decisionTable = new DecisionTable();
			decisionTable.setSearch(new GreedyStepwise());
			return decisionTable;
		}  else if (theClassifier == DECISION_TABLE_3) {
			DecisionTable decisionTable = new DecisionTable();
			decisionTable.setEvaluationMeasure(new SelectedTag(DecisionTable.EVAL_MAE, DecisionTable.TAGS_EVALUATION));
			return decisionTable;
		}  else if (theClassifier == DECISION_TABLE_4) {
			DecisionTable decisionTable = new DecisionTable();
			decisionTable.setSearch(new GeneticSearch());
			decisionTable.setEvaluationMeasure(new SelectedTag(DecisionTable.EVAL_MAE, DecisionTable.TAGS_EVALUATION));
			return decisionTable;
		}  else if (theClassifier == DECISION_TABLE_5) {
			DecisionTable decisionTable = new DecisionTable();
			decisionTable.setSearch(new GreedyStepwise());
			decisionTable.setEvaluationMeasure(new SelectedTag(DecisionTable.EVAL_MAE, DecisionTable.TAGS_EVALUATION));
			return decisionTable;
		}  else if (theClassifier == REP_TREE) {
			REPTree repTree = new REPTree();
			return repTree;
		}  else if (theClassifier == REP_TREE_1) {
			REPTree repTree = new REPTree();
			repTree.setMinNum(3);
			return repTree;
		}  else if (theClassifier == REP_TREE_2) {
			REPTree repTree = new REPTree();
			repTree.setMinNum(1);
			return repTree;
		}  else if (theClassifier == REP_TREE_3) {
			REPTree repTree = new REPTree();
			repTree.setNoPruning(true);
			return repTree;
		}  else if (theClassifier == REP_TREE_4) {
			REPTree repTree = new REPTree();
			repTree.setNoPruning(true);
			repTree.setMinNum(3);
			return repTree;
		}  else if (theClassifier == REP_TREE_5) {
			REPTree repTree = new REPTree();
			repTree.setNoPruning(true);
			repTree.setMinNum(1);
			return repTree;
		}  else if (theClassifier == CONJUNCTIVE_RULES) {
			ConjunctiveRule conjunctiveRules = new ConjunctiveRule();
			return conjunctiveRules;
		}  else if (theClassifier == CONJUNCTIVE_RULES_1) {
			ConjunctiveRule conjunctiveRules = new ConjunctiveRule();
			conjunctiveRules.setMinNo(3);
			return conjunctiveRules;
		}  else if (theClassifier == CONJUNCTIVE_RULES_2) {
			ConjunctiveRule conjunctiveRules = new ConjunctiveRule();
			conjunctiveRules.setMinNo(5);
			return conjunctiveRules;
		}  else if (theClassifier == CONJUNCTIVE_RULES_3) {
			ConjunctiveRule conjunctiveRules = new ConjunctiveRule();
			conjunctiveRules.setMinNo(7);
			return conjunctiveRules;
		}  else if (theClassifier == CONJUNCTIVE_RULES_4) {
			ConjunctiveRule conjunctiveRules = new ConjunctiveRule();
			conjunctiveRules.setExclusive(true);
			conjunctiveRules.setMinNo(3);
			return conjunctiveRules;
		}  else if (theClassifier == CONJUNCTIVE_RULES_5) {
			ConjunctiveRule conjunctiveRules = new ConjunctiveRule();
			conjunctiveRules.setExclusive(true);
			conjunctiveRules.setMinNo(5);
			return conjunctiveRules;
		}  else if (theClassifier == CONJUNCTIVE_RULES_6) {
			ConjunctiveRule conjunctiveRules = new ConjunctiveRule();
			conjunctiveRules.setExclusive(true);
			conjunctiveRules.setMinNo(7);
			return conjunctiveRules;
		}  else if (theClassifier == RBF_NETWORK) {
			RBFNetwork rbfNetwork = new RBFNetwork();
			return rbfNetwork;
		}  else if (theClassifier == RBF_NETWORK_1) {
			RBFNetwork rbfNetwork = new RBFNetwork();
			rbfNetwork.setNumClusters(3);
			return rbfNetwork;
		}  else if (theClassifier == BAGGING) {
			Bagging bagging = new Bagging();
			bagging.setClassifier(new REPTree());
			return bagging;
		}  else if (theClassifier == BAGGING_1) {
			Bagging bagging = new Bagging();
			bagging.setClassifier(new DecisionStump());
			return bagging;
		}  else if (theClassifier == BAGGING_2) {
			Bagging bagging = new Bagging();
			bagging.setClassifier(new J48());
			return bagging;
		}  else if (theClassifier == BAGGING_3) {
			Bagging bagging = new Bagging();
			bagging.setClassifier(new LADTree());
			return bagging;
		}  else if (theClassifier == BAGGING_4) {
			Bagging bagging = new Bagging();
			bagging.setClassifier(new LMT());
			return bagging;
		}  else if (theClassifier == BAGGING_5) {
			Bagging bagging = new Bagging();
			bagging.setClassifier(new BFTree());
			return bagging;
		}  else if (theClassifier == BAGGING_KNN_1) {
			Bagging bagging = new Bagging();
			IBk ibk = new IBk(3);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_NONE, IBk.TAGS_WEIGHTING));
			bagging.setClassifier(ibk);
			return bagging;
		}  else if (theClassifier == BAGGING_KNN_2) {
			Bagging bagging = new Bagging();
			IBk ibk = new IBk(3);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_INVERSE, IBk.TAGS_WEIGHTING));
			bagging.setClassifier(ibk);
			return bagging;
		}  else if (theClassifier == BAGGING_KNN_3) {
			Bagging bagging = new Bagging();
			IBk ibk = new IBk(3);
			ibk.setDistanceWeighting(new SelectedTag(IBk.WEIGHT_SIMILARITY, IBk.TAGS_WEIGHTING));
			bagging.setClassifier(ibk);
			return bagging;
		}  else if (theClassifier == BAGGING_MLP_1) {
			Bagging bagging = new Bagging();
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setLearningRate(0.1);
			bagging.setClassifier(mlp);
			return bagging;
		}  else if (theClassifier == BAGGING_MLP_2) {
			Bagging bagging = new Bagging();
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setLearningRate(0.05);
			bagging.setClassifier(mlp);
			return bagging;
		}  else if (theClassifier == BAGGING_MLP_3) {
			Bagging bagging = new Bagging();
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setHiddenLayers("a,2");
			bagging.setClassifier(mlp);
			return bagging;
		}  else if (theClassifier == BAGGING_MLP_4) {
			Bagging bagging = new Bagging();
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setHiddenLayers("a,2");
			mlp.setLearningRate(0.1);
			bagging.setClassifier(mlp);
			return bagging;
		}  else if (theClassifier == BAGGING_MLP_5) {
			Bagging bagging = new Bagging();
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setHiddenLayers("a,2");
			mlp.setLearningRate(0.05);
			bagging.setClassifier(mlp);
			return bagging;
		}  else if (theClassifier == BAGGING_KSTAR) {
			Bagging bagging = new Bagging();
			KStar kStar = new KStar();
			SelectedTag newMode = new SelectedTag(KStar.M_DELETE, KStar.TAGS_MISSING);
			kStar.setMissingMode(newMode);
			bagging.setClassifier(kStar);
			return bagging;
		}  else if (theClassifier == BAGGING_BAYES_NET) {
			Bagging bagging = new Bagging();
			BayesNet bayesNet = new BayesNet();
			bagging.setClassifier(bayesNet);
			return bagging;
		}  else if (theClassifier == M5P) {
			M5P m5P = new M5P();
			return m5P;
		}  else if (theClassifier == M5P_1) {
			M5P m5P = new M5P();
			m5P.setMinNumInstances(8);
			return m5P;
		}  else if (theClassifier == M5P_2) {
			M5P m5P = new M5P();
			m5P.setMinNumInstances(2);
			return m5P;
		}  else if (theClassifier == M5P_3) {
			M5P m5P = new M5P();
			m5P.setUnpruned(true);
			return m5P;
		}  else if (theClassifier == M5P_4) {
			M5P m5P = new M5P();
			m5P.setMinNumInstances(8);
			m5P.setUnpruned(true);
			return m5P;
		}  else if (theClassifier == M5P_5) {
			M5P m5P = new M5P();
			m5P.setMinNumInstances(2);
			m5P.setUnpruned(true);
			return m5P;
		}  else if (theClassifier == DECISION_STUMP) {
			DecisionStump decisionStump = new DecisionStump();
			return decisionStump;
		}  else if (theClassifier == LEAST_MED_SQ) {
			LeastMedSq leastMedSq = new LeastMedSq();
			return leastMedSq;
		}  else if (theClassifier == LEAST_MED_SQ_1) {
			LeastMedSq leastMedSq = new LeastMedSq();
			leastMedSq.setSampleSize(5);
			return leastMedSq;
		}  else if (theClassifier == LEAST_MED_SQ_2) {
			LeastMedSq leastMedSq = new LeastMedSq();
			leastMedSq.setSampleSize(6);
			return leastMedSq;
		}  else if (theClassifier == LEAST_MED_SQ_3) {
			LeastMedSq leastMedSq = new LeastMedSq();
			leastMedSq.setRandomSeed(1);
			return leastMedSq;
		}  else if (theClassifier == LEAST_MED_SQ_4) {
			LeastMedSq leastMedSq = new LeastMedSq();
			leastMedSq.setRandomSeed(1);
			leastMedSq.setSampleSize(5);
			return leastMedSq;
		}  else if (theClassifier == LEAST_MED_SQ_5) {
			LeastMedSq leastMedSq = new LeastMedSq();
			leastMedSq.setRandomSeed(1);
			leastMedSq.setSampleSize(6);
			return leastMedSq;
		}  else if (theClassifier == LOCALLY_WEIGHTED_LEARNING) {
			LWL lwl = new LWL();
			return lwl;
		}  else if (theClassifier == LOCALLY_WEIGHTED_LEARNING_1) {
			LWL lwl = new LWL();
			LinearNNSearch lns = new LinearNNSearch();
			try {
				lns.setDistanceFunction(new ManhattanDistance());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lwl.setNearestNeighbourSearchAlgorithm(lns);
			return lwl;
		}  else if (theClassifier == BAYES_NET) {
			BayesNet bayesNet = new BayesNet();
			return bayesNet;
		}  else if (theClassifier == LMT) {
			LMT lmt = new LMT();
			return lmt;
		}  else if (theClassifier == LAD_TREE) {
			LADTree ladTree = new LADTree();
			return ladTree;
		} else if (theClassifier == J_RIP) {
			JRip jRip = new JRip();
			return jRip;
		}   else if (theClassifier == BEST_FIRST_TREE) {
			BFTree bestFirstTree = new BFTree();
			bestFirstTree.setPruningStrategy(new SelectedTag(BFTree.PRUNING_PREPRUNING, BFTree.TAGS_PRUNING));
			return bestFirstTree;
		}  else if (theClassifier == BEST_FIRST_TREE_1) {
			BFTree bestFirstTree = new BFTree();
			bestFirstTree.setPruningStrategy(new SelectedTag(BFTree.PRUNING_UNPRUNED, BFTree.TAGS_PRUNING));
			return bestFirstTree;
		}  else if (theClassifier == BEST_FIRST_TREE_2) {
			BFTree bestFirstTree = new BFTree();
			return bestFirstTree;
		}  
		
		
		else {
			return new J48();
		}
	}




	/**
	 * Devolve o número de folds se o experimento está usando validação cruzada
	 * k-fold.
	 * 
	 * @return O número de folds configurado para o experimento.
	 */
	public int getFolds() {
		return folds;
	}


	
	/**
	 * Retorna um List com os atributos encontrados no arquivo de treinamento.
	 * 
	 * @return Um ArrayList com os atributos.
	 */
	public List<String> getAttributes() {
		List<String> list = new ArrayList<String>();

		Enumeration<?> e = trainingData.enumerateAttributes();
		while (e.hasMoreElements()) {
			list.add(((Attribute) e.nextElement()).name());
		}
		return list;
	}

	/**
	 * Retorna um String com a sequencia de atributos do arquivo de treino ou
	 * teste, separados por "," (virgula)
	 * 
	 * @param typeFile
	 *            A constante de classe TRAINING_FILE ou TEST_FILE
	 * @return Um String com a sequência de atributos.
	 */
	public String getAttributesAsString(int typeFile) {
		StringBuilder str = new StringBuilder();
		Instances dataset = null;

		if (typeFile == TRAINING_FILE)
			dataset = trainingData;
		else if (typeFile == TEST_FILE)
			dataset = testData;
		else
			dataset = trainingData;

		Enumeration<?> e = dataset.enumerateAttributes();
		while (e.hasMoreElements()) {
			if (str.length() > 0)
				str.append(", ");
			str.append(((Attribute) e.nextElement()).name());
		}
		return str.toString();
	}

	/**
	 * Retorna um List com o indice dos atributos encontrados no arquivo de
	 * treinamento.
	 * 
	 * @return Um ArrayList com os indices dos atributos.
	 */
	public List<String> getAttributeIndex() {
		List<String> list = new ArrayList<String>();

		Enumeration<?> e = trainingData.enumerateAttributes();
		while (e.hasMoreElements()) {
			Attribute att = (Attribute) e.nextElement();

			list.add(String.valueOf(att.index() + 1));
		}
		return list;
	}

	/**
	 * Obtem o número de atributos de um arquivo de Treino ou Teste
	 * 
	 * @param typeFile
	 *            A constante de classe TRAINING_FILE ou TEST_FILE, que sinaliza
	 *            qual dos arquivos deseja-se obter o número de atributos.
	 * @return O número de atributos. O valor -1 indica que "typeFile" foi
	 *         informado incorretamente.
	 */
	public int getNumAttributes(int typeFile) {
		Instances dataset = null;

		if (typeFile == TRAINING_FILE)
			dataset = trainingData;
		else if (typeFile == TEST_FILE)
			dataset = testData;
		else
			return -1;

		return dataset.numAttributes();
	}

	
	
	/**
	 * Remove atributos do arquivo de treinamento.
	 * 
	 * @param listOfAttributes
	 *            Um string contendo a sequencia de atributos que serão
	 *            desconsiderados. Por exemplo: "1,2,3" ou com range "1-3,6,9"
	 * @param invertSelection
	 *            true, quando deve ser removido os atributos cujos índices não
	 *            estão presentes em listOfAttributes, ou false, caso os
	 *            atributos a serem removidos sejam os que estão implícitos em
	 *            listOfAttributes.
	 * @param typeFile
	 *            A constante de classe TRAINING_FILE ou TESTE_FILE
	 * @throws Exception
	 *             Fonte: (1) - http://weka.wikispaces.com/Remove+Attributes
	 *             OBS: internamente, o atributo de classe é acrescentado.
	 */
	public void removeAttribute(String listOfAttributes,
			boolean invertSelection, int typeFile) throws Exception {
		/*
		 * Use este trecho para usar com o metodo setOption() String[] options =
		 * new String[2]; options[0] = "-R"; options[1] = "1,3"; // ou "1,3-5,7"
		 */

		Instances dataset = null;
		if (typeFile == TRAINING_FILE)
			dataset = trainingData;
		else if (typeFile == TEST_FILE)
			dataset = testData;
		else
			dataset = trainingData;

		Remove remove = new Remove();

		// remove.setOptions( options );
		// Adicionando o atributo de classe
		listOfAttributes += ("," + (dataset.classIndex() + 1));
		remove.setAttributeIndices(listOfAttributes);
		remove.setInvertSelection(new Boolean(invertSelection));
		remove.setInputFormat(dataset);
		// Filter gera um novo objeto com os atributos selecionados. Portanto, o
		// arquivo
		// de treino ou teste deve ser atualizado
		if (typeFile == TRAINING_FILE)
			trainingData = Filter.useFilter(dataset, remove);
		else
			testData = Filter.useFilter(dataset, remove);

		// System.out.println("\nAtributos originais");
		// System.out.println("------------------------------");
		// Enumeration<?> e = trainingData.enumerateAttributes();
		// while( e.hasMoreElements() ){
		// System.out.println( ((Attribute) e.nextElement()).name() );
		// }

		// System.out.println("\nAtributos remanescentes");
		// System.out.println("------------------------------");
		// e = newData.enumerateAttributes();
		// while( e.hasMoreElements() ){
		// System.out.println( ((Attribute) e.nextElement()).name() );
		// }
		// return null;
		// return newData;
	}

	
	/**
	 * Exibe na tela a lista de atributos encontrada no arquivo de instancias
	 * ARFF
	 * 
	 * @param data
	 *            O arquivo de instancias ARFF.
	 */
	public void showAttributes(Instances data) {
		Enumeration<?> e = data.enumerateAttributes();
		while (e.hasMoreElements()) {
			System.out.println(((Attribute) e.nextElement()).name());
		}

	}

	/**
	 * Cria e avalia um modelo de classificação utilizando o método de
	 * reamostragem k-fold validation.
	 * 
	 * @param nFolds
	 *            O número de folds (partições) desejado
	 * @param seed
	 *            O valor de seed para randomizar as instâncias. O padrão é 1.
	 * @param theClassifier
	 *            A constante referente ao classificador desejado
	 * 
	 *            OBSERVAÇÃO: - Internamente já são chamados os métodos
	 *            buildClassifier() e evaluateModel();
	 * 
	 *            O arquivo fornecido para validação cruzada é armazenado em
	 *            "trainingData/'.
	 * @throws Exception
	 *             Se houver problema na avaliação da validação cruzada.
	 */
	public void KFoldCrossValidationStratified(int folds, int seed,
			int theClassifier) throws Exception {
		this.folds = folds;
		this.seed = seed;

		// O classificador é criado aqui de acordo com a constante que foi
		// passada
		Classifier chosenClassifier = createClassifier(theClassifier);
		isTrainEvaluated = true;

		// TODO aqui tenho que ajustar os atributos que serão utilizados nas
		// instâncias usando o trainingData

		// randomize data
		Random rand = new Random(seed);
		// Pega os dados que serão testados e coloca dentro do instances, o training data foi setado antes com as instancias
		Instances randData = new Instances(trainingData);

		randData.randomize(rand);
		if (randData.classAttribute().isNominal())
			// estratifica um conjunto de instancias de acordo com seu valor de
			// classe se
			// se o atributo de classe é nominal (para que depois um
			// cross-validation estratificado
			// possa ser realizado.
			randData.stratify(folds);

		// perform cross-validation
		eval = new Evaluation(randData);

		for (int n = 0; n < folds; n++) {
			Instances train = randData.trainCV(folds, n); // Creates the
															// training set for
															// one fold of a
															// cross-validation
															// on the dataset.
			Instances test = randData.testCV(folds, n); // Creates the test set
														// for one fold of a
														// cross-validation on
														// the dataset.
			// the above code is used by the StratifiedRemoveFolds filter, the
			// code below by the Explorer/Experimenter:
			// Instances train = randData.trainCV(folds, n, rand);
			// Pega um fold específico para cross-validation.

			// build and evaluate classifier
			classifier = Classifier.makeCopy(chosenClassifier);
			classifier.buildClassifier(train);

			
			eval.evaluateModel(classifier, test);
		}

		classifier.buildClassifier(trainingData);
		// eval.evaluateModel(classifier, trainingData);
		// System.out.println( eval.toSummaryString());
		// System.out.println( getDecisionTree());

	}

	/**
	 * Exibe na tela o resultado da avaliação do classificador.
	 */
	public void showOutputEvaluation() {
		System.out.println(toRunInformation());
		//System.out.println(toClassDetailsString());
		//System.out.println(toConfusionMatrix());
	}

	/**
	 * Retorna um String com os dados de entrada para avaliação do classificador
	 * quando for usado o método de reamostragem k-fold cross-validation.
	 * 
	 * @return A String com o resultado da avaliação.
	 */
	public String toRunInformation() {
		StringBuilder output = new StringBuilder();

		Instances data = null;
		if (isTrainEvaluated)
			data = trainingData;
		else
			data = testData;

		// output evaluation
		output.append("\n");
		output.append("=== Setup ===\n");
		output.append("Classifier:\t" + this.classifier.getClass().getName()
				+ "\n");
		output.append("Dataset:\t" + data.relationName() + "\n");
		output.append("Instances:\t" + data.numInstances() + "\n");
		output.append("Attributes:\t" + data.numAttributes() + "\n");
		output.append("Folds: " + this.folds + "\n");
		output.append("Seed: " + this.seed + "\n\n");
		// O método toSummaryString() equivale a 1a informação de saída da
		// avaliação no Weka
		if (folds < 0)
			output.append(eval.toSummaryString("=== Summary ===", false));
		else
			output.append(eval.toSummaryString("=== " + folds
					+ "-fold Cross-validation ===", false));
		return output.toString();
	}

	/**
	 * Retorna um String contendo a matriz de confusão da avaliação.
	 * 
	 * @return A matriz de confusão como um String.
	 */
	public String toConfusionMatrix() {
		if (eval == null)
			return null;

		try {
			// O método toMatrixString() equivale a 1a informação de saída da
			// avaliação no Weka
			return eval.toMatrixString();
		} catch (Exception e) {
			System.err.println("Erro no método assDetailsString()");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retorna um String contendo a acurácia detalhada por classe.
	 * 
	 * @return Os detalhes da acurácia como um String.
	 */
	public String toClassDetailsString() {
		if (eval == null)
			return null;
		try {
			// O método toClassDetailsString() equivale a 2a informação de saída
			// da avaliação no Weka
			return eval.toClassDetailsString();
		} catch (Exception e) {
			System.err.println("Erro no método classDetailsString()");
			e.printStackTrace();
			return null;
		}
	}

	

	public Classifier getClassifier() {
		return classifier;
	}

	public void setTrainingData(Instances trainingData) {
		this.trainingData = trainingData;
		trainingFileLoaded = true;

	}

	public void setTestData(Instances testData) {
		this.testData = testData;
		testFileLoaded = true;

	}
}
