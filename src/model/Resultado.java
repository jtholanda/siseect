package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Utilities;

import util.Utilidade;

public class Resultado {

	private String nomeModelo;
	String relatorio = "";
	private double erroAbsolutoMedia;
	private double erroMarLogMedia;
	private double taxaDeAcertoDoClassificador;
	protected boolean acertouAlgoritmo;
	private int acertosClassificador;
	private int errosClassificador;
	private List<Double> estimativas = new ArrayList<Double>();
	private List<Double> valoresReais = new ArrayList<Double>();
	private List<Double> errosAbsolutos = new ArrayList<Double>();
	private List<Double> marlogs = new ArrayList<Double>();
	private List<Double> bres = new ArrayList<Double>();
	private List<Double> mres = new ArrayList<Double>();
	private List<Double> errosQuadraticos = new ArrayList<Double>();
	private List<Double> errosLogQuadraticos = new ArrayList<Double>();
	private List<Double> mresAjustados = new ArrayList<Double>();
	private List<Double> numeradoresR2 = new ArrayList<Double>();
	private List<Double> denominadoresR2 = new ArrayList<Double>();
	private List<Double> errosGanhosRelativos = new ArrayList<Double>();
	private double mresAjustadoMedia;
	private double mreMedia;
	private double breMedia;
	private double errosQuadraticosMedia;
	private double errosLogQuadraticosMedia;
	private double raizErrosquadraticosMedia;
	private double rQuadrado;
	private Double mediaValoresReais;
	private double ganhoRelativo;
	
	public Resultado() {
		super();
	}

	public Resultado(String nomeModelo, ArrayList<String> atributosSelecionados) {
		super();
		this.nomeModelo = nomeModelo;
	}




	public String getNomeModelo() {
		return nomeModelo;
	}

	public void setNomeModelo(String nomeModelo) {
		this.nomeModelo = nomeModelo;
	}

	public List<Double> getErrosAbsolutos() {
		return errosAbsolutos;
	}
	
	public String getRelatorio() {
		return relatorio;
	}	
	
	
	public List<Double> getMarlogs() {
		return marlogs;
	}

	public void setMarlogs(List<Double> marlogs) {
		this.marlogs = marlogs;
	}

	public List<Double> getMresAjustados() {
		return mresAjustados;
	}

	public void setMresAjustados(List<Double> mresAjustados) {
		this.mresAjustados = mresAjustados;
	}

	public List<Double> getBres() {
		return bres;
	}

	public void setBres(List<Double> bres) {
		this.bres = bres;
	}

	public List<Double> getMres() {
		return mres;
	}

	public void setMres(List<Double> mres) {
		this.mres = mres;
	}

	public List<Double> getErrosQuadraticos() {
		return errosQuadraticos;
	}

	public void setErrosQuadraticos(List<Double> errosQuadraticos) {
		this.errosQuadraticos = errosQuadraticos;
	}
	
	public List<Double> getErrosLogQuadraticos() {
		return errosLogQuadraticos;
	}

	public void setErrosLogQuadraticos(List<Double> errosLogQuadraticos) {
		this.errosLogQuadraticos = errosLogQuadraticos;
	}
	
	public List<Double> getNumeradoresR2() {
		return numeradoresR2;
	}

	public void setNumeradoresR2(List<Double> numeradoresR2) {
		this.numeradoresR2 = numeradoresR2;
	}

	public List<Double> getDenominadoresR2() {
		return denominadoresR2;
	}

	public void setDenominadoresR2(List<Double> denominadoresR2) {
		this.denominadoresR2 = denominadoresR2;
	}
	
	public List<Double> getErrosGanhosRelativos() {
		return errosGanhosRelativos;
	}

	public void setErrosGanhosRelativos(List<Double> errosGanhosRelativos) {
		this.errosGanhosRelativos = errosGanhosRelativos;
	}

	public double getErroAbsoluto() {
		return erroAbsolutoMedia;
	}
	

