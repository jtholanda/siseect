package method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Projeto;
import model.Resultado;
import util.Constantes;
import util.GeradorAleatorioDados;
import util.TipoMetodoCombinacao;
import util.TipoMetricaAvaliacao;
import util.TipoValidacao;
import util.Utilidade;
import util.WekaExperiment;
import weka.classifiers.Classifier;
import weka.core.Instances;
import data.Padrao;

public abstract class Tecnica {

	protected String nomeModeloCriado;
	protected String caminhoArquivo;
	protected FileReader reader;
	protected Instances instancias;
	protected WekaExperiment we;
	protected Resultado resultado;
	// instâncias da base classificada com o melhor algoritimo
	protected Instances instanciasClassificadas;

	// instancias da base sem a classificação de melhor algoritimo as quais
	// serão utilizadas pelo classificador
	protected Instances instanciasSemClassificacao;

	// classificador de melhor algoritimo
	protected Classifier classificador;
	
	List<Projeto> listaProjetos = new ArrayList<Projeto>();
	List<Projeto> listaProjetosTeste = new ArrayList<Projeto>();
	List<Projeto> listaProjetosValidacao = new ArrayList<Projeto>();
	
	// define o caminho do arquivo que será analisado, o reader as instâncias do arquivo e quem é o atributo rótulo
	protected void configurarDados(String nomeArquivo)
			throws Exception {

		// Usaremos a base definida no parâmetro caminho
		caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS + nomeArquivo;
		reader = new FileReader(caminhoArquivo);
		instancias = new Instances(reader);

		// Inicialmente os atributos serão fixos

		// configura o rótulo
		instancias.setClassIndex(instancias.numAttributes() - 1);
		
	}

