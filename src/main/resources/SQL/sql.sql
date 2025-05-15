-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS inmobiliaria;
USE inmobiliaria;

-- Crear tabla zonas
CREATE TABLE zonas (
                       idZona INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(100) NOT NULL
);

-- Crear tabla pisos
CREATE TABLE pisos (
                       idPiso INT AUTO_INCREMENT PRIMARY KEY,
                       direccion VARCHAR(255) NOT NULL,
                       idZona INT,
                       FOREIGN KEY (idZona) REFERENCES zonas(idZona)
);

-- Insertar datos en zonas
INSERT INTO zonas (nombre) VALUES
                               ('Centro'),
                               ('Parquesol');

-- Insertar datos en pisos (relacionados con las zonas)
INSERT INTO pisos (direccion, idZona) VALUES
                                          ('Calle Santiago 12, Valladolid', 1),  -- Centro
                                          ('Avenida Salamanca 45, Valladolid', 2);  -- Parquesol
