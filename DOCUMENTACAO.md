# GestorCaixa — Documentação do Projeto

## Visão Geral

**GestorCaixa** é um sistema desktop de controle financeiro para comércio, desenvolvido em Java com interface Swing. Permite que usuários façam login, se cadastrem, registrem movimentações financeiras (vendas e despesas), visualizem um dashboard de resumo e gerem relatórios.

---

## Tecnologias Utilizadas

| Componente | Versão |
|---|---|
| Java | 17 |
| Swing | Nativo JDK |
| Maven | 3.x |
| MySQL | 8.x |
| mysql-connector-java | 8.0.33 |

---

## Estrutura de Pacotes

```
src/main/java/
├── telas/               ← Telas manuais (login e cadastro)
│   ├── TelaLogin.java
│   └── TelaCadastro.java
├── testetelas/          ← Telas do sistema principal
│   ├── Login.java
│   ├── Cadastro3.java
│   ├── Dashboard.java
│   ├── FormularioMovimentacao.java
│   └── Relatorio.java
├── conexao/
│   └── Conexao.java
└── model/
    ├── UsuarioBean.java
    ├── UsuarioDAO.java
    ├── MovimentacaoBean.java
    └── MovimentacaoDAO.java
```

---

## Banco de Dados

**Banco:** `gestor_caixa`  
**Host:** `localhost:3337`  
**Usuário:** `root` / **Senha:** *(vazia)*

### Tabela `usuarios`

| Coluna | Tipo | Descrição |
|---|---|---|
| id | INT (PK, AUTO_INCREMENT) | Identificador único |
| nome | VARCHAR | Nome completo |
| usuario | VARCHAR | Login do usuário |
| senha | VARCHAR | Senha (texto plano) |
| admin | BOOLEAN | Flag de administrador |

### Tabela `movimentacoes`

| Coluna | Tipo | Descrição |
|---|---|---|
| id | INT (PK, AUTO_INCREMENT) | Identificador único |
| usuario_id | INT (FK) | Referência ao usuário |
| tipo | VARCHAR | `"Venda"` ou `"Despesa"` |
| descricao | VARCHAR | Descrição da movimentação |
| valor | DECIMAL | Valor em reais |
| data | DATE/DATETIME | Data do registro |

---

## Classes e Responsabilidades

### `telas.TelaLogin`
Tela de entrada manual do sistema. Exibe painel verde com identidade visual, campos de usuário e senha. Autentica diretamente via SQL na tabela `usuarios`. Em caso de sucesso, instancia o `Dashboard` passando o `UsuarioBean` do usuário logado. Botão "Cadastre-se" navega para `TelaCadastro`.

### `telas.TelaCadastro`
Tela de registro de novos usuários. Coleta nome, usuário, senha e confirmação de senha. Valida campos vazios e divergência de senhas antes de chamar `UsuarioDAO.cadastrar()`. Verifica se o nome de usuário já existe via `UsuarioDAO.usuarioExiste()`. Ao concluir, retorna para `TelaLogin`.

### `testetelas.Login`
Versão alternativa da tela de login gerada pelo NetBeans com GroupLayout. Mesma funcionalidade de autenticação, porém com layout construído pelo Form Editor.

### `testetelas.Cadastro3`
Versão alternativa da tela de cadastro gerada pelo NetBeans.

### `testetelas.Dashboard`
Tela principal do sistema pós-login. Exibe:
- Nome do usuário logado
- Cards de resumo: saldo atual, total de vendas, total de despesas
- Tabela de movimentações recentes
- Botões para nova venda, nova despesa, relatório e logout

Recebe um `UsuarioBean` no construtor e carrega os dados via `MovimentacaoDAO`.

### `testetelas.FormularioMovimentacao`
Janela de diálogo para inserção e edição de movimentações. Recebe tipo (`Venda`/`Despesa`) e id para edição (ou `-1` para novo registro). Salva via `MovimentacaoDAO`.

### `testetelas.Relatorio`
Tela de relatório com filtros e listagem de movimentações. Permite visualizar histórico por período ou tipo.

### `conexao.Conexao`
Singleton de conexão com o banco MySQL. Método estático `conectar()` retorna uma `Connection` aberta, reutilizando a instância se já estiver ativa.

### `model.UsuarioBean`
POJO de usuário com campos: `id`, `nome`, `usuario`, `senha`, `admin`. Getters e setters padrão.

### `model.UsuarioDAO`
Acesso a dados de usuário:
- `cadastrar(UsuarioBean)` → INSERT na tabela `usuarios`
- `logar(String usuario, String senha)` → SELECT com credenciais, retorna `UsuarioBean`
- `usuarioExiste(String usuario)` → verifica duplicidade de login

### `model.MovimentacaoBean`
POJO de movimentação com campos: `id`, `usuarioId`, `tipo`, `descricao`, `valor`, `data`.

### `model.MovimentacaoDAO`
Acesso a dados financeiros:
- `inserir(MovimentacaoBean)` → INSERT
- `atualizar(MovimentacaoBean)` → UPDATE por id
- `excluir(int id)` → DELETE por id
- `listarPorUsuario(int usuarioId)` → SELECT de todas as movimentações do usuário
- `calcularSaldo/Vendas/Despesas(int usuarioId)` → agregações financeiras

---

## Fluxo de Navegação

```
TelaLogin
  ├── [Login OK]     → Dashboard
  ├── [Cadastre-se]  → TelaCadastro → TelaLogin
  └── [Login Inválido] → mensagem de erro

Dashboard
  ├── [Nova Venda]    → FormularioMovimentacao(tipo=Venda)
  ├── [Nova Despesa]  → FormularioMovimentacao(tipo=Despesa)
  ├── [Editar linha]  → FormularioMovimentacao(id=X)
  ├── [Relatório]     → Relatorio
  └── [Sair]          → TelaLogin
```

---

## Como Executar

**Pré-requisito:** MySQL rodando em `localhost:3337` com banco `gestor_caixa` configurado (usar `database_setup.sql`).

```bash
# Compilar e empacotar
mvn clean package

# Executar via Maven
mvn exec:java

# Ou executar o JAR gerado
java -jar target/gestorCaixa-1.0-SNAPSHOT.jar
```

**Classe principal:** `telas.TelaLogin`

---

## Pontos de Atenção

- As senhas são armazenadas em **texto plano**. Para produção, aplicar hash (ex: BCrypt).
- A conexão com o banco não usa pool. Para múltiplos usuários simultâneos, considerar HikariCP.
- O arquivo `login.form` e `cadastro.form` são artefatos do NetBeans Form Editor e não são utilizados na compilação Maven.
