-- Inserção de Clientes
INSERT INTO clientes (nome, cpf_cnpj, telefone, email) VALUES
('Construtora Alfa Ltda', '12345678000195', '555599998888', 'contato@alfa.com.br'),
('Incorporadora Beta S.A.', '98765432000110', '555588887777', 'financeiro@beta.com.br');

-- Inserção de Colaboradores
INSERT INTO colaboradores (nome, cpf, cargo, telefone, data_admissao) VALUES
('Carlos Eduardo Silva', '11122233344', 'Engenheiro Civil', '55991112222', '2023-01-15'),
('Maria Regina Souza', '22233344455', 'Mestre de Obras', '55992223333', '2023-03-01'),
('João Pedro Santos', '33344455566', 'Pedreiro', '55993334444', '2023-06-10');

-- Inserção de Obras (associadas aos clientes criados acima)
INSERT INTO obras (nome, endereco, orcamento, status, cliente_id) VALUES
('Residencial Vista Alegre', 'Rua das Flores, 100 - Centro', 450000.00, 'EM_ANDAMENTO', 1),
('Edifício Comercial Horizonte', 'Av. Presidente Vargas, 500', 1200000.00, 'PLANEJAMENTO', 2);
-- Alocação de Colaboradores nas Obras
INSERT INTO obras_colaboradores (obra_id, colaborador_id) VALUES
(1, 1), -- Carlos na obra Residencial Vista Alegre
(1, 3), -- João Pedro na obra Residencial Vista Alegre
(2, 2); -- Maria Regina na obra Edifício Comercial Horizonte
