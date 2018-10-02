package simbolos;

import sintatico.Hash;
import sintatico.Simbolo;

public class Principal {
	public static void main(String[] args) {
		Hash h = new Hash(400);
		//System.out.println(h.horner("string"));
		for(int i = 0; i < 500; i++) {
			h.inserir(new Simbolo(""+(i*27)));
		}
		System.out.println(h);
	}
}
