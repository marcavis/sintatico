package sintatico;

public class SintaticoException extends Exception {
	//são as exceções que chegam à interface prontas pra apresentação;
	//às vezes, empacotam uma InvalidTokenException recebida anteriormente.
	private static final long serialVersionUID = -8227969311013494310L;
	private String aviso;
	private int linha;
	public SintaticoException(String aviso, int linha) {
		// TODO Auto-generated constructor stub
		this.aviso = aviso;
		this.linha = linha;
	}

	public int getLinha() {
		return linha;
	}
	
	public String getAviso() {
		return aviso;
	}
}
