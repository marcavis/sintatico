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
	
	public Simbolo(String nome, Categoria categoria, int nivel, int geralA, int geralB) {
		this.nome = nome;
		this.categoria = categoria;
		this.nivel = nivel;
		this.geralA = geralA;
		this.geralB = geralB;
	}
	
	//construtor sem geralB, não necessário para algumas categorias
	public Simbolo(String nome, Categoria categoria, int nivel, int geralA) {
		this.nome = nome;
		this.categoria = categoria;
		this.nivel = nivel;
		this.geralA = geralA;
	}
	
	public Simbolo(String nome, Categoria categoria, int nivel) {
		this.nome = nome;
		this.categoria = categoria;
		this.nivel = nivel;
	}

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

	@Override
	public String toString() {
		return "Simbolo [nome=" + nome + ", categoria=" + categoria + ", nivel=" + nivel + ", geralA=" + geralA
				+ ", geralB=" + geralB + "]";
	}
	

}
