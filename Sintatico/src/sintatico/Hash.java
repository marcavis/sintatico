package sintatico;

public class Hash {
	private NodeSimbolo[] lista;
	private int tamanho;
	
	public Hash(int tamanhoInformado) {
		tamanho = tamanhoCerto(tamanhoInformado);
		lista = new NodeSimbolo[tamanho];
	}
	
	//métodos que não sejam "buscar" retornam o código hash calculado
	//quando há sucesso, e -1 quando o registro não é encontrado.
	
	//retorna -1 quando o símbolo já existe na tabela; não é da alçada
	//da tabela hash fazer a conversão alpha criando um símbolo diferente
	public int inserir(Simbolo s) {
		int hash = horner(s.getNome());
		
		//não há colisão
		if(lista[hash] == null) {
			lista[hash] = new NodeSimbolo(s);
		} else {
			
			NodeSimbolo atual = lista[hash];
			NodeSimbolo anterior = lista[hash];
			while(atual != null) {
				String nomeAtual = atual.valor.getNome();
				if(nomeAtual.equals(s.getNome())) {
					//já existe na tabela
					return -1;
				}
				anterior = atual;
				atual = atual.next;
			} 
			//não existe tal símbolo, então podemos incluí-lo
			anterior.next = new NodeSimbolo(s);
			return hash;
		}
		return hash;
	}
	
	public int remover(Simbolo s) {
		int hash = horner(s.getNome());
		if(lista[hash] == null) {
			return -1;
		}
		//existe apenas um item nessa posição, podemos anula-la...
		//se for o símbolo que procuramos
		if(lista[hash].next == null) {
			if(s.getNome().equals(lista[hash].valor.getNome())) {
				lista[hash] = null;
				return hash;
			}
			//não existe tal símbolo
			return -1;
		} else {
			//existe mais de um item na posição
			//se for o primeiro, basta mover o segundo item para a primeira posição
			if(s.getNome().equals(lista[hash].valor.getNome())) {
				lista[hash] = lista[hash].next;
				return hash;
			}
			
			//se não for o primeiro, então 
			NodeSimbolo atual = lista[hash].next;
			NodeSimbolo anterior = lista[hash];
			while(atual != null) {
				String nomeAtual = atual.valor.getNome();
				if(nomeAtual.equals(lista[hash].valor.getNome())) {
					anterior.next = anterior.next.next;
					atual = null;
					return hash;
				}
				anterior = atual;
				atual = atual.next;
			} 
			//não existe tal símbolo
			return -1;
		}
	}
	
	public Simbolo buscar(String nome) throws Exception {
		int hash = horner(nome);
		NodeSimbolo atual = lista[hash];
		while (atual != null) {
			if(atual.valor.getNome().equals(nome)) {
				return lista[hash].valor;
			}
			atual = atual.next;
		}
		throw new Exception("Símbolo " + nome + " não encontrado!");
	}
	
	//recebe um símbolo que não vai de fato ser colocado no hashmap,
	//mas vai fornecer dados que serão inseridos no símbolo atual com o mesmo nome.
	public int atualizar(Simbolo s) {
		int hash = horner(s.getNome());
		Simbolo antigo;
		try {
			antigo = buscar(s.getNome());
		} catch (Exception e) {
			//não encontrado para ser atualizado
			return -1;
		}
		antigo.setCategoria(s.getCategoria());
		antigo.setGeralA(s.getGeralA());
		antigo.setGeralB(s.getGeralB());
		antigo.setNivel(s.getNivel());
		return hash;
	}
	
	public int horner(String string) {
		if(string.length() == 0) {
			return 0;
		}
		char[] vetor = string.toCharArray();
		int indice = (int) vetor[vetor.length -1];
		for (int i = vetor.length - 2; i >= 0; i--) {
			indice = 37 * ((int) vetor[i] + indice);
			
		}
		return indice % getTamanho();
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	//retorna um número primo ligeiramente menor que o dobro do número solicitado
	//para resultar em uma tabela de hash com taxa de ocupação de até 50%
	//(se não houver colisão)
	//para isso, vamos implementar o Crivo de Eratóstenes
	public int tamanhoCerto(int numero) {
		//return numero*2; //TODO
		boolean[] composto = new boolean[numero*2]; //começa informando false pra todos os números
		for (int i = 2; i < numero * 2; i++) {
			if (!composto[i]) {
				for (int j = i*i; j > 0 && j < numero * 2; j+=i) {
					composto[j] = true;
				}
			}
		}
		for(int i = numero*2 - 1; i > 0; i--) {
			if(!composto[i]) {
				return i;
			}
		}
		//nunca vai acontecer, já que sempre haverá um 
		return numero*2 - 1;
	}
}
