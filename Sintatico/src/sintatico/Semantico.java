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
	static int lit; //indice da areaLiteraiss
	static int tipoId; //tipo do próximo identificador a ser incluído, variável ou parâmetro
	static String enderecoConst = "";
	static boolean houveParametro;
	static String idDaAtribuicao = "";
	static int nivelId = 0;
	static int deslocamentoId = 0;
	static int contexto = 0;
	static String idDaProc = "";
	
	static PilhaDinamica<Integer> pilhaIf = new PilhaDinamica<Integer>();
	
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
			lc = 1;
			lit = 1;
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
					}
					//já existe mas foi declarado em um escopo diferente, então podemos
					//inserir um símbolo novo com um nome diferente
					conversaoAlfa(new Simbolo(nome, Categoria.VAR, nivelAtual, deslocamento + numVariaveis));
				}
				numVariaveis++;
			} else if (tipoId == PAR) {
				int retorno = simbolos.inserir(new Simbolo(nome, Categoria.PAR, nivelAtual));
				if(retorno == -1) {
					Simbolo existente = new Simbolo("");
					try {
						existente = simbolos.buscar(nome);
					} catch (Exception e) { //não acontecerá, pois a falha na inclusão indica que o símbolo existe
					}
					if(existente.getNivel() == nivelAtual) {
						throw new SintaticoException("Símbolo " + nome + " já existe na tabela neste escopo", linha);
					}
					//já existe mas foi declarado em um escopo diferente, então podemos
					//inserir um símbolo novo com um nome diferente
					conversaoAlfa(new Simbolo(nome, Categoria.PAR, nivelAtual));
				}
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
				}
				//já existe mas foi declarado em um escopo diferente, então podemos
				//inserir um símbolo novo com um nome diferente
				nomeConst = conversaoAlfa(new Simbolo(nome, Categoria.CONST, nivelAtual));
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
			simbolos.inserir(new Simbolo(nome, Categoria.PROC, nivelAtual));
			nivelAtual++;
			idDaProc = nome;
			break;
		case 109:
			System.out.println(nome);
			//TODO
			break;
		case 110:
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
				System.out.println(simbolos);
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
			incluiInstrucao(ARMZ, nivelId, deslocamentoId);
			break;
		case 116:
			break;
		case 117:
			break;
		case 118:
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
			break;
		case 128:
			contexto = C_READLN;
			break;
		case 129:
			
			break;
		case 130:
			
			break;
		case 131:
			
			break;
		case 132:
			
			break;
		case 133:
			
			break;
		case 134:
			
			break;
		case 135:
			
			break;
		case 136:
			
			break;
		case 137:
			
			break;
		case 138:
			
			break;
		case 139:
			
			break;
		case 140:
			//TODO
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
		default:
			//TODO = comando não existe
			break;
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
	
	//insere um novo símbolo com o nome desejado sufixado de algum número para não haver
	//colisão de identificadores
	public static String conversaoAlfa(Simbolo s) {
		int sufixo = 1;
		int retorno = -1;
		String nomeAntigo = s.getNome();
		String novoNome = "";
		while (retorno == -1) {
			novoNome = nomeAntigo + sufixo;
			//se houver colisão, tentaremos de novo com um sufixo diferente
			retorno = simbolos.inserir(new Simbolo(novoNome, s.getCategoria(), s.getNivel(), s.getGeralA(), s.getGeralB()));
			sufixo++;
		}
		simbolos.inserirConversao(novoNome, nivelAtual);
		return novoNome;
	}
}
