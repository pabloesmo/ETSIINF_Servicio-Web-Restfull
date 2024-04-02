CREATE USER 'vinosUser'@'localhost' IDENTIFIED BY 'vinosPass';
GRANT ALL PRIVILEGES ON vinos.* TO 'vinosUser'@'localhost';
FLUSH PRIVILEGES;

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


CREATE TABLE vino (
    id_vino INT NOT NULL AUTO_INCREMENT UNIQUE,
    id_usuario INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    bodega VARCHAR(25) NOT NULL,
    agnada INT NOT NULL,
    denominacion VARCHAR(40) NOT NULL,
    tipo VARCHAR(25) NOT NULL,
    tiposUva VARCHAR(200) NOT NULL,
    puntuacion INT NOT NULL,
    PRIMARY KEY (id_vino),
    FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);

CREATE TABLE seguimiento (
    id_seguimiento INT NOT NULL AUTO_INCREMENT UNIQUE,
    seguidor_id INT NOT NULL,
    usuario_seguido_id INT NOT NULL,
    PRIMARY KEY (id_seguimiento),
    FOREIGN KEY (seguidor_id) REFERENCES usuario(id),
    FOREIGN KEY (usuario_seguido_id) REFERENCES usuario(id)
);