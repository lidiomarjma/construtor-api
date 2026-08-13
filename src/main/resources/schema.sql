-- Dropar tabelas na ordem correta para respeitar as chaves estrangeiras
DROP TABLE IF EXISTS obras_colaboradores CASCADE;
DROP TABLE IF EXISTS obras CASCADE;
DROP TABLE IF EXISTS colaboradores CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;

-- Tabela de Clientes
CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100) NOT NULL UNIQUE,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Colaboradores
CREATE TABLE colaboradores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    cargo VARCHAR(50) NOT NULL,
    telefone VARCHAR(20),
    data_admissao DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Tabela de Obras
CREATE TABLE obras (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    orcamento NUMERIC(15, 2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PLANEJAMENTO',
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_obras_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE RESTRICT
);

-- Tabela de Junção (Alocação de Colaboradores na Obra)
CREATE TABLE obras_colaboradores (
    obra_id BIGINT NOT NULL,
    colaborador_id BIGINT NOT NULL,
    data_alocacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (obra_id, colaborador_id),
    CONSTRAINT fk_alocacao_obra FOREIGN KEY (obra_id) REFERENCES obras(id) ON DELETE CASCADE,
    CONSTRAINT fk_alocacao_colaborador FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id) ON DELETE CASCADE
);
