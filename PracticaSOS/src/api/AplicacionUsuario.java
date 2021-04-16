package api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "app")
public class AplicacionUsuario {
	
	private Usuario user;
	private Libro ultimaLectura;
	private int nAmigos;
	private Libro ultimaLecturaAmigos;
	
	@XmlElement(name = "usuario")
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	@XmlElement(name = "libro")
	public Libro getUltimaLectura() {
		return ultimaLectura;
	}
	
	public void setUltimaLectura(Libro ultimaLectura) {
		this.ultimaLectura = ultimaLectura;
	}
	
	public int getnAmigos() {
		return nAmigos;
	}
	public void setnAmigos(int nAmigos) {
		this.nAmigos = nAmigos;
	}
	
	@XmlElement(name = "libroAmigos")
	public Libro getUltimaLecturaAmigos() {
		return ultimaLecturaAmigos;
	}
	
	public void setUltimaLecturaAmigos(Libro ultimaLecturaAmigos) {
		this.ultimaLecturaAmigos = ultimaLecturaAmigos;
	}


}
