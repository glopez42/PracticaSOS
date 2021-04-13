import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Calificacion {
	
	private int calificacion;

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	
	public Calificacion() {
		
	}
	
	public Calificacion(int n) {
		this.calificacion = n;
	}

}
