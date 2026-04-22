PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS consulta;
DROP TABLE IF EXISTS medico;
DROP TABLE IF EXISTS paciente;
DROP TABLE IF EXISTS especialidade;

CREATE TABLE especialidade(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE medico(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL,
    crm VARCHAR(20) NOT NULL UNIQUE,
    especialidade_id INTEGER NOT NULL,
    FOREIGN KEY (especialidade_id) REFERENCES especialidade(id)
);

CREATE TABLE paciente(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento VARCHAR(10) NOT NULL
);

CREATE TABLE consulta(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    data_consulta VARCHAR(10) NOT NULL,
    hora_consulta VARCHAR(5) NOT NULL,
    medico_id INTEGER NOT NULL,
    paciente_id INTEGER NOT NULL,
    observacao VARCHAR(255),
    FOREIGN KEY (medico_id) REFERENCES medico(id),
    FOREIGN KEY (paciente_id) REFERENCES paciente(id)
);

INSERT INTO especialidade (nome) VALUES
('Cardiologia'),
('Pediatria'),
('Ortopedia');


INSERT INTO medico (nome, crm, especialidade_id) VALUES
('Dr. Daniel Notari', 'CRM123', 1),
('Dr. Alexandre Krohn', 'CRM456', 2),
('Dr. Andre Martinotto', 'CRM789', 3);

INSERT INTO paciente (nome, cpf, data_nascimento) VALUES
('Lucas Zorrer', '111.111.111-11', '30/06/2002'),
('Gustavo Quadri', '222.222.222-22', '18/12/2022'),
('Zezinho da Silva', '333.333.333-33', '11/09/2001');

INSERT INTO consulta (data_consulta, hora_consulta, medico_id, paciente_id, observacao) VALUES
('22/04/2026', '09:00', 1, 1, 'Consulta inicial cardiologia'),
('22/04/2026', '10:30', 2, 2, 'Consulta inicial pediatria'),
('22/04/2026', '14:00', 3, 3, 'Consulta inicial ortopedia');
