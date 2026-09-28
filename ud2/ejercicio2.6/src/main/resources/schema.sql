CREATE TABLE IF NOT EXISTS empleados (
    id INTEGER PRIMARY KEY,
    nombre_completo VARCHAR(120) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL CHECK (salario >= 0)
);
