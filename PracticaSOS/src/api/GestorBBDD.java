package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GestorBBDD {

	private Connection conn;

	// método para conectarse a la base de datos, se llama al principio de cada
	// método, pero si ya se ha establezido conexión anteriormente, no hace nada
	public boolean connect() {

		String serverAddress = "localhost:3306";
		String db = "MiRedDeLibros";
		String us = "root";
		String pass = "restuser";
		String url = "jdbc:mysql://" + serverAddress + "/" + db;

		try {
			if (this.conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				// el atributo conn ya no será null
				this.conn = DriverManager.getConnection(url, us, pass);

			}
			return true;
		} catch (SQLException e) {
			System.out.println("Fallo al conectar con la base de datos: " + e.getMessage());
			return false;
		} catch (ClassNotFoundException e) {
			System.out.println("Fallo al cargar el driver: " + e.getMessage());
			return false;
		}
	}

	public boolean disconnect() {

		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Fallo al cerrar la conexión");
			return false;
		}
	}

	// método que inserta un usuario
	public int insertUser(Usuario user) throws SQLException {
		connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri) VALUES (?,?,?,?,?);";
		ps = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getNickname());
		ps.setString(2, user.getNombre());
		ps.setString(3, user.getApellido1());
		ps.setString(4, user.getApellido2());
		ps.setString(5, user.getUri());
		int affectedRows = ps.executeUpdate();
		rs = ps.getGeneratedKeys();

		ps.close();
		rs.close();
		return affectedRows;

	}

	// método que devuelve en un array list todos los usuarios de la red
	public ArrayList<Usuario> getUsers() throws SQLException {
		connect();

		Statement st = null;
		ResultSet rs = null;
		ArrayList<Usuario> list = new ArrayList<Usuario>();

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM usuario");
			while (rs.next()) {
				Usuario u = new Usuario();
				u.setNickname(rs.getString("nickname"));
				u.setNombre(rs.getString("nombre"));
				u.setApellido1(rs.getString("apellido1"));
				u.setApellido2(rs.getString("apellido2"));
				u.setUri(rs.getString("uri"));
				list.add(u);
			}
		} catch (SQLException e) {

		}

		st.close();
		rs.close();
		return list;
	}

	// método que devuelve un usuario de la red
	public Usuario getUserData(String nickname) throws SQLException, UserNotFoundException {
		connect();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM usuario WHERE nickname = (?) ;";

		ps = conn.prepareStatement(query);
		ps.setNString(1, nickname);
		rs = ps.executeQuery();
		Usuario u = new Usuario();

		if (rs.next()) {

			u.setNickname(rs.getString("nickname"));
			u.setNombre(rs.getString("nombre"));
			u.setApellido1(rs.getString("apellido1"));
			u.setApellido2(rs.getString("apellido2"));
			u.setUri(rs.getString("uri"));

		} else {
			throw new UserNotFoundException();
		}

		ps.close();
		rs.close();

		return u;
	}

	// método que devuelve un libro de la red
	public Libro getBookData(String isbn) throws SQLException, BookNotFoundException {
		connect();

		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM libros WHERE isbn = (?) ;";

		ps = conn.prepareStatement(query);
		ps.setNString(1, isbn);
		rs = ps.executeQuery();
		Libro l = new Libro();

		if (rs.next()) {
			l.setAutor(rs.getString("autor"));
			l.setTitulo(rs.getString("titulo"));
			l.setIsbn(rs.getString("isbn"));
			l.setGeneroPrincipal(rs.getString("generoPrincipal"));
			l.setGeneroSecundario(rs.getString("generoSecundario"));
			l.setEditorial(rs.getString("editorial"));

		} else {
			throw new BookNotFoundException();
		}

		ps.close();
		rs.close();

		return l;
	}

	// método que actualiza un usuario de la red
	public boolean updateUser(Usuario user) throws SQLException, UserNotFoundException {
		connect();

		// comprobamos que exista el usuario
		this.getUserData(user.getNickname());

		PreparedStatement ps = null;
		int affectedRows = 0;
		// No se puede modificar el nickname
		String query = "UPDATE usuario SET nombre = ?, apellido1 = ? , apellido2 = ? WHERE nickname = ? ;";

		ps = conn.prepareStatement(query);
		ps.setString(1, user.getNombre());
		ps.setString(2, user.getApellido1());
		ps.setString(3, user.getApellido2());
		ps.setString(4, user.getNickname());
		affectedRows = ps.executeUpdate();

		ps.close();
		return (affectedRows > 0);
	}

	// método que borra un usuario de la red
	public boolean deleteUser(String nickname) throws SQLException, UserNotFoundException {
		connect();

		PreparedStatement ps = null;
		int affectedRows = 0;
		String query = "DELETE FROM usuario WHERE nickname = ? ;";

		ps = conn.prepareStatement(query);
		ps.setNString(1, nickname);
		affectedRows = ps.executeUpdate();

		if (affectedRows <= 0) {
			throw new UserNotFoundException();
		}

		ps.close();
		return (affectedRows > 0);
	}

	// método que inserta una lectura con calificacion de un usuario
	public int addLectura(String nickname, String isbn, int calificacion, String uri)
			throws SQLException, UserNotFoundException, BookNotFoundException {
		connect();
		PreparedStatement ps = null;

		// comprobamos que exista el usuario
		this.getUserData(nickname);
		// comprobamos que existe el libro
		this.getBookData(isbn);

		String query = "INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri) VALUES(?,?,NOW(),?,?)";

		ps = this.conn.prepareStatement(query);
		ps.setString(1, nickname);
		ps.setString(2, isbn);
		ps.setInt(3, calificacion);
		ps.setString(4, uri);
		int affectedRows = ps.executeUpdate();

		ps.close();
		return affectedRows;
	}

	// método que borra una lectura por parte de un usuario de la red
	public boolean deleteLectura(String nickname, String isbn)
			throws SQLException, UserNotFoundException, BookNotFoundException {
		connect();

		// comprobamos que exista el usuario
		this.getUserData(nickname);
		// comprobamos que existe el libro
		this.getBookData(isbn);

		PreparedStatement ps = null;
		int affectedRows = 0;
		String query = "DELETE FROM lecturas WHERE nickname = ?  AND isbn = ? ;";

		ps = conn.prepareStatement(query);
		ps.setNString(1, nickname);
		ps.setNString(2, isbn);
		affectedRows = ps.executeUpdate();

		ps.close();
		return (affectedRows > 0);
	}

	// método que devuelve en un array list todos los libros leidos por un usuario
	public ArrayList<Libro> getUltimasLecturas(String nickname, int start, int end)
			throws SQLException, UserNotFoundException, BookNotFoundException {

		connect();
		// comprobamos que exista el usuario
		this.getUserData(nickname);

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Libro> list = new ArrayList<Libro>();
		String isbn = null;
		Libro l = null;

		String query = "SELECT * FROM lecturas WHERE nickname = ? ORDER BY fecha DESC ;";
		ps = conn.prepareStatement(query);
		ps.setString(1, nickname);
		rs = ps.executeQuery();

		// saltamos los libros que nos indica start
		for (int i = 1; i < start && rs.next(); i++) {
		}

		int contador = start;
		while (rs.next() && contador <= end) {
			isbn = rs.getString("isbn");
			l = this.getBookData(isbn);
			l.setUri(rs.getString("uri"));
			l.setCalificacion(rs.getInt("calificacion"));
			list.add(l);
			contador++;
		}

		ps.close();
		rs.close();
		return list;
	}

	// método que actualiza una lectura de la red
	public boolean updateLibro(String nickname, Libro libro) throws SQLException, BookNotFoundException, UserNotFoundException {
		connect();
		
		// comprobamos que exista el usuario
		this.getUserData(nickname);
		// comprobamos que existe el libro
		this.getBookData(libro.getIsbn());
		PreparedStatement ps = null;
		int affectedRows = 0;
		
		String query = "UPDATE lecturas SET calificacion = ? WHERE isbn = ? ;";

		ps = conn.prepareStatement(query);
		ps.setInt(1, libro.getCalificacion());
		ps.setString(2, libro.getIsbn());
		affectedRows = ps.executeUpdate();

		ps.close();
		return (affectedRows > 0);
	}

	// método que permite a un usuario añadir un amigo
	public boolean addAmigo(String user, String amigo) throws SQLException, UserNotFoundException {
		connect();
		// comprobamos que existe el usuario
		this.getUserData(user);
		// comprobamos que existe el amigo
		this.getUserData(amigo);

		PreparedStatement ps = null;
		String query = "INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ( ?, ?) ;";
		ps = conn.prepareStatement(query);
		ps.setString(1, user);
		ps.setString(2, amigo);
		int affectedRows = ps.executeUpdate();

		ps.close();
		return (affectedRows > 0);
	}

	// método que borra un amigo de un usuario dado
	public boolean deleteAmigo(String user, String amigo) throws SQLException, UserNotFoundException {
		connect();

		// comprobamos que existe el usuario
		this.getUserData(user);
		// comprobamos que existe el amigo
		this.getUserData(amigo);

		PreparedStatement ps = null;
		int affectedRows = 0;
		String query = "DELETE FROM amigos WHERE nicknameUser = ?  AND nicknameAmigo = ? ;";

		ps = conn.prepareStatement(query);
		ps.setNString(1, user);
		ps.setNString(2, amigo);
		affectedRows = ps.executeUpdate();

		ps.close();
		return (affectedRows > 0);
	}

	// método que devuelve en un array list todos los amigos de un usuario
	public ArrayList<Usuario> getListaAmigos(String nickname, int start, int end)
			throws SQLException, UserNotFoundException {

		connect();
		// comprobamos que exista el usuario
		this.getUserData(nickname);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Usuario> list = new ArrayList<Usuario>();
		String n = null;
		Usuario user = null;

		String query = "SELECT * FROM amigos WHERE nicknameUser = ? ";
		ps = conn.prepareStatement(query);
		ps.setString(1, nickname);
		rs = ps.executeQuery();

		// saltamos los libros que nos indica start
		for (int i = 1; i < start && rs.next(); i++) {
		}

		int contador = start;
		while (rs.next() && contador <= end) {
			n = rs.getString("nicknameAmigo");
			user = this.getUserData(n);
			list.add(user);
			contador++;
		}

		ps.close();
		rs.close();
		return list;
	}

	// método que devuelve en un array list todos los amigos de un usuario
	// diferenciando por un patrón
	public ArrayList<Usuario> getListaAmigos(String nickname, String pattern)
			throws SQLException, UserNotFoundException {

		connect();
		// comprobamos que exista el usuario
		this.getUserData(nickname);

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Usuario> list = new ArrayList<Usuario>();
		String n = null;
		Usuario user = null;

		String query = "SELECT * FROM amigos WHERE nicknameUser = ? AND nicknameAmigo LIKE ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, nickname);
		ps.setString(2, pattern);
		rs = ps.executeQuery();

		while (rs.next()) {
			n = rs.getString("nicknameAmigo");
			user = this.getUserData(n);
			list.add(user);
		}

		ps.close();
		rs.close();
		return list;
	}

	// método que devuelve un array con las últimas lecturas de los amigos de un
	// usuario, pudiendo
	// filtrar por fecha y limitando la cantidad de lecturas devueltas
	public ArrayList<Libro> getLecturasAmigos(String nickname, int start, int end, String fecha)
			throws SQLException, UserNotFoundException, BookNotFoundException {

		connect();
		// comprobamos que exista el usuario
		this.getUserData(nickname);

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Libro> list = new ArrayList<Libro>();
		String isbn = null;
		Libro l = null;

		String query = "SELECT * FROM lecturas "
				+ "WHERE nickname IN ( SELECT nicknameAmigo FROM amigos WHERE nicknameUser = ?) " + "AND fecha < ? "
				+ "ORDER BY fecha DESC;";

		ps = conn.prepareStatement(query);
		ps.setString(1, nickname);
		ps.setString(2, fecha);
		rs = ps.executeQuery();

		while (rs.next()) {
			isbn = rs.getString("isbn");
			l = this.getBookData(isbn);
			l.setUri(rs.getString("uri"));
			l.setCalificacion(rs.getInt("calificacion"));
			list.add(l);
		}

		ps.close();
		rs.close();
		return list;
	}

	// método que devuelve un array con las recomendaciones de los amigos de un
	// usuario, pudiend filtrar por autor, género y calificacion
	public ArrayList<Libro> getRecomendacionesAmigos(String nickname, String autor, String genero, int calificacion)
			throws SQLException, UserNotFoundException, BookNotFoundException {

		connect();
		// comprobamos que exista el usuario
		this.getUserData(nickname);

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Libro> list = new ArrayList<Libro>();
		String isbn = null;
		Libro l = null;
		boolean cond1, cond2, cond3;
		int qMark = 2;

		String query = "SELECT * FROM lecturas,libros "
				+ "WHERE nickname IN (SELECT nicknameAmigo FROM amigos WHERE nicknameUser = ? ) "
				+ "AND lecturas.isbn = libros.isbn ";

		if (cond1 = autor.compareTo("") != 0) {
			query = query + "AND  libros.autor = ? ";
		}

		if (cond2 = genero.compareTo("") != 0) {
			query = query + " AND libros.generoPrincipal = ? ";
		}

		if (cond3 = calificacion >= 0) {
			query = query + "AND calificacion > ? ";
		}

		query = query + " ; ";

		ps = conn.prepareStatement(query);
		ps.setString(1, nickname);

		if (cond1) {
			ps.setString(qMark, autor);
			qMark++;
		}

		if (cond2) {
			ps.setString(qMark, genero);
			qMark++;
		}

		if (cond3) {
			ps.setInt(qMark, calificacion);
			qMark++;
		}

		rs = ps.executeQuery();

		while (rs.next()) {
			isbn = rs.getString("isbn");
			l = this.getBookData(isbn);
			l.setUri(rs.getString("uri"));
			l.setCalificacion(rs.getInt("calificacion"));
			list.add(l);
		}

		ps.close();
		rs.close();
		return list;
	}

}
