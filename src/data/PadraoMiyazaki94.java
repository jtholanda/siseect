package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Projeto;
import util.Constantes;
import util.TipoValidacao;
import util.WekaExperiment;
import weka.core.Instances;

/**Padrão CocomoNasa V1 normal com todos atributos
 * */
public class PadraoMiyazaki94 extends Padrao {

	private static final int INDICE_ROTULO = 7;
	private static final int QTDE_PROJETOS = 48;

	public static final double KNORA_MEDIA_MAR = 62;
	public static final double KNORA_MEDIA_MARLOG = 1.29;
	public static final double KNORA_MEDIA_MREAJUSTADO = 1.66;	

	public PadraoMiyazaki94() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMreajustado = KNORA_MEDIA_MREAJUSTADO;
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
			projeto.setKloc(instancias.instance(contador).value(indice++));
			projeto.setScrn((int)instancias.instance(contador).value(indice++));
			projeto.setForm((int)instancias.instance(contador).value(indice++));
			projeto.setFile((int)instancias.instance(contador).value(indice++));
			projeto.setEscrn((int)instancias.instance(contador).value(indice++));
			projeto.setEform((int)instancias.instance(contador).value(indice++));
			projeto.setEfile((int)instancias.instance(contador).value(indice++));
			projeto.setMm(instancias.instance(contador).value(indice++));
			
			listaProjetos.add(projeto);

		}
		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		
		for (Projeto projeto : listaProjetos) {

			System.out.print(projeto.getKloc() + "\t");
			System.out.print(projeto.getScrn() + "\t");
			System.out.print(projeto.getForm() + "\t");
			System.out.print(projeto.getFile() + "\t");
			System.out.print(projeto.getEscrn() + "\t");
			System.out.print(projeto.getEform() + "\t");
			System.out.print(projeto.getEfile() + "\t");
			System.out.print(projeto.getMm() + "\t");
			System.out.print(projeto.getMenorErro() + "\t");
			System.out.println(projeto.getMelhorAlgoritmo());
		}
		
	}

	@Override
	public String toString() {
		return "MIYAZAKI94/MIYAZAKI94";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}

	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "MIYAZAKI94/MIYAZAKI94.ARFF";
	}
	
	@Override
	public String getCabecalho() {
		// TODO Auto-generated method stub
		return "@relation miyazaki94 \n\n" +

		"@attribute KLOC numeric \n" +
		"@attribute SCRN numeric \n" +
		"@attribute FORM numeric \n" +
		"@attribute FILE numeric \n" +
		"@attribute ESCRN numeric \n" +
		"@attribute EFORM numeric \n" +
		"@attribute EFILE numeric \n" +
		"@attribute MM numeric \n" +

		"@data \n\n";

	}
	
	
	@Override
	public String getCabecalhoClassificacao() {
		// TODO Auto-generated method stub
		return "@relation miyazaki94 \n\n" +

		"@attribute KLOC numeric \n" +
		"@attribute SCRN numeric \n" +
		"@attribute FORM numeric \n" +
		"@attribute FILE numeric \n" +
		"@attribute ESCRN numeric \n" +
		"@attribute EFORM numeric \n" +
		"@attribute EFILE numeric \n" +
		"@attribute winner" + classificadores + "\n\n" +

		"@data \n\n";

	}

	@Override
	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento) {
		
		StringBuilder sb = new StringBuilder();

		sb.append(projeto.getKloc() + ",\t");
		sb.append(projeto.getScrn() + ",\t");
		sb.append(projeto.getForm() + ",\t");
		sb.append(projeto.getFile() + ",\t");
		sb.append(projeto.getEscrn() + ",\t");
		sb.append(projeto.getEform() + ",\t");
		sb.append(projeto.getEfile() + ",\t");

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
		return super.getTipoValidacao();
	}


	@Override
	public String getCaminhoModeloPadrao() {
		// TODO Auto-generated method stub
		return "MODELOS/MYIAZAKI94";
	}


	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "MIYAZAKI94";
	}


	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return WekaExperiment.SUPPORT_VECTOR_REGRESSION_1;
	}


	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.LEAST_MED_SQ_5;
	}


	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.KNN_2;
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
		if(folds == 20){
			classificador = WekaExperiment.KNN_1;
		}
		return classificador;	
		}


	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.REP_TREE_1;
		}
		return classificador;	}


	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.SVM;
		}
		return classificador;	}


	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.ADABOOST_1;
		}
		return classificador;	}


	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.DECISION_TABLE;
		}
		return classificador;	}


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
		if(folds == 20){
			classificador = WekaExperiment.REP_TREE_5;
		}
		return classificador;		
	}


	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.KNN_2;
		}
		return classificador;		
	}


	@Override
	public Integer getClassificadorComProbabilidade3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.KNN_5;
		}
		return classificador;		
	}


	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.KSTAR;
		}
		return classificador;		
	}


	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 20){
			classificador = WekaExperiment.ADABOOST_3;
		}
		return classificador;		
	}

}
