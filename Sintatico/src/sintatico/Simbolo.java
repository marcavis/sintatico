package sintatico;

public class Simbolo {
	private String nome;
	private Categoria categoria;
	private int nivel;
	private int geralA;
	private int geralB;
	
	public Simbolo(String nome) {
		this.nome = nome;
	}
	
	//public 
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getGeralA() {
		return geralA;
	}
	public void setGeralA(int geralA) {
		this.geralA = geralA;
	}
	public int getGeralB() {
		return geralB;
	}
	public void setGeralB(int geralB) {
		this.geralB = geralB;
	}
}
