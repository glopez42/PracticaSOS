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
	//método para conectarse a la base de datos, se llama al principio de cada
	//método, pero si ya se ha establezido conexión anteriormente, no hace nada
	public boolean connect() {

		String serverAddress = "localhost:3306";
		String db = "MiRedDeLibros";
		String us = "root";
		String pass = "restuser";
		String url = "jdbc:mysql://" + serverAddress + "/" + db;

		try {
			if (this.conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				//el atributo conn ya no será null
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
	
	//método que inserta un usuario
	public int insertUser(Usuario user) throws SQLException {
		connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "INSERT INTO usuario(nickname,nombre,apellido1,apellido2) " + "VALUES ('" + user.getNickname()
				+ "','" + user.getNombre() + "','" + user.getApellido1() + "','" + user.getApellido2() + "');";

		ps = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int affectedRows = ps.executeUpdate();
		rs = ps.getGeneratedKeys();

		ps.close();
		rs.close();
		return affectedRows;
	}
	
	//método que devuelve en un array list todos los usuarios de la red
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

				list.add(u);
			}
		} catch (SQLException e) {

		}

		st.close();
		rs.close();
		return list;
	}

}

