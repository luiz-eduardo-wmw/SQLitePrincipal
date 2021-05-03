package domain;

public class Sorvete {

	public int codigo;
	public String sabor;
	public double valorUnidade;
	public int estoqueAtivo;
	
	@Override
	public String toString() {
		return "[" + sabor + "]";
	}
	
	
	
}
