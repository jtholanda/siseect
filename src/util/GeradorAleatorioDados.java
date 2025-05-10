package util;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Projeto;
import weka.core.Instance;
import weka.core.Instances;
import data.Padrao;

public class GeradorAleatorioDados {
	
	List<Projeto> listaProjetos;
	Padrao padrao;
	String nomeAlgoritmoModelo;

	public GeradorAleatorioDados(String nomeAlgoritmoModelo, Padrao padrao) {

	}

	
	public static void gerarTreinamentoTeste(String nomeAlgoritmoModelo, Padrao padrao){
		try {

			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS);

			String nomeArquivo = padrao.getNomeArquivo();
			String caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS + nomeArquivo;
			FileReader reader = new FileReader(caminhoArquivo);
			Instances instancias = new Instances(reader);

			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;

			Instance instancia;
			List<String> linhas = new ArrayList<String>();

			for (int i = 0; i < instancias.numInstances(); i++) {

				instancia = instancias.instance(i);
				linhas.add(instancia.toString().replace(",", "\t"));
			}

			List<String> linhasTreinamento = new ArrayList<String>();
			List<String> linhasTeste = new ArrayList<String>();
			int tamanhoTreinamento = (int) (linhas.size() * Constantes.TAMANHO_TREINAMENTO);

			for (int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++) {

				Collections.shuffle(linhas);
				linhasTreinamento = new ArrayList<String>();
				linhasTeste = new ArrayList<String>();

				for (int j = 0; j < linhas.size(); j++) {

					if (j < tamanhoTreinamento) {
						linhasTreinamento.add(linhas.get(j));
					}

					if (j >= tamanhoTreinamento) {
						linhasTeste.add(linhas.get(j));
					}
				}

				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamento(nomeAlgoritmoModelo, padrao, i));
				bfw = Files.newBufferedWriter(path, charset);
				bfw.write(padrao.getCabecalho());
				bfw.newLine();

				for (int k = 0; k < linhasTreinamento.size(); k++) {
					bfw.write(linhasTreinamento.get(k));
					bfw.newLine();
				}
				bfw.flush();

				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTeste(nomeAlgoritmoModelo, padrao, i));
				bfw = Files.newBufferedWriter(path, charset);
				bfw.write(padrao.getCabecalho());
				bfw.newLine();

				for (int l = 0; l < linhasTeste.size(); l++) {
					bfw.write(linhasTeste.get(l));
					bfw.newLine();
				}
				bfw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// gera os arquivos de treinamento, validação e testes para hold-out com reamostragem
	public static void gerarTreinamentoValidacaoTeste(String nomeAlgoritmoModelo, Padrao padrao){
		try {

			
			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS);
			Path pathTreinamentoValidacao = Paths.get(Constantes.CAMINHO_PADRAO_DADOS);
			String nomeArquivo = padrao.getNomeArquivo();
			String caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS + nomeArquivo;
			FileReader reader = new FileReader(caminhoArquivo);
			Instances instancias = new Instances(reader);

			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;
			BufferedWriter bfwTreinamentoValidacao = null;

			Instance instancia;
			List<String> linhas = new ArrayList<String>();

			// itera por todas as instâncias do Instances e adiciona cada instância a uma lista de String trocando a vírgula por tabulação
			for (int i = 0; i < instancias.numInstances(); i++) {

				instancia = instancias.instance(i);
				linhas.add(instancia.toString().replace(",", "\t"));
			}

			List<String> linhasTreinamento = new ArrayList<String>();
			List<String> linhasValidacao = new ArrayList<String>();
			List<String> linhasTeste = new ArrayList<String>();
			int tamanhoTreinamento = (int) (linhas.size() * Constantes.TAMANHO_TREINAMENTO);
			int tamanhoValidacao = (int) (linhas.size() * Constantes.TAMANHO_VALIDACAO);

			// gera os arquivos de treino, validação e testes de todas as iterações
			for (int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++) {

				Collections.shuffle(linhas); // embaralha os dados
				linhasTreinamento = new ArrayList<String>();
				linhasValidacao = new ArrayList<String>();
				linhasTeste = new ArrayList<String>();

				// itera em linhas para obter os dados de treinamento, validação e testes
				for (int j = 0; j < linhas.size(); j++) {

					// os primeiros dados são atribuídos ao treinamento de acordo com o percentual definido em TAMANHO_TREINAMENTO
					if (j < tamanhoTreinamento) {
						linhasTreinamento.add(linhas.get(j));
					}

					// se o índice de j for maior do que o tamanho de treinamento e 
					// se for menor do que a soma de treinamento e validação inicia a geração dos dados de validação, senão os de testes 
					if (j >= tamanhoTreinamento) {
						
						if(j < (tamanhoTreinamento + tamanhoValidacao)){
							linhasValidacao.add(linhas.get(j));
						}else{
							linhasTeste.add(linhas.get(j));
						}
					}
				}

				// gera o arquivo de treinamento com o cabeçalho definido no Padrao e inicia o arquivo de treinamento usado para os testes junto com a validação
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamento(nomeAlgoritmoModelo, padrao, i));
				pathTreinamentoValidacao = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamentoEValidacao(nomeAlgoritmoModelo, padrao, i));
				
