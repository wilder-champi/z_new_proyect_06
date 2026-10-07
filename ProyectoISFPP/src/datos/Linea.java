package datos;

import java.util.List;

public class Linea {

	private int numero; //usa este atirbuto para guardar el numero del linea que se esta leyendo
	private List<String> campos;//se usa para guardar los atributos o partes de cada linea 

	public Linea(int numero, List<String> campos) {
		this.numero = numero;
		this.campos = campos;
	}

	public int getNumero() {
		return numero;
	}

	public List<String> getCampos() {
		return campos;
	}
}