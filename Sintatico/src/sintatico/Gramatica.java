package sintatico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Gramatica {
	private ArrayList<Producao> producoes;
	private String inicial;
	private int[][] parsing;
	private HashMap<String, Boolean> tiposDeSimbolo;
	
	public Gramatica(String inicial) {
		this.inicial = inicial;
		producoes = new ArrayList<Producao>();
		tiposDeSimbolo = new HashMap<String, Boolean>();
	}
	
	//retorna o símbolo inicial da gramática
	public String getInicial() {
		return inicial;
	}
	
	//dicionário que liga símbolos ao tipo (terminal ou não)
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
	
	//retorna um dicionário associando símbolos ao primeiro símbolo  
	public HashMap<String, ArrayList<String>> first() {
		Function<String, ArrayList<String>> achaFirstNT = (String simb) -> {
			for (Producao p : producoes) {
				if (p.getEsquerda().equals(simb)) {
					//TODO: fazer de modo recursivo com que os firsts de outros NTs sejam adicionados
					//ao first do simbolo atual
				}
			}
			return null;
		};
		
		if(!primeiraRegra()) {
			System.out.println("Impossível criar first, gramática não é determinística");
			return null;
		}
			
		//TODO: alterar para preencher por simbolo, e não por produção
		
		HashMap<String, ArrayList<String>> resultado = new HashMap<String, ArrayList<String>>();
		//preencher os firsts dos símbolos terminais
		for (String simb : tiposDeSimbolo.keySet()) {
			//se for terminal, adicione o first
			if(tiposDeSimbolo.get(simb))  {
				ArrayList<String> novoFirst = new ArrayList<String>();
				novoFirst.add(simb);
				resultado.put(simb, novoFirst);
			} else {
				for (Producao p : producoes) {
					if (p.getEsquerda().equals(simb)) {
						
					}
				}
			}
		}
		
		//preencher os firsts dos símbolos não-terminais
//		for (Producao p : producoes) {
//			if (resultado.containsKey(p.getEsquerda())) {
//				//perigoso, isso altera o objeto original
//				//ArrayList<String> novoFirst = resultado.get(p.getEsquerda());
//				ArrayList<String> novoFirst = new ArrayList<String>();
//				for (String s : resultado.get(p.getEsquerda())) {
//					novoFirst.add(s);
//				}
//				
//				String novoItem = p.getDireita().get(0);
//				boolean jaExiste = false;
//				for (String s : novoFirst) {
//					if (novoItem.equals(s))
//						jaExiste = true;
//				}
//				//apenas adicionar ao First símbolos que não foram adicionados ainda
//				if(!jaExiste) {
//					novoFirst.add(p.getDireita().get(0));
//					resultado.put(p.getEsquerda(), novoFirst);
//				}
//			}
//			else
//				resultado.put(p.getEsquerda(), resultado.get(p.getDireita().get(0)));
//		}
		
		
		return resultado;
	}
	
	public HashMap<String, ArrayList<String>> follow() {
		HashMap<String, ArrayList<String>> resultado = new HashMap<String, ArrayList<String>>();
		resultado.get(getInicial()).add("$");
		
		
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

		for (String s : getNaoTerminais()) {
			boolean[] regraVisitada = new boolean[producoes.size()];
			for(int i = 0; i < regraVisitada.length; i++)
				regraVisitada[i] = false;
			int qtNaoVisitadas = producoes.size();
			
			//vetor que diz como fazer o backtrack para procurar recursões novamente no
			//"nó" anterior
			int[] regraAncestral = new int[producoes.size()];
			for(int i = 0; i < regraAncestral.length; i++)
				regraAncestral[i] = -1;
			
			//System.out.println("1"+s);
			ArrayList<String> naoTerminaisVisitados = new ArrayList<String>();
			int i = 0;
			while (qtNaoVisitadas > 0) {
				Producao p = producoes.get(i);
				//System.out.println("estou na regra " + i +", " +p );
				if(!regraVisitada[i]) {
					if (!ehTerminal(p.getDireita().get(0)) && !p.getEsquerda().equals(p.getDireita().get(0))) {
						if(s.equals(p.getDireita().get(0)) && jaMeVisitei(s, naoTerminaisVisitados)) {
							if (mostraErro)
								System.out.println("existe recursão à direita terminando na regra " + i + ": " + s + " -> * -> " + p);
							atende = false;
							break;
						} else {
							//essa regra começa com um símbolo não-terminal T, devemos investigar
							//procurando loops dentro das regras que partem de T
							String t = p.getDireita().get(0);
							boolean acheiT = false;
							for(int j = 0; j < producoes.size(); j++) {
								
								Producao q = producoes.get(j);
								if(q.getEsquerda().equals(t) && regraVisitada[j] == false) {
									//achamos uma produção que parte de símbolo não-terminal T
									regraAncestral[j] = i;
									naoTerminaisVisitados.add(p.getEsquerda());
									i = j;
									acheiT = true;
									break;
								}
							}
							if (!acheiT) {
								regraVisitada[i] = true;
								qtNaoVisitadas--;
								if (regraAncestral[i] == -1) {
									//não achei uma produção para seguir nem posso voltar atrás,
									//então não deve haver recursão indireta
									break;
								} else {
									//voltaremos atrás, ao símbolo que tinha a produção que levava a T
									i = regraAncestral[i];
									naoTerminaisVisitados.remove(naoTerminaisVisitados.size());
								}
							}
						}
					} else {
						//saímos de uma sequência de não-terminais, vamos seguir adiante
						regraVisitada[i] = true;
						qtNaoVisitadas--;
						i = (i+1) % producoes.size();
					}
				} else {
					//estamos numa regra que já foi visitada, vamos seguir adiante, dando a volta
					//para começar a procurar na regra 0 se necessário
					regraVisitada[i] = true;
					qtNaoVisitadas--;
					i = (i+1) % producoes.size();
				}
			}
		}
		
		return atende;
	}
	
	public boolean jaMeVisitei(String procurada, ArrayList<String> lista) {
		for (String string : lista)
			if( string.equals(procurada))
				return true;
		return false;
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
