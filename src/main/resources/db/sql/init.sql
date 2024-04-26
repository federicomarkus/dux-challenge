-- USUARIO

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO usuario (usuario, password) VALUES ('test', '12345');


-- EQUIPO

CREATE TABLE equipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    liga VARCHAR(50),
    pais VARCHAR(50)
);

INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Real Madrid', 'La Liga', 'España');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('FC Barcelona', 'La Liga', 'España');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Manchester United', 'Premier League', 'Inglaterra');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Liverpool FC', 'Premier League', 'Inglaterra');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Juventus FC', 'Serie A', 'Italia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('AC Milan', 'Serie A', 'Italia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Bayern Munich', 'Bundesliga', 'Alemania');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Borussia Dortmund', 'Bundesliga', 'Alemania');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Paris Saint-Germain', 'Ligue 1', 'Francia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Olympique de Marseille', 'Ligue 1', 'Francia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('FC Porto', 'Primeira Liga', 'Portugal');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Sporting CP', 'Primeira Liga', 'Portugal');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Ajax Amsterdam', 'Eredivisie', 'Países Bajos');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Feyenoord', 'Eredivisie', 'Países Bajos');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Celtic FC', 'Scottish Premiership', 'Escocia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Rangers FC', 'Scottish Premiership', 'Escocia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Galatasaray SK', 'Süper Lig', 'Turquía');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Fenerbahçe SK', 'Süper Lig', 'Turquía');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('FC Zenit Saint Petersburg', 'Premier League Rusa', 'Rusia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Spartak Moscow', 'Premier League Rusa', 'Rusia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('SL Benfica', 'Primeira Liga', 'Portugal');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Besiktas JK', 'Süper Lig', 'Turquía');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('SSC Napoli', 'Serie A', 'Italia');
INSERT INTO equipo (id, nombre, liga, pais) VALUES ('Atlético Madrid', 'La Liga', 'España');

CREATE INDEX equipo_nombre_index ON equipo (nombre);
