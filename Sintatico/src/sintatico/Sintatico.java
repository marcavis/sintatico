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
		Token _a = new Token("A",false);
		Token y = new Token("y",true);
		Token k = new Token("k",true);
		Token x = new Token("x",true);
		Token m = new Token("m",true);
		Token r = new Token("r",true);
		Token _w = new Token("W",false);
		Token _1 = new Token("1",true);
		
		gG.addProducao(new Producao(_a, new ArrayList<Token>(Arrays.asList(_a, y))));
		gG.addProducao(new Producao(_a, new ArrayList<Token>(Arrays.asList(k))));
		gG.addProducao(new Producao(_a, new ArrayList<Token>(Arrays.asList(_a, x))));
		gG.addProducao(new Producao(_a, new ArrayList<Token>(Arrays.asList(m, _w))));
		gG.addProducao(new Producao(_w, new ArrayList<Token>(Arrays.asList(r))));
		gG.addProducao(new Producao(_w, new ArrayList<Token>(Arrays.asList(k))));
		gG.addProducao(new Producao(_w, new ArrayList<Token>(Arrays.asList(_1))));
		gG.addProducao(new Producao(_w, new ArrayList<Token>(Arrays.asList(y))));
		//ArrayList<String> places = new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
		HashMap<Token, ArrayList<Token>> first = gG.first();
		System.out.println(first);
		for (HashMap.Entry<Token, ArrayList<Token>> e : first.entrySet()) {
			System.out.println(e.getValue());
		}
	}
}
