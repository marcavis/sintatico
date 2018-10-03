package simbolos;

import sintatico.Categoria;
import sintatico.Hash;
import sintatico.Simbolo;

public class Principal {
	public static void main(String[] args) {
		//criar um hash que espera armazenar X elementos
		Hash tabela = new Hash(400);
		
		tabela.inserir(new Simbolo("x", Categoria.VAR, 0, 1));
		tabela.inserir(new Simbolo("y", Categoria.VAR, 0, 2));
		tabela.inserir(new Simbolo("pi", Categoria.CONST, 0, 314));
		tabela.inserir(new Simbolo("raizDeDois", Categoria.CONST, 0, 142));
		tabela.inserir(new Simbolo("bhaskara", Categoria.PROC, 0, 50, 3));
		tabela.inserir(new Simbolo("delta", Categoria.VAR, 1, 3));
		tabela.inserir(new Simbolo("resultado", Categoria.VAR, 1, 4));
		tabela.inserir(new Simbolo("a", Categoria.PAR, 1, 1));
		tabela.inserir(new Simbolo("b", Categoria.PAR, 1, 2));
		tabela.inserir(new Simbolo("c", Categoria.PAR, 1, 3));
		
		System.out.println("Conteúdo após 10 inserções:\n" + tabela + "\n");
		
		tabela.atualizar("pi", Categoria.CONST, 0, 628);
		tabela.atualizar("bhaskara", Categoria.PROC, 0, 55, 3);
		tabela.atualizar("delta", Categoria.VAR, 1, 6);
		tabela.atualizar("raizDeDois", Categoria.CONST, 0, 171);
		tabela.atualizar("c", Categoria.PAR, 1, 4);
		
		System.out.println("Conteúdo após 5 alterações:\n" + tabela + "\n");
		
		tabela.remover("pi");
		tabela.remover("raizDeDois");
		tabela.remover("resultado");
		
		System.out.println("Conteúdo após 3 remoções:\n" + tabela + "\n");
		
		try {
			tabela.buscar("pedraFilosofal");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			System.out.println(tabela.buscar("x"));
			System.out.println(tabela.buscar("y"));
			System.out.println(tabela.buscar("bhaskara"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
