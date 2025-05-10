package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.TipoValidacao;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import weka.core.Instances;
import model.Projeto;

/**Padrão Coc81 com todos os atributos
 * */
public class PadraoCocomo81 extends Padrao {

	private static final int INDICE_ROTULO = 17;
	private int numeroEnsemble = 0;
	private static final int QTDE_PROJETOS = 63;

	public static double KNORA_MEDIA_MAR = 693;
	public static double KNORA_MEDIA_MARLOG = 2.35;
	public static double KNORA_MEDIA_MREAJUSTADO = 9.16;	

	public PadraoCocomo81() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMreajustado = KNORA_MEDIA_MREAJUSTADO;
	}

	public PadraoCocomo81(int numeroEnsemble) {
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
			projeto.setDevMode(instancias.instance(contador).stringValue(indice++));
			projeto.setRely(instancias.instance(contador).value(indice++));
			projeto.setData(instancias.instance(contador).value(indice++));
			projeto.setCplx(instancias.instance(contador).value(indice++));
			projeto.setTime(instancias.instance(contador).value(indice++));
			projeto.setStor(instancias.instance(contador).value(indice++));
			projeto.setVirt(instancias.instance(contador).value(indice++));
			projeto.setTurn(instancias.instance(contador).value(indice++));
			projeto.setAcap(instancias.instance(contador).value(indice++));
			projeto.setAexp(instancias.instance(contador).value(indice++));
			projeto.setPcap(instancias.instance(contador).value(indice++));
			projeto.setVexp(instancias.instance(contador).value(indice++));
			projeto.setLexp(instancias.instance(contador).value(indice++));
			projeto.setModp(instancias.instance(contador).value(indice++));
			projeto.setTool(instancias.instance(contador).value(indice++));
			projeto.setSced(instancias.instance(contador).value(indice++));
			projeto.setLoc(instancias.instance(contador).value(indice++));
			projeto.setActEffort(instancias.instance(contador).value(indice++));
			
			listaProjetos.add(projeto);

		}
		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		
		for (Projeto projeto : listaProjetos) {

			System.out.print(projeto.getDevMode() + "\t");
			System.out.print(projeto.getRely() + "\t");
			System.out.print(projeto.getData() + "\t");
			System.out.print(projeto.getCplx() + "\t");
			System.out.print(projeto.getTime() + "\t");
			System.out.print(projeto.getStor() + "\t");
			System.out.print(projeto.getVirt() + "\t");
			System.out.print(projeto.getTurn() + "\t");
			System.out.print(projeto.getAcap() + "\t");
			System.out.print(projeto.getAexp() + "\t");
			System.out.print(projeto.getPcap() + "\t");
			System.out.print(projeto.getVexp() + "\t");
			System.out.print(projeto.getLexp() + "\t");
			System.out.print(projeto.getModp() + "\t");
			System.out.print(projeto.getTool() + "\t");
			System.out.print(projeto.getSced() + "\t");
			System.out.print(projeto.getLoc() + "\t");
			System.out.print(projeto.getActEffort() + "\t");
			System.out.print(projeto.getMenorErro() + "\t");
			System.out.println(projeto.getMelhorAlgoritmo());
		}
		
	}

	@Override
	public String toString() {
		return "COCOMO81/COCOMO81";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}

	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "COCOMO81/COCOMO81.ARFF";
	}

	@Override
	public String getCabecalho() {
		// TODO Auto-generated method stub
		return "@relation cocomo81.csv \n\n" +

		"@attribute dev_mode {organic,embedded,semidetached} \n" +
		"@attribute rely numeric \n" + 
		"@attribute data numeric \n" +
		"@attribute cplx numeric \n" +
		"@attribute time numeric \n" +
		"@attribute stor numeric \n" +
		"@attribute virt numeric \n" +
		"@attribute turn numeric \n" +
		"@attribute acap numeric \n" +
		"@attribute aexp numeric \n" +
		"@attribute pcap numeric \n" +
		"@attribute vexp numeric \n" +
		"@attribute lexp numeric \n" +
		"@attribute modp numeric \n" +
		"@attribute tool numeric \n" +
		"@attribute sced numeric \n" +
		"@attribute loc numeric \n" +
		"@attribute act_effort numeric \n" +
		"@data \n\n";

	}
	
	
	@Override
	public String getCabecalhoClassificacao() {
		// TODO Auto-generated method stub
		return "@relation cocomo81.csv \n\n" +

		"@attribute dev_mode {organic,embedded,semidetached} \n" +
		"@attribute rely numeric \n" + 
		"@attribute data numeric \n" +
		"@attribute cplx numeric \n" +
		"@attribute time numeric \n" +
		"@attribute stor numeric \n" +
		"@attribute virt numeric \n" +
		"@attribute turn numeric \n" +
		"@attribute acap numeric \n" +
		"@attribute aexp numeric \n" +
		"@attribute pcap numeric \n" +
		"@attribute vexp numeric \n" +
		"@attribute lexp numeric \n" +
		"@attribute modp numeric \n" +
		"@attribute tool numeric \n" +
		"@attribute sced numeric \n" +
		"@attribute loc numeric \n" +
		"@attribute winner" + classificadores + "\n\n" +
		"@data \n\n";
	}

	@Override
	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento) {
		
		StringBuilder sb = new StringBuilder();

		sb.append(projeto.getDevMode()+ ",\t");
		sb.append(projeto.getRely() + ",\t");
		sb.append(projeto.getData() + ",\t");
		sb.append(projeto.getCplx() + ",\t");
		sb.append(projeto.getTime() + ",\t");
		sb.append(projeto.getStor() + ",\t");
		sb.append(projeto.getVirt() + ",\t");
		sb.append(projeto.getTurn() + ",\t");
		sb.append(projeto.getAcap() + ",\t");
		sb.append(projeto.getAexp() + ",\t");
		sb.append(projeto.getPcap() + ",\t");
		sb.append(projeto.getVexp() + ",\t");
		sb.append(projeto.getLexp() + ",\t");
		sb.append(projeto.getModp() + ",\t");
		sb.append(projeto.getTool() + ",\t");
		sb.append(projeto.getSced() + ",\t");
		sb.append(projeto.getLoc() + ",\t");

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
		return "MODELOS/COCOMO81";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "COCOMO81";
	}

	public Integer getR1(){
		return WekaExperiment.LEAST_MED_SQ_4;
		
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.M5P_4;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.KNN_4;
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
		return classificador;
	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KNN_2;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KSTAR;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.RANDOM_FOREST;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.REP_TREE_5;
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
			classificador = WekaExperiment.ADABOOST_3;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.RANDOM_FOREST;
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
			classificador = WekaExperiment.KNN_8;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.ADABOOST_2;
		}
		return classificador;
	}
}
