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

/**Padr�o Coc81 com todos os atributos
 * */
public class PadraoTaxaReprovacaoMedio extends Padrao {

	private static final int INDICE_ROTULO = 10;
	private int numeroEnsemble = 0;
	private static final int QTDE_PROJETOS = 1012;

	public static double KNORA_MEDIA_MAR = 0;
	public static double KNORA_MEDIA_MARLOG = 0;
	public static double KNORA_MEDIA_MREAJUSTADO = 0;	

	public PadraoTaxaReprovacaoMedio() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMreajustado = KNORA_MEDIA_MREAJUSTADO;
	}

	public PadraoTaxaReprovacaoMedio(int numeroEnsemble) {
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
			projeto.setAfd1(instancias.instance(contador).value(indice++));
			projeto.setAfd3(instancias.instance(contador).value(indice++));
			projeto.setAfd4(instancias.instance(contador).value(indice++));
			projeto.setAtu(instancias.instance(contador).value(indice++));
			projeto.setIed3(instancias.instance(contador).value(indice++));
			projeto.setIed4(instancias.instance(contador).value(indice++));
			projeto.setIed5(instancias.instance(contador).value(indice++));
			projeto.setIed6(instancias.instance(contador).value(indice++));
			projeto.setIrd(instancias.instance(contador).value(indice++));
			projeto.setTdi(instancias.instance(contador).value(indice++));
			projeto.setTr(instancias.instance(contador).value(indice++));
			
			listaProjetos.add(projeto);

		}
		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		
		for (Projeto projeto : listaProjetos) {

			System.out.print(projeto.getAfd1() + "\t");
			System.out.print(projeto.getAfd3() + "\t");
			System.out.print(projeto.getAfd4() + "\t");
			System.out.print(projeto.getAtu() + "\t");
			System.out.print(projeto.getIed3() + "\t");
			System.out.print(projeto.getIed4() + "\t");
			System.out.print(projeto.getIed5() + "\t");
			System.out.print(projeto.getIed6() + "\t");
			System.out.print(projeto.getIrd() + "\t");
			System.out.print(projeto.getTdi() + "\t");
			System.out.print(projeto.getTr() + "\t");
			System.out.print(projeto.getMenorErro() + "\t");
			System.out.println(projeto.getMelhorAlgoritmo());
		}
		
	}

	@Override
	public String toString() {
		return "TAXA_REPROVACAO_MEDIO/TAXA_REPROVACAO_MEDIO";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}

	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "TAXA_REPROVACAO_MEDIO/TAXA_REPROVACAO_MEDIO.ARFF";
	}

	@Override
	public String getCabecalho() {
		// TODO Auto-generated method stub
		return "@relation taxa_reprovacao_medio \n\n" +

		"@attribute afd1 numeric \n" +
		"@attribute afd3 numeric \n" + 
		"@attribute afd4 numeric \n" +
		"@attribute atu numeric \n" +
		"@attribute ied3 numeric \n" +
		"@attribute ied4 numeric \n" +
		"@attribute ied5 numeric \n" +
		"@attribute ied6 numeric \n" +
		"@attribute ird numeric \n" +
		"@attribute tdi numeric \n" +
		"@attribute ta numeric \n" +
		"@data \n\n";

	}
	
	
	@Override
	public String getCabecalhoClassificacao() {
		// TODO Auto-generated method stub
		return "@relation taxa_aprovacao_medio \n\n" +

		"@attribute afd1 numeric \n" +
		"@attribute afd3 numeric \n" + 
		"@attribute afd4 numeric \n" +
		"@attribute atu numeric \n" +
		"@attribute ied3 numeric \n" +
		"@attribute ied4 numeric \n" +
		"@attribute ied5 numeric \n" +
		"@attribute ied6 numeric \n" +
		"@attribute ird numeric \n" +
		"@attribute tdi numeric \n" +
		"@attribute winner" + classificadores + "\n\n" +
		"@data \n\n";
	}

	@Override
	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento) {
		
		StringBuilder sb = new StringBuilder();

		sb.append(projeto.getAfd1()+ ",\t");
		sb.append(projeto.getAfd3() + ",\t");
		sb.append(projeto.getAfd4() + ",\t");
		sb.append(projeto.getAtu() + ",\t");
		sb.append(projeto.getIed3() + ",\t");
		sb.append(projeto.getIed4() + ",\t");
		sb.append(projeto.getIed5() + ",\t");
		sb.append(projeto.getIed6() + ",\t");
		sb.append(projeto.getIrd() + ",\t");
		sb.append(projeto.getTdi() + ",\t");

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
		return "MODELOS/TAXA_REPROVACAO_MEDIO";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "TAXA_REPROVACAO_MEDIO";
	}

	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return WekaExperiment.SUPPORT_VECTOR_REGRESSION_1;
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.LEAST_MED_SQ_2;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.GP;
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
			classificador = WekaExperiment.KSTAR;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KNN_4;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.REP_TREE_2;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.MLP_3;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.NAIVE_BAYES;
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
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade3(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

}
