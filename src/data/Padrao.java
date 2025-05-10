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
 * Classe padr�o para os dados, que define os m�todos que precisam ser
 *  implementados por cada base. 
 *  
 *  � uma classe abstrata define m�todos e constantes usadas por outras classes do sistema.
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
	
	/** M�todo abstrato que obriga a implementa��o nas classes filhas. 
	 * Cada classe vai retorna o tamanho da base de dados representada na classe */
	public abstract int getTamanhoBase();

	/** String que os r�tulos na base de treinamento dos classificadores */
	protected String classificadores;

	/** Construtor default */
	public Padrao(){
		
		// Se a quantidade de regressores for igual a 3 crie o r�tulo do arquivo .arff com 3 op��es
		if(Utilidade.QUANTIDADE_REGRESSOR == 3){
			classificadores = "{REGRESSOR1, REGRESSOR2, REGRESSOR3}";
		}
		
		// Se a quantidade de regressores for igual a 3 crie o r�tulo do arquivo .arff com 4 op��es
		if(Utilidade.QUANTIDADE_REGRESSOR == 4){
			classificadores = "{REGRESSOR1, REGRESSOR2, REGRESSOR3, REGRESSOR4}";
		}

	}

	/** Este m�todo as inst�ncias de uma arquivo .arff para objetos de uma classe Java Projetos 
	 * 
	 * @return uma lista de objetos da classe Projetos
	 * @param padrao identifica um objeto da classe Padrao, o objeto vai ser sempre referente a uma classe filha de Padrao 
	 * 
	 * */
	public static List<Projeto> converteInstanciasParaProjetos(Padrao padrao,String nomeArquivo) throws IOException{

		// Usaremos a base de testes passada por par�metro.
		String caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS
				+ nomeArquivo;
		
		// retorna a lista de projetos convertida de acordo com a base de dados e os dados que est�o no caminho passado do arquivo passado
		return padrao.getProjetos(caminhoArquivo);
	}
	
	/** M�todo abstrato que for�a a implementa��o nas classes filhas, o m�todo vai retonar uma lista de projetos que podem ser chamados de inst�ncias da base de dados a partir do arquivo .arff informado no par�metro caminho  
	 * @param caminho arquivo � o caminho do arquivo .arff
	 * */
	protected abstract  List<Projeto> getProjetos(String caminhoArquivo) throws FileNotFoundException, IOException;
	
	/** M�todo abstrato que for�a a implementa��o nas classes filhas,  */
	public abstract void imprimirProjetos(List<Projeto> listaProjetos);

	/* O KNORA precisa de um par�metro para saber se o m�todo base acertou ou errou, 
	quando o m�todo base � um classificador � simples porque estamos trabalhando com valores categ�ricos, mas quando o m�todo base � um regressor � preciso definir um limiar de erro para saber se o m�todo base acertou. 
	Esse limiar � calculado a partir dos erros m�dios de cada m�todo base.*/   

	/** Retorna a m�dia do erro absoluto para o knora utilizar como valor de compara��o. */
	public double getKnoraMediaMar() {
		return knoraMediaMar;
	}

	/** Configura a m�dia do erro absoluto para o knora utilizar como valor de compara��o. */
	public void setKnoraMediaMar(double knoraMediaMar) {
		this.knoraMediaMar = knoraMediaMar;
	}

	/** Retorna a m�dia do erro logaritimico absoluto para o knora utilizar como valor de compara��o. */
	public double getKnoraMediaMarlog() {
		return knoraMediaMarlog;
	}

	/** Configura a m�dia do erro absoluto logaritmico para o knora utilizar como valor de compara��o. */
	public void setKnoraMediaMarlog(double knoraMediaMarlog) {
		this.knoraMediaMarlog = knoraMediaMarlog;
	}

	/** Retorna a m�dia do erro relativo para o knora utilizar como valor de compara��o. */
	public double getKnoraMediaMre() {
		return knoraMediaMre;
	}

	/** Configura a m�dia do erro relativo para o knora utilizar como valor de compara��o. */
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
