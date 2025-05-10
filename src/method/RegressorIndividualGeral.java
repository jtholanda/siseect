package method;

import java.io.File;
import java.lang.invoke.ConstantCallSite;

import javax.swing.text.Utilities;

import model.Resultado;
import util.Constantes;
import util.GeradorAleatorioDados;
import util.TipoValidacao;
import util.Utilidade;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import data.Padrao;

public class RegressorIndividualGeral extends Tecnica {

	private Classifier regressor;
	
	public RegressorIndividualGeral(Classifier regressor) {
		this.regressor = regressor;
	}

	// cria o modelo de acordo com o arquivo relacionado ao caminho absoluto passado, constrói e gera depois no SO para cada iteração 
	public void criarModelo(String nomeArquivoTreino, int i, Padrao padrao) throws Exception {

		// nome do arquivo de treinamento gerado referente a uma iteração qualquer que já foi definida no nome do arquivo
		configurarDados(nomeArquivoTreino);
		// constrói o modelo para o algoritmo definido em regressor de acordo com a variável instâncias que foi definida em configurarDados
		regressor.buildClassifier(instancias);
		// constrói modelo comparativo para utilizar no ganho relativo
		Utilidade.REGRESSOR_COMPARATIVO.buildClassifier(instancias);
		// gera o modelo em forma de arquivo no sistema operacional identificando a iteração
		gerarModelo(regressor, padrao.getCaminhoModeloPadrao() + "_" + regressor.getClass().getName() +"_"+ i);

	}
	
	// cria o modelo de acordo com o arquivo relacionado ao caminho absoluto passado, constrói e gera depois um arquivo do modelo no SO
	public void criarModelo(String nomeArquivoTreino) throws Exception {

		// nome do arquivo de treinamento gerado referente a uma iteração qualquer que já foi definida no nome do arquivo
		configurarDados(nomeArquivoTreino);
		// constrói o modelo para o algoritmo definido em regressor de acordo com a variável instâncias que foi definida em configurarDados
		regressor.buildClassifier(instancias);		
		// constrói modelo comparativo para utilizar no ganho relativo
		Utilidade.REGRESSOR_COMPARATIVO.buildClassifier(instancias);
		// gera o modelo em forma de arquivo no sistema operacional identificando a iteração
		gerarModelo(regressor, regressor.getClass().getName());

	}

	public void usarModelo(String nomeArquivoTeste) throws Exception {

		// recupera o modelo que foi criado no sistema operacional
		regressor = recuperarModelo();
		// passa o caminho do arquivo que será avaliado, o caminho absoluto do arquivo, reader, instâncias e a classe rótulo são definidas na classe Tecnica
		configurarDados(nomeArquivoTeste);
		// cria um objeto resultado que salva as informações da avaliação realizada
		resultado = new Resultado();
		// avalia o regressor nas instâncias que foram configuradas
		avaliarModelo(regressor, instancias, nomeModeloCriado);
	}

	public void avaliarModelo(Classifier classificador, Instances instancias, String nomeModeloCriado) throws Exception {
		
		// Agora vamos classificar cada dado original
		Double valorEstimado, valorReal, valorEstimadoComparativo;
		resultado.setNomeModelo(nomeModeloCriado);
		
		for (int a = 0; a < instancias.numInstances(); a++) {

			// Recuperamos cada uma das instâncias
			Instance instancia = instancias.instance(a);

			// Classificamos esta instância
			//valorEstimado = (double) Math.abs(classificador.classifyInstance(instancia));
			valorEstimadoComparativo = Utilidade.REGRESSOR_COMPARATIVO.classifyInstance(instancia);
			valorEstimado = (double) classificador.classifyInstance(instancia);
			valorReal = (double) instancia.classValue();
			
			Utilidade.calculaMetricas(resultado, valorEstimado, valorReal, valorEstimadoComparativo);
			
	}
		
		// calcula os erros do modelo e adiciona a lista com log do resultado
		calcularResultadosGerais();
		
	
		// imprime as estimativas do método se a variável estiver definida como true
		if(Constantes.IMPRIMIR_ESTIMATIVAS){
			System.out.println(resultado.toStringEstimativas());
		}
	}


	@Override
	public void run(String treino, String validacao) {
		try {
			
			criarModelo(treino);
			usarModelo(validacao);
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void run(Padrao padrao, TipoValidacao tipoValidacao) {
		try {
			
			File arquivo = null;
			Utilidade.adicionaCabecalhoDados(padrao);
			
			if(tipoValidacao == TipoValidacao.HOLD_OUT){
				
				// pega o primeiro arquivo de treinamento referente a primeira iteração do hold-out
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento(null, padrao, 1));

				// se o arquivo não existir é porque falta gerar os dados de treinamento, validação e teste  
				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTeste(null, padrao);
				}
				
				// cria um modelo de acordo com os dados de treinamento para cada iteração e usa o modelo baseado na constante de validação
				for(int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++){
					
					// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
						criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
						criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
					}
					
					// verifica se a constante de validação está definida como true
					if(Constantes.BASE_VALIDACAO){
						// pega o caminho do arquivo de validação corrente e passa para o método que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoValidacao(null, padrao, i));
					}else{
						// pega o caminho do arquivo de teste corrente e passa para o método que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoTeste(null, padrao, i));						
					}
				}
			}
			
			if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){

				// pega o primeiro arquivo de treinamento referente a primeira iteração do hold-out
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, 1));

				// se o arquivo não existir é porque falta gerar os dados de treinamento, validação e teste baseado na validação leave one out  
				if(arquivo == null || !arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTesteLeaveOneOut(null, padrao);
				}

				// cria um modelo de acordo com os dados de treinamento para cada iteração e usa o modelo baseado na constante de validação
				for(int i = 1; i <= padrao.getTamanhoBase(); i++){
					
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i
						criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i
						criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null,padrao, i), i, padrao);						
					}
					
					// verifica se a constante de validação está definida como true
					if(Constantes.BASE_VALIDACAO){
						// pega o caminho do arquivo de validação corrente e passa para o método que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoValidacaoLeaveOneOut(null, padrao, i));
					}else{
						// pega o caminho do arquivo de teste corrente e passa para o método que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoTesteLeaveOneOut(null, padrao, i));
					}
				}

			}
			
			// calcula a média das médias de todas as iterações
			Utilidade.calculaValoresCentraisDasMetricas();
			// inicializa a lista das médias de erros para ser usada pelo próximo modelo 
			Utilidade.zerarMediasAuxiliares();
			// adiciona uma linha nos arquivos que contém os resultados de todos os modelos
			Utilidade.adicionaLinhaArquivo();
			 
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void run(Padrao padrao) {
		File arquivo = null;
		try{
			
				
			arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento("CONSTANTE", padrao, 1));

			if(!arquivo.exists()){
				GeradorAleatorioDados.gerarTreinamentoValidacaoTeste(null, padrao);
			}
			
			for(int i = 1; i <= 50; i++){
				criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
				if(Constantes.BASE_VALIDACAO){
					usarModelo(Utilidade.getCaminhoValidacao(null, padrao, i));
				}else{
					usarModelo(Utilidade.getCaminhoTeste(null, padrao, i));						
				}
			}
	
			Utilidade.calculaValoresCentraisDasMetricas();
			Utilidade.zerarMediasAuxiliares();
			Utilidade.adicionaLinhaArquivo();

		}catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
