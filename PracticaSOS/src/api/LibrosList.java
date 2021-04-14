package api;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibrosList {
	
	private List<Libro> lista;
	
	@XmlElement(name="libro")
	public List<Libro> getL() {
		return lista;
	}

	public void setL(List<Libro> l) {
		this.lista = l;
	}

	public LibrosList() {
		
	}
	
	
}
