package model;

public class ResultadoIdeal extends Resultado {

	private double melhorValorEstimado;
	private double menorErroEstimado;
	private String melhorAlgoritmo;
	
	
	public double getMelhorValorEstimado() {
		return melhorValorEstimado;
	}
	public void setMelhorValorEstimado(double melhorValorEstimado) {
		this.melhorValorEstimado = melhorValorEstimado;
	}
	public double getMenorErroEstimado() {
		return menorErroEstimado;
	}
	public void setMenorErroEstimado(double menorErroEstimado) {
		this.menorErroEstimado = menorErroEstimado;
	}
	public String getMelhorAlgoritmo() {
		return melhorAlgoritmo;
	}
	public void setMelhorAlgoritmo(String melhorAlgoritmo) {
		this.melhorAlgoritmo = melhorAlgoritmo;
	}
	public boolean isAcertouAlgoritmo(double valorEstimadoReal){
		if(valorEstimadoReal - melhorValorEstimado == 0){
			acertouAlgoritmo = true;
			return acertouAlgoritmo;
		}
		return false;
	}
	
	
}
