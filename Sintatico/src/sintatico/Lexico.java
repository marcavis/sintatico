package sintatico;

import java.lang.Character;

public class Lexico {
	private char[] fonte;
	private int linha;
	private int posicao;
	private int estado;
	private String esteToken;
	
	//estados possíveis:
	final int ENEUTRO = 0;
	final int EID = 1;
	final int ENUMERO = 2;
	final int ELITERAL = 8;


	//tabela de tokens
	int EPSILON = 0;
	int DOLLAR = 1;
	int PLUS = 2;
	int MINUS = 3;
	int ASTERISK = 4;
	int SLASH = 5;
	int EQUALS = 6;
	int GT = 7;
	int GE = 8;
	int LT = 9;
	int LE = 10;
	int DIFFERENT = 11;
	int ATTRIBUTION = 12;
	int COLON = 13;
	int SEMICOLON = 14;
	int COMMA = 15;
	int PERIOD = 16;
	int LPAREN = 17;
	int RPAREN = 18;
	int ID = 19;
	int INTEIRO = 20;
	int LIT = 21;
	int PROGRAM = 22;
	int CONST = 23;
	int VAR = 24;
	int PROCEDURE = 25;
	int BEGIN = 26;
	int END = 27;
	int INTEGER = 28;
	int OF = 29;
	int CALL = 30;
	int IF = 31;
	int THEN = 32;
	int ELSE = 33;
	int WHILE = 34;
	int DO = 35;
	int REPEAT = 36;
	int UNTIL = 37;
	int READLN = 38;
	int WRITELN = 39;
	int OR = 40;
	int AND = 41;
	int NOT = 42;
	int FOR = 43;
	int TO = 44;
	int CASE = 45;
	final private int PRIMEIRA_RESERVADA = 22; //PROGRAM
	final private String[] reservadas = new String[] {
			"PROGRAM", "CONST", "VAR", "PROCEDURE", "BEGIN", "END",
			"INTEGER", "OF", "CALL", "IF", "THEN", "ELSE", "WHILE", "DO",
			"REPEAT", "UNTIL", "READLN", "WRITELN", "OR", "AND", "NOT",
			"FOR", "TO", "CASE"
	};
	
	public Lexico(char[] fonte) {
		this.fonte = fonte;
		this.posicao = 0;
		this.linha = 0; //TODO: implementar mudança de linha nos \n
		this.esteToken = "";
	}
	
	public int proxToken() throws Exception {
		esteToken = "";
		{ //um do-while aqui?
		char atual = fonte[posicao];
		
		
		//provavelmente colocar as estruturas abaixo dentro de um while(true)
		// que retornam um código quando completam um token com sucesso (ou não)
		switch (estado) {
		case ENEUTRO:
			if(Character.isWhitespace(atual)) {
				linha++;
			} else if (Character.isLetter(atual)) {
				estado = EID;
				esteToken += atual;
			} else if (Character.isDigit(atual)) {
				estado = ENUMERO;
				esteToken += atual;
			}
			break;
		case EID:
			if(Character.isLetter(atual) || Character.isDigit(atual)) {
				esteToken += atual;
				//estado = ID;
			} else {
				//acabou o identificador, voltar ao estado neutro sem avançar na leitura de
				//caracteres do código-fonte; como a posição será avançada
				//depois do fim do switch, fazemos ela retroceder neste ponto.
				estado = ENEUTRO;
				posicao--;
				return tratarIdentificador(esteToken);
			}
			break;
		case ENUMERO:
			if(Character.isDigit(atual)) {
				esteToken += atual;
			} else if(Character.isLetter(atual)) {
				throw new Exception("Linha " + linha + ": Caractere inesperado '" 
						+ atual + "' quando da leitura de um número");
			} else {
				estado = ENEUTRO;
				posicao--;
				return tratarNumero(esteToken);
			}
		default:
			break;
		}
		posicao++;
		}
		return 0;
	}
	
//	public boolean ehLetra(char c) {
		
//	}
	
	//retorna se o número x está incluído num conjunto de números 
	public boolean contido(int x, int[] conjunto) {
		boolean resposta = false;
		for (int i : conjunto)
			if (i == x) 
				return true;
		return resposta;
	}
	
	public int tratarIdentificador(String token) throws Exception {
		if(token.length() > 30) {
			throw new Exception("Linha " + linha + ": Identificador inválido '" 
					+ token + "' tem mais de 30 caracteres");
		}
		for(int i = 0; i < reservadas.length; i++) {
			//presumindo que a linguagem é case-insensitive
			if(token.toUpperCase().equals(reservadas[i])) {
				return PRIMEIRA_RESERVADA + i;
			}
		}
		return 19; //código de token do tipo ID
	}
}
