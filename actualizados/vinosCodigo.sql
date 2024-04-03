DROP SCHEMA IF EXISTS vinos;
CREATE SCHEMA vinos; 
use vinos;



CREATE TABLE usuario (
    id INT NOT NULL AUTO_INCREMENT UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    fechaNacimiento VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE tiposUva (
	id INT NOT NULL AUTO_INCREMENT UNIQUE,
	nombre VARCHAR(50) NOT NULL,
	porcentaje DOUBLE NOT NULL CHECK (porcentaje >= 0 AND porcentaje <= 100),
	PRIMARY KEY (id)
);


CREATE TABLE vino (
    id_vino INT NOT NULL AUTO_INCREMENT UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    bodega VARCHAR(25) NOT NULL,
    agnada INT NOT NULL,
    denominacion VARCHAR(40) NOT NULL,
    tipo VARCHAR(25) NOT NULL,
    puntuacion DOUBLE NOT NULL CHECK (puntuacion >= 0 AND puntuacion <=10),
    PRIMARY KEY (id_vino)
);

CREATE TABLE vinos_usuarios (
	id_vinos_usuarios INT NOT NULL UNIQUE AUTO_INCREMENT,
	id_vino INT NOT NULL,
	id_usuario INT NOT NULL,
	PRIMARY KEY(id_vinos_usuarios),
	FOREIGN KEY(id_vino) REFERENCES vino(id_vino) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(id_usuario) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tiposUva_Vino (
	id_tiposUva_Vino INT NOT NULL UNIQUE AUTO_INCREMENT,
	id_tipoUva INT NOT NULL,
	id_vino INT NOT NULL,
	PRIMARY KEY(id_tiposUva_Vino),
	FOREIGN KEY (id_tipoUva) REFERENCES tiposUva(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_vino) REFERENCES vino(id_vino) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE seguimiento (
    id_seguimiento INT NOT NULL AUTO_INCREMENT UNIQUE,
    seguidor_id INT NOT NULL,
    usuario_seguido_id INT NOT NULL,
    PRIMARY KEY (id_seguimiento),
    FOREIGN KEY (seguidor_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (usuario_seguido_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO usuario(nombre, fechaNacimiento, email) VALUES ('Pablo','2002-01-19','pablo@example.es');
INSERT INTO usuario(nombre, fechaNacimiento, email) VALUES ('JD','2002-04-17','jd@example.es');
INSERT INTO usuario(nombre, fechaNacimiento, email) VALUES ('Antonio','2002-12-22','antonio@example.es');

INSERT INTO vino(nombre,bodega,agnada,denominacion,tipo,puntuacion) VALUES ('Ramon Bilbao','La Bodega Los Charitos',2003,'La Rioja','Tinto',0);
INSERT INTO vino(nombre,bodega,agnada,denominacion,tipo,puntuacion) VALUES ('Grillo','La Bodega Los Parrales',2019,'Jerez','Tinto',0);
INSERT INTO vino(nombre,bodega,agnada,denominacion,tipo,puntuacion) VALUES ('Pepito','La Bodega Los Menores',1990,'Bilbao','Tinto',0);
INSERT INTO vino(nombre,bodega,agnada,denominacion,tipo,puntuacion) VALUES ('Librito','La Bodega Los Autenticos',2005,'Zaragoza','Blanco',0);


INSERT INTO tiposUva(nombre,porcentaje) VALUES ('Chardonnay',55);
INSERT INTO tiposUva(nombre,porcentaje) VALUES ('Merlot',45);
INSERT INTO tiposUva(nombre,porcentaje) VALUES ('Cabernet Sauvignon',27);
INSERT INTO tiposUva(nombre,porcentaje) VALUES ('Uva marron',73);


INSERT INTO tiposUva_Vino(id_tipoUva,id_vino) VALUES (1,1);
INSERT INTO tiposUva_Vino(id_tipoUva,id_vino) VALUES (2,1);
INSERT INTO tiposUva_Vino(id_tipoUva,id_vino) VALUES (1,2);
INSERT INTO tiposUva_Vino(id_tipoUva,id_vino) VALUES (3,2);







