CREATE SCHEMA MiRedDeLibros;

USE MiRedDeLibros;

CREATE TABLE usuario (
	nickname VARCHAR(25) NOT NULL, #nickname como máximo de 25 caracteres
	nombre VARCHAR(25),
	apellido1 VARCHAR(25),
	apellido2 VARCHAR(25),
	uri VARCHAR(125),
	PRIMARY KEY(nickname)
);


CREATE TABLE libros (
	isbn VARCHAR(13) NOT NULL, #isbn de 13
	titulo VARCHAR(100),
	autor VARCHAR(100),
	generoPrincipal VARCHAR(25),
	generoSecundario VARCHAR(25),
	editorial VARCHAR(25),
	PRIMARY KEY(isbn)
);


CREATE TABLE amigos (
	nicknameUser VARCHAR(25) NOT NULL,
	nicknameAmigo VARCHAR(25) NOT NULL,
	PRIMARY KEY (nicknameUser, nicknameAmigo),
	FOREIGN KEY (nicknameUser) REFERENCES usuario(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (nicknameAmigo) REFERENCES usuario(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE lecturas (
	nickname VARCHAR(25) NOT NULL,
	isbn VARCHAR(13) NOT NULL,
	fecha DATETIME,
	calificacion INT,
	uri VARCHAR(125),
	PRIMARY KEY(nickname,isbn),
	FOREIGN KEY(nickname) REFERENCES usuario(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(isbn) REFERENCES libros(isbn)
		ON DELETE CASCADE ON UPDATE CASCADE
);


#Libros para la base de datos
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788496246175','El sabueso de los Baskerville','Arthur Conan Doyle','misterio','policiaca','El Pais');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788496246434','Estudio en escarlata','Arthur Conan Doyle','misterio','policiaca','El Pais');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788420683652','Apología de Sócrates','Platón','filosofía',NULL,'Alianza');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788435410992','Ética para Amador','Fernando Savater','filosofía',NULL,'Ariel');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788497931021','El misterio de Salems lot','Stephen King','terror','misterio','Best Seller');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788497593793','It','Stephen King','terror','misterio','Best Seller');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788466647304','Guía de los secretos de la T.I.A','Francisco Ibañez','humor','cómic','Ediciones B');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788417552183','Gravedad','Marcus Chown','divulgación','ciencia','Blackie Books');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9789700732954','Un mundo feliz','Aldous Huxley','ciencia ficción','ficción utópica','Porrúa');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788420464831','Los recreos del pequeño Nicolás','René Gosciny','humor',NULL,'Alfaguara');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788417347949','La era de la supernova','Cixin Liu','ciencia ficción','misterio','Nova');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788466659734','El problema de los tres cuerpos','Cixin Liu','ciencia ficción','misterio','Nova');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788496252905','Dinosaurios','David Burnie','divulgación','prehistoria','Edilupa');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('9788416880065','Un noruego en el camino de santiago','John Arne','humor',Null,'Astiberri');

#Creacion de usuarios
INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri)
VALUES ('glopezg', 'Guillermo', 'Lopez', 'Garcia', 'http://localhost:8080/PracticaSOS/api/usuarios/glopezg');
INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri)
VALUES ('hlopezv', 'Hector', 'Lopez', 'Valero', 'http://localhost:8080/PracticaSOS/api/usuarios/hlopezv');
INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri)
VALUES ('pepe10', 'Jose', 'Muñoz', 'Perez', 'http://localhost:8080/PracticaSOS/api/usuarios/pepe10');
INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri)
VALUES ('pepe14', 'Pepe', 'Torres', 'Cañadas', 'http://localhost:8080/PracticaSOS/api/usuarios/pepe14');
INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri)
VALUES ('readMaster', 'Julia', 'Galeano', 'Freijo', 'http://localhost:8080/PracticaSOS/api/usuarios/readMaster');
INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri)
VALUES ('bookDestroyer', 'Ana', 'Martin', 'Siguero', 'http://localhost:8080/PracticaSOS/api/usuarios/bookDestroyer');
INSERT INTO usuario(nickname,nombre,apellido1,apellido2,uri)
VALUES ('xXLector360Xx', 'Roberto', 'Monzon', 'Moya', 'http://localhost:8080/PracticaSOS/api/usuarios/xXLector360Xx');

