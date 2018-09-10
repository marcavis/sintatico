package sintatico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Sintatico {
	public static void main(String[] args) {
		
		exercicio1G1();
		
	}
	
	public static void exercicio1G1() {
		Gramatica gG = new Gramatica();
		HashMap<String, Boolean> tiposDeSimbolo = gG.getTiposDeSimbolo();
		tiposDeSimbolo.put("A", false); //A é um token não-terminal
		tiposDeSimbolo.put("y",true);
		tiposDeSimbolo.put("k",true);
		tiposDeSimbolo.put("x",true);
		tiposDeSimbolo.put("m",true);
		tiposDeSimbolo.put("r",true);
		tiposDeSimbolo.put("W",false);
		tiposDeSimbolo.put("1",true);
		
		gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("A", "y"))));
		gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("k"))));
		gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("A", "x"))));
		gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("m", "W"))));
		
		//esses dois nao existem no exemplo
		gG.addProducao(new Producao("A", new ArrayList<String>(Arrays.asList("W","y"))));
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("A","y"))));
		
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("r"))));
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("k"))));
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("1"))));
		gG.addProducao(new Producao("W", new ArrayList<String>(Arrays.asList("y"))));
		//ArrayList<String> places = new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
		HashMap<String, ArrayList<String>> first = gG.first();
		System.out.println(first);
		for (HashMap.Entry<String, ArrayList<String>> e : first.entrySet()) {
			System.out.println(e.getValue());
		}
		System.out.println(gG.primeiraRegra(true));
		
		gG.listaRegras();
		
	}
}
