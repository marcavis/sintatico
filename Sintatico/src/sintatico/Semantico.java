package sintatico;
import hipotetica.AreaInstrucoes;
import hipotetica.AreaLiterais;
import hipotetica.Hipotetica;


public class Semantico {
	static Hash simbolos;
	static AreaInstrucoes areaInstrucoes;
	static AreaLiterais areaLiterais;
	static int nivelAtual;
	static int ponteiroLivre;
	static int numVariaveis;
	static int numParametros;
	static int deslocamento;
	static int lc; //indice da areaInstrucoes
	static int lit; //indice da areaLiterais
	static int tipoId; //tipo do próximo identificador a ser incluído, variável ou parâmetro
	static String enderecoConst = "";
	static boolean houveParametro;
	static String idDaAtribuicao = "";
	static int enderecoCall = -1;
	static int parametrosEsperadosCall = 0;
	static int numParametrosEfetivos = 0; //quantos parâmetros estão sendo usados nesta chamada de função
	static String nomeProc = "";
	static int nivelId = 0;
	static int deslocamentoId = 0;
	static int contexto = 0;
	static String idDaProc = "";
	static int ultimoNumeroCase = 0;
	
	static PilhaDinamica<Integer> pilhaIf = new PilhaDinamica<Integer>();
	static PilhaDinamica<Integer> pilhaFor = new PilhaDinamica<Integer>();
	static PilhaDinamica<String> pilhaVarControleFor = new PilhaDinamica<String>();
	static PilhaDinamica<Integer> pilhaSaidaFor = new PilhaDinamica<Integer>();
	static PilhaDinamica<Integer> pilhaWhile = new PilhaDinamica<Integer>();
	static PilhaDinamica<Integer> pilhaRepeat = new PilhaDinamica<Integer>();
	static PilhaDinamica<Integer> pilhaCaseDSVT = new PilhaDinamica<Integer>();
	static PilhaDinamica<Integer> pilhaCaseDSVS = new PilhaDinamica<Integer>();
	static PilhaDinamica<Integer> pilhaCaseDSVF = new PilhaDinamica<Integer>();
	static PilhaDinamica<String> pilhaPar = new PilhaDinamica<String>();
	static PilhaDinamica<Integer> pilhaNumPar = new PilhaDinamica<Integer>();
	static PilhaDinamica<Integer> pilhaDesvios = new PilhaDinamica<Integer>(); //usada para desvios de procedures
	
	//contextos para readln ou expressao
	static int C_READLN = 1;
	static int C_EXPRESSAO = 2;
	
	static int RETU = 1;
	static int CRVL = 2;
	static int CRCT = 3;
	static int ARMZ = 4;
	static int SOMA = 5;
	static int SUBT = 6;
	static int MULT = 7;
	static int DIVI = 8;
	static int INVR = 9;
	static int NEGA = 10;
	static int CONJ = 11;
	static int DISJ = 12;
	static int CMME = 13;
	static int CMMA = 14;
	static int CMIG = 15;
	static int CMDF = 16;
	static int CMEI = 17;
	static int CMAI = 18;
	static int DSVS = 19;
	static int DSVF = 20;
	static int LEIT = 21;
	static int IMPR = 22;
	static int IMPRL = 23;
	static int AMEM = 24;
	static int CALL = 25;
	static int PARA = 26;
	static int NADA = 27;
	static int COPI = 28;
	static int DSVT = 29;
	
	static String[] legenda = {
			"    ", "RETU", "CRVL", "CRCT", "ARMZ", "SOMA", "SUBT", "MULT", "DIVI", "INVR",
			"NEGA", "CONJ", "DISJ", "CMME", "CMMA", "CMIG", "CMDF", "CMEI", "CMAI", "DSVS",
			"DSVF", "LEIT", "IMPR", "IMPRL","AMEM", "CALL", "PARA", "NADA", "COPI", "DSVT"
	};
	
	//tipos de identificadores
	static int VAR = 1;
	static int PAR = 2;
	
	public static void trataAcao(int atual, int linha) throws SintaticoException {
		trataAcao(atual, linha, "");
	}
	
