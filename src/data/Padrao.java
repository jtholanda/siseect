package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.Projeto;
import util.Constantes;
import util.TipoValidacao;
import util.Utilidade;
import weka.classifiers.Classifier;

/**
 * Classe padrão para os dados, que define os métodos que precisam ser
 *  implementados por cada base. 
 *  
 *  É uma classe abstrata define métodos e constantes usadas por outras classes do sistema.
 * 
 * */
public abstract class Padrao {

	public abstract String getNomeArquivo();
	public abstract String getCabecalho(); 
	public abstract String getCabecalhoClassificacao();
	public abstract String getLinhaClassificacao(Projeto projeto, boolean isTreinamento);
	
	public abstract int getIndiceRotuloClassificador();
	
	public static int ENSEMBLE_01 = 1;
	public static int ENSEMBLE_02 = 2;
	public static int ENSEMBLE_03 = 3;
	public static int ENSEMBLE_04 = 4;
	public static int ENSEMBLE_05 = 5;
	public static int ENSEMBLE_06 = 6;
	public static int ENSEMBLE_07 = 7;

	protected double knoraMediaMar = 0;
	protected double knoraMediaMarlog = 0;
	protected double knoraMediaMre = 0;	
	protected double knoraMediaBre = 0;
	protected double knoraMediaMreajustado = 0;	
	
	/** Método abstrato que obriga a implementação nas classes filhas. 
	 * Cada classe vai retorna o tamanho da base de dados representada na classe */
	public abstract int getTamanhoBase();

	/** String que os rótulos na base de treinamento dos classificadores */
	protected String classificadores;

	/** Construtor default */
	public Padrao(){
		
		// Se a quantidade de regressores for igual a 3 crie o rótulo do arquivo .arff com 3 opções
		if(Utilidade.QUANTIDADE_REGRESSOR == 3){
			classificadores = "{REGRESSOR1, REGRESSOR2, REGRESSOR3}";
		}
		
		// Se a quantidade de regressores for igual a 3 crie o rótulo do arquivo .arff com 4 opções
		if(Utilidade.QUANTIDADE_REGRESSOR == 4){
			classificadores = "{REGRESSOR1, REGRESSOR2, REGRESSOR3, REGRESSOR4}";
		}

	}

	/** Este método as instâncias de uma arquivo .arff para objetos de uma classe Java Projetos 
	 * 
	 * @return uma lista de objetos da classe Projetos
	 * @param padrao identifica um objeto da classe Padrao, o objeto vai ser sempre referente a uma classe filha de Padrao 
	 * 
	 * */
	public static List<Projeto> converteInstanciasParaProjetos(Padrao padrao,String nomeArquivo) throws IOException{

		// Usaremos a base de testes passada por parâmetro.
		String caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS
				+ nomeArquivo;
		
		// retorna a lista de projetos convertida de acordo com a base de dados e os dados que estão no caminho passado do arquivo passado
		return padrao.getProjetos(caminhoArquivo);
	}
	
	/** Método abstrato que força a implementação nas classes filhas, o método vai retonar uma lista de projetos que podem ser chamados de instâncias da base de dados a partir do arquivo .arff informado no parâmetro caminho  
	 * @param caminho arquivo é o caminho do arquivo .arff
	 * */
	protected abstract  List<Projeto> getProjetos(String caminhoArquivo) throws FileNotFoundException, IOException;
	
	/** Método abstrato que força a implementação nas classes filhas,  */
	public abstract void imprimirProjetos(List<Projeto> listaProjetos);

	/* O KNORA precisa de um parâmetro para saber se o método base acertou ou errou, 
	quando o método base é um classificador é simples porque estamos trabalhando com valores categóricos, mas quando o método base é um regressor é preciso definir um limiar de erro para saber se o método base acertou. 
	Esse limiar é calculado a partir dos erros médios de cada método base.*/   

	/** Retorna a média do erro absoluto para o knora utilizar como valor de comparação. */
	public double getKnoraMediaMar() {
		return knoraMediaMar;
	}

	/** Configura a média do erro absoluto para o knora utilizar como valor de comparação. */
	public void setKnoraMediaMar(double knoraMediaMar) {
		this.knoraMediaMar = knoraMediaMar;
	}

	/** Retorna a média do erro logaritimico absoluto para o knora utilizar como valor de comparação. */
	public double getKnoraMediaMarlog() {
		return knoraMediaMarlog;
	}

	/** Configura a média do erro absoluto logaritmico para o knora utilizar como valor de comparação. */
	public void setKnoraMediaMarlog(double knoraMediaMarlog) {
		this.knoraMediaMarlog = knoraMediaMarlog;
	}

	/** Retorna a média do erro relativo para o knora utilizar como valor de comparação. */
	public double getKnoraMediaMre() {
		return knoraMediaMre;
	}

	/** Configura a média do erro relativo para o knora utilizar como valor de comparação. */
	public void setKnoraMediaMre(double knoraMediaMre) {
		this.knoraMediaMre = knoraMediaMre;
	}
	public double getKnoraMediaBre() {
		return knoraMediaBre;
	}
	public void setKnoraMediaBre(double knoraMediaBre) {
		this.knoraMediaBre = knoraMediaBre;
	}
	public double getKnoraMediaMreajustado() {
		return knoraMediaMreajustado;
	}
	public void setKnoraMediaMreajustado(double knoraMediaMreajustado) {
		this.knoraMediaMreajustado = knoraMediaMreajustado;
	}
	
	
	public TipoValidacao getTipoValidacao(){
		if ((getTamanhoBase() > Constantes.TAMANHO_LIMITE_LEAVE_ONE_OUT) || (getTamanhoBase() == 0)){
			return TipoValidacao.HOLD_OUT;
		}else{
			return TipoValidacao.LEAVE_ONE_OUT;
		}

	}
	
	public abstract String getCaminhoModeloPadrao();
	
	public abstract String getNomePadrao();

	public abstract Integer getR1();
	public abstract Integer getR2();
	public abstract Integer getR3();
	public abstract Integer getR4();
	public abstract Integer getR5();
	
	public abstract Integer getClassificador1(int folds);
	public abstract Integer getClassificador2(int folds);
	public abstract Integer getClassificador3(int folds);
	public abstract Integer getClassificador4(int folds);
	public abstract Integer getClassificador5(int folds);
	public abstract Integer getClassificador6(int folds);
	public abstract Integer getClassificador7(int folds);
	public abstract Integer getClassificador8(int folds);
	public abstract Integer getClassificador9(int folds);
	public abstract Integer getClassificador10(int folds);
	public abstract Integer getClassificadorComProbabilidade1(int folds);
	public abstract Integer getClassificadorComProbabilidade2(int folds);
	public abstract Integer getClassificadorComProbabilidade3(int folds);
	public abstract Integer getClassificadorComProbabilidade4(int folds);
	public abstract Integer getClassificadorComProbabilidade5(int folds);
	
}
