# Sistema de Controle de Despesas

## Objetivo Geral

Desenvolver um **Sistema de Controle de Despesas** que permita ao usuário gerenciar despesas e pagamentos (Conciliação da despesa), atendendo a todos os requisitos de Programação Orientada a Objetos (POO) e persistência de dados em arquivos de texto, conforme especificado no trabalho acadêmico.

## Estratégia de Construção (PARTE 01)

O projeto foi refatorado para utilizar o **mínimo de arquivos possível**, consolidando todas as classes de modelo, interfaces e DAOs em apenas dois arquivos principais: `Main.java` (lógica de menu e execução) e `Modelos.java` (classes de modelo e DAOs).

A construção do sistema seguiu a estrutura de commits solicitada:

1.  **Commit 0.0.1 (B4T01.1):** Estrutura inicial e menu com *placeholders* (`println`).
2.  **Commit 0.0.2 (B4T01.2):** Classes de modelo, interfaces e DAOs consolidadas em `Modelos.java` (POC).
3.  **Commit 0.0.3 (B4T01.3 - MVP):** Integração completa do MVP em `Main.java`, aplicação de todos os conceitos de POO e documentação final.

## Requisitos de POO Implementados

| Conceito de POO | Classes/Interfaces | Descrição da Implementação |
| :--- | :--- | :--- |
| **Classes e Herança** | `Despesa` (Abstrata), `DespesaAlimentacao` (Concreta) | `Despesa` define o comportamento comum. `DespesaAlimentacao` herda de `Despesa` e implementa o método abstrato `getDetalheTipo()`. |
| **Interfaces e Polimorfismo** | `Pagavel` (Interface) | A interface `Pagavel` define o contrato (`isPago()`, `anotarPagamento()`). A classe `Despesa` (e suas subclasses) implementa esta interface, permitindo que um objeto `DespesaAlimentacao` seja tratado como `Pagavel`. |
| **Sobrecarga de Construtor** | `Despesa`, `DespesaAlimentacao` | Possui construtores sobrecarregados para criação de objetos e para reconstrução a partir da persistência (arquivo de texto). |
| **Sobrescrita de Método** | `DespesaAlimentacao` | Sobrescreve o método abstrato `getDetalheTipo()` e o método `toString()` (herdado de `Object` e aprimorado em `Despesa`) para persistência. |
| **Métodos e Atributos Estáticos** | `Despesa`, `UsuarioDAO` | O atributo estático `contadorDespesas` em `Despesa` mantém a contagem global. O método estático `criptografarSenha()` em `UsuarioDAO` é usado para criptografia. |
| **Criptografia de Senhas** | `UsuarioDAO` | Implementa o método estático `criptografarSenha(String senha)` utilizando o algoritmo **SHA-256** para armazenar as senhas de forma segura. |

## Documentação das Classes (Consolidada em `Modelos.java`)

Todas as classes, interfaces e DAOs estão definidas no arquivo `src/Modelos.java`.

### 1. `Pagavel` (Interface)

Define o contrato para qualquer objeto que possa ser pago.

### 2. `Despesa` (Classe Abstrata)

Classe base para todas as despesas, implementa `Pagavel`. Contém lógica de ID e persistência.

### 3. `DespesaAlimentacao` (Classe Concreta)

Exemplo de subclasse de `Despesa` com implementação de `getDetalheTipo()`.

### 4. `TipoDespesa` (Modelo)

Representa a categoria de uma despesa. Persistido em `tipos_despesa.txt`.

### 5. `Usuario` (Modelo)

Representa um usuário do sistema.

### 6. `TipoDespesaDAO` (Data Access Object)

Gerencia a persistência de `TipoDespesa` em `tipos_despesa.txt`.

### 7. `UsuarioDAO` (Data Access Object)

Gerencia a persistência de `Usuario` em `usuarios.txt` e a criptografia de senhas.

### 8. `DespesaDAO` (Data Access Object - Definido em `Main.java`)

Gerencia a persistência de `Despesa` em `despesas.txt`. Inclui métodos para `salvar`, `listarTodos` e `excluir`.

## Localização da Documentação

O arquivo `CHANGELOG.md` está localizado no diretório `docs/`, conforme solicitado.

[CHANGELOG.md](docs/CHANGELOG.md)
