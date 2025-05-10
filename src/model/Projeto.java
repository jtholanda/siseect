package model;

public class Projeto {

	// atributos do isbsg
	private String linguagemProgramacaoPrimaria;
	private int pontosFuncoesAjustados;
	private String grupoDeAplicacao;
	private String arquitetura;
	private String clienteServidor;
	private String desenvolvimentoWeb;
	private String plataformaDeDesenvolvimento;
	private String setorDaIndustria;
	private String tipoLinguagem;
	private double tempoDeInatividade;
	private String tipoDesenvolvimento;
	private double mudancaDePessoal;
	private String ocorreuMudancaDePessoalNaEquipe;
	private String usouDBMS;
	private double esforcoNormalizado;
	private double esforcoSumarizado;
	private double pontosDeFuncoesClassificados;
	private String comoMetodologiaAdiquirida;
	private String usouFerramentaGerenciaProjeto;
	private String houveTempoInativo;
	private String tamanhoEquipeGrupo;
	private String totalDefeitosEntregues;
	private String usouMetodologia;
	private String usouFerramentaCase;
	private String abordagemContagem;
	private String nivelRecurso;
	private int quantidadeDocumentos;
	private int tamanhoMaximoEquipe;
	private String usouProcessoSoftwareCMMI;

	// atributos da nasa e cocomonasav2
	private int embedded;
	private int organic;
	private int semiDetached;
	private double rely;
	private double data;
	private double cplx;
	private double time;
	private double stor;
	private double virt;
	private double turn;
	private double acap;
	private double aexp;
	private double pcap;
	private double vexp;
	private double lexp;
	private double modp;
	private double tool;
	private double sced;
	private double equivPhyskLoc;
	private double actEffort;
	
	private String projectName;
	private String cat2;
	private char forg;
	private String center;
	private double year;
	private String mode;
	private String relyString;
	private String dataString;
	private String cplxString;
	private String timeString;
	private String storString;
	private String virtString;
	private String turnString;
	private String acapString;
	private String aexpString;
	private String pcapString;
	private String vexpString;
	private String lexpString;
	private String modpString;
	private String toolString;
	private String scedString;
	
	

	// atributos desharnais
	private double teamExp;
	private double managerExp;
	private double yearEnd;
	private double length;
	private double transactions;
	private double entities;
	private double pointsAdjust;
	private double envergure;
	private double pointsNonAjust;
	private int language;
	private double adjustment;
	private double effort;

	// atributos da cocomo-nasa
	private double loc;
	
	// atributos do cocomo81
	private String devMode;
	
	// atributos cpu
	private double myct;
	private double mmin;
	private double mmax;
	private double cach;
	private double chmin;
	private double chmax;
	private double classCPU;
	
	//atributos maxwell
	private double syear;
	private double app;
	private double har;
	private double dba;
	private double ifc;
	private double src;
	private double telonuse;
	private double nlan;
	private double t01;
	private double t02;
	private double t03;
	private double t04;
	private double t05;
	private double t06;
	private double t07;
	private double t08;
	private double t09;
	private double t010;
	private double t011;
	private double t012;
	private double t013;
	private double t014;
	private double t015;
	private double duration;
	private double size;
	private double timeMaxwell;
	private double effortMaxwell;
	
	// atributos sdr
	private String prec;
	private String flex;
	private String resl;
	private String team;
	private String pmat;
	private String ruse;
	private String docu;
	private String pvol;
	private String pcon;
	private String pexp;
	private String ltex;
	private String site; 
	
	// atributos albrecht
	private double input;
	private double output;
	private double inquiry;
	private double file;
	private double fpAdjust;
	private double rawFPcounts;
	private double adjFP;
	private double effortAlbrecht;
	
	// atributos kemerer
	private int hardware;
	private double ksloc;
	private double EffortMM;
	
