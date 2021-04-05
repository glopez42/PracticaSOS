package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
	
	@POST
	@Path("usuarios")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createUser(Usuario user) {
		try {
			
			int affectedRows = gestor.insertUser(user);
			if (affectedRows > 0) {
				String location = uriInfo.getAbsolutePath() + "/" + user.getNickname();
				System.out.println(location);
				return Response.status(Status.CREATED).header("Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
			
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario\n" + e.getStackTrace()).build();
		}
	}
	
	
	@GET
	@Path("usuarios")
	@Produces(MediaType.APPLICATION_XML)
	public Response getUsers() {
		UsuarioList list = new UsuarioList();
		try {
			list.setL(gestor.getUsers());
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return Response.ok(list).build();
	}

}
