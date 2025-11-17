# Sistema de Controle Financeiro Pessoal (CFP)

![Versão](https://img.shields.io/badge/versão-1.0.0-blue.svg)
![Linguagem](https://img.shields.io/badge/Linguagem-Java%2017%2B-orange.svg)
![Status](https://img.shields.io/badge/status-MVP%20Estendido-green.svg)

Este projeto consiste em um **Sistema de Controle Financeiro Pessoal (CFP)** desenvolvido em Java, aplicando os princípios da Programação Orientada a Objetos (POO). O objetivo é fornecer uma ferramenta de linha de comando simples e eficaz para o gerenciamento de despesas, auxiliando o usuário no controle de suas finanças pessoais.

## 📋 Funcionalidades Implementadas

O sistema oferece as seguintes funcionalidades principais:

-   ✅ **Cadastro de Despesas**: Permite registrar despesas com descrição, valor, categoria e, crucialmente, a **Data de Vencimento**.
-   ✅ **Registro de Pagamento**: Marca uma despesa pendente como paga através de seu ID.
-   ✅ **Visualização Detalhada**: Listagem separada de despesas **Pendentes** e **Pagas**.
-   ✅ **Exclusão de Despesas**: Possibilidade de remover registros financeiros.
-   ✅ **Relatório por Categoria**: Gera um relatório consolidado do total gasto em uma categoria específica.
-   ✅ **Cálculo de Total Geral**: Exibe o somatório de todas as despesas cadastradas no sistema.
-   ✅ **Tratamento de Entrada**: Implementação de tratamento de exceções para entradas de usuário (como valores e IDs), garantindo maior robustez.

## 🚀 Como Executar

### Pré-requisitos

-   Java Development Kit (JDK) 17 ou superior.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone [URL DO REPOSITÓRIO]
    cd sistema-controle-financeiro
    ```

2.  **Compile os arquivos:**
    ```bash
    javac *.java
    ```

3.  **Execute o programa:**
    ```bash
    java Main
    ```

## 🏗️ Estrutura do Projeto

O projeto é composto por três classes principais, seguindo o padrão de separação de responsabilidades:

| Arquivo | Responsabilidade | Descrição |
| :--- | :--- | :--- |
| `Main.java` | **Interface/Controlador** | Contém o menu principal e a lógica de interação com o usuário (entrada e saída de dados). |
| `GerenciadorDespesas.java` | **Lógica de Negócio/Serviço** | Gerencia a coleção de despesas e implementa as regras de negócio (cadastro, listagem, pagamento, relatórios). |
| `Despesa.java` | **Modelo de Dados** | Representa a entidade Despesa, contendo seus atributos (ID, descrição, valor, categoria, data de vencimento, status de pagamento) e métodos de acesso (Getters/Setters). |

## 🎯 Conceitos de POO Aplicados

Este projeto demonstra a aplicação prática dos seguintes conceitos de Programação Orientada a Objetos:

-   **Encapsulamento**: Uso de atributos privados e métodos públicos (`getters` e `setters`) para controlar o acesso aos dados da classe `Despesa`.
-   **Sobrescrita de Métodos**: Implementação do método `toString()` na classe `Despesa` para fornecer uma representação textual formatada e detalhada do objeto.
-   **Atributos Estáticos**: Utilização de um contador estático (`contadorId`) na classe `Despesa` para garantir que cada nova despesa tenha um ID único e sequencial.
-   **Collections**: Uso da interface `List` e da classe `ArrayList` para armazenar e gerenciar dinamicamente a coleção de objetos `Despesa`.

## 👨‍💻 Autor

**Bruno Toshiaki Tazoe de Oliveira**  
**RA:** 206068-25

Projeto desenvolvido como parte da disciplina de Programação Orientada a Objetos.

## 📄 Licença

Este projeto é de uso acadêmico e educacional.
