package api;

import java.sql.SQLException;
import java.util.ArrayList;

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

@Path("/")
public class Recursos {

	@Context
	private UriInfo uriInfo;
	private GestorBBDD gestor;

	public Recursos() {
		gestor = new GestorBBDD();
	}

	// Creación de un usuario
	@POST
	@Path("usuarios")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createUser(Usuario user) {
		try {
			// llamamos al gestor para que inserte el usuario
			String location = uriInfo.getAbsolutePath() + "/" + user.getNickname();
			user.setUri(location);
			gestor.insertUser(user);

			// si sale bien se devuelve CREATED + Location
			return Response.status(Status.CREATED).header("Location", location).build();

		} catch (SQLException e) {
			// si hay algun error con la BBDD es que la petición está mal hecha (usuario ya
			// existe, por ejemplo).
			if (e.getErrorCode() == 1062)
				return Response.status(Response.Status.BAD_REQUEST).entity("Nickname ya existente.").build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	// Obtención de todos los usuarios de la red
	@GET
	@Path("usuarios")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUsers() {
		UsuarioList list = new UsuarioList();
		try {
			list.setLista(gestor.getUsers());
			return Response.ok(list).build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		}
	}

	// Obtención de los datos del usuario indicado(nickname)
	@GET
	@Path("usuarios/{nickname}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUserData(@PathParam("nickname") String n) {
		try {
			Usuario user = gestor.getUserData(n);
			return Response.ok(user).build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		}
	}

	// Actualizar el usuario en la base de datos
	@PUT
	@Path("usuarios/{nickname}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUserData(@PathParam("nickname") String n, Usuario newUser) {
		try {
			if (n.compareTo(newUser.getNickname()) == 0) {
				gestor.updateUser(newUser);
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity("Error, el Nickname tiene que ser el mismo")
						.build();
			}
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		}
	}

	// Eliminar el usuario de la base de datos
	@DELETE
	@Path("usuarios/{nickname}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteUser(@PathParam("nickname") String n) {
		try {
			gestor.deleteUser(n);
			return Response.ok().build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		}
	}

	// Añadir libro a tabla lecturas
	@POST
	@Path("usuarios/{nickname}/libros")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addLectura(@PathParam("nickname") String n, Libro l) {
		int calificacion = l.getCalificacion();
		try {
			if (0 <= calificacion && calificacion <= 10) {
				String location = uriInfo.getAbsolutePath() + "/" + l.getIsbn();
				gestor.addLectura(n, l.getIsbn(), calificacion, location);
				// si sale bien se devuelve CREATED + Location
				return Response.status(Status.CREATED).header("Location", location).build();
			} else {
				// Calificacion Erronea
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Error, el rango de la calificacion es de 0-10").build();
			}
		} catch (SQLException e) {
			// si hay algun error con la BBDD es que la petición está mal hecha (usuario ya
			// existe, por ejemplo).
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (BookNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el libro
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el libro indicado")
					.build();
		}
	}

	// Eliminar la lectura de la base de datos
	@DELETE
	@Path("usuarios/{nickname}/libros/{isbn}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteLectura(@PathParam("nickname") String n, @PathParam("isbn") String isbn) {
		try {

			gestor.deleteLectura(n, isbn);
			return Response.ok().build();

		} catch (SQLException e) {
			// si hay algun error con la BBDD es que la petición está mal hecha (usuario ya
			// existe, por ejemplo).
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (BookNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el libro
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el libro indicado")
					.build();
		}
	}

	// Obtención los ultimos libros leidos
	@GET
	@Path("usuarios/{nickname}/libros")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUltimasLecturas(@QueryParam("start") @DefaultValue("0") int start,
			@QueryParam("end") @DefaultValue("10") int end, @PathParam("nickname") String n) {
		LibrosList list = new LibrosList();
		try {
			list.setLista(gestor.getUltimasLecturas(n, start, end));
			return Response.ok(list).build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (BookNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el libro
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el libro indicado")
					.build();
		}

	}

	// Actualizar una lectura en la base de datos
	@PUT
	@Path("usuarios/{nickname}/libros/{isbn}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateLectura(@PathParam("nickname") String nickname, @PathParam("isbn") String isbn,
			Libro newLibro) {
		try {
			if (isbn.compareTo(newLibro.getIsbn()) == 0) {
				gestor.updateLibro(nickname, newLibro);
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity("Error, el ISBN tiene que ser el mismo")
						.build();
			}
		} catch (BookNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el libro
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el libro indicado")
					.build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		}
	}

	// Añadir un amigo
	@POST
	@Path("usuarios/{nickname}/amigos")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addAmigo(@PathParam("nickname") String n, Usuario amigo) {
		try {
			gestor.addAmigo(n, amigo.getNickname());
			String location = uriInfo.getAbsolutePath() + "/" + amigo.getNickname();
			return Response.status(Status.CREATED).header("Location", location).build();
		} catch (SQLException e) {
			// si hay algun error con la BBDD es que la petición está mal hecha (usuario ya
			// existe, por ejemplo).
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		}
	}

	// Eliminar un amigo de la lista del usuario
	@DELETE
	@Path("usuarios/{nickname}/amigos/{nicknameAmigo}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteAmigo(@PathParam("nickname") String n, @PathParam("nicknameAmigo") String nA) {
		try {
			gestor.deleteAmigo(n, nA);
			return Response.ok().build();
		} catch (SQLException e) {
			// si hay algun error con la BBDD es que la petición está mal hecha (usuario ya
			// existe, por ejemplo).
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		}
	}

	// Obtención de una lista de amigos
	@GET
	@Path("usuarios/{nickname}/amigos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getListaAmigos(@QueryParam("start") @DefaultValue("0") int start,
			@QueryParam("end") @DefaultValue("10") int end, @QueryParam("patron") @DefaultValue("") String patron,
			@PathParam("nickname") String n) {
		UsuarioList list = new UsuarioList();
		try {
			if (patron.compareTo("") != 0)
				list.setLista(gestor.getListaAmigos(n, patron));
			else
				list.setLista(gestor.getListaAmigos(n, start, end));

			return Response.ok(list).build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			System.out.println(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		}
	}

	// Obtención de una lista de lecturas de los amigos del usuario
	@GET
	@Path("usuarios/{nickname}/amigos/libros")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getLecturasAmigos(@PathParam("nickname") String n,
			@QueryParam("start") @DefaultValue("0") int start, @QueryParam("end") @DefaultValue("10") int end,
			@QueryParam("fecha") @DefaultValue("9999-01-01 00:00:00") String fecha) {
		LibrosList list = new LibrosList();
		try {
			list.setLista(gestor.getLecturasAmigos(n, start, end, fecha));
			return Response.ok(list).build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (BookNotFoundException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		}
	}

	// Obtención de las recomendaciones de amigos
	@GET
	@Path("usuarios/{nickname}/amigos/recomendaciones")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getRecomendacionesAmigos(@PathParam("nickname") String n,
			@QueryParam("calificacion") @DefaultValue("5") int c, @QueryParam("autor") @DefaultValue("") String autor,
			@QueryParam("genero") @DefaultValue("") String genero) {
		LibrosList list = new LibrosList();
		try {
			if (c >= 0 && c <= 10) {
				list.setLista(gestor.getRecomendacionesAmigos(n, autor, genero, c));
				return Response.ok(list).build();
			} else {
				// Calificacion rango Erronea
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Error, el rango de la calificacion es de 0-10 " + c).build();
			}
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (BookNotFoundException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		}
	}

	// Obtención de los datos de la app movil
	@GET
	@Path("usuarios/{nickname}/app")
	@Produces(MediaType.APPLICATION_XML)
	public Response getApp(@PathParam("nickname") String n) {
		try {
			AplicacionUsuario aU = new AplicacionUsuario();
			ArrayList<Libro> l1, l2;
			aU.setUser(gestor.getUserData(n));
			if ((l1 = gestor.getUltimasLecturas(n, 0, 0)).size() > 0)
				aU.setUltimaLectura(l1.get(0));
			aU.setnAmigos(gestor.getNumAmigos(n));
			if ((l2 = gestor.getLecturasAmigos(n, 0, 0, "9999-01-01 00:00:00")).size() > 0)
				aU.setUltimaLecturaAmigos(l2.get(0));
			return Response.ok(aU).build();
		} catch (SQLException e) {
			// si hay algun error significa que hay algún error con el servidor de la BBDD
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
		} catch (UserNotFoundException e) {
			// si hay algun error significa que no se ha encontrado el Usuario
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el usuario indicado")
					.build();
		} catch (BookNotFoundException e) { // si hay algun error significa que no se ha encontrado el libro
			return Response.status(Response.Status.NOT_FOUND).entity("Error, no se ha encontrado el libro indicado")
					.build();
		}
	}
}