	// atributos kitchenham
	private String clienteCode;
	private String projectType;
	private int actualDuration;
	private double adjustedFunctionPoints;
	private String firstEstimateMethod;
	private double actualEffort;
	
	
	// atributos miyazaki94
	private double kloc;
	private int scrn;
	private int form;
	private int escrn;
	private int eform;
	private int efile;
	private double mm;
	
	// atributos gerais
	private double menorErro;
	private String melhorAlgoritmo;
	private double produtividade;

	// atributos china
	private double afp;
	//private int input1;
	//private int output1;
	private double enquiry;
	//private int file1;
	private double interface1;
	private double added;
	private double changed;
	private double deleted;
	private double pdrAfp;
	private double pdrUfp;
	private double nPdrAfp;
	private double nPduAfp;

	// atributos taxa de aprovação do fundamental e médio
	
	private double afd1;
	private double afd2;
	private double afd3;
	private double afd4;
	private double afd5;
	private double atu;
	private double dsu;
	private double icg;
	private double ied1;
	private double ied2;
	private double ied3;
	private double ied4;
	private double ied5;
	private double ied6;
	private double ird;
	private double tdi;
	private double ta;
	private double tr;
	private double te;

	
	
	// métodos do isbsg
	public String getLinguagemProgramacaoPrimaria() {
		return linguagemProgramacaoPrimaria;
	}

	public void setLinguagemProgramacaoPrimaria(
			String linguagemProgramacaoPrimaria) {
		this.linguagemProgramacaoPrimaria = linguagemProgramacaoPrimaria;
	}

	public int getPontosFuncoesAjustados() {
		return pontosFuncoesAjustados;
	}

	public void setPontosFuncoesAjustados(int pontosFuncoesAjustados) {
		this.pontosFuncoesAjustados = pontosFuncoesAjustados;
	}

	public String getGrupoDeAplicacao() {
		return grupoDeAplicacao;
	}

	public void setGrupoDeAplicacao(String grupoDeAplicacao) {
		this.grupoDeAplicacao = grupoDeAplicacao;
	}

	public String getArquitetura() {
		return arquitetura;
	}

	public void setArquitetura(String arquitetura) {
		this.arquitetura = arquitetura;
	}

	public String getClienteServidor() {
		return clienteServidor;
	}

	public void setClienteServidor(String clienteServidor) {
		this.clienteServidor = clienteServidor;
	}

	public String getDesenvolvimentoWeb() {
		return desenvolvimentoWeb;
	}

	public void setDesenvolvimentoWeb(String desenvolvimentoWeb) {
		this.desenvolvimentoWeb = desenvolvimentoWeb;
	}

	public String getPlataformaDeDesenvolvimento() {
		return plataformaDeDesenvolvimento;
	}

	public void setPlataformaDeDesenvolvimento(
			String plataformaDeDesenvolvimento) {
		this.plataformaDeDesenvolvimento = plataformaDeDesenvolvimento;
	}

	public String getSetorDaIndustria() {
		return setorDaIndustria;
	}

	public void setSetorDaIndustria(String setorDaIndustria) {
		this.setorDaIndustria = setorDaIndustria;
	}

	public String getTipoLinguagem() {
		return tipoLinguagem;
	}

	public void setTipoLinguagem(String tipoLinguagem) {
		this.tipoLinguagem = tipoLinguagem;
	}

	public double getTempoDeInatividade() {
		return tempoDeInatividade;
	}

	public void setTempoDeInatividade(double tempoDeInatividade) {
		this.tempoDeInatividade = tempoDeInatividade;
	}

	public String getTipoDesenvolvimento() {
		return tipoDesenvolvimento;
	}

	public void setTipoDesenvolvimento(String tipoDesenvolvimento) {
		this.tipoDesenvolvimento = tipoDesenvolvimento;
	}

	public double getMudancaDePessoal() {
		return mudancaDePessoal;
	}

	public void setMudancaDePessoal(double mudancaDePessoal) {
		this.mudancaDePessoal = mudancaDePessoal;
	}

