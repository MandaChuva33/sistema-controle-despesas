# Sistema de Controle de Despesas

## Objetivo Geral

Desenvolver um **Sistema de Controle de Despesas** que permita ao usuário gerenciar despesas e pagamentos (Conciliação da despesa), atendendo a todos os requisitos de Programação Orientada a Objetos (POO) e persistência de dados em arquivos de texto, conforme especificado no trabalho acadêmico.

## Estratégia de Construção (PARTE 01)

A construção do sistema foi dividida em três commits principais, seguindo as entregas solicitadas:

1.  **Commit 0.0.1 (B4T01.1):** Foco na estrutura inicial, criação do repositório, e implementação do menu principal com *placeholders* (`println`) para cada funcionalidade.
2.  **Commit 0.0.2 (B4T01.2):** Foco na Prova de Conceito (POC) e separação de prioridades. Implementação das classes de persistência (`TipoDespesaDAO`) e modelo (`TipoDespesa`) para o gerenciamento de tipos de despesa, validando o uso de arquivos de texto como "banco de dados".
3.  **Commit 0.0.3 (B4T01.3 - MVP):** Foco na entrega do Mínimo Produto Viável (MVP) com a implementação das principais funcionalidades, aplicando os conceitos de POO (Classes, Herança, Interfaces, Polimorfismo, Métodos Estáticos) e documentação completa.

## Requisitos de POO Implementados

| Conceito de POO | Classes/Interfaces | Descrição da Implementação |
| :--- | :--- | :--- |
| **Classes e Herança** | `Despesa` (Abstrata), `DespesaAlimentacao` (Concreta) | `Despesa` define o comportamento comum. `DespesaAlimentacao` herda de `Despesa` e implementa o método abstrato `getDetalheTipo()`. |
| **Interfaces e Polimorfismo** | `Pagavel` (Interface) | A interface `Pagavel` define o contrato (`isPago()`, `anotarPagamento()`). A classe `Despesa` (e suas subclasses) implementa esta interface, permitindo que um objeto `DespesaAlimentacao` seja tratado como `Pagavel`. |
| **Sobrecarga de Construtor** | `Despesa` | Possui dois construtores: um que recebe `LocalDate` e outro que recebe `String` para a data de vencimento, facilitando a criação de objetos. |
| **Sobrescrita de Método** | `DespesaAlimentacao` | Sobrescreve o construtor e o método `toString()` (herdado de `Object` e aprimorado em `Despesa`) para personalizar o comportamento. |
| **Métodos e Atributos Estáticos** | `Despesa` | O atributo estático `contadorDespesas` mantém a contagem global de despesas criadas, e o método estático `getContadorDespesas()` permite o acesso a essa contagem. |
| **Criptografia de Senhas** | `UsuarioDAO` | Implementa o método estático `criptografarSenha(String senha)` utilizando o algoritmo **SHA-256** para armazenar as senhas de forma segura. |

## Documentação das Classes

A documentação detalhada de cada classe, seus métodos e atributos está descrita abaixo.

### 1. `TipoDespesa` (Modelo)

Representa a categoria de uma despesa (ex: Alimentação, Transporte).

| Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `int` | Identificador único do tipo de despesa. |
| `nome` | `String` | Nome da categoria. |

| Método | Descrição |
| :--- | :--- |
| `TipoDespesa(int id, String nome)` | Construtor. |
| `toString()` | Sobrescrito para formatar a linha de persistência (`id;nome`). |
| `fromString(String linha)` | Método estático para recriar o objeto a partir da linha do arquivo. |

### 2. `TipoDespesaDAO` (Data Access Object)

Responsável pela persistência e recuperação dos objetos `TipoDespesa` no arquivo `tipos_despesa.txt`.

| Método | Descrição |
| :--- | :--- |
| `salvar(TipoDespesa tipo)` | Adiciona um novo tipo ao arquivo. |
| `listarTodos()` | Retorna uma lista de todos os tipos de despesa cadastrados. |

### 3. `Pagavel` (Interface)

Define o contrato para qualquer objeto que possa ser pago.

| Método | Descrição |
| :--- | :--- |
| `isPago()` | Retorna `true` se a despesa estiver totalmente paga. |
| `anotarPagamento(double valorPago)` | Registra um pagamento parcial ou total. |

### 4. `Despesa` (Classe Abstrata)

Classe base para todas as despesas, implementa a interface `Pagavel`.

| Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `contadorDespesas` | `static int` | Contador global de despesas criadas. |
| `id` | `int` | Identificador único da despesa. |
| `descricao` | `String` | Detalhe da despesa. |
| `valor` | `double` | Valor total da despesa. |
| `dataVencimento` | `LocalDate` | Data de vencimento. |
| `tipo` | `TipoDespesa` | Categoria da despesa. |
| `valorPago` | `double` | Valor já pago. |
| `paga` | `boolean` | Status de pagamento. |

| Método | Descrição |
| :--- | :--- |
| `Despesa(...)` | Construtores sobrecarregados. |
| `getContadorDespesas()` | Método estático para obter a contagem. |
| `anotarPagamento(...)` | Implementação de `Pagavel`. |
| `getDetalheTipo()` | **Abstrato**. Deve ser implementado pelas subclasses. |

### 5. `DespesaAlimentacao` (Classe Concreta)

Exemplo de subclasse de `Despesa`.

| Método | Descrição |
| :--- | :--- |
| `getDetalheTipo()` | Implementa o método abstrato, retornando um detalhe específico. |

### 6. `DespesaDAO` (Data Access Object)

Responsável pela persistência e recuperação dos objetos `Despesa` no arquivo `despesas.txt`.

| Método | Descrição |
| :--- | :--- |
| `salvar(Despesa despesa)` | Salva ou atualiza uma despesa no arquivo. |
| `listarTodos()` | Retorna a lista de todas as despesas. |

### 7. `Usuario` (Modelo)

Representa um usuário do sistema.

| Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `int` | Identificador único do usuário. |
| `login` | `String` | Nome de usuário. |
| `senhaCriptografada` | `String` | Senha armazenada após criptografia. |

### 8. `UsuarioDAO` (Data Access Object)

Responsável pela persistência e criptografia de usuários no arquivo `usuarios.txt`.

| Método | Descrição |
| :--- | :--- |
| `salvar(Usuario usuario)` | Salva um novo usuário. |
| `listarTodos()` | Retorna a lista de todos os usuários. |
| `criptografarSenha(String senha)` | **Método estático** que usa SHA-256 para criptografar a senha. |

## Localização da Documentação

O arquivo `CHANGELOG.md` está localizado no diretório `docs/`, conforme solicitado.

[CHANGELOG.md](docs/CHANGELOG.md)
