package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Projeto;
import util.TipoValidacao;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Padrão ISBSG com todos os atributos
 * */
public class PadraoChina extends Padrao{

	
	/**
	 * @param args
	 */
		
	private static final int INDICE_ROTULO = 13;
	private int numeroEnsemble = 0;

	public static double KNORA_MEDIA_MAR = 2146;
	public static double KNORA_MEDIA_MARLOG = 2.91;
	public static double KNORA_MEDIA_MREAJUSTADO = 2.22;	

	private static final int QTDE_PROJETOS = 346;

	
	public PadraoChina() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMreajustado = KNORA_MEDIA_MREAJUSTADO;
	}

	public PadraoChina(int numeroEnsemble) {
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
	public List<Projeto> getProjetos(String caminhoArquivo)
			throws FileNotFoundException, IOException {
		
		List<Projeto> listaProjetos = new ArrayList<Projeto>();
		FileReader reader = new FileReader(caminhoArquivo);

		Instances instancias = new Instances(reader);

		int indice = 0;
		for (int contador = 0; contador < instancias.numInstances(); contador++, indice = 0) {

			Projeto projeto = new Projeto();
			projeto.setAfp(instancias.instance(contador).value(indice++));
			projeto.setInput(instancias.instance(contador).value(indice++));
			projeto.setOutput(instancias.instance(contador).value(indice++));
			projeto.setEnquiry(instancias.instance(contador).value(indice++));
			projeto.setFile(instancias.instance(contador).value(indice++));
			projeto.setInterface1(instancias.instance(contador).value(indice++));
			projeto.setAdded(instancias.instance(contador).value(indice++));
			projeto.setChanged(instancias.instance(contador).value(indice++));			
			projeto.setDeleted(instancias.instance(contador).value(indice++));
			projeto.setPdrAfp(instancias.instance(contador).value(indice++));
			projeto.setPdrUfp(instancias.instance(contador).value(indice++));
			projeto.setNpdrAfp(instancias.instance(contador).value(indice++));
			projeto.setNpduAfp(instancias.instance(contador).value(indice++));
			projeto.setEsforcoSumarizado(instancias.instance(contador).value(indice++));
			listaProjetos.add(projeto);

		}
		

		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		// Testando se a lista de projetos foi populada corretamente
		
		  for (Projeto projeto : listaProjetos) {
	
		  System.out.print(projeto.getAfp() + "\t");
		  System.out.print(projeto.getInput() + "\t");
		  System.out.print(projeto.getOutput() + "\t");
		  System.out.print(projeto.getEnquiry() + "\t");
		  System.out.print(projeto.getFile() + "\t");
		  System.out.print(projeto.getInterface1() + "\t");
		  System.out.print(projeto.getAdded() + "\t");
		  System.out.print(projeto.getChanged() + "\t");
		  System.out.print(projeto.getDeleted() + "\t");
		  System.out.print(projeto.getPdrAfp() + "\t");
		  System.out.print(projeto.getPdrUfp() + "\t");
		  System.out.print(projeto.getNpdrAfp() + "\t");
		  System.out.print(projeto.getNpduAfp() + "\t");
		  System.out.print(projeto.getEsforcoSumarizado() + "\t");  
		  System.out.print((projeto.getMenorErro()+"").replace(".",",") + "\t");
		  System.out.println(projeto.getMelhorAlgoritmo());  

		  }

		
	}
	
	@Override
	public String toString() {
		return "CHINA/CHINA";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}


	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "CHINA/CHINA.ARFF";
	}
	
	@Override
	public String getCabecalho() {

		return "@relation Softwares_Data\n\n" +

		"@attribute AFP numeric" + "\n" +
		"@attribute Input numeric" + "\n" +
		"@attribute Output numeric" + "\n" +
		"@attribute Enquiry numeric " + "\n" +
		"@attribute File numeric" + "\n" +
		"@attribute Interface numeric " + "\n" +
		"@attribute Added numeric" + "\n" +
		"@attribute Changed numeric" + "\n" +
		"@attribute Deleted numeric " + "\n" +
		"@attribute PDR_AFP numeric" + "\n" +
		"@attribute PDR_UFP numeric" + "\n" +
		"@attribute NPDR_AFP numeric" + "\n" +
		"@attribute NPDU_UFP numeric" + "\n" +
		"@attribute Effort numeric" + "\n\n" +
		"@data \n";	
}
	
	@Override
	public String getCabecalhoClassificacao() {

		return "@relation Softwares_Data\n\n" +

		"@attribute AFP numeric" + "\n" +
		"@attribute Input numeric" + "\n" +
		"@attribute Output numeric" + "\n" +
		"@attribute Enquiry numeric " + "\n" +
		"@attribute File numeric" + "\n" +
		"@attribute Interface numeric " + "\n" +
		"@attribute Added numeric" + "\n" +
		"@attribute Changed numeric" + "\n" +
		"@attribute Deleted numeric " + "\n" +
		"@attribute PDR_AFP numeric" + "\n" +
		"@attribute PDR_UFP numeric" + "\n" +
		"@attribute NPDR_AFP numeric" + "\n" +
		"@attribute NPDU_UFP numeric" + "\n" +
		"@attribute winner" + classificadores + "\n\n" +
		"@data \n";
	}

	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento){

		
		StringBuilder sb = new StringBuilder();
		
		  sb.append(projeto.getAfp() + "\t");
		  sb.append(projeto.getInput() + "\t");
		  sb.append(projeto.getOutput() + "\t");
		  sb.append(projeto.getEnquiry() + "\t");
		  sb.append(projeto.getFile() + "\t");
		  sb.append(projeto.getInterface1() + "\t");
		  sb.append(projeto.getAdded() + "\t");
		  sb.append(projeto.getChanged() + "\t");
		  sb.append(projeto.getDeleted() + "\t");
		  sb.append(projeto.getPdrAfp() + "\t");
		  sb.append(projeto.getPdrUfp() + "\t");
		  sb.append(projeto.getNpdrAfp() + "\t");
		  sb.append(projeto.getNpduAfp() + "\t");
		  
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
		return "MODELOS/CHINA";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "CHINA";
	}

	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return WekaExperiment.M5P_1;
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.M5R;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.GP_3;
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
			classificador = WekaExperiment.BAGGING_5;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.ADABOOST_2;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.RANDOM_FOREST;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.REP_TREE_5;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.KSTAR;
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
			classificador = WekaExperiment.RANDOM_FOREST_1;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.BAGGING_5;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.LAD_TREE;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.KNN_9;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 2){
			classificador = WekaExperiment.KNN_7;
		}
		return classificador;
	}
	
	
}
