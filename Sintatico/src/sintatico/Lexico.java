package sintatico;

import java.lang.Character;

public class Lexico {
	private char[] fonte;
	private int posicao;
	private int estado;
	private String esteToken;
	
	//estados possíveis:
	int NEUTRO = 0;
	int ID = 1;
	int NUMERO = 2;
	int LITERAL = 8;
	
	public Lexico(char[] fonte) {
		this.fonte = fonte;
		this.posicao = 0;
		this.esteToken = "";
	}
	
	public int proxToken() {
		
		char atual = fonte[posicao];
		
		//provavelmente colocar as estruturas abaixo dentro de um while(true)
		// que retornam um código quando completam um token com sucesso (ou não)
		if(estado == ID) {
			posicao++;
			if(Character.isLetter(atual) || atual == '_') {
				esteToken += atual;
				//estado = ID;
			}
			if(Character.isDigit(atual)) {
				esteToken += atual;
			}
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
}