	public String getOcorreuMudancaDePessoalNaEquipe() {
		return ocorreuMudancaDePessoalNaEquipe;
	}

	public void setOcorreuMudancaDePessoalNaEquipe(
			String ocorreuMudancaDePessoalNaEquipe) {
		this.ocorreuMudancaDePessoalNaEquipe = ocorreuMudancaDePessoalNaEquipe;
	}

	public String getUsouDBMS() {
		return usouDBMS;
	}

	public void setUsouDBMS(String usouDBMS) {
		this.usouDBMS = usouDBMS;
	}

	public double getEsforcoNormalizado() {
		return esforcoNormalizado;
	}

	public void setEsforcoNormalizado(double esforcoNormalizado) {
		this.esforcoNormalizado = esforcoNormalizado;
	}

	public double getEsforcoSumarizado() {
		return esforcoSumarizado;
	}

	public void setEsforcoSumarizado(double esforcoSumarizado) {
		this.esforcoSumarizado = esforcoSumarizado;
	}

	public double getPontosDeFuncoesClassificados() {
		return pontosDeFuncoesClassificados;
	}

	public void setPontosDeFuncoesClassificados(
			double pontosDeFuncoesClassificados) {
		this.pontosDeFuncoesClassificados = pontosDeFuncoesClassificados;
	}

	public String getComoMetodologiaAdiquirida() {
		return comoMetodologiaAdiquirida;
	}

	public void setComoMetodologiaAdiquirida(String comoMetodologiaAdiquirida) {
		this.comoMetodologiaAdiquirida = comoMetodologiaAdiquirida;
	}

	public String getUsouFerramentaGerenciaProjeto() {
		return usouFerramentaGerenciaProjeto;
	}

	public void setUsouFerramentaGerenciaProjeto(
			String usouFerramentaGerenciaProjeto) {
		this.usouFerramentaGerenciaProjeto = usouFerramentaGerenciaProjeto;
	}

	public String getHouveTempoInativo() {
		return houveTempoInativo;
	}

	public void setHouveTempoInativo(String houveTempoInativo) {
		this.houveTempoInativo = houveTempoInativo;
	}

	public String getTamanhoEquipeGrupo() {
		return tamanhoEquipeGrupo;
	}

	public void setTamanhoEquipeGrupo(String tamanhoEquipeGrupo) {
		this.tamanhoEquipeGrupo = tamanhoEquipeGrupo;
	}
	
	public String getTotalDefeitosEntregues() {
		return totalDefeitosEntregues;
	}

	public void setTotalDefeitosEntregues(String totalDefeitosEntregues) {
		this.totalDefeitosEntregues = totalDefeitosEntregues;
	}
	
	public String getUsouMetodologia() {
		return usouMetodologia;
	}

	public void setUsouMetodologia(String usouMetodologia) {
		this.usouMetodologia = usouMetodologia;
	}
	

	public String getUsouFerramentaCase() {
		return usouFerramentaCase;
	}

	public void setUsouFerramentaCase(String usouFerramentaCase) {
		this.usouFerramentaCase = usouFerramentaCase;
	}
	

	public String getAbordagemContagem() {
		return abordagemContagem;
	}

	public void setAbordagemContagem(String abordagemContagem) {
		this.abordagemContagem = abordagemContagem;
	}
	
	public String getNivelRecurso() {
		return nivelRecurso;
	}

	public void setNivelRecurso(String nivelRecurso) {
		this.nivelRecurso = nivelRecurso;
	}
	
	public int getQuantidadeDocumentos() {
		return quantidadeDocumentos;
	}

	public void setQuantidadeDocumentos(int quantidadeDocumentos) {
		this.quantidadeDocumentos = quantidadeDocumentos;
	}
	

	public int getTamanhoMaximoEquipe() {
		return tamanhoMaximoEquipe;
	}

	public void setTamanhoMaximoEquipe(int tamanhoMaximoEquipe) {
		this.tamanhoMaximoEquipe = tamanhoMaximoEquipe;
	}

