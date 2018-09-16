package sintatico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Sintatico {
	public static void main(String[] args) {
		int EPSILON  = 0;
	    int DOLLAR   = 1;

	    int t_TOKEN_2 = 2; //"("
	    int t_TOKEN_3 = 3; //")"
	    int t_TOKEN_4 = 4; //";"
	    int t_TOKEN_5 = 5; //"."
	    int t_id = 6;
	    int t_num = 7;
	    int t_prog = 8;
	    int t_begin = 9;
	    int t_end = 10;
	    int t_read = 11;
	    int t_write = 12;
	    
	    int START_SYMBOL = 13;

	    int FIRST_NON_TERMINAL    = 13;
	    int FIRST_SEMANTIC_ACTION = 17;

	    //
	    
	    int[][] PARSER_TABLE =
	    {
	    	// $  (   )   ;   .   id num prog bgn end rd  wrt	
	        { -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1 }, //inicio
	        { -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1 }, //bloco
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2,  3 }, //cmd
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1,  5,  4,  4 }  //repcmd
	    };

	    int[][] PRODUCTIONS = 
	    {
	        {  8, 14,  5 },				// prog <bloco> .
	        {  9, 15, 10 },				// begin <cmd> end
	        { 11,  2,  6,  3,  4, 16 },	// read ( id ) ; <repcmd>
	        { 12,  2,  6,  3,  4, 16 },	// write ( id ) ; <repcmd>
	        { 15, 11 },					// cmd read (estranho)
	        {  0 }						// epsilon
	    };

	    String[] PARSER_ERROR =
	    {
	        "",
	        "Era esperado fim de programa",
	        "Era esperado \"(\"",
	        "Era esperado \")\"",
	        "Era esperado \";\"",
	        "Era esperado \".\"",
	        "Era esperado id",
	        "Era esperado num",
	        "Era esperado prog",
	        "Era esperado begin",
	        "Era esperado end",
	        "Era esperado read",
	        "Era esperado write",
	        "<INICIO> inválido",
	        "<BLOCO> inválido",
	        "<CMD> inválido",
	        "<REPCMD> inválido"
	    };
		
	    
		//acima =  exemplo do arquivo gals no EVA
		
	    //exercicio1G1();
		String codigoFonte = importarCodigoFonte();
		char[] fonte = new char[codigoFonte.length()];
		
		for (int i = 0; i < codigoFonte.length(); i++) {
			fonte[i] = codigoFonte.charAt(i);
		}
		Lexico lex = new Lexico(fonte);
		
		PilhaDinamica<Integer> simbolos = new PilhaDinamica<Integer>();
		simbolos.inserir(START_SYMBOL);
		{
			int atual = simbolos.retirar();
			//se símbolo atual da pilha é terminal:
			if(atual < FIRST_NON_TERMINAL) {
				try {
					if(atual == lex.proxToken()) {
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private static String importarCodigoFonte() {
		// TODO Auto-generated method stub
		return "prog begin read ( foo ) ; end.";
	}

	public static void exercicio1G1() {
		Gramatica gG = new Gramatica("A");
		HashMap<String, Boolean> tiposDeSimbolo = gG.getTiposDeSimbolo();
		tiposDeSimbolo.put("A", false); //A é um token não-terminal
		tiposDeSimbolo.put("q",true);
		tiposDeSimbolo.put("y",true);
		tiposDeSimbolo.put("k",true);
		tiposDeSimbolo.put("x",true);
		tiposDeSimbolo.put("m",true);
		tiposDeSimbolo.put("r",true);
		tiposDeSimbolo.put("W",false);
		tiposDeSimbolo.put("1",true);
		
		//gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("A", "y"))));
		gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("k"))));
		//gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("A", "x"))));
		gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("m", "W"))));
		
		//esses dois nao existem no exemplo
		//gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("W","y"))));
		//gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("A","y"))));
		
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("r"))));
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("k"))));
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("1"))));
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("y"))));
		//ArrayList<String> places = new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
		HashMap<String, ArrayList<String>> first = gG.first();
		
		System.out.println("first:");
		System.out.println(first);
		
		System.out.println(gG.primeiraRegra(true));
		
		gG.listaRegras();
		
	}
	
	

}
