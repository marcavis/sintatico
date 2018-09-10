package sintatico;

import java.util.ArrayList;

public class Producao {
	private String esquerda;
	private ArrayList<String> direita;
	
	public Producao(String esquerda, ArrayList<String> direita) {
		super();
		this.esquerda = esquerda;
		this.direita = direita;
	}

	public String getEsquerda() {
		return esquerda;
	}
	
	public void setEsquerda(String esquerda) {
		this.esquerda = esquerda;
	}
	
	public ArrayList<String> getDireita() {
		return direita;
	}
	
	public void setDireita(ArrayList<String> direita) {
		this.direita = direita;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String resultado = ""; 
		for (String d : direita) {
			resultado += d;
		}
		return getEsquerda() + " -> " + resultado;
	}
}