	// métodos da nasa e cocomonasav2
	public int getEmbedded() {
		return embedded;
	}

	public void setEmbedded(int embedded) {
		this.embedded = embedded;
	}

	public int getOrganic() {
		return organic;
	}

	public void setOrganic(int organic) {
		this.organic = organic;
	}

	public int getSemiDetached() {
		return semiDetached;
	}

	public void setSemiDetached(int semiDetached) {
		this.semiDetached = semiDetached;
	}

	public double getRely() {
		return rely;
	}

	public void setRely(double rely) {
		this.rely = rely;
	}

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public double getCplx() {
		return cplx;
	}

	public void setCplx(double cplx) {
		this.cplx = cplx;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getStor() {
		return stor;
	}

	public void setStor(double stor) {
		this.stor = stor;
	}

	public double getVirt() {
		return virt;
	}

	public void setVirt(double virt) {
		this.virt = virt;
	}

	public double getTurn() {
		return turn;
	}

	public void setTurn(double turn) {
		this.turn = turn;
	}

	public double getAcap() {
		return acap;
	}

	public void setAcap(double acap) {
		this.acap = acap;
	}
	
	public double getAexp() {
		return aexp;
	}

	public void setAexp(double aexp) {
		this.aexp = aexp;
	}

	public double getPcap() {
		return pcap;
	}

	public void setPcap(double pcap) {
		this.pcap = pcap;
	}

	public double getVexp() {
		return vexp;
	}

	public void setVexp(double vexp) {
		this.vexp = vexp;
	}
	
	public double getLexp() {
		return lexp;
	}

	public void setLexp(double lexp) {
		this.lexp = lexp;
	}

	public double getModp() {
		return modp;
	}

	public void setModp(double modp) {
		this.modp = modp;
	}

	public double getTool() {
		return tool;
	}

	public void setTool(double tool) {
		this.tool = tool;
	}

	public double getSced() {
		return sced;
	}

	public void setSced(double sced) {
		this.sced = sced;
	}

	public double getEquivPhyskLoc() {
		return equivPhyskLoc;
	}

	public void setEquivPhyskLoc(double equivPhyskLoc) {
		this.equivPhyskLoc = equivPhyskLoc;
	}

	public double getActEffort() {
		return actEffort;
	}

	public void setActEffort(double actEffort) {
		this.actEffort = actEffort;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCat2() {
		return cat2;
	}

	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}

	public char getForg() {
		return forg;
	}

	public void setForg(char forg) {
		this.forg = forg;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public double getYear() {
		return year;
	}

	public void setYear(double year) {
		this.year = year;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRelyString() {
		return relyString;
	}

	public void setRelyString(String relyString) {
		this.relyString = relyString;
	}

	public String getDataString() {
		return dataString;
	}

	public void setDataString(String dataString) {
		this.dataString = dataString;
	}

	public String getCplxString() {
		return cplxString;
	}

	public void setCplxString(String cplxString) {
		this.cplxString = cplxString;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getStorString() {
		return storString;
	}

	public void setStorString(String storString) {
		this.storString = storString;
	}

	public String getVirtString() {
		return virtString;
	}

	public void setVirtString(String virtString) {
		this.virtString = virtString;
	}

	public String getTurnString() {
		return turnString;
	}

	public void setTurnString(String turnString) {
		this.turnString = turnString;
	}
	
	public String getAcapString() {
		return acapString;
	}

	public void setAcapString(String acapString) {
		this.acapString = acapString;
	}

	public String getAexpString() {
		return aexpString;
	}

	public void setAexpString(String aexpString) {
		this.aexpString = aexpString;
	}

	public String getPcapString() {
		return pcapString;
	}

	public void setPcapString(String pcapString) {
		this.pcapString = pcapString;
	}

	public String getVexpString() {
		return vexpString;
	}

	public void setVexpString(String vexpString) {
		this.vexpString = vexpString;
	}

	public String getLexpString() {
		return lexpString;
	}

	public void setLexpString(String lexpString) {
		this.lexpString = lexpString;
	}

	public String getModpString() {
		return modpString;
	}

	public void setModpString(String modpString) {
		this.modpString = modpString;
	}
	
	public String getToolString() {
		return toolString;
	}

	public void setToolString(String toolString) {
		this.toolString = toolString;
	}

	public String getScedString() {
		return scedString;
	}

	public void setScedString(String scedString) {
		this.scedString = scedString;
	}
	
	

	public String getUsouProcessoSoftwareCMMI() {
		return usouProcessoSoftwareCMMI;
	}

	public void setUsouProcessoSoftwareCMMI(String usouProcessoSoftwareCMMI) {
		this.usouProcessoSoftwareCMMI = usouProcessoSoftwareCMMI;
	}

	// métodos desharnais
	public double getTeamExp() {
		return teamExp;
	}

	public void setTeamExp(double teamExp) {
		this.teamExp = teamExp;
	}

	public double getManagerExp() {
		return managerExp;
	}

	public void setManagerExp(double managerExp) {
		this.managerExp = managerExp;
	}

	public double getYearEnd() {
		return yearEnd;
	}

	public void setYearEnd(double yearEnd) {
		this.yearEnd = yearEnd;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getTransactions() {
		return transactions;
	}

	public void setTransactions(double transactions) {
		this.transactions = transactions;
	}

	public double getEntities() {
		return entities;
	}

	public void setEntities(double entities) {
		this.entities = entities;
	}

	public double getPointsAdjust() {
		return pointsAdjust;
	}

	public void setPointsAdjust(double pointsAdjust) {
		this.pointsAdjust = pointsAdjust;
	}

	public double getEnvergure() {
		return envergure;
	}

	public void setEnvergure(double envergure) {
		this.envergure = envergure;
	}

	public double getPointsNonAjust() {
		return pointsNonAjust;
	}

	public void setPointsNonAjust(double pointsNonAjust) {
		this.pointsNonAjust = pointsNonAjust;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}
	
	public double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(double adjustment) {
		this.adjustment = adjustment;
	}

	public double getEffort() {
		return effort;
	}

	public void setEffort(double effort) {
		this.effort = effort;
	}
	
	// métodos cocomo-nasa	
	public double getLoc() {
		return loc;
	}

	public void setLoc(double loc) {
		this.loc = loc;
	}

	// métodos do cocomo81
	public String getDevMode() {
		return devMode;
	}

	public void setDevMode(String devMode) {
		this.devMode = devMode;
	}

	
	// métodos cpu
	public double getMyct() {
		return myct;
	}

	public void setMyct(double myct) {
		this.myct = myct;
	}

	public double getMmin() {
		return mmin;
	}

	public void setMmin(double mmin) {
		this.mmin = mmin;
	}

	public double getMmax() {
		return mmax;
	}

	public void setMmax(double mmax) {
		this.mmax = mmax;
	}

	public double getCach() {
		return cach;
	}

	public void setCach(double cach) {
		this.cach = cach;
	}

	public double getChmin() {
		return chmin;
	}

	public void setChmin(double chmin) {
		this.chmin = chmin;
	}

	public double getChmax() {
		return chmax;
	}

	public void setChmax(double chmax) {
		this.chmax = chmax;
	}

	public double getClassCPU() {
		return classCPU;
	}

	public void setClassCPU(double classCPU) {
		this.classCPU = classCPU;
	}

	// métodos maxwell
	public double getSyear() {
		return syear;
	}

	public void setSyear(double syear) {
		this.syear = syear;
	}

	public double getApp() {
		return app;
	}

	public void setApp(double app) {
		this.app = app;
	}

	public double getHar() {
		return har;
	}

	public void setHar(double har) {
		this.har = har;
	}

	public double getDba() {
		return dba;
	}

	public void setDba(double dba) {
		this.dba = dba;
	}

	public double getIfc() {
		return ifc;
	}

	public void setIfc(double ifc) {
		this.ifc = ifc;
	}

	public double getSrc() {
		return src;
	}

	public void setSrc(double src) {
		this.src = src;
	}

	public double getTelonuse() {
		return telonuse;
	}

	public void setTelonuse(double telonuse) {
		this.telonuse = telonuse;
	}

	public double getNlan() {
		return nlan;
	}

	public void setNlan(double nlan) {
		this.nlan = nlan;
	}

	public double getT01() {
		return t01;
	}

	public void setT01(double t01) {
		this.t01 = t01;
	}

	public double getT02() {
		return t02;
	}

	public void setT02(double t02) {
		this.t02 = t02;
	}

	public double getT03() {
		return t03;
	}

	public void setT03(double t03) {
		this.t03 = t03;
	}

	public double getT04() {
		return t04;
	}

	public void setT04(double t04) {
		this.t04 = t04;
	}

	public double getT05() {
		return t05;
	}

	public void setT05(double t05) {
		this.t05 = t05;
	}

	public double getT06() {
		return t06;
	}

	public void setT06(double t06) {
		this.t06 = t06;
	}

	public double getT07() {
		return t07;
	}

	public void setT07(double t07) {
		this.t07 = t07;
	}

	public double getT08() {
		return t08;
	}

	public void setT08(double t08) {
		this.t08 = t08;
	}

	public double getT09() {
		return t09;
	}

	public void setT09(double t09) {
		this.t09 = t09;
	}

	public double getT010() {
		return t010;
	}

	public void setT010(double t010) {
		this.t010 = t010;
	}

	public double getT011() {
		return t011;
	}

	public void setT011(double t011) {
		this.t011 = t011;
	}

	public double getT012() {
		return t012;
	}

	public void setT012(double t012) {
		this.t012 = t012;
	}

	public double getT013() {
		return t013;
	}

	public void setT013(double t013) {
		this.t013 = t013;
	}

	public double getT014() {
		return t014;
	}

	public void setT014(double t014) {
		this.t014 = t014;
	}

	public double getT015() {
		return t015;
	}

	public void setT015(double t015) {
		this.t015 = t015;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getTimeMaxwell() {
		return timeMaxwell;
	}

	public void setTimeMaxwell(double timeMaxwell) {
		this.timeMaxwell = timeMaxwell;
	}

	public double getEffortMaxwell() {
		return effortMaxwell;
	}

	public void setEffortMaxwell(double effortMaxwell) {
		this.effortMaxwell = effortMaxwell;
	}
	
	// métodos cocomosdr
	public String getPrec() {
		return prec;
	}

	public void setPrec(String prec) {
		this.prec = prec;
	}

	public String getFlex() {
		return flex;
	}

	public void setFlex(String flex) {
		this.flex = flex;
	}

	public String getResl() {
		return resl;
	}

	public void setResl(String resl) {
		this.resl = resl;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPmat() {
		return pmat;
	}

	public void setPmat(String pmat) {
		this.pmat = pmat;
	}

	public String getRuse() {
		return ruse;
	}

	public void setRuse(String ruse) {
		this.ruse = ruse;
	}

	public String getDocu() {
		return docu;
	}

	public void setDocu(String docu) {
		this.docu = docu;
	}

	public String getPvol() {
		return pvol;
	}

	public void setPvol(String pvol) {
		this.pvol = pvol;
	}

	public String getPcon() {
		return pcon;
	}

	public void setPcon(String pcon) {
		this.pcon = pcon;
	}

	public String getPexp() {
		return pexp;
	}

	public void setPexp(String pexp) {
		this.pexp = pexp;
	}

	public String getLtex() {
		return ltex;
	}

	public void setLtex(String ltex) {
		this.ltex = ltex;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	// métodos albrecht
	public double getInput() {
		return input;
	}

	public void setInput(double input) {
		this.input = input;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getInquiry() {
		return inquiry;
	}

	public void setInquiry(double inquiry) {
		this.inquiry = inquiry;
	}

	public double getFile() {
		return file;
	}

	public void setFile(double file) {
		this.file = file;
	}

	public double getFpAdjust() {
		return fpAdjust;
	}

	public void setFpAdjust(double fpAdjust) {
		this.fpAdjust = fpAdjust;
	}

	public double getRawFPcounts() {
		return rawFPcounts;
	}

	public void setRawFPcounts(double rawFPcounts) {
		this.rawFPcounts = rawFPcounts;
	}

	public double getAdjFP() {
		return adjFP;
	}

	public void setAdjFP(double adjFP) {
		this.adjFP = adjFP;
	}

	public double getEffortAlbrecht() {
		return effortAlbrecht;
	}

	public void setEffortAlbrecht(double effortAlbrecht) {
		this.effortAlbrecht = effortAlbrecht;
	}

	// métodos kemerer	
	public int getHardware() {
		return hardware;
	}

	public void setHardware(int hardware) {
		this.hardware = hardware;
	}

	public double getKsloc() {
		return ksloc;
	}

	public void setKsloc(double ksloc) {
		this.ksloc = ksloc;
	}

	public double getEffortMM() {
		return EffortMM;
	}

	public void setEffortMM(double effortMM) {
		EffortMM = effortMM;
	}
	
	// métodos kitchenham
	public String getClienteCode() {
		return clienteCode;
	}

	public void setClienteCode(String clienteCode) {
		this.clienteCode = clienteCode;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public int getActualDuration() {
		return actualDuration;
	}

	public void setActualDuration(int actualDuration) {
		this.actualDuration = actualDuration;
	}

	public double getAdjustedFunctionPoints() {
		return adjustedFunctionPoints;
	}

	public void setAdjustedFunctionPoints(double adjustedFunctionPoints) {
		this.adjustedFunctionPoints = adjustedFunctionPoints;
	}

	public String getFirstEstimateMethod() {
		return firstEstimateMethod;
	}

	public void setFirstEstimateMethod(String firstEstimateMethod) {
		this.firstEstimateMethod = firstEstimateMethod;
	}

	public double getActualEffort() {
		return actualEffort;
	}

	public void setActualEffort(double actualEffort) {
		this.actualEffort = actualEffort;
	}


	// métodos miyazaki94
	public double getMenorErro() {
		return menorErro;
	}

	public double getKloc() {
		return kloc;
	}

	public void setKloc(double kloc) {
		this.kloc = kloc;
	}

	public int getScrn() {
		return scrn;
	}

	public void setScrn(int scrn) {
		this.scrn = scrn;
	}

	public int getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}

	public int getEscrn() {
		return escrn;
	}

	public void setEscrn(int escrn) {
		this.escrn = escrn;
	}

	public int getEform() {
		return eform;
	}

	public void setEform(int eform) {
		this.eform = eform;
	}

	public int getEfile() {
		return efile;
	}

	public void setEfile(int efile) {
		this.efile = efile;
	}

	public double getMm() {
		return mm;
	}

	public void setMm(double mm) {
		this.mm = mm;
	}
	
	// métodos china
	

	public double getAfp() {
		return afp;
	}

	public void setAfp(double afp) {
		this.afp = afp;
	}

	public double getEnquiry() {
		return enquiry;
	}

	public void setEnquiry(double enquiry) {
		this.enquiry = enquiry;
	}

	public double getInterface1() {
		return interface1;
	}

	public void setInterface1(double interface1) {
		this.interface1 = interface1;
	}

	public double getAdded() {
		return added;
	}

	public void setAdded(double added) {
		this.added = added;
	}

	public double getChanged() {
		return changed;
	}

	public void setChanged(double changed) {
		this.changed = changed;
	}

	public double getDeleted() {
		return deleted;
	}

	public void setDeleted(double deleted) {
		this.deleted = deleted;
	}

	public double getPdrAfp() {
		return pdrAfp;
	}

	public void setPdrAfp(double pdrAfp) {
		this.pdrAfp = pdrAfp;
	}

	public double getPdrUfp() {
		return pdrUfp;
	}

	public void setPdrUfp(double pdrUfp) {
		this.pdrUfp = pdrUfp;
	}

	public double getNpdrAfp() {
		return nPdrAfp;
	}

	public void setNpdrAfp(double nPdrAfp) {
		this.nPdrAfp = nPdrAfp;
	}

	public double getNpduAfp() {
		return nPduAfp;
	}

	public void setNpduAfp(double nPduAfp) {
		this.nPduAfp = nPduAfp;
	}


	// métodos taxa de aprovação do fundamental e médio
	
	public double getnPdrAfp() {
		return nPdrAfp;
	}

	public void setnPdrAfp(double nPdrAfp) {
		this.nPdrAfp = nPdrAfp;
	}

	public double getnPduAfp() {
		return nPduAfp;
	}

	public void setnPduAfp(double nPduAfp) {
		this.nPduAfp = nPduAfp;
	}

	public double getAfd1() {
		return afd1;
	}

	public void setAfd1(double afd1) {
		this.afd1 = afd1;
	}

	public double getAfd2() {
		return afd2;
	}

	public void setAfd2(double afd2) {
		this.afd2 = afd2;
	}

	public double getAfd3() {
		return afd3;
	}

	public void setAfd3(double afd3) {
		this.afd3 = afd3;
	}

	public double getAfd4() {
		return afd4;
	}

	public void setAfd4(double afd4) {
		this.afd4 = afd4;
	}

	public double getAfd5() {
		return afd5;
	}

	public void setAfd5(double afd5) {
		this.afd5 = afd5;
	}

	public double getAtu() {
		return atu;
	}

	public void setAtu(double atu) {
		this.atu = atu;
	}

	public double getDsu() {
		return dsu;
	}

	public void setDsu(double dsu) {
		this.dsu = dsu;
	}

	public double getIcg() {
		return icg;
	}

	public void setIcg(double icg) {
		this.icg = icg;
	}

	public double getIed1() {
		return ied1;
	}

	public void setIed1(double ied1) {
		this.ied1 = ied1;
	}

	public double getIed2() {
		return ied2;
	}

	public void setIed2(double ied2) {
		this.ied2 = ied2;
	}

	public double getIed3() {
		return ied3;
	}

	public void setIed3(double ied3) {
		this.ied3 = ied3;
	}

	public double getIed4() {
		return ied4;
	}

	public void setIed4(double ied4) {
		this.ied4 = ied4;
	}

	public double getIed5() {
		return ied5;
	}

	public void setIed5(double ied5) {
		this.ied5 = ied5;
	}

	public double getIed6() {
		return ied6;
	}

	public void setIed6(double ied6) {
		this.ied6 = ied6;
	}

	public double getIrd() {
		return ird;
	}

	public void setIrd(double ird) {
		this.ird = ird;
	}

	public double getTdi() {
		return tdi;
	}

	public void setTdi(double tdi) {
		this.tdi = tdi;
	}

	public double getTa() {
		return ta;
	}

	public void setTa(double ta) {
		this.ta = ta;
	}
	
	
	
	
	public double getTe() {
		return te;
	}

	public void setTe(double te) {
		this.te = te;
	}

	public double getTr() {
		return tr;
	}

	public void setTr(double tr) {
		this.tr = tr;
	}

	// métodos gerais
	public void setMenorErro(double menorErro) {
		this.menorErro = menorErro;
	}

	public String getMelhorAlgoritmo() {
		return melhorAlgoritmo;
	}

	public void setMelhorAlgoritmo(String melhorAlgoritmo) {
		this.melhorAlgoritmo = melhorAlgoritmo;
	}

	public double getProdutividade() {
		return produtividade;
	}

	public void setProdutividade(double produtividade) {
		this.produtividade = produtividade;
	}

	
	
	
	
	
	
	
	
	
}
