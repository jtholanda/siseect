package main;

import util.Constantes;
import data.Padrao;
import data.PadraoChina;
import data.PadraoCocomo81;
import data.PadraoCocomoNasaV1;
import data.PadraoCocomoNasaV2;
import data.PadraoDesharnais;
import data.PadraoISBSG5;
import data.PadraoKitchenham;
import data.PadraoMaxwell;
import data.PadraoMiyazaki94;

public class TesteExperimentos {
	
	private static Padrao padrao = null;

	
	public static void main(String []args){
		
		
		System.out.println("Avaliação de validação:"+ Constantes.BASE_VALIDACAO);

		
		System.out.println("Avaliação de várias bases de dados");

		padrao = new PadraoISBSG5();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);

		padrao = new PadraoChina();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);


		padrao = new PadraoCocomo81();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);

		padrao = new PadraoCocomoNasaV1();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);

		padrao = new PadraoCocomoNasaV2();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);
		
		padrao = new PadraoDesharnais();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);
		
		padrao = new PadraoKitchenham();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);

		padrao = new PadraoMaxwell();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);


		padrao = new PadraoMiyazaki94();
		System.out.println(padrao.toString());
		TesteExperimento.padrao = padrao;
		TesteExperimento.main(args);

		
	}

	
}