	public static void trataAcao(int atual, int linha, String nome) throws SintaticoException {
		Simbolo s = new Simbolo("");
		System.out.println(atual);
		switch (atual) {
		case 100:
			//inic pilhas
			simbolos = new Hash(200);
			areaInstrucoes = new AreaInstrucoes();
			Hipotetica.InicializaAI(areaInstrucoes);
			areaLiterais = new AreaLiterais();
			Hipotetica.InicializaAL(areaLiterais);
			nivelAtual = 0;
			ponteiroLivre = 1;
			numVariaveis = 0;
			deslocamento = 3;
			areaInstrucoes.LC = 1;
			areaLiterais.LIT = 1;
			break;
		case 101:
			incluiInstrucao(PARA);
			break;
		case 102:
			incluiInstrucao(AMEM, -1, numVariaveis + 3);
			numVariaveis = 0;
			break;
		//case 103: nao existe mais,
		//	break;
		case 104:
			if(tipoId == VAR) {
				//a tabela de símbolos não permite inserção quando um símbolo com mesmo nome já existe,
				//portanto não precisamos buscar; em caso de fracasso na inserção, podemos fazer conversão alfa
				//nos casos em que a nova declaração é em um escopo mais interno.
				int retorno = simbolos.inserir(new Simbolo(nome, Categoria.VAR, nivelAtual, deslocamento + numVariaveis));
				if(retorno == -1) {
					//símbolo com esse nome já existe
					Simbolo existente = new Simbolo("");
					try {
						existente = simbolos.buscar(nome);
					} catch (Exception e) { //não acontecerá, pois a falha na inclusão indica que o símbolo existe
					}
					if(existente.getNivel() == nivelAtual) {
						throw new SintaticoException("Símbolo " + nome + " já existe na tabela neste escopo", linha);
					} else {
						//desistimos de implementar conversão alfa
						throw new SintaticoException("Símbolo " + nome + " já foi definido na tabela em um escopo superior;\nescolha outro nome para o identificador.", linha);
					}
				}
				numVariaveis++;
			} else if (tipoId == PAR) {
				String nomeUsado = nome;
				int retorno = simbolos.inserir(new Simbolo(nome, Categoria.PAR, nivelAtual));
				if(retorno == -1) {
					Simbolo existente = new Simbolo("");
					try {
						existente = simbolos.buscar(nome);
					} catch (Exception e) { //não acontecerá, pois a falha na inclusão indica que o símbolo existe
					}
					if(existente.getNivel() == nivelAtual) {
						throw new SintaticoException("Símbolo " + nome + " já existe na tabela neste escopo", linha);
					} else {
						//desistimos de implementar conversão alfa
						throw new SintaticoException("Símbolo " + nome + " já foi definido na tabela em um escopo superior;\nescolha outro nome para o identificador.", linha);
					}
				}
				pilhaPar.inserir(nomeUsado);
				numParametros++;
			} else {
				throw new SintaticoException("Tipo de identificador desconhecido", linha);
			}
			break;
		case 105:
			String nomeConst = "";
			int retorno = simbolos.inserir(new Simbolo(nome, Categoria.CONST, nivelAtual));
			nomeConst = nome;
			if(retorno == -1) {
				//símbolo com esse nome já existe
				Simbolo existente = new Simbolo("");
				try {
					existente = simbolos.buscar(nome);
				} catch (Exception e) { //não acontecerá, pois a falha na inclusão indica que o símbolo existe
				}
				if(existente.getNivel() == nivelAtual) {
					throw new SintaticoException("Símbolo " + nome + " já existe na tabela neste escopo", linha);
				} else {
					//desistimos de implementar conversão alfa
					throw new SintaticoException("Símbolo " + nome + " já foi definido na tabela em um escopo superior;\nescolha outro nome para o identificador.", linha);
				}
			}
			enderecoConst = nomeConst;
			break;
		case 106:
			try {
				s = simbolos.buscar(enderecoConst);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int valorConst = Integer.parseInt(nome);
			simbolos.atualizar(enderecoConst, s.getCategoria(), s.getNivel(), valorConst, s.getGeralB());
			break;
		case 107:
			tipoId = VAR;
			break;
		case 108:
			
			houveParametro = false;
			numParametros = 0;
			simbolos.inserir(new Simbolo(nome, Categoria.PROC, nivelAtual, -1, -1));
			nivelAtual++;
			idDaProc = nome;
			break;
		case 109:
			System.out.println(nome);
			try {
				s = simbolos.buscar(idDaProc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			simbolos.atualizar(idDaProc, s.getCategoria(), s.getNivel(), areaInstrucoes.LC + 1, numParametros);
			int i = 0;
			while(pilhaPar.tamanho() > 0) {
				String par = pilhaPar.retirar();
				try {
					s = simbolos.buscar(par);
				} catch (Exception e) {
					e.printStackTrace();
				}
				simbolos.atualizar(par, s.getCategoria(), s.getNivel(), i - numParametros, s.getGeralB());
				try {
					System.out.println(simbolos.buscar(par));
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
			pilhaNumPar.inserir(numParametros);
			pilhaDesvios.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVS, -1, -1);
			//System.exit(0);
			break;
		case 110:
			incluiInstrucao(RETU, -1, pilhaNumPar.retirar());
			alteraInstrucao(pilhaDesvios.retirar(), -1, areaInstrucoes.LC);
			
			simbolos.removerEscopo(nivelAtual);
			nivelAtual--;
			break;
		case 111:
			houveParametro = true;
			tipoId = PAR;
			break;
		case 112:
			break;
		//case 113:
		//	break;
		case 114:
			try {
				s = simbolos.buscar(nome);
			} catch (Exception e) {
				System.out.println("erroaqui?" + nome);
				throw new SintaticoException("Variável " + nome + " não localizada", linha);
			}
			if(s.getCategoria() != Categoria.VAR) {
				throw new SintaticoException("Símbolo " + nome + " não é variável", linha);
			}
			nivelId = s.getNivel();
			deslocamentoId = s.getGeralA();
			break;
		case 115:
			incluiInstrucao(ARMZ, nivelAtual - nivelId, deslocamentoId);
			System.out.println("ARMZ " + (nivelAtual - nivelId) + ", " + deslocamentoId + ", " + idDaAtribuicao );
			break;
		case 116:
			try{
				s = simbolos.buscar(nome);
			} catch (Exception e) {
				throw new SintaticoException("Procedure " + nome + " não localizada", linha);
			}
			if (s.getCategoria() != Categoria.PROC) {
				throw new SintaticoException("Símbolo " + nome + " não é procedure", linha);
			}
			enderecoCall = s.getGeralA();
			System.out.println(enderecoCall);
			parametrosEsperadosCall = s.getGeralB();
			nomeProc = nome;
			break;
		case 117:
			if(numParametrosEfetivos != parametrosEsperadosCall) {
				throw new SintaticoException("Tentativa de chamar " + nomeProc + " com número incorreto de parâmetros", linha);
			}
			incluiInstrucao(CALL, 0, enderecoCall);
			numParametrosEfetivos = 0;
			break;
		case 118:
			numParametrosEfetivos++;
			break;
		case 120:
			pilhaIf.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVF, -1, -1);
			break;
		case 121:
			alteraInstrucao((int) pilhaIf.retirar(), -1, areaInstrucoes.LC);
			break;
		case 122:
			alteraInstrucao((int) pilhaIf.retirar(), -1, areaInstrucoes.LC + 1);
			pilhaIf.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVS, -1, -1);
			break;
		case 123:
			//endereço de retorno do while
			pilhaWhile.inserir(areaInstrucoes.LC);
			break;
		case 124:
			//endereço do desvio de saída do while
			pilhaWhile.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVF, -1, -1);
			break;
		case 125:
			alteraInstrucao((int) pilhaWhile.retirar(), -1, areaInstrucoes.LC + 1);
			incluiInstrucao(DSVS, -1, pilhaWhile.retirar());
			break;
		case 126:
			pilhaRepeat.inserir(areaInstrucoes.LC);
			break;
		case 127:
			incluiInstrucao(DSVF, -1, pilhaRepeat.retirar());
			break;
		case 128:
			contexto = C_READLN;
			break;
		case 129:
			try {
				s = simbolos.buscar(nome);
			} catch (Exception e) {
				throw new SintaticoException("Identificador " + nome + " não localizado", linha);
			}
			System.out.println(s.getCategoria());
			if(contexto == C_READLN) {
				if (s.getCategoria() != Categoria.VAR) {
					throw new SintaticoException("Símbolo " + nome + " não é variável", linha);
				}
				incluiInstrucao(LEIT, -1, -1);
				incluiInstrucao(ARMZ, nivelAtual - s.getNivel(), s.getGeralA());
			} else {
				//contexto eh expressão
				if (s.getCategoria() == Categoria.VAR || s.getCategoria() == Categoria.PAR) {
					incluiInstrucao(CRVL, nivelAtual - s.getNivel(), s.getGeralA());
				} else if (s.getCategoria() == Categoria.CONST) {
					incluiInstrucao(CRCT, -1, s.getGeralA());
				} else {
					throw new SintaticoException("Símbolo " + nome + " não é variável nem constante", linha);
				}
				
			}
			break;
		case 130:
			incluiInstrucao(IMPRL, -1, areaLiterais.LIT);
			Hipotetica.IncluirAL(areaLiterais, nome.substring(1, nome.length() - 1));
			break;
		case 131:
			incluiInstrucao(IMPR, -1, -1);
			break;
		case 132:
			break;
		case 133:
			while(pilhaCaseDSVS.tamanho() > 0) {
				alteraInstrucao(pilhaCaseDSVS.retirar(), -1, areaInstrucoes.LC);
			}
			incluiInstrucao(AMEM, -1, -1);
			break;
		case 134:
			incluiInstrucao(COPI, -1, -1);
			System.out.println(nome);
			incluiInstrucao(CRCT, -1, ultimoNumeroCase);
			incluiInstrucao(CMIG, -1, -1);
			while(pilhaCaseDSVT.tamanho() > 0) {
				alteraInstrucao(pilhaCaseDSVT.retirar(), -1, areaInstrucoes.LC+1);
			}
			pilhaCaseDSVF.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVF, -1, -1);
			break;
		case 135:
			alteraInstrucao(pilhaCaseDSVF.retirar(), -1, areaInstrucoes.LC+1);
			pilhaCaseDSVS.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVS, -1, -1);
			break;
		case 136:
			incluiInstrucao(COPI, -1, -1);
			System.out.println(nome);
			incluiInstrucao(CRCT, -1, ultimoNumeroCase);
			incluiInstrucao(CMIG, -1, -1);
			pilhaCaseDSVT.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVT, -1, -1);
			//System.out.println(nome);
			//System.exit(0);
			break;
		case 137:
			try {
				s = simbolos.buscar(nome);
			} catch (Exception e) {
				throw new SintaticoException("Identificador " + nome + " não localizado", linha);
			}
			nivelId = s.getNivel();
			deslocamentoId = s.getGeralA();
			pilhaVarControleFor.inserir(nome);
			break;
		case 138:
			incluiInstrucao(ARMZ, nivelAtual - nivelId, deslocamentoId);
			break;
		case 139:
			pilhaFor.inserir(areaInstrucoes.LC);
			incluiInstrucao(COPI, -1, -1);
			incluiInstrucao(CRVL, nivelAtual - nivelId, deslocamentoId);
			incluiInstrucao(CMAI, -1, -1);
			pilhaSaidaFor.inserir(areaInstrucoes.LC);
			incluiInstrucao(DSVF, -1, -1);
			break;
		case 140:
			try {
				s = simbolos.buscar(pilhaVarControleFor.retirar());
			} catch (Exception e) {
				throw new SintaticoException("Identificador " + nome + " não localizado", linha);
			}
			incluiInstrucao(CRVL, nivelAtual - s.getNivel(), s.getGeralA());
			incluiInstrucao(CRCT, -1, 1);
			incluiInstrucao(SOMA, -1, -1);
			incluiInstrucao(ARMZ, nivelAtual - s.getNivel(), s.getGeralA());
			alteraInstrucao(pilhaSaidaFor.retirar(), -1, areaInstrucoes.LC + 1);
			incluiInstrucao(DSVS, -1, pilhaFor.retirar());
			incluiInstrucao(AMEM, -1, -1);
			break;
		case 141:
			incluiInstrucao(CMIG, -1, -1);
			break;
		case 142:
			incluiInstrucao(CMME, -1, -1);
			break;
		case 143:
			incluiInstrucao(CMMA, -1, -1);
			break;
		case 144:
			incluiInstrucao(CMAI, -1, -1);
			break;
		case 145:
			incluiInstrucao(CMEI, -1, -1);
			break;
		case 146:
			incluiInstrucao(CMDF, -1, -1);
			break;
		case 147:
			incluiInstrucao(INVR, -1, -1);
			break;
		case 148:
			incluiInstrucao(SOMA, -1, -1);
			break;
		case 149:
			incluiInstrucao(SUBT, -1, -1);
			break;
		case 150:
			incluiInstrucao(DISJ, -1, -1);
			break;
		case 151:
			incluiInstrucao(MULT, -1, -1);
			break;
		case 152:
			incluiInstrucao(DIVI, -1, -1);
			break;
		case 153:
			incluiInstrucao(CONJ, -1, -1);
			break;
		case 154:
			incluiInstrucao(CRCT, -1, Integer.parseInt(nome));
			break;
		case 155:
			incluiInstrucao(NEGA, -1, -1);
			break;
		case 156:
			contexto = C_EXPRESSAO;
			break;
		case 157: //ação semântica adicional que trata os números logo que encontrados
			//nas linhas case x, y, z:
			ultimoNumeroCase = Integer.parseInt(nome);
			break;
		default:
			throw new SintaticoException("Instrução desconhecida", linha);
			//comando não existe
		}
		
	}

	public static void incluiInstrucao(int instrucao) {
		incluiInstrucao(instrucao, -1, -1);
	}
	
	public static void incluiInstrucao(int instrucao, int op1, int op2) {
		Hipotetica.IncluirAI(areaInstrucoes, instrucao, op1, op2);
	}
	
	public static void alteraInstrucao(int indice, int op1, int op2) {
		Hipotetica.AlterarAI(areaInstrucoes, indice, op1, op2);
	}
}
