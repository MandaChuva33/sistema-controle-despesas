# Histórico de Alterações

## 0.0.1 - [Data do Commit]

### Adicionado
- Estrutura inicial do projeto.
- Menu principal com todas as opções solicitadas.
- Implementação de `println` para cada funcionalidade do menu.
- Criação da estrutura de diretórios `src` e `docs`.
- Arquivo `README.md` inicial.


## 0.0.2 - [Data do Commit]

### Adicionado
- Implementação da Prova de Conceito (POC) para a funcionalidade "Gerenciar Tipos de Despesa".
- Criação das classes `TipoDespesa` (modelo) e `TipoDespesaDAO` (acesso a dados em arquivo de texto).
- Separação de prioridades e início da abstração de regras de negócio.
- Atualização do `Main.java` para integrar a POC.


## 0.0.3 - [Data do Commit]

### Adicionado
- Implementação do Mínimo Produto Viável (MVP) do sistema.
- Aplicação completa dos conceitos de POO:
    - Classes Abstratas (`Despesa`) e Concretas (`DespesaAlimentacao`).
    - Interface (`Pagavel`) e Polimorfismo.
    - Sobrecarga e Sobrescrita de Construtor.
    - Métodos e Atributos Estáticos (`contadorDespesas` em `Despesa`).
- Implementação da criptografia de senhas (SHA-256) em `UsuarioDAO`.
- Implementação dos DAOs para persistência em arquivo de texto (`DespesaDAO`, `UsuarioDAO`).
- Integração das funcionalidades de `Entrar Despesa`, `Anotar Pagamento`, `Listar Despesas` e `Gerenciar Usuários` no `Main.java`.
- Documentação completa do projeto no `README.md`.
