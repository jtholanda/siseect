package main;
import method.Knora_DCS_Set_Ensemble_Dynamic;
import util.Constantes;
import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import data.Padrao;
import data.PadraoISBSGGeral;
import data.PadraoMiyazaki94;

public class TesteKnoraDCSSetEnsembleDynamic {

	public static TipoMetodoCombinacao metodoDeSelecaoPorDistancia;
	public static Classifier regressor1, regressor2, regressor3;
	public static String experimento;
	public static Padrao padrao;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

			
			try {
				
		
					
				// Experiment name
				//experimento = "Myazaki94";

				
				//metodoDeSelecaoPorDistancia = TipoMetodoCombinacao.KNORA_E;
				//Utilidade.METRICA_AVALIACAO = Constantes.MAR; 
				
				//Knora_DCS_Set_Ensemble_Dynamic.K_SELECAO_DINAMICA = 7;
				//Knora_DCS_Set_Ensemble_Dynamic.KNORA = 0.0;
				
				//System.out.println("Avaliação de validação:"+ Constantes.BASE_VALIDACAO);

				
				//regressor1 = new WekaExperiment().createClassifier(WekaExperiment.LEAST_MED_SQ);	
				//regressor2 = new WekaExperiment().createClassifier(WekaExperiment.SUPPORT_VECTOR_REGRESSION);	
				//regressor3 = new WekaExperiment().createClassifier(WekaExperiment.M5P);	
					
				Utilidade.inicializaListasMetricas();
				
				new Knora_DCS_Set_Ensemble_Dynamic(regressor1,regressor2,regressor3).run(padrao, metodoDeSelecaoPorDistancia, padrao.getTipoValidacao());

				String avaliacao = Utilidade.AVALIACAO;
				Utilidade.gerarArquivosMassa(0, "DYNAMIC_SELECTION_" + experimento + "_" + padrao.getNomePadrao() + "_" + metodoDeSelecaoPorDistancia + "_" + Knora_DCS_Set_Ensemble_Dynamic.K_SELECAO_DINAMICA + "_" + avaliacao);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

	}
		

}
