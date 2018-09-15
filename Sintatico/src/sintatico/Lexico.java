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
	final int ECOLON = 5;
	final int EMENOR = 6;
	final int EMAIOR = 7;
	final int ELITERAL = 8;
	final int ELPAREN = 9;
	final int ECOMENT = 15;
	final int EFIMCOMENT = 16;


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
		do { //um do-while aqui?
			char atual = fonte[posicao];
			
			//provavelmente colocar as estruturas abaixo dentro de um while(true)
			// que retornam um código quando completam um token com sucesso (ou não)
			switch (estado) {
			case ENEUTRO:
				if(Character.isWhitespace(atual)) {
					//basicamente ignorar espaços, exceto \n, que incrementa o contador de linhas
					if(atual == '\n') {
						linha++;
					}
					
				} else if (Character.isLetter(atual)) {
					estado = EID;
					esteToken += atual;
				} else if (Character.isDigit(atual)) {
					estado = ENUMERO;
					esteToken += atual;
				} else if (atual == '"') {
					estado = ELITERAL;
					esteToken += atual;
				} else if (atual == '(') {
					estado = ELPAREN;
				} else if (atual == ')') {
					estado = ENEUTRO;
					posicao++;
					return RPAREN;
				} else if (atual == '(') {
					estado = ELPAREN;
				} else if (atual == '+') {
					estado = ENEUTRO;
					posicao++;
					return PLUS;
				} else if (atual == '-') {
					estado = ENEUTRO;
					posicao++;
					return MINUS;
				} else if (atual == '*') {
					estado = ENEUTRO;
					posicao++;
					return ASTERISK;
				} else if (atual == '/') {
					estado = ENEUTRO;
					posicao++;
					return SLASH;
				} else if (atual == '=') {
					estado = ENEUTRO;
					posicao++;
					return EQUALS;
				} else if (atual == '>') {
					estado = EMAIOR;
				} else if (atual == '<') {
					estado = EMENOR;
				} else if (atual == ':') {
					estado = ECOLON;
				} else if (atual == ';') {
					estado = ENEUTRO;
					posicao++;
					return SEMICOLON;
				} else if (atual == ',') {
					estado = ENEUTRO;
					posicao++;
					return COMMA;
				} else if (atual == '.') {
					estado = ENEUTRO;
					posicao++;
					return PERIOD;
				} 
				break;
			case EID:
				if(Character.isLetter(atual) || Character.isDigit(atual)) {
					esteToken += atual;
					//estado = ID;
				} else {
					//acabou o identificador, voltar ao estado neutro sem avançar na leitura de
					//caracteres do código-fonte.
					estado = ENEUTRO;
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
					return tratarNumero(esteToken);
				}
				break;
			case ECOLON:
				if(atual == '=') {
					estado = ENEUTRO;
					posicao++;
					return ATTRIBUTION;
				} else {
					estado = ENEUTRO;
					return COLON;
				}
			case EMENOR:
				if(atual == '=') {
					estado = ENEUTRO;
					posicao++;
					return LE;
				} else if(atual == '>') {
					estado = ENEUTRO;
					posicao++;
					return DIFFERENT;
				} else {
					//foi encontrado apenas '<', não seguir adiante e devolver o 
					//estado para o padrão
					estado = ENEUTRO;
					return LT;
				}
				//break;
			case EMAIOR:
				if(atual == '=') {
					estado = ENEUTRO;
					posicao++;
					return GE;
				} else {
					//foi encontrado apenas '>', não seguir adiante e devolver o 
					//estado para o padrão
					estado = ENEUTRO;
					return GT;
				}
				//break;
			case ELITERAL:
				if(atual == '"') {
					estado = ENEUTRO;
					posicao++;
					return tratarLiteral(esteToken);
				} else {
					esteToken += atual;
				}
				break;
			case ELPAREN:
				if(atual == '*') {
					estado = ECOMENT;
				} else {
					estado = ENEUTRO;
					return LPAREN;
				}
				break;
				//os estados ECOMENT e EFIMCOMENT praticamente não fazem nada, apenas
				//fazem o processamento necessário para que termine o comentário
			case ECOMENT:
				if(atual == '*') {
					estado = EFIMCOMENT;
				}
				break;
			case EFIMCOMENT:
				if(atual == ')') {
					estado = ENEUTRO;
				}
				break;
			default:
				break;
			}
			posicao++;
		} while(true);
		//return 0;
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
			throw new Exception("Linha " + linha + ": Identificador inválido: '" 
					+ token + "' tem mais de 30 caracteres");
		}
		for(int i = 0; i < reservadas.length; i++) {
			//presumindo que a linguagem é case-insensitive
			if(token.toUpperCase().equals(reservadas[i])) {
				return PRIMEIRA_RESERVADA + i;
			}
		}
		return ID; //código de token do tipo ID
	}
	
	public int tratarNumero(String token) throws Exception {
		int numero = Integer.parseInt(token);
		if(token.length() > 5 || numero > 32767 | numero < 32767) {
			throw new Exception("Linha " + linha + ": Número inválido: '" 
					+ token + "' não está entre -32767 e 32767.");
		}
		return INTEIRO;
	}
	
	public int tratarLiteral(String token) throws Exception {
		if(token.length() > 255) {
			throw new Exception("Linha " + linha + ": Literal inválido: '" 
					+ token + "' tem mais de 255 caracteres");
		}
		return LIT; //código de token do tipo ID
	}
}
