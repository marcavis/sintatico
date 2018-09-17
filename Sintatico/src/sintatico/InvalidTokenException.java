package sintatico;

//um tipo de exceção específico para distinguir erros de captura de tokens definidos pela linguagem LMS
//(string muito longa, número acima de 32767) de exceções mais básicas, como NullPointerException
public class InvalidTokenException extends Exception {

	private static final long serialVersionUID = -6718791518279400021L;

	public InvalidTokenException() {
		super();
	}

	public InvalidTokenException(String message) {
		super(message);
	}

}
