package util;

import java.io.File;

import weka.classifiers.Classifier;

/**
 * 
 * Classe que define o caminho do conjunto de dados que serão testados na
 * aplicação
 * 
 * */

public class Constantes {
	/**
	 * Caminho do conjunto de dados 01 - Dados - Vazios Preenchidos e
	 * Transformados com CS e Web completos sem arquitetura vazio e atributos
	 * desnecessários
	 */
	//public static final String CAMINHO_PADRAO = "F:/Dropbox/Documentos/Doutorado/Tese/Dados/Novos/Esforço/";
	public static final String CAMINHO_PADRAO_DADOS = new File("").getAbsolutePath() + File.separator + "DADOS" + File.separator;
	public static final String CAMINHO_PADRAO_METRICAS = new File("").getAbsolutePath() + File.separator + "MÉTRICAS" + File.separator;
	public static final boolean IMPRIMIR_MELHOR_ALGORITMO = false;
	//public static final boolean IMPRIMIR_RESULTADO_INDIVIDUAL = true;
	//public static final boolean IMPRIMIR_ERRO_EXEMPLO = false;
	public static final boolean IMPRIMIR_ESTIMATIVAS_DINAMICAS = false;
	public static final boolean IMPRIMIR_ESTIMATIVAS = false;
	public static final boolean BASE_VALIDACAO = false;
	public static final int INDICE_INICIAL = 1;
	public static final int INDICE_FINAL = 50;
	public static final int FOLD = 5;
	public static final int TAMANHO_LIMITE_LEAVE_ONE_OUT = 350;
	public static final double TAMANHO_TREINAMENTO = 0.5;
	public static final double TAMANHO_VALIDACAO = 0.25;
	public static final String MAR = "MAR";
	public static final String MARLOG = "MARLOG";
	public static final String MREAJUSTADO = "MREAJUSTADO";
	public static final String MRE = "MRE";
	public static final String BRE = "BRE";
	public static final String MSE = "MSE";
	public static final String RMSE = "RMSE";
	public static final String RMSLE = "RMSLE";
	public static final String R2 = "R2";
	public static final String RG = "RG";
	public static final boolean IMPRIMIR_RELATORIO_RESULTADO_DINAMICO_IDEAL = false;




}
