INSERT IGNORE INTO fabricadosaber.transaction VALUES (1, 'PAYROLL', '2023-01-01', 'Cartao', 'INPUT', 100);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (2, 'INFRASTRUCTURE_EXPENSE', '2023-02-15', 'Dinheiro', 'OUTPUT', 200);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (3, 'INSTITUTIONAL_MARKETING', '2023-03-20', 'Cheque', 'INPUT', 150);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (4, 'EDUCATIONAL_PROJECTS', '2023-04-05', 'Transferência Bancária', 'OUTPUT', 120);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (5, 'ADMINISTRATIVE_COSTS', '2023-05-10', 'Boleto', 'INPUT', 180);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (6, 'SCHOOL_EVENTS', '2023-06-15', 'Pix', 'OUTPUT', 90);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (7, 'MAINTENANCE_SERVICES', '2023-07-20', 'Cartão de Crédito', 'INPUT', 250);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (8, 'EDUCATIONAL_MATERIAL', '2023-08-25', 'TED', 'OUTPUT', 300);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (9, 'PAYROLL', '2023-09-30', 'Dinheiro', 'INPUT', 180);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (10, 'INFRASTRUCTURE_EXPENSE', '2023-10-05', 'Transferência Bancária', 'OUTPUT', 120);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (11, 'INSTITUTIONAL_MARKETING', '2023-11-10', 'Boleto', 'INPUT', 220);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (12, 'EDUCATIONAL_PROJECTS', '2023-12-15', 'Pix', 'OUTPUT', 130);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (13, 'ADMINISTRATIVE_COSTS', '2023-12-31', 'Cartão de Débito', 'INPUT', 190);
INSERT IGNORE INTO fabricadosaber.transaction VALUES (14, 'SCHOOL_EVENTS', '2023-11-25', 'Transferência Bancária', 'OUTPUT', 160);

--        senha utilizadas na encriptacao: admin123456 e user123456
INSERT IGNORE INTO fabricadosaber.user VALUES (1, '2023-11-25', "admin@email.com", "administrator", "$2a$10$MPwDDc6gsgGHpc.rQ8Cea.Q/3evYk/tzBq7nUEkZIU./VxCYcNufW" );
INSERT IGNORE INTO fabricadosaber.user_profile VALUES (1, 1);
INSERT IGNORE INTO fabricadosaber.user VALUES (2, '2023-11-25', "user@email.com", "usuario", "$2a$10$Tbbo8EMq1C9Ou5jpet4qd.Pr1AhxZXmAPYEwChM3MgIbcxp7ULTfS" );
INSERT IGNORE INTO fabricadosaber.user_profile VALUES (2, 1);

