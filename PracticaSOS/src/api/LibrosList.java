package api;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibrosList {
	
	private List<Libro> l;

	public List<Libro> getL() {
		return l;
	}

	public void setL(List<Libro> l) {
		this.l = l;
	}

	public LibrosList() {
		
	}
	
	
}
