package sintatico;

import java.lang.Character;

public class Lexico {
	private char[] fonte;
	private int linha;
	private int posicao;
	private int posicaoAnterior;
	private int estado;
	private String esteToken;
	private String ultimoLexema;
	
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
	int LPAREN = 2; //"("
	int RPAREN = 3; //")"
	int COMMA = 4; //","
	int SEMICOLON = 5; //";"
	int PERIOD = 6; //"."
	int COLON = 7; //":"
	int ATTRIBUTION = 8; //":="
	int EQUALS = 9; //"="
	int LT = 10; //"<"
	int GT = 11; //">"
	int GE = 12; //">="
	int LE = 13; //"<="
	int DIFFERENT = 14; //"<>"
	int PLUS = 15; //"+"
	int MINUS = 16; //"-"
	int ASTERISK = 17; //"*"
	int SLASH = 18; //"/"
	int ID = 19;
	int INTEIRO = 20;
	int LITERAL = 21;
	int PROGRAM = 22;
	int CONST = 23;
	int VAR = 24;
	int PROCEDURE = 25;
	int BEGIN = 26;
	int END = 27;
	int INTEGER = 28;
	int CALL = 29;
	int IF = 30;
	int THEN = 31;
	int ELSE = 32;
	int WHILE = 33;
	int DO = 34;
	int REPEAT = 35;
	int UNTIL = 36;
	int READLN = 37;
	int WRITELN = 38;
	int OR = 39;
	int AND = 40;
	int NOT = 41;
	int FOR = 42;
	int TO = 43;
	int CASE = 44;
	int OF = 45;
	final private int PRIMEIRA_RESERVADA = 22; //PROGRAM
	final private String[] reservadas = new String[] {
			"PROGRAM", "CONST", "VAR", "PROCEDURE", "BEGIN", "END",
			"INTEGER", "CALL", "IF", "THEN", "ELSE", "WHILE", "DO",
			"REPEAT", "UNTIL", "READLN", "WRITELN", "OR", "AND", "NOT",
			"FOR", "TO", "CASE", "OF"
	};
	
	public Lexico(char[] fonte) {
		this.fonte = fonte;
		this.posicao = 0;
		this.linha = 0; 
		this.esteToken = "";
	}
	
	public int getLinha() {
		return linha;
	}
	
	public int getPosicao() {
		return posicao;
	}
	
	public String getUltimoLexema() {
		return ultimoLexema;
	}

	public int proxToken() throws Exception {
		posicaoAnterior = posicao;
		esteToken = "";
		do { //um do-while aqui?
			char atual = ' ';
			try {
				atual = fonte[posicao];
			} catch (Exception e) {
				throw new Exception("");
			}
			
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
		ultimoLexema = token;
		return ID; //código de token do tipo ID
	}
	
	public int tratarNumero(String token) throws Exception {
		int numero = Integer.parseInt(token);
		if(token.length() > 5 || numero > 32767 | numero < 32767) {
			throw new Exception("Linha " + linha + ": Número inválido: '" 
					+ token + "' não está entre -32767 e 32767.");
		}
		ultimoLexema = token;
		return INTEIRO;
	}
	
	public int tratarLiteral(String token) throws Exception {
		if(token.length() > 255) {
			throw new Exception("Linha " + linha + ": Literal inválido: '" 
					+ token + "' tem mais de 255 caracteres");
		}
		ultimoLexema = token;
		return LITERAL; //código de token do tipo ID
	}
	
	public void retorna() {
		posicao = posicaoAnterior;
	}
}
