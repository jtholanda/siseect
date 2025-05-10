package util;

public enum TipoMetricaAvaliacao {

	
	MRE("MRE"), MAR("MAR"), MARLOG("MARLOG"), BRE("BRE"), MREAJUSTADO("MREAJUSTADO");

	private String nome;
	
	TipoMetricaAvaliacao (String nome){
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	
	
	
	
	
}
