package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weka.classifiers.Classifier;
import data.Padrao;
import model.Resultado;

public class Utilidade {

	public static final String ENSEMBLE_1 = "_E1"; 
	public static final String ENSEMBLE_2 = "_E2"; 
	public static final String ENSEMBLE_3 = "_E3"; 
	public static final String ENSEMBLE_4 = "_E4"; 
	public static final String ENSEMBLE_5 = "_E5"; 
	public static final String ENSEMBLE_6 = "_E6"; 
	public static final String ENSEMBLE_7 = "_E7";
	public static boolean ADICIONAR_RESULTADOS_INDIVIDUAIS = true; // controla se os resultados de cada modelo vai ser adicionado no arquivo de resultados ou apresentado no console 
	public static String METRICA_AVALIACAO = null; // métrica padrão da avaliação nos ensembles dinâmicos knora
	public static int QUANTIDADE_CLASSIFICADOR = 0;
	public static int QUANTIDADE_REGRESSOR = 0;
	public static TipoMetricaAvaliacao TIPO_METRICA_AVALIACAO = null; // usado no arquivo de dados para saber onde o classificador vai aprender
	public static Classifier REGRESSOR_COMPARATIVO = new WekaExperiment().createClassifier(WekaExperiment.ZEROR);
	public static int FOLD_TREINAMENTO_CLASSIFICADOR = 1;

	
	// lista de erros das iterações, apresenta a média das iterações de todos os métodos
	public static ArrayList<String> RESULTADOS_METRICA_MAR = new ArrayList<>(); 
	public static ArrayList<String> RESULTADOS_METRICA_MARLOG = new ArrayList<>(); 
	public static ArrayList<String> RESULTADOS_METRICA_MREAJUSTADO = new ArrayList<>();
	public static ArrayList<String> RESULTADOS_METRICA_MRE = new ArrayList<String>();
	public static ArrayList<String> RESULTADOS_METRICA_BRE = new ArrayList<String>();
	public static ArrayList<String> RESULTADOS_METRICA_MSE = new ArrayList<String>();
	public static ArrayList<String> RESULTADOS_METRICA_RMSE = new ArrayList<String>();
	public static ArrayList<String> RESULTADOS_METRICA_RMSLE = new ArrayList<String>();
	public static ArrayList<String> RESULTADOS_METRICA_R2 = new ArrayList<String>();
	public static ArrayList<String> RESULTADOS_METRICA_RG = new ArrayList<String>();
	
	// lista das médias dos valores das iterações do experimento corrente
	public static ArrayList<String> AUXILIAR_METRICA_MAR = new ArrayList<>();
	public static ArrayList<String> AUXILIAR_METRICA_MARLOG = new ArrayList<>();
	public static ArrayList<String> AUXILIAR_METRICA_MREAJUSTADO = new ArrayList<>();
	public static ArrayList<String> AUXILIAR_METRICA_MRE = new ArrayList<String>();
	public static ArrayList<String> AUXILIAR_METRICA_BRE = new ArrayList<String>();
	public static ArrayList<String> AUXILIAR_METRICA_MSE = new ArrayList<String>();
	public static ArrayList<String> AUXILIAR_METRICA_RMSE = new ArrayList<String>();
	public static ArrayList<String> AUXILIAR_METRICA_RMSLE = new ArrayList<String>();
	public static ArrayList<String> AUXILIAR_METRICA_R2 = new ArrayList<String>();
	public static ArrayList<String> AUXILIAR_METRICA_RG = new ArrayList<String>();
	
	// lista das médias apresentadas para cada base de dados
	public static ArrayList<String> MEDIA_CONJUNTO_MAR = new ArrayList<>();
	public static ArrayList<String> MEDIA_CONJUNTO_MARLOG = new ArrayList<>();
	public static ArrayList<String> MEDIA_CONJUNTO_MREAJUSTADO = new ArrayList<>();
	public static ArrayList<String> MEDIA_CONJUNTO_MRE = new ArrayList<String>();
	public static ArrayList<String> MEDIA_CONJUNTO_BRE = new ArrayList<String>();
	public static ArrayList<String> MEDIA_CONJUNTO_MSE = new ArrayList<String>();
	public static ArrayList<String> MEDIA_CONJUNTO_RMSE = new ArrayList<String>();
	public static ArrayList<String> MEDIA_CONJUNTO_RMSLE = new ArrayList<String>();
	public static ArrayList<String> MEDIA_CONJUNTO_R2 = new ArrayList<String>();
	public static ArrayList<String> MEDIA_CONJUNTO_RG = new ArrayList<String>();
	
