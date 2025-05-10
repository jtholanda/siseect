package method;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Utilities;

import main.TesteEnsembleEstatico;
import model.Resultado;
import util.Constantes;
import util.GeradorAleatorioDados;
import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import util.Utilidade;
import weka.classifiers.Classifier;
import weka.core.Instance;
import data.Padrao;

public class EnsembleEstaticoGeral extends EnsembleEstaticoTecnica {

	private Classifier regressor1;
	private Classifier regressor2;
	private Classifier regressor3;
	private Classifier regressor4;
	private Classifier regressor5;
	
	public EnsembleEstaticoGeral(Classifier regressor1, Classifier regressor2, Classifier regressor3, Classifier regressor4, Classifier regressor5) {
		this.regressor1 = regressor1;
		this.regressor2 = regressor2;
		this.regressor3 = regressor3;
		this.regressor4 = regressor4;
		this.regressor5 = regressor5;

	}

	private void avaliarMetodo(Padrao padrao, Resultado resultado1,
			Resultado resultado2, Resultado resultado3, Resultado resultado4, Resultado resultado5, TipoMetodoCombinacao tipoMetodoCombinacao, TipoValidacao tipoValidacao, int i) throws Exception {

		defineAvaliacao(padrao, tipoValidacao, i);

		// calcula as estimativas combinadas utilizando os resultados individuais
		// padrão e tipo de método de combinação definem as informações do dataset e a regra de combinação das estimativas
		calcularEstimativas(padrao, resultado1, resultado2, resultado3, resultado4, resultado5, tipoMetodoCombinacao);


		// imprimi os resultados em tela
		//imprimirResultadosMdMRE();
		
		// calcula os erros do modelo e adiciona a lista com log do resultado
		calcularResultadosGerais();
	}


