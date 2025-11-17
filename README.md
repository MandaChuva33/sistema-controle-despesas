# 💰 Sistema de Controle de Despesas Pessoais (CFP)

## Visão Geral do Projeto

Este projeto consiste em um **Sistema de Controle de Despesas Pessoais (CFP)**, desenvolvido em Java, com foco na aplicação prática dos conceitos de Programação Orientada a Objetos (POO). O sistema é uma ferramenta de linha de comando projetada para auxiliar o usuário no gerenciamento e acompanhamento de suas finanças pessoais.

| Status | Versão | Linguagem |
| :--- | :--- | :--- |
| **MVP Estendido** | `v1.0.0` | Java 17+ |

---

## ✨ Funcionalidades Principais

O sistema foi estruturado para oferecer um controle financeiro robusto e intuitivo, com as seguintes funcionalidades:

*   **Registro Completo de Despesas:** Permite o cadastro de despesas com detalhes como descrição, valor, categoria e, fundamentalmente, a **Data de Vencimento**.
*   **Gestão de Pagamentos:** Marca despesas pendentes como pagas através de um identificador único (ID).
*   **Visualização Segmentada:** Listagem clara e separada de despesas **Pendentes** e **Pagas**.
*   **Exclusão de Registros:** Funcionalidade para remover despesas do sistema.
*   **Relatórios Financeiros:**
    *   Geração de relatório consolidado do total gasto por **Categoria**.
    *   Cálculo e exibição do **Total Geral** de todas as despesas cadastradas.
*   **Robustez e Tratamento de Erros:** Implementação de tratamento de exceções para garantir a integridade dos dados de entrada (valores, IDs, etc.).

---

## 🛠️ Estrutura e Conceitos de POO

O projeto segue um design modular, aplicando os seguintes conceitos de POO:

| Arquivo | Responsabilidade | Conceito de POO |
| :--- | :--- | :--- |
| `Main.java` | **Controlador/Interface** | Lógica de interação com o usuário (Menu). |
| `GerenciadorDespesas.java` | **Lógica de Negócio** | Gerencia a coleção de despesas e as regras de negócio. |
| `Despesa.java` | **Modelo de Dados** | Representa a entidade Despesa. |

**Conceitos Aplicados:**

*   **Encapsulamento:** Uso de atributos privados e métodos de acesso (`getters` e `setters`) na classe `Despesa`.
*   **Sobrescrita (`toString()`):** Implementação de `toString()` em `Despesa` para representação textual formatada.
*   **Atributos Estáticos:** Utilização de um contador estático para garantir IDs únicos e sequenciais para cada despesa.
*   **Collections:** Uso de `ArrayList` para armazenamento dinâmico e gerenciamento das despesas.

---

## 🚀 Como Iniciar

### Pré-requisitos
*   Java Development Kit (JDK) 17 ou superior.

### Execução
1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/MandaChuva33/sistema-controle-despesas.git
    cd sistema-controle-despesas
    ```
2.  **Compile:**
    ```bash
    javac *.java
    ```
3.  **Execute:**
    ```bash
    java Main
    ```

---

## 👨‍🎓 Informações do Autor

| Nome | RA |
| :--- | :--- |
| Bruno Toshiaki Tazoe de Oliveira | 206068-25 |

*Projeto desenvolvido como parte de uma disciplina acadêmica.*

## 📄 Licença

Este projeto é de uso acadêmico e educacional.