	public static String AVALIACAO = "";
	public static boolean ATIVA_PROBABILIDADES = false;
	
	
	
	// métodos para obter os caminhos dos arquivos baseado na validação hold-out
	public static String getCaminhoTreinamento(String nomeAlgoritmoModelo, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_TREINAMENTO_"+ (i) + ".arff";
	}

	public static String getCaminhoValidacao(String nomeAlgoritmoModelo,Padrao padrao, int i) {
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_VALIDACAO_" + (i) + ".arff";
	}
	
	public static String getCaminhoTeste(String nomeAlgoritmoModelo, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_TESTE_" + (i) + ".arff";
	}
	
	public static String getCaminhoTreinamentoClassificacao(String nomeAlgoritmoModelo, Padrao padrao, int quantidadeRegressores, TipoMetricaAvaliacao metricaAvaliacao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_" + quantidadeRegressores + "_TREINAMENTO_CLASSIFICADOR_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" +  metricaAvaliacao.getNome() + "_" + (i) + ".arff";
	}
	
	public static String getCaminhoTesteClassificacao(String nomeAlgoritmoModelo, int quantidadeRegressores, Padrao padrao, int i){
		return padrao.toString() + "_" +  quantidadeRegressores + "_TESTE_CLASSIFICADOR_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" +  (i) + ".arff";
	}
	
	public static String getCaminhoValidacaoClassificacao(String nomeAlgoritmoModelo, int quantidadeRegressores, Padrao padrao, int i){
		return padrao.toString() + "_" +  quantidadeRegressores + "_VALIDACAO_CLASSIFICADOR_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" + (i) + ".arff";
	}
	
	public static String getCaminhoTreinamentoEValidacao(String nomeAlgoritmoModelo, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_TREINAMENTO_VALIDACAO_" + (i) + ".arff";
	}

	public static String getCaminhoTreinamentoClassificacaoComValidacao(String nomeAlgoritmoModelo, Padrao padrao, int quantidadeRegressores, TipoMetricaAvaliacao metricaAvaliacao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_" + quantidadeRegressores + "_TREINAMENTO_CLASSIFICADOR_VALIDACAO_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" +  metricaAvaliacao.getNome() + "_" + (i) + ".arff";
	}

	// métodos para obter os caminhos dos arquivos baseados na validação leave-one-out
	public static String getCaminhoTreinamentoLeaveOneOut(String nomeAlgoritmoModelo, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_TREINAMENTO_LOO_" + (i) + ".arff";
	}
	
	public static String getCaminhoValidacaoLeaveOneOut(String nomeAlgoritmoModelo, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_VALIDACAO_LOO_" + (i) + ".arff";
	}
	
	public static String getCaminhoTesteLeaveOneOut(String nomeAlgoritmoModelo, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_TESTE_LOO_" + (i) + ".arff";
	}
	
	public static String getCaminhoTreinamentoClassificacaoLeaveOneOut(String nomeAlgoritmoModelo, Padrao padrao, int quantidadeRegressores, TipoMetricaAvaliacao metricaAvaliacao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_" + quantidadeRegressores + "_TREINAMENTO_CLASSIFICADOR_LOO_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" +  metricaAvaliacao.getNome() + "_" + (i) + ".arff";
	}
	
	public static String getCaminhoTesteClassificacaoLeaveOneOut(String nomeAlgoritmoModelo, int quantidadeRegressores, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_" + quantidadeRegressores + "_TESTE_CLASSIFICADOR_LOO_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" + (i) + ".arff";
	}
	
	public static String getCaminhoValidacaoClassificacaoLeaveOneOut(String nomeAlgoritmoModelo, int quantidadeRegressores, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_" + quantidadeRegressores + "_VALIDACAO_CLASSIFICADOR_LOO_" + Utilidade.FOLD_TREINAMENTO_CLASSIFICADOR + "_FOLD_" + (i) + ".arff";
	}

