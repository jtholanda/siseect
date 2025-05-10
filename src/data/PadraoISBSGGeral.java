package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Projeto;
import util.TipoValidacao;
import weka.core.Instances;

/**
 * Padrão ISBSG com todos os atributos
 * */
public class PadraoISBSGGeral extends Padrao{

	
	/**
	 * @param args
	 */
		
	private static final int INDICE_ROTULO = 19;
	private int numeroEnsemble = 0;

	public double KNORA_MEDIA_MAR = 3130;
	public double KNORA_MEDIA_MARLOG = 3.12;
	public double KNORA_MEDIA_MRE = 4.03;	
	public double KNORA_MEDIA_BRE = 4.83;
	public double KNORA_MEDIA_MREAJUSTADO = 4.73;	
	
	private static final int QTDE_PROJETOS = 1113;

	
	
	public PadraoISBSGGeral() {
		super();
		knoraMediaMar = KNORA_MEDIA_MAR;
		knoraMediaMarlog = KNORA_MEDIA_MARLOG;
		knoraMediaMre = KNORA_MEDIA_MRE;
		knoraMediaBre = KNORA_MEDIA_BRE;
		knoraMediaMreajustado = KNORA_MEDIA_BRE;
	}

	public PadraoISBSGGeral(int numeroEnsemble) {
		this.numeroEnsemble = numeroEnsemble;
		if(this.numeroEnsemble == 1){
			classificadores = "{KNN3,MLP1,LR}";
		}
		if(this.numeroEnsemble == 2){
			classificadores = "{LMS,MLP2,SVMR}";
		}
		if(this.numeroEnsemble == 3){
			classificadores = "{LMS,M5R,SVMR}";
		}
		if(this.numeroEnsemble == 4){
			classificadores = "{M5R,MLP2,SVMR}";
		}
		if(this.numeroEnsemble == 5){
			classificadores = "{LMS,M5R,MLP2}";
		}
		if(this.numeroEnsemble == 6){
			classificadores = "{LMS,M5R,MLP2,SVMR}";
		}
		if(this.numeroEnsemble == 7){
			classificadores = "{KNN3,KNN7,MLP2,LR,AR,GP,SVR,BA,M5R,M5P}";
		}

		
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
			projeto.setAbordagemContagem(instancias.instance(contador).stringValue(indice++));
			projeto.setGrupoDeAplicacao(instancias.instance(contador).stringValue(indice++));
			projeto.setArquitetura(instancias.instance(contador).stringValue(indice++));
			projeto.setClienteServidor(instancias.instance(contador).stringValue(indice++));
			projeto.setDesenvolvimentoWeb(instancias.instance(contador).stringValue(indice++));
			projeto.setPlataformaDeDesenvolvimento(instancias.instance(contador).stringValue(indice++));			
			projeto.setSetorDaIndustria(instancias.instance(contador).stringValue(indice++));
			projeto.setUsouFerramentaGerenciaProjeto(instancias.instance(contador).stringValue(indice++));
			projeto.setUsouFerramentaCase(instancias.instance(contador).stringValue(indice++));
			projeto.setTipoLinguagem(instancias.instance(contador).stringValue(indice++));
			projeto.setUsouMetodologia(instancias.instance(contador).stringValue(indice++));
			projeto.setTempoDeInatividade(instancias.instance(contador).value(indice++));
			projeto.setTamanhoEquipeGrupo(instancias.instance(contador).stringValue(indice++));
			projeto.setTipoDesenvolvimento(instancias.instance(contador).stringValue(indice++));
			projeto.setMudancaDePessoal(instancias.instance(contador).value(indice++));
			projeto.setUsouDBMS(instancias.instance(contador).stringValue(indice++));
			projeto.setComoMetodologiaAdiquirida(instancias.instance(contador).stringValue(indice++));
			projeto.setEsforcoNormalizado(instancias.instance(contador).value(indice++));



			listaProjetos.add(projeto);

		}
		

