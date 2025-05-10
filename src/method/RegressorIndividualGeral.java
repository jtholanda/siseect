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

	// cria o modelo de acordo com o arquivo relacionado ao caminho absoluto passado, constr�i e gera depois no SO para cada itera��o 
	public void criarModelo(String nomeArquivoTreino, int i, Padrao padrao) throws Exception {

		// nome do arquivo de treinamento gerado referente a uma itera��o qualquer que j� foi definida no nome do arquivo
		configurarDados(nomeArquivoTreino);
		// constr�i o modelo para o algoritmo definido em regressor de acordo com a vari�vel inst�ncias que foi definida em configurarDados
		regressor.buildClassifier(instancias);
		// constr�i modelo comparativo para utilizar no ganho relativo
		Utilidade.REGRESSOR_COMPARATIVO.buildClassifier(instancias);
		// gera o modelo em forma de arquivo no sistema operacional identificando a itera��o
		gerarModelo(regressor, padrao.getCaminhoModeloPadrao() + "_" + regressor.getClass().getName() +"_"+ i);

	}
	
	// cria o modelo de acordo com o arquivo relacionado ao caminho absoluto passado, constr�i e gera depois um arquivo do modelo no SO
	public void criarModelo(String nomeArquivoTreino) throws Exception {

		// nome do arquivo de treinamento gerado referente a uma itera��o qualquer que j� foi definida no nome do arquivo
		configurarDados(nomeArquivoTreino);
		// constr�i o modelo para o algoritmo definido em regressor de acordo com a vari�vel inst�ncias que foi definida em configurarDados
		regressor.buildClassifier(instancias);		
		// constr�i modelo comparativo para utilizar no ganho relativo
		Utilidade.REGRESSOR_COMPARATIVO.buildClassifier(instancias);
		// gera o modelo em forma de arquivo no sistema operacional identificando a itera��o
		gerarModelo(regressor, regressor.getClass().getName());

	}

	public void usarModelo(String nomeArquivoTeste) throws Exception {

		// recupera o modelo que foi criado no sistema operacional
		regressor = recuperarModelo();
		// passa o caminho do arquivo que ser� avaliado, o caminho absoluto do arquivo, reader, inst�ncias e a classe r�tulo s�o definidas na classe Tecnica
		configurarDados(nomeArquivoTeste);
		// cria um objeto resultado que salva as informa��es da avalia��o realizada
		resultado = new Resultado();
		// avalia o regressor nas inst�ncias que foram configuradas
		avaliarModelo(regressor, instancias, nomeModeloCriado);
	}

	public void avaliarModelo(Classifier classificador, Instances instancias, String nomeModeloCriado) throws Exception {
		
		// Agora vamos classificar cada dado original
		Double valorEstimado, valorReal, valorEstimadoComparativo;
		resultado.setNomeModelo(nomeModeloCriado);
		
		for (int a = 0; a < instancias.numInstances(); a++) {

			// Recuperamos cada uma das inst�ncias
			Instance instancia = instancias.instance(a);

			// Classificamos esta inst�ncia
			//valorEstimado = (double) Math.abs(classificador.classifyInstance(instancia));
			valorEstimadoComparativo = Utilidade.REGRESSOR_COMPARATIVO.classifyInstance(instancia);
			valorEstimado = (double) classificador.classifyInstance(instancia);
			valorReal = (double) instancia.classValue();
			
			Utilidade.calculaMetricas(resultado, valorEstimado, valorReal, valorEstimadoComparativo);
			
	}
		
		// calcula os erros do modelo e adiciona a lista com log do resultado
		calcularResultadosGerais();
		
	
		// imprime as estimativas do m�todo se a vari�vel estiver definida como true
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
				
				// pega o primeiro arquivo de treinamento referente a primeira itera��o do hold-out
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento(null, padrao, 1));

				// se o arquivo n�o existir � porque falta gerar os dados de treinamento, valida��o e teste  
				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTeste(null, padrao);
				}
				
				// cria um modelo de acordo com os dados de treinamento para cada itera��o e usa o modelo baseado na constante de valida��o
				for(int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++){
					
					// verifica a avalia��o para criar os dados de valida��o ou testes corretamente, o treinamento � diferente dependendo da avalia��o
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque n�o importa o algoritmo, o padr�o e a itera��o i para valida��o
						criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque n�o importa o algoritmo, o padr�o e a itera��o i para testes
						criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
					}
					
					// verifica se a constante de valida��o est� definida como true
					if(Constantes.BASE_VALIDACAO){
						// pega o caminho do arquivo de valida��o corrente e passa para o m�todo que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoValidacao(null, padrao, i));
					}else{
						// pega o caminho do arquivo de teste corrente e passa para o m�todo que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoTeste(null, padrao, i));						
					}
				}
			}
			
			if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){

				// pega o primeiro arquivo de treinamento referente a primeira itera��o do hold-out
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, 1));

				// se o arquivo n�o existir � porque falta gerar os dados de treinamento, valida��o e teste baseado na valida��o leave one out  
				if(arquivo == null || !arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTesteLeaveOneOut(null, padrao);
				}

				// cria um modelo de acordo com os dados de treinamento para cada itera��o e usa o modelo baseado na constante de valida��o
				for(int i = 1; i <= padrao.getTamanhoBase(); i++){
					
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque n�o importa o algoritmo, o padr�o e a itera��o i
						criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque n�o importa o algoritmo, o padr�o e a itera��o i
						criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null,padrao, i), i, padrao);						
					}
					
					// verifica se a constante de valida��o est� definida como true
					if(Constantes.BASE_VALIDACAO){
						// pega o caminho do arquivo de valida��o corrente e passa para o m�todo que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoValidacaoLeaveOneOut(null, padrao, i));
					}else{
						// pega o caminho do arquivo de teste corrente e passa para o m�todo que vai avaliar os dados 
						usarModelo(Utilidade.getCaminhoTesteLeaveOneOut(null, padrao, i));
					}
				}

			}
			
			// calcula a m�dia das m�dias de todas as itera��es
			Utilidade.calculaValoresCentraisDasMetricas();
			// inicializa a lista das m�dias de erros para ser usada pelo pr�ximo modelo 
			Utilidade.zerarMediasAuxiliares();
			// adiciona uma linha nos arquivos que cont�m os resultados de todos os modelos
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
