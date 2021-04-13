

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibrosList {
	
	private List<Libro> l;
	
	@XmlElement(name="libro")
	public List<Libro> getL() {
		return l;
	}

	public void setL(List<Libro> l) {
		this.l = l;
	}

	public LibrosList() {
		
	}
	
	
}