				bfw = Files.newBufferedWriter(path, charset);
				bfw.write(padrao.getCabecalho());
				bfw.newLine();

				bfwTreinamentoValidacao = Files.newBufferedWriter(pathTreinamentoValidacao, charset);
				bfwTreinamentoValidacao.write(padrao.getCabecalho());
				bfwTreinamentoValidacao.newLine();

				// itera por todas as linhas de treinamento que foram criadas e escreve no arquivo .arff criado anteriormente
				for (int k = 0; k < linhasTreinamento.size(); k++) {
					bfw.write(linhasTreinamento.get(k));
					bfw.newLine();

					bfwTreinamentoValidacao.write(linhasTreinamento.get(k));
					bfwTreinamentoValidacao.newLine();
				}
				bfw.flush();

				// gera o arquivo de validação com o cabeçalho definido no Padrao e continua o arquivo de treinamento completo usado nos testes e junto com a validação usada
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoValidacao(nomeAlgoritmoModelo, padrao, i));
				bfw = Files.newBufferedWriter(path, charset);
				bfw.write(padrao.getCabecalho());
				bfw.newLine();

				// itera por todas as linhas de treinamento que foram criadas e escreve no arquivo .arff criado anteriormente
				for (int l = 0; l < linhasValidacao.size(); l++) {
					bfw.write(linhasValidacao.get(l));
					bfw.newLine();

					bfwTreinamentoValidacao.write(linhasValidacao.get(l));
					bfwTreinamentoValidacao.newLine();
				}
				bfw.flush();
				bfwTreinamentoValidacao.flush();

				// gera o arquivo de teste com o cabeçalho definido no Padrao
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTeste(nomeAlgoritmoModelo, padrao, i));
				bfw = Files.newBufferedWriter(path, charset);
				bfw.write(padrao.getCabecalho());
				bfw.newLine();

				// itera por todas as linhas de testes que foram criadas e escreve no arquivo .arff criado anteriormente
				for (int l = 0; l < linhasTeste.size(); l++) {
					bfw.write(linhasTeste.get(l));
					bfw.newLine();
				}
				bfw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void gerarTreinamentoTesteLeaveOneOut(String nomeAlgoritmoModelo, Padrao padrao){
		try {

			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS);

			String nomeArquivo = padrao.getNomeArquivo();
			String caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS + nomeArquivo;
			FileReader reader = new FileReader(caminhoArquivo);
			Instances instancias = new Instances(reader);

			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;

			Instance instancia;
			List<String> linhas = new ArrayList<String>();

			for (int i = 0; i < instancias.numInstances(); i++) {

				instancia = instancias.instance(i);
				linhas.add(instancia.toString().replace(",", "\t"));
			}

			List<String> linhasTreinamento = new ArrayList<String>();
			List<String> linhasTeste = new ArrayList<String>();
			
			int leaveOneOut = 0;
			
			for (int i = Constantes.INDICE_INICIAL; i <= linhas.size(); i++) {

				linhasTreinamento = new ArrayList<String>();
				linhasTeste = new ArrayList<String>();
				

				for (int j = 0; j < linhas.size(); j++) {

					if (j != leaveOneOut) {
						linhasTreinamento.add(linhas.get(j));
					}

					if (j == leaveOneOut) {
						linhasTeste.add(linhas.get(leaveOneOut));
					}
				}
				
				leaveOneOut++;

				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamentoLeaveOneOut(nomeAlgoritmoModelo, padrao, i));
				bfw = Files.newBufferedWriter(path, charset);
				bfw.write(padrao.getCabecalho());
				bfw.newLine();

				for (int k = 0; k < linhasTreinamento.size(); k++) {
					bfw.write(linhasTreinamento.get(k));
					bfw.newLine();
				}
				bfw.flush();

				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTesteLeaveOneOut(nomeAlgoritmoModelo, padrao, i));
				bfw = Files.newBufferedWriter(path, charset);
				bfw.write(padrao.getCabecalho());
				bfw.newLine();

				for (int l = 0; l < linhasTeste.size(); l++) {
					bfw.write(linhasTeste.get(l));
					bfw.newLine();
				}
				bfw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// gera os arquivos de treinamento, validação e testes para o leave one out, para cada iteração uma instância é separada para validação e outra para teste, total de n iterações são realizadas  
	public static void gerarTreinamentoValidacaoTesteLeaveOneOut(String nomeAlgoritmoModelo, Padrao padrao){
		try {

			// pega o caminho da pasta onde se encontra o arquivo principal
			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS);			
			// pega o caminho do arquivo de treinamento para avaliar os testes
			Path pathTreinamentoValidacao = null;
			// pega o nome do arquivo de acordo com a configuração do dataset
			String nomeArquivo = padrao.getNomeArquivo();
			// caminho completo do arquivo no sistema operacional
			String caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS + nomeArquivo;
			// leitor do arquivo
			FileReader reader = new FileReader(caminhoArquivo);
			// instâncias do arquivo que foi lido no reader
			Instances instancias = new Instances(reader);

			// charset da formatação do arquivo
			Charset charset = StandardCharsets.UTF_8;
			// escritor bufferizado
			BufferedWriter bfw = null;
			// escritor buferizado para treinamento nos testes
			BufferedWriter bfwTreinamentoValidacao = null;

			
			Instance instancia;
			List<String> linhas = new ArrayList<String>();
			nomeAlgoritmoModelo = null; // definido null
			
			// percorrer todas as instâncias do arquivo de dados e colocar em uma lista trocando vírgula por tab
			for (int i = 0; i < instancias.numInstances(); i++) {

				instancia = instancias.instance(i);
				linhas.add(instancia.toString().replace(",", "\t"));
			}

			// linhas referentes a cada instância que será treinada e avaliada
			List<String> linhasTreinamento = new ArrayList<String>();
			List<String> linhasValidacao = new ArrayList<String>();
			List<String> linhasTeste = new ArrayList<String>();
			
			int leaveOneOut = 0;
			int leaveTwoOut = 1;
			boolean entrou = false;
			
			// percorrer de 1 até n
			for (int i = Constantes.INDICE_INICIAL; i <= linhas.size(); i++) {

				// inicializa as variáveis durante as iterações
				linhasTreinamento = new ArrayList<String>();
				linhasValidacao = new ArrayList<String>();
				linhasTeste = new ArrayList<String>();
				
				// percorrer a lista de String de 0 até o final da lista na iteração corrente
				for (int j = 0; j < linhas.size(); j++) {
					
					// se o índice for diferente de qualquer instância separada para validação ou teste ele é colocado no treinamento
					if (j != leaveOneOut && j!=leaveTwoOut) {
						linhasTreinamento.add(linhas.get(j));
					}
					
					// se for o índice referente a validação, é colocado na lista de string de validação
					if (j == leaveOneOut) {
						linhasValidacao.add(linhas.get(leaveOneOut));
					}

					// se for o índice referente ao teste, é colocado na lista de string de teste 
					if (j == leaveTwoOut || (leaveTwoOut == (linhas.size()) && !entrou)) {
						// se for igual ao índice, colocar na lista de teste
						if(j == leaveTwoOut){
							linhasTeste.add(linhas.get(leaveTwoOut));
						}
						// se for igual ao tamanho da lista deve pegar a primeira instância que ainda não foi usada para testes
						if(leaveTwoOut == (linhas.size())){
							linhasTeste.add(linhas.get(0));
							entrou = true;
						}
					}
				}
				
				// incrementa as variáveis de índice para próxima iteração
				leaveOneOut++;
				leaveTwoOut++;

				// pega o caminho do arquivo de treinamento da iteração corrente para leave one out 
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamentoLeaveOneOut(nomeAlgoritmoModelo, padrao, i));
				pathTreinamentoValidacao = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(nomeAlgoritmoModelo, padrao, i));

				// cria o buffer para escrever no arquivo
				bfw = Files.newBufferedWriter(path, charset);
				// escreve o cabeçalho no arquivo, preparado para cada conjunto de dados 
				bfw.write(padrao.getCabecalho());
				// cria uma nova linha no arquivo
				bfw.newLine();
				
				// cria o buffer para escrever no arquivo de treinamento para os testes
				bfwTreinamentoValidacao = Files.newBufferedWriter(pathTreinamentoValidacao, charset);
				// escreve o cabeçalho no arquivo, preparado para cada conjunto de dados de treinamento para os testes 
				bfwTreinamentoValidacao.write(padrao.getCabecalho());
				// cria uma nova linha no arquivo de treinamento para os testes
				bfwTreinamentoValidacao.newLine();


				// percorrer todas as linhas de treinamento que foram criadas anteriormente para a iteração corrente e escreve no arquivo
				for (int k = 0; k < linhasTreinamento.size(); k++) {
					bfw.write(linhasTreinamento.get(k));
					bfw.newLine();
					
					bfwTreinamentoValidacao.write(linhasTreinamento.get(k));
					bfwTreinamentoValidacao.newLine();
				}
				bfw.flush();

				
				// pega o caminho do arquivo de validação da iteração corrente para leave one out 
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoValidacaoLeaveOneOut(nomeAlgoritmoModelo, padrao, i));
				// cria o buffer para escrever no arquivo
				bfw = Files.newBufferedWriter(path, charset);
				// escreve o cabeçalho no arquivo, preparado para cada conjunto de dados 
				bfw.write(padrao.getCabecalho());
				// cria uma nova linha no arquivo
				bfw.newLine();

				// percorrer todas as linhas de validação que foram criadas anteriormente para a iteração corrente e escreve no arquivo
				for (int l = 0; l < linhasValidacao.size(); l++) {
					bfw.write(linhasValidacao.get(l));
					bfw.newLine();
					
					bfwTreinamentoValidacao.write(linhasValidacao.get(l));
					bfwTreinamentoValidacao.newLine();
				}
				bfw.flush();
				bfwTreinamentoValidacao.flush();
				
				// pega o caminho do arquivo de validação da iteração corrente para leave one out 
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTesteLeaveOneOut(nomeAlgoritmoModelo, padrao, i));
				// cria o buffer para escrever no arquivo
				bfw = Files.newBufferedWriter(path, charset);
				// escreve o cabeçalho no arquivo, preparado para cada conjunto de dados 
				bfw.write(padrao.getCabecalho());
				// cria uma nova linha no arquivo
				bfw.newLine();

				for (int l = 0; l < linhasTeste.size(); l++) {
					bfw.write(linhasTeste.get(l));
					bfw.newLine();
				}
				bfw.flush();
}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void gerarTreinamentoClassificador(List<Projeto> listaProjetos, Padrao padrao, int i) {
		
	try {

			// define o caminho do treinamento do classificador
			Path path;
			
			if(Constantes.BASE_VALIDACAO){
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamentoClassificacao(null, padrao, Utilidade.QUANTIDADE_REGRESSOR, Utilidade.TIPO_METRICA_AVALIACAO, i));
			}else{
				path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
						Utilidade.getCaminhoTreinamentoClassificacaoComValidacao(null, padrao, Utilidade.QUANTIDADE_REGRESSOR, Utilidade.TIPO_METRICA_AVALIACAO, i));
			}
			
			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;
	
			// linhas do arquivo de treinamento do classificador
			List<String> linhas = new ArrayList<String>();

			// lista de projetos com melhores resultados para cada instância
			for(Projeto projeto:listaProjetos){
				// adiciona a linha do arquivo com os valores de cada atributo da base de dados e o valor true define que deve colocar o melhor regressor
				linhas.add(padrao.getLinhaClassificacao(projeto, true));
			}
	
			// cria um buffer para escrever no arquivo, coloca o cabeçalho e inicia a escrita dos dados
			bfw = Files.newBufferedWriter(path, charset);
			bfw.write(padrao.getCabecalhoClassificacao());
			bfw.newLine();
	
			// percorre as linhas e vai escrevendo no arquivo
			for (int l = 0; l < linhas.size(); l++) {
				bfw.write(linhas.get(l));
				bfw.newLine();
			}
			bfw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}


	public static void gerarTesteClassificador(List<Projeto> listaProjetos, String nomeAlgoritmoModelo, Padrao padrao, int i) {
		
	try {

			
			// configura o caminho de teste da classificação
			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
					Utilidade.getCaminhoTesteClassificacao(nomeAlgoritmoModelo, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;
	
			// linhas do arquivo de teste da classificação
			List<String> linhas = new ArrayList<String>();
			
			// pecorre os projetos e adiciona nas linhas
			for(Projeto projeto:listaProjetos){
				linhas.add(padrao.getLinhaClassificacao(projeto, false));
			}
	

			bfw = Files.newBufferedWriter(path, charset);
			bfw.write(padrao.getCabecalhoClassificacao());
			bfw.newLine();
	
			for (int l = 0; l < linhas.size(); l++) {
				bfw.write(linhas.get(l));
				bfw.newLine();
			}
			bfw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	public static void gerarValidacaoClassificador(List<Projeto> listaProjetos, String nomeAlgoritmoModelo, Padrao padrao, int i) {
		
	try {

			// configura arquivo que vai fazer a validação do classificador
			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
					Utilidade.getCaminhoValidacaoClassificacao(null, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;
	

			// linhas do arquivo com os objetos de dados
			List<String> linhas = new ArrayList<String>();
			
			// percorre a lista de projetos criada e adiciona cada linha
			for(Projeto projeto:listaProjetos){
				linhas.add(padrao.getLinhaClassificacao(projeto, false));
			}
	

			// escreve no arquivo
			bfw = Files.newBufferedWriter(path, charset);
			bfw.write(padrao.getCabecalhoClassificacao());
			bfw.newLine();
	
			for (int l = 0; l < linhas.size(); l++) {
				bfw.write(linhas.get(l));
				bfw.newLine();
			}
			
			bfw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	public static void gerarTreinamentoClassificadorLeaveOneOut(List<Projeto> listaProjetos, Padrao padrao, int i) {
		
	try {

			
			// define o caminho do treinamento do classificador
			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
					Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, Utilidade.QUANTIDADE_REGRESSOR,  Utilidade.TIPO_METRICA_AVALIACAO, i));
			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;
	

			// linhas do arquivo de treinamento do classificador
			List<String> linhas = new ArrayList<String>();
			
			// lista de projetos com melhores resultados para cada instância
			for(Projeto projeto:listaProjetos){
				// adiciona a linha do arquivo com os valores de cada atributo da base de dados e o valor true define que deve colocar o melhor regressor
				linhas.add(padrao.getLinhaClassificacao(projeto, true));
			}
	

			// cria um buffer para escrever no arquivo, coloca o cabeçalho e inicia a escrita dos dados
			bfw = Files.newBufferedWriter(path, charset);
			bfw.write(padrao.getCabecalhoClassificacao());
			bfw.newLine();
	
			// percorre as linhas e vai escrevendo no arquivo
			for (int l = 0; l < linhas.size(); l++) {
				bfw.write(linhas.get(l));
				bfw.newLine();
			}
			bfw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	public static void gerarTesteClassificadorLeaveOneOut(List<Projeto> listaProjetos, String nomeAlgoritmoModelo, Padrao padrao, int i) {
		
	try {

			
			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
					Utilidade.getCaminhoTesteClassificacaoLeaveOneOut(nomeAlgoritmoModelo, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;
	

			List<String> linhas = new ArrayList<String>();
			for(Projeto projeto:listaProjetos){
				linhas.add(padrao.getLinhaClassificacao(projeto, false));
			}
	

			bfw = Files.newBufferedWriter(path, charset);
			bfw.write(padrao.getCabecalhoClassificacao());
			bfw.newLine();
	
			for (int l = 0; l < linhas.size(); l++) {
				bfw.write(linhas.get(l));
				bfw.newLine();
			}
			bfw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	public static void gerarValidacaoClassificadorLeaveOneOut(List<Projeto> listaProjetos, String nomeAlgoritmoModelo, Padrao padrao, int i) {
		
	try {

			
			Path path = Paths.get(Constantes.CAMINHO_PADRAO_DADOS + 
					Utilidade.getCaminhoValidacaoClassificacaoLeaveOneOut(nomeAlgoritmoModelo, Utilidade.QUANTIDADE_REGRESSOR, padrao, i));
			Charset charset = StandardCharsets.UTF_8;
			BufferedWriter bfw = null;
	

			List<String> linhas = new ArrayList<String>();
			for(Projeto projeto:listaProjetos){
				linhas.add(padrao.getLinhaClassificacao(projeto, false));
			}
	

			bfw = Files.newBufferedWriter(path, charset);
			bfw.write(padrao.getCabecalhoClassificacao());
			bfw.newLine();
	
			for (int l = 0; l < linhas.size(); l++) {
				bfw.write(linhas.get(l));
				bfw.newLine();
			}
			bfw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}



}
