

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UsuarioList {
	
	private List<Usuario> l;

	public List<Usuario> getL() {
		return l;
	}

	public void setL(List<Usuario> l) {
		this.l = l;
	}

	public UsuarioList() {
		
	}
	
	

}