	public static String getCaminhoTreinamentoEValidacaoLeaveOneOut(String nomeAlgoritmoModelo, Padrao padrao, int i){
		return padrao.toString() + "_" + nomeAlgoritmoModelo + "_TREINAMENTO_VALIDACAO_LOO_" + (i) + ".arff";
	}

	// cria um arquivo com todos os erros do método corrente para a métrica de acordo com os resultados da lista que inclue os bancos de dados
	private static void gerarArquivoErros(String nomeDoMetodo, String metrica, ArrayList<String> resultados) {

		PrintWriter printWriter = null;
		File arquivo = new File(Constantes.CAMINHO_PADRAO_METRICAS + metrica + File.separator + metrica + "_" + nomeDoMetodo + ".txt");

		try {

			/*if(!arquivo.exists()){*/

				printWriter = new PrintWriter(arquivo);
				for(String resultado: resultados){
					printWriter.println(resultado);
				}
				printWriter.close();
			/*}else{

				bfr = Files.newBufferedReader(path, charset);
				ArrayList<String> resultados = new ArrayList<>();
				while(true){
					if(linha != null){
						linha += bfr.readLine()+"\n";
						resultados.add(linha);
					}else{
						break;
					}
				}
				printWriter = new PrintWriter(arquivo);
				for(String resultado: resultados){
					printWriter.println(resultado);
				}
				for(String resultado: texto){
					printWriter.println(resultado);
				}
				
			}*/
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}
	// gera arquivos com os resultados de cada métrica para o modelo corrente e na base de dados
	public static void gerarArquivosMassa(long tempo, String metodo){

		RESULTADOS_METRICA_MAR.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_MARLOG.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_MREAJUSTADO.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_MRE.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_BRE.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_MSE.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_RMSE.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_RMSLE.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_R2.add("\n" + "Tempo: " + tempo); 
		RESULTADOS_METRICA_RG.add("\n" + "Tempo: " + tempo); 
		
		adicionaLinhaArquivo();
		
		RESULTADOS_METRICA_MAR.addAll(MEDIA_CONJUNTO_MAR);
		RESULTADOS_METRICA_MARLOG.addAll(MEDIA_CONJUNTO_MARLOG);
		RESULTADOS_METRICA_MREAJUSTADO.addAll(MEDIA_CONJUNTO_MREAJUSTADO);
		RESULTADOS_METRICA_MRE.addAll(MEDIA_CONJUNTO_MRE);
		RESULTADOS_METRICA_BRE.addAll(MEDIA_CONJUNTO_BRE);
		RESULTADOS_METRICA_MSE.addAll(MEDIA_CONJUNTO_MSE);
		RESULTADOS_METRICA_RMSE.addAll(MEDIA_CONJUNTO_RMSE);
		RESULTADOS_METRICA_RMSLE.addAll(MEDIA_CONJUNTO_RMSLE);
		RESULTADOS_METRICA_R2.addAll(MEDIA_CONJUNTO_R2);
		RESULTADOS_METRICA_RG.addAll(MEDIA_CONJUNTO_RG);
		
		gerarArquivoErros(metodo, Constantes.MAR, Utilidade.RESULTADOS_METRICA_MAR);
		gerarArquivoErros(metodo, Constantes.MARLOG, Utilidade.RESULTADOS_METRICA_MARLOG);
		gerarArquivoErros(metodo, Constantes.MREAJUSTADO, Utilidade.RESULTADOS_METRICA_MREAJUSTADO);
		gerarArquivoErros(metodo, Constantes.MRE, Utilidade.RESULTADOS_METRICA_MRE);
		gerarArquivoErros(metodo, Constantes.BRE, Utilidade.RESULTADOS_METRICA_BRE);
		gerarArquivoErros(metodo, Constantes.MSE, Utilidade.RESULTADOS_METRICA_MSE);
		gerarArquivoErros(metodo, Constantes.RMSE, Utilidade.RESULTADOS_METRICA_RMSE);
		gerarArquivoErros(metodo, Constantes.RMSLE, Utilidade.RESULTADOS_METRICA_RMSLE);
		gerarArquivoErros(metodo, Constantes.R2, Utilidade.RESULTADOS_METRICA_R2);
		gerarArquivoErros(metodo, Constantes.RG, Utilidade.RESULTADOS_METRICA_RG);
		
	}
	// adiciona cabeçalho aos resultados impressos com o nome da base de dados
	public static void adicionaCabecalhoDados(Padrao padrao) {
		RESULTADOS_METRICA_MAR.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_MARLOG.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_MREAJUSTADO.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_MRE.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_BRE.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_MSE.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_RMSE.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_RMSLE.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_R2.add(padrao.toString() + "\n");
		RESULTADOS_METRICA_RG.add(padrao.toString() + "\n");
	}
	
	// adiciona uma linha nos arquivos de erros
	public static void adicionaLinhaArquivo() {
		RESULTADOS_METRICA_MAR.add("\n");
		RESULTADOS_METRICA_MARLOG.add("\n");
		RESULTADOS_METRICA_MREAJUSTADO.add("\n");
		RESULTADOS_METRICA_MRE.add("\n");
		RESULTADOS_METRICA_BRE.add("\n");
		RESULTADOS_METRICA_MSE.add("\n");
		RESULTADOS_METRICA_RMSE.add("\n");
		RESULTADOS_METRICA_RMSLE.add("\n");
		RESULTADOS_METRICA_R2.add("\n");
		RESULTADOS_METRICA_RG.add("\n");
	}
	
	public static void calculaValoresCentraisDasMetricas(){
		
		calculaMediasMetricas1();
		calculaMedianasMetricas();
	
	}
	public static String calcularMediaLista(ArrayList<String> lista) {

		// TODO Auto-generated method stub
		double somaDosValores = 0; 
		
		for(String valor: lista){
			somaDosValores += Double.parseDouble(valor.replace(",", "."));
		}
			
		
		return ("" + somaDosValores / lista.size()).replace(".", ",");
	}
	
	public static double calcularMediaLista(List<Double> valoresEstimados) {
		double soma = 0;
		double valorEstimado = 0;
		
		for (Double valor : valoresEstimados) {
			soma += valor;
		}
		
		valorEstimado = soma/valoresEstimados.size();
		
		return valorEstimado;
		
	}

	public static String calcularMedianaLista(ArrayList<String> lista) {

		// TODO Auto-generated method stub
		double mediana = 0;
		ArrayList<Double> valores = new ArrayList<Double>();
		
		for(String valor: lista){
			valores.add(Double.parseDouble(valor.replace(",", ".")));
		}
			
		mediana = calcularMedianaLista(valores);
		
		return ("" + mediana).replace(".", ",");
	}


	public static double calcularMedianaLista(List<Double> valoresEstimados) {
		double valorEstimado;		
		int esq=0;  
		int dir= valoresEstimados.size()-1;  
		int meio;		
		Collections.sort(valoresEstimados);
		if((esq+dir)%2 == 0){
			meio=(esq+dir)/2;  
			valorEstimado = valoresEstimados.get(meio);
		}else{
			double esquerda = 0;
			double direita = valoresEstimados.size()-1;		
			double metade = (esquerda+direita)/2;
			esq = (int)metade;
			dir = (int)Math.ceil(metade);
			valorEstimado = (valoresEstimados.get(esq) + valoresEstimados.get(dir))/2;
		}
		valoresEstimados = new ArrayList<Double>();
		return valorEstimado;
	}

	
	public static double calcularMediaPontas(List<Double> valoresEstimados) {

		double valorEstimado = 0;
		double minimo;
		double maximo;
		
		minimo = calcularMenorValor(valoresEstimados);
		maximo = calcularMaiorValor(valoresEstimados);
		
		valorEstimado = (minimo + maximo)/2;
		
		return valorEstimado;
		
	}

	
	public double calcularMediaPonderadaNormalLista(List<Double> valoresEstimados) {
		double soma = 0;
		double valorEstimado = 0;
		
		//for (int i=0; i < valoresEstimados.size(); i++) {
		if(valoresEstimados.size() == 3){
			soma += valoresEstimados.get(0)*0.25;
			soma += valoresEstimados.get(1)*0.25;
			soma += valoresEstimados.get(2)*0.50;
		}
		if(valoresEstimados.size() == 5){
			soma += valoresEstimados.get(0)*0.15;
			soma += valoresEstimados.get(1)*0.15;
			soma += valoresEstimados.get(2)*0.2;
			soma += valoresEstimados.get(3)*0.25;
			soma += valoresEstimados.get(4)*0.25;
		}
		//}
		
		//valorEstimado = soma/valoresEstimados.size();
			valorEstimado = soma;
		
		return valorEstimado;
		
	}
	
	public static double calcularModaLista(List<Double> valoresEstimados) {

		int nVezes = 1, v = valoresEstimados.size(), i = 0;
        double moda = 0;
        int comparaV = 0;
        double M[] = new double[v];
        
        
        for (Double valor : valoresEstimados) {
			M[i] = valor.doubleValue();
			i++;
		}

        for (int p = 0; p < M.length; p++) {
            nVezes = 1;

            for (int k = p + 1; k < M.length; k++) {
                if (M[p] == M[k]) {
                    ++nVezes;
                }
            }
            if (nVezes > comparaV) {
                moda = M[p];
                comparaV = nVezes;
            }
        }

        return moda;
	}
	
	
	public static double calcularMenorValor(List<Double> valoresEstimados) {
		
		double menor = valoresEstimados.get(0);
		
		for (Double estimativa: valoresEstimados) {
			if (estimativa < menor){
				menor = estimativa;
			}			
		}
		return menor;
	}

	public static double calcularMaiorValor(List<Double> valoresEstimados) {
		
		double maior = valoresEstimados.get(0);
		
		for (Double estimativa: valoresEstimados) {
			if (estimativa > maior){
				maior = estimativa;
			}			
		}
		return maior;
	}

	public static double calcularUnicoValor(List<Double> valoresEstimados) {
		
		double unico = valoresEstimados.get(0);
		
		return unico;
	}

	public static void calculaMetricas(Resultado resultado, Double valorEstimado, Double valorReal,
			Double valorEstimadoComparativo) {
		double erroAbsoluto;
		double marlog;
		double mreAjustado;
		double mre;
		double bre;
		double menor;
		double erroQuadratico;
		double erroLogQuadratico;
		double numeradorR2;
		double denominadorR2;
		double erroGanhoRelativo;
		// calcula os erros de acordo com as métricas
		
		erroAbsoluto = Math
				.abs(valorReal - (valorEstimado));
		
		marlog = Math.log10(erroAbsoluto); 
				
					
		mreAjustado = ((erroAbsoluto / valorReal) + (erroAbsoluto / Math.abs(valorEstimado))/2);
		
		
		mre = erroAbsoluto / valorReal;
		
		if (valorReal < Math.abs(valorEstimado)){
			menor = valorReal;
		}else{
			menor = Math.abs(valorEstimado);
		}
		
		bre = erroAbsoluto / menor;

		erroQuadratico = (valorEstimado - valorReal) * (valorEstimado - valorReal); 
		
		erroLogQuadratico = Math.pow(Math.log(valorReal+1) - Math.log(Math.abs(valorEstimado)+1), 2);
		
		numeradorR2 = (valorReal - valorEstimado) * (valorReal - valorEstimado);
		denominadorR2 = (valorReal - valorEstimadoComparativo) * (valorReal - valorEstimadoComparativo);
		
		erroGanhoRelativo = Math.abs(valorReal - valorEstimadoComparativo);
		
		// adiciona os erros a uma lista de métricas
		
		resultado.getErrosAbsolutos().add(erroAbsoluto);
		resultado.getMarlogs().add(marlog);
		resultado.getMresAjustados().add(mreAjustado);
		resultado.getMres().add(mre);
		resultado.getBres().add(bre);
		resultado.getErrosQuadraticos().add(erroQuadratico);
		resultado.getErrosLogQuadraticos().add(erroLogQuadratico);
		resultado.getNumeradoresR2().add(numeradorR2);
		resultado.getDenominadoresR2().add(denominadorR2);
		resultado.getErrosGanhosRelativos().add(erroGanhoRelativo);
		
		
		// adiciona as estimativas do modelo			
		resultado.getEstimativas().add(valorEstimado);
		
		// adiciona os valores reais das instâncias
		resultado.getValoresReais().add(valorReal);
	}

	// calcula a média das médias das iterações de acordo com os erros do modelo corrente
	public static void calculaMediasMetricas1() {
		
		MEDIA_CONJUNTO_MAR.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_MAR));
		MEDIA_CONJUNTO_MARLOG.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_MARLOG));
		MEDIA_CONJUNTO_MREAJUSTADO.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_MREAJUSTADO));
		MEDIA_CONJUNTO_MRE.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_MRE));
		MEDIA_CONJUNTO_BRE.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_BRE));
		MEDIA_CONJUNTO_MSE.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_MSE));
		MEDIA_CONJUNTO_RMSE.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_RMSE));
		MEDIA_CONJUNTO_RMSLE.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_RMSLE));
		MEDIA_CONJUNTO_R2.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_R2));
		MEDIA_CONJUNTO_RG.add(Utilidade.calcularMediaLista(Utilidade.AUXILIAR_METRICA_RG));
	}

	// calcula a média das médias das iterações de acordo com os erros do modelo corrente
	public static void calculaMedianasMetricas() {
		
		MEDIA_CONJUNTO_MAR.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_MAR));
		MEDIA_CONJUNTO_MARLOG.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_MARLOG));
		MEDIA_CONJUNTO_MREAJUSTADO.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_MREAJUSTADO));
		MEDIA_CONJUNTO_MRE.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_MRE));
		MEDIA_CONJUNTO_BRE.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_BRE));
		MEDIA_CONJUNTO_MSE.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_MSE));
		MEDIA_CONJUNTO_RMSE.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_RMSE));
		MEDIA_CONJUNTO_RMSLE.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_RMSLE));
		MEDIA_CONJUNTO_R2.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_R2));
		MEDIA_CONJUNTO_RG.add(Utilidade.calcularMedianaLista(AUXILIAR_METRICA_RG));
	}

	// cria as lista de médias de erros que contém os resultados dos métodos para todas as bases de dados
	public static void inicializaListasMetricas() {
		
		RESULTADOS_METRICA_MAR = new ArrayList<String>();
		RESULTADOS_METRICA_MARLOG = new ArrayList<String>();
		RESULTADOS_METRICA_MREAJUSTADO = new ArrayList<String>();
		RESULTADOS_METRICA_MRE = new ArrayList<String>();
		RESULTADOS_METRICA_BRE = new ArrayList<String>();
		RESULTADOS_METRICA_MSE = new ArrayList<String>();
		RESULTADOS_METRICA_RMSE = new ArrayList<String>();
		RESULTADOS_METRICA_RMSLE = new ArrayList<String>();
		RESULTADOS_METRICA_R2 = new ArrayList<String>();
		RESULTADOS_METRICA_RG = new ArrayList<String>();
		

		MEDIA_CONJUNTO_MAR = new ArrayList<String>();
		MEDIA_CONJUNTO_MARLOG = new ArrayList<String>();
		MEDIA_CONJUNTO_MREAJUSTADO = new ArrayList<String>();
		MEDIA_CONJUNTO_MRE = new ArrayList<String>();
		MEDIA_CONJUNTO_BRE = new ArrayList<String>();
		MEDIA_CONJUNTO_MSE = new ArrayList<String>();
		MEDIA_CONJUNTO_RMSE = new ArrayList<String>();
		MEDIA_CONJUNTO_RMSLE = new ArrayList<String>();
		MEDIA_CONJUNTO_R2 = new ArrayList<String>();
		MEDIA_CONJUNTO_RG = new ArrayList<String>();
		
		
	}
	// inicializa a lista das médias dos erros auxiliares que são relacionados ao modelo corrente   
	public static void zerarMediasAuxiliares() {
		// TODO Auto-generated method stub
		AUXILIAR_METRICA_MAR = new ArrayList<>();
		AUXILIAR_METRICA_MARLOG = new ArrayList<>();
		AUXILIAR_METRICA_MREAJUSTADO = new ArrayList<>();
		AUXILIAR_METRICA_MRE = new ArrayList<String>();
		AUXILIAR_METRICA_BRE = new ArrayList<String>();
		AUXILIAR_METRICA_MSE = new ArrayList<String>();
		AUXILIAR_METRICA_RMSE = new ArrayList<String>();
		AUXILIAR_METRICA_RMSLE = new ArrayList<String>();
		AUXILIAR_METRICA_R2 = new ArrayList<String>();
		AUXILIAR_METRICA_RG = new ArrayList<String>();
	
	}


}
