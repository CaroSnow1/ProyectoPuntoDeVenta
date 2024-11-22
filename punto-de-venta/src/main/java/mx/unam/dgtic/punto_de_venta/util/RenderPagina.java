package mx.unam.dgtic.punto_de_venta.util;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class RenderPagina<T> {
	private String url;
	private Page<T> page;
	private int totalPaginas;
	private int paginaActual;
	private List<PageItem> paginas;

	public RenderPagina(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<>();
		this.totalPaginas = page.getTotalPages();
		this.paginaActual = page.getNumber() + 1;

		int desde, hasta;
		int elementosPorPagina = Math.min(totalPaginas, 5); // Mostrar un máximo de 5 páginas

		if (totalPaginas <= elementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (paginaActual <= elementosPorPagina / 2) {
				desde = 1;
				hasta = elementosPorPagina;
			} else if (paginaActual >= totalPaginas - elementosPorPagina / 2) {
				desde = totalPaginas - elementosPorPagina + 1;
				hasta = totalPaginas;
			} else {
				desde = paginaActual - elementosPorPagina / 2;
				hasta = paginaActual + elementosPorPagina / 2;
			}
		}

		for (int i = 0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}
	}

	public String getUrl() {
		return url;
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public int getPaginaActual() {
		return paginaActual;
	}
	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	public boolean isLast() {
		return page.isLast();
	}
	public boolean isHasNext() {
		return page.hasNext();
	}
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}

}
