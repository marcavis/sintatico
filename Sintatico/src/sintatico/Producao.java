package sintatico;

import java.util.ArrayList;

public class Producao {
	private Token esquerda;
	private ArrayList<Token> direita;
	
	public Producao(Token esquerda, ArrayList<Token> direita) {
		super();
		this.esquerda = esquerda;
		this.direita = direita;
	}

	public Token getEsquerda() {
		return esquerda;
	}
	
	public void setEsquerda(Token esquerda) {
		this.esquerda = esquerda;
	}
	
	public ArrayList<Token> getDireita() {
		return direita;
	}
	
	public void setDireita(ArrayList<Token> direita) {
		this.direita = direita;
	}
}
