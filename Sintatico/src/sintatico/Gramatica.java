package sintatico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Gramatica {
	private ArrayList<Producao> producoes;
	private int[][] parsing;
	private HashMap<String, Boolean> tiposDeSimbolo;
	
	public Gramatica() {
		producoes = new ArrayList<Producao>();
		tiposDeSimbolo = new HashMap<String, Boolean>();
	}
	
	public HashMap<String, Boolean> getTiposDeSimbolo() {
		return tiposDeSimbolo;
	}
	
	public boolean ehTerminal(String s) {
		return tiposDeSimbolo.get(s);
	}
	
	public void addProducao(Producao p) {
		producoes.add(p);
	}
	
	public ArrayList<Producao> getProducoes() {
		return producoes;
	}
	
	public HashMap<String, ArrayList<String>> first() {
		HashMap<String, ArrayList<String>> resultado = new HashMap<String, ArrayList<String>>();
		for (Producao p : producoes) {
			if (resultado.containsKey(p.getEsquerda())) {
				//perigoso, isso altera o objeto original
				//ArrayList<String> novoFirst = resultado.get(p.getEsquerda());
				ArrayList<String> novoFirst = new ArrayList<String>();
				for (String s : resultado.get(p.getEsquerda())) {
					novoFirst.add(s);
				}
				
				String novoItem = p.getDireita().get(0);
				boolean jaExiste = false;
				for (String s : novoFirst) {
					if (novoItem.equals(s))
						jaExiste = true;
				}
				//apenas adicionar ao First símbolos que não foram adicionados ainda
				if(!jaExiste) {
					novoFirst.add(p.getDireita().get(0));
					resultado.put(p.getEsquerda(), novoFirst);
				}
			}
			else
				resultado.put(p.getEsquerda(), p.getDireita());
		}
		return resultado;
	}
	
	public boolean primeiraRegra() {
		return primeiraRegra(false);
	}
	
	//quando debug = true, mostrar onde a regra foi ferida
	public boolean primeiraRegra(boolean mostraErro) {
		boolean atende = true;
		
		//teste de recursão à esquerda direta
		int numRegra = 0;
		for (Producao p : producoes) {
			numRegra++;
			if(p.getEsquerda().equals(p.getDireita().get(0))) {
				atende = false;
				if(mostraErro) {
					System.out.println("Regra " + numRegra + " com recursão à esquerda direta: " + p);
				}
			}
			
		}
		
		//teste de recursão à esquerda indireta
		numRegra = 0;
		boolean[] regraVisitada = new boolean[producoes.size()];
		for(int i = 0; i < regraVisitada.length; i++)
			regraVisitada[i] = false;
		for (String s : getNaoTerminais()) {
			//System.out.println("1"+s);
			ArrayList<String> naoTerminaisVisitados = new ArrayList<String>();
			for (int i = 0; i < producoes.size(); i++) {
				Producao p = producoes.get(i);
				if (!ehTerminal(p.getDireita().get(0)) && !p.getEsquerda().equals(p.getDireita().get(0))) {
					//essa regra começa com um símbolo não-terminal T, devemos investigar
					//procurando loops dentro das regras que partem de T
					String t = p.getDireita().get(0);
					for (int j = 0; j < producoes.size(); j++) {
						if()
					}
					//descer(t, naoTerminaisVisitados);
					//Function<T, R>
					System.out.println(p + "hufdshfa");
				} else {
					regraVisitada[i] = true;
				}
			}
		}
		
		
		return atende;
	}
	
	

	private ArrayList<String> getNaoTerminais() {
		ArrayList<String> resultado = new ArrayList<String>();
		for (String s : tiposDeSimbolo.keySet()) {
			if(!tiposDeSimbolo.get(s))
				resultado.add(s);
		}
		return resultado;
	}

	public void listaRegras() {
		int indice = 0;
		for (Producao p : producoes) {
			indice++;
			System.out.println("" + indice + ": " + p);
		}
	}
}
