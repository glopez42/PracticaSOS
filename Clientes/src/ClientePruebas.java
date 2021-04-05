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
		
		Usuario user = new Usuario("asdasdsad", "Jose", "Uzurriaga", "Gartxiburu");
		
		Response res = target.path("api/usuarios").request().post(Entity.xml(user),Response.class);
		
		System.out.println(res.getStatus());
		
//		UsuarioList list = target.path("api/usuarios").request().accept(MediaType.APPLICATION_XML).get(UsuarioList.class);
//		
//		for (int i = 0; i < list.getL().size(); i++) {
//			System.out.println(list.getL().get(i).getNickname());
//		}
		
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/PracticaSOS/").build();
	}
	
}
