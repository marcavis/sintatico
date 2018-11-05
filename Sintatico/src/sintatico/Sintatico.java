package sintatico;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import hipotetica.Tipos;

public class Sintatico {
	static int EPSILON = 0;
	static int DOLLAR = 1;
    static int START_SYMBOL = 46;
    
    static int FIRST_NON_TERMINAL    = 46;
    static int FIRST_SEMANTIC_ACTION = 77;

    static int[][] PARSER_TABLE =
    {			//   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40  41  42  43  44  45
	/*program	*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*bloco		*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*lid		*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*repident	*/{ -1, -1, -1,  4, -1, -1,  3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*dclconst	*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  5,  8,  8,  8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*ldconst	*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  7, -1, -1, -1, -1,  6,  6,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*dclvar	*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  9, 12, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*ldvar		*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, 10, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*tipo		*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*dclproc	*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*defpar	*/{ -1, 17, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*corpo		*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*repcomando*/{ -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*comando	*/{ -1, -1, -1, -1, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, 22, 23, -1, 24, 29, -1, 23, 32, -1, 33, 23, 34, 38, -1, -1, -1, 49, -1, 43, -1 },
	/*parametros*/{ -1, 26, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, 25, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*reppar	*/{ -1, -1, 27, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*elseparte	*/{ -1, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1, 31, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*variavel	*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*repvariavl*/{ -1, -1, 36, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*itemsaida	*/{ -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, 40, -1, -1, 40, 40, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1 },
	/*repitem	*/{ -1, -1, 41, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*expressao	*/{ -1, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, 50, -1, -1, 50, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, -1, -1, -1, -1 },
	/*repexpsimp*/{ -1, -1, 51, 51, 51, -1, -1, -1, 52, 53, 54, 55, 56, 57, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 51, -1, -1, -1, 51, 51, -1, 51, -1, 51, -1, -1, -1, -1, -1, -1, 51, -1, 51 },
	/*expsimp	*/{ -1, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 58, 59, -1, -1, 60, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 60, -1, -1, -1, -1 },
	/*repexp	*/{ -1, -1, 64, 64, 64, -1, -1, -1, 64, 64, 64, 64, 64, 64, 61, 62, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, -1, 64, 64, -1, 64, -1, 64, -1, -1, 63, -1, -1, -1, 64, -1, 64 },
	/*termo		*/{ -1, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, -1, -1, -1, -1 },
	/*reptermo	*/{ -1, -1, 66, 66, 66, -1, -1, -1, 66, 66, 66, 66, 66, 66, 66, 66, 67, 68, -1, -1, -1, -1, -1, -1, -1, -1, 66, -1, -1, -1, 66, 66, -1, 66, -1, 66, -1, -1, 66, 69, -1, -1, 66, -1, 66 },
	/*fator		*/{ -1, 71, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 73, 70, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 72, -1, -1, -1, -1 },
	/*condcase	*/{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*contcase	*/{ -1, -1, -1, -1, 48, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	/*rpinteiro	*/{ -1, -1, -1, 45, -1, -1, 46, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
    };

    static int[][] PRODUCTIONS = 
    {
        {  22,  19, 100,   5,  47,   6, 101 },
        {  50,  52, 102,  55,  57 },
        {  19, 104,  49 },
        {   0 },
        {   4,  19, 104,  49 },
        {  23,  19, 105,   9,  20, 106,   5,  51 },
        {   0 },
        {  19, 105,   9,  20, 106,   5,  51 },
        {   0 },
        {  24, 107,  48,   7,  54,   5,  53 },
        {   0 },
        {  48,   7,  54,   5,  53 },
        {   0 },
        {  28 },
        {  25,  19, 108,  56,   5, 109,  47,   5, 110,  55 },
        {   0 },
        {   0 },
        {   2, 111,  48,   7,  28,   3 },
        {  26,  59,  58,  27 },
        {   0 },
        {   5,  59,  58 },
        {  19, 114,   8,  67, 115 },
        {  57 },
        {   0 },
        {  29,  19, 116,  60, 117 },
        {   0 },
        {   2,  67, 118,  61,   3 },
        {   0 },
        {   4,  67, 118,  61 },
        {  30,  67, 120,  31,  59,  62, 121 },
        {   0 },
        { 122,  32,  59 },
        {  33, 123,  67, 124,  34,  59, 125 },
        {  35, 126,  59,  36,  67, 127 },
        {  37, 128,   2,  63,  64,   3 },
        {  19, 129 },
        {   0 },
        {   4,  63,  64 },
        {  38,   2,  65,  66,   3 },
        {  21, 130 },
        {  67, 131 },
        {   0 },
        {   4,  65,  66 },
        {  44, 132,  67,  45,  74,  27, 133 },
        {  20,  76,   7, 134,  59, 135,  75 },
        {   4, 136,  20,  76 },
        {   0 },
        {   0 },
        {   5,  74 },
        {  42,  19, 137,   8,  67, 138,  43,  67, 139,  34,  59, 140 },
        {  69,  68 },
        {   0 },
        {   9,  69, 141 },
        {  10,  69, 142 },
        {  11,  69, 143 },
        {  12,  69, 144 },
        {  13,  69, 145 },
        {  14,  69, 146 },
        {  15,  71,  70 },
        {  16,  71, 147,  70 },
        {  71,  70 },
        {  15,  71, 148,  70 },
        {  16,  71, 149,  70 },
        {  39,  71, 150,  70 },
        {   0 },
        {  73,  72 },
        {   0 },
        {  17,  73, 151,  72 },
        {  18,  73, 152,  72 },
        {  40,  73, 153,  72 },
        {  20, 154 },
        {   2,  67,   3 },
        {  41,  73, 155 },
        { 156,  63 }
    };

    static String[] LEGENDA = {
    		"vazio", "fim do arquivo", "\"(\"", "\")\"", "\",\"", "\";\"", "\".\"", "\":\"", "\":=\"",
    		"\"=\"", "\"<\"", "\">\"",
    		"\">=\"", "\"<=\"", "\"<>\"", "\"+\"", "\"-\"", "\"*\"", "\"/\"", "identificador", "número", "string",
    		"program", "const", "var", "procedure", "begin", "end", "integer", "call",
    		"if", "then", "else", "while", "do", "repeat", "until", "readln", "writeln",
    		"or", "and", "not", "for", "to", "case", "of",
    		"<PROGRAMA>",
            "<BLOCO>",
            "<LID>",
            "<REPIDENT>",
            "<DCLCONST>",
            "<LDCONST>",
            "<DCLVAR>",
            "<LDVAR>",
            "<TIPO>",
            "<DCLPROC>",
            "<DEFPAR>",
            "<CORPO>",
            "<REPCOMANDO>",
            "<COMANDO>",
            "<PARAMETROS>",
            "<REPPAR>",
            "<ELSEPARTE>",
            "<VARIAVEL>",
            "<REPVARIAVEL>",
            "<ITEMSAIDA>",
            "<REPITEM>",
            "<EXPRESSAO>",
            "<REPEXPSIMP>",
            "<EXPSIMP>",
            "<REPEXP>",
            "<TERMO>",
            "<REPTERMO>",
            "<FATOR>",
            "<CONDCASE>",
            "<CONTCASE>",
            "<RPINTEIRO>",
            "","","","","","","","","","","","","","","","","","","","","","","",
            "#100","#101","#102","#103","#104","#105","#106","#107","#108","#109",
            "#110","#111","#112","#113","#114","#115","#116","#117","#118","#119",
            "#120","#121","#122","#123","#124","#125","#126","#127","#128","#129",
            "#130","#131","#132","#133","#134","#135","#136","#137","#138","#139",
            "#140","#141","#142","#143","#144","#145","#146","#147","#148","#149",
            "#150","#151","#152","#153","#154","#155","#156"
    };
    
    //Executa apenas a análise léxica, mas adicionalmente retorna uma tabela dos tokens identificados
    public static ArrayList<String[]> soLexico(Shell shell, String codigoFonte) throws Exception {
    	ArrayList<String[]> tabela = new ArrayList<String[]>();
    	char[] fonte = new char[codigoFonte.length()];
		
		for (int i = 0; i < codigoFonte.length(); i++) {
			fonte[i] = codigoFonte.charAt(i);
		}
		Lexico lex = new Lexico(fonte);
		
		do {
			int token = -1;
			try {
				token = lex.proxToken();
				tabela.add(new String[] {""+token, LEGENDA[token], lex.getUltimoLexema()});
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new SintaticoException("Erro: fim de arquivo encontrado quando se esperava algum token", lex.getLinha());
			} catch (InvalidTokenException e) {
				//repassar a mensagem de erro do léxico, de quando um token não segue as regras da LMS
				throw new SintaticoException(e.getMessage(), lex.getLinha());
			} catch (Exception e) {
				throw new SintaticoException(e.getMessage(), lex.getLinha());
			}
			
			//debug: mostra o que há na frente da posição atual no código fonte
			//System.out.println("Codigo fonte: Linha " + lex.getLinha() + ": " + LEGENDA[token] +";");
//			String resto = "";
//			for (int k = lex.getPosicao(); k < codigoFonte.length(); k++) {
//				resto += codigoFonte.charAt(k);
//			}
//			System.out.println(resto);
			
		} while (!lex.semTokens());
		System.out.println("Arquivo processado com sucesso.");
		return tabela;
    }
	
	public static void analisar(Shell shell, String codigoFonte, boolean semantico) throws Exception {
		String penultimoLexema = ""; //acoes semanticas podem precisar voltar atrás no código fonte
		//para acessar identificadores depois de o léxico já ter avançado
		char[] fonte = new char[codigoFonte.length()];
		
		for (int i = 0; i < codigoFonte.length(); i++) {
			fonte[i] = codigoFonte.charAt(i);
		}
		Lexico lex = new Lexico(fonte);
		
		PilhaDinamica<Integer> simbolos = new PilhaDinamica<Integer>();
		simbolos.inserir(DOLLAR);
		simbolos.inserir(START_SYMBOL);
		//repetir o algoritmo descendente preditivo até que acabe a leitura do código fonte
		do {
			//DEBUG: mostra a situação atual da pilha
			System.out.println("\n" + simbolos);
			int atual = simbolos.retirar();
			int token = -1;
			try {
				token = lex.proxToken();
			} catch (ArrayIndexOutOfBoundsException e) {
				while (atual >= FIRST_SEMANTIC_ACTION) {
					//para evitar que o analisador reclame de estar procurando uma ação
					//semântica no código, o que não é algo que o usuário final poderá encontrar
					atual = simbolos.retirar();
				}
				throw new SintaticoException("Erro: fim de arquivo encontrado quando se esperava " + LEGENDA[atual], lex.getLinha());
			} catch (InvalidTokenException e) {
				//repassar a mensagem de erro do léxico, de quando um token não segue as regras da LMS
				throw new SintaticoException(e.getMessage(), lex.getLinha());
			} catch (Exception e) {
				throw new SintaticoException(e.getMessage(), lex.getLinha());
			}
			
			//debug: mostra o que há na frente da posição atual no código fonte
			System.out.println("ULTIMO " + lex.getUltimoLexema());
			System.out.println("Codigo fonte: Linha " + lex.getLinha() + ": " + LEGENDA[token] +"; topo da pilha: " + LEGENDA[atual]);
			String resto = "";
			for (int k = lex.getPosicao(); k < codigoFonte.length(); k++) {
				resto += codigoFonte.charAt(k);
			}
			System.out.println(resto);
			//se símbolo atual da pilha é terminal:
			if(atual < FIRST_NON_TERMINAL) {
				if (atual == token) {
					//não é necessário fazer mais nada quando o símbolo terminal correto é encontrado,
					//pois o processamento do algoritmo já irá ler o próximo token e desempilhar
					//o primeiro item da pilha automaticamente.
				} else { 
					//cancelar o avanço na leitura do código fonte se tivermos um epsilon na pilha
					if(atual == 0) {
						lex.retorna();
					} else {
						//o símbolo atual na pilha não é esperado NEM o epsilon, isso é um erro
						String aviso = "Erro, esperava o " + LEGENDA[atual] + ", encontrou " + LEGENDA[token];
						System.out.println(aviso);
						throw new SintaticoException(aviso, lex.getLinha());
					}
				}
			} else if (atual < FIRST_SEMANTIC_ACTION) { //não é terminal
				//como estaremos expandindo a produção atual na pilha em vez de retirar o token atual,
				//devemos retroceder ao ponto anterior na leitura do código fonte.
				lex.retorna();
				//o acesso às produções na PARSER TABLE precisa tomar em consideração que
				//não parte do símbolo 0 (EPSILON) mas sim do 1ª símbolo não terminal;
				//também não possui a coluna do EPSILON
				int idProducao = PARSER_TABLE[atual - START_SYMBOL][token - 1];
				if (idProducao >= 0){
					int [] producao = PRODUCTIONS[idProducao];
					//empilhar os símbolos da produção encontrada, em ordem inversa
					//(ou, digamos, ordem vertical)
					for (int j = producao.length - 1; j >= 0; j--) {
						simbolos.inserir(producao[j]);
					}
				} else {
					String aviso = "Erro, esperava o " + LEGENDA[atual] + ", encontrou " + LEGENDA[token];
					System.out.println(aviso);
					String sugestoes = "";
					for (int i = 0; i < FIRST_NON_TERMINAL - 1; i++) {
						if(PARSER_TABLE[atual - START_SYMBOL][i] >= 0) {
							sugestoes += LEGENDA[i+1] + ", ";
						}
					}
					aviso += "\nSugestões: " + sugestoes + "em vez de " + lex.getUltimoLexema();
					throw new SintaticoException(aviso, lex.getLinha());
				}
			} else {
				if(!semantico) {
					lex.retorna();
					//é ação semântica e estamos no modo com ação semântica desabilitada 
					System.out.println("Ação semântica, por enquanto ignorar");
					//evitar que o token atual do código seja consumido neste passo
				} else {
					lex.retorna();
					System.out.println("Tratando a ação semântica " + atual);
					Semantico.trataAcao(atual, lex.getLinha(), penultimoLexema);
				}
			}
			System.out.println(lex.semTokens());
			penultimoLexema = lex.getUltimoLexema();
		} while (!lex.semTokens());
		System.out.println("Arquivo processado com sucesso.");
		//debug, mostra que apenas o $ localiza-se na pilha
		System.out.println(simbolos);
		if(semantico) {
			for (Tipos t : Semantico.areaInstrucoes.AI) {
				if (t.codigo == -1) {break;}
				System.out.println(Semantico.legenda[t.codigo] + ", " + t.op1 + ", " + t.op2);
			}
		}
	}
	
}
