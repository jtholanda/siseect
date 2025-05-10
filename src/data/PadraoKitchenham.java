package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.TipoValidacao;
import util.WekaExperiment;
import weka.core.Instances;
import model.Projeto;

/**Padrão CocomoNasa V1 normal com todos atributos
 * */
public class PadraoKitchenham extends Padrao {

	private static final int INDICE_ROTULO = 5;
	private int numeroEnsemble = 0;
	private static final int QTDE_PROJETOS = 145;

	public static double KNORA_MEDIA_MAR = 2183;
	public static double KNORA_MEDIA_MARLOG = 2.89;
	public static double KNORA_MEDIA_MREAJUSTADO = 1.72;	

	public PadraoKitchenham() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMreajustado = KNORA_MEDIA_MREAJUSTADO;
	}

	public PadraoKitchenham(int numeroEnsemble) {
		this.numeroEnsemble = numeroEnsemble;
		if(this.numeroEnsemble == 1){
			classificadores = "{KNN3,MLP1,LR}";
		}
		if(this.numeroEnsemble == 2){
			classificadores = "{LMS,MLP2,SVMR}";
		}
		if(this.numeroEnsemble == 3){
			classificadores = "{LMS,M5R,SVMR}";
		}
		if(this.numeroEnsemble == 4){
			classificadores = "{M5R,MLP2,SVMR}";
		}
		if(this.numeroEnsemble == 5){
			classificadores = "{LMS,M5R,MLP2}";
		}
		if(this.numeroEnsemble == 6){
			classificadores = "{LMS,M5R,MLP2,SVMR}";
		}
		if(this.numeroEnsemble == 7){
			classificadores = "{KNN3,KNN7,MLP2,LR,AR,GP,SVR,BA,M5R,M5P}";
		}
	}


	@Override
	protected List<Projeto> getProjetos(String caminhoArquivo)
			throws FileNotFoundException, IOException {
		List<Projeto> listaProjetos = new ArrayList<Projeto>();
		FileReader reader = new FileReader(caminhoArquivo);

		Instances instancias = new Instances(reader);

		int indice = 0;
		for (int contador = 0; contador < instancias.numInstances(); contador++, indice = 0) {

			Projeto projeto = new Projeto();
			projeto.setClienteCode(instancias.instance(contador).stringValue(indice++));
			projeto.setProjectType(instancias.instance(contador).stringValue(indice++));
			projeto.setActualDuration((int)instancias.instance(contador).value(indice++));
			projeto.setAdjustedFunctionPoints(instancias.instance(contador).value(indice++));
			projeto.setFirstEstimateMethod(instancias.instance(contador).stringValue(indice++));
			projeto.setActualEffort(instancias.instance(contador).value(indice++));
			
			listaProjetos.add(projeto);

		}
		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		
		for (Projeto projeto : listaProjetos) {

			System.out.print(projeto.getClienteCode() + "\t");
			System.out.print(projeto.getProjectType() + "\t");
			System.out.print(projeto.getActualDuration() + "\t");
			System.out.print(projeto.getAdjustedFunctionPoints() + "\t");
			System.out.print(projeto.getFirstEstimateMethod() + "\t");
			System.out.print(projeto.getActualEffort() + "\t");
			System.out.print(projeto.getMenorErro() + "\t");
			System.out.println(projeto.getMelhorAlgoritmo());
		}
		
	}

	@Override
	public String toString() {
		return "KITCHENHAM/KITCHENHAM";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}

	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "KITCHENHAM/KITCHENHAM.ARFF";
	}
	
	@Override
	public String getCabecalho() {
		// TODO Auto-generated method stub
		return "@relation kitchenham \n\n" +

		"@attribute ClientCode {1,2,3,4,5,6} \n" +
		"@attribute ProjectType {A,C,D,P,Pr,U} \n" +
		"@attribute ActualDuration numeric \n" +
		"@attribute AdjustedFunctionPoints numeric \n" +
		"@attribute FirstEstimateMethod {A,C,CAE,D,EO,W} \n" +
		"@attribute ActualEffort numeric \n" +

		"@data \n\n";

	}
	
	
	@Override
	public String getCabecalhoClassificacao() {
		// TODO Auto-generated method stub
		return "@relation kitchenham \n\n" +

		"@attribute ClientCode {1,2,3,4,5,6} \n" +
		"@attribute ProjectType {A,C,D,P,Pr,U} \n" +
		"@attribute ActualDuration numeric \n" +
		"@attribute AdjustedFunctionPoints numeric \n" +
		"@attribute FirstEstimateMethod {A,C,CAE,D,EO,W} \n" +
		"@attribute winner" + classificadores + "\n\n" +

		"@data \n\n";

	}

	@Override
	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento) {
		
		StringBuilder sb = new StringBuilder();

		sb.append(projeto.getClienteCode() + ",\t");
		sb.append(projeto.getProjectType() + ",\t");
		sb.append(projeto.getActualDuration() + ",\t");
		sb.append(projeto.getAdjustedFunctionPoints() + ",\t");
		sb.append(projeto.getFirstEstimateMethod() + ",\t");

		if (isTreinamento) {
			sb.append(projeto.getMelhorAlgoritmo());
		} else {
			sb.append("?");
		}

		return sb.toString();
		 
	}

	@Override
	public int getTamanhoBase() {
		// TODO Auto-generated method stub
		return QTDE_PROJETOS;
	}

	@Override
	public TipoValidacao getTipoValidacao() {
		// TODO Auto-generated method stub
		return super.getTipoValidacao();
	}

	@Override
	public String getCaminhoModeloPadrao() {
		// TODO Auto-generated method stub
		return "MODELOS/KITCHENHAM";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "KITCHENHAM";
	}

	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return WekaExperiment.SUPPORT_VECTOR_REGRESSION;
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.M5P_5;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.LEAST_MED_SQ;
	}

	@Override
	public Integer getR4() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getR5() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador1(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.KNN_2;
		}
		return classificador;	

	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.ADABOOST_4;
		}
		return classificador;	

	}

	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.BAGGING_4;
		}
		return classificador;	

	}

	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.LOCALLY_WEIGHTED_LEARNING;
		}
		return classificador;	

	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.J48;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificador6(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador7(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador8(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador9(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador10(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade1(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.NAIVE_BAYES;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.NAIVE_BAYES_1;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.NAIVE_BAYES_2;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.LMT;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.ADABOOST_1;
		}
		return classificador;	
	}

}
