package api;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.apache.naming.NamingContext;

@Path("/")
public class Recursos {

	@Context
	private UriInfo uriInfo;
	private GestorBBDD gestor;

	public Recursos() {
		gestor = new GestorBBDD();
	}

	//Creación de un usuario
	@POST
	@Path("usuarios")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createUser(Usuario user) {
		try {
			//llamamos al gestor para que inserte el usuario
			gestor.insertUser(user);
			String location = uriInfo.getAbsolutePath() + "/" + user.getNickname();
			//si sale bien se devuelve CREATED + Location
			return Response.status(Status.CREATED).header("Location", location).build();

		} catch (SQLException e) {
			//si hay algun error con la BBDD es que la petición está mal hecha (usuario ya existe, por ejemplo).
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	//Obtención de todos los usuarios de la red
	@GET
	@Path("usuarios")
	@Produces(MediaType.APPLICATION_XML)
	public Response getUsers() {
		UsuarioList list = new UsuarioList();
		try {
			list.setL(gestor.getUsers());
			return Response.ok(list).build();
		} catch (SQLException e) {
			//si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		}

		
	}

}
