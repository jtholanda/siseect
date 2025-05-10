package util;

public enum TipoValidacao {

	
	HOLD_OUT("HOLD_OUT"), LEAVE_ONE_OUT("LEAVE_ONE_OUT");

	private String nome;
	
	TipoValidacao (String nome){
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	
	
	
	
	
}