	// calcula a média dos erros absolutos calculados na iteração e adiciona nas listas de erros
	public void calculaMediaErroAbsoluto() {

		
		this.erroAbsolutoMedia = Utilidade.calcularMediaLista(errosAbsolutos);
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_MAR.add((""+erroAbsolutoMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_MAR.add((""+erroAbsolutoMedia).replace(".", ","));
		}
	}
	
	// calcula a média dos logaritmos dos erros absolutos calculados na iteração e adiciona nas listas de erros
	public void calculaMediaMarLog() {

		this.erroMarLogMedia = Utilidade.calcularMediaLista(marlogs);
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_MARLOG.add((""+erroMarLogMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_MARLOG.add((""+erroMarLogMedia).replace(".", ","));
		}
	}

	// calcula a média dos valores de MRE ajustados calculados na iteração e adiciona nas listas de erros
	public void calculaMediaMREAjustado() {

		this.mresAjustadoMedia = Utilidade.calcularMediaLista(mresAjustados);

		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_MREAJUSTADO.add((""+mresAjustadoMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_MREAJUSTADO.add((""+mresAjustadoMedia).replace(".", ","));
		}

	}

	// calcula a média dos valores de MRE calculados na iteração e adiciona nas listas de erros
	public void calculaMediaMRE() {


		this.mreMedia = Utilidade.calcularMediaLista(mres);
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_MRE.add((""+mreMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_MRE.add((""+mreMedia).replace(".", ","));
		}

	}
	
	// calcula a média dos valores de BRE calculados na iteração e adiciona nas listas de erros
	public void calculaMediaBRE() {


		this.breMedia = Utilidade.calcularMediaLista(bres);

		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_BRE.add((""+breMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_BRE.add((""+breMedia).replace(".", ","));
		}

	}
	
	// calcula a média dos valores dos erros quadráticos calculados na iteração e adiciona nas listas de erros
	public void calculaMediaErrosQuadraticos() {

		this.errosQuadraticosMedia = Utilidade.calcularMediaLista(errosQuadraticos);
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_MSE.add((""+errosQuadraticosMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_MSE.add((""+errosQuadraticosMedia).replace(".", ","));

		}

	}

	// calcula a média dos valores dos erros quadráticos calculados na iteração e adiciona nas listas de erros
	public void calculaRaizMediaErrosQuadraticos() {

		if(errosQuadraticos.size() == 0){
			this.errosQuadraticosMedia = Utilidade.calcularMediaLista(errosQuadraticos);
		}

		this.raizErrosquadraticosMedia = Math.sqrt(errosQuadraticosMedia);
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){

			Utilidade.RESULTADOS_METRICA_RMSE.add((""+raizErrosquadraticosMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_RMSE.add((""+raizErrosquadraticosMedia).replace(".", ","));
		}

	}

	// calcula a média dos valores dos erros dos logaritmos quadráticos calculados na iteração e adiciona nas listas de erros
	public void calculaMediaErrosLogQuadraticos() {


		this.errosLogQuadraticosMedia = Utilidade.calcularMediaLista(errosLogQuadraticos);
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_RMSLE.add((""+errosLogQuadraticosMedia).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_RMSLE.add((""+errosLogQuadraticosMedia).replace(".", ","));
		}

	}
	

	// calcula R2 na iteração e adiciona nas listas de erros
	public void calculaRQuadrado() {

		double numerador = 0; 
		double denominador = 0;
		

		numerador = Utilidade.calcularMediaLista(numeradoresR2);
		denominador = Utilidade.calcularMediaLista(denominadoresR2);
				
		
		this.rQuadrado = 1 - (numerador / denominador);
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_R2.add((""+rQuadrado).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_R2.add((""+rQuadrado).replace(".", ","));
		}

	}

	
	// calcula os ganhos relativos de acordo com o erros que foram calculados na iteração e adiciona nas listas de erros
	public void calculaGanhoRelativo() {


		double mediaPiorErro = 0;
		mediaPiorErro = Utilidade.calcularMediaLista(errosGanhosRelativos);
		this.ganhoRelativo = (mediaPiorErro - this.erroAbsolutoMedia)/mediaPiorErro;
		
		if(Utilidade.ADICIONAR_RESULTADOS_INDIVIDUAIS){
			Utilidade.RESULTADOS_METRICA_RG.add((""+ganhoRelativo).replace(".", ","));
			Utilidade.AUXILIAR_METRICA_RG.add((""+ganhoRelativo).replace(".", ","));
		}

	}

	
	private Double calculaMediaPiorErro(){

		
		double somaPioresErros = 0; 
		double piorErro = 0;
		
		mediaValoresReais = Utilidade.calcularMediaLista(valoresReais);
		
		for (Double valorReal : valoresReais) {
			
			somaPioresErros += Math.abs(valorReal-mediaValoresReais);
			
		}
		
		piorErro = somaPioresErros/valoresReais.size();
		
		return piorErro;

	}
	
	

	
	public double getTaxaDeAcertoDoClassificador() {
		return taxaDeAcertoDoClassificador;
	}
	
	public void calculaTaxaDeAcertoDoClassificador(){
		taxaDeAcertoDoClassificador = acertosClassificador*100 / (acertosClassificador + errosClassificador);
		
	}

	public boolean isAcertouAlgoritmo() {
		return acertouAlgoritmo;
	}

	public void setAcertouAlgoritmo(boolean acertouAlgoritmo) {
		this.acertouAlgoritmo = acertouAlgoritmo;
	}
	
	public void addAcerto() {
		this.acertosClassificador++;
	}

	public void addErro(){
		this.errosClassificador++;
	}

	public List<Double> getEstimativas() {
		return estimativas;
	}

	public void setEstimativas(List<Double> estimativas) {
		this.estimativas = estimativas;
	}

	public List<Double> getValoresReais() {
		return valoresReais;
	}

	public void setValoresReais(List<Double> valoresReais) {
		this.valoresReais = valoresReais;
	}

	

	public StringBuilder toStringEstimativas() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for (Double estimativa: estimativas){
			sb.append(estimativa.toString());
			sb.append("\n");
		}
		return sb;
	}

	public String toStringSimples() {
	
		relatorio = "";

		relatorio += "Erro Absoluto Médio: " + getErroAbsoluto() + "\n";
		relatorio += "Taxa de Acerto do Classificador: " + getTaxaDeAcertoDoClassificador() + "%" + "\n";
	
		relatorio += nomeModelo;
		return relatorio;
	}
	@Override
	public String toString() {

	
		relatorio = "";

	

		relatorio += "Erro Absoluto Médio: " + getErroAbsoluto() + "\n";
		relatorio += "Taxa de Acerto do Classificador: " + getTaxaDeAcertoDoClassificador() + "%" + "\n";
	

		relatorio += nomeModelo;
		return relatorio;
	}
	
	
	
}
