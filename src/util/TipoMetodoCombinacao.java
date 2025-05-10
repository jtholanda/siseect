package util;

public enum TipoMetodoCombinacao {

	
	MEDIA("EE_MEDIA", "ED_MEDIA"), MEDIANA("EE_MEDIANA", "ED_MEDIANA"), MEDIA_DAS_PONTAS("EE_MEDIA_PONTAS", "ED_MEDIA_PONTAS"), MODA("EE_MODA", "ED_MODA"), MINIMO("EE_MINIMO", "ED_MINIMO"),  MAXIMO("EE_MAXIMO", "ED_MAXIMO"), SD("SD", "SD"), ALL("ALL", "ALL"), KNORA_E("KNORA_E", "KNORA_E"), KNORA_U("KNORA_U", "KNORA_U"), DCS_LA("DCS_LA", "DCS_LA"), DCS_LAW("DCS_LAW", "DCS_LAW");;

	private String nomeArquivoEE;
	private String nomeArquivoED;
	
	TipoMetodoCombinacao (String nomeArquivoEE, String nomeArquivoED){
		this.nomeArquivoEE = nomeArquivoEE;
		this.nomeArquivoED = nomeArquivoED;
	}
	
	public String getNomeArquivoEE() {
		return nomeArquivoEE;
	}
	public void setNomeArquivoEE(String nomeArquivoEE) {
		this.nomeArquivoEE = nomeArquivoEE;
	}

	public String getNomeArquivoED() {
		return nomeArquivoED;
	}

	public void setNomeArquivoED(String nomeArquivoED) {
		this.nomeArquivoED = nomeArquivoED;
	}
	
	
	
	
	
	
	
}
