-- =====================================================
-- GESTOR DE CAIXA - Script do Banco de Dados MySQL
-- Execute este script no MySQL Workbench
-- =====================================================

-- 1. Criar o banco de dados
CREATE DATABASE IF NOT EXISTS gestor_caixa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. Selecionar o banco
USE gestor_caixa;

-- 3. Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    nome     VARCHAR(100) NOT NULL,
    usuario  VARCHAR(50)  NOT NULL UNIQUE,
    senha    VARCHAR(100) NOT NULL,
    admin    BOOLEAN DEFAULT FALSE,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 4. Tabela de Movimentações
CREATE TABLE IF NOT EXISTS movimentacoes (
    id         INT PRIMARY KEY AUTO_INCREMENT,
    descricao  VARCHAR(200) NOT NULL,
    tipo       ENUM('Venda', 'Despesa') NOT NULL,
    valor      DECIMAL(10, 2) NOT NULL CHECK (valor > 0),
    data       DATE NOT NULL,
    id_usuario INT NOT NULL,
    criado_em  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 5. Índice para melhorar performance nas consultas por data
CREATE INDEX IF NOT EXISTS idx_data ON movimentacoes(data);
CREATE INDEX IF NOT EXISTS idx_usuario ON movimentacoes(id_usuario);

-- =====================================================
-- DADOS INICIAIS (Opcional - para testes)
-- =====================================================

-- Usuário admin padrão (senha: admin)
INSERT INTO usuarios (nome, usuario, senha, admin)
VALUES ('Administrador', 'admin', 'admin', TRUE)
ON DUPLICATE KEY UPDATE nome = nome;

-- Usuário de teste (senha: 123456)
INSERT INTO usuarios (nome, usuario, senha, admin)
VALUES ('João da Loja', 'joao', '123456', FALSE)
ON DUPLICATE KEY UPDATE nome = nome;

-- Movimentações de exemplo para o usuário joao (id=2)
INSERT INTO movimentacoes (descricao, tipo, valor, data, id_usuario) VALUES
('Venda de pão', 'Venda', 45.00, CURDATE(), 2),
('Venda de refrigerante', 'Venda', 120.50, CURDATE(), 2),
('Compra de mercadoria', 'Despesa', 300.00, CURDATE(), 2),
('Conta de luz', 'Despesa', 180.00, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 2),
('Venda do dia anterior', 'Venda', 560.00, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 2);

-- =====================================================
-- CONSULTAS ÚTEIS PARA VERIFICAR
-- =====================================================

-- Ver todos os usuários:
-- SELECT * FROM usuarios;

-- Ver movimentações com saldo:
-- SELECT
--   SUM(CASE WHEN tipo = 'Venda' THEN valor ELSE 0 END) AS total_vendas,
--   SUM(CASE WHEN tipo = 'Despesa' THEN valor ELSE 0 END) AS total_despesas,
--   SUM(CASE WHEN tipo = 'Venda' THEN valor ELSE -valor END) AS saldo
-- FROM movimentacoes WHERE id_usuario = 2;
