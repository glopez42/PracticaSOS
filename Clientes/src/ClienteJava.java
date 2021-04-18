import java.net.URI;
import java.util.Scanner;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class ClienteJava {

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/PracticaSOS/").build();
	}

	private static void libroToString(Libro l) {
		System.out.println("+Libro --> " + l.getIsbn());
		System.out.println("\t Título --> " + l.getTitulo());
		System.out.println("\t Autor --> " + l.getAutor());
		System.out.println("\t Género --> " + l.getGeneroPrincipal());
		System.out.println("\t Editorial --> " + l.getEditorial());
		System.out.println("\t Calificacion --> " + l.getCalificacion());
		System.out.println();

	}

	private static void userToString(Usuario user) {
		System.out.println("+Nickname --> " + user.getNickname());
		System.out.println("\t -Nombre:  " + user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
		System.out.println();
	}
	
	private static String getUserFromUri(String uri) {
		String nickname = "";
		String base = "http://localhost:8080/PracticaSOS/api/usuarios/";
		int saltar = base.length();
		boolean done = false;
		for (int i = saltar; i < uri.length() && !done; i++) {
			nickname = nickname + uri.charAt(i);
			done = (uri.charAt(i + 1) == '/');
		}
				
		return nickname;
	}

	public static void main(String[] args) {

		// Para conectarse al proyecto web
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Scanner scan = new Scanner(System.in);
		int operacion;
		String nickname;
		String nicknameAmigo;
		String path;
		String isbn;
		String aux, fecha1, fecha2;
		int calificacion, start, end;
		Libro libro;
		Response res;
		LibrosList libros;
		UsuarioList users;
		boolean done = false;

		System.out.println("\t-Cliente Java-");

		// bucle principal
		while (!done) {
			System.out.println("------------------------------------");
			System.out.println("Operaciones disponibles: ");
			System.out.println();
			System.out.println("0) Desconectar.");
			System.out.println("1) Añadir un usuario a la red.");
			System.out.println("2) Obtener todos los usuarios de la red.");
			System.out.println("3) Obtener datos de un usuario.");
			System.out.println("4) Modificar datos de un usuario.");
			System.out.println("5) Borrar un usuario de la red.");
			System.out.println("6) Añadir la lectura de un libro por parte de un usuario.");
			System.out.println("7) Borrar la lectura de un libro por parte de un usuario.");
			System.out.println("8) Obtener todas las lecturas de un usuario.");
			System.out.println("9) Modificar una lectura de un usuario.");
			System.out.println("10) Añadir un usuario como amigo.");
			System.out.println("11) Borrar un amigo de un usuario.");
			System.out.println("12) Obtener los amigos de un usuario.");
			System.out.println("13) Obtener las últimas lecturas de los amigos de un usuario.");
			System.out.println("14) Buscar entre las últimas lecturas de los amigos de un usuario.");
			System.out.println("15) Realizar operaciones de la aplicación móvil.");
			System.out.println();
			System.out.print("Inserte el nº de operación -> ");

			operacion = scan.nextInt();

			switch (operacion) {
			// Añadir usuario a la red---------------------
			case 0:
				scan.close();
				done = true;
				System.out.println("Conexión cerrada.");
				break;
			case 1:
				Usuario user = new Usuario();
				System.out.println();
				System.out.println("Operación 1:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario:");
				aux = scan.next();
				scan.nextLine();
				user.setNickname(aux);
				System.out.println("Introduzca su Nombre por favor:");
				aux = scan.nextLine();
				;
				user.setNombre(aux);
				System.out.println("Introduzca su Primer Apellido por favor:");
				aux = scan.nextLine();
				user.setApellido1(aux);
				System.out.println("Por último introduzca su Segundo Apellido por favor:");
				aux = scan.nextLine();
				user.setApellido2(aux);
				// Response-----------------------------
				Response r1 = target.path("api/usuarios").request().accept(MediaType.APPLICATION_XML)
						.post(Entity.xml(user), Response.class);
				System.out.println("Estado de la respuesta es --> " + r1.getStatus());
				if (r1.getStatus() == 400) {
					System.out.println("Ya hay un usuario con ese nickname.");
				}
				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 2:
				System.out.println();
				System.out.println("Operación 2:");
				System.out.println();
				System.out.println("Esta es la lista de todos los usuarios de la red: ");
				// Response-----------------------------
				try {
					UsuarioList list = target.path("api/usuarios").request().accept(MediaType.APPLICATION_XML)
							.get(UsuarioList.class);

					for (int i = 0; i < list.getLista().size(); i++)
						userToString(list.getLista().get(i));

				} catch (InternalServerErrorException e) {
					System.out.println("Error en el servidor.");
				}

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 3:
				System.out.println();
				System.out.println("Operación 3:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario:");
				aux = scan.next();
				// Response-----------------------------
				try {
					Usuario user1 = target.path("api/usuarios/" + aux).request().accept(MediaType.APPLICATION_XML)
							.get(Usuario.class);
					userToString(user1);
				} catch (NotFoundException e) {
					System.out.println("Error, no encontrado.");
				} catch (InternalServerErrorException e) {
					System.out.println("Error en el servidor.");
				}

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 4:
				System.out.println();
				System.out.println("Operación 4:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario que quiera modificar:");
				nickname = scan.next();
				Usuario newUser = new Usuario();
				newUser.setNickname(nickname);
				System.out.println("A continuación, introduzca los valores actualizados");
				System.out.println("Nuevo Nombre:");
				aux = scan.next();
				newUser.setNombre(aux);
				System.out.println("Nuevo Primer Apellido:");
				aux = scan.next();
				newUser.setApellido1(aux);
				System.out.println("Nuevo Segundo Apellido:");
				aux = scan.next();
				newUser.setApellido2(aux);
				// Response-----------------------------
				Response r2 = target.path("api/usuarios/" + nickname).request().accept(MediaType.APPLICATION_XML)
						.put(Entity.xml(newUser), Response.class);
				System.out.println("El estado de la respuesta es --> " + r2.getStatus());
				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 5:
				System.out.println();
				System.out.println("Operación 5:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario que quiera elimiar:");
				aux = scan.next();
				// Response-----------------------------
				Response r3 = target.path("api/usuarios/" + aux).request().accept(MediaType.APPLICATION_XML)
						.delete(Response.class);
				System.out.println("El estado de la respuesta es --> " + r3.getStatus());
				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 6:
				System.out.println();
				System.out.println("Operación 6:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario:");
				aux = scan.next();
				path = "api/usuarios/" + aux + "/libros";
				System.out.println("Introduzca el 'ISBN' del libro por favor:");
				aux = scan.next();
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
				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 7:
				System.out.println();
				System.out.println("Operación 7:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario:");
				aux = scan.next();
				path = "api/usuarios/" + aux + "/libros/";
				System.out.println("Introduzca el 'ISBN' del libro que desea eliminar por favor:");
				aux = scan.next();
				path = path + "" + aux;
				// Response-----------------------------
				Response r5 = target.path(path).request().accept(MediaType.APPLICATION_XML).delete(Response.class);
				System.out.println("El estado de la respuesta es --> " + r5.getStatus());
				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 8:
				System.out.println();
				System.out.println("Operación 8:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				nickname = scan.next();
				System.out.println("Se permite limitar la cantidad de lecturas recibidas.");
				System.out.println("Por ejemplo, si selecciona 1 como comienzo y 10 como final,\n"
						+ "devolverá las 10 últimas lecturas.");
				System.out.println();
				System.out.println("Seleccione comienzo (-1 para dejar sin especificar): ");
				start = scan.nextInt();
				System.out.println("Seleccione final (-1 para dejar sin especificar): ");
				end = scan.nextInt();

				path = "api/usuarios/" + nickname + "/libros";

				libros = new LibrosList();

				try {
					// Obtenemos la lista
					if (start == -1 && end == -1)
						libros = target.path(path).request().get(LibrosList.class);
					else if (start == -1 && end != -1)
						libros = target.path(path).queryParam("end", end).request().get(LibrosList.class);
					else if (start != -1 && end == -1)
						libros = target.path(path).queryParam("start", start).request().get(LibrosList.class);
					else if (start != -1 && end != -1)
						libros = target.path(path).queryParam("start", start).queryParam("end", end).request()
								.get(LibrosList.class);

					System.out.println();
					System.out.println("Libros leídos por " + nickname + ": ");
					System.out.println();

					for (int i = 0; i < libros.getLista().size(); i++) {
						libroToString(libros.getLista().get(i));
					}

				} catch (NotFoundException e) {
					System.out.println("Error, no encontrado.");
				} catch (InternalServerErrorException e) {
					System.out.println("Error en el servidor.");
				}

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 9:
				System.out.println();
				System.out.println("Operación 9:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				nickname = scan.next();
				System.out.println("Seleccione el isbn del libro cuya lectura se va a modificar: ");
				isbn = scan.next();

				path = "api/usuarios/" + nickname + "/libros/" + isbn;

				System.out.println("Seleccione la nueva calificación: ");
				calificacion = scan.nextInt();

				libro = new Libro();
				libro.setIsbn(isbn);
				libro.setCalificacion(calificacion);

				res = target.path(path).request().put(Entity.xml(libro), Response.class);
				System.out.println("Estado de la respuesta --> " + res.getStatus());

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 10:
				System.out.println();
				System.out.println("Operación 10:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				String nUser = scan.next();
				path = "api/usuarios/" + nUser + "/amigos";
				System.out.println("Introduzca el 'NickName' de su amigo por favor:");
				aux = scan.next();
				Usuario amigo = new Usuario();
				amigo.setNickname(aux);
				// Response---------------------------
				Response r6 = target.path(path).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(amigo),
						Response.class);
				System.out.println("El estado de la respuesta es --> " + r6.getStatus());
				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 11:
				System.out.println();
				System.out.println("Operación 11:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				nickname = scan.next();
				System.out.println("Seleccione el nickname del amigo que desea eliminar: ");
				nicknameAmigo = scan.next();

				path = "api/usuarios/" + nickname + "/amigos/" + nicknameAmigo;

				res = target.path(path).request().delete(Response.class);
				System.out.println("Estado de la respuesta --> " + res.getStatus());

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 12:
				System.out.println();
				System.out.println("Operación 12:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				nickname = scan.next();

				path = "api/usuarios/" + nickname + "/amigos";
				users = new UsuarioList();

				System.out.println("¿Quiere filtrar entre los amigos por un patrón? (s/n)");
				String r = scan.next();

				if (r.compareTo("s") == 0) {
					System.out.println("Para filtrar por patrón use los siguientes formatos:");
					System.out.println(" símbolos + % + símbolos  ó  % + símbolos + %");
					System.out.println();
					System.out.println("Por ejemplo, dado un amigo pepe10, se obtendría al");
					System.out.println("escribir patrones como: pep% , %10, p%, %epe%, etc ");
					System.out.println("Siendo el símbolo '%' el resto del nickname.");
					System.out.println("Introduzca patrón: ");
					String patron = scan.next();
					try {

						users = target.path(path).queryParam("patron", patron).request().get(UsuarioList.class);
						
						System.out.println();
						System.out.println("Amigos de " + nickname + ":");
						for (int i = 0; i < users.getLista().size(); i++)
							userToString(users.getLista().get(i));

					} catch (NotFoundException e) {
						System.out.println("Error, no encontrado.");
					} catch (InternalServerErrorException e) {
						System.out.println("Error en el servidor.");
					}

					System.out.println();
					System.out.println("Fin de la operación");
					System.out.println();
					break;

				}

				System.out.println("Se permite limitar la cantidad de amigos recibidos.");
				System.out.println("Por ejemplo, si selecciona 1 como comienzo y 10 como final,\n"
						+ "devolverá los 10 últimos amigos.");

				System.out.println("Seleccione comienzo (-1 para dejar sin especificar): ");
				start = scan.nextInt();
				System.out.println("Seleccione final (-1 para dejar sin especificar): ");
				end = scan.nextInt();

				// Obtenemos la lista
				try {
					if (start == -1 && end == -1)
						users = target.path(path).request().get(UsuarioList.class);
					else if (start == -1 && end != -1)
						users = target.path(path).queryParam("end", end).request().get(UsuarioList.class);
					else if (start != -1 && end == -1)
						users = target.path(path).queryParam("start", start).request().get(UsuarioList.class);
					else if (start != -1 && end != -1)
						users = target.path(path).queryParam("start", start).queryParam("end", end).request()
								.get(UsuarioList.class);
					
					System.out.println();
					System.out.println("Amigos de " + nickname + ":");
					for (int i = 0; i < users.getLista().size(); i++)
						userToString(users.getLista().get(i));

				} catch (NotFoundException e) {
					System.out.println("Error, no encontrado.");
				} catch (InternalServerErrorException e) {
					System.out.println("Error en el servidor.");
				}

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 13:
				System.out.println();
				System.out.println("Operación 13:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				nickname = scan.next();
				System.out.println("La lista puede ser acotada, pudiendo elegir el rango (ej:2 hasta la 6).");
				System.out.println("Si se desea dejar sin especificar, introduzca -1.");
				System.out.println("Elija desde donde quiera empezar a ver la lista:");
				start = scan.nextInt();
				start = (start == -1) ? 0 : start;
				System.out.println("Elija donde quiera que termine la lista:");
				end = scan.nextInt();
				end = (end == -1) ? 10 : end;
				System.out.println("Se puede filtrar por fecha pudiendo ver solo los libros a partir de x fecha");
				System.out.println("Formato de la fecha: AAAA-MM-DD HH:MM:SS ");
				System.out.println("Si se desea dejar sin especificar, introduzca -1.");
				System.out.println("Escriba la fecha desde donde quiera obtener la lista");
				aux = scan.next();

				if (aux.compareTo("-1") != 0) {
					fecha1 = aux;
					fecha2 = scan.next();
					aux = fecha1 + " " + fecha2;
				} else {
					aux = "9999-01-01 00:00:00";
				}

				path = "api/usuarios/" + nickname + "/amigos/libros";
				// Response-----------------------
				try {
					LibrosList ll = target.path(path).queryParam("start", start).queryParam("end", end)
							.queryParam("fecha", aux).request().accept(MediaType.APPLICATION_XML).get(LibrosList.class);

					if (ll.getLista() != null) {
						for (int i = 0; i < ll.getLista().size(); i++) {
							System.out.println("Leído por " + getUserFromUri(ll.getLista().get(i).getUri() + "."));
							libroToString(ll.getLista().get(i));
						}
					}

				} catch (NotFoundException e) {
					System.out.println("Error, no encontrado.");
				} catch (InternalServerErrorException e) {
					System.out.println("Error en el servidor.");
				}
				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 14:
				System.out.println();
				System.out.println("Operación 14:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				aux = scan.next();
				path = "api/usuarios/" + aux + "/amigos/recomendaciones";
				System.out.println("Para filtrar la lista de recomendaciones se pueden filtrar según su calificación ,"
						+ "\nautor o por su género, estos filtros se podrán usar de uno en uno o combinando varias");
				System.out.println("Por defecto se muestran las lecturas con calificaciones > 5.");
				System.out.println("--------------");
				int calif = 5;
				String autor = "";
				String genero = "";
				System.out.println("¿Desea filtrar por calificación? (s/n)");
				boolean c = (scan.next()).compareTo("s") == 0;
				System.out.println("¿Desea filtrar por autor? (s/n)");
				boolean a = (scan.next()).compareTo("s") == 0;
				System.out.println("¿Desea filtrar por género? (s/n)");
				boolean g = (scan.next()).compareTo("s") == 0;
				scan.nextLine();
				if (c) {
					System.out.println("Indique que valor desea para la calificación:");
					calif = scan.nextInt();
					scan.nextLine();
				}
				if (a) {
					System.out.println("Indique el nombre del autor que desea filtrar:");
					autor = scan.nextLine();
				}
				if (g) {
					System.out.println("Indique el género por el que deasea filtrar");
					genero = scan.nextLine();
				}
				// Response-----------------------------
				try {
					LibrosList ll2 = target.path(path).queryParam("calificacion", calif).queryParam("autor", autor)
							.queryParam("genero", genero).request().accept(MediaType.APPLICATION_XML)
							.get(LibrosList.class);
					System.out.println();
					
					System.out.print("Mostrando recomendaciones ");
					if (c)
						System.out.print("con mayor calificación que " + calif);
					if (a)
						System.out.print(" del autor " + autor);
					if (c)
						System.out.print(" del género " + genero);
					
					System.out.println(".");
					System.out.println();
					
					for (int i = 0; i < ll2.getLista().size(); i++) {
						System.out.println("Leído por " + getUserFromUri(ll2.getLista().get(i).getUri() + "."));
						libroToString(ll2.getLista().get(i));
					}

				} catch (NotFoundException e) {
					System.out.println("Error, no encontrado.");
				} catch (InternalServerErrorException e) {
					System.out.println("Error en el servidor.");
				}

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			case 15:
				System.out.println();
				System.out.println("Operación 15:");
				System.out.println();
				System.out.println("Seleccione el nickname de usuario: ");
				nickname = scan.next();

				path = "api/usuarios/" + nickname + "/app";

				try {
					AplicacionUsuario app = target.path(path).request().get(AplicacionUsuario.class);

					if (app != null) {
						System.out.println();
						System.out.println("A continuación se muestra la información del usuario "
								+ app.getUser().getNickname() + ".");
						System.out.println();
						System.out.println("Datos del perfil: ");
						userToString(app.getUser());
						System.out.println();
						System.out.println("Último libro leído: ");
						Libro lUser = app.getUltimaLectura();
						if (lUser != null)
							libroToString(lUser);
						else
							System.out.println("Este usuario no ha leído ningún libro todavía.");

						System.out.println();
						System.out.println("Nº de amigos: " + app.getnAmigos());

						System.out.println();
						System.out.println("Último libro leído de amigos: ");
						Libro lAmigo = app.getUltimaLecturaAmigos();

						if (lAmigo != null) {
							System.out.println("Leído por " + getUserFromUri(lAmigo.getUri() + "."));
							libroToString(lAmigo);
						}
						else
							System.out.println("Sus amigos no han leido ningún libro todavía.");

					} else {
						System.out.println("No hay información que mostrar.");
					}

				} catch (NotFoundException e) {
					System.out.println("Error, no encontrado.");
				} catch (InternalServerErrorException e) {
					System.out.println("Error en el servidor.");
				}

				System.out.println();
				System.out.println("Fin de la operación");
				System.out.println();
				break;

			// caso erróneo
			default:
				System.out.println("Nº de operación incorrecta, operaciones disponibles: 1 - 15");
			}

		}

	}

}
