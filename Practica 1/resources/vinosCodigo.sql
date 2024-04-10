DROP SCHEMA IF EXISTS vinos;
CREATE SCHEMA vinos; 
use vinos;

#CREATE USER 'vinosUser'@'localhost' IDENTIFIED BY 'vinosPass';
#GRANT ALL PRIVILEGES ON vinos.* TO 'vinosUser'@'localhost';
#FLUSH PRIVILEGES;

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
    id INT NOT NULL AUTO_INCREMENT UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    bodega VARCHAR(100) NOT NULL,
    agnada INT NOT NULL,
    denominacion VARCHAR(40) NOT NULL,
    tipo VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE vinos_usuarios (
	id_vinos_usuarios INT NOT NULL UNIQUE AUTO_INCREMENT,
	id_vino INT NOT NULL,
	id_usuario INT NOT NULL,
	puntuacion DOUBLE NOT NULL,
	PRIMARY KEY(id_vinos_usuarios),
	FOREIGN KEY(id_vino) REFERENCES vino(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(id_usuario) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tiposUva_Vino (
	id_tiposUva_Vino INT NOT NULL UNIQUE AUTO_INCREMENT,
	id_tipoUva INT NOT NULL,
	id_vino INT NOT NULL,
	PRIMARY KEY(id_tiposUva_Vino),
	FOREIGN KEY (id_tipoUva) REFERENCES tiposUva(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_vino) REFERENCES vino(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE seguir (
    id_seguimiento INT NOT NULL AUTO_INCREMENT UNIQUE,
    id_seguidor INT NOT NULL,
    id_seguido INT NOT NULL,
    PRIMARY KEY (id_seguimiento),
    FOREIGN KEY (id_seguidor) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_seguido) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO usuario (nombre, fechaNacimiento, email) VALUES
('Juan Perez', '1990-05-15', 'juan.perez@example.com'),
('María López', '1985-08-20', 'maria.lopez@example.com'),
('Pedro García', '1993-02-10', 'pedro.garcia@example.com'),
('Ana Martínez', '1988-11-25', 'ana.martinez@example.com'),
('Luis Rodríguez', '1995-04-30', 'luis.rodriguez@example.com'),
('Elena Sánchez', '1983-09-05', 'elena.sanchez@example.com'),
('Carlos Gómez', '1991-07-18', 'carlos.gomez@example.com'),
('Sofía Ruiz', '1997-12-12', 'sofia.ruiz@example.com'),
('Diego Fernández', '1980-03-08', 'diego.fernandez@example.com'),
('Laura González', '1987-06-22', 'laura.gonzalez@example.com');


INSERT INTO vino (nombre, bodega, agnada, denominacion, tipo) VALUES
('Vega Sicilia Único', 'Vega Sicilia', 2010, 'Ribera del Duero', 'Tinto'),
('Château Margaux', 'Château Margaux', 2015, 'Margaux', 'Tinto'),
('Domaine de la Romanée-Conti Romanée-Conti', 'Domaine de la Romanée-Conti', 2012, 'Romanée-Conti', 'Tinto'),
('Sassicaia', 'Tenuta San Guido', 2016, 'Bolgheri Sassicaia', 'Tinto'),
('Opus One', 'Opus One Winery', 2014, 'Napa Valley', 'Tinto'),
('Screaming Eagle Cabernet Sauvignon', 'Screaming Eagle Winery', 2010, 'Oakville', 'Tinto'),
('Penfolds Grange', 'Penfolds', 2013, 'South Australia', 'Tinto'),
('La Tâche', 'Domaine de la Romanée-Conti', 2015, 'La Tâche', 'Tinto'),
('Petrus', 'Château Petrus', 2011, 'Pomerol', 'Tinto'),
('Krug Clos dAmbonnay', 'Krug', 2000, 'Champagne', 'Champagne'),
('Château dYquem', 'Château dYquem', 2010, 'Sauternes', 'Blanco'),
('Dom Pérignon', 'Moët & Chandon', 2008, 'Champagne', 'Champagne'),
('Veuve Clicquot Ponsardin Brut Rosé', 'Veuve Clicquot Ponsardin', 2012, 'Champagne', 'Champagne'),
('Leroy Chambertin', 'Domaine Leroy', 2013, 'Chambertin', 'Tinto'),
('Sine Qua Non Grenache', 'Sine Qua Non', 2014, 'California', 'Tinto');


INSERT INTO tiposUva (nombre, porcentaje) VALUES
('Cabernet Sauvignon', 25.0),
('Merlot', 20.0),
('Chardonnay', 15.0),
('Syrah', 10.0),
('Sauvignon Blanc', 5.0),
('Malbec', 10.0),
('Pinot Noir', 15.0),
('Tempranillo', 20.0),
('Riesling', 10.0),
('Grenache', 15.0);


-- Relacionando el vino "Vega Sicilia Único" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(1, 1), -- Cabernet Sauvignon
(2, 1); -- Merlot

-- Relacionando el vino "Château Margaux" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(7, 2), -- Pinot Noir
(9, 2); -- Riesling

-- Relacionando el vino "Domaine de la Romanée-Conti Romanée-Conti" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(8, 3), -- Tempranillo
(10, 3); -- Grenache

-- Relacionando el vino "Sassicaia" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(1, 4), -- Cabernet Sauvignon
(3, 4); -- Chardonnay

-- Relacionando el vino "Opus One" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(1, 5), -- Cabernet Sauvignon
(4, 5); -- Syrah

-- Relacionando el vino "Screaming Eagle Cabernet Sauvignon" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(1, 6), -- Cabernet Sauvignon
(2, 6); -- Merlot

-- Relacionando el vino "Penfolds Grange" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(6, 7), -- Malbec
(8, 7); -- Tempranillo

-- Relacionando el vino "La Tâche" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(7, 8), -- Pinot Noir
(10, 8); -- Grenache

-- Relacionando el vino "Petrus" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(8, 9), -- Tempranillo
(10, 9); -- Grenache

-- Relacionando el vino "Krug Clos d’Ambonnay" con sus tipos de uva
INSERT INTO tiposUva_Vino (id_tipoUva, id_vino) VALUES
(9, 10); -- Riesling

