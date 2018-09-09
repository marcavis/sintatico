package sintatico;

public class Token {
	private String nome;
	private boolean terminal;
	
	public Token(String nome, boolean terminal) {
		super();
		this.nome = nome;
		this.terminal = terminal;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isTerminal() {
		return terminal;
	}
	
	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}
	
}