	private void calcularEstimativas(Padrao padrao, Resultado resultado1,
			Resultado resultado2, Resultado resultado3, Resultado resultado4, Resultado resultado5, TipoMetodoCombinacao tipoMetodoCombinacao) throws Exception {

		
		Double valorEstimado = null;
		Double valorEstimado1 = null;
		Double valorEstimado2 = null;
		Double valorEstimado3 = null;
		Double valorEstimado4 = null;
		Double valorEstimado5 = null;
		Double valorReal;
		Double erroAbsoluto, marlog, mreAjustado;
		Double valorEstimadoComparativo;
		Instance instancia;
		

		Classifier regressor1 = null;
		Classifier regressor2 = null;
		Classifier regressor3 = null;
		Classifier regressor4 = null;
		Classifier regressor5 = null;
		
		resultado = new Resultado();
		resultado.setNomeModelo(toString() + " - " + padrao.toString());

		if (resultado1 != null){
			// obtem o modelo
			ObjectInputStream objectInputStream1 = new ObjectInputStream(new FileInputStream(
					resultado1.getNomeModelo()));
			regressor1 = (Classifier) objectInputStream1.readObject();
			objectInputStream1.close();
		}
		
		if(resultado2 != null){
			// obtem o modelo
			ObjectInputStream objectInputStream2 = new ObjectInputStream(new FileInputStream(
					resultado2.getNomeModelo()));
			regressor2 = (Classifier) objectInputStream2.readObject();
			objectInputStream2.close();
		}
		
		if(resultado3 != null){
			// obtem o modelo
			ObjectInputStream objectInputStream3 = new ObjectInputStream(new FileInputStream(
					resultado3.getNomeModelo()));
			regressor3 = (Classifier) objectInputStream3.readObject();
			objectInputStream3.close();
		}
		
		if(resultado4 != null){
			// obtem o modelo
			ObjectInputStream objectInputStream4 = new ObjectInputStream(new FileInputStream(
					resultado4.getNomeModelo()));
			regressor4 = (Classifier) objectInputStream4.readObject();
			objectInputStream4.close();
		}

		if(resultado5 != null){
			// obtem o modelo
			ObjectInputStream objectInputStream5 = new ObjectInputStream(new FileInputStream(
					resultado5.getNomeModelo()));
			regressor5 = (Classifier) objectInputStream5.readObject();
			objectInputStream5.close();
		}
		
		// percorre todas as instâncias que foram configuradas em configurar dados
		for (int i = 0; i < instancias.numInstances(); i++) {

			// pega cada instancia do conjunto de validação ou teste
			instancia = instancias.instance(i);
			
		
			valorEstimadoComparativo = Utilidade.REGRESSOR_COMPARATIVO.classifyInstance(instancia);
			
			if(regressor1 != null){
				// Classificamos esta instância com o algoritimo relativo ao valor
				//valorEstimado1 = (double) Math.abs(regressor1.classifyInstance(instancia));
				valorEstimado1 = (double) regressor1.classifyInstance(instancia);
			}
			
			if(regressor2 != null){
				// Classificamos esta instância com o algoritimo relativo ao valor
				//valorEstimado2 = (double) Math.abs(regressor2.classifyInstance(instancia));
				valorEstimado2 = (double) regressor2.classifyInstance(instancia);
			}

			if(regressor3 != null){
				// Classificamos esta instância com o algoritimo relativo ao valor
				// valorEstimado3 = (double) Math.abs(regressor3.classifyInstance(instancia));
				valorEstimado3 = (double) regressor3.classifyInstance(instancia);
			}
			
			if(regressor4 != null){
				// Classificamos esta instância com o algoritimo relativo ao valor
				// valorEstimado4 = (double) Math.abs(regressor4.classifyInstance(instancia));
				valorEstimado4 = (double) regressor4.classifyInstance(instancia);
			}

			if(regressor5 != null){
				// Classificamos esta instância com o algoritimo relativo ao valor
				// valorEstimado5 = (double) Math.abs(regressor5.classifyInstance(instancia));
				valorEstimado5 = (double) regressor5.classifyInstance(instancia);
			}

			// adiciona os valores estimados por cada regressor a lista de valores estimados			
			List<Double> valoresEstimados = new ArrayList<Double>();
			if(valorEstimado1 != null){
				valoresEstimados.add(valorEstimado1);
			}
			if(valorEstimado2 != null){
				valoresEstimados.add(valorEstimado2);
			}
			if(valorEstimado3 != null){
				valoresEstimados.add(valorEstimado3);
			}
			if(valorEstimado4 != null){
				valoresEstimados.add(valorEstimado4);
			}
			if(valorEstimado5 != null){
				valoresEstimados.add(valorEstimado5);
			}

			// calcula a estimativa de acordo com a regra de combinação
			if(TipoMetodoCombinacao.MEDIA == tipoMetodoCombinacao){
				valorEstimado = Utilidade.calcularMediaLista(valoresEstimados);
			}

			if(TipoMetodoCombinacao.MEDIANA == tipoMetodoCombinacao){
				valorEstimado = Utilidade.calcularMedianaLista(valoresEstimados);
			}

			if (TipoMetodoCombinacao.MODA == tipoMetodoCombinacao) {
				valorEstimado = Utilidade.calcularModaLista(valoresEstimados);
			}

			if(TipoMetodoCombinacao.MINIMO == tipoMetodoCombinacao){
				valorEstimado = Utilidade.calcularMenorValor(valoresEstimados);
			}
			
			if(TipoMetodoCombinacao.MAXIMO == tipoMetodoCombinacao){
				valorEstimado = Utilidade.calcularMaiorValor(valoresEstimados);
			}
			
			if(TipoMetodoCombinacao.MEDIA_DAS_PONTAS == tipoMetodoCombinacao){
				valorEstimado = Utilidade.calcularMediaPontas(valoresEstimados);
			}

	
			// recupera o valor real do esforço
			valorReal = (double) instancia.classValue();

			Utilidade.calculaMetricas(resultado, valorEstimado, valorReal, valorEstimadoComparativo);
		}
	}


