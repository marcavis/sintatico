package sintatico;

public class NodeSimbolo {
	public Simbolo valor;
	public NodeSimbolo next;
	
	public NodeSimbolo(Simbolo v) {
		this.valor = v;
	}
	
	public void adicionaProximo(Simbolo s2) {
		if (next == null) {
			next = new NodeSimbolo(s2);
		} else {
			next.adicionaProximo(s2);
		}
	}
}