package mx.unam.dgtic.punto_de_venta.util;

public class PageItem {
	private int numero;
	private boolean actual;
	public PageItem(int numero, boolean actual) {		
		this.numero = numero;
		this.actual = actual;
	}
	public int getNumero() {
		return numero;
	}
	public boolean isActual() {
		return actual;
	}
}
