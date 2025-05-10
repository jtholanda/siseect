package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Projeto;
import util.TipoValidacao;
import util.WekaExperiment;
import weka.core.Instances;

/**
 * Desharnais com todos os atributos
 * */
public class PadraoDesharnais extends Padrao {

	private static final int INDICE_ROTULO = 10;
	private int numeroEnsemble = 0;
	private static final int QTDE_PROJETOS = 81;

	public static double KNORA_MEDIA_MAR = 2435;
	public static double KNORA_MEDIA_MARLOG = 3.12;
	public static double KNORA_MEDIA_MREAJUSTADO = 1.00;	

	public PadraoDesharnais() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMreajustado = KNORA_MEDIA_MREAJUSTADO;
	}

	public PadraoDesharnais(int numeroEnsemble) {
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
			projeto.setTeamExp(instancias.instance(contador).value(indice++));
			projeto.setManagerExp(instancias.instance(contador).value(indice++));
			projeto.setYearEnd(instancias.instance(contador).value(indice++));
			projeto.setLength(instancias.instance(contador).value(indice++));
			projeto.setTransactions(instancias.instance(contador).value(indice++));
			projeto.setEntities(instancias.instance(contador).value(indice++));
			projeto.setPointsAdjust(instancias.instance(contador).value(indice++));
			projeto.setEnvergure(instancias.instance(contador).value(indice++));
			projeto.setPointsNonAjust(instancias.instance(contador).value(indice++));
			projeto.setLanguage(Integer.parseInt(instancias.instance(contador).stringValue(indice++)));
			projeto.setEffort(instancias.instance(contador).value(indice++));
			
			listaProjetos.add(projeto);

		}
		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		for (Projeto projeto : listaProjetos) {
			  
			  System.out.print(projeto.getTeamExp() + "\t");
			  System.out.print(projeto.getManagerExp() + "\t");
			  System.out.print(projeto.getYearEnd() + "\t");
			  System.out.print(projeto.getLength() + "\t");
			  System.out.print(projeto.getTransactions() + "\t");
			  System.out.print(projeto.getEntities() + "\t");
			  System.out.print(projeto.getPointsAdjust() + "\t");
			  System.out.print(projeto.getEnvergure() + "\t");
			  System.out.print(projeto.getPointsNonAjust() + "\t");
			  System.out.print(projeto.getLanguage() + "\t");
			  System.out.print(projeto.getEffort() + "\t");
			  System.out.print(projeto.getMenorErro() + "\t");
			  System.out.println(projeto.getMelhorAlgoritmo());  
			 }
		
	}

	@Override
	public String toString() {
		return "DESHARNAIS/DESHARNAIS";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}

	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "DESHARNAIS/DESHARNAIS.ARFF";
	}


	@Override
	public String getCabecalho() {
		// TODO Auto-generated method stub
		return "@relation desharnais.csv \n\n" + 
				"@attribute TeamExp numeric        % measured in years \n" + 
				"@attribute ManagerExp numeric     % measured in years \n" +
				"@attribute YearEnd numeric \n" + 
				"@attribute Length numeric \n" +
				"@attribute Transactions numeric  % Transactions is a count of basic logical transactions in the system \n" +
				"@attribute Entities numeric      % Entities is the number of entities in the systems data model \n" +
				"@attribute PointsAdjust numeric \n" +
				"@attribute Envergure numeric \n" +
				"@attribute PointsNonAjust numeric \n" +
				"@attribute Language {1,2,3} \n" +
				"@attribute Effort numeric        % ActualEffort is measured in person-hours \n\n" +
				"@data \n";
	}
	
	@Override
	public String getCabecalhoClassificacao() {
		// TODO Auto-generated method stub
		return "@relation desharnais.csv \n\n" + 
				"@attribute TeamExp numeric        % measured in years \n" + 
				"@attribute ManagerExp numeric     % measured in years \n" +
				"@attribute YearEnd numeric \n" + 
				"@attribute Length numeric \n" +
				"@attribute Transactions numeric  % Transactions is a count of basic logical transactions in the system \n" +
				"@attribute Entities numeric      % Entities is the number of entities in the systems data model \n" +
				"@attribute PointsAdjust numeric \n" +
				"@attribute Envergure numeric \n" +
				"@attribute PointsNonAjust numeric \n" +
				"@attribute Language {1,2,3} \n" +
				"@attribute winner" + classificadores + "\n\n" +
				"@data \n";
	}

	@Override
	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();

		sb.append(projeto.getTeamExp() + "\t");
		sb.append(projeto.getManagerExp() + "\t");
		sb.append(projeto.getYearEnd() + "\t");
		sb.append(projeto.getLength() + "\t");
		sb.append(projeto.getTransactions() + "\t");
		sb.append(projeto.getEntities() + "\t");
		sb.append(projeto.getPointsAdjust() + "\t");
		sb.append(projeto.getEnvergure() + "\t");
		sb.append(projeto.getPointsNonAjust() + "\t");
		sb.append(projeto.getLanguage() + "\t");
		if(isTreinamento){
			sb.append(projeto.getMelhorAlgoritmo());
		}else{
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
		return "MODELOS/DESHARNAIS";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "DESHARNAIS";
	}

	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return WekaExperiment.SUPPORT_VECTOR_REGRESSION_2;
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.LEAST_MED_SQ;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.M5P_1;
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
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.ADABOOST_2;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KSTAR;
		}
		return classificador;	

	}

	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.RANDOM_FOREST;
		}
		return classificador;	

	}

	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.REP_TREE_5;
		}
		return classificador;	

	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.BAGGING_5;
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
		if(folds == 1){
			classificador = WekaExperiment.KNN_6;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KNN_4;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KNN_5;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.LOGISTIC_REGRESSION;
		}
		return classificador;	
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KNN_8;
		}
		return classificador;	
	}
	

}
