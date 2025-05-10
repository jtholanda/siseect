package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Projeto;
import util.TipoValidacao;
import util.WekaExperiment;
import weka.core.Instances;

/**
 * Padrão ISBSG com todos os atributos
 * */
public class PadraoISBSG5 extends Padrao{

	
	/**
	 * @param args
	 */
		
	private static final int INDICE_ROTULO = 15;
	private int numeroEnsemble = 0;

	public double KNORA_MEDIA_MAR = 0;
	public double KNORA_MEDIA_MARLOG = 0;
	public double KNORA_MEDIA_MRE = 0;	
	public double KNORA_MEDIA_BRE = 0;
	public double KNORA_MEDIA_MREAJUSTADO = 0;	
	
	private static final int QTDE_PROJETOS = 1466;

	
	
	public PadraoISBSG5() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMre = KNORA_MEDIA_MRE;
		knoraMediaBre = KNORA_MEDIA_BRE;
		knoraMediaMreajustado = KNORA_MEDIA_BRE;
	}

	public PadraoISBSG5(int numeroEnsemble) {
		this.numeroEnsemble = numeroEnsemble;

		
	}

	@Override
	public List<Projeto> getProjetos(String caminhoArquivo)
			throws FileNotFoundException, IOException {
		
		List<Projeto> listaProjetos = new ArrayList<Projeto>();
		FileReader reader = new FileReader(caminhoArquivo);

		Instances instancias = new Instances(reader);

		int indice = 0;
		for (int contador = 0; contador < instancias.numInstances(); contador++, indice = 0) {

			Projeto projeto = new Projeto();
			projeto.setLinguagemProgramacaoPrimaria(instancias.instance(contador).stringValue(indice++));
			projeto.setPontosDeFuncoesClassificados(instancias.instance(contador).value(indice++));
			projeto.setTipoDesenvolvimento(instancias.instance(contador).stringValue(indice++));
			projeto.setNivelRecurso(instancias.instance(contador).stringValue(indice++));
			projeto.setPlataformaDeDesenvolvimento(instancias.instance(contador).stringValue(indice++));			
			projeto.setGrupoDeAplicacao(instancias.instance(contador).stringValue(indice++));			
			projeto.setSetorDaIndustria(instancias.instance(contador).stringValue(indice++));
			projeto.setUsouDBMS(instancias.instance(contador).stringValue(indice++));
			projeto.setArquitetura(instancias.instance(contador).stringValue(indice++));			
			projeto.setComoMetodologiaAdiquirida(instancias.instance(contador).stringValue(indice++));
			projeto.setUsouFerramentaCase(instancias.instance(contador).stringValue(indice++));
			projeto.setQuantidadeDocumentos((int) instancias.instance(contador).value(indice++));
			projeto.setOcorreuMudancaDePessoalNaEquipe(instancias.instance(contador).stringValue(indice++));
			projeto.setTamanhoMaximoEquipe((int) instancias.instance(contador).value(indice++));
			projeto.setUsouProcessoSoftwareCMMI(instancias.instance(contador).stringValue(indice++));
			projeto.setEsforcoNormalizado(instancias.instance(contador).value(indice++));



			listaProjetos.add(projeto);

		}
		

		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		// Testando se a lista de projetos foi populada corretamente
		
		  for (Projeto projeto : listaProjetos) {
	
				System.out.println(projeto.getLinguagemProgramacaoPrimaria());
				System.out.println(projeto.getPontosDeFuncoesClassificados());
				System.out.println(projeto.getTipoDesenvolvimento());
				System.out.println(projeto.getNivelRecurso());
				System.out.println(projeto.getPlataformaDeDesenvolvimento());			
				System.out.println(projeto.getGrupoDeAplicacao());			
				System.out.println(projeto.getSetorDaIndustria());
				System.out.println(projeto.getUsouDBMS());
				System.out.println(projeto.getArquitetura());			
				System.out.println(projeto.getComoMetodologiaAdiquirida());
				System.out.println(projeto.getUsouFerramentaCase());
				System.out.println(projeto.getQuantidadeDocumentos());
				System.out.println(projeto.getOcorreuMudancaDePessoalNaEquipe());
				System.out.println(projeto.getTamanhoMaximoEquipe());
				System.out.println(projeto.getUsouProcessoSoftwareCMMI());
				System.out.println(projeto.getEsforcoNormalizado());

		  }

		
	}
	
	@Override
	public String toString() {
		return "ISBSG_5/ISBSG_5";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}


	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "ISBSG_5/ISBSG_5.ARFF";
	}
	
	@Override
	public String getCabecalho() {

		return "@relation Softwares_Data\n\n" +

		"@attribute primary_programming_language {Visual_Basic_4G,Visual_Basic,C++,Java,JavaEE,Visual_C++}" + "\n" +
		"@attribute adjusted_function_points numeric" + "\n" + 
		"@attribute development_type {Enhancement,New_Development,Re-development}" + "\n" +
		"@attribute resource_level {1,2,3,4}" + "\n" +
		"@attribute development_plataform {MF,MR,Multi,PC,NAN}" + "\n" +
		"@attribute application_group {Business-Application,Infrastructure-Software,Mathematically-Intensive-Application,Real-Time-Application,NAN}" + "\n" +
		"@attribute industry_sector {Subconjunto_1,Subconjunto_2,Subconjunto_3,Subconjunto_4,Subconjunto_5,Subconjunto_6,Subconjunto_7,Subconjunto_8}" + "\n" +
		"@attribute used_database {Yes,No}" + "\n" +
		"@attribute architecture {Multi-tier,Stand-alone,Multi-tier/Client-server,Multi-tier-with-web-public-interface,NAN}" + "\n" +
		"@attribute how_methodology_acquired {Combined-Developed/Purchased,Developed-In-house,Traditional,NAN}" + "\n" +
		"@attribute case_tool_used {Yes,No,NAN}" + "\n" +
		"@attribute quantity_documents numeric" + "\n" +
		"@attribute personnel_changes {Yes,No}" + "\n" +
		"@attribute max_team_size numeric" + "\n" +
		"@attribute software_process_cmmi {Yes,No}" + "\n" +
		"@attribute normalised_work_effort numeric" + "\n" +
		"@data \n";	
}
	
	@Override
	public String getCabecalhoClassificacao() {

		return "@relation Softwares_Data\n\n" +

		"@attribute primary_programming_language {Visual_Basic_4G,Visual_Basic,C++,Java,JavaEE,Visual_C++}" + "\n" +
		"@attribute adjusted_function_points numeric" + "\n" + 
		"@attribute development_type {Enhancement,New_Development,Re-development}" + "\n" +
		"@attribute resource_level {1,2,3,4}" + "\n" +
		"@attribute development_plataform {MF,MR,Multi,PC,NAN}" + "\n" +
		"@attribute application_group {Business-Application,Infrastructure-Software,Mathematically-Intensive-Application,Real-Time-Application,NAN}" + "\n" +
		"@attribute industry_sector {Subconjunto_1,Subconjunto_2,Subconjunto_3,Subconjunto_4,Subconjunto_5,Subconjunto_6,Subconjunto_7,Subconjunto_8}" + "\n" +
		"@attribute used_database {Yes,No}" + "\n" +
		"@attribute architecture {Multi-tier,Stand-alone,Multi-tier/Client-server,Multi-tier-with-web-public-interface,NAN}" + "\n" +
		"@attribute how_methodology_acquired {Combined-Developed/Purchased,Developed-In-house,Traditional,NAN}" + "\n" +
		"@attribute case_tool_used {Yes,No,NAN}" + "\n" +
		"@attribute quantity_documents numeric" + "\n" +
		"@attribute personnel_changes {Yes,No}" + "\n" +
		"@attribute max_team_size numeric" + "\n" +
		"@attribute software_process_cmmi {Yes,No}" + "\n" +
		"@attribute winner" + classificadores + "\n\n" +
		"@data \n";
	}

	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento){

		StringBuilder sb = new StringBuilder();
		
		sb.append(projeto.getLinguagemProgramacaoPrimaria() + "\t");
		sb.append(projeto.getPontosDeFuncoesClassificados() + "\t");
		sb.append(projeto.getTipoDesenvolvimento() + "\t");
		sb.append(projeto.getNivelRecurso() + "\t");
		sb.append(projeto.getPlataformaDeDesenvolvimento() + "\t");			
		sb.append(projeto.getGrupoDeAplicacao() + "\t");			
		sb.append(projeto.getSetorDaIndustria() + "\t");
		sb.append(projeto.getUsouDBMS() + "\t");
		sb.append(projeto.getArquitetura() + "\t");			
		sb.append(projeto.getComoMetodologiaAdiquirida() + "\t");
		sb.append(projeto.getUsouFerramentaCase() + "\t");
		sb.append(projeto.getQuantidadeDocumentos() + "\t");
		sb.append(projeto.getOcorreuMudancaDePessoalNaEquipe() + "\t");
		sb.append(projeto.getTamanhoMaximoEquipe() + "\t");
		sb.append(projeto.getUsouProcessoSoftwareCMMI()+ "\t");
		  
		  
		if(isTreinamento){
			sb.append(projeto.getMelhorAlgoritmo());
		}else{
			sb.append("?");
		}
		
		return sb.toString();
		
	}

	@Override
	public int getTamanhoBase() {
		// TODO Auto-generated method stub
		return QTDE_PROJETOS;
	}

	@Override
	public TipoValidacao getTipoValidacao() {
		// TODO Auto-generated method stub
		return super.getTipoValidacao();

	}

	@Override
	public String getCaminhoModeloPadrao() {
		// TODO Auto-generated method stub
		return "MODELOS/ISBSG_5";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "ISBSG_5";
	}

	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return WekaExperiment.SUPPORT_VECTOR_REGRESSION;
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return WekaExperiment.LEAST_MED_SQ_2;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return WekaExperiment.M5P_5;
	}

	@Override
	public Integer getR4() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getR5() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador1(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KNN_1;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.J48_1;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KSTAR;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.MLP_3;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.RANDOM_FOREST;
		}
		return classificador;
	}

	@Override
	public Integer getClassificador6(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador7(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador8(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador9(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador10(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade1(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KNN_8;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.RANDOM_FOREST_1;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade3(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.BAGGING_4;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.BAGGING_2;
		}
		return classificador;
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		int classificador = 0;
		if(folds == 1){
			classificador = WekaExperiment.KSTAR;
		}
		return classificador;
	}
	
	
}
