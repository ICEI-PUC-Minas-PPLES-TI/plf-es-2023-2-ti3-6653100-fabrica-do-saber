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

--        senha utilizadas na encriptacao: admin123456
INSERT IGNORE INTO fabricadosaber.user VALUES (1, '2023-11-25', "admin@email.com", "administrator", "$2a$10$MPwDDc6gsgGHpc.rQ8Cea.Q/3evYk/tzBq7nUEkZIU./VxCYcNufW" );
INSERT IGNORE INTO fabricadosaber.user_profile VALUES (1, 1);
INSERT IGNORE INTO fabricadosaber.user VALUES (2, '2023-11-25', "user@email.com", "usuario", "$2a$10$MPwDDc6gsgGHpc.rQ8Cea.Q/3evYk/tzBq7nUEkZIU./VxCYcNufW" );
INSERT IGNORE INTO fabricadosaber.user_profile VALUES (2, 1);

