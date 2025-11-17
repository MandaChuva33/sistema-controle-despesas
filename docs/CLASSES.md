# Documentação das Classes

## Autor
Pedro Pourchet - RA: 206454-25

Esta documentação detalha as classes, interfaces e DAOs (Data Access Objects) implementados no projeto, com foco na aplicação dos conceitos de Programação Orientada a Objetos (POO).

### 1. `Pagavel` (Interface)

Define o contrato para qualquer objeto que possa ser pago.

| Método | Descrição |
| :--- | :--- |
| `isPago()` | Retorna `true` se a despesa estiver totalmente paga. |
| `anotarPagamento(double valorPago)` | Registra um pagamento parcial ou total. |

### 2. `Despesa` (Classe Abstrata - Herança e Polimorfismo)

Classe base para todas as despesas, implementa a interface `Pagavel`.

| Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `contadorDespesas` | `static int` | **Atributo Estático** que mantém a contagem global de despesas criadas. |
| `id` | `int` | Identificador único da despesa. |
| `valor` | `double` | Valor total da despesa. |
| `tipo` | `TipoDespesa` | Categoria da despesa. |

| Método | Descrição |
| :--- | :--- |
| `Despesa(...)` | **Construtores Sobrecarrregados** (para criação e persistência). |
| `getContadorDespesas()` | **Método Estático** para obter a contagem. |
| `anotarPagamento(...)` | Implementação de `Pagavel`. |
| `getDetalheTipo()` | **Método Abstrato** para ser sobrescrito pelas subclasses. |

### 3. `DespesaAlimentacao` (Classe Concreta - Herança e Sobrescrita)

Exemplo de subclasse de `Despesa`.

| Método | Descrição |
| :--- | :--- |
| `getDetalheTipo()` | **Sobrescrita** do método abstrato, retornando um detalhe específico. |

### 4. `TipoDespesa` (Modelo)

Representa a categoria de uma despesa.

### 5. `Usuario` (Modelo)

Representa um usuário do sistema.

### 6. `TipoDespesaDAO`, `UsuarioDAO`, `DespesaDAO` (Data Access Objects)

Classes responsáveis pela persistência e recuperação dos dados em arquivos de texto.

| Classe | Responsabilidade | Destaque POO |
| :--- | :--- | :--- |
| `UsuarioDAO` | Gerencia `Usuario` em `usuarios.txt`. | Contém o **Método Estático** `criptografarSenha()` (SHA-256). |
| `TipoDespesaDAO` | Gerencia `TipoDespesa` em `tipos_despesa.txt`. | - |
| `DespesaDAO` | Gerencia `Despesa` em `despesas.txt`. | Utiliza `TipoDespesaDAO` para reconstruir objetos. |

### 7. `GerenciadorDespesas` (Classe de Gerenciamento)

Classe que centraliza a criação e acesso aos DAOs, facilitando a injeção de dependências no `Main.java`.