	@Override
	public void run(Padrao padrao, TipoMetodoCombinacao tipoMetodoCombinacao, TipoValidacao tipoValidacao) {

		// classes referentes aos regressores individuais
		RegressorIndividualGeral regressorIndividualGeral1 = null;
		RegressorIndividualGeral regressorIndividualGeral2 = null;
		RegressorIndividualGeral regressorIndividualGeral3 = null;
		RegressorIndividualGeral regressorIndividualGeral4 = null;
		RegressorIndividualGeral regressorIndividualGeral5 = null;
		
		// classes resultado que guarda informações de testes de cada regressor
		Resultado resultado1 = null;
		Resultado resultado2 = null;
		Resultado resultado3 = null;
		Resultado resultado4 = null;
		Resultado resultado5 = null;
		
		try {
			
			// define a base de dados que será avaliada na string de resultados 
			Utilidade.adicionaCabecalhoDados(padrao);

			// se o tipo de validação for hold out, ler o arquivo de treinamento em hold out e cria se necessário os arquivos para avaliação hold out
			if(tipoValidacao == TipoValidacao.HOLD_OUT){
			
				// pega um arquivo que já foi criado anteriormente, ou recebe null
				File arquivo = null;
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamento(null, padrao, 1));
				
				// se o arquivo ainda não tiver sido criado é gerado todos os arquivos de treinamento e avaliação
				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTeste(null, padrao);
				}
	
				// percorre todos os arquivos de treinamento e gera um modelo
				for (int i = Constantes.INDICE_INICIAL; i <= Constantes.INDICE_FINAL; i++) {
	
				if(regressor1 != null){
					// treina o regressor1
					regressorIndividualGeral1 = new RegressorIndividualGeral(regressor1);

					// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
						regressorIndividualGeral1.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
						regressorIndividualGeral1.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
					}
					resultado1 = regressorIndividualGeral1.getResultado();
				}
				
				if(regressor2 != null){
					// treina o regressor2
					regressorIndividualGeral2 = new RegressorIndividualGeral(regressor2);
					// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
						regressorIndividualGeral2.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
						regressorIndividualGeral2.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
					}
					resultado2 = regressorIndividualGeral2.getResultado();
				}
				
