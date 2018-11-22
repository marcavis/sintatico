package sintatico;

import java.lang.Character;

public class Lexico {
	private char[] fonte;
	private int posicao;
	private int posicaoAnterior;
	private int estado;
	private String esteToken;
	private String ultimoLexema;
	private int ultimoNumero;
	
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
	//lista para facilitar o código que identifica qual palavra reservada foi detectada
	final private String[] reservadas = new String[] {
			"PROGRAM", "CONST", "VAR", "PROCEDURE", "BEGIN", "END",
			"INTEGER", "CALL", "IF", "THEN", "ELSE", "WHILE", "DO",
			"REPEAT", "UNTIL", "READLN", "WRITELN", "OR", "AND", "NOT",
			"FOR", "TO", "CASE", "OF"
	};
	
	public Lexico(char[] fonte) {
		this.fonte = fonte;
		this.posicao = 0;
		this.esteToken = "";
	}
	
	//conta a quantidade de linhas passada até o momento
	public int getLinha() {
		int linha = 0;
		for(int i = 0; i < posicao; i++) {
			if(fonte[i] == '\n') {
				linha++;
			}
		}
		return linha;
	}
	
	public int getPosicao() {
		return posicao;
	}
	
	//retorna qual foi o último identificador, número ou literal identificado
	public String getUltimoLexema() {
		return ultimoLexema;
	}


	//função que, partindo da posição do cursor (variável "posicao" dentro do arquivo), captura o token seguinte
	public int proxToken() throws Exception {
		posicaoAnterior = posicao;
		esteToken = "";
		do {
			char atual = ' ';
			try {
				atual = fonte[posicao];
			} catch (Exception e) {
				throw e;
			}
			
			//implementação de algo parecido com um autômato para o léxico
			switch (estado) {
			case ENEUTRO:
				if(Character.isWhitespace(atual)) {
					//basicamente ignorar espaços, exceto \n, que incrementa o contador de linhas
					//porém os \n são contados apenas quando se solicita o número de linha
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
					ultimoLexema = ")";
					return RPAREN;
				} else if (atual == '(') {
					estado = ELPAREN;
				} else if (atual == '+') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = "+";
					return PLUS;
				} else if (atual == '-') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = "-";
					return MINUS;
				} else if (atual == '*') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = "*";
					return ASTERISK;
				} else if (atual == '/') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = "/";
					return SLASH;
				} else if (atual == '=') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = "=";
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
					ultimoLexema = ";";
					return SEMICOLON;
				} else if (atual == ',') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = ",";
					return COMMA;
				} else if (atual == '.') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = ".";
					return PERIOD;
				} else {
					throw new InvalidTokenException("Linha " + getLinha() + ": Caractere \"" + atual + "\" inválido encontrado.");
				}
				break;
			case EID:
				//já estamos dentro de um identificador, portanto o segundo caractere em diante também
				//pode ser um número
				if(Character.isLetter(atual) || Character.isDigit(atual)) {
					esteToken += atual;
				} else {
					//acabou o identificador, voltar ao estado neutro sem avançar na leitura de
					//caracteres do código-fonte.
					estado = ENEUTRO;
					return tratarIdentificador(esteToken);
				}
				break;
			case ENUMERO:
				//estamos dentro de um número - um número seguido de uma letra é sempre um erro,
				//mas em outros casos devolve-se a decisão para o estado neutro do autômato
				if(Character.isDigit(atual)) {
					esteToken += atual;
				} else if(Character.isLetter(atual)) {
					throw new Exception("Linha " + getLinha() + ": Caractere inesperado '" 
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
					ultimoLexema = ":=";
					return ATTRIBUTION;
				} else {
					estado = ENEUTRO;
					ultimoLexema = ":";
					return COLON;
				}
				//break;
			case EMENOR:
				if(atual == '=') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = "<=";
					return LE;
				} else if(atual == '>') {
					estado = ENEUTRO;
					posicao++;
					ultimoLexema = "<>";
					return DIFFERENT;
				} else {
					//foi encontrado apenas '<', não seguir adiante e devolver o 
					//estado para o padrão
					estado = ENEUTRO;
					ultimoLexema = "<";
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
				esteToken += atual;
				if(atual == '"') {
					estado = ENEUTRO;
					posicao++;
					return tratarLiteral(esteToken);
				} 
				break;
			case ELPAREN:
				if(atual == '*') {
					estado = ECOMENT;
				} else {
					estado = ENEUTRO;
					ultimoLexema = "(";
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
	}
	
	//retorna se o número x está incluído num conjunto de números 
	public boolean contido(int x, int[] conjunto) {
		boolean resposta = false;
		for (int i : conjunto)
			if (i == x) 
				return true;
		return resposta;
	}
	
	//se for um identificador válido retorna o código de identificador normal
	//ou o código de alguma palavra reservada específica
	public int tratarIdentificador(String token) throws Exception {
		ultimoLexema = token;
		if(token.length() > 30) {
			throw new InvalidTokenException("Linha " + getLinha() + ": Identificador inválido: '" 
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
	
	//retorna o código de token de número inteiro, se for um número permitido pela linguagem
	public int tratarNumero(String token) throws Exception {
		ultimoLexema = token;
		int numero = Integer.parseInt(token);
		if(numero > 32767 || numero < -32767) {
			throw new InvalidTokenException("Linha " + getLinha() + ": Número inválido: '" 
					+ token + "' não está entre -32767 e 32767.");
		}
		return INTEIRO;
	}
	
	//retorna o código de token de literal ("string"), se for um literal válido
	public int tratarLiteral(String token) throws Exception {
		ultimoLexema = token;
		
		//permitir strings com 2 caracteres a mais que o limite já que as aspas estão sendo contadas
		if(token.length() > 255 + 2) {
			throw new InvalidTokenException("Linha " + getLinha() + ": Literal inválido: '" 
					+ token + "' tem mais de 255 caracteres");
		}
		return LITERAL; //código de token do tipo ID
	}
	
	//retrocede na leitura do código fonte para que um token possa ser lido novamente
	public void retorna() {
		posicao = posicaoAnterior;
	}
	
	//informa se chegamos no fim do código fonte
	public boolean semTokens() {
		return posicao >= fonte.length;
	}
}
