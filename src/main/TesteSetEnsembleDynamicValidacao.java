package main;
import java.util.Date;

import method.Set_Ensemble_Dynamic;
import util.Constantes;
import util.TipoMetodoCombinacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import data.Padrao;

public class TesteSetEnsembleDynamicValidacao {

	
	public static int classificador;
	public static String experimento;
	public static TipoMetodoCombinacao metodoDeCombinacao;
	public static Classifier regressor1;
	public static Classifier regressor2;
	public static Classifier regressor3;
	public static Classifier regressor4;
	public static Classifier regressor5;
	public static Padrao padrao;

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		try {
			
			
			
				Utilidade.inicializaListasMetricas();
				

				new Set_Ensemble_Dynamic(regressor1, regressor2, regressor3).run(padrao, null, null, null, null, null, null, classificador,  metodoDeCombinacao, padrao.getTipoValidacao());
	
				

				String avaliacao;
				if(Constantes.BASE_VALIDACAO){
					avaliacao = "Validação";
				}else{
					avaliacao = "Teste";
				}
				
				Classifier classifier = new WekaExperiment().createClassifier(classificador);
				String nomeDoClassificador;
				if(classifier instanceof IBk){
					IBk ibk = (IBk) classifier;
					nomeDoClassificador = classifier.getClass().getName() + "_" + ibk.getKNN() + "_" + ibk.distanceWeightingTipText();
				}
				if(classifier instanceof MultilayerPerceptron){
					MultilayerPerceptron mlp = (MultilayerPerceptron) classifier;
					nomeDoClassificador = classifier.getClass().getName() + "_" + mlp.getHiddenLayers() + "_" + mlp.getLearningRate();
				}				
				if(classifier instanceof LibSVM){
					LibSVM svm = (LibSVM) classifier;
					nomeDoClassificador = classifier.getClass().getName() + "_" + svm.getCost();
				}else{
					nomeDoClassificador = classifier.getClass().getName() + "_" + classifier.getClass().toString();
				}
				
				Utilidade.gerarArquivosMassa(0, "SetEnsembleDynamic" + experimento + "_" + metodoDeCombinacao + "_" + nomeDoClassificador + "_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" +  new Date().getTime() + "_" + avaliacao);

	


		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