		return listaProjetos;
	}

	@Override
	public void imprimirProjetos(List<Projeto> listaProjetos) {
		// Testando se a lista de projetos foi populada corretamente
		
		  for (Projeto projeto : listaProjetos) {
	
		  System.out.print(projeto.getLinguagemProgramacaoPrimaria() + "\t");
		  System.out.print((projeto.getPontosDeFuncoesClassificados()+"").replace(".", ",") + "\t");
		  System.out.print((projeto.getAbordagemContagem()+"") + "\t");
		  System.out.print(projeto.getGrupoDeAplicacao() + "\t");
		  System.out.print(projeto.getArquitetura() + "\t");
		  System.out.print(projeto.getClienteServidor() + "\t");
		  System.out.print(projeto.getDesenvolvimentoWeb() + "\t");
		  System.out.print(projeto.getPlataformaDeDesenvolvimento() + "\t");
		  System.out.print(projeto.getSetorDaIndustria() + "\t");
		  System.out.print(projeto.getUsouFerramentaGerenciaProjeto() + "\t");
		  System.out.print(projeto.getUsouFerramentaCase() + "\t");
		  System.out.print(projeto.getTipoLinguagem() + "\t");
		  System.out.print(projeto.getUsouMetodologia() + "\t");
		  System.out.print((projeto.getTempoDeInatividade()+"").replace(".",",") + "\t");  
		  System.out.print(projeto.getTamanhoEquipeGrupo() + "\t");
		  System.out.print(projeto.getTipoDesenvolvimento() + "\t");
		  System.out.print((projeto.getMudancaDePessoal()+"").replace(".",",") + "\t");  
		  System.out.print(projeto.getUsouDBMS() + "\t");
		  System.out.print(projeto.getComoMetodologiaAdiquirida() + "\t");  
		  System.out.print((projeto.getEsforcoNormalizado()+"").replace(".", ",") + "\t");  
		  System.out.print((projeto.getMenorErro()+"").replace(".",",") + "\t");
		  System.out.println(projeto.getMelhorAlgoritmo());  

		  }

		
	}
	
	@Override
	public String toString() {
		return "ISBSG_GERAL/ISBSG";
	}


	@Override
	public int getIndiceRotuloClassificador() {
		return INDICE_ROTULO;
	}


	@Override
	public String getNomeArquivo() {
		// TODO Auto-generated method stub
		return "ISBSG_GERAL/ISBSG_GERAL.ARFF";
	}
	
	@Override
	public String getCabecalho() {

		return "@relation Softwares_Data\n\n" +

		"@attribute primary_programming_language {Java,C++,Visual-Basic,Visual-C++}" + "\n" +
		"@attribute adjusted_function_points	real" + "\n" +
		"@attribute count_approach {COSMIC,IFPUG4+,FISMA,NESMA,MarkII}" + "\n" +
		"@attribute application_group {Business-Application,Infrastructure-Software,Mathematically-Intensive-Application,Real-Time-Application}" + "\n" +
		"@attribute architecture {Client-server,Multi-tier,Multi-tier-Client-server,Stand-alone,Multi-tier-wwpi}" + "\n" +
		"@attribute client_server {Yes,No}" + "\n" +
		"@attribute web_development {Web,No}" + "\n" +
		"@attribute development_platform	{MF,MR,Multi,PC}" + "\n" +
		"@attribute industry_sector {Banking,Communication,Construction,Defence,Education,Electronics&Computers,Financial,Government,Insurance,Logistics,Manufacturing,Medical&Health-Care,Professional-Services,Service-Industry,Utilities,Wholesale&Retail}" + "\n" +
		"@attribute project_management_tools {Yes,No}" + "\n" +
		"@attribute case_tool_used {Yes,No}" + "\n" +
		"@attribute language_type {3GL,4GL}" + "\n" +
		"@attribute used_methodology {Yes,No}" + "\n" +
		"@attribute project_inactive_time real" + "\n" +
		"@attribute team_size_group {1--1,2--2,3--4,5--8,9--14,15--20,21--30,31--40,41--50,51--70,61--70}" + "\n" +
		"@attribute development_type {Enhancement,New-Development,Re-development}" + "\n" +
		"@attribute personnel_changes real" + "\n" +
		"@attribute dbms_used {Yes,No}" + "\n" +
		"@attribute how_methodology_acquired {Combined-Developed-Purchased,Developed-In-house,Purchased,Traditional}" + "\n" +
		"@attribute normalised_work_effort real" + "\n\n" +
		"@data \n";	
}
	
	@Override
	public String getCabecalhoClassificacao() {

		return "@relation Softwares_Data\n\n" +

		"@attribute primary_programming_language {Java,C++,Visual-Basic,Visual-C++}" + "\n" +
		"@attribute adjusted_function_points	real" + "\n" +
		"@attribute count_approach {COSMIC,IFPUG4+,FISMA,NESMA,MarkII}" + "\n" +
		"@attribute application_group {Business-Application,Infrastructure-Software,Mathematically-Intensive-Application,Real-Time-Application}" + "\n" +
		"@attribute architecture {Client-server,Multi-tier,Multi-tier-Client-server,Stand-alone,Multi-tier-wwpi}" + "\n" +
		"@attribute client_server {Yes,No}" + "\n" +
		"@attribute web_development {Web,No}" + "\n" +
		"@attribute development_platform	{MF,MR,Multi,PC}" + "\n" +
		"@attribute industry_sector {Banking,Communication,Construction,Defence,Education,Electronics&Computers,Financial,Government,Insurance,Logistics,Manufacturing,Medical&Health-Care,Professional-Services,Service-Industry,Utilities,Wholesale&Retail}" + "\n" +
		"@attribute project_management_tools {Yes,No}" + "\n" +
		"@attribute case_tool_used {Yes,No}" + "\n" +
		"@attribute language_type {3GL,4GL}" + "\n" +
		"@attribute used_methodology {Yes,No}" + "\n" +
		"@attribute project_inactive_time real" + "\n" +
		"@attribute team_size_group {1--1,2--2,3--4,5--8,9--14,15--20,21--30,31--40,41--50,51--70,61--70}" + "\n" +
		"@attribute development_type {Enhancement,New-Development,Re-development}" + "\n" +
		"@attribute personnel_changes real" + "\n" +
		"@attribute dbms_used {Yes,No}" + "\n" +
		"@attribute how_methodology_acquired {Combined-Developed-Purchased,Developed-In-house,Purchased,Traditional}" + "\n" +
		"@attribute winner" + classificadores + "\n\n" +
		"@data \n";
	}

	public String getLinhaClassificacao(Projeto projeto, boolean isTreinamento){

		StringBuilder sb = new StringBuilder();
		
		  sb.append(projeto.getLinguagemProgramacaoPrimaria() + "\t");
		  sb.append(projeto.getPontosDeFuncoesClassificados() + "\t");
		  sb.append((projeto.getAbordagemContagem()+"") + "\t");
		  sb.append(projeto.getGrupoDeAplicacao() + "\t");
		  sb.append(projeto.getArquitetura() + "\t");
		  sb.append(projeto.getClienteServidor() + "\t");
		  sb.append(projeto.getDesenvolvimentoWeb() + "\t");
		  sb.append(projeto.getPlataformaDeDesenvolvimento() + "\t");
		  sb.append(projeto.getSetorDaIndustria() + "\t");
		  sb.append(projeto.getUsouFerramentaGerenciaProjeto() + "\t");
		  sb.append(projeto.getUsouFerramentaCase() + "\t");
		  sb.append(projeto.getTipoLinguagem() + "\t");
		  sb.append(projeto.getUsouMetodologia() + "\t");
		  sb.append(projeto.getTempoDeInatividade() + "\t");  
		  sb.append(projeto.getTamanhoEquipeGrupo() + "\t");
		  sb.append(projeto.getTipoDesenvolvimento() + "\t");
		  sb.append(projeto.getMudancaDePessoal() + "\t");  
		  sb.append(projeto.getUsouDBMS() + "\t");
		  sb.append(projeto.getComoMetodologiaAdiquirida() + "\t");  
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
		return "MODELOS/ISBSG_GERAL";
	}

	@Override
	public String getNomePadrao() {
		// TODO Auto-generated method stub
		return "ISBAG_GERAL";
	}

	@Override
	public Integer getR1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getR2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getR3() {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	@Override
	public Integer getClassificador2(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador3(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador4(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificador5(int folds) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade2(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade3(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade4(int folds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getClassificadorComProbabilidade5(int folds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
