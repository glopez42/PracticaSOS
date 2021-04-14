import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import javax.ws.rs.core.Response;

public class ClientePruebas {

	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Usuario user = new Usuario();
		user.setNickname("pepe1");
		user.setNombre("Jose");
		user.setApellido1("Uzurriaga");
		user.setApellido2("Gartxiburu");

		Usuario user2 = new Usuario();
		user2.setNickname("pepe2");
		user2.setNombre("Jose");
		user2.setApellido1("Uzurriaga");
		user2.setApellido2("Gartxiburu");

		Usuario user3 = new Usuario();
		user3.setNickname("pepe3");
		user3.setNombre("Jose");
		user3.setApellido1("Uzurriaga");
		user3.setApellido2("Gartxiburu");

//		Response res = target.path("api/usuarios").request().post(Entity.xml(user), Response.class);
//		System.out.println(res.getStatus());
//		Response res1 = target.path("api/usuarios").request().post(Entity.xml(user2), Response.class);
//		System.out.println(res1.getStatus());
//		Response res2 = target.path("api/usuarios").request().post(Entity.xml(user3), Response.class);
//		System.out.println(res2.getStatus());
		
//		Response res = target.path("api/usuarios/pepe2/amigos").request().post(Entity.xml(user), Response.class);
//		System.out.println(res.getStatus());
//		Response res1 = target.path("api/usuarios/pepe2/amigos").request().post(Entity.xml(user3), Response.class);
//		System.out.println(res1.getStatus());

		//
		// UsuarioList list =
		// target.path("api/usuarios").request().accept(MediaType.APPLICATION_XML).get(UsuarioList.class);
		//
		// for (int i = 0; i < list.getL().size(); i++) {
		// System.out.println(list.getL().get(i).getNickname());
		// }

//		 Calificacion c = new Calificacion(10);
//		 Response u =
//		 target.path("api/usuarios/pepe1/libros/9788416880065").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u.getStatus());
//		 Response u1 =
//		 target.path("api/usuarios/pepe1/libros/9788420683652").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u1.getStatus());
//		 Response u2 =
//		 target.path("api/usuarios/pepe1/libros/9788435410992").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u2.getStatus());
//		 Response u3 =
//		 target.path("api/usuarios/pepe2/libros/9788497931021").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u3.getStatus());
//		 Response u4 =
//		 target.path("api/usuarios/pepe2/libros/9788497593793").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u4.getStatus());
//		 Response u5 =
//		 target.path("api/usuarios/pepe3/libros/9788417552183").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u5.getStatus());
//		 Response u6 =
//		 target.path("api/usuarios/pepe3/libros/9789700732954").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u6.getStatus());
//		 Response u7 =
//		 target.path("api/usuarios/pepe3/libros/9788420464831").request().post(Entity.xml(c),Response.class);
//		 System.out.println(u7.getStatus());

		// LibrosList list =
		// target.path("api/usuarios/peppe/libros").queryParam("start",
		// 1).queryParam("end", 8).request()
		// .accept(MediaType.APPLICATION_XML).get(LibrosList.class);
		//
		// for (int i = 0; i < list.getL().size(); i++) {
		// System.out.println(list.getL().get(i).getTitulo());
		// }

		// Libro l = new Libro();
		// l.setAutor("Hitler");
		// l.setTitulo("Mein Kampf");
		// l.setEditorial("SS editorial");
		// l.setGeneroPrincipal("Comedia");
		// l.setGeneroSecundario("Filosofía");
		// l.setIsbn("9788420464831");
		// Response u1 =
		// target.path("api/libros/9788420464831").request().put(Entity.xml(l),
		// Response.class);
		// System.out.println(u1.getStatus());

	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/PracticaSOS/").build();
	}

}
