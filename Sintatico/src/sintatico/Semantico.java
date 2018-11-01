package sintatico;

public class Semantico {
	static Hash simbolos;
	
	
	
	public static void trataAcao(int atual) {
		switch (atual) {
		case 100:
			//inic pilhas
			simbolos = new Hash(200);
			
			break;

		default:
			break;
		}
		
	}

}
