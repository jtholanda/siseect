package method;

import util.TipoMetodoCombinacao;
import util.TipoValidacao;
import weka.classifiers.lazy.IBk;
import data.Padrao;

public class KNN_Knora extends Tecnica {

	public static final String KNN = "KNN";
	private IBk ibk;
	
	public KNN_Knora() {

	}

	public void criarModeloKNORA(Integer k, TipoMetodoCombinacao tipoSelecaoDinamica, String nomeArquivoTreino, int i, Padrao padrao) throws Exception {


		// coloca os dados de treino nas instâncias principais
		configurarDados(nomeArquivoTreino);
		
		// constrói o nodelo que vai fazer a seleção dinâmica por semelhança
		IBk ibk = new IBk(k);
		ibk.buildClassifier(instancias);

		// constrói o arquivo no sistema operacional
		gerarModelo(ibk, getNomeModeloCriadoKnora(k, tipoSelecaoDinamica, i, padrao));

	}

	public String getNomeModeloCriadoKnora(Integer k,
			TipoMetodoCombinacao tipoSelecaoDinamica, int i, Padrao padrao) {
		return padrao.getCaminhoModeloPadrao() + "_" + tipoSelecaoDinamica + "_"+ k + "_" + i;
	}

	public IBk getIbk() {
		return ibk;
	}

	public void setIbk(IBk ibk) {
		this.ibk = ibk;
	}

	public static String getKnn() {
		return KNN;
	}


	@Override
	public void run(String treino, String teste) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run(Padrao padrao, TipoValidacao tipoValidacao) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run(Padrao padrao) {
		// TODO Auto-generated method stub
		
	}
	
	/*public void criarModelo(Integer k, String nomeArquivoTreino) throws Exception {


	configurarDados(nomeArquivoTreino);
	IBk ibk = new IBk(k);
	ibk.buildClassifier(instancias);
	

	gerarModelo(ibk, KNN + k);

	}

	public void usarModelo(String nomeArquivoTeste) throws Exception {
	
		ibk = (IBk) recuperarModelo();
		configurarDados(nomeArquivoTeste);
		
		RegressorIndividualGeral regressorIndividualGeral = new RegressorIndividualGeral(ibk);
		regressorIndividualGeral.avaliarModelo(ibk, instancias, nomeModeloCriado);
	
		//resultado = new Resultado();
		//resultado.avaliarModelo(ibk, instancias, nomeModeloCriado);
		
	}
	
	public void run(String treino, String validacao, Integer k) {
		try {
			
			criarModelo(k, treino);
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
				
				arquivo = new File(Constantes.CAMINHO_PADRAO + Utilidade.getCaminhoTreinamento("CONSTANTE", padrao, 1));

				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTeste("CONSTANTE", padrao);
				}
				
				for(int i = 1; i <= 50; i++){
					KNN_Knora knn = new KNN_Knora();
					knn.criarModelo(k, Utilidade.getCaminhoTreinamento("CONSTANTE",padrao, i));
					if(Constantes.BASE_VALIDACAO){
						knn.usarModelo(Utilidade.getCaminhoValidacao("CONSTANTE", padrao, i));
					}else{
						knn.usarModelo(Utilidade.getCaminhoTeste("CONSTANTE", padrao, i));						
					}
				}
			}
			
			if(tipoValidacao == TipoValidacao.LEAVE_ONE_OUT){

				arquivo = new File(Constantes.CAMINHO_PADRAO + Utilidade.getCaminhoTreinamentoLeaveOneOut("CONSTANTE", padrao, 1));

				if(!arquivo.exists()){
					GeradorAleatorioDados.gerarTreinamentoValidacaoTesteLeaveOneOut("CONSTANTE", padrao);
				}

				for(int i = 1; i <= padrao.getTamanhoBase(); i++){
					KNN_Knora knn = new KNN_Knora();
					knn.criarModelo(k, Utilidade.getCaminhoTreinamentoLeaveOneOut("CONSTANTE",padrao, i));
					if(Constantes.BASE_VALIDACAO){
						knn.usarModelo(Utilidade.getCaminhoValidacaoLeaveOneOut("CONSTANTE", padrao, i));
					}else{
						knn.usarModelo(Utilidade.getCaminhoTesteLeaveOneOut("CONSTANTE", padrao, i));
					}
				}

			}
			
			Utilidade.calculaMediasMetricas();
			Utilidade.zerarMediasAuxiliares();
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
			
				
			Utilidade.adicionaCabecalhoDados(padrao);
			arquivo = new File(Constantes.CAMINHO_PADRAO + Utilidade.getCaminhoTreinamento("CONSTANTE", padrao, 1));

			if(!arquivo.exists()){
				GeradorAleatorioDados.gerarTreinamentoValidacaoTeste("CONSTANTE", padrao);
			}
			
			for(int i = 1; i <= 50; i++){
				KNN_Knora knn = new KNN_Knora();
				knn.criarModelo(k, Utilidade.getCaminhoTreinamento("CONSTANTE",padrao, i));
				knn.usarModelo(Utilidade.getCaminhoValidacao("CONSTANTE", padrao, i));
			}
	
			Utilidade.calculaMediasMetricas();
			Utilidade.zerarMediasAuxiliares();
			Utilidade.adicionaLinhaArquivo();

		}catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void runKNORA(String treino, String validacao, Integer k) {
		try {
			
			criarModeloKNORA(k, treino, 1);
			usarModelo(validacao);
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void run(String treino, String teste) {
		// TODO Auto-generated method stub
		
	}

	*/

	
}
