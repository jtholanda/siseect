package main;
import method.RegressorIndividualGeral;
import util.Constantes;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.meta.Bagging;
import data.Padrao;
import data.PadraoMiyazaki94;
import data.PadraoISBSGGeral;
public class TesteIndividualGeral {

	/**
	 * @param args
	 */
	
	public static double idRegressor;
	public static Classifier regressor;
	public static Padrao padrao;
	
	
	
	public static void main(String[] args) {
		


		Utilidade.inicializaListasMetricas();
			

		new RegressorIndividualGeral(regressor).run(padrao, padrao.getTipoValidacao());


		
		// get the full name of regressor if the regressor is bagging or boosting type
		String regressorBasico = "NO_ENSEMBLE";
		if(regressor instanceof Bagging){
			Bagging bagging = (Bagging) regressor;
			regressorBasico = bagging.getClassifier().getClass().getName();
		}
		if(regressor instanceof AdditiveRegression){
			AdditiveRegression additiveRegression = (AdditiveRegression) regressor;
			regressorBasico = additiveRegression.getClassifier().getClass().getName();
		}

		// setting the string variable to make the name of file  
		String avaliacao = Utilidade.AVALIACAO;
		if(Constantes.BASE_VALIDACAO){
			avaliacao = "Validação";
		}else{
			avaliacao = "Teste";
		}

		// generate the file with results
		Utilidade.gerarArquivosMassa(0, padrao.getNomePadrao() + "_" + regressor.getClass().getName() + "_" + idRegressor + "_"  + regressorBasico + "_" + avaliacao);
	}


}
