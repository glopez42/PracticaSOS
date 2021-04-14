DROP SCHEMA MiRedDeLibros;
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


INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('0008496246175','El sabueso de los Baskerville','Arthur Conan Doyle','misterio','policiaca','El Pais');
INSERT INTO libros(isbn,titulo,autor,generoPrincipal,generoSecundario,editorial) 
VALUES ('0008496246434','Estudio en escarlata','Arthur Conan Doyle','misterio','policiaca','El Pais');
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

INSERT INTO lecturas(nickname,isbn,fecha,calificacion) VALUES('peppe','9788416880065',NOW(),2);
INSERT INTO lecturas(nickname,isbn,fecha,calificacion) VALUES('peppe','9788420464831',NOW(),3);
INSERT INTO usuario(nickname,nombre,apellido1,apellido2) VALUES('peppe','Jose','Uzualgo','Utsinoseque');

INSERT INTO lecturas(nickname,isbn,fecha,calificacion) VALUES('peppe','9789700732954',NOW(),10);

SELECT * FROM lecturas,libros WHERE nickname IN (SELECT nicknameAmigo FROM amigos WHERE nicknameUser = 'pepe2') AND lecturas.isbn = libros.isbn;

SELECT * FROM usuario;
SELECT * FROM lecturas;
SELECT * FROM amigos WHERE nicknameUser = 'hlopezv' AND nicknameAmigo LIKE 'p%';