	// geraa o modelo que foi criado no SO em forma de arquivo serializável
	protected void gerarModelo(Classifier classificador,
			String nomeModeloCriado) throws IOException, FileNotFoundException {

		// criando o modelo no sistema operacional em formato de objeto de stream de saída
		resultado = new Resultado();
		resultado.setNomeModelo(nomeModeloCriado);
		this.nomeModeloCriado = nomeModeloCriado;
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				nomeModeloCriado));
		oos.writeObject(classificador);
		oos.close();
	}

	// recupera o modelo que foi gerado de acordo com o nome da variável nomeModeloCriado de Tecnica referente a iteração corrente
	protected Classifier recuperarModelo() throws IOException, FileNotFoundException,
			ClassNotFoundException {
		// Recuperamos o modelo criado
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				nomeModeloCriado));
		// recupera o classificador que foi definido no arquivo
		Classifier classificador = (Classifier) ois.readObject();
		ois.close();
		return classificador;
	}
	
	
	protected void imprimirResultados() throws Exception{
		
		resultado.calculaMediaErroAbsoluto();
		resultado.calculaTaxaDeAcertoDoClassificador();
		System.out.println(resultado.toStringSimples());
	}
	protected void imprimirResultadosBasicos() throws Exception{
		
		resultado.calculaMediaErroAbsoluto();
		System.out.println(resultado.toStringSimples());
	}	
	
	protected void calcularResultadosGerais() {
	
		resultado.calculaMediaErroAbsoluto();
		resultado.calculaMediaMarLog();
		resultado.calculaMediaMREAjustado();
		resultado.calculaMediaMRE();
		resultado.calculaMediaBRE();
		resultado.calculaMediaErrosQuadraticos();
		resultado.calculaRaizMediaErrosQuadraticos();;
		resultado.calculaMediaErrosLogQuadraticos();
		resultado.calculaRQuadrado();
		resultado.calculaGanhoRelativo();

		
		
	}
	
	protected void defineAvaliacao(Padrao padrao, TipoValidacao tipoValidacao,
			int i) throws Exception {
	
		// se o tipo de validação for hold out, vai pegar um conjunto de instâncias em um arquivo de dados
		if(tipoValidacao == TipoValidacao.HOLD_OUT){
			if(Constantes.BASE_VALIDACAO){
				// carrega os dados de validação nas instancias principais
				configurarDados(Utilidade.getCaminhoValidacao(null, padrao, i));
			}else{
				// carrega os dados de teste nas instancias principais
				configurarDados(Utilidade.getCaminhoTeste(null, padrao, i));
				
			}
		}

		// se o tipo de validação for leave one out vai pegar um arquivo com apenas uma instância
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			
			if(Constantes.BASE_VALIDACAO){
				// carrega os dados de validação nas instancias principais
				configurarDados(Utilidade.getCaminhoValidacaoLeaveOneOut(null, padrao, i));
			}else{
				// carrega os dados de validação nas instancias principais
				configurarDados(Utilidade.getCaminhoTesteLeaveOneOut(null, padrao, i));
			}
		}
	}



	// adiciona os regressores de melhores desempenhos na lista de projetos de treinamento que foi definida anteriormente baseado em três regressores 
	protected void adicionarCamposListaProjetosAvaliados(Padrao padrao, Resultado resultado1, Resultado resultado2,
			Resultado resultado3, String nomeAlgoritmoModelo, TipoValidacao tipoValidacao, int i) {


		int tamanhoListaProjetos = this.listaProjetos.size();

		// adiciona os resultados dos modelos individuais na lista de resultados
		List<Resultado> resultados = new ArrayList<Resultado>();
		resultados.add(resultado1);
		resultados.add(resultado2);
		resultados.add(resultado3);

		String caminhoTreinamentoClassificador = null;
		
		if(Constantes.BASE_VALIDACAO){
			caminhoTreinamentoClassificador = Utilidade.getCaminhoTreinamentoClassificacao(null, padrao, 3, Utilidade.TIPO_METRICA_AVALIACAO, i);
		}else{
			caminhoTreinamentoClassificador = Utilidade.getCaminhoTreinamentoClassificacaoComValidacao(null, padrao, 3, Utilidade.TIPO_METRICA_AVALIACAO, i);			
		}
		
		// percorre a lista de projetos que foi convertida em objetos
		for (int j = 0; j < tamanhoListaProjetos; j++) {
			
			
			// retorna um projeto com menor erro e o regressor identificado por um número de acordo com os resultados que foram passados na validação dos regressores  
			Projeto projetoComMelhoresResultados = menorResultado(resultados, j);
			

			// projetos que vieram no parâmetro recebe qual foi o menor erro obtido e o melhor algoritmo para o projeto
			listaProjetos.get(j).setMenorErro(projetoComMelhoresResultados.getMenorErro());
			listaProjetos.get(j).setMelhorAlgoritmo(projetoComMelhoresResultados.getMelhorAlgoritmo());

		}

		// se a validação for do tipo hold out
		if(tipoValidacao == TipoValidacao.HOLD_OUT){
			
			
			
			// obtém um arquivo com os dados de treinamento para o classificador de acordo com a iteração
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + caminhoTreinamentoClassificador );
			
			// se o arquivo não existir no SO é gerado todos os arquivos de treinamento para os classificadores
			if(!arquivoTreinamentoClassificador.exists()){
				// gera o arquivo de treinamento do classificador
				GeradorAleatorioDados.gerarTreinamentoClassificador(listaProjetos, padrao, i); 
			}
		}
		
		// se a validação for leave one out
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			
			// obtém o arquivo com os dados de treinamento do classificador de acordo com a iteração 
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, 3,  Utilidade.TIPO_METRICA_AVALIACAO, i));
			
			// se o arquivo não existir, cria um para a iteração corrente
			if(!arquivoTreinamentoClassificador.exists()){
				// gera o arquivo de treinamento do classificador
				GeradorAleatorioDados.gerarTreinamentoClassificadorLeaveOneOut(listaProjetos, padrao, i); 
			}
		}

		// se melhor algoritmo estiver definido como true imprime no console os dados da lista de projetos do padrão corrente
		if(Constantes.IMPRIMIR_MELHOR_ALGORITMO){
			imprimirMelhorRegressor(padrao);
		}
	}

	// adiciona os regressores de melhores desempenhos na lista de projetos de treinamento que foi definida anteriormente baseado em quatro regressores 
	protected void adicionarCamposListaProjetosAvaliados(Padrao padrao, Resultado resultado1, Resultado resultado2,
			Resultado resultado3, Resultado resultado4, String nomeAlgoritmoModelo, TipoValidacao tipoValidacao, int i) {


		int tamanhoListaProjetos = this.listaProjetos.size();

		
		for (int j = 0; j < tamanhoListaProjetos; j++) {
			
			List<Resultado> resultados = new ArrayList<Resultado>();
			resultados.add(resultado1);
			resultados.add(resultado2);
			resultados.add(resultado3);
			resultados.add(resultado4);
			
			// recupera um projeto que contém os melhores resultados
			Projeto projetoComMelhoresResultados = menorResultado(resultados, j);
			

			//projetos que vieram no parâmetro recebe qual foi o menor erro obtido e o melhor algoritmo para o projeto
			listaProjetos.get(j).setMenorErro(projetoComMelhoresResultados.getMenorErro());
			listaProjetos.get(j).setMelhorAlgoritmo(projetoComMelhoresResultados.getMelhorAlgoritmo());

		}

		if(tipoValidacao == TipoValidacao.HOLD_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacao(null, padrao, 4, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificador(listaProjetos, padrao, i); 
			}
		}
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, 4, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificadorLeaveOneOut(listaProjetos, padrao, i); 
			}
		}

		if(Constantes.IMPRIMIR_MELHOR_ALGORITMO){
			imprimirMelhorRegressor(padrao);
		}
	}

	// adiciona os regressores de melhores desempenhos na lista de projetos de treinamento que foi definida anteriormente baseado em cinco regressores 
	protected void adicionarCamposListaProjetosAvaliados(Padrao padrao, Resultado resultado1, Resultado resultado2,
			Resultado resultado3, Resultado resultado4, Resultado resultado5, String nomeAlgoritmoModelo, TipoValidacao tipoValidacao, int i) {


		int tamanhoListaProjetos = this.listaProjetos.size();

		
		for (int j = 0; j < tamanhoListaProjetos; j++) {
			
			List<Resultado> resultados = new ArrayList<Resultado>();
			resultados.add(resultado1);
			resultados.add(resultado2);
			resultados.add(resultado3);
			resultados.add(resultado4);
			resultados.add(resultado5);
			
			Projeto projetoComMelhoresResultados = menorResultado(resultados, j);
			

			//projetos que vieram no parâmetro recebe qual foi o menor erro obtido e o melhor algoritmo para o projeto
			listaProjetos.get(j).setMenorErro(projetoComMelhoresResultados.getMenorErro());
			listaProjetos.get(j).setMelhorAlgoritmo(projetoComMelhoresResultados.getMelhorAlgoritmo());

		}

		if(tipoValidacao == TipoValidacao.HOLD_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacao(null, padrao, 5, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificador(listaProjetos, padrao, i); 
			}
		}
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, 5, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificadorLeaveOneOut(listaProjetos, padrao, i); 
			}
		}

		if(Constantes.IMPRIMIR_MELHOR_ALGORITMO){
			imprimirMelhorRegressor(padrao);
		}
	}

	// adiciona os regressores de melhores desempenhos na lista de projetos de treinamento que foi definida anteriormente baseado em seis regressores 
	protected void adicionarCamposListaProjetosAvaliados(Padrao padrao, Resultado resultado1,
			Resultado resultado2, Resultado resultado3, Resultado resultado4,
			Resultado resultado5, Resultado resultado6, String nomeAlgoritmoModelo, TipoValidacao tipoValidacao, int i) throws IOException {
		

		int tamanhoListaProjetos = this.listaProjetos.size();

		
		for (int j = 0; j < tamanhoListaProjetos; j++) {
			
			List<Resultado> resultados = new ArrayList<Resultado>();
			resultados.add(resultado1);
			resultados.add(resultado2);
			resultados.add(resultado3);
			resultados.add(resultado4);
			resultados.add(resultado5);
			resultados.add(resultado6);
			
			Projeto projetoComMelhoresResultados = menorResultado(resultados, j);
			

			//projetos que vieram no parâmetro recebe qual foi o menor erro obtido e o melhor algoritmo para o projeto
			listaProjetos.get(j).setMenorErro(projetoComMelhoresResultados.getMenorErro());
			listaProjetos.get(j).setMelhorAlgoritmo(projetoComMelhoresResultados.getMelhorAlgoritmo());
			
		}
		if(tipoValidacao == TipoValidacao.HOLD_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS+ Utilidade.getCaminhoTreinamentoClassificacao(null, padrao, 6, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificador(listaProjetos, padrao, i); 
			}
		}
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, 6, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificadorLeaveOneOut(listaProjetos, padrao, i); 
			}
		}

		
		if(Constantes.IMPRIMIR_MELHOR_ALGORITMO){
			imprimirMelhorRegressor(padrao);
		}
	}

	// adiciona os regressores de melhores desempenhos na lista de projetos de treinamento que foi definida anteriormente baseado em dez regressores 
	protected void adicionarCamposListaProjetosAvaliados(Padrao padrao, Resultado resultado1,
			Resultado resultado2, Resultado resultado3, Resultado resultado4,
			Resultado resultado5, Resultado resultado6, Resultado resultado7, Resultado resultado8, Resultado resultado9, Resultado resultado10, String nomeAlgoritmoModelo, TipoValidacao tipoValidacao, int i) {
		

		int tamanhoListaProjetos = this.listaProjetos.size();

		
		for (int j = 0; j < tamanhoListaProjetos; j++) {
			
			List<Resultado> resultados = new ArrayList<Resultado>();
			resultados.add(resultado1);
			resultados.add(resultado2);
			resultados.add(resultado3);
			resultados.add(resultado4);
			resultados.add(resultado5);
			resultados.add(resultado6);
			resultados.add(resultado7);
			resultados.add(resultado8);
			resultados.add(resultado9);
			resultados.add(resultado10);
			
			
			
			Projeto projetoComMelhoresResultados = menorResultado(resultados, j);
			

			//projetos que vieram no parâmetro recebe qual foi o menor erro obtido e o melhor algoritmo para o projeto
			listaProjetos.get(j).setMenorErro(projetoComMelhoresResultados.getMenorErro());
			listaProjetos.get(j).setMelhorAlgoritmo(projetoComMelhoresResultados.getMelhorAlgoritmo());

		}

		if(tipoValidacao == TipoValidacao.HOLD_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacao(null, padrao, 7, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificador(listaProjetos, padrao, i); 
			}
		}
		if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
			File arquivoTreinamentoClassificador = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoClassificacaoLeaveOneOut(null, padrao, 7, Utilidade.TIPO_METRICA_AVALIACAO, i));
			if(!arquivoTreinamentoClassificador.exists()){
				GeradorAleatorioDados.gerarTreinamentoClassificadorLeaveOneOut(listaProjetos, padrao, i); 
			}
		}

		if(Constantes.IMPRIMIR_MELHOR_ALGORITMO){
			imprimirMelhorRegressor(padrao);
		}
	}

	
	// retorna o projeto com melhor resultado definido dentro do objeto criado para esta finalidade
	private Projeto menorResultado(List<Resultado> resultados, int i) {
		
		double menorErro = 10000000;
		String melhorAlgoritimo = "";
		Projeto projeto = new Projeto();
		
		TipoMetricaAvaliacao tipoMetricaAvaliacao = Utilidade.TIPO_METRICA_AVALIACAO;
		
		// se a métrica de avaliação tiver definida como MAR
		if(tipoMetricaAvaliacao == TipoMetricaAvaliacao.MAR){
			// faz uma iteração na lista dos resultados que foram passados
			for (int j = 0; j < resultados.size(); j++) {
				if(resultados.get(j).getErrosAbsolutos().get(i) < menorErro){
					menorErro = resultados.get(j).getErrosAbsolutos().get(i);
					melhorAlgoritimo = "REGRESSOR" + (j+1);
				}
			} 
		}

		// se a métrica de avaliação tiver definida como MARLOG
		if(tipoMetricaAvaliacao == TipoMetricaAvaliacao.MARLOG){
			// faz uma iteração na lista dos resultados que foram passados
			for (int j = 0; j < resultados.size(); j++) {
				if(resultados.get(j).getMarlogs().get(i) < menorErro){
					menorErro = resultados.get(j).getMarlogs().get(i);
					melhorAlgoritimo = "REGRESSOR" + (j+1);
				}
			}
		}

		// se a métrica de avaliação tiver definida como MREAJUSTADO
		if(tipoMetricaAvaliacao == TipoMetricaAvaliacao.MREAJUSTADO){
			// faz uma iteração na lista dos resultados que foram passados
			for (int j=0; j < resultados.size(); j++) {
				if(resultados.get(j).getMresAjustados().get(i) < menorErro){
					menorErro = resultados.get(j).getMresAjustados().get(i);
					melhorAlgoritimo = "REGRESSOR" + (j+1);
				}
			}
		}

		projeto.setMenorErro(menorErro);
		projeto.setMelhorAlgoritmo(melhorAlgoritimo);
		
		return projeto;
	}
	
	
	protected Classifier getClassificadorTreinado(Integer tipoClassificador) throws Exception {
		//treino o classificador no arquivo com as instancias e o melhor método encontrado
//		we = new WekaExperiment();
//		we.setTrainingData(instanciasClassificadas);
//		we.KFoldCrossValidationStratified(Constantes.FOLD, 1, tipoClassificador); 
		
		// retorna o classificador gerado
		Classifier classificador = new WekaExperiment().createClassifier(tipoClassificador);
		classificador.buildClassifier(instanciasClassificadas);
		return classificador;
	}

	protected void configurarDadosClassificacao(String dadosTreino, String dadosValidacao)
			throws FileNotFoundException, IOException {
		
		// Usaremos a base definida no parâmetro caminho, é o mesmo conjunto de dados mas agora com rotulo de classificação
		String nomeArquivoComClassificador = dadosTreino;
		caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS + nomeArquivoComClassificador;
		reader = new FileReader(caminhoArquivo);
		this.instanciasClassificadas = new Instances(reader);
		this.instanciasClassificadas.setClassIndex(instanciasClassificadas.numAttributes() - 1);


		// define as instâncias sem classificação para serem avaliadas pelo classificador
		String nomeArquivoSemClassificador = dadosValidacao;
		caminhoArquivo = Constantes.CAMINHO_PADRAO_DADOS + nomeArquivoSemClassificador;
		reader = new FileReader(caminhoArquivo);
		this.instanciasSemClassificacao = new Instances(reader);
		this.instanciasSemClassificacao.setClassIndex(instanciasSemClassificacao.numAttributes() - 1);
	}
	
	private void imprimirMelhorRegressor(Padrao padrao) {
		// impressão dos valores dos dados do projeto
		padrao.imprimirProjetos(listaProjetos);
		
	}
	

	protected double getEstimativaEnsemble(TipoMetodoCombinacao tipoSelecaoDinamica, List<Double> valoresEstimados){
		
		double valorEstimado = 0;
		
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.MEDIA){
			// escolher uma estimativa ideal da lista criada
			valorEstimado = Utilidade.calcularMediaLista(valoresEstimados);
			if(Constantes.IMPRIMIR_ESTIMATIVAS_DINAMICAS){
			System.out.println(""+valoresEstimados.get(0)+
					"\t"+valoresEstimados.get(1)+
					"\t"+valoresEstimados.get(2)+
					"\t"+valoresEstimados.get(3)+
					"\t"+valoresEstimados.get(4)+
					"\t"+valorEstimado+
					"");
			}
		}
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.MEDIANA){
			// escolher uma estimativa ideal da lista criada
			valorEstimado = Utilidade.calcularMedianaLista(valoresEstimados);  						
			if(Constantes.IMPRIMIR_ESTIMATIVAS_DINAMICAS){
			System.out.println(""+valoresEstimados.get(0)+
					"\t"+valoresEstimados.get(1)+
					"\t"+valoresEstimados.get(2)+
					"\t"+valoresEstimados.get(3)+
					"\t"+valoresEstimados.get(4)+
					"\t"+valorEstimado+
					"");
			}
		}
	
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.MODA){
			// escolher uma estimativa ideal da lista criada
			valorEstimado = Utilidade.calcularModaLista(valoresEstimados);
			if(Constantes.IMPRIMIR_ESTIMATIVAS_DINAMICAS){
				System.out.println(""+valoresEstimados.get(0)+
						"\t"+valoresEstimados.get(1)+
						"\t"+valoresEstimados.get(2)+
						"\t"+valoresEstimados.get(3)+
						"\t"+valoresEstimados.get(4)+
						"\t"+valorEstimado+
						"");
			}
		}
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.MINIMO){
			// escolher uma estimativa ideal da lista criada
			valorEstimado = Utilidade.calcularMenorValor(valoresEstimados);
			if(Constantes.IMPRIMIR_ESTIMATIVAS_DINAMICAS){
			System.out.println(""+valoresEstimados.get(0)+
					"\t"+valoresEstimados.get(1)+
					"\t"+valoresEstimados.get(2)+
					"\t"+valoresEstimados.get(3)+
					"\t"+valoresEstimados.get(4)+
					"\t"+valorEstimado+
					"");		
				}
		}

		if(tipoSelecaoDinamica == TipoMetodoCombinacao.MAXIMO){
			// escolher uma estimativa ideal da lista criada
			valorEstimado = Utilidade.calcularMaiorValor(valoresEstimados);
			if(Constantes.IMPRIMIR_ESTIMATIVAS_DINAMICAS){
			System.out.println(""+valoresEstimados.get(0)+
					"\t"+valoresEstimados.get(1)+
					"\t"+valoresEstimados.get(2)+
					"\t"+valoresEstimados.get(3)+
					"\t"+valoresEstimados.get(4)+
					"\t"+valorEstimado+
					"");		
				}
		}
		
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.MEDIA_DAS_PONTAS){
			// escolher uma estimativa ideal da lista criada
			valorEstimado = Utilidade.calcularMediaPontas(valoresEstimados);
			if(Constantes.IMPRIMIR_ESTIMATIVAS_DINAMICAS){
			System.out.println(""+valoresEstimados.get(0)+
					"\t"+valoresEstimados.get(1)+
					"\t"+valoresEstimados.get(2)+
					"\t"+valoresEstimados.get(3)+
					"\t"+valoresEstimados.get(4)+
					"\t"+valorEstimado+
					"");
			}
		}
		
		if(tipoSelecaoDinamica == TipoMetodoCombinacao.SD){
			// escolher uma estimativa ideal da lista criada
			valorEstimado = Utilidade.calcularUnicoValor(valoresEstimados);
			if(Constantes.IMPRIMIR_ESTIMATIVAS_DINAMICAS){
			System.out.println(""+valoresEstimados.get(0)+
					"\t"+valoresEstimados.get(1)+
					"\t"+valoresEstimados.get(2)+
					"\t"+valoresEstimados.get(3)+
					"\t"+valoresEstimados.get(4)+
					"\t"+valorEstimado+
					"");
			}
		}		

	
		
		return valorEstimado;

	}
	
	public List<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(List<Projeto> listaProjetos) {
		this.listaProjetos = listaProjetos;
	}
	
	public String getNomeModeloCriado() {
		return nomeModeloCriado;
	}

	public void setNomeModeloCriado(String nomeModeloCriado) {
		this.nomeModeloCriado = nomeModeloCriado;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public FileReader getReader() {
		return reader;
	}

	public void setReader(FileReader reader) {
		this.reader = reader;
	}

	public Instances getInstancias() {
		return instancias;
	}

	public void setInstancias(Instances instancias) {
		this.instancias = instancias;
	}

	public WekaExperiment getWe() {
		return we;
	}

	public void setWe(WekaExperiment we) {
		this.we = we;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	
	public List<Projeto> getListaProjetosTeste() {
		return listaProjetosTeste;
	}

	public void setListaProjetosTeste(List<Projeto> listaProjetosTeste) {
		this.listaProjetosTeste = listaProjetosTeste;
	}

	public abstract void run(String treino, String teste);
	public abstract void run(Padrao padrao, TipoValidacao tipoValidacao);
	public abstract void run(Padrao padrao);

	public void run(Padrao padrao, int tamanhoLeaveOneOut) {
		
	}

	public void run(Padrao padrao, Classifier metaclassificador) {
		// TODO Auto-generated method stub
		
	}

	public void run(Padrao padrao, TipoValidacao tipoValidacao,
			Classifier metaClassificador) {
		// TODO Auto-generated method stub
		
	}

	public List<Projeto> getListaProjetosValidacao() {
		return listaProjetosValidacao;
	}

	public void setListaProjetosValidacao(List<Projeto> listaProjetosValidacao) {
		this.listaProjetosValidacao = listaProjetosValidacao;
	}

	



	
	
	

}
