package sintatico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Sintatico {
	public static void main(String[] args) {
	    int START_SYMBOL = 46;

	    int FIRST_NON_TERMINAL    = 46;
	    int FIRST_SEMANTIC_ACTION = 77;

	    int[][] PARSER_TABLE =
	    {
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1,  4, -1, -1,  3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  5,  8,  8,  8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  7, -1, -1, -1, -1,  6,  6,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  9, 12, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, 10, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, 17, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, 22, 23, -1, 24, 29, -1, 23, 32, -1, 33, 23, 34, 38, -1, -1, -1, 49, -1, 43, -1 },
	        { -1, 26, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, 25, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, 27, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1, 31, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, 36, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, 40, -1, -1, 40, 40, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1 },
	        { -1, -1, 41, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, 50, -1, -1, 50, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, -1, -1, -1, -1 },
	        { -1, -1, 51, 51, 51, -1, -1, -1, 52, 53, 54, 55, 56, 57, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 51, -1, -1, -1, 51, 51, -1, 51, -1, 51, -1, -1, -1, -1, -1, -1, 51, -1, 51 },
	        { -1, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 58, 59, -1, -1, 60, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 60, -1, -1, -1, -1 },
	        { -1, -1, 64, 64, 64, -1, -1, -1, 64, 64, 64, 64, 64, 64, 61, 62, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, -1, 64, 64, -1, 64, -1, 64, -1, -1, 63, -1, -1, -1, 64, -1, 64 },
	        { -1, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, -1, -1, -1, -1 },
	        { -1, -1, 66, 66, 66, -1, -1, -1, 66, 66, 66, 66, 66, 66, 66, 66, 67, 68, -1, -1, -1, -1, -1, -1, -1, -1, 66, -1, -1, -1, 66, 66, -1, 66, -1, 66, -1, -1, 66, 69, -1, -1, 66, -1, 66 },
	        { -1, 71, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 73, 70, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 72, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, -1, 48, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	        { -1, -1, -1, 45, -1, -1, 46, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
	    };

	    int[][] PRODUCTIONS = 
	    {
	        { 22, 19,  5, 47,  6 },
	        { 50, 52, 55, 57 },
	        { 19, 49 },
	        {  0 },
	        {  4, 19, 49 },
	        { 23, 19,  9, 20,  5, 51 },
	        {  0 },
	        { 19,  9, 20,  5, 51 },
	        {  0 },
	        { 24, 48,  7, 54,  5, 53 },
	        {  0 },
	        { 48,  7, 54,  5, 53 },
	        {  0 },
	        { 28 },
	        { 25, 19, 56,  5, 47,  5, 55 },
	        {  0 },
	        {  0 },
	        {  2, 48,  7, 28,  3 },
	        { 26, 59, 58, 27 },
	        {  0 },
	        {  5, 59, 58 },
	        { 19,  8, 67 },
	        { 57 },
	        {  0 },
	        { 29, 19, 60 },
	        {  0 },
	        {  2, 67, 61,  3 },
	        {  0 },
	        {  4, 67, 61 },
	        { 30, 67, 31, 59, 62 },
	        {  0 },
	        { 32, 59 },
	        { 33, 67, 34, 59 },
	        { 35, 59, 36, 67 },
	        { 37,  2, 63, 64,  3 },
	        { 19 },
	        {  0 },
	        {  4, 63, 64 },
	        { 38,  2, 65, 66,  3 },
	        { 21 },
	        { 67 },
	        {  0 },
	        {  4, 65, 66 },
	        { 44, 67, 45, 74, 27 },
	        { 20, 76,  7, 59, 75 },
	        {  4, 20, 76 },
	        {  0 },
	        {  0 },
	        {  5, 74 },
	        { 42, 19,  8, 67, 43, 67, 34, 59 },
	        { 69, 68 },
	        {  0 },
	        {  9, 69 },
	        { 10, 69 },
	        { 11, 69 },
	        { 12, 69 },
	        { 13, 69 },
	        { 14, 69 },
	        { 15, 71, 70 },
	        { 16, 71, 70 },
	        { 71, 70 },
	        { 15, 71, 70 },
	        { 16, 71, 70 },
	        { 39, 71, 70 },
	        {  0 },
	        { 73, 72 },
	        {  0 },
	        { 17, 73, 72 },
	        { 18, 73, 72 },
	        { 40, 73, 72 },
	        { 20 },
	        {  2, 67,  3 },
	        { 41, 73 },
	        { 63 }
	    };

	    String[] PARSER_ERROR =
	    {
	        "",
	        "Era esperado fim de programa",
	        "Era esperado \"(\"",
	        "Era esperado \")\"",
	        "Era esperado \",\"",
	        "Era esperado \";\"",
	        "Era esperado \".\"",
	        "Era esperado \":\"",
	        "Era esperado \":=\"",
	        "Era esperado \"=\"",
	        "Era esperado \"<\"",
	        "Era esperado \">\"",
	        "Era esperado \">=\"",
	        "Era esperado \"<=\"",
	        "Era esperado \"<>\"",
	        "Era esperado \"+\"",
	        "Era esperado \"-\"",
	        "Era esperado \"*\"",
	        "Era esperado \"/\"",
	        "Era esperado id",
	        "Era esperado num",
	        "Era esperado literal",
	        "Era esperado program",
	        "Era esperado const",
	        "Era esperado var",
	        "Era esperado procedure",
	        "Era esperado begin",
	        "Era esperado end",
	        "Era esperado integer",
	        "Era esperado call",
	        "Era esperado if",
	        "Era esperado then",
	        "Era esperado else",
	        "Era esperado while",
	        "Era esperado do",
	        "Era esperado repeat",
	        "Era esperado until",
	        "Era esperado readln",
	        "Era esperado writeln",
	        "Era esperado or",
	        "Era esperado and",
	        "Era esperado not",
	        "Era esperado for",
	        "Era esperado to",
	        "Era esperado case",
	        "Era esperado of",
	        "<PROGRAMA> inválido",
	        "<BLOCO> inválido",
	        "<LID> inválido",
	        "<REPIDENT> inválido",
	        "<DCLCONST> inválido",
	        "<LDCONST> inválido",
	        "<DCLVAR> inválido",
	        "<LDVAR> inválido",
	        "<TIPO> inválido",
	        "<DCLPROC> inválido",
	        "<DEFPAR> inválido",
	        "<CORPO> inválido",
	        "<REPCOMANDO> inválido",
	        "<COMANDO> inválido",
	        "<PARAMETROS> inválido",
	        "<REPPAR> inválido",
	        "<ELSEPARTE> inválido",
	        "<VARIAVEL> inválido",
	        "<REPVARIAVEL> inválido",
	        "<ITEMSAIDA> inválido",
	        "<REPITEM> inválido",
	        "<EXPRESSAO> inválido",
	        "<REPEXPSIMP> inválido",
	        "<EXPSIMP> inválido",
	        "<REPEXP> inválido",
	        "<TERMO> inválido",
	        "<REPTERMO> inválido",
	        "<FATOR> inválido",
	        "<CONDCASE> inválido",
	        "<CONTCASE> inválido",
	        "<RPINTEIRO> inválido"
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
