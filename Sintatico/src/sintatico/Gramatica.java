package sintatico;

import java.util.ArrayList;
import java.util.HashMap;

public class Gramatica {
	private ArrayList<Producao> producoes;
	private int[][] parsing;
	
	public Gramatica() {
		producoes = new ArrayList<Producao>();
	}
	
	public void addProducao(Producao p) {
		producoes.add(p);
	}
	
	public HashMap<Token, ArrayList<Token>> first() {
		HashMap<Token, ArrayList<Token>> resultado = new HashMap<Token, ArrayList<Token>>();
		for (Producao p : producoes) {
			resultado.put(p.getEsquerda(), p.getDireita());
		}
		return resultado;
	}
}
