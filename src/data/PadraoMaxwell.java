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

/**Padrão Maxwell com todos os atributos
 * */
public class PadraoMaxwell extends Padrao {

	private static final int INDICE_ROTULO = 26;
	private int numeroEnsemble = 0;
	private static final int QTDE_PROJETOS = 62;

	public static double KNORA_MEDIA_MAR = 4923;
	public static double KNORA_MEDIA_MARLOG = 3.39;
	public static double KNORA_MEDIA_MRE = 1.21;	
	public static double KNORA_MEDIA_BRE = 1.67;
	public static double KNORA_MEDIA_MREAJUSTADO = 1.67;	

	public PadraoMaxwell() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMre = KNORA_MEDIA_MRE;
		knoraMediaBre = KNORA_MEDIA_BRE;
		knoraMediaMreajustado = KNORA_MEDIA_BRE;
	}

	public PadraoMaxwell(int numeroEnsemble) {
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
			projeto.setSyear(instancias.instance(contador).value(indice++));
			projeto.setApp(instancias.instance(contador).value(indice++));
			projeto.setHar(instancias.instance(contador).value(indice++));
			projeto.setDba(instancias.instance(contador).value(indice++));
			projeto.setIfc(instancias.instance(contador).value(indice++));
			projeto.setSrc(instancias.instance(contador).value(indice++));
			projeto.setTelonuse(instancias.instance(contador).value(indice++));
			projeto.setNlan(instancias.instance(contador).value(indice++));
			projeto.setT01(instancias.instance(contador).value(indice++));
			projeto.setT02(instancias.instance(contador).value(indice++));
			projeto.setT03(instancias.instance(contador).value(indice++));
			projeto.setT04(instancias.instance(contador).value(indice++));
			projeto.setT05(instancias.instance(contador).value(indice++));
			projeto.setT06(instancias.instance(contador).value(indice++));
			projeto.setT07(instancias.instance(contador).value(indice++));
			projeto.setT08(instancias.instance(contador).value(indice++));
			projeto.setT09(instancias.instance(contador).value(indice++));
			projeto.setT010(instancias.instance(contador).value(indice++));
			projeto.setT011(instancias.instance(contador).value(indice++));
			projeto.setT012(instancias.instance(contador).value(indice++));
			projeto.setT013(instancias.instance(contador).value(indice++));
			projeto.setT014(instancias.instance(contador).value(indice++));
			projeto.setT015(instancias.instance(contador).value(indice++));
			projeto.setDuration(instancias.instance(contador).value(indice++));
			projeto.setSize(instancias.instance(contador).value(indice++));
			projeto.setTime(instancias.instance(contador).value(indice++));
			projeto.setEffort(instancias.instance(contador).value(indice++));
			
			listaProjetos.add(projeto);

		}
		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		for (Projeto projeto : listaProjetos) {
			  
			  System.out.print(projeto.getSyear() + "\t");
			  System.out.print(projeto.getApp() + "\t");
			  System.out.print(projeto.getHar() + "\t");
			  System.out.print(projeto.getDba() + "\t");
			  System.out.print(projeto.getIfc() + "\t");
			  System.out.print(projeto.getSrc() + "\t");
			  System.out.print(projeto.getTelonuse() + "\t");
			  System.out.print(projeto.getNlan() + "\t");  
			  System.out.print(projeto.getT01() + "\t");  
			  System.out.print(projeto.getT02() + "\t");  
			  System.out.print(projeto.getT03() + "\t");  
			  System.out.print(projeto.getT04() + "\t");  
			  System.out.print(projeto.getT05() + "\t");  
			  System.out.print(projeto.getT06() + "\t");  
			  System.out.print(projeto.getT07() + "\t");  
			  System.out.print(projeto.getT08() + "\t");  
			  System.out.print(projeto.getT09() + "\t");  
			  System.out.print(projeto.getT010() + "\t");  
			  System.out.print(projeto.getT011() + "\t");  
			  System.out.print(projeto.getT012() + "\t");  
			  System.out.print(projeto.getT013() + "\t");  
			  System.out.print(projeto.getT014() + "\t");  
			  System.out.print(projeto.getT015() + "\t");  
			  System.out.print(projeto.getDuration() + "\t");  
			  System.out.print(projeto.getSize() + "\t");  
			  System.out.print(projeto.getTime() + "\t");  
			  System.out.print(projeto.getEffort() + "\t");  
			  System.out.print(projeto.getMenorErro() + "\t");
			  System.out.println(projeto.getMelhorAlgoritmo());  
			 }
		
	}

	@Override
	public String toString() {
		return "MAXWELL/MAXWELL";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}

	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "MAXWELL/MAXWELL.ARFF";
	}
	
	@Override
	public String getCabecalho() {
		// TODO Auto-generated method stub
		return "@relation MAXWELL \n\n" +

		"@attribute	Syear	numeric \n" +
		"@attribute	App	numeric \n" +
		"@attribute	Har	numeric \n" +
		"@attribute	Dba	numeric \n" + 
		"@attribute	Ifc	numeric \n" +
		"@attribute	Source	numeric \n" +
		"@attribute	Telonuse 	numeric \n" +
		"@attribute	Nlan	numeric \n" +
		"@attribute	T01	numeric \n" +
		"@attribute	T02	numeric \n" +
		"@attribute	T03	numeric \n" +
		"@attribute	T04	numeric \n" +
		"@attribute	T05	numeric \n" +
		"@attribute	T06	numeric \n" +
		"@attribute	T07	numeric \n" +
		"@attribute	T08	numeric \n" +
		"@attribute	T09	numeric \n" +
		"@attribute	T10	numeric \n" +
		"@attribute	T11	numeric \n" +
		"@attribute	T12	numeric \n" +
		"@attribute	T13	numeric \n" +
		"@attribute	T14	numeric \n" +
		"@attribute	T15	numeric \n" +
		"@attribute	Duration	numeric \n" +
		"@attribute	Size	numeric \n" +
		"@attribute	Time	numeric \n" +
		"@attribute	Effort	numeric \n" +
		"@data \n\n";

	}
	
	
	@Override
	public String getCabecalhoClassificacao() {
		// TODO Auto-generated method stub
		return "@relation MAXWELL \n\n" +

		"@attribute	Syear	numeric \n" +
		"@attribute	App	numeric \n" +
		"@attribute	Har	numeric \n" +
		"@attribute	Dba	numeric \n" + 
		"@attribute	Ifc	numeric \n" +
		"@attribute	Source	numeric \n" +
		"@attribute	Telonuse 	numeric \n" +
		"@attribute	Nlan	numeric \n" +
		"@attribute	T01	numeric \n" +
		"@attribute	T02	numeric \n" +
		"@attribute	T03	numeric \n" +
		"@attribute	T04	numeric \n" +
		"@attribute	T05	numeric \n" +
		"@attribute	T06	numeric \n" +
		"@attribute	T07	numeric \n" +
		"@attribute	T08	numeric \n" +
		"@attribute	T09	numeric \n" +
		"@attribute	T10	numeric \n" +
		"@attribute	T11	numeric \n" +
		"@attribute	T12	numeric \n" +
		"@attribute	T13	numeric \n" +
		"@attribute	T14	numeric \n" +
		"@attribute	T15	numeric \n" +
		"@attribute	Duration	numeric \n" +
		"@attribute	Size	numeric \n" +
		"@attribute	Time	numeric \n" +
		"@attribute winner" + classificadores + "\n\n" +
		"@data \n\n";
	}

	@Override
	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento) {
		
		StringBuilder sb = new StringBuilder();

		sb.append(projeto.getSyear() + "\t");
		sb.append(projeto.getApp() + "\t");
		sb.append(projeto.getHar() + "\t");
		sb.append(projeto.getDba() + "\t");
		sb.append(projeto.getIfc() + "\t");
		sb.append(projeto.getSrc() + "\t");
		sb.append(projeto.getTelonuse() + "\t");
		sb.append(projeto.getNlan() + "\t");  
		sb.append(projeto.getT01() + "\t");  
		sb.append(projeto.getT02() + "\t");  
		sb.append(projeto.getT03() + "\t");  
		sb.append(projeto.getT04() + "\t");  
		sb.append(projeto.getT05() + "\t");  
		sb.append(projeto.getT06() + "\t");  
		sb.append(projeto.getT07() + "\t");  
		sb.append(projeto.getT08() + "\t");  
		sb.append(projeto.getT09() + "\t");  
		sb.append(projeto.getT010() + "\t");  
		sb.append(projeto.getT011() + "\t");  
		sb.append(projeto.getT012() + "\t");  
		sb.append(projeto.getT013() + "\t");  
		sb.append(projeto.getT014() + "\t");  
		sb.append(projeto.getT015() + "\t");  
		sb.append(projeto.getDuration() + "\t");  
		sb.append(projeto.getSize() + "\t");  
		sb.append(projeto.getTime() + "\t");  
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
		return "MODELOS/MAXWELL";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "MAXWELL";
	}

	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return WekaExperiment.SUPPORT_VECTOR_REGRESSION_1;
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.LEAST_MED_SQ_3;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.M5R_4;
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
		if(folds == 1){		
			classificador = WekaExperiment.ADABOOST_2;
		}
		if(folds == 2){		
			classificador = WekaExperiment.LAD_TREE;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){		
			classificador = WekaExperiment.KNN_2;
		}
		if(folds == 2){		
			classificador = WekaExperiment.J48_1;
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
		if(folds == 2){		
			classificador = WekaExperiment.BAGGING_5;
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
		if(folds == 2){		
			classificador = WekaExperiment.J_RIP;
		}
		
		return classificador;
	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){		
			classificador = WekaExperiment.KSTAR;
		}
		if(folds == 2){		
			classificador = WekaExperiment.BAGGING_3;
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
			classificador = WekaExperiment.KNN_8;
		}
		if(folds == 2){		
			classificador = 0;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){		
			classificador = WekaExperiment.ADABOOST_4;
		}
		if(folds == 2){		
			classificador = 0;
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
		if(folds == 2){		
			classificador = 0;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){		
			classificador = WekaExperiment.ADABOOST_2;
		}
		if(folds == 2){		
			classificador = 0;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){		
			classificador = WekaExperiment.ADABOOST_3;
		}
		if(folds == 2){		
			classificador = 0;
		}
		return classificador;
	}

}