#Creacion de amigos
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('hlopezv','glopezg');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('hlopezv','pepe10');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('hlopezv','pepe14');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('hlopezv','readMaster');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('hlopezv','xXLector360Xx');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('hlopezv','bookDestroyer');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('glopezg','hlopezv');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('glopezg','readMaster');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('glopezg','bookDestroyer');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('glopezg','pepe10');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('pepe10','pepe14');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('pepe10','hlopezv');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('pepe10','glopezg');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('pepe10','readMaster');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('pepe14','hlopezv');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('pepe14','glopezg');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('pepe14','readMaster');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('readMaster','glopezg');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('readMaster','pepe10');
INSERT INTO amigos(nicknameUser,nicknameAmigo) VALUES ('xXLector360Xx','hlopezv');

#Creacion de lecturas
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('glopezg','9788420683652','2021-04-17 06:00:00',4,'http://localhost:8080/PracticaSOS/api/usuarios/glopezg/libros/9788420683652');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('glopezg','9788497931021','2021-04-16 09:00:00',6,'http://localhost:8080/PracticaSOS/api/usuarios/glopezg/libros/9788497931021');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('glopezg','9788496246175','2021-04-18 14:00:00',9,'http://localhost:8080/PracticaSOS/api/usuarios/glopezg/libros/9788496246175');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('glopezg','9788497593793','2021-04-10 18:00:00',8,'http://localhost:8080/PracticaSOS/api/usuarios/glopezg/libros/9788497593793');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('glopezg','9788466659734','2021-04-08 16:00:00',10,'http://localhost:8080/PracticaSOS/api/usuarios/glopezg/libros/9788466659734');

INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('readMaster','9788420683652','2021-04-18 18:00:00',6,'http://localhost:8080/PracticaSOS/api/usuarios/readMaster/libros/9788420683652');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('readMaster','9789700732954','2021-04-16 09:00:00',3,'http://localhost:8080/PracticaSOS/api/usuarios/readMaster/libros/9789700732954');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('readMaster','9788416880065','2021-04-06 16:00:00',7,'http://localhost:8080/PracticaSOS/api/usuarios/readMaster/libros/9788416880065');

INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('xXLector360Xx','9788420683652','2021-04-08 12:00:00',1,'http://localhost:8080/PracticaSOS/api/usuarios/xXLector360Xx/libros/9788420683652');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('xXLector360Xx','9788466659734','2021-04-10 19:00:00',9,'http://localhost:8080/PracticaSOS/api/usuarios/xXLector360Xx/libros/9788466659734');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('xXLector360Xx','9788416880065','2021-04-16 09:00:00',5,'http://localhost:8080/PracticaSOS/api/usuarios/xXLector360Xx/libros/9788416880065');

INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('hlopezv','9788416880065','2021-04-06 22:00:00',3,'http://localhost:8080/PracticaSOS/api/usuarios/hlopezv/libros/9788416880065');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('hlopezv','9788417552183','2021-04-08 17:00:00',10,'http://localhost:8080/PracticaSOS/api/usuarios/hlopezv/libros/9788417552183');

INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('pepe10','9788496246434','2021-04-18 14:00:00',9,'http://localhost:8080/PracticaSOS/api/usuarios/pepe10/libros/9788496246434');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('pepe10','9788496246175','2021-04-10 21:00:00',10,'http://localhost:8080/PracticaSOS/api/usuarios/pepe10/libros/9788496246175');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('pepe10','9788420464831','2021-04-16 09:00:00',4,'http://localhost:8080/PracticaSOS/api/usuarios/pepe10/libros/9788420464831');

INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('pepe14','9788496246434','2021-04-06 12:00:00',9,'http://localhost:8080/PracticaSOS/api/usuarios/pepe14/libros/9788496246434');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('pepe14','9788496246175','2021-04-04 16:00:00',10,'http://localhost:8080/PracticaSOS/api/usuarios/pepe14/libros/9788496246175');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('pepe14','9788420464831','2021-04-18 17:00:00',4,'http://localhost:8080/PracticaSOS/api/usuarios/pepe14/libros/9788420464831');

INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('bookDestroyer','9788435410992','2021-04-04 13:00:00',5,'http://localhost:8080/PracticaSOS/api/usuarios/bookDestroyer/libros/9788435410992');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('bookDestroyer','9788497593793','2021-04-08 11:00:00',7,'http://localhost:8080/PracticaSOS/api/usuarios/bookDestroyer/libros/9788497593793');
INSERT INTO lecturas(nickname,isbn,fecha,calificacion,uri)
VALUES ('bookDestroyer','9788417347949','2021-04-10 08:00:00',8,'http://localhost:8080/PracticaSOS/api/usuarios/bookDestroyer/libros/9788417347949');

















