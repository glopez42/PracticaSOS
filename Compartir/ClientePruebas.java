import java.net.URI;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import javax.ws.rs.core.Response;

public class ClientePruebas {

	private static void userToString(Usuario user) {
		System.out.println("+Nickname --> " + user.getNickname());
		System.out.println("\t -Nombre:  " + user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
	}

	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		Scanner scan = new Scanner(System.in);

		while (true) {
			int op = scan.nextInt();
			String aux;
			String path;
			switch (op) {
			// Añadir usuario a la red---------------------
			case 1:
				Usuario user = new Usuario();
				System.out.println("Introduzca primero su 'NickName' por favor:");
				aux = scan.nextLine();
				user.setNickname(aux);
				System.out.println("Introduzca su 'Nombre' por favor:");
				aux = scan.nextLine();
				user.setNombre(aux);
				System.out.println("Introduzca su 'Primer Apellido' por favor:");
				aux = scan.nextLine();
				user.setApellido1(aux);
				System.out.println("Por ultimo introduzca su 'Segundo Apellido' por favor:");
				aux = scan.nextLine();
				user.setApellido2(aux);
				// Response-----------------------------
				Response r1 = target.path("api/usuarios").request().accept(MediaType.APPLICATION_XML)
						.post(Entity.xml(user), Response.class);
				System.out.println("El estado de la respuesta es --> " + r1.getStatus());
				break;
			// Obtener todos los usuarios de la red---------------------------
			case 2:
				System.out.println("Esta es la lista de todos los usuarios de la red: ");
				// Response-----------------------------
				UsuarioList list = target.path("api/usuarios").request().accept(MediaType.APPLICATION_XML)
						.get(UsuarioList.class);

				for (int i = 0; i < list.getLista().size(); i++)
					userToString(list.getLista().get(i));
				break;
			// Obtener datos de un usuario-----------------------------
			case 3:
				System.out.println("Introduzca el 'NickName' del usuario por favor:");
				aux = scan.nextLine();
				// Response-----------------------------
				Usuario user1 = target.path("api/usuarios/" + aux).request().accept(MediaType.APPLICATION_XML)
						.get(Usuario.class);

				userToString(user1);
				break;
			// Modificar un usuario de la red-----------------------------
			case 4:
				System.out.println("Introduzca el 'NickName' del usuario que quiere modificar por favor:");
				String nickname = scan.nextLine();
				Usuario newUser = new Usuario();
				newUser.setNickname(nickname);
				System.out.println("A continuacion introduzca los valores que se le piden para modificarlos");
				System.out.println("Nuevo Nombre:");
				aux = scan.nextLine();
				newUser.setNombre(aux);
				System.out.println("Nuevo Primer Apellido:");
				aux = scan.nextLine();
				newUser.setApellido1(aux);
				System.out.println("Nuevo Segundo Apellido:");
				aux = scan.nextLine();
				newUser.setApellido2(aux);
				// Response-----------------------------
				Response r2 = target.path("api/usuarios/" + nickname).request().accept(MediaType.APPLICATION_XML)
						.put(Entity.xml(newUser), Response.class);
				System.out.println("El estado de la respuesta es --> " + r2.getStatus());
				break;
			// Borrar un usuario de la red-----------------------------
			case 5:
				System.out.println("Introduzca el 'NickName' del usuario que quiere eliminar por favor:");
				aux = scan.nextLine();
				// Response-----------------------------
				Response r3 = target.path("api/usuarios/" + aux).request().accept(MediaType.APPLICATION_XML)
						.delete(Response.class);
				System.out.println("El estado de la respuesta es --> " + r3.getStatus());
				break;
			// Añadir la lectura de un libro por un usuario
			case 6:
				System.out.println("Introduzca el 'NickName' de su usuario por favor:");
				aux = scan.nextLine();
				path = "api/usuarios/" + aux + "/libros";
				System.out.println("Introduzca el 'ISBN' del libro por favor:");
				aux = scan.nextLine();
				Libro l = new Libro();
				l.setIsbn(aux);
				System.out.println("Introduzca el 'Calificación' del libro por favor:");
				System.out.println("(Recuerde que solo se puede calificar del 0 al 10)");
				int c1 = scan.nextInt();
				l.setCalificacion(c1);
				// Response-----------------------------
				Response r4 = target.path(path).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(l),
						Response.class);
				System.out.println("El estado de la respuesta es --> " + r4.getStatus());
				break;
			// Borrar una lectura de un usuario de la red-----------------------------
			case 7:
				System.out.println("Introduzca el 'NickName' de su usuario por favor:");
				aux = scan.nextLine();
				path = "api/usuarios/" + aux + "/libros/";
				System.out.println("Introduzca el 'ISBN' del libro que desea eliminar por favor:");
				aux = scan.nextLine();
				path = path + "" + aux;
				// Response-----------------------------
				Response r5 = target.path(path).request().accept(MediaType.APPLICATION_XML).delete(Response.class);
				System.out.println("El estado de la respuesta es --> " + r5.getStatus());
				break;
			// Añadir un amigo--------------------
			case 10:
				System.out.println("Introduzca el 'NickName' de su usuario por favor:");
				String nUser = scan.nextLine();
				path = "api/usuarios/" + nUser + "/amigos";
				System.out.println("Introduzca el 'NickName' de su amigo por favor:");
				aux = scan.nextLine();
				Usuario amigo = new Usuario();
				amigo.setNickname(aux);
				// Response---------------------------
				Response r6 = target.path(path).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(amigo),
						Response.class);
				System.out.println("El estado de la respuesta es --> " + r6.getStatus());
				break;
			// Obtener las ultimas lecturas los amigos del usuario-----------------------
			case 13:
				System.out.println("Introduzca el 'NickName' de su usuario por favor:");
				aux = scan.nextLine();
				System.out.println("La lista puede ser acotada, pudiendo elegir el rango (ej:2 hasta la 6)");
				System.out
						.println("Tambien se puede filtrar por fecha pudiendo ver solo los libros a partir de x fecha");
				System.out.println("Formato de rango es 'Start' siendo un numero hasta 'End' siendo otro numero mayor");
				System.out.println(
						"Formato de la fecha es el siguiente AAAA-MM-DD HH:MM:SS y valor por defecto 9999-01-01 00:00:00 ");
				System.out.println("Elige desde donde quieres empezar a ver la lista:");
				int start = scan.nextInt();
				System.out.println("Elige donde quieres que termine la lista:");
				int end = scan.nextInt();
				System.out.println("Escribe la fecha desde donde quieres obtener la lista");
				aux = scan.nextLine();
				path = "api/usuarios/" + aux + "/amigos/libros";
				//Response------------------------
				LibrosList ll = target.path(path).queryParam("start", start).queryParam("end", end)
						.queryParam("fecha", aux).request().accept(MediaType.APPLICATION_XML).get(LibrosList.class);
				for (int i = 0; i < ll.getLista().size(); i++)
					libroToString(ll.getLista().get(i));
				break;
			//Buscar las ultimas recomendaciones-----------------------
			case 14:
				System.out.println("Introduzca el 'NickName' de su usuario por favor:");
				aux = scan.nextLine();
				path = "api/usuarios/" + aux + "/amigos/recomendaciones";
				System.out.println("Para filtrar la lista de recomendaciones se pueden filtrar segun su calificacion o autor o por su genero,");
				System.out.println("estos filtros se podran combinar de una en una o combinando varias\n");
				System.out.println("--------------");
				int calif = 5;
				String autor = "";
				String genero = "";
				System.out.println("¿Desea filtrar por calificacion?");
				boolean c = scan.nextBoolean();
				System.out.println("¿Desea filtrar por autor?");
				boolean a = scan.nextBoolean();
				System.out.println("¿Desea filtrar por genero?");
				boolean g = scan.nextBoolean();
				if(c) {
					System.out.println("Indique que valor desea para la calificacion:");
					calif = scan.nextInt();
				}
				if(a) {
					System.out.println("Indique por cual autor desea filtrar:");
					autor = scan.nextLine();
				}
				if(g) {
					System.out.println("Indique por cual genero deasea filtrar");
					genero = scan.nextLine();
				}
				//Response-----------------------------
				LibrosList ll2 = target.path(path).queryParam("calificacion", calif).queryParam("autor", autor)
						.queryParam("genero", genero).request().accept(MediaType.APPLICATION_XML).get(LibrosList.class);
				for (int i = 0; i < ll2.getLista().size(); i++)
					libroToString(ll2.getLista().get(i));
				break;
			}
			
		}
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/PracticaSOS/").build();
	}

}
