CREATE DATABASE IF NOT EXISTS gestionnominas;
USE gestionnominas;

-- Tabla de empleados (la del primer ejercicio de repaso)
CREATE TABLE IF NOT EXISTS empleados (
                                         NOMBRE    VARCHAR(255) NOT NULL,
    DNI       VARCHAR(9)   NOT NULL PRIMARY KEY,
    SEXO      CHAR(1)      NOT NULL,
    CATEGORIA INT(11)      DEFAULT 1,
    ANYOS     INT(11)      DEFAULT 0
    );

-- Tabla de nominas: guarda el sueldo calculado de cada empleado
CREATE TABLE IF NOT EXISTS nominas (
                                       DNI    VARCHAR(9) NOT NULL PRIMARY KEY,
    SUELDO DOUBLE     NOT NULL,
    CONSTRAINT fk_nominas_empleados FOREIGN KEY (DNI)
    REFERENCES empleados (DNI) ON DELETE CASCADE ON UPDATE CASCADE
    );

-- Empleados de prueba
INSERT INTO empleados (NOMBRE, DNI, SEXO, CATEGORIA, ANYOS) VALUES
('David',  '12345678Y', 'M', 1, 3),
('Dani', '00000000D', 'M', 7, 3),
('Garrido', '11111111G', 'M', 1, 2),
('Mama', '22222222X', 'F', 1, 2),
('Loli', '33333333L', 'F', 3, 5);


-- Sueldo de cada empleado: base de la categoría + 5000 por año trabajado
INSERT INTO nominas (DNI, SUELDO) VALUES
                                      ('12345678Y', 65000),
                                      ('00000000D', 185000),
                                      ('11111111G', 60000),
                                      ('22222222X', 60000),
                                      ('33333333L', 115000);