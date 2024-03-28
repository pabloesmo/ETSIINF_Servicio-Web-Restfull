CREATE TABLE usuario (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    fechaNacimiento VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE vino (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    bodega VARCHAR(25) NOT NULL,
    agnada INT NOT NULL,
    denominacion VARCHAR(40) NOT NULL,
    tipo VARCHAR(25) NOT NULL,
    tiposUva VARCHAR(25) NOT NULL,
    puntuacion INT NOT NULL,
    PRIMARY KEY(id)
);


CREATE TABLE listaSeguidores (
	id_seguidor INT NOT NULL AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    nombre_usuario VARCHAR(50) NOT NULL,
    fechaNacimiento_usuario VARCHAR(30) NOT NULL,
    email_usuario VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_seguidor),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (nombre_usuario) REFERENCES usuario(nombre),
    FOREIGN KEY (fechaNacimiento_usuario) REFERENCES usuario(fechaNacimiento),
    FOREIGN KEY (email_usuario) REFERENCES usuario(email)
);

CREATE TABLE listaVinos (
	id_vino INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id_vino),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (nombre_vino) REFERENCES vino(nombre),
    FOREIGN KEY (bodega_vino) REFERENCES vino(bodega),
    FOREIGN KEY (agnada_vino) REFERENCES vino(agnada),
    FOREIGN KEY (denominacion_vino) REFERENCES vino(denominacion),
    FOREIGN KEY (tipo_vino) REFERENCES vino(tipo),
    FOREIGN KEY (tiposUva_vino) REFERENCES vino(tiposUva),
    FOREIGN KEY (puntuacion_vino) REFERENCES vino(puntuacion)
);


