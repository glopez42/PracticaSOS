
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UsuarioList {
	
	private List<Usuario> lista;

	@XmlElement(name="usuario")
	public List<Usuario> getL() {
		return lista;
	}

	public void setL(List<Usuario> l) {
		this.lista = l;
	}

	public UsuarioList() {
		
	}
	

}

