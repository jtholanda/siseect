package main;
import method.EnsembleEstaticoGeral;
import util.Constantes;
import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import data.Padrao;
import data.PadraoISBSGGeral;
import data.PadraoMiyazaki94;

public class TesteEnsembleEstatico {

	public static TipoMetodoCombinacao metodoDeCombinacao;
	public static Classifier regressor1, regressor2, regressor3, regressor4, regressor5;
	public static String experimento;
	public static Padrao padrao;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Experiment name
		//experimento = "Myazaki94";
		
		//System.out.println("Avaliação de validação:"+ Constantes.BASE_VALIDACAO);
	
		

		//metodoDeCombinacao = TipoMetodoCombinacao.MEDIA;


		//regressor1 = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ);	
		//regressor2 = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION);	
		//regressor3 = new WekaExperiment().createClassifier(WekaExperiment.M5P);	

		Utilidade.inicializaListasMetricas();
		
		new EnsembleEstaticoGeral(regressor1, regressor2, regressor3, regressor4, regressor5).run(padrao, metodoDeCombinacao, padrao.getTipoValidacao());

		//padrao = new PadraoISBSGGeral();
		//new EnsembleEstaticoGeral(regressor1, regressor2, regressor3, regressor4, regressor5).run(padrao, metodoDeCombinacao, padrao.getTipoValidacao());

		Utilidade.gerarArquivosMassa(0, "ENSEMBLE_" + metodoDeCombinacao + "_" + experimento +  "_" + Utilidade.AVALIACAO);

	}

}
