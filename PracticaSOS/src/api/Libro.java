package api;

public class Libro {
	
	private String isbn;
	private String titulo;
	private String autor;
	private String generoPrincipal;
	private String generoSecundario;
	private String editorial;
	
	public Libro() {
		
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getGeneroPrincipal() {
		return generoPrincipal;
	}


	public void setGeneroPrincipal(String generoPrincipal) {
		this.generoPrincipal = generoPrincipal;
	}


	public String getGeneroSecundario() {
		return generoSecundario;
	}


	public void setGeneroSecundario(String generoSecunadrio) {
		this.generoSecundario = generoSecunadrio;
	}


	public String getEditorial() {
		return editorial;
	}


	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	

}