-- INSERT IGNORE INTO fabricadosaber.teacher VALUES (1, 'Apto 803', 100, '1990-12-31', 'Belo Horizonte', '104.258.222-20', 'professor01@email.com', 'Professor 01', 'MINAS_GERAIS', 'Bairro teste', '73 12345-2541', '2023-11-30', '66.847.333', 'Rua teste', '12345-678', '2022-11-30', 1500, '2023-11-30', 'Eng de software', 'CURSANDO');
-- INSERT IGNORE INTO fabricadosaber.teacher VALUES (2, 'Apt 301', 101, '1985-08-15', 'Salvador', '123.456.789-00', 'teacher02@email.com', 'Professor 02', 'BAHIA', 'Centro', '71 98765-4321', '2023-11-29', '55.123.987', 'Avenida Principal', '45678-901', '2022-10-31', 2000, '2023-11-29', 'Matemática', 'CONCLUIDO');
-- INSERT IGNORE INTO fabricadosaber.teacher VALUES (3, 'Apt 102', 102, '1992-03-20', 'Fortaleza', '987.654.321-00', 'teacher03@email.com', 'Professor 03', 'CEARA', 'Praia', '85 87654-3210', '2023-11-28', '77.543.890', 'Rua das Palmeiras', '34567-890', '2022-09-30', 1800, '2023-11-28', 'História', 'CURSANDO');
-- INSERT IGNORE INTO fabricadosaber.teacher VALUES (4, 'Apt 405', 103, '1988-12-10', 'Brasília', '234.567.890-12', 'teacher04@email.com', 'Professor 04', 'DISTRITO_FEDERAL', 'Setor Comercial', '61 76543-2109', '2023-11-27', '88.876.543', 'Quadra dos Ministérios', '56789-012', '2022-08-31', 2200, '2023-11-27', 'Geografia', 'CONCLUIDO');
-- INSERT IGNORE INTO fabricadosaber.teacher VALUES (5, 'House 10', 104, '1995-06-25', 'Recife', '345.678.901-23', 'teacher05@email.com', 'Professor 05', 'PERNAMBUCO', 'Boa Viagem', '81 65432-1098', '2023-11-26', '99.234.567', 'Rua do Mar', '67890-123', '2022-07-31', 2500, '2023-11-26', 'Português', 'CURSANDO');
-- INSERT IGNORE INTO fabricadosaber.teacher VALUES (6, 'Suite 202', 105, '1980-02-05', 'Rio de Janeiro', '456.789.012-34', 'teacher06@email.com', 'Professor 06', 'RIO_DE_JANEIRO', 'Copacabana', '21 54321-0987', '2023-11-25', '66.789.012', 'Avenida Atlântica', '78901-234', '2022-06-30', 1800, '2023-11-25', 'Ciências', 'CONCLUIDO');
--
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('team', 1, '100', 'PRIMEIRA_SERIE', 'Turma A', 0, '2023-12-31', '2023-12-10', 1);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('team', 2, '200', 'SEGUNDA_SERIE', 'Turma B', 0, '2023-12-31', '2023-12-10', 2);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('team', 3, '300', 'TERCEIRA_SERIE', 'Turma C', 0, '2023-12-31', '2023-12-10', 3);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('team', 4, '400', 'QUARTA_SERIE', 'Turma D', 0, '2023-12-31', '2023-12-10', 4);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('team', 5, '500', 'QUINTA_SERIE', 'Turma E', 0, '2023-12-31', '2023-12-10', 5);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('team', 6, '600', 'PRIMEIRA_SERIE', 'Turma F', 0, '2023-12-31', '2023-12-10', 6);
--
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('vacation', 7, '101', 'PRIMEIRA_SERIE', 'Creche A', 0, '2023-12-31', '2023-12-10', 1);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('vacation', 8, '202', 'SEGUNDA_SERIE', 'Creche B', 0, '2023-12-31', '2023-12-10', 2);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('vacation', 9, '303', 'TERCEIRA_SERIE', 'Creche C', 0, '2023-12-31', '2023-12-10', 3);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('vacation', 10, '404', 'QUARTA_SERIE', 'Creche D', 0, '2023-12-31', '2023-12-10', 4);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('vacation', 11, '505', 'QUINTA_SERIE', 'Creche E', 0, '2023-12-31', '2023-12-10', 5);
-- INSERT IGNORE INTO fabricadosaber.team VALUES ('vacation', 12, '606', 'PRIMEIRA_SERIE', 'Creche F', 0, '2023-12-31', '2023-12-10', 6);
--
-- INSERT IGNORE INTO fabricadosaber.parent VALUES (1, 'Suite 202', 105, '1980-02-05', 'Rio de Janeiro', '456.789.012-99', 'parent01@email.com', 'Parente 01', 'RIO_DE_JANEIRO', 'Copacabana', '21 54321-0999', '2023-11-25', '66.789.012', 'Avenida Atlântica', '78901-234', 'Empresa fictícia', 'Engenheiro civil', 'PAI');
-- INSERT IGNORE INTO fabricadosaber.parent VALUES (2, 'Apt 405', 103, '1988-12-10', 'Brasília', '234.567.890-88', 'parent02@email.com', 'Parente 02', 'DISTRITO_FEDERAL', 'Setor Comercial', '61 76543-2108', '2023-11-27', '88.876.543', 'Quadra dos Ministérios', '56789-012', 'Empresa fictícia', 'Arquiteto', 'MAE');
-- INSERT IGNORE INTO fabricadosaber.parent VALUES (3, 'House 10', 104, '1995-06-25', 'Recife', '345.678.901-22', 'parent03@email.com', 'Parente 03', 'PERNAMBUCO', 'Boa Viagem', '81 65432-1097', '2023-11-26', '99.234.567', 'Rua do Mar', '67890-123', 'Empresa fictícia', 'Designer', 'PAI');
-- INSERT IGNORE INTO fabricadosaber.parent VALUES (4, 'Suite 202', 105, '1980-02-05', 'Rio de Janeiro', '456.789.012-98', 'parent04@email.com', 'Parente 04', 'RIO_DE_JANEIRO', 'Copacabana', '21 34321-0186', '2023-11-25', '66.785.012', 'Avenida Atlântica', '78941-234', 'Empresa fictícia', 'Engenheiro Eletricista', 'MAE');
--
-- INSERT IGNORE INTO fabricadosaber.student VALUES (1, '-', 105, '2023-02-05', 'Rio de Janeiro', '-', '-', 'Estudante 01', 'RIO_DE_JANEIRO', 'Copacabana', '-', '2023-11-25', '-', 'Avenida Atlântica', '78941-234', 'Belo Horizonte', 'Belo Horizonte', 'PREFIRO_NAO_DECLARAR', 'PREFIRO_NAO_DECLARAR');
-- INSERT IGNORE INTO fabricadosaber.student VALUES (2, '-', 103, '2023-03-10', 'Brasília', '-', '-', 'Estudante 02', 'DISTRITO_FEDERAL', 'Setor Comercial', '-', '2023-11-27', '-', 'Escola Municipal XYZ', '56789-012', 'Belo Horizonte', 'São Pedro', 'PARDO', 'PROTESTANTISMO');
-- INSERT IGNORE INTO fabricadosaber.student VALUES (3, '-', 104, '2023-04-25', 'Recife', '-', '-', 'Estudante 03', 'PERNAMBUCO', 'Boa Viagem', '-', '2023-11-26', '-', 'Escola Estadual ABC', '67890-123', 'Porto Alegre', 'Centro', 'BRANCO', 'CATOLICISMO');
-- INSERT IGNORE INTO fabricadosaber.student VALUES (4, '-', 105, '2023-05-05', 'Rio de Janeiro', '-', '-', 'Estudante 04', 'RIO_DE_JANEIRO', 'Copacabana', '-', '2023-11-25', '-', 'Escola Particular XYZ', '78901-234', 'Belo Horizonte', 'Savassi', 'AMARELO', 'CANDOMBLE');
-- INSERT IGNORE INTO fabricadosaber.student VALUES (5, '-', 106, '2023-06-15', 'Porto Alegre', '-', '-', 'Estudante 05', 'RIO_GRANDE_DO_SUL', 'Moinhos de Vento', '-', '2023-11-24', '-', 'Escola Estadual ZZZ', '89012-345', 'Belo Horizonte', 'Lourdes', 'OUTRA', 'NAO_POSSUI');
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (1, 1);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (1, 2);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (2, 1);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (2, 2);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (3, 1);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (3, 2);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (4, 1);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (4, 2);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (5, 1);
-- INSERT IGNORE INTO fabricadosaber.student_parents VALUES (5, 2);
--
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 1, 1);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 1, 2);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 1, 3);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 2, 4);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 2, 5);
--
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 7, 1);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 5, 1);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 6, 1);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 5, 2);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 6, 2);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 6, 3);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 7, 3);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 8, 3);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 9, 4);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 10, 4);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 11, 4);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 12, 4);
-- INSERT IGNORE INTO fabricadosaber.student_team_association VALUES ('-', 1,'2023-06-15', 12, 5);