				if(regressor3 != null){
					// treina o regressor3
					regressorIndividualGeral3 = new RegressorIndividualGeral(regressor3);

					// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
						regressorIndividualGeral3.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
						regressorIndividualGeral3.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
					}
					resultado3 = regressorIndividualGeral3.getResultado();
				}
				if(regressor4 != null){
					// treina o regressor4
					regressorIndividualGeral4 = new RegressorIndividualGeral(regressor4);

					// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
						regressorIndividualGeral4.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
						regressorIndividualGeral4.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
					}
					resultado4 = regressorIndividualGeral4.getResultado();
				}
	
				if(regressor5 != null){
					// treina o regressor5
					regressorIndividualGeral5 = new RegressorIndividualGeral(regressor5);

					// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
					if(Constantes.BASE_VALIDACAO){
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
						regressorIndividualGeral5.criarModelo(Utilidade.getCaminhoTreinamento(null,padrao, i), i, padrao);
					}else{
						// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
						regressorIndividualGeral5.criarModelo(Utilidade.getCaminhoTreinamentoEValidacao(null,padrao, i), i, padrao);						
					}
					resultado5 = regressorIndividualGeral5.getResultado();
				}

				
				// avalia o método de acordo com a base de dados, os resultados que contém os caminhos dos modelos no SO, 
				// a regra de combinação e o tipo de validação para a iteração corrente
				avaliarMetodo(padrao, resultado1, resultado2, resultado3, resultado4, resultado5, tipoMetodoCombinacao, tipoValidacao, i);
				
				}
			}
			
			// se o tipo de validação for leave one out, ler o arquivo de treinamento em leave one out e cria se necessário os arquivos para avaliação leave one out
			if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){
				
				File arquivo = null;
				arquivo = new File(Constantes.CAMINHO_PADRAO_DADOS + Utilidade.getCaminhoTreinamentoLeaveOneOut(null, padrao, 1));
				
				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTesteLeaveOneOut(null, padrao);
				}
	
				for (int i = 1; i <= padrao.getTamanhoBase(); i++) {
	
					if(regressor1 != null){
						// treina o regressor1
						regressorIndividualGeral1 = new RegressorIndividualGeral(regressor1);

						// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
						if(Constantes.BASE_VALIDACAO){
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
							regressorIndividualGeral1.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null,padrao, i), i, padrao);
						}else{
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
							regressorIndividualGeral1.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null,padrao, i), i, padrao);						
						}
						resultado1 = regressorIndividualGeral1.getResultado();

					}
					
					if(regressor2 != null){
						// treina o regressor2
						regressorIndividualGeral2 = new RegressorIndividualGeral(regressor2);
						// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
						if(Constantes.BASE_VALIDACAO){
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
							regressorIndividualGeral2.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null,padrao, i), i, padrao);
						}else{
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
							regressorIndividualGeral2.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null,padrao, i), i, padrao);						
						}
						resultado2 = regressorIndividualGeral2.getResultado();
					}
					
					if(regressor3 != null){
						// treina o regressor3
						regressorIndividualGeral3 = new RegressorIndividualGeral(regressor3);

						// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
						if(Constantes.BASE_VALIDACAO){
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
							regressorIndividualGeral3.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null,padrao, i), i, padrao);
						}else{
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
							regressorIndividualGeral3.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null,padrao, i), i, padrao);						
						}
						resultado3 = regressorIndividualGeral3.getResultado();
					}
					if(regressor4 != null){
						// treina o regressor4
						regressorIndividualGeral4 = new RegressorIndividualGeral(regressor4);

						// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
						if(Constantes.BASE_VALIDACAO){
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
							regressorIndividualGeral4.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null,padrao, i), i, padrao);
						}else{
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
							regressorIndividualGeral4.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null,padrao, i), i, padrao);						
						}
						resultado4 = regressorIndividualGeral4.getResultado();
					}
		
					if(regressor5 != null){
						// treina o regressor5
						regressorIndividualGeral5 = new RegressorIndividualGeral(regressor5);

						// verifica a avaliação para criar os dados de validação ou testes corretamente, o treinamento é diferente dependendo da avaliação
						if(Constantes.BASE_VALIDACAO){
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para validação
							regressorIndividualGeral5.criarModelo(Utilidade.getCaminhoTreinamentoLeaveOneOut(null,padrao, i), i, padrao);
						}else{
							// passa dos dados de treinamento com string vazia ou null porque não importa o algoritmo, o padrão e a iteração i para testes
							regressorIndividualGeral5.criarModelo(Utilidade.getCaminhoTreinamentoEValidacaoLeaveOneOut(null,padrao, i), i, padrao);						
						}
						resultado5 = regressorIndividualGeral5.getResultado();
					}

					
					// avaliar o método de combinação de acordo com os resultados individuais, o dataset e método de combinação
					avaliarMetodo(padrao, resultado1, resultado2, resultado3, resultado4, resultado5, tipoMetodoCombinacao, tipoValidacao, i);
					
					}
			}
			
			// prepara os arquivos de saída com os resultados para o próximo conjunto de dados
			Utilidade.calculaValoresCentraisDasMetricas();
			Utilidade.zerarMediasAuxiliares();
			Utilidade.adicionaLinhaArquivo();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	@Override
	public String toString() {
		return "EnsembleEstatico" + TesteEnsembleEstatico.experimento;
	}

	@Override
	public void run(Padrao padrao, TipoValidacao tipoValidacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Padrao padrao, TipoMetodoCombinacao ensembleEstatico) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(String treino, String teste) {
		// TODO Auto-generated method stub
		
	}
}
