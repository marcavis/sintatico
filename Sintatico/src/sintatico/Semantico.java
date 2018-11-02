package sintatico;
import hipotetica.AreaInstrucoes;
import hipotetica.AreaLiterais;


public class Semantico {
	static Hash simbolos;
	static AreaInstrucoes areaInstrucoes;
	static AreaLiterais areaLiterais;
	static int nivelAtual;
	static int ponteiroLivre;
	static int numVariaveis;
	static int deslocamento;
	static int lc;
	static int lit;
	
	public static void trataAcao(int atual) {
		switch (atual) {
		case 100:
			//inic pilhas
			simbolos = new Hash(200);
			areaInstrucoes = new AreaInstrucoes();
			areaLiterais = new AreaLiterais();
			nivelAtual = 0;
			ponteiroLivre = 1;
			numVariaveis = 0;
			deslocamento = 3;
			lc = 1;
			lit = 1;
			break;

		default:
			break;
		}
		
	}

}
