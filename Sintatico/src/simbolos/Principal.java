package simbolos;

import sintatico.Hash;
import sintatico.Simbolo;

public class Principal {
	public static void main(String[] args) {
		Hash h = new Hash(300);
		//System.out.println(h.horner("string"));
		
		h.inserir(new Simbolo("gha"));
	}
}
